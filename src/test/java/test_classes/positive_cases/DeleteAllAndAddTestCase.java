package test_classes.positive_cases;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import test_classes.BasicTest;
import test_task.CfgKeys;
import test_task.Filenames;
import test_task.data_models.Book;
import test_task.data_models.BookWithId;
import test_task.utils.JSONUtils;
import test_task.utils.LogUtils;
import test_task.utils.RESTUtils;

import java.util.List;
import java.util.Map;

public class DeleteAllAndAddTestCase extends BasicTest {
    @Test
    @Ignore
    @SuppressWarnings("unchecked")
    public void deleteAllBooksAndAddBookTest() {
        LogUtils.logInfo("Тестирование удаления всех записей и последующего добавления новой записи");
        List<BookWithId> getAllBooksBeforeDelete = JSONUtils.saveJSONToDataModel(RESTUtils.getBooks().getBody(), BookWithId.class);
        for (BookWithId getAllBook : getAllBooksBeforeDelete) {
            int bookId = getAllBook.getId();
            RESTUtils.deleteBook(bookId);
        }
        List<BookWithId> getAllBooksAfterDelete = JSONUtils.saveJSONToDataModel(RESTUtils.getBooks().getBody(), BookWithId.class);
        Assert.assertTrue(getAllBooksAfterDelete.isEmpty(), "Не все записи были удалены!");
        Book bookToPost = new Book((Map<String, Object>) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.BOOK_TO_POST));
        var postBookResponse = RESTUtils.postBook(bookToPost, RESTUtils.getHeaderJson());
        Assert.assertEquals(postBookResponse.getStatus(), HttpStatus.SC_CREATED, "Код статуса отличается от ожидаемого!");
        Assert.assertTrue(JSONUtils.validateJSONSchema(Filenames.bookResponseSchemaFileName,
                postBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
    }
}

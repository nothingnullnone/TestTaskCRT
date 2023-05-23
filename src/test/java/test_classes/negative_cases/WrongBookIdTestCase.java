package test_classes.negative_cases;

import org.apache.http.HttpStatus;
import org.testng.Assert;
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

public class WrongBookIdTestCase extends BasicTest {
    @Test
    public void getWrongIdTest() {
        LogUtils.logInfo("Тестирование GET запроса с несуществующим ID книги");
        List<BookWithId> getAllBooks = JSONUtils.saveJSONToDataModel(RESTUtils.getBooks().getBody(), BookWithId.class);
        int bookId = getAllBooks.isEmpty() ? 1 : getAllBooks.get(getAllBooks.size() - 1).getId() + 1;
        var getBookResponse = RESTUtils.getBook(bookId);
        Assert.assertEquals(getBookResponse.getStatus(), HttpStatus.SC_NOT_FOUND, "Код статуса отличается от ожидаемого!");
        Assert.assertTrue(JSONUtils.validateJSONSchema(Filenames.errorResponseSchemaFileName,
                getBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void putWrongIdTest() {
        LogUtils.logInfo("Тестирование PUT запроса с несуществующим ID книги");
        List<BookWithId> getAllBooks = JSONUtils.saveJSONToDataModel(RESTUtils.getBooks().getBody(), BookWithId.class);
        int bookId = getAllBooks.isEmpty() ? 1 : getAllBooks.get(getAllBooks.size() - 1).getId() + 1;
        Book bookToPut = new Book((Map<String, Object>) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.BOOK_TO_PUT));
        var putBookResponse = RESTUtils.putBook(bookId, bookToPut, RESTUtils.getHeaderJson());
        Assert.assertEquals(putBookResponse.getStatus(), HttpStatus.SC_NOT_FOUND, "Код статуса отличается от ожидаемого!");
        Assert.assertTrue(JSONUtils.validateJSONSchema(Filenames.errorResponseSchemaFileName,
                putBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
    }

    @Test
    public void deleteWrongIdTest() {
        LogUtils.logInfo("Тестирование DELETE запроса с несуществующим ID книги");
        List<BookWithId> getAllBooks = JSONUtils.saveJSONToDataModel(RESTUtils.getBooks().getBody(), BookWithId.class);
        int bookId = getAllBooks.isEmpty() ? 1 : getAllBooks.get(getAllBooks.size() - 1).getId() + 1;
        var deleteBookResponse = RESTUtils.deleteBook(bookId);
        Assert.assertEquals(deleteBookResponse.getStatus(), HttpStatus.SC_NOT_FOUND, "Код статуса отличается от ожидаемого!");
        Assert.assertTrue(JSONUtils.validateJSONSchema(Filenames.errorResponseSchemaFileName,
                deleteBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
    }
}

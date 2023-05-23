package test_classes.positive_cases;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import test_classes.BasicTest;
import test_task.CfgKeys;
import test_task.Filenames;
import test_task.data_models.BookOnlyName;
import test_task.data_models.Book;
import test_task.data_models.BookWithId;
import test_task.utils.JSONUtils;
import test_task.utils.LogUtils;
import test_task.utils.RESTUtils;

import java.util.List;
import java.util.Map;

public class AddBookWithoutOptionalFieldsTestCase extends BasicTest {
    @Test
    @SuppressWarnings("unchecked")
    public void postBookWithoutOptionalFieldsTest() {
        LogUtils.logInfo("Тестирование добавления записи о книге без опциональных полей");
        BookOnlyName bookToPost = new BookOnlyName((Map<String, Object>) JSONUtils.getValueByJSONPath(testDataFile,
                CfgKeys.BOOK_TO_POST_WO_OPT_FIELDS));
        var postBookResponse = RESTUtils.postBook(bookToPost, RESTUtils.getHeaderJson());
        Assert.assertEquals(postBookResponse.getStatus(), HttpStatus.SC_CREATED, "Код статуса отличается от ожидаемого!");
        Assert.assertTrue(JSONUtils.validateJSONSchema(Filenames.bookResponseSchemaFileName,
                postBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        List<BookWithId> booksListPost = JSONUtils.saveJSONToDataModel(postBookResponse.getBody(), BookWithId.class);
        Assert.assertEquals(booksListPost.size(), 1, "Формат ответа на запрос неверен!");
        BookWithId bookFromPostResponse = booksListPost.get(0);
        Assert.assertEquals(bookFromPostResponse, new Book(bookToPost),"Данные из ответа на запрос не совпадают с данными запроса!");
        LogUtils.logInfo("Получение данных о книге по ID");
        int bookId = bookFromPostResponse.getId();
        var getBookResponse = RESTUtils.getBook(bookId);
        Assert.assertEquals(getBookResponse.getStatus(), HttpStatus.SC_OK, "Код статуса отличается от ожидаемого!");
        Assert.assertTrue(JSONUtils.validateJSONSchema(Filenames.bookResponseSchemaFileName,
                getBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        List<Book> booksListGet = JSONUtils.saveJSONToDataModel(getBookResponse.getBody(), Book.class);
        Assert.assertEquals(booksListGet.size(), 1, "Формат ответа на запрос неверен!");
        Book bookFromGetResponse = booksListPost.get(0);
        Assert.assertEquals(bookFromGetResponse, new Book(bookToPost), "Данные, полученные методом GET, не совпадают с данными, отправленными методом POST!");
    }
}

package test_classes.positive_cases;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import test_classes.BasicTest;
import test_task.Filenames;
import test_task.CfgKeys;
import test_task.data_models.Book;
import test_task.data_models.BookWithId;
import test_task.utils.JSONUtils;
import test_task.utils.LogUtils;
import test_task.utils.RESTUtils;

import java.util.List;
import java.util.Map;

public class HappyPathTestCase extends BasicTest {
    private int bookId = 0;

    @Test
    @SuppressWarnings("unchecked")
    public void postBookTest() {
        LogUtils.logInfo("Тестирование добавления записи о книге");
        Book bookToPost = new Book((Map<String, Object>) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.BOOK_TO_POST));
        var postBookResponse = RESTUtils.postBook(bookToPost, RESTUtils.getHeaderJson());
        Assert.assertEquals(postBookResponse.getStatus(), HttpStatus.SC_CREATED, "Код статуса отличается от ожидаемого!");
        Assert.assertTrue(JSONUtils.validateJSONSchema(Filenames.bookResponseSchemaFileName,
                postBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        List<BookWithId> booksListPost = JSONUtils.saveJSONToDataModel(postBookResponse.getBody(), BookWithId.class);
        Assert.assertEquals(booksListPost.size(), 1, "Формат ответа на запрос неверен!");
        BookWithId bookFromPostResponse = booksListPost.get(0);
        Assert.assertEquals(bookToPost, bookFromPostResponse, "Данные из ответа на запрос не совпадают с данными запроса!");
        LogUtils.logInfo("Получение данных о книге по ID");
        bookId = bookFromPostResponse.getId();
        var getBookResponse = RESTUtils.getBook(bookId);
        Assert.assertEquals(getBookResponse.getStatus(), HttpStatus.SC_OK, "Код статуса отличается от ожидаемого!");
        Assert.assertTrue(JSONUtils.validateJSONSchema(Filenames.bookResponseSchemaFileName,
                getBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        List<Book> booksListGet = JSONUtils.saveJSONToDataModel(getBookResponse.getBody(), Book.class);
        Assert.assertEquals(booksListGet.size(), 1, "Формат ответа на запрос неверен!");
        Book bookFromGetResponse = booksListPost.get(0);
        Assert.assertEquals(bookFromPostResponse, bookFromGetResponse, "Данные, полученные методом GET, не совпадают с данными, отправленными методом POST!");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void putBookTest() {
        LogUtils.logInfo("Тестирование обновления информации о книге");
        Book bookToPut = new Book((Map<String, Object>) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.BOOK_TO_PUT));
        var putBookResponse = RESTUtils.putBook(bookId, bookToPut, RESTUtils.getHeaderJson());
        Assert.assertEquals(putBookResponse.getStatus(), HttpStatus.SC_OK, "Код статуса отличается от ожидаемого!");
        Assert.assertTrue(JSONUtils.validateJSONSchema(Filenames.bookResponseSchemaFileName,
                putBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        List<BookWithId> booksListPut = JSONUtils.saveJSONToDataModel(putBookResponse.getBody(), BookWithId.class);
        Assert.assertEquals(booksListPut.size(), 1, "Формат ответа на запрос неверен!");
        Book bookFromPutResponse = booksListPut.get(0);
        Assert.assertEquals(bookToPut, bookFromPutResponse, "Данные из ответа на запрос не совпадают с данными запроса!");
        LogUtils.logInfo("Получение данных о книге по ID");
        var getBookResponse = RESTUtils.getBook(bookId);
        Assert.assertEquals(getBookResponse.getStatus(), HttpStatus.SC_OK, "Код статуса отличается от ожидаемого!");
        Assert.assertTrue(JSONUtils.validateJSONSchema(Filenames.bookResponseSchemaFileName,
                getBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        List<Book> booksListGet = JSONUtils.saveJSONToDataModel(getBookResponse.getBody(), Book.class);
        Assert.assertEquals(booksListGet.size(), 1, "Формат ответа на запрос неверен!");
        Book bookFromGetResponse = booksListPut.get(0);
        Assert.assertEquals(bookFromPutResponse, bookFromGetResponse, "Данные, полученные методом GET, не совпадают с данными, отправленными методом PUT!");
    }

    @Test
    public void deleteBookTest() {
        LogUtils.logInfo("Тестирование удаления записи о книге по ID");
        var deleteBookResponse = RESTUtils.deleteBook(bookId);
        Assert.assertEquals(deleteBookResponse.getStatus(), HttpStatus.SC_OK, "Код статуса отличается от ожидаемого!");
        Assert.assertTrue(JSONUtils.validateJSONSchema(Filenames.deleteResponseSchemaFileName,
                deleteBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        LogUtils.logInfo("Проверка удаления записи");
        var getBookResponse = RESTUtils.getBook(bookId);
        Assert.assertEquals(getBookResponse.getStatus(), HttpStatus.SC_NOT_FOUND, "Код статуса отличается от ожидаемого!");
        Assert.assertTrue(JSONUtils.validateJSONSchema(Filenames.errorResponseSchemaFileName,
                getBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
    }
}

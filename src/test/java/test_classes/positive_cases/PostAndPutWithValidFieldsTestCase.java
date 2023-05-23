package test_classes.positive_cases;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import test_classes.BasicTest;
import test_task.Filenames;
import test_task.data_models.Book;
import test_task.data_models.BookWithId;
import test_task.utils.JSONUtils;
import test_task.utils.LogUtils;
import test_task.utils.RESTUtils;
import test_task.utils.RandomUtils;

import java.util.HashMap;
import java.util.List;

public class PostAndPutWithValidFieldsTestCase extends BasicTest {
    @Test
    public void postWithValidFieldsTest() {
        LogUtils.logInfo("Тестирование POST запроса с валидными значениями полей");
        HashMap<String, Object> bookMap = new HashMap<>();
        bookMap.put("name", RandomUtils.getRandomString());
        bookMap.put("author", RandomUtils.getRandomString());
        bookMap.put("year", ((Integer) RandomUtils.getRandomInt()).doubleValue());
        bookMap.put("isElectronicBook", RandomUtils.getRandomBoolean());
        Book bookToPost = new Book(bookMap);
        var postBookResponse = RESTUtils.postBook(bookToPost, RESTUtils.getHeaderJson());
        Assert.assertEquals(postBookResponse.getStatus(), HttpStatus.SC_CREATED, "Код статуса отличается от ожидаемого!");
        Assert.assertTrue(JSONUtils.validateJSONSchema(Filenames.bookResponseSchemaFileName,
                postBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        List<BookWithId> booksListPost = JSONUtils.saveJSONToDataModel(postBookResponse.getBody(), BookWithId.class);
        Assert.assertEquals(booksListPost.size(), 1, "Формат ответа на запрос неверен!");
        BookWithId bookFromPostResponse = booksListPost.get(0);
        Assert.assertEquals(bookToPost, bookFromPostResponse, "Данные из ответа на запрос не совпадают с данными запроса!");
        }

    @Test
    public void putWithValidFieldsTest() {
        LogUtils.logInfo("Тестирование PUT запроса с валидными значениями полей");
        List<BookWithId> getAllBooks = JSONUtils.saveJSONToDataModel(RESTUtils.getBooks().getBody(), BookWithId.class);
        BookWithId bookFromGet = RandomUtils.getRandomElement(getAllBooks);
        int bookId = bookFromGet.getId();
        HashMap<String, Object> bookMap = new HashMap<>();
        bookMap.put("name", RandomUtils.getRandomString());
        bookMap.put("author", RandomUtils.getRandomString());
        bookMap.put("year", ((Integer) RandomUtils.getRandomInt()).doubleValue());
        bookMap.put("isElectronicBook", RandomUtils.getRandomBoolean());
        Book bookToPut = new Book(bookMap);
        var putBookResponse = RESTUtils.putBook(bookId,bookToPut, RESTUtils.getHeaderJson());
        Assert.assertEquals(putBookResponse.getStatus(), HttpStatus.SC_OK, "Код статуса отличается от ожидаемого!");
        Assert.assertTrue(JSONUtils.validateJSONSchema(Filenames.bookResponseSchemaFileName,
                putBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        List<BookWithId> booksListPut = JSONUtils.saveJSONToDataModel(putBookResponse.getBody(), BookWithId.class);
        Assert.assertEquals(booksListPut.size(), 1, "Формат ответа на запрос неверен!");
        Book bookFromPutResponse = booksListPut.get(0);
        Assert.assertEquals(bookToPut, bookFromPutResponse, "Данные из ответа на запрос не совпадают с данными запроса!");
        }
    }

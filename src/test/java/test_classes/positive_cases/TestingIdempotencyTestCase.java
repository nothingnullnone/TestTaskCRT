package test_classes.positive_cases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import test_classes.BasicTest;
import test_task.CfgKeys;
import test_task.data_models.BookWithId;
import test_task.utils.JSONUtils;
import test_task.utils.LogUtils;
import test_task.utils.RESTUtils;
import test_task.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class TestingIdempotencyTestCase extends BasicTest {

    private List<BookWithId> getAllInitial = new ArrayList<>();

    @BeforeClass
    public void setUp(){
        getAllInitial = JSONUtils.saveJSONToDataModel(RESTUtils.getBooks().getBody(), BookWithId.class);
    }
    @Test
    public void idempotencyGetAllTest() {
        LogUtils.logInfo("Тестирование идемпотентности GET запроса списка всех книг");
        SoftAssert softAssertGetAll = new SoftAssert();
        List<List<BookWithId>> booksListsGetAll = new ArrayList<>();
        int repeats = ((Double) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.REPEATS_IDEMPOTENCY)).intValue();
        for (int i = 0; i < repeats; i++) {
            booksListsGetAll.add(JSONUtils.saveJSONToDataModel(RESTUtils.getBooks().getBody(), BookWithId.class));
        }
        for (int i = 0; i < booksListsGetAll.size(); i++) {
            var bookList = booksListsGetAll.get(i);
            for (List<BookWithId> otherbookList : booksListsGetAll) {
                softAssertGetAll.assertEquals(bookList, otherbookList, "Метод не идемпотентен!");
            }
        }
        softAssertGetAll.assertAll();
    }

    @Test
    public void idempotencyGetTest() {
        LogUtils.logInfo("Тестирование идемпотентности GET запроса книги по id");
        int repeats = ((Double) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.REPEATS_IDEMPOTENCY)).intValue();
        BookWithId bookToGet = RandomUtils.getRandomElement(getAllInitial);
        int bookId = bookToGet.getId();
        List<BookWithId> getAllBeforeGet = JSONUtils.saveJSONToDataModel(RESTUtils.getBooks().getBody(), BookWithId.class);
        for (int i = 0; i < repeats; i++) {
            RESTUtils.getBook(bookId);
        }
        List<BookWithId> getAllAfterGet = JSONUtils.saveJSONToDataModel(RESTUtils.getBooks().getBody(), BookWithId.class);
        Assert.assertEquals(getAllBeforeGet, getAllAfterGet, "Метод не идемпотентен!");
    }

    @Test
    public void idempotencyPutTest() {
        LogUtils.logInfo("Тестирование идемпотентности PUT запросов");
        int repeats = ((Double) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.REPEATS_IDEMPOTENCY)).intValue();
        BookWithId bookToPut = RandomUtils.getRandomElement(getAllInitial);
        int bookId = bookToPut.getId();
        RESTUtils.putBook(bookId, bookToPut, RESTUtils.getHeaderJson());
        List<BookWithId> getAllBeforePuts = JSONUtils.saveJSONToDataModel(RESTUtils.getBooks().getBody(), BookWithId.class);
        for (int i = 0; i < repeats; i++) {
            RESTUtils.putBook(bookId, bookToPut, RESTUtils.getHeaderJson());
        }
        List<BookWithId> getAllAfterPut = JSONUtils.saveJSONToDataModel(RESTUtils.getBooks().getBody(), BookWithId.class);
        Assert.assertEquals(getAllBeforePuts, getAllAfterPut, "Метод не идемпотентен!");
    }

    @Test
    public void idempotencyDeleteTest() {
        LogUtils.logInfo("Тестирование идемпотентности DELETE запросов");
        int repeats = ((Double) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.REPEATS_IDEMPOTENCY)).intValue();
        BookWithId bookToDelete = RandomUtils.getRandomElement(getAllInitial);
        int bookId = bookToDelete.getId();
        RESTUtils.deleteBook(bookId);
        List<BookWithId> getAllBeforePuts = JSONUtils.saveJSONToDataModel(RESTUtils.getBooks().getBody(), BookWithId.class);
        for (int i = 0; i < repeats; i++) {
            RESTUtils.deleteBook(bookId);
        }
        List<BookWithId> getAllAfterPut = JSONUtils.saveJSONToDataModel(RESTUtils.getBooks().getBody(), BookWithId.class);
        Assert.assertEquals(getAllBeforePuts, getAllAfterPut, "Метод не идемпотентен!");
    }
}

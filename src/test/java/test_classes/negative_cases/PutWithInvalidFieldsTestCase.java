package test_classes.negative_cases;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import test_classes.BasicTest;
import test_task.Filenames;
import test_task.data_models.BookNoDatatypeCheck;
import test_task.data_models.BookWithId;
import test_task.utils.JSONUtils;
import test_task.utils.LogUtils;
import test_task.utils.RESTUtils;
import test_task.utils.RandomUtils;

import java.util.List;

public class PutWithInvalidFieldsTestCase extends BasicTest {
    private static BookWithId bookToPut;
    private static int bookId = 0;

    @BeforeClass
    public static void setUp() {
        List<BookWithId> getAllBooks = JSONUtils.saveJSONToDataModel(RESTUtils.getBooks().getBody(), BookWithId.class);
        bookToPut = RandomUtils.getRandomElement(getAllBooks);
        bookId = bookToPut.getId();
    }
    @Test
    public void putWithEmptyBodyTest() {
        LogUtils.logInfo("Тестирование PUT запроса с пустым телом");
        var putBookResponse = RESTUtils.putEmptyBody(bookId, RESTUtils.getHeaderJson());
        Assert.assertEquals(putBookResponse.getStatus(), HttpStatus.SC_BAD_REQUEST, "Код статуса отличается от ожидаемого!");
    }
    @Test
    public void putWithInvalidFieldNameTest() {
        LogUtils.logInfo("Тестирование PUT запроса с невалидными значениями поля name");
        SoftAssert softAssert = new SoftAssert();
        BookNoDatatypeCheck bookWithInvalidName = new BookNoDatatypeCheck(bookToPut);
        Object[] invalidValues = new Object[] {RandomUtils.getRandomInt(),
                RandomUtils.getRandomBoolean(),
                RandomUtils.getRandomList(),
                RandomUtils.getRandomObject(),
                null};
        for (Object value : invalidValues) {
            bookWithInvalidName.setName(value);
            var putBookResponse = RESTUtils.putBook(bookId,bookWithInvalidName, RESTUtils.getHeaderJson());
            softAssert.assertEquals(putBookResponse.getStatus(), HttpStatus.SC_BAD_REQUEST, "Код статуса отличается от ожидаемого!");
            softAssert.assertTrue(JSONUtils.validateJSONSchema(Filenames.errorResponseSchemaFileName,
                    putBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        }
        softAssert.assertAll();
    }

    @Test
    public void putWithInvalidFieldAuthorTest() {
        LogUtils.logInfo("Тестирование PUT запроса с невалидными значениями поля author");
        SoftAssert softAssert = new SoftAssert();
        BookNoDatatypeCheck bookWithInvalidAuthor = new BookNoDatatypeCheck(bookToPut);
        Object[] invalidValues = new Object[] {RandomUtils.getRandomInt(),
                RandomUtils.getRandomBoolean(),
                RandomUtils.getRandomList(),
                RandomUtils.getRandomObject(),
                null};
        for (Object value : invalidValues) {
            bookWithInvalidAuthor.setAuthor(value);
            var putBookResponse = RESTUtils.putBook(bookId,bookWithInvalidAuthor, RESTUtils.getHeaderJson());
            softAssert.assertEquals(putBookResponse.getStatus(), HttpStatus.SC_BAD_REQUEST, "Код статуса отличается от ожидаемого!");
            softAssert.assertTrue(JSONUtils.validateJSONSchema(Filenames.errorResponseSchemaFileName,
                    putBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        }
        softAssert.assertAll();
    }

    @Test
    public void putWithInvalidFieldYearTest() {
        LogUtils.logInfo("Тестирование PUT запроса с невалидными значениями поля year");
        SoftAssert softAssert = new SoftAssert();
        BookNoDatatypeCheck bookWithInvalidYear = new BookNoDatatypeCheck(bookToPut);
        Object[] invalidValues = new Object[] {RandomUtils.getRandomBoolean(),
                RandomUtils.getRandomString(),
                RandomUtils.getRandomList(),
                RandomUtils.getRandomObject(),
                null};
        for (Object value : invalidValues) {
            bookWithInvalidYear.setYear(value);
            var postBookResponse = RESTUtils.putBook(bookId, bookWithInvalidYear, RESTUtils.getHeaderJson());
            softAssert.assertEquals(postBookResponse.getStatus(), HttpStatus.SC_BAD_REQUEST, "Код статуса отличается от ожидаемого!");
            softAssert.assertTrue(JSONUtils.validateJSONSchema(Filenames.errorResponseSchemaFileName,
                    postBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        }
        softAssert.assertAll();
    }

    @Test
    public void putWithInvalidFieldIsElectronicBookTest() {
        LogUtils.logInfo("Тестирование PUT запроса с невалидными значениями поля isElectronicBook");
        SoftAssert softAssert = new SoftAssert();
        BookNoDatatypeCheck bookWithInvalidIsElectronicBook = new BookNoDatatypeCheck(bookToPut);
        Object[] invalidValues = new Object[] {RandomUtils.getRandomInt(),
                RandomUtils.getRandomString(),
                RandomUtils.getRandomList(),
                RandomUtils.getRandomObject(),
                null};
        for (Object value : invalidValues) {
            bookWithInvalidIsElectronicBook.setIsElectronicBook(value);
            var putBookResponse = RESTUtils.putBook(bookId, bookWithInvalidIsElectronicBook, RESTUtils.getHeaderJson());
            softAssert.assertEquals(putBookResponse.getStatus(), HttpStatus.SC_BAD_REQUEST, "Код статуса отличается от ожидаемого!");
            softAssert.assertTrue(JSONUtils.validateJSONSchema(Filenames.errorResponseSchemaFileName,
                    putBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        }
        softAssert.assertAll();
    }
}

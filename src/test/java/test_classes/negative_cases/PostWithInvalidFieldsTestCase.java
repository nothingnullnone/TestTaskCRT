package test_classes.negative_cases;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import test_classes.BasicTest;
import test_task.CfgKeys;
import test_task.Filenames;
import test_task.data_models.Book;
import test_task.data_models.BookNoDatatypeCheck;
import test_task.utils.JSONUtils;
import test_task.utils.LogUtils;
import test_task.utils.RESTUtils;
import test_task.utils.RandomUtils;

import java.util.Map;

public class PostWithInvalidFieldsTestCase extends BasicTest {
    @Test
    public void postWithEmptyBodyTest() {
        LogUtils.logInfo("Тестирование POST запроса с пустым телом");
        var postBookResponse = RESTUtils.postEmptyBody(RESTUtils.getHeaderJson());
        Assert.assertEquals(postBookResponse.getStatus(), HttpStatus.SC_BAD_REQUEST, "Код статуса отличается от ожидаемого!");
    }
    @Test
    @SuppressWarnings("unchecked")
    public void postWithInvalidFieldNameTest() {
        LogUtils.logInfo("Тестирование POST запроса с невалидными значениями поля name");
        SoftAssert softAssert = new SoftAssert();
        Book bookToPost = new Book((Map<String, Object>) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.BOOK_TO_POST));
        BookNoDatatypeCheck bookWithInvalidName = new BookNoDatatypeCheck(bookToPost);
        Object[] invalidValues = new Object[] {RandomUtils.getRandomInt(),
                RandomUtils.getRandomBoolean(),
                RandomUtils.getRandomList(),
                RandomUtils.getRandomObject(),
                null};
        for (Object value : invalidValues) {
            bookWithInvalidName.setName(value);
            var postBookResponse = RESTUtils.postBook(bookWithInvalidName, RESTUtils.getHeaderJson());
            softAssert.assertEquals(postBookResponse.getStatus(), HttpStatus.SC_BAD_REQUEST, "Код статуса отличается от ожидаемого!");
            softAssert.assertTrue(JSONUtils.validateJSONSchema(Filenames.errorResponseSchemaFileName,
                    postBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        }
        softAssert.assertAll();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void postWithInvalidFieldAuthorTest() {
        LogUtils.logInfo("Тестирование POST запроса с невалидными значениями поля author");
        SoftAssert softAssert = new SoftAssert();
        Book bookToPost = new Book((Map<String, Object>) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.BOOK_TO_POST));
        BookNoDatatypeCheck bookWithInvalidName = new BookNoDatatypeCheck(bookToPost);
        Object[] invalidValues = new Object[] {RandomUtils.getRandomInt(),
                RandomUtils.getRandomBoolean(),
                RandomUtils.getRandomList(),
                RandomUtils.getRandomObject(),
                null};
        for (Object value : invalidValues) {
            bookWithInvalidName.setAuthor(value);
            var postBookResponse = RESTUtils.postBook(bookWithInvalidName, RESTUtils.getHeaderJson());
            softAssert.assertEquals(postBookResponse.getStatus(), HttpStatus.SC_BAD_REQUEST, "Код статуса отличается от ожидаемого!");
            softAssert.assertTrue(JSONUtils.validateJSONSchema(Filenames.errorResponseSchemaFileName,
                    postBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        }
        softAssert.assertAll();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void postWithInvalidFieldYearTest() {
        LogUtils.logInfo("Тестирование POST запроса с невалидными значениями поля year");
        SoftAssert softAssert = new SoftAssert();
        Book bookToPost = new Book((Map<String, Object>) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.BOOK_TO_POST));
        BookNoDatatypeCheck bookWithInvalidName = new BookNoDatatypeCheck(bookToPost);
        Object[] invalidValues = new Object[] {RandomUtils.getRandomBoolean(),
                RandomUtils.getRandomString(),
                RandomUtils.getRandomList(),
                RandomUtils.getRandomObject(),
                null};
        for (Object value : invalidValues) {
            bookWithInvalidName.setYear(value);
            var postBookResponse = RESTUtils.postBook(bookWithInvalidName, RESTUtils.getHeaderJson());
            softAssert.assertEquals(postBookResponse.getStatus(), HttpStatus.SC_BAD_REQUEST, "Код статуса отличается от ожидаемого!");
            softAssert.assertTrue(JSONUtils.validateJSONSchema(Filenames.errorResponseSchemaFileName,
                    postBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        }
        softAssert.assertAll();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void postWithInvalidFieldIsElectronicBookTest() {
        LogUtils.logInfo("Тестирование POST запроса с невалидными значениями поля isElectronicBook");
        SoftAssert softAssert = new SoftAssert();
        Book bookToPost = new Book((Map<String, Object>) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.BOOK_TO_POST));
        BookNoDatatypeCheck bookWithInvalidName = new BookNoDatatypeCheck(bookToPost);
        Object[] invalidValues = new Object[] {RandomUtils.getRandomInt(),
                RandomUtils.getRandomString(),
                RandomUtils.getRandomList(),
                RandomUtils.getRandomObject(),
                null};
        for (Object value : invalidValues) {
            bookWithInvalidName.setIsElectronicBook(value);
            var postBookResponse = RESTUtils.postBook(bookWithInvalidName, RESTUtils.getHeaderJson());
            softAssert.assertEquals(postBookResponse.getStatus(), HttpStatus.SC_BAD_REQUEST, "Код статуса отличается от ожидаемого!");
            softAssert.assertTrue(JSONUtils.validateJSONSchema(Filenames.errorResponseSchemaFileName,
                    postBookResponse.getBody().toString()), "Схема ответа отличается от ожидаемой!");
        }
        softAssert.assertAll();
    }
}

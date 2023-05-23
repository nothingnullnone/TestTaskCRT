package test_classes.negative_cases;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import test_classes.BasicTest;
import test_task.CfgKeys;
import test_task.data_models.Book;
import test_task.utils.JSONUtils;
import test_task.utils.LogUtils;
import test_task.utils.RESTUtils;

import java.util.Map;

public class WrongHeaderTestCase extends BasicTest {
    @Test
    @SuppressWarnings("unchecked")
    public void postWithWrongHeaderTest() {
        LogUtils.logInfo("Тестирование POST запроса с неправильным заголовком Content-Type");
        Book bookToPost = new Book((Map<String, Object>) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.BOOK_TO_POST));
        var postBookResponse = RESTUtils.postBook(bookToPost, RESTUtils.getHeaderXml());
        Assert.assertEquals(postBookResponse.getStatus(), HttpStatus.SC_BAD_REQUEST, "Код статуса отличается от ожидаемого!");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void postWithoutHeaderTest() {
        LogUtils.logInfo("Тестирование POST запроса без заголовка Content-Type");
        Book bookToPost = new Book((Map<String, Object>) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.BOOK_TO_POST));
        var postBookResponse = RESTUtils.postBook(bookToPost);
        Assert.assertEquals(postBookResponse.getStatus(), HttpStatus.SC_BAD_REQUEST, "Код статуса отличается от ожидаемого!");
    }
}

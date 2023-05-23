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

public class WrongMethodTestCase extends BasicTest {
    @Test
    @SuppressWarnings("unchecked")
    public void nonAllowedMethodPatchBookTest() {
        LogUtils.logInfo("Тестирование не определённого в документации PATCH запроса");
        Book bookToPatch = new Book((Map<String, Object>) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.BOOK_TO_POST));
        var patchBookResponse = RESTUtils.patchBook(1,bookToPatch,RESTUtils.getHeaderJson());
        Assert.assertEquals(patchBookResponse.getStatus(), HttpStatus.SC_METHOD_NOT_ALLOWED, "Код статуса отличается от ожидаемого!");
    }
}

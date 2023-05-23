package test_classes;

import org.testng.annotations.AfterSuite;
import test_task.CfgKeys;
import test_task.Filenames;
import test_task.data_models.Book;
import test_task.data_models.BookWithIdNoDatatypeCheck;
import test_task.utils.JSONUtils;
import test_task.utils.RESTUtils;

import java.util.List;
import java.util.Map;

public class BasicTest {
    protected static final String testDataFile = JSONUtils.getJSONFileAsString(Filenames.testDataFile);

    @AfterSuite
    @SuppressWarnings("unchecked")
    public static void tearDown() {
        List<BookWithIdNoDatatypeCheck> booksListGetAll = JSONUtils.saveJSONToDataModel(RESTUtils.getBooks().getBody(), BookWithIdNoDatatypeCheck.class);
        List<Map<String, Object>> initBooksList = (List<Map<String, Object>>) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.INIT_BOOKS_LIST);
        int initCount = initBooksList.size();
        for (int i = initCount; i < booksListGetAll.size(); i++) {
            RESTUtils.deleteBook(((Double) booksListGetAll.get(i).getId()).intValue());
        }
        if (booksListGetAll.size() < initCount) {
            for (int i = booksListGetAll.size(); i < initCount; i++) {
                Book bookToPost = new Book(initBooksList.get(i));
                RESTUtils.postBook(bookToPost, RESTUtils.getHeaderJson());
            }
        }
        if (booksListGetAll.size() >= initCount) {
            for (int i = 0; i < initCount; i++) {
                Book bookToPut = new Book(initBooksList.get(i));
                RESTUtils.putBook(((Double) booksListGetAll.get(i).getId()).intValue(),bookToPut, RESTUtils.getHeaderJson());
            }
        }
    }
}

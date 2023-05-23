package test_task.utils;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import test_task.Filenames;
import test_task.CfgKeys;
import test_task.Constants;
import javafx.util.Pair;


public class RESTUtils {
    private static final String cfgFile = JSONUtils.getJSONFileAsString(Filenames.cfgFile);

    public static Pair<String, String> getHeaderJson() {
        return new Pair<>("Content-Type", "application/json");
    }

    public static Pair<String, String> getHeaderXml() {
        return new Pair<>("Content-Type", "application/xml");
    }

    public static HttpResponse<JsonNode> getBooks() {
        String url = (String) JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BASE_URL) +
                JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BOOKS_ENDPOINT);
        LogUtils.logInfo("Выполнение GET запроса к " + url);
        return Unirest.get(url).asJson();
    }

    public static HttpResponse<JsonNode> getBook(int id) {
        String url = (String) JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BASE_URL) +
                JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BOOKS_ENDPOINT) + Constants.JSON_PATH_DELIMITER + id;
        LogUtils.logInfo("Выполнение GET запроса к " + url);
        return Unirest.get(url).asJson();
    }

    public static HttpResponse<JsonNode> postBook(Object body, Pair<String, String> header) {
        String url = (String) JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BASE_URL) +
                JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BOOKS_ENDPOINT);
        LogUtils.logInfo("Выполнение POST запроса к " + url + " с телом " + body.toString());
        return Unirest.post(url).header(header.getKey(), header.getValue()).body(body).asJson();
    }

    public static HttpResponse<JsonNode> postBook(Object body) {
        String url = (String) JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BASE_URL) +
                JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BOOKS_ENDPOINT);
        LogUtils.logInfo("Выполнение POST запроса к " + url + " с телом " + body.toString());
        return Unirest.post(url).body(body).asJson();
    }

    public static HttpResponse<JsonNode> postEmptyBody(Pair<String, String> header) {
        String url = (String) JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BASE_URL) +
                JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BOOKS_ENDPOINT);
        LogUtils.logInfo("Выполнение POST запроса к " + url);
        return Unirest.post(url).header(header.getKey(), header.getValue()).asJson();
    }

    public static HttpResponse<JsonNode> putBook(int id, Object body, Pair<String, String> header) {
        String url = (String) JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BASE_URL) +
                JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BOOKS_ENDPOINT) + Constants.JSON_PATH_DELIMITER + id;
        LogUtils.logInfo("Выполнение PUT запроса к " + url + " с телом " + body.toString());
        return Unirest.put(url).header(header.getKey(), header.getValue()).body(body).asJson();
    }

    public static HttpResponse<JsonNode> putEmptyBody(int id, Pair<String, String> header) {
        String url = (String) JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BASE_URL) +
                JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BOOKS_ENDPOINT) + Constants.JSON_PATH_DELIMITER + id;
        LogUtils.logInfo("Выполнение PUT запроса к " + url);
        return Unirest.put(url).header(header.getKey(), header.getValue()).asJson();
    }

    public static HttpResponse<JsonNode> patchBook(int id, Object body, Pair<String, String> header) {
        String url = (String) JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BASE_URL) +
                JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BOOKS_ENDPOINT) + Constants.JSON_PATH_DELIMITER + id;
        LogUtils.logInfo("Выполнение PATCH запроса к " + url + " с телом " + body.toString());
        return Unirest.patch(url).header(header.getKey(), header.getValue()).body(body).asJson();
    }

    public static HttpResponse<JsonNode> deleteBook(int id) {
        String url = (String) JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BASE_URL) +
                JSONUtils.getValueByJSONPath(cfgFile,CfgKeys.BOOKS_ENDPOINT) + Constants.JSON_PATH_DELIMITER + id;
        LogUtils.logInfo("Выполнение DELETE запроса к " + url);
        return Unirest.delete(url).asJson();
    }
}

package test_task.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import kong.unirest.JsonNode;
import test_task.CfgKeys;
import test_task.Constants;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class JSONUtils {
    public static String getJSONFileAsString(String filename) {
        try {
            return Files.readString(Path.of(filename));
        }
        catch (Exception e) {
            LogUtils.logError( e);
            throw new RuntimeException();
        }
    }

    private static Object getTypeFromJSON(String jsonString, Class<?> collectionClazz, Class<?>... typeArgs) {
        try {
            Gson gson = new Gson();
            gson.toJson(jsonString);
            Type collectionType = TypeToken.getParameterized(collectionClazz, typeArgs).getType();
            return gson.fromJson(jsonString, collectionType);
        } catch (Exception e) {
            LogUtils.logError(e);
            throw new RuntimeException();
        }
    }

    public static Object getValueByJSONPath(String jsonString, String valuePath) {
        String[] values = valuePath.split(Constants.JSON_PATH_DELIMITER);
        String jsonStringCurrent = jsonString;
        Object result = new Object();
        for (var value : values) {
            Gson gson = new Gson();
            if (result instanceof ArrayList<?>) {
                result = ((ArrayList<?>) getTypeFromJSON(jsonStringCurrent, List.class, Object.class)).get(Integer.parseInt(value));
            } else {
                result = ((HashMap<?, ?>) getTypeFromJSON(jsonStringCurrent, HashMap.class, String.class, Object.class)).get(value);
            }
            jsonStringCurrent = gson.toJson(result);
        }
        return result;
    }

    public static <T> List<T> saveJSONToDataModel(JsonNode jsonNode, Class<T> type) {
        try {
            Gson gson = new Gson();
            Type collectionType = TypeToken.getParameterized(List.class, type).getType();
            return jsonNode.getObject().has(CfgKeys.BOOKS)
                    ? gson.fromJson(jsonNode.getObject().get(CfgKeys.BOOKS).toString(), collectionType)
                    : Collections.singletonList(gson.fromJson(jsonNode.getObject().get(CfgKeys.BOOK).toString(), type));
        } catch (Exception e) {
            LogUtils.logError(e);
        }
        return new ArrayList<>();
    }

    public static boolean validateJSONSchema(String schemaFileName, String responseBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
            JsonSchema jsonSchema = factory.getSchema(new DataInputStream(new FileInputStream(schemaFileName)));
            com.fasterxml.jackson.databind.JsonNode jsonNode = mapper.readTree(responseBody);
            Set<ValidationMessage> errors = jsonSchema.validate(jsonNode);
            return errors.isEmpty();
        }
        catch (Exception e) {
            LogUtils.logError(e);
        }
        return false;
    }
}

package test_task.utils;

import test_task.CfgKeys;
import test_task.Filenames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandomUtils {
    private static final String testDataFile = JSONUtils.getJSONFileAsString(Filenames.testDataFile);
    private static final int minLen = ((Double) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.RANDOM_STR_MIN_LEN)).intValue();
    private static final int maxLen = ((Double) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.RANDOM_STR_MAX_LEN)).intValue();
    private static final int minInt = ((Double) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.RANDOM_INT_MIN)).intValue();
    private static final int maxInt = ((Double) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.RANDOM_INT_MAX)).intValue();
    private static final char firstChar = ((String) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.FIRST_POSSIBLE_CHAR)).charAt(0);
    private static final char lastChar = ((String) JSONUtils.getValueByJSONPath(testDataFile, CfgKeys.LAST_POSSIBLE_CHAR)).charAt(0);

    public static String getRandomString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < getRandomIntRange(minLen, maxLen); i++) {
            int randomCode = getRandomIntRange(firstChar, lastChar);
            result.append((char) randomCode);
        }
        return result.toString();
    }

    public static int getRandomIntRange(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static int getRandomInt() {
        return  getRandomIntRange(minInt, maxInt);
    }

    public static <T> T getRandomElement(List<T> list) {
        return list.get(getRandomIntRange(0, list.size() - 1));
    }

    public static boolean getRandomBoolean() {
        return getRandomIntRange(minInt, maxInt) > (maxInt / 2);
    }

    public static List<String> getRandomList() {
        List<String> resultList = new ArrayList<>();
        resultList.add(getRandomString());
        return resultList;
    }

    public static HashMap<String, String> getRandomObject() {
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put(getRandomString(), getRandomString());
        return resultMap;
    }

}

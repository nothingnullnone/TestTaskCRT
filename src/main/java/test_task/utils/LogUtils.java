package test_task.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {
    private static final Logger logger = LogManager.getLogger(LogUtils.class);

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logError(Throwable e) {
        logger.error(e.getMessage(), e);
    }

}

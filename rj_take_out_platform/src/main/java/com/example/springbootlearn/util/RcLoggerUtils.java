package com.example.springbootlearn.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * checkList1.0
 * <p>
 * record 正常日志
 * systemError 系统异常
 *
 * @author 王海虹
 */
public class RcLoggerUtils {

    private static final Logger LOG = LogManager.getLogger(RcLoggerUtils.class);

    private static final String FULL_info_LOG = "startTime: {},\n requestUrl: {},\n method: {},\n headers:{},\n req:{},\n resp:{}";

    private static final String FULL_error_LOG = "startTime: {},\n requestUrl:{},\n headers:{},\n req:{},\n error:{}";

    private static final String FULL_system_error_LOG = "startTime: {},\n serviceName:{},\n methodName:{},\n requestBody:{},\n errorMessage:{}";

    /**
     * INFO 记录checkList日志
     *
     * @param: requestUrl: 请求url， headers：请求头，
     * requestBody：请求入参， responseBody：请求出参
     * @return:
     * @date: 2022/7/15
     * @toDo:
     */
    public static void recordReq(Logger logger, String method, long startTime,
                                 String requestUrl, Object headers,
                                 Object requestBody, Object responseBody) {
        String timeString = DateUtil.format(new Date(startTime), DateUtil.FORMAT_ALL);
        logger.info(FULL_info_LOG, timeString, requestUrl, method, headers, requestBody, responseBody);
    }

    public static void recordError(Logger logger, long startTime, String requestUrl, String headers, Object requestBody, Exception exception) {
        String timeString = DateUtil.format(new Date(startTime), DateUtil.FORMAT_ALL);
        logger.error(FULL_error_LOG, timeString, requestUrl, headers, requestBody, exception);
    }

    public static void recordSystemError(Logger logger, String methodName, long startTime, Object requestBody, String errorMessage) {
        String timeString = DateUtil.format(new Date(startTime), DateUtil.FORMAT_ALL);
        String serviceName = getServiceName(logger);
        logger.error(FULL_system_error_LOG, timeString, serviceName, methodName, requestBody, errorMessage);
    }

    /**
     * 根据logger获取ServiceName
     *
     * @param logger
     * @return
     */
    private static String getServiceName(Logger logger) {
        String fullServiceName = logger.getName();
        int start = fullServiceName.lastIndexOf(".") + 1;
        int end = fullServiceName.length();
        return StringUtils.substring(fullServiceName, start, end);
    }
}

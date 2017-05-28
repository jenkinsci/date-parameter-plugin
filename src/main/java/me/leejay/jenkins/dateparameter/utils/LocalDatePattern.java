package me.leejay.jenkins.dateparameter.utils;

import me.leejay.jenkins.dateparameter.DateParameterDefinition;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by leejay on 2017. 5. 28..
 */
public class LocalDatePattern {

    private final static org.slf4j.Logger log = LoggerFactory.getLogger(DateParameterDefinition.class);

    private final static Pattern REALTION_PATTERN = Pattern.compile("^LocalDate\\.now\\(\\)(\\.(plus|minus)(Days|Months|Years)\\([0-9]+\\))*;$");

    private final static Pattern METHOD_INT_PARAM_PATTERN = Pattern.compile("^.+\\((?<offset>[0-9]+)\\)$");

    private final static Pattern METHOD_NAME_PATTERN = Pattern.compile("^(?<methodName>.+)\\(.+\\)$");

    public static boolean isValidLocalDateJavaCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return false;
        }

        return REALTION_PATTERN.matcher(code).matches();
    }

    public static boolean isValidLocalDateString(String dateFormat, String stringDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormat);
            LocalDate.parse(stringDate, formatter);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String runJavaStringCode(String code) {
        List<String> codes = Arrays.asList(code.split("\\."));

        LocalDate now = LocalDate.now();
        if (codes.size() == 2) {
            return now.toString();
        }

        for (String methodCode : codes.subList(2, codes.size())) {
            String methodName = parseMethodName(methodCode.replace(";", ""));
            Integer offset = catchIntParameter(methodCode.replace(";", ""));
            now = invokeLocalDateMethod(now, methodName, offset);
        }

        return now.toString();
    }

    static String parseMethodName(String code) {
        Matcher matcher = METHOD_NAME_PATTERN.matcher(code);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Failed to parse method name: " + code);
        }
        return matcher.group("methodName");
    }

    static Integer catchIntParameter(String fullStringMethod) {
        Matcher matcher = METHOD_INT_PARAM_PATTERN.matcher(fullStringMethod);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Failed to find integer argument: " + fullStringMethod);
        }
        return Integer.parseInt(matcher.group("offset"));
    }

    private static LocalDate invokeLocalDateMethod(LocalDate localDate, String methodName, Integer offset) {
        try {
            Method method = localDate.getClass().getDeclaredMethod(methodName, int.class);
            return (LocalDate) method.invoke(localDate, offset);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to invoke method: " + methodName);
        }
    }

}

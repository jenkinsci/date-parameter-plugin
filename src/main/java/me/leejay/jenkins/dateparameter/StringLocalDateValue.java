package me.leejay.jenkins.dateparameter;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JuHyunLee on 2017. 6. 2..
 */
public class StringLocalDateValue implements Serializable {

    private static final long serialVersionUID = 8295455815421939737L;

    private final static String JAVA_PATTERN = "^LocalDate\\.now\\(\\)(\\.(plus|minus)(Days|Months|Years)\\([0-9]+\\))*;?$";

    private final String stringLocalDate;

    private final String stringDateFormat;

    public StringLocalDateValue(String stringLocalDate, String stringDateFormat) {
        this.stringLocalDate = stringLocalDate;
        this.stringDateFormat = stringDateFormat;
    }

    public String getStringLocalDate() {
        return stringLocalDate;
    }

    public String getStringValue() {
        return stringLocalDate;
    }

    public boolean isCompletionFormat() {
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(stringDateFormat);
            return LocalDate.parse(stringLocalDate, formatter) != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isJavaFormat() {
        return stringLocalDate.matches(JAVA_PATTERN);
    }

    public String getStringDateFormat() {
        return stringDateFormat;
    }

    LocalDate parseJava() {
        List<String> codes = Arrays.asList(stringLocalDate.split("\\."));
        if (codes.size() == 2) { // LocalDate.now();
            if (stringLocalDate.equals("LocalDate.now()") || stringLocalDate.equals("LocalDate.now();")) {
                return LocalDate.now();
            }
            return null;
        }

        LocalDate localDate = LocalDate.now();
        for (String code : codes.subList(2, codes.size())) {
            IntegerParamMethod paramMethod = new IntegerParamMethod(code);
            if (paramMethod.getName() == null || paramMethod.getParameter() == null) {
                return null;
            }

            try {
                Method method = localDate.getClass().getMethod(paramMethod.getName(), int.class);
                localDate = (LocalDate) method.invoke(localDate, paramMethod.getParameter());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                return null;
            }
        }

        return localDate;
    }

    String getValue() {
        if (isCompletionFormat()) {
            return stringLocalDate;
        }

        if (isJavaFormat()) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(stringDateFormat);
            return parseJava().toString(formatter);
        }

        return "";
    }

}

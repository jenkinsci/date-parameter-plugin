package me.leejay.jenkins.dateparameter;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by JuHyunLee on 2017. 6. 2..
 */
public class StringLocalDateValueTest {

    @Test
    public void string_return_test() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDate.now();", "yyyyMMdd");
        Assert.assertEquals("LocalDate.now();", value.getStringValue());
    }

    @Test
    public void string_is_completion_format() {
        StringLocalDateValue value = new StringLocalDateValue("20170501", "yyyyMMdd");
        Assert.assertTrue(value.isCompletionFormat());
    }

    @Test
    public void string_is_java_format() {
        StringLocalDateValue value0 = new StringLocalDateValue("LocalDate.now()", "yyyyMMdd");
        StringLocalDateValue value1 = new StringLocalDateValue("LocalDate.now().plusDays(1);", "yyyyMMdd");
        StringLocalDateValue value2 = new StringLocalDateValue("LocalDate.now().plusDays(1);", "yyyyMMdd");
        StringLocalDateValue value3 = new StringLocalDateValue("LocalDate.now().plusMonths(5);", "yyyyMMdd");
        StringLocalDateValue value4 = new StringLocalDateValue("LocalDate.now().minusDays(1).plusMonths(5);", "yyyyMMdd");

        Assert.assertTrue(value0.isJavaFormat());
        Assert.assertTrue(value1.isJavaFormat());
        Assert.assertTrue(value2.isJavaFormat());
        Assert.assertTrue(value3.isJavaFormat());
        Assert.assertTrue(value4.isJavaFormat());
    }

    @Test
    public void string_parse_java() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDate.now()", "yyyyMMdd");
        LocalDate localDate = value.parseJava();
        Assert.assertEquals(LocalDate.now(), localDate);
    }

    @Test
    public void string_parse_java_single_plus_method() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDate.now().plusMonths(1)", "yyyyMMdd");
        LocalDate localDate = value.parseJava();
        Assert.assertEquals(LocalDate.now().plusMonths(1), localDate);
    }

    @Test
    public void string_parse_java_single_minus_method() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDate.now().minusYears(1)", "yyyyMMdd");
        LocalDate localDate = value.parseJava();
        Assert.assertEquals(LocalDate.now().minusYears(1), localDate);
    }

    @Test
    public void string_parse_java_single_minus_method_wit_semicolon() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDate.now().minusYears(1);", "yyyyMMdd");
        LocalDate localDate = value.parseJava();
        Assert.assertEquals(LocalDate.now().minusYears(1), localDate);
    }

    @Test
    public void string_parse_java_multiple_plus_methods() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDate.now().plusDays(10).plusYears(1)", "yyyyMMdd");
        LocalDate localDate = value.parseJava();
        Assert.assertEquals(LocalDate.now().plusDays(10).plusYears(1), localDate);
    }

    @Test
    public void string_parse_java_multiple_minus_methods() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDate.now().minusDays(10).minusYears(1)", "yyyyMMdd");
        LocalDate localDate = value.parseJava();
        Assert.assertEquals(LocalDate.now().minusDays(10).minusYears(1), localDate);
    }
}
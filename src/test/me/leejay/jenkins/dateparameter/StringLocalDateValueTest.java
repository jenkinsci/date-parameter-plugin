package me.leejay.jenkins.dateparameter;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by JuHyunLee on 2017. 6. 2..
 */
public class StringLocalDateValueTest {

    @Test
    public void string_completion_format() {
        StringLocalDateValue value = new StringLocalDateValue("2017-01-01", "yyyy-MM-dd");
        Assert.assertEquals("2017-01-01", value.getValue());
    }

    @Test
    public void string_completion_format2() {
        StringLocalDateValue value = new StringLocalDateValue("20170101", "yyyyMMdd");
        Assert.assertEquals("20170101", value.getValue());
    }

    @Test
    public void string_completion_format_time_fields() {
        StringLocalDateValue value = new StringLocalDateValue("20170101010101", "yyyyMMddHHmmss");
        Assert.assertEquals("20170101010101", value.getValue());
    }

    @Test
    public void string_completion_format_time_fields2() {
        StringLocalDateValue value = new StringLocalDateValue("2017-05-04T10:00:50", "yyyy-MM-dd'T'HH:mm:ss");
        Assert.assertEquals("2017-05-04T10:00:50", value.getValue());
    }

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
    public void string_is_java_time_format() {
        StringLocalDateValue value0 = new StringLocalDateValue("LocalDateTime.now()", "yyyyMMdd HH:mm:ss");
        StringLocalDateValue value1 = new StringLocalDateValue("LocalDateTime.now().minusHours(1);", "yyyyMMdd HH:mm:ss");
        StringLocalDateValue value2 = new StringLocalDateValue("LocalDateTime.now().plusSeconds(1);", "yyyyMMdd HH:mm:ss");
        StringLocalDateValue value3 = new StringLocalDateValue("LocalDateTime.now().plusYears(5);", "yyyyMMdd HH:mm:ss");
        StringLocalDateValue value4 = new StringLocalDateValue("LocalDateTime.now().plusMinutes(5).plusMonths(5);", "yyyyMMdd HH:mm:ss");

        Assert.assertTrue(value0.isJavaFormat());
        Assert.assertTrue(value1.isJavaFormat());
        Assert.assertTrue(value2.isJavaFormat());
        Assert.assertTrue(value3.isJavaFormat());
        Assert.assertTrue(value4.isJavaFormat());
    }

    @Test
    public void string_parse_java() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDate.now()", "yyyyMMdd");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyyMMdd").print(LocalDate.now()), value.getValue());
    }

    @Test
    public void string_parse_java_single_plus_method() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDate.now().plusMonths(1)", "yyyyMMdd");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyyMMdd").print(LocalDate.now().plusMonths(1)), value.getValue());
    }

    @Test
    public void string_parse_java_single_minus_method() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDate.now().minusYears(1)", "yyyyMMdd");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyyMMdd").print(LocalDate.now().minusYears(1)), value.getValue());
    }

    @Test
    public void string_parse_java_single_minus_method_wit_semicolon() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDate.now().minusYears(1);", "yyyyMMdd");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyyMMdd").print(LocalDate.now().minusYears(1)), value.getValue());
    }

    @Test
    public void string_parse_java_multiple_plus_methods() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDate.now().plusDays(10).plusYears(1)", "yyyyMMdd");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyyMMdd").print(LocalDate.now().plusDays(10).plusYears(1)), value.getValue());
    }

    @Test
    public void string_parse_java_multiple_minus_methods() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDate.now().minusDays(10).minusYears(1)", "yyyyMMdd");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyyMMdd").print(LocalDate.now().minusDays(10).minusYears(1)), value.getValue());
    }



    @Test
    public void string_parse_java_time() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDateTime.now()", "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(LocalDateTime.now()), value.getValue());
    }

    @Test
    public void string_parse_java_time_single_plus_method() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDateTime.now().plusMonths(1)", "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(LocalDateTime.now().plusMonths(1)), value.getValue());
    }

    @Test
    public void string_parse_java_time_single_minus_method() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDateTime.now().minusYears(1)", "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(LocalDateTime.now().minusYears(1)), value.getValue());
    }

    @Test
    public void string_parse_java_time_single_minus_method_wit_semicolon() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDateTime.now().minusYears(1);", "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(LocalDateTime.now().minusYears(1)), value.getValue());
    }

    @Test
    public void string_parse_java_time_multiple_plus_methods() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDateTime.now().plusDays(10).plusYears(1)", "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(LocalDateTime.now().plusDays(10).plusYears(1)), value.getValue());
    }

    @Test
    public void string_parse_java_time_multiple_minus_methods() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDateTime.now().minusDays(10).minusYears(1)", "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(LocalDateTime.now().minusDays(10).minusYears(1)), value.getValue());
    }

    @Test
    public void string_parse_java_time_plus_hours() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDateTime.now().plusHours(5)", "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(LocalDateTime.now().plusHours(5)), value.getValue());
    }

    @Test
    public void string_parse_java_time_minus_hours() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDateTime.now().minusHours(3)", "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(LocalDateTime.now().minusHours(3)), value.getValue());
    }

    @Test
    public void string_parse_java_time_plus_minutes() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDateTime.now().plusMinutes(25)", "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(LocalDateTime.now().plusMinutes(25)), value.getValue());
    }

    @Test
    public void string_parse_java_time_minus_minutes() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDateTime.now().minusMinutes(3)", "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(LocalDateTime.now().minusMinutes(3)), value.getValue());
    }

    @Test
    public void string_parse_java_time_plus_seconds() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDateTime.now().plusSeconds(10)", "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(LocalDateTime.now().plusSeconds(10)), value.getValue());
    }

    @Test
    public void string_parse_java_time_minus_seconds() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDateTime.now().minusSeconds(30)", "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(LocalDateTime.now().minusSeconds(30)), value.getValue());
    }

    @Test
    public void string_parse_java_time_multi_field() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDateTime.now().plusHours(30).plusYears(1).minusSeconds(50)", "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(LocalDateTime.now().plusHours(30).plusYears(1).minusSeconds(50)), value.getValue());
    }

    @Test
    public void string_parse_java_time_multi_field2() {
        StringLocalDateValue value = new StringLocalDateValue("LocalDateTime.now().minusMinutes(22).plusMonths(3).plusHours(9)", "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(LocalDateTime.now().minusMinutes(22).plusMonths(3).plusHours(9)), value.getValue());
    }

}
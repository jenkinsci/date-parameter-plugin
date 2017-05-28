package me.leejay.jenkins.dateparameter.utils;

import org.junit.Assert;
import org.junit.Test;

import static me.leejay.jenkins.dateparameter.utils.LocalDatePattern.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by leejay on 2017. 5. 28..
 */
public class LocalDatePatternTest {

    @Test
    public void parseMethodName_test() {
        String methodName = LocalDatePattern.parseMethodName("plusDays(1)");
        Assert.assertEquals("plusDays", methodName);
    }

    @Test
    public void catchIntParameter_test() {
        Integer offset = LocalDatePattern.catchIntParameter("plusDays(1)");
        assertTrue(1 == offset);
    }

    @Test
    public void isLocalDateString_test() {
        boolean case0 = isValidLocalDateString("yyyy-MM-dd", "2017-05-01");
        Assert.assertTrue(case0);

        boolean case1 = isValidLocalDateString("yyyyMMdd", "2017-05-01");
        Assert.assertFalse(case1);
    }

    @Test
    public void isLocalDateJavaCode_fail_cases() {
        boolean case0 = isValidLocalDateJavaCode(null);
        boolean case1 = isValidLocalDateJavaCode("");
        boolean case2 = isValidLocalDateJavaCode("LocalDate");
        boolean case3 = isValidLocalDateJavaCode("LocalDate.now");
        boolean case4 = isValidLocalDateJavaCode("LocalDate.now.");
        boolean case5 = isValidLocalDateJavaCode("LocalDate.now.plusDays(1)");
        boolean case6 = isValidLocalDateJavaCode("..");
        boolean case7 = isValidLocalDateJavaCode("LocalDate.now().plusDay1)");

        Assert.assertFalse(case0);
        Assert.assertFalse(case1);
        Assert.assertFalse(case2);
        Assert.assertFalse(case3);
        Assert.assertFalse(case4);
        Assert.assertFalse(case5);
        Assert.assertFalse(case6);
        Assert.assertFalse(case7);
    }

    @Test
    public void isLocalDateJavaCode_success_cases() {
        boolean case0 = isValidLocalDateJavaCode("LocalDate.now();");
        boolean case1 = isValidLocalDateJavaCode("LocalDate.now().plusDays(1);");
        boolean case2 = isValidLocalDateJavaCode("LocalDate.now().plusDays(1).plusYears(1);");

        Assert.assertTrue(case0);
        Assert.assertTrue(case1);
        Assert.assertTrue(case2);
    }

    @Test
    public void runJavaStringCode_test() {
        String case1 = runJavaStringCode("LocalDate.now();");
        Assert.assertNotNull(case1);

        String case2 = runJavaStringCode("LocalDate.now().plusDays(1);");
        Assert.assertNotNull(case2);

        String case3 = runJavaStringCode("LocalDate.now().plusDays(1).plusMonths(2);");
        Assert.assertNotNull(case3);

        String case4 = runJavaStringCode("LocalDate.now().plusDays(1).plusMonths(2).plusYears(3);");
        Assert.assertNotNull(case4);

        String case5 = runJavaStringCode("LocalDate.now().minusDays(1);");
        Assert.assertNotNull(case5);

        String case6 = runJavaStringCode("LocalDate.now().minusDays(1).minusMonths(3);");
        Assert.assertNotNull(case6);

        String case7 = runJavaStringCode("LocalDate.now().minusDays(1).minusMonths(2).minusYears(3);");
        Assert.assertNotNull(case7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void runJavaStringCode_bad_cases() {
        runJavaStringCode("LocalDate.now().plus"); // not ends with ;
        runJavaStringCode("LocalDate.now().plusDays(-99)"); // not ends with ;
    }

}
package me.leejay.jenkins.dateparameter;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

/**
 * Created by JuHyunLee on 2017. 6. 2..
 */
public class IntegerParamMethodTest {

    @Test
    public void test_method() {
        IntegerParamMethod method = new IntegerParamMethod("plusDays(1);");
        Assert.assertEquals(method.getName(), "plusDays");
        Assert.assertThat(method.getParameter(), is(1));
    }

}
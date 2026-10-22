package me.leejay.jenkins.dateparameter;

import hudson.util.FormValidation;
import org.junit.Assert;
import org.junit.Test;

public class DateParameterDefinitionTest {

    @Test
    public void check_name_when_null_returns_error() {
        DateParameterDefinition.DescriptorImpl descriptor = new DateParameterDefinition.DescriptorImpl();

        FormValidation result = descriptor.doCheckName(null);

        Assert.assertEquals(FormValidation.Kind.ERROR, result.kind);
    }

    @Test
    public void check_name_when_empty_returns_error() {
        DateParameterDefinition.DescriptorImpl descriptor = new DateParameterDefinition.DescriptorImpl();

        FormValidation result = descriptor.doCheckName("");

        Assert.assertEquals(FormValidation.Kind.ERROR, result.kind);
    }

    @Test
    public void check_name_when_not_empty_returns_ok() {
        DateParameterDefinition.DescriptorImpl descriptor = new DateParameterDefinition.DescriptorImpl();

        FormValidation result = descriptor.doCheckName("MY_DATE");

        Assert.assertEquals(FormValidation.Kind.OK, result.kind);
    }

    @Test
    public void check_date_format_when_null_returns_error() {
        DateParameterDefinition.DescriptorImpl descriptor = new DateParameterDefinition.DescriptorImpl();

        FormValidation result = descriptor.doCheckDateFormat(null);

        Assert.assertEquals(FormValidation.Kind.ERROR, result.kind);
    }

    @Test
    public void check_date_format_when_empty_returns_error() {
        DateParameterDefinition.DescriptorImpl descriptor = new DateParameterDefinition.DescriptorImpl();

        FormValidation result = descriptor.doCheckDateFormat("");

        Assert.assertEquals(FormValidation.Kind.ERROR, result.kind);
    }

    @Test
    public void check_date_format_when_not_empty_returns_ok() {
        DateParameterDefinition.DescriptorImpl descriptor = new DateParameterDefinition.DescriptorImpl();

        FormValidation result = descriptor.doCheckDateFormat("yyyy-MM-dd");

        Assert.assertEquals(FormValidation.Kind.OK, result.kind);
    }
}
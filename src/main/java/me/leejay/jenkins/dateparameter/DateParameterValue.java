package me.leejay.jenkins.dateparameter;

import hudson.AbortException;
import hudson.EnvVars;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Run;
import hudson.model.StringParameterValue;
import hudson.tasks.BuildWrapper;
import hudson.util.VariableResolver;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by JuHyunLee on 2017. 5. 23..
 */
public class DateParameterValue extends StringParameterValue {

    private final static Logger log = LoggerFactory.getLogger(StringLocalDateValue.class);

    private final static long serialVersionUID = -3270996447541190520L;

    private final String value;

    private String dateFormat;

    @DataBoundConstructor
    public DateParameterValue(String name, String value, String description) {
        super(name, value, description);
        this.value = value;
    }

    public DateParameterValue(String name, String value, String dateFormat, String description) {
        this(name, value, description);
        this.dateFormat = dateFormat;
    }

    public void createValueFromDefault(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void createValueFromJenkins(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void createValueFromPostRequest(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public VariableResolver<String> createVariableResolver(AbstractBuild<?, ?> build) {
        return new VariableResolver<String>() {
            @Override
            public String resolve(String s) {
                return getValue();
            }
        };
    }

    @Override
    public void buildEnvironment(Run<?, ?> build, EnvVars env) {
        super.buildEnvironment(build, env);
    }

    @Override
    public BuildWrapper createBuildWrapper(AbstractBuild<?, ?> build) {
        if (StringUtils.isEmpty(getValue())) {
            return null;
        }

        StringLocalDateValue value = new StringLocalDateValue(getValue(), getDateFormat());
        if (!value.isCompletionFormat()) {
            return new BuildWrapper() {
                @Override
                public Environment setUp(AbstractBuild build, Launcher launcher, BuildListener listener) throws IOException, InterruptedException {
                    throw new AbortException("Can't parse date format '" + getValue() + "' with '" + getDateFormat() + "'");
                }
            };
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DateParameterValue that = (DateParameterValue) o;

        if (!value.equals(that.value)) return false;
        return dateFormat.equals(that.dateFormat);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + dateFormat.hashCode();
        return result;
    }
}

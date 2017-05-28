package me.leejay.jenkins.dateparameter;

import hudson.Extension;
import hudson.cli.CLICommand;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import hudson.util.FormValidation;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static me.leejay.jenkins.dateparameter.utils.LocalDatePattern.*;

/**
 * Created by JuHyunLee on 2017. 5. 23..
 */
public class DateParameterDefinition extends ParameterDefinition {

    private final static Logger log = LoggerFactory.getLogger(DateParameterDefinition.class);

    static final long serialVersionUID = 4;

    private final String dateFormat;

    private final String defaultValue;

    @DataBoundConstructor
    public DateParameterDefinition(String name, String dateFormat, String defaultValue, String description) {
        super(name, description);
        this.dateFormat = dateFormat;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getValue() {
        if (isValidLocalDateJavaCode(getDefaultValue())) {
            return runJavaStringCode(getDefaultValue());
        }

        if (isValidLocalDateString(getDateFormat(), getDefaultValue())) {
            return getDefaultValue();
        }

        return "";
    }

    @Override
    public DateParameterValue getDefaultParameterValue() {
        log.info(">>>>> getDefaultParameterValue called");
        DateParameterValue v = new DateParameterValue(getName(), "good", getDescription());
        return v;
    }

    @Override
    public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
        DateParameterValue value = req.bindJSON(DateParameterValue.class, jo);
        value.setDateFormat(getDateFormat());
        return value;
    }

    @Override
    public ParameterValue createValue(StaplerRequest staplerRequest) {
        log.info("createValue2 called");
        String[] value = staplerRequest.getParameterValues(getName());
        if (ArrayUtils.isEmpty(value)) {
            log.info(">>>>> createValue2: empty");
            return getDefaultParameterValue();
        }

        return new DateParameterValue(getName(), value[0], getDescription());
    }

    @Override
    public ParameterValue createValue(CLICommand command, String value) throws IOException, InterruptedException {
        log.info(">>>>> createValue3 called cli");
        return super.createValue(command, value);
    }

    @Extension
    public static final class DescriptorImpl extends ParameterDescriptor {

        private final static String DISPLAY_NAME = "Date Parameter";

        @Override
        public String getDisplayName() {
            return DISPLAY_NAME;
        }

        public FormValidation doCheckName(@QueryParameter String name) {
            if (StringUtils.isEmpty(name)) {
                return FormValidation.error("Please enter a name.");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckDateFormat(@QueryParameter String dateFormat) {
            if (StringUtils.isEmpty(dateFormat)) {
                return FormValidation.error("Please enter a date format");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckDefaultValue(@QueryParameter String dateFormat, @QueryParameter String defaultValue) {
            if (isValidLocalDateJavaCode(defaultValue)) {
                return FormValidation.ok();
            }

            if (isValidLocalDateString(dateFormat, defaultValue)) {
                return FormValidation.ok();
            }

            return FormValidation.error("Invalid default value");
        }

    }

}

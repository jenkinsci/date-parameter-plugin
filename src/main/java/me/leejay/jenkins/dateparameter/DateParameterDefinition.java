package me.leejay.jenkins.dateparameter;

import hudson.Extension;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import hudson.util.FormValidation;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import static me.leejay.jenkins.dateparameter.utils.LocalDatePattern.*;
import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Created by JuHyunLee on 2017. 5. 23..
 */
public class DateParameterDefinition extends ParameterDefinition {

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
        return new DateParameterValue(getName(), getValue(), getDescription());
    }

    @Override
    public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
        DateParameterValue dateParameterValue = req.bindJSON(DateParameterValue.class, jo);
        dateParameterValue.setDateFormat(getDateFormat());
        return dateParameterValue;
    }

    @Override
    public ParameterValue createValue(StaplerRequest staplerRequest) {
        String value = staplerRequest.getParameter(getName());
        if (isEmpty(value)) {
            return getDefaultParameterValue();
        }

        DateParameterValue dateParameterValue = new DateParameterValue(getName(), value, getDescription());
        dateParameterValue.setDateFormat(getDateFormat());
        return dateParameterValue;
    }

    @Extension
    public static final class DescriptorImpl extends ParameterDescriptor {

        private final static String DISPLAY_NAME = "Date Parameter";

        @Override
        public String getDisplayName() {
            return DISPLAY_NAME;
        }

        public FormValidation doCheckName(@QueryParameter String name) {
            if (isEmpty(name)) {
                return FormValidation.error("Please enter a name.");
            }
            return FormValidation.ok();
        }

        public FormValidation doCheckDateFormat(@QueryParameter String dateFormat) {
            if (isEmpty(dateFormat)) {
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

package me.leejay.jenkins.dateparameter;

import hudson.Extension;
import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import hudson.util.FormValidation;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Created by JuHyunLee on 2017. 5. 23..
 */
public class DateParameterDefinition extends ParameterDefinition {

    static final long serialVersionUID = 4;

    private final StringLocalDateValue stringLocalDateValue;

    @DataBoundConstructor
    public DateParameterDefinition(String name, String dateFormat, String defaultValue, String description) {
        super(name, description);
        this.stringLocalDateValue = new StringLocalDateValue(defaultValue, dateFormat);
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
        return stringLocalDateValue.getStringDateFormat();
    }

    public String getDefaultValue() {
        return stringLocalDateValue.getStringLocalDate();
    }

    public String getValue() {
        return stringLocalDateValue.getValue();
    }

    @Override
    public DateParameterValue getDefaultParameterValue() {
        return new DateParameterValue(getName(), getValue(), getDescription());
    }

    @Override
    public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
        DateParameterValue value = req.bindJSON(DateParameterValue.class, jo);
        value.createValueFromJenkins(getDateFormat());
        return value;
    }

    @Override
    public ParameterValue createValue(StaplerRequest staplerRequest) {
        String requestedValue = staplerRequest.getParameter(getName());
        if (isEmpty(requestedValue)) {
            return getDefaultParameterValue();
        }

        DateParameterValue value = new DateParameterValue(getName(), requestedValue, getDateFormat(), getDescription());
        value.createValueFromPostRequest(getDateFormat());
        return value;
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
            StringLocalDateValue value = new StringLocalDateValue(defaultValue, dateFormat);
            if (value.isCompletionFormat()) {
                return FormValidation.ok();
            }

            if (value.isJavaFormat()) {
                return FormValidation.ok();
            }

            return FormValidation.error("Invalid default value");
        }

    }

}

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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by JuHyunLee on 2017. 5. 23..
 */
public class DateParameterDefinition extends ParameterDefinition {

    private final static Logger LOG = Logger.getLogger(DateParameterDefinition.class.getName());

    static final long serialVersionUID = 4;

    private final String dateFormat;

    private final String defaultValue;

    @DataBoundConstructor
    public DateParameterDefinition(String name, String dateFormat, String defaultValue, String description) {
        super(name, description);
        this.dateFormat = dateFormat;
        this.defaultValue = defaultValue;
        LOG.info(">>>>> DateParameterDefinition constructor called");
    }

    @Override
    public String getName() {
        LOG.info(">>>>> getName called");
        return super.getName();
    }

    @Override
    public String getDescription() {
        LOG.info(">>>>> getDescription called");
        return super.getDescription();
    }

    public String getDateFormat() {
        LOG.info(">>>>> getDateFormat called");
        return dateFormat;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getDefaultDateValue() {
        LOG.info(">>>>> getDefaultValue called: " + defaultValue);
        List<String> codes = Arrays.asList(defaultValue.split("."));

        if (codes.isEmpty()) {
            LOG.info(">>>>> getDefaultValue: empty");
            return "";
        }

        if (!codes.get(0).startsWith("LocalDate.now()")) {
            LOG.info(">>>>> getDefaultValue: not startsWith LocalDate.now()");
            return "";
        }

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(c.getTime());
    }

    @Override
    public DateParameterValue getDefaultParameterValue() {
        LOG.info(">>>>> getDefaultParameterValue called");
        DateParameterValue v = new DateParameterValue(getName(), "good", getDescription());
        return v;
    }

    @Override
    public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
        LOG.info(">>>>> createValue1 called");
        DateParameterValue value = req.bindJSON(DateParameterValue.class, jo);
        value.setDescription(this.getDescription());
        return value;
    }

    @Override
    public ParameterValue createValue(StaplerRequest staplerRequest) {
        LOG.info(">>>>> createValue2 called");
        String[] value = staplerRequest.getParameterValues(getName());
        if (ArrayUtils.isEmpty(value)) {
            LOG.info(">>>>> createValue2: empty");
            return getDefaultParameterValue();
        }

        return new DateParameterValue(getName(), "good2", value[0]);
    }

    @Override
    public ParameterValue createValue(CLICommand command, String value) throws IOException, InterruptedException {
        LOG.info(">>>>> createValue3 called cli");
        return super.createValue(command, value);
    }


    @Extension
    public static final class DescriptorImpl extends ParameterDescriptor {

        private final static String DISPLAY_NAME = "Date Parameter";

        @Override
        public String getDisplayName() {
            LOG.info(">>>>> getDisplayName called");
            return DISPLAY_NAME;
        }

        public FormValidation doCheckName(@QueryParameter String name) {
            if (isValidName(name)) {
                return FormValidation.ok();
            }
            return FormValidation.error("Please Enter a name.");
        }

        public FormValidation doCheckDefaultValue(@QueryParameter String defaultValue) {
            if (isValidStringDate(defaultValue)) {

            }
        }

        public boolean isValidName(String dateFormat) {
            return StringUtils.isNotEmpty(dateFormat);
        }

        public boolean isValidStringDate(String defaultValue) {

        }

    }

}

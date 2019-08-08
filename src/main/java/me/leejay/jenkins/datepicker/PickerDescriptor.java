package me.leejay.jenkins.datepicker;

import hudson.model.ParameterDefinition;
import hudson.util.FormValidation;
import org.kohsuke.stapler.QueryParameter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * @author Vimalraj Chandra Sekaran (rajvimalc)
 * Email: rajvimalc@gmail.com
 * Created on: 8/8/2019 11:13 AM
 */
public class PickerDescriptor extends ParameterDefinition.ParameterDescriptor {

    private final String displayName;
    private final String defaultFormat;

    protected PickerDescriptor(String displayName, String defaultFormat) {
        this.displayName = displayName;
        this.defaultFormat = defaultFormat;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    public FormValidation doCheckName(@QueryParameter String name) {
        if (isEmpty(name)) {
            return FormValidation.error("Please enter a name");
        }
        return FormValidation.ok();
    }

    public FormValidation doCheckDefaultValue(@QueryParameter String defaultValue) {
        if (validateJavaDate(defaultValue)) {
            return FormValidation.ok();
        }
        return FormValidation.error("Invalid default value");
    }

    public boolean validateJavaDate(String strDate) {
        if (isBlank(strDate)) {
            return true;
        }
        final SimpleDateFormat sdf = new SimpleDateFormat(defaultFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(strDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}

package me.leejay.jenkins.datepicker;

import hudson.Extension;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * @author Vimalraj Chandra Sekaran (rajvimalc)
 * Email: rajvimalc@gmail.com
 * Created on: 8/8/2019 10:54 AM
 */
public class DatePickerDefinition extends PickerDefinition {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    private static final String DISPLAY_NAME = "Date Picker Parameter";

    @DataBoundConstructor
    public DatePickerDefinition(String name, String defaultValue, String description) {
        super(name, defaultValue, description, DEFAULT_DATE_FORMAT);
    }

    @Extension
    public static final class DescriptorImpl extends PickerDescriptor {
        public DescriptorImpl() {
            super(DISPLAY_NAME, DEFAULT_DATE_FORMAT);
        }
    }

}

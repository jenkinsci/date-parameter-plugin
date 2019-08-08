package me.leejay.jenkins.datepicker;

import hudson.model.ParameterDefinition;
import hudson.model.ParameterValue;
import me.leejay.jenkins.DateParameterValue;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * @author Vimalraj Chandra Sekaran (rajvimalc)
 * Email: rajvimalc@gmail.com
 * Created on: 8/8/2019 11:13 AM
 */
public class PickerDefinition extends ParameterDefinition {

    private String defaultValue;

    private String value;

    private final String defaultFormat;

    protected PickerDefinition(String name, String defaultValue, String description, String defaultFormat) {
        super(name, description);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.defaultFormat = defaultFormat;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }


    /**
     * Called from jelly
     * @return the default value
     */
    public String getDefaultValue() {
        return this.defaultValue;
    }

    /**
     * Called from jelly
     * @return the value entered, if blank, default value
     */
    public String getValue() {
        return this.value;
    }

    @Override
    public DateParameterValue getDefaultParameterValue() {
        DateParameterValue value = new DateParameterValue(getName(), getValue(), getDescription());
        value.createValueFromDefault(defaultFormat);
        return value;
    }

    @Override
    public ParameterValue createValue(StaplerRequest req, JSONObject jo) {
        DateParameterValue value = req.bindJSON(DateParameterValue.class, jo);
        value.createValueFromJenkins(defaultFormat);
        return value;
    }

    @Override
    public ParameterValue createValue(StaplerRequest staplerRequest) {
        String requestedValue = staplerRequest.getParameter(getName());
        if (isEmpty(requestedValue)) {
            return getDefaultParameterValue();
        }

        DateParameterValue value = new DateParameterValue(getName(), requestedValue, defaultFormat, getDescription());
        value.createValueFromPostRequest(defaultFormat);
        return value;
    }

}

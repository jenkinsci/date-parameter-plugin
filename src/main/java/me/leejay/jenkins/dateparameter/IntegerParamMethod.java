package me.leejay.jenkins.dateparameter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by JuHyunLee on 2017. 6. 2..
 */
public class IntegerParamMethod {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final Pattern PATTERN = Pattern.compile("^(?<name>.+)\\((?<parameter>[0-9]+)\\);?$");

    public String name;

    public Integer parameter;

    private String code;

    public IntegerParamMethod(String code) {
        log.info("IntegerParamMethod({})", code);
        this.code = code;
        parse();
    }

    private void parse() {
        Matcher matcher = PATTERN.matcher(code);
        if (matcher.matches()) {
            name = matcher.group("name");
            parameter = Integer.parseInt(matcher.group("parameter"));
        }
    }

    public String getName() {
        return name;
    }

    public Integer getParameter() {
        return parameter;
    }
}

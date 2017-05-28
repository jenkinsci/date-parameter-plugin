package me.leejay.jenkins.dateparameter;

import hudson.EnvVars;
import hudson.model.AbstractBuild;
import hudson.model.Run;
import hudson.model.StringParameterValue;
import hudson.util.VariableResolver;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.logging.Logger;

/**
 * Created by JuHyunLee on 2017. 5. 23..
 */
public class DateParameterValue extends StringParameterValue {

    private final static Logger LOG = Logger.getLogger(DateParameterValue.class.getName());

    private static final long serialVersionUID = 1L;

    @DataBoundConstructor
    public DateParameterValue(String name, String value, String description) {
        super(name, value, description);
        LOG.info(">>>>> DateParameterValue1 called");
        LOG.info(">>>>> " + name);
        LOG.info(">>>>> " + value);
    }

    @Override
    public VariableResolver<String> createVariableResolver(AbstractBuild<?, ?> build) {
        LOG.info(">>>>> createVariableResolver called");
        return new VariableResolver<String>() {
            @Override
            public String resolve(String s) {
                return "good";
            }
        };
    }

    @Override
    public void buildEnvironment(Run<?, ?> build, EnvVars env) {
        super.buildEnvironment(build, env);
    }

    @Override
    public String getValue() {
        LOG.info(">>>>> getValue called");
        return "my static string value";
    }

    @Override
    public String getShortDescription() {
        LOG.info(">>>>> getShortDescription called");
        return "getShortDescription";
    }

//    @Override
//    public BuildWrapper createBuildWrapper(AbstractBuild<?, ?> build) {
//        LOG.info(">>>>> createBuildWrapper called");
//        return new BuildWrapper() {
//            @Override
//            public Environment setUp(AbstractBuild build, Launcher launcher, BuildListener listener) throws IOException, InterruptedException {
//                LOG.info(">>>>> createBuildWrapper.setup called");
//                throw new AbortException("엥..");
//            }
//        };
//    }

    @Override
    public int hashCode() {
        LOG.info(">>>>> hashCode called");
        final int prime = 71;
        int result = super.hashCode();
        result = prime * result;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        LOG.info(">>>>> equals called");
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (DateParameterValue.class != obj.getClass()) {
            return false;
        }
        DateParameterValue other = (DateParameterValue) obj;
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        LOG.info(">>>>> toString called");
        return "(DateParameterValue) " + getName() + "='" + value + "'";
    }

}

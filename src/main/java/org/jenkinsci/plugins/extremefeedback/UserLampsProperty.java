package org.jenkinsci.plugins.extremefeedback;

import com.google.common.collect.Sets;
import hudson.Extension;
import hudson.model.Action;
import hudson.model.User;
import hudson.model.UserProperty;
import hudson.model.UserPropertyDescriptor;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.extremefeedback.model.Lamp;
import org.kohsuke.stapler.bind.JavaScriptMethod;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.logging.Logger;

/**
 * User Property for controlling his own lamps.
 */
public class UserLampsProperty extends UserProperty implements Action{

    private static final Logger LOGGER = Logger.getLogger("jenkins.plugins.extremefeedback.user");
    private User user;
    private Set<Lamp> lamps = new ConcurrentSkipListSet<Lamp>();

    public UserLampsProperty(User user) {
        this.user = user;

    }

    @JavaScriptMethod
    public Set<Lamp> getLamps() {
        if (lamps == null) {
            lamps = new ConcurrentSkipListSet<Lamp>();
        }
        return lamps;
    }

    @JavaScriptMethod
    public Set<Lamp> addLampByIpAddress(String ipAddress) {
        return Lamps.addLampByIp(getLamps(), ipAddress);
    }

    @JavaScriptMethod
    public Collection<String> getProjects() {
        return Jenkins.getInstance().getJobNames();
    }

    @JavaScriptMethod
    public boolean updateLamp(Lamp lamp) {
        Map<String,Lamp> lamps = Lamps.getLampsAsMap(this.getLamps());
        lamps.put(lamp.getMacAddress(), lamp);
        this.lamps = Sets.newHashSet(lamps.values());
        try {
            user.save();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @JavaScriptMethod
    public Collection<Lamp> addProjectToLamp(String projectName, String macAddress) {
        if (Jenkins.getInstance().getJobNames().contains(projectName)) {
            Map<String, Lamp> lamps = Lamps.getLampsAsMap(this.getLamps());
            Lamp lamp = lamps.get(macAddress);
            lamp.addJob(projectName);
            this.lamps = Sets.newHashSet(lamps.values());
            try {
                user.save();
            } catch (IOException e) {
                LOGGER.severe("Could not save the Lamps plugin");
                return new HashSet<Lamp>();
            }
        }
        return getLamps();
    }

    @JavaScriptMethod
    public Collection<Lamp> removeProjectFromLamp(String projectName, String macAddress) {
        Map<String, Lamp> lamps = Lamps.getLampsAsMap(this.getLamps());
        Lamp lamp = lamps.get(macAddress);
        lamp.removeJob(projectName);
        this.lamps = Sets.newHashSet(lamps.values());
        try {
            user.save();
        } catch (IOException e) {
            LOGGER.severe("Could not save the Lamps plugin");
            return new HashSet<Lamp>();
        }
        return getLamps();
    }

    @JavaScriptMethod
    public Collection<Lamp> removeLamp(String macAddress) {
        Map<String, Lamp> lamps = Lamps.getLampsAsMap(this.getLamps());
        lamps.remove(macAddress);
        this.lamps = Sets.newHashSet(lamps.values());
        try {
            user.save();
        } catch (IOException e) {
            LOGGER.severe("Could not save the Lamps plugin");
        }
        return getLamps();
    }

    public String getIconFileName() {
        return "/plugin/extreme-feedback/traffic-light.png";
    }

    public String getDisplayName() {
        return "Lamps";
    }

    public String getUrlName() {
        return "extreme-feedback";
    }

    @Extension
    public static class UserLampsPropertyDescriptor extends UserPropertyDescriptor {
        @Override
        public UserProperty newInstance(User user) {
            return new UserLampsProperty(user);
        }

        @Override
        public String getDisplayName() {
            return "My XF Lamps";
        }
    }
}

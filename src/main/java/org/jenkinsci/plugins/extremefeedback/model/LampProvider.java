package org.jenkinsci.plugins.extremefeedback.model;

import com.google.common.collect.Sets;
import hudson.ExtensionList;
import jenkins.model.Jenkins;
import org.apache.tools.ant.ExtensionPoint;

import java.util.Set;

/**
 * Extension point for different lamp containers
 */
public abstract class LampProvider extends ExtensionPoint {
    public abstract Set<Lamp> getLamps();

    public Set<Lamp> getLampsContainingJob(String jobName) {
        Set<Lamp> activeLamps = Sets.newHashSet();
        for (Lamp lamp : getLamps()) {
            if (lamp.getJobs().contains(jobName)) {
                activeLamps.add(lamp);
            }
        }
        return activeLamps;
    }

    public Set<String> getJobs() {
        Set<String> jobs = Sets.newHashSet();
        for (Lamp lamp : getLamps()) {
            jobs.addAll(lamp.getJobs());
        }
        return jobs;
    }

    public static Set<String> getAllJobs() {
        Set<String> jobs = Sets.newHashSet();
        for(LampProvider provider : all()) {
            jobs.addAll(provider.getJobs());
        }
        return jobs;
    }

    public static Set<Lamp> getAllLampsContainingJob(String jobName) {
        Set<Lamp> activeLamps = Sets.newHashSet();
        for(LampProvider provider : all()) {
            activeLamps.addAll(provider.getLampsContainingJob(jobName));
        }
        return activeLamps;
    }

    public static ExtensionList<LampProvider> all() {
        return Jenkins.getInstance().getExtensionList(LampProvider.class);
    }
}

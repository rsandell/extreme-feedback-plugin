package org.jenkinsci.plugins.extremefeedback.model;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.Serializable;
import java.util.Set;

public class Lamp implements Comparable<Lamp>, Serializable {

    private static final long serialVersionUID = 6746373847474387387L;

    private String ipAddress;
    private String macAddress;
    private String name;
    private Set<String> jobs = Sets.newHashSet();
    private boolean noisy;
    private boolean sfx;
    private boolean inactive;

    public Lamp(String macAddress, String ipAddress) {
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
    }

    @DataBoundConstructor
    public Lamp(String ipAddress, String macAddress, String name, String[] jobs, boolean noisy, boolean sfx, boolean inactive) {
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.name = name;
        this.jobs = Sets.newHashSet(jobs);
        this.noisy = noisy;
        this.sfx = sfx;
        this.inactive = inactive;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNoisy() {
        return noisy;
    }

    public void setNoisy(boolean noisy) {
        this.noisy = noisy;
    }

    public boolean isSfx() {
        return sfx;
    }

    public void setSfx(boolean sfx) {
        this.sfx = sfx;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public Set<String> getJobs() {
        return jobs;
    }

    public void setJobs(Set<String> jobs) {
        this.jobs = jobs;
    }

    public void addJobs(Set<String> jobs) {
        this.jobs.addAll(jobs);
    }

    public int compareTo(Lamp o) {
        return this.macAddress.compareTo(o.getMacAddress());
    }

    public void addJob(String job) {
        this.jobs.add(job);
    }

    public void removeJob(String job) {
        this.jobs.remove(job);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.macAddress);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Lamp) {
            Lamp that = (Lamp) object;
            return Objects.equal(this.getMacAddress(), that.getMacAddress());
        }
        return false;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("MAC", macAddress).toString();
    }
}

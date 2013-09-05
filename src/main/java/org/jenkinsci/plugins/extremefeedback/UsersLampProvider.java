package org.jenkinsci.plugins.extremefeedback;

import com.google.common.collect.Sets;
import hudson.Extension;
import hudson.model.User;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.extremefeedback.model.Lamp;
import org.jenkinsci.plugins.extremefeedback.model.LampProvider;

import java.util.Set;

/**
 * {@link LampProvider} that collects all lamps from {@link UserLampsProperty}.
 */
@Extension
public class UsersLampProvider extends LampProvider {
    @Override
    public Set<Lamp> getLamps() {
        Set<Lamp> activeLamps = Sets.newHashSet();
        for(User user : User.getAll()) {
            UserLampsProperty prop = user.getProperty(UserLampsProperty.class);
            if (prop != null) {
                activeLamps.addAll(prop.getLamps());
            }
        }
        return activeLamps;
    }
}

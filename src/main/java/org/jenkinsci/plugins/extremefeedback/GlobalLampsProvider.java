package org.jenkinsci.plugins.extremefeedback;

import hudson.Extension;
import org.jenkinsci.plugins.extremefeedback.model.Lamp;
import org.jenkinsci.plugins.extremefeedback.model.LampProvider;

import java.util.Set;

/**
 * {@link LampProvider} for the global lamps contained in {@link Lamps}.
 */
@Extension
public class GlobalLampsProvider extends LampProvider {
    @Override
    public Set<Lamp> getLamps() {
        return Lamps.getInstance().getLamps();
    }
}

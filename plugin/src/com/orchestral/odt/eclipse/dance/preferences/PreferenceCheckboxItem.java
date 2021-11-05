package com.orchestral.odt.eclipse.dance.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;

import com.orchestral.odt.eclipse.dance.EclipseDanceActivator;

public class PreferenceCheckboxItem extends PreferenceItemAdapter<Boolean> {
			
	public PreferenceCheckboxItem(String name, IPreferenceItem<?> parent, boolean defaultValue) {
		super(name, parent, defaultValue);
	}

	public PreferenceCheckboxItem(String name, boolean defaultValue) {
		super(name, defaultValue);
	}

	@Override
	public Boolean getValue() {
		Preferences preferences = InstanceScope.INSTANCE.getNode(EclipseDanceActivator.PLUGIN_ID);
		return preferences.getBoolean(getName(), this.getDefaultValue());
	}

	@Override
	public boolean isEnabled() {
		if (this.getParent() != null) {
			return this.getParent().isEnabled() && this.getValue();
		}
		return this.getValue();
	}
}

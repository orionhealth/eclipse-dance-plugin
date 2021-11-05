package com.orchestral.odt.eclipse.dance.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;

import com.orchestral.odt.eclipse.dance.EclipseDanceActivator;

public class PreferenceTextItem extends PreferenceItemAdapter<String> {

	public PreferenceTextItem(String name, String defaultValue) {
		super(name, defaultValue);
	}

	@Override
	public String getValue() {
		Preferences preferences = InstanceScope.INSTANCE.getNode(EclipseDanceActivator.PLUGIN_ID);
		return preferences.get(this.getName(), this.getDefaultValue());
	}

	@Override
	public boolean isEnabled() {
		return this.getValue() != null && !this.getValue().isEmpty();
	}
}

package com.orchestral.odt.eclipse.dance.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.orchestral.odt.eclipse.dance.EclipseDanceActivator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore preferenceStore = EclipseDanceActivator.getDefault().getPreferenceStore();
		for (IPreferenceItem<?> item : PreferenceItemRegistry.getInstance().getPreferences()) {
			Object defaultValue = item.getDefaultValue();
			if (defaultValue.getClass().equals(String.class)) {
				preferenceStore.setDefault(item.getName(), (String) defaultValue);
			}
			else if (defaultValue.getClass().equals(Boolean.class)) {
				preferenceStore.setDefault(item.getName(), (Boolean) defaultValue);
			}
		}
	}
}

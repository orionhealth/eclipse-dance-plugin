package com.orchestral.odt.eclipse.dance.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.statushandlers.StatusManager;

public final class PreferenceItemRegistry {
	
	private static final PreferenceItemRegistry INSTANCE = new PreferenceItemRegistry();
	
	private final List<IPreferenceItem<?>> preferences;
	
	private PreferenceItemRegistry() {
		preferences = new ArrayList<>();
		preferences.add(new PreferenceCheckboxItem(Preference.RELOAD_TARGET_PLATFORM.name(), true));
		preferences.add(new PreferenceCheckboxItem(Preference.REFRESH_PROJECTS.name(), true));
		preferences.add(new PreferenceCheckboxItem(Preference.CLEAN_ALL_PROJECTS.name(), true));
		
		IPreferenceItem<String> runConfig = new PreferenceTextItem(Preference.RUN_CONFIG.name(), "");
		preferences.add(runConfig);
		
		try {
			preferences.add(new PreferenceCheckboxItem(Preference.RUN_CONFIG_ADD_ALL_BUNDLES.name(), runConfig, PreferenceUtils.hasOsgiLaunchConfigurations()));
			preferences.add(new PreferenceCheckboxItem(Preference.RUN_CONFIG_DELETE_WORK_DIR.name(), runConfig, PreferenceUtils.hasOsgiLaunchConfigurations()));
		} catch (CoreException e) {
			StatusManager.getManager().handle(e.getStatus());
		}
		
	}
	
	public static PreferenceItemRegistry getInstance() {
		return INSTANCE;
	}
	
	public List<IPreferenceItem<?>> getPreferences() {
		return this.preferences;
	}
	
	@SuppressWarnings("unchecked")
	public <T> IPreferenceItem<T> getPreference(String name, Class<T> type) {
		for (IPreferenceItem<?> item : preferences) {
			if (item.getName().equals(name)) {
				return (IPreferenceItem<T>) item;
			}
		}
		throw new IllegalArgumentException(String.format("Cannot find preference item with name %s", name));
	}
}

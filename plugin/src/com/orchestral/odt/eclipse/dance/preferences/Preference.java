package com.orchestral.odt.eclipse.dance.preferences;


/**
 * Constant definitions for plug-in preferences
 */
public enum Preference {

	RELOAD_TARGET_PLATFORM("Reload target platform", Boolean.class),
	REFRESH_PROJECTS("Refresh projects", Boolean.class),
	CLEAN_ALL_PROJECTS("Clean all projects", Boolean.class),
	RUN_CONFIG("Run configuration", String.class),
	RUN_CONFIG_ADD_ALL_BUNDLES("Add all bundles from target platform", Boolean.class),
	RUN_CONFIG_DELETE_WORK_DIR("Delete work directory", Boolean.class);
	
	private final String label;
	
	private Preference(String label, Class<?> type) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
}

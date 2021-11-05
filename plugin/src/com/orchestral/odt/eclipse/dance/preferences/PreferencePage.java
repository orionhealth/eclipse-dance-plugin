package com.orchestral.odt.eclipse.dance.preferences;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.service.prefs.Preferences;

import com.orchestral.odt.eclipse.dance.EclipseDanceActivator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	private ComboFieldEditor comboRunconfig;
	private BooleanFieldEditor boolAddAllPlugins;
	private BooleanFieldEditor boolDeleteWorkDirectory;
	
	public PreferencePage() {
		super(GRID);
		setPreferenceStore(EclipseDanceActivator.getDefault().getPreferenceStore());
		setDescription("Eclipse Dance Plug-In Settings");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	@Override
	public void createFieldEditors() {
		try {
			createBasicPluginSettings();
			createRunConfigurationSettings();
		}
		catch (CoreException e) {
			StatusManager.getManager().handle(e.getStatus());
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
	}
		
	private void createBasicPluginSettings() {
		addField(new BooleanFieldEditor(Preference.RELOAD_TARGET_PLATFORM.name(), Preference.RELOAD_TARGET_PLATFORM.getLabel(), getFieldEditorParent())); 
		addField(new BooleanFieldEditor(Preference.REFRESH_PROJECTS.name(), Preference.REFRESH_PROJECTS.getLabel(), getFieldEditorParent()));
		addField(new BooleanFieldEditor(Preference.CLEAN_ALL_PROJECTS.name(), Preference.CLEAN_ALL_PROJECTS.getLabel(), getFieldEditorParent()));
	}
	
	private void createRunConfigurationSettings() throws CoreException {
		createRunConfigComboEditor();
		createRunConfigActionSettings();
	}
	
	private void createRunConfigComboEditor() throws CoreException {
		String name = Preference.RUN_CONFIG.name();
		String label = Preference.RUN_CONFIG.getLabel();
				
		ILaunchConfiguration[] launchConfigurations = PreferenceUtils.getOsgiLaunchConfigurations();
		String[][] entryNamesAndValues = new String[0][0];
		
		if (launchConfigurations.length > 0) {
			entryNamesAndValues = new String[launchConfigurations.length][2];
			for (int i = 0; i < launchConfigurations.length; i++) {
				entryNamesAndValues[i][0] = launchConfigurations[i].getName();
				entryNamesAndValues[i][1] = launchConfigurations[i].getMemento();
			}
		} 
		else {
			Preferences preferences = InstanceScope.INSTANCE.getNode(EclipseDanceActivator.PLUGIN_ID);
			preferences.put(Preference.RUN_CONFIG.name(), "");
			preferences.putBoolean(Preference.RUN_CONFIG_ADD_ALL_BUNDLES.name(), false);
			preferences.putBoolean(Preference.RUN_CONFIG_DELETE_WORK_DIR.name(), false);
		}
						
		comboRunconfig = new ComboFieldEditor(name, label, entryNamesAndValues, getFieldEditorParent());
		addField(comboRunconfig);
	}
	
	private void createRunConfigActionSettings() throws CoreException {
		createAddAllPluginsEditor();
		createDeleteWorkDirectoryEditor();
	}
	
	private void createDeleteWorkDirectoryEditor() throws CoreException {
		boolDeleteWorkDirectory = new BooleanFieldEditor(Preference.RUN_CONFIG_DELETE_WORK_DIR.name(), Preference.RUN_CONFIG_DELETE_WORK_DIR.getLabel(), getFieldEditorParent()); 
		addField(boolDeleteWorkDirectory);
		boolDeleteWorkDirectory.setEnabled(PreferenceUtils.hasOsgiLaunchConfigurations(), getFieldEditorParent());
	}
	
	private void createAddAllPluginsEditor() throws CoreException {
		boolAddAllPlugins = new BooleanFieldEditor(Preference.RUN_CONFIG_ADD_ALL_BUNDLES.name(), Preference.RUN_CONFIG_ADD_ALL_BUNDLES.getLabel(), getFieldEditorParent());
		addField(boolAddAllPlugins);
		boolAddAllPlugins.setEnabled(PreferenceUtils.hasOsgiLaunchConfigurations(), getFieldEditorParent());
	}
}

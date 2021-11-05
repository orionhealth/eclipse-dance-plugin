package com.orchestral.odt.eclipse.dance.preferences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.pde.core.plugin.IPluginModelBase;

public final class PreferenceUtils {
	
	private static final String OSGI_LAUNCH_CONFIG_ID = "org.eclipse.pde.ui.EquinoxLauncher";
	
	private static final String BUNDLE_START_LEVEL = "default";
	private static final String BUNDLE_AUTO_START = "default";
	
	private PreferenceUtils() {}
	
	public static ILaunchConfiguration[] getOsgiLaunchConfigurations() throws CoreException {
		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType launchConfigurationType = launchManager.getLaunchConfigurationType(OSGI_LAUNCH_CONFIG_ID);
		
		return launchManager.getLaunchConfigurations(launchConfigurationType);
	}
	
	public static ILaunchConfiguration getOsgiLaunchConfiguration() throws CoreException {
		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		PreferenceItemRegistry preferenceRegistry = PreferenceItemRegistry.getInstance();
		IPreferenceItem<String> preferenceItem = preferenceRegistry.getPreference(Preference.RUN_CONFIG.name(), String.class);
		
		return launchManager.getLaunchConfiguration(preferenceItem.getValue());
	}
	
	public static boolean hasOsgiLaunchConfigurations() throws CoreException {
		return getOsgiLaunchConfigurations().length > 0;
	}
	
	public static String getBundleNames(IPluginModelBase[] bundles) {
		List<String> bundleInfo = new ArrayList<String>();
		for (IPluginModelBase model : bundles) {
			bundleInfo.add(model.getBundleDescription().getName() + "@" + BUNDLE_START_LEVEL + ":" + BUNDLE_AUTO_START);
		}
		return joinBundleNames(bundleInfo);
	}
	
	private static String joinBundleNames(List<String> names) {
		Iterator<String> iterator = names.iterator();
		StringBuilder resultBuilder = new StringBuilder();
		if (iterator.hasNext()) {
			resultBuilder.append(iterator.next());
			while (iterator.hasNext()) {
				resultBuilder.append(",").append(iterator.next());
			}
		}
		return resultBuilder.toString();
	}
}

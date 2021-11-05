package com.orchestral.odt.eclipse.dance.tasks;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import com.orchestral.odt.eclipse.dance.EclipseDanceActivator;
import com.orchestral.odt.eclipse.dance.preferences.IPreferenceItem;
import com.orchestral.odt.eclipse.dance.preferences.Preference;
import com.orchestral.odt.eclipse.dance.preferences.PreferenceCheckboxItem;
import com.orchestral.odt.eclipse.dance.preferences.PreferenceItemRegistry;

public class RefreshEnvironmentTask implements Runnable {
	
	private final Map<Preference, Runnable> taskMap;
	
	public RefreshEnvironmentTask() {
		taskMap = new EnumMap<Preference, Runnable>(Preference.class);
		taskMap.put(Preference.RELOAD_TARGET_PLATFORM, new ReloadTargetPlatformTask());
		taskMap.put(Preference.REFRESH_PROJECTS, new RefreshProjectsTask());
		taskMap.put(Preference.CLEAN_ALL_PROJECTS, new CleanAllProjectsTask());
		taskMap.put(Preference.RUN_CONFIG_ADD_ALL_BUNDLES, new UpdateRunConfigurationTask());
		taskMap.put(Preference.RUN_CONFIG_DELETE_WORK_DIR, new DeleteWorkDirectoryTask());
	}
	
	@Override
	public void run() {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		List<Callable<Object>> callableTasks = TaskUtils.toCallableTasks(getEnabledTasks());
		
		try {
			executorService.invokeAll(callableTasks);
		} catch (InterruptedException e) {
			IStatus status = new Status(IStatus.ERROR, EclipseDanceActivator.PLUGIN_ID, "Environment reloading failed", e);
			StatusManager.getManager().handle(status);
		}
	}
	
	private List<Runnable> getEnabledTasks() {
		List<Runnable> result = new ArrayList<>();
		PreferenceItemRegistry preferenceRegistry = PreferenceItemRegistry.getInstance();
		for (IPreferenceItem<?> preferenceItem : preferenceRegistry.getPreferences()) {
			if (preferenceItem instanceof PreferenceCheckboxItem && preferenceItem.isEnabled()) {
				result.add(taskMap.get(Preference.valueOf(preferenceItem.getName())));
			}
		}
		return result;
	}	
}

package com.orchestral.odt.eclipse.dance.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public final class TaskUtils {
	
	private TaskUtils() {}
	
	public static List<Callable<Object>> toCallableTasks(List<Runnable> runnableTasks) {
		List<Callable<Object>> callableTasks = new ArrayList<Callable<Object>>(runnableTasks.size());
		for (Runnable runnable : runnableTasks) {
			Callable<Object> callable = Executors.callable(runnable);
			callableTasks.add(callable);
		}
		return callableTasks;
	}
}

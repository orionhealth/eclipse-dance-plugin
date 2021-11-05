package com.orchestral.odt.eclipse.dance.tasks;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.ui.statushandlers.StatusManager;

import com.orchestral.odt.eclipse.dance.EclipseDanceActivator;
import com.orchestral.odt.eclipse.dance.preferences.PreferenceUtils;

public class DeleteWorkDirectoryTask implements Runnable {
	
	private static final String WORKING_DIR = "org.eclipse.jdt.launching.WORKING_DIRECTORY";
	
	@Override
	public void run() {
		try {
			ILaunchConfiguration launchConfiguration = PreferenceUtils.getOsgiLaunchConfiguration();
			
			String path = (String) launchConfiguration.getAttributes().get(WORKING_DIR);
			Path directory = Paths.get(path);
			
			Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}				
			});
		} catch (CoreException e) {
			StatusManager.getManager().handle(e.getStatus());
		} catch (IOException e) {
			IStatus status = new Status(Status.ERROR, EclipseDanceActivator.PLUGIN_ID, e.getMessage());
			StatusManager.getManager().handle(status);
		}
	}
}

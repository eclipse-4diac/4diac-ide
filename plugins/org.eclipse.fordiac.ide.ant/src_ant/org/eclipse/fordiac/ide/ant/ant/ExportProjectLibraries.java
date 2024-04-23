package org.eclipse.fordiac.ide.ant.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;

public class ExportProjectLibraries extends Task {
	private static final String ANT_EXPORT_TASK_DIRECTORY_NAME = "exported_FBs"; //$NON-NLS-1$

	private String projectName;
	private String exportDirectory = ANT_EXPORT_TASK_DIRECTORY_NAME;
	private boolean exportCMakeList = false;
	private boolean preserveDirectoryStructure = false;

	public void setProjectName(final String value) {
		projectName = value;
	}

	public void setExportDirectory(final String value) {
		exportDirectory = value;
	}

	public void setExportCMakeList(final boolean value) {
		exportCMakeList = value;
	}

	public void setPreserveDirectoryStructure(final boolean preserveDirectoryStructure) {
		this.preserveDirectoryStructure = preserveDirectoryStructure;
	}

	@Override
	public void execute() throws BuildException {
		if (projectName == null) {
			throw new BuildException("Project name not specified!"); //$NON-NLS-1$
		}
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject fordiacProject = workspace.getRoot().getProject(projectName);

		final Manifest manifest = ManifestHelper.getContainerManifest(fordiacProject);
		if (manifest == null) {
			throw new BuildException("Project named '" + projectName + "' lacks a manifest file"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (manifest.getExports() == null) {
			throw new BuildException("No exports present in manifest file"); //$NON-NLS-1$
		}
		if (manifest.getExports().getLibrary().isEmpty()) {
			throw new BuildException("No libraries defined in manifest file"); //$NON-NLS-1$
		}

		manifest.getExports().getLibrary().forEach(lib -> {
			final ExportProjectLibrary libexport = new ExportProjectLibrary();
			libexport.setProjectName(projectName);
			libexport.setExportDirectory(exportDirectory);
			libexport.setExportCMakeList(exportCMakeList);
			libexport.setPreserveDirectoryStructure(preserveDirectoryStructure);
			libexport.setSymbolicName(lib.getSymbolicName());

			log("Exporting library: " + lib.getSymbolicName()); //$NON-NLS-1$
			libexport.execute();
		});
	}

}

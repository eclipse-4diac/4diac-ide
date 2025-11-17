/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *https://github.com/eclipse-4diac/4diac-ide/pull/655
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Mario Kastner - implementation of auto export
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.builder;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.export.IExportFilter;
import org.eclipse.fordiac.ide.export.Messages;
import org.eclipse.fordiac.ide.export.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.export.utils.ExportFilterUtil;
import org.eclipse.fordiac.ide.model.buildpath.Buildpath;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathAttributes;
import org.eclipse.fordiac.ide.model.buildpath.SourceFolder;
import org.eclipse.fordiac.ide.model.buildpath.util.BuildpathUtil;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.typelibrary.CMakeListsMarker;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public class ExportBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "org.eclipse.fordiac.ide.export.builder"; //$NON-NLS-1$
	private static final String FORTE_NG_FILTER_ID = "org.eclipse.fordiac.ide.export.exportFilter.forteNg"; //$NON-NLS-1$

	private static final Set<String> fileTypes = Set.of(TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING,
			TypeLibraryTags.DATA_TYPE_FILE_ENDING, TypeLibraryTags.FB_TYPE_FILE_ENDING,
			TypeLibraryTags.GLOBAL_CONST_FILE_ENDING, TypeLibraryTags.FC_TYPE_FILE_ENDING);

	private String outputDirectory;
	private IExportFilter filter;
	private MultiStatus status;

	@Override
	protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
			throws CoreException {

		if (!isExportEnabled()) {
			return new IProject[0];
		}

		performSetup();

		if (filter == null || !ExportFilterUtil.validateExportPath(outputDirectory, getProject())) {
			return new IProject[0];
		}

		final SubMonitor progress = SubMonitor.convert(monitor, 2);
		progress.setTaskName(Messages.ExportBuilder_Build);

		switch (kind) {
		case FULL_BUILD:
			fullBuild(progress.split(1));
			break;
		case INCREMENTAL_BUILD, AUTO_BUILD:
			final IResourceDelta root = getDelta(getProject());
			if (root != null) {
				incrementalBuild(root, progress.split(1));
			}
			break;
		default:
			break;
		}

		exportCmakeLists(progress.split(1));

		filter.getErrors().forEach(e -> status.add(new Status(IStatus.ERROR, getClass(), e)));

		if (status.matches(IStatus.ERROR)) {
			throw new CoreException(status);
		}
		return new IProject[0];
	}

	private void fullBuild(final SubMonitor monitor) throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, IProgressMonitor.UNKNOWN);

		final List<SourceFolder> buildPathFolders = getExportableFoldersFromBuildpath();
		for (final SourceFolder folder : buildPathFolders) {
			BuildpathUtil.acceptMatches(folder, getProject(), (IResourceVisitor) resource -> {
				if (!isExportCanceled(progress)) {
					if ((resource instanceof final IFile file) && isExportableFileType(file)) {
						exportElement(progress, file);
					} else if (resource instanceof IFolder || resource instanceof IProject) {
						return true;
					}
				}
				return false;
			});
		}
	}

	private void exportElement(final SubMonitor monitor, final IFile file) throws CoreException {
		try {
			if (!hasRelevantErrorMarker(file)) {
				monitor.subTask(MessageFormat.format(Messages.FordiacExporter_ExportingType, file.getName()));
				filter.export(file, getProject().getLocation().append(new Path(outputDirectory)).toString(), true);
				monitor.split(1);
			}
		} catch (final ExportException e) {
			status.add(new Status(IStatus.ERROR, getClass(), e.getMessage()));
		}
	}

	@Override
	protected void clean(final IProgressMonitor monitor) throws CoreException {
		if (!isExportEnabled()) {
			return;
		}

		performSetup();

		final SubMonitor progress = SubMonitor.convert(monitor, 1);
		progress.setTaskName(Messages.ExportBuilder_Clean);

		if (ExportFilterUtil.validateExportPath(outputDirectory, getProject())) {
			final IFolder folder = getProject().getFolder(outputDirectory);
			if (folder.exists()) {
				folder.delete(true, progress.split(1));
			}
		}
	}

	private void performSetup() {
		this.outputDirectory = getProjectPreferenceNode().get(PreferenceConstants.OUTPUT_FOLDER,
				PreferenceConstants.DEFAULT_OUTPUT_FOLDER_NAME);
		final String exportFilterID = getProjectPreferenceNode().get(PreferenceConstants.EXPORT_FILTER_ID, ""); //$NON-NLS-1$
		final Optional<IConfigurationElement> filterConfig = ExportFilterUtil.getExportFilter(exportFilterID);
		this.filter = filterConfig.isPresent() ? ExportFilterUtil.createExportFilter(filterConfig) : null;
		this.status = new MultiStatus(getClass(), IStatus.OK, "Export Builder Status"); //$NON-NLS-1$

	}

	private void incrementalBuild(final IResourceDelta rootDelta, final SubMonitor monitor) throws CoreException {
		for (final IResourceDelta delta : rootDelta
				.getAffectedChildren(IResourceDelta.CONTENT | IResourceDelta.CHANGED)) {
			if (!isExportCanceled(monitor)) {
				if ((delta.getResource() instanceof final IFile file) && isExportable(file)) {
					exportElement(monitor, file);
				} else if (delta.getResource() instanceof IFolder) {
					incrementalBuild(delta, monitor);
				}
			}
		}
	}

	private List<SourceFolder> getExportableFoldersFromBuildpath() {
		return getBuildpath().getSourceFolders().stream().filter(ExportBuilder::getExportAttributeValue).toList();
	}

	private boolean isExportable(final IFile file) {
		if (isExportableFileType(file)) {
			final Optional<SourceFolder> sourceFolder = BuildpathUtil.findSourceFolder(getBuildpath(), file);
			return sourceFolder.isPresent() && getExportAttributeValue(sourceFolder.get());
		}
		return false;
	}

	private static boolean isExportableFileType(final IFile file) {
		return fileTypes.stream().anyMatch(type -> type.equalsIgnoreCase(file.getFileExtension()));
	}

	private IEclipsePreferences getProjectPreferenceNode() {
		final ProjectScope projectScope = new ProjectScope(getProject());
		return projectScope.getNode(PreferenceConstants.EXPORT_PREFERENCES_ID);
	}

	private boolean isExportEnabled() {
		return getProjectPreferenceNode().getBoolean(PreferenceConstants.ENABLE_TYPE_EXPORT, false);
	}

	private Buildpath getBuildpath() {
		return TypeLibraryManager.INSTANCE.getTypeLibrary(getProject()).getBuildpath();
	}

	private static boolean getExportAttributeValue(final SourceFolder folder) {
		final String attributeValue = BuildpathAttributes.getAttributeValue(folder.getAttributes(),
				BuildpathAttributes.EXPORT);
		return !attributeValue.isEmpty() && Boolean.parseBoolean(attributeValue);
	}

	private void exportCmakeLists(final IProgressMonitor monitor) {
		if (getProjectPreferenceNode().get(PreferenceConstants.EXPORT_FILTER_ID, "").equals(FORTE_NG_FILTER_ID) //$NON-NLS-1$
				&& !isExportCanceled(monitor)) {
			final IPath location = getProject().getLocation().append(new Path(outputDirectory));
			final CMakeListsMarker marker = new CMakeListsMarker(getProject(), location.toPath());
			monitor.subTask(MessageFormat.format(Messages.FordiacExporter_ExportingType, marker.getName()));
			try {
				filter.export(null, location.toString(), true, marker);
				monitor.worked(1);
			} catch (final ExportException e) {
				status.add(new Status(IStatus.ERROR, getClass(), e.getMessage()));
			}
		}
	}

	private static boolean hasRelevantErrorMarker(final IFile file) throws CoreException {
		for (final IMarker marker : file.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE)) {
			if (FordiacErrorMarker.isModelMarkerType(marker.getType())) {
				return true;
			}
		}
		return false;
	}

	private boolean isExportCanceled(final IProgressMonitor monitor) {
		return isInterrupted() || monitor.isCanceled();
	}
}

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.fordiac.ide.export.AbstractExporter;
import org.eclipse.fordiac.ide.export.IExportFilter;
import org.eclipse.fordiac.ide.export.Messages;
import org.eclipse.fordiac.ide.export.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.export.utils.ExportFilterUtil;
import org.eclipse.fordiac.ide.model.buildpath.Buildpath;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathAttributes;
import org.eclipse.fordiac.ide.model.buildpath.SourceFolder;
import org.eclipse.fordiac.ide.model.buildpath.util.BuildpathUtil;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class ExportBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "org.eclipse.fordiac.ide.export.builder"; //$NON-NLS-1$
	private static final String FORTE_NG_FILTER_ID = "org.eclipse.fordiac.ide.export.exportFilter.forteNg"; //$NON-NLS-1$

	private static final Set<String> fileTypes = Set.of("fbt", "adp", "dtp", "gcf"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	private boolean enableAutoExport;
	private String outputDirectory;
	private Optional<IConfigurationElement> exportFilterConfiguration;
	private Exporter exporter;
	List<IFile> exportees = new ArrayList<>();

	@Override
	protected void startupOnInitialize() {
		createOutputFolder();
		initializePreferences();
		addPreferenceListener();
		super.startupOnInitialize();
	}

	@Override
	protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
			throws CoreException {

		final IResourceDelta root = getDelta(getProject());
		if (!enableAutoExport || exporter == null || root == null) {
			return new IProject[0];
		}

		exportees.clear();
		addResourceDeltas(exportees, root);

		if (!exportees.isEmpty()) {
			exporter.exportElements(monitor, exportees);
		}

		return new IProject[0];
	}

	@Override
	protected void clean(final IProgressMonitor monitor) throws CoreException {
		if (outputDirectory == null || outputDirectory.isEmpty()) {
			return;
		}
		final IFolder folder = getProject().getFolder(outputDirectory);
		if (folder.exists()) {
			folder.accept(resource -> {
				if (resource instanceof final IFolder root && root.equals(folder)) {
					// do not delete root folder
					return true;
				}
				resource.delete(enableAutoExport, monitor);
				return true;
			});
		}
		super.clean(monitor);
	}

	private void initializePreferences() {
		this.enableAutoExport = getProjectPreferenceNode().getBoolean(PreferenceConstants.ENABLE_TYPE_EXPORT, false);

		if (!enableAutoExport) {
			return;
		}

		this.outputDirectory = getProjectPreferenceNode().get(PreferenceConstants.OUTPUT_FOLDER,
				PreferenceConstants.DEFAULT_OUTPUT_FOLDER_NAME);
		final String exportFilterID = getProjectPreferenceNode().get(PreferenceConstants.EXPORT_FILTER_ID, ""); //$NON-NLS-1$
		this.exportFilterConfiguration = ExportFilterUtil.getExportFilter(exportFilterID);
		updateExporter();
	}

	private void updateExporter() {
		if ((exportFilterConfiguration.isPresent() && !outputDirectory.isEmpty())) {
			final IFolder destinationDirectory = getProject().getFolder(outputDirectory);
			if (exportFilterConfiguration.get().getAttribute(ExportFilterUtil.FILTER_ID).equals(FORTE_NG_FILTER_ID)
					&& destinationDirectory.exists()) {
				// enable export of CmakeListsFile only for forte_ng
				this.exporter = new Exporter(exportFilterConfiguration.get(),
						destinationDirectory.getLocation().toOSString(), true, true);
			} else {
				this.exporter = new Exporter(exportFilterConfiguration.get(),
						destinationDirectory.getLocation().toOSString(), true, false);
			}
		}
	}

	private void addPreferenceListener() {
		getProjectPreferenceNode().addPreferenceChangeListener(evt -> {
			switch (evt.getKey()) {
			case PreferenceConstants.ENABLE_TYPE_EXPORT:
				this.enableAutoExport = getProjectPreferenceNode().getBoolean(PreferenceConstants.ENABLE_TYPE_EXPORT,
						false);
				break;
			case PreferenceConstants.OUTPUT_FOLDER:
				this.outputDirectory = getProjectPreferenceNode().get(PreferenceConstants.OUTPUT_FOLDER, ""); //$NON-NLS-1$
				break;
			case PreferenceConstants.EXPORT_FILTER_ID:
				this.exportFilterConfiguration = ExportFilterUtil
						.getExportFilter(getProjectPreferenceNode().get(PreferenceConstants.EXPORT_FILTER_ID, "")); //$NON-NLS-1$
				break;
			default:
				// do nothing here a preference has been change which is not of our interest
				break;
			}
			updateExporter();
		});
	}

	private void addResourceDeltas(final List<IFile> exportees, final IResourceDelta deltas) {
		for (final IResourceDelta delta : deltas.getAffectedChildren(IResourceDelta.CONTENT | IResourceDelta.CHANGED)) {
			if ((delta.getResource() instanceof final IFile file) && isExportable(file)) {
				exportees.add(file);
			} else if (delta.getResource() instanceof IFolder) {
				addResourceDeltas(exportees, delta);
			}
		}

	}

	private boolean isExportable(final IFile file) {
		if (fileTypes.contains(file.getFileExtension())) {
			final Buildpath buildpath = BuildpathUtil.loadBuildpath(getProject());
			final Optional<SourceFolder> sourceFolder = BuildpathUtil.findSourceFolder(buildpath, file);
			return sourceFolder.isPresent() && getExportAttributeValue(sourceFolder.get());
		}
		return false;
	}

	private IEclipsePreferences getProjectPreferenceNode() {
		final ProjectScope projectScope = new ProjectScope(getProject());
		return projectScope.getNode(PreferenceConstants.EXPORT_PREFERENCES_ID);
	}

	private static boolean getExportAttributeValue(final SourceFolder folder) {
		final String attributeValue = BuildpathAttributes.getAttributeValue(folder.getAttributes(),
				BuildpathAttributes.EXPORT);
		return !attributeValue.isEmpty() && Boolean.parseBoolean(attributeValue);
	}

	private void createOutputFolder() {
		final IFolder folder = getProject().getFolder(PreferenceConstants.DEFAULT_OUTPUT_FOLDER_NAME);
		if (!folder.exists()) {
			try {
				folder.create(true, true, new NullProgressMonitor());
			} catch (final CoreException e) {
				FordiacLogHelper.logError(MessageFormat.format(Messages.ExportBuilder_CannotCreateDirectory,
						PreferenceConstants.DEFAULT_OUTPUT_FOLDER_NAME));
			}
		}

		try {
			folder.refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());
		} catch (final CoreException e) {
			e.printStackTrace();
		}
	}

	private class Exporter extends AbstractExporter {

		protected Exporter(final IConfigurationElement filterConfig, final String outputDirectory,
				final boolean overwriteWithoutWarning, final boolean enableCMakeLists) {
			super(filterConfig, outputDirectory, overwriteWithoutWarning, enableCMakeLists);
		}

		@Override
		protected void showErrorWarningSummary(final IExportFilter filter) {
			filter.getErrors().stream().forEach(FordiacLogHelper::logError);
		}

		@Override
		protected void processError(final String errorMessage) {
			FordiacLogHelper.logError(errorMessage);
		}

		@Override
		protected boolean exportIsCanceled(final IProgressMonitor monitor) {
			return super.exportIsCanceled(monitor) || isInterrupted();
		}

	}
}

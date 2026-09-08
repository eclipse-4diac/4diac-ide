/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial API and implementation and/or initial documentation (copy of Export Wizard)
 *******************************************************************************/

package org.eclipse.fordiac.ide.export;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.export.utils.ExportFilterUtil;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.CMakeListsMarker;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;

public class Exporter {

	private final String outputDirectory;
	private final IConfigurationElement filterConfig;
	private boolean overwriteWithoutWarning;
	private boolean enableCMakeLists;
	private final IExportFilter filter;

	public Exporter(final IConfigurationElement filterConfig, final String outputDirectory,
			final boolean overwriteWithoutWarning, final boolean enableCMakeLists) {
		this.filterConfig = filterConfig;
		this.outputDirectory = outputDirectory;
		this.overwriteWithoutWarning = overwriteWithoutWarning;
		this.enableCMakeLists = enableCMakeLists;
		this.filter = ExportFilterUtil.createExportFilter(Optional.of(filterConfig));

	}

	public void exportElements(final IProgressMonitor monitor, final List<IFile> exportees) {
		final SubMonitor progress = SubMonitor.convert(monitor,
				MessageFormat.format(Messages.FordiacExporter_ExportingSelectedTypesUsingExporter,
						filterConfig.getAttribute("name")), //$NON-NLS-1$
				exportees.size());

		if (filter == null) {
			return;
		}

		for (final IFile file : exportees) {
			try {
				exportElement(progress.split(1), filter, file, null);
			} catch (final ExportException.OverwriteAll e) {
				overwriteWithoutWarning = true;
			} catch (final ExportException.CancelAll e) {
				enableCMakeLists = false;
				filter.getWarnings().add(Messages.FordiacExporter_EXPORT_CANCELED);
				break;
			} catch (final ExportException.UserInteraction e) {
				// noop
			}

		}

		if (enableCMakeLists) {
			try {
				exportElement(progress.split(1), filter, null,
						new CMakeListsMarker(exportees.getFirst().getProject(), Path.of(outputDirectory)));
			} catch (final ExportException.UserInteraction e) {
				// noop
			}
		}
	}

	public IExportFilter getExportFilter() {
		return filter;
	}

	private void exportElement(final SubMonitor monitor, final IExportFilter filter, final IFile file,
			final EObject source) throws ExportException.UserInteraction {
		try {
			if (source instanceof CMakeListsMarker) {
				monitor.subTask(Messages.FordiacExporter_ExportingCMakeLists);
				filter.export(null, outputDirectory, overwriteWithoutWarning, source);
			} else {
				monitor.subTask(MessageFormat.format(Messages.FordiacExporter_ExportingType,
						getExportElementName(source, file)));
				filter.export(file, outputDirectory, overwriteWithoutWarning, source);
			}
		} catch (final ExportException.UserInteraction e) {
			throw (e);
		} catch (final ExportException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	private static String getExportElementName(final EObject element, final IFile file) {
		String name = "anonymous"; //$NON-NLS-1$
		if (element instanceof final INamedElement ne) {
			name = ne.getName();
		} else if (file != null) {
			name = file.getFullPath().removeFileExtension().lastSegment();
		}
		return name;
	}
}

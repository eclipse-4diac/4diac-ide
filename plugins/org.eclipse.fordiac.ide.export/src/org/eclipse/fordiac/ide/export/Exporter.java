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
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.export.utils.ExportFilterUtil;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.CMakeListsMarker;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

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
		monitor.beginTask(MessageFormat.format(Messages.FordiacExporter_ExportingSelectedTypesUsingExporter,
				filterConfig.getAttribute("name")), exportees.size() + 1); //$NON-NLS-1$

		if (null != filter) {
			for (final IFile file : exportees) {
				if (!monitor.isCanceled()) {
					try {
						exportElement(monitor, filter, file, null);
					} catch (final ExportException.OverwriteAll e) {
						overwriteWithoutWarning = true;
					} catch (final ExportException.CancelAll e) {
						enableCMakeLists = false;
						filter.getWarnings().add(Messages.FordiacExporter_EXPORT_CANCELED);
						break;
					} catch (final ExportException.UserInteraction e) {
						// noop
					}
					monitor.worked(1);
				}
			}

			if (enableCMakeLists && !monitor.isCanceled()) {
				try {
					exportElement(monitor, filter, null,
							new CMakeListsMarker(exportees.getFirst().getProject(), Path.of(outputDirectory)));
				} catch (final ExportException.UserInteraction e) {
					// noop
				}
				monitor.worked(1);
			}
			monitor.worked(1);
			if (monitor.isCanceled()) {
				filter.getErrors().add(Messages.FordiacExporter_EXPORT_CANCELED);
				throw new OperationCanceledException();
			}
		}
		monitor.done();
	}

	public IExportFilter getExportFilter() {
		return filter;
	}

	protected void exportElement(final IProgressMonitor monitor, final IExportFilter filter, final IFile file,
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
			processError(Messages.FordiacExporter_ERROR + e.getMessage());
		}
	}

	protected static void processError(final String errorMessage) {
		FordiacLogHelper.logError(errorMessage);
		final MessageBox msg = new MessageBox(Display.getDefault().getActiveShell());
		msg.setMessage(errorMessage);
		msg.open();
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

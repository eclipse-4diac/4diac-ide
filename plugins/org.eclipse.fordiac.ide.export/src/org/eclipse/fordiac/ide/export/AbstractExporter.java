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

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.CMakeListsMarker;
import org.eclipse.swt.widgets.Display;

public abstract class AbstractExporter {
	private final String outputDirectory;
	private final IConfigurationElement filterConfig;
	private boolean overwriteWithoutWarning;
	private boolean enableCMakeLists;
	private final IExportFilter filter;

	public void exportElements(final IProgressMonitor monitor, final List<IFile> exportees) {
		monitor.beginTask(MessageFormat.format(Messages.FordiacExporter_ExportingSelectedTypesUsingExporter,
				filterConfig.getAttribute("name")), exportees.size() + 1); //$NON-NLS-1$

		if (null != filter) {
			for (final IFile file : exportees) {
				if (!exportIsCanceled(monitor)) {
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
					exportElement(monitor, filter, null, new CMakeListsMarker());
				} catch (final ExportException.UserInteraction e) {
					// noop
				}
				monitor.worked(1);
			}
			monitor.worked(1);
			if (exportIsCanceled(monitor)) {
				filter.getErrors().add(Messages.FordiacExporter_EXPORT_CANCELED);
				throw new OperationCanceledException();
			}
			Display.getDefault().asyncExec(() -> showErrorWarningSummary(filter));
		}
		monitor.done();
	}

	protected AbstractExporter(final IConfigurationElement filterConfig, final String outputDirectory,
			final boolean overwriteWithoutWarning, final boolean enableCMakeLists) {
		this.filterConfig = filterConfig;
		this.outputDirectory = outputDirectory;
		this.overwriteWithoutWarning = overwriteWithoutWarning;
		this.enableCMakeLists = enableCMakeLists;
		this.filter = createExportFilter();
	}

	@SuppressWarnings("static-method")
	protected boolean exportIsCanceled(final IProgressMonitor monitor) {
		return monitor.isCanceled();
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

	protected IExportFilter createExportFilter() {
		IExportFilter exportFilter = null;
		try {
			exportFilter = (IExportFilter) filterConfig.createExecutableExtension("class"); //$NON-NLS-1$
		} catch (final CoreException e) {
			processError(Messages.FordiacExporter_ERROR + e.getMessage());
		}
		return exportFilter;
	}

	protected abstract void showErrorWarningSummary(final IExportFilter filter);

	protected abstract void processError(String errorMessage);

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

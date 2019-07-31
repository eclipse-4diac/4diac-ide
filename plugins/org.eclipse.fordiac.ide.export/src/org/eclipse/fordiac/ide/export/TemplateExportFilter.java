/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

public abstract class TemplateExportFilter extends ExportFilter {

	public TemplateExportFilter() {
	}

	@Override
	public final void export(IFile typeFile, String destination, boolean forceOverwrite) throws ExportException {
		this.export(typeFile, destination, forceOverwrite, null);
	}

	@Override
	public void export(IFile typeFile, String destination, boolean forceOverwrite, LibraryElement type)
			throws ExportException {
		try {
			final Path destinationPath = Paths.get(destination);
			final Set<? extends IExportTemplate> templates = this.getTemplates(type);
			for (final IExportTemplate template : templates) {
				try {
					final CharSequence content = template.generate();
					final byte[] data = content.toString().getBytes();
					getErrors().addAll(template.getErrors());
					getWarnings().addAll(template.getWarnings());
					getInfos().addAll(template.getInfos());
					final Path templatePath = destinationPath.resolve(template.getPath());
					Files.write(templatePath, data);
				} catch (final Exception t) {
					Activator.getDefault().getLog().log(
							new Status(IStatus.ERROR, Activator.PLUGIN_ID, 1, "Error during template generation", t));
					this.getErrors().add(t.getMessage() != null ? t.getMessage() : "Error during template generation");
				}
			}
		} catch (final Exception t) {
			Activator.getDefault().getLog()
					.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, 1, "Error during template generation", t));
			this.getErrors().add(t.getMessage() != null ? t.getMessage() : "Error during template generation");
		}
	}

	protected abstract Set<? extends IExportTemplate> getTemplates(LibraryElement type);

}

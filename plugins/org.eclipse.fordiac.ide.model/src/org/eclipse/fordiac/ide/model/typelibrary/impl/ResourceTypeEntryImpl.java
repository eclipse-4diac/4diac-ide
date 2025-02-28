/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 *    Alois Zoitl  - turned the Palette model into POJOs
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.RESImporter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.typelibrary.ResourceTypeEntry;

public class ResourceTypeEntryImpl extends AbstractCheckedTypeEntryImpl<ResourceType> implements ResourceTypeEntry {

	public ResourceTypeEntryImpl() {
		super(ResourceType.class);
	}

	@Override
	public void save(final LibraryElement toSave, final IProgressMonitor monitor) throws CoreException {
		// currently we can not save resources, but we also have no editor for it

		// Read the existing XML file
		final Path filePath = getFile().getLocation().toFile().toPath();
		try {
			// Read the entire file as a string
			String xmlContent = new String(Files.readAllBytes(filePath));

			// Update the Name attribute using a regular expression
			xmlContent = xmlContent.replaceFirst("(<ResourceType[^>]* Name=\")[^\"]*(\"[^>]*>)",
					"$1" + toSave.getName() + "$2");

			// Write the updated XML back to the file
			Files.write(filePath, xmlContent.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		} catch (final Exception e) {
			throw new CoreException(
					new Status(IStatus.ERROR, "org.eclipse.fordiac.ide.model", "Error saving LibraryElement", e));
		}
	}

	@Override
	protected CommonElementImporter getImporter() {
		return new RESImporter(getFile());
	}

	@Override
	protected AbstractTypeExporter getTypeExporter(final ResourceType type) {
		// currently we can not save resources, but we also have no editor for it
		return null;
	}

	@Override
	public EClass getTypeEClass() {
		return LibraryElementPackage.Literals.RESOURCE_TYPE;
	}
}

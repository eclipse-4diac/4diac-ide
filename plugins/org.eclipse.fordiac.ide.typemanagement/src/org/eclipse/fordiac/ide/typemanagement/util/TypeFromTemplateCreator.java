/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.DataTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.ADPImporter;
import org.eclipse.fordiac.ide.model.dataimport.DEVImporter;
import org.eclipse.fordiac.ide.model.dataimport.DataTypeImporter;
import org.eclipse.fordiac.ide.model.dataimport.FBTImporter;
import org.eclipse.fordiac.ide.model.dataimport.RESImporter;
import org.eclipse.fordiac.ide.model.dataimport.SEGImporter;
import org.eclipse.fordiac.ide.model.dataimport.SubAppTImporter;
import org.eclipse.fordiac.ide.model.dataimport.TypeImporter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.typemanagement.preferences.TypeManagementPreferencesHelper;

public class TypeFromTemplateCreator {

	private final IFile targetTypeFile;
	private final File typeTemplate;

	public TypeFromTemplateCreator(final IFile targetTypeFile, final File typeTemplate) {
		this.targetTypeFile = targetTypeFile;
		this.typeTemplate = typeTemplate;
	}

	public PaletteEntry createTypeFromTemplate() {
		final PaletteEntry entry = TypeLibrary.getTypeLibrary(targetTypeFile.getProject())
				.createPaletteEntry(targetTypeFile);

		final TypeImporter importer = getTypeImporter(entry);
		if (importer != null) {
			importer.loadElement();
			final LibraryElement type = importer.getElement();
			type.setName(TypeEntry.getTypeNameFromFile(targetTypeFile));
			setupIdentifcationAndVersionInfo(type);
			performTypeSpecificSetup(type);
			entry.setLastModificationTimestamp(targetTypeFile.getModificationStamp());
			entry.setType(type);
			AbstractTypeExporter.saveType(entry);
			return entry;
		}
		return null;
	}

	protected void performTypeSpecificSetup(final LibraryElement type) {
		// hook for subclasses to perform any type specific setup, e.g., saveassubapptype -> setup interface and network
	}

	private TypeImporter getTypeImporter(final PaletteEntry entry) {
		if (entry instanceof AdapterTypePaletteEntry) {
			return new ADPImporter(entry.getFile()) {
				@Override
				protected InputStream getInputStream() throws IOException {
					return Files.newInputStream(typeTemplate.toPath());
				}
			};
		} else if (entry instanceof DataTypePaletteEntry) {
			return new DataTypeImporter(entry.getFile()) {
				@Override
				protected InputStream getInputStream() throws IOException {
					return Files.newInputStream(typeTemplate.toPath());
				}
			};
		} else if (entry instanceof DeviceTypePaletteEntry) {
			return new DEVImporter(entry.getFile()) {
				@Override
				protected InputStream getInputStream() throws IOException {
					return Files.newInputStream(typeTemplate.toPath());
				}
			};
		} else if (entry instanceof FBTypePaletteEntry) {
			return new FBTImporter(entry.getFile()) {
				@Override
				protected InputStream getInputStream() throws IOException {
					return Files.newInputStream(typeTemplate.toPath());
				}
			};
		} else if (entry instanceof ResourceTypeEntry) {
			return new RESImporter(entry.getFile()) {
				@Override
				protected InputStream getInputStream() throws IOException {
					return Files.newInputStream(typeTemplate.toPath());
				}
			};
		} else if (entry instanceof SegmentTypePaletteEntry) {
			return new SEGImporter(entry.getFile()) {
				@Override
				protected InputStream getInputStream() throws IOException {
					return Files.newInputStream(typeTemplate.toPath());
				}
			};
		} else if (entry instanceof SubApplicationTypePaletteEntry) {
			return new SubAppTImporter(entry.getFile()) {
				@Override
				protected InputStream getInputStream() throws IOException {
					return Files.newInputStream(typeTemplate.toPath());
				}
			};
		}
		return null;
	}

	private static void setupIdentifcationAndVersionInfo(final LibraryElement type) {
		TypeManagementPreferencesHelper.setupIdentification(type);
		TypeManagementPreferencesHelper.setupVersionInfo(type);
	}

}

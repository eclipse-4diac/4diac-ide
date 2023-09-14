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
import org.eclipse.fordiac.ide.model.dataimport.ADPImporter;
import org.eclipse.fordiac.ide.model.dataimport.DEVImporter;
import org.eclipse.fordiac.ide.model.dataimport.DataTypeImporter;
import org.eclipse.fordiac.ide.model.dataimport.FBTImporter;
import org.eclipse.fordiac.ide.model.dataimport.FCTImporter;
import org.eclipse.fordiac.ide.model.dataimport.GlobalConstantsImporter;
import org.eclipse.fordiac.ide.model.dataimport.RESImporter;
import org.eclipse.fordiac.ide.model.dataimport.SEGImporter;
import org.eclipse.fordiac.ide.model.dataimport.SubAppTImporter;
import org.eclipse.fordiac.ide.model.dataimport.TypeImporter;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.TextFunctionBody;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.DeviceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FunctionFBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.GlobalConstantsEntry;
import org.eclipse.fordiac.ide.model.typelibrary.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SegmentTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.preferences.TypeManagementPreferencesHelper;

public class TypeFromTemplateCreator {

	private final IFile targetTypeFile;
	private final File typeTemplate;
	private final String packageName;

	public TypeFromTemplateCreator(final IFile targetTypeFile, final File typeTemplate) {
		this(targetTypeFile, typeTemplate, null);
	}

	public TypeFromTemplateCreator(final IFile targetTypeFile, final File typeTemplate, final String packageName) {
		this.targetTypeFile = targetTypeFile;
		this.typeTemplate = typeTemplate;
		this.packageName = packageName;
	}

	public TypeEntry createTypeFromTemplate() {
		final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeLibrary(targetTypeFile.getProject())
				.createTypeEntry(targetTypeFile);

		final TypeImporter importer = getTypeImporter(entry);
		if (importer != null) {
			importer.loadElement();
			final LibraryElement type = importer.getElement();
			type.setName(TypeEntry.getTypeNameFromFile(targetTypeFile));
			PackageNameHelper.setPackageName(type, packageName);
			setupIdentifcationAndVersionInfo(type);
			performTypeSpecificSetup(type);
			entry.setLastModificationTimestamp(targetTypeFile.getModificationStamp());
			entry.setType(type);
			entry.save();
			return entry;
		}
		return null;
	}

	@SuppressWarnings("static-method")  // allow subclasses to override
	protected void performTypeSpecificSetup(final LibraryElement type) {
		// hook for subclasses to perform any type specific setup, e.g., saveassubapptype -> setup interface and network
		if (type instanceof final AdapterType adpType) {
			// for adapter types we need to also set the name for the adapterfbtype entry
			adpType.getAdapterFBType().setName(type.getName());
		}
		if ((type instanceof final FunctionFBType functionFBType)
				&& (functionFBType.getBody() instanceof final TextFunctionBody body)) {
			// for function types we need to also set the name inside the body
			body.setText(body.getText().replace("Function", functionFBType.getName())); //$NON-NLS-1$
		}
	}

	private TypeImporter getTypeImporter(final TypeEntry entry) {
		if (entry instanceof AdapterTypeEntry) {
			return new ADPImporter(entry.getFile()) {
				@Override
				protected InputStream getInputStream() throws IOException {
					return Files.newInputStream(typeTemplate.toPath());
				}
			};
		}
		if (entry instanceof DataTypeEntry) {
			return new DataTypeImporter(entry.getFile()) {
				@Override
				protected InputStream getInputStream() throws IOException {
					return Files.newInputStream(typeTemplate.toPath());
				}
			};
		}
		if (entry instanceof DeviceTypeEntry) {
			return new DEVImporter(entry.getFile()) {
				@Override
				protected InputStream getInputStream() throws IOException {
					return Files.newInputStream(typeTemplate.toPath());
				}
			};
		}
		if (entry instanceof FunctionFBTypeEntry) {
			return new FCTImporter(entry.getFile()) {
				@Override
				protected InputStream getInputStream() throws IOException {
					return Files.newInputStream(typeTemplate.toPath());
				}
			};
		}
		if (entry instanceof FBTypeEntry) {
			return new FBTImporter(entry.getFile()) {
				@Override
				protected InputStream getInputStream() throws IOException {
					return Files.newInputStream(typeTemplate.toPath());
				}
			};
		}
		if (entry instanceof ResourceTypeEntry) {
			return new RESImporter(entry.getFile()) {
				@Override
				protected InputStream getInputStream() throws IOException {
					return Files.newInputStream(typeTemplate.toPath());
				}
			};
		}
		if (entry instanceof SegmentTypeEntry) {
			return new SEGImporter(entry.getFile()) {
				@Override
				protected InputStream getInputStream() throws IOException {
					return Files.newInputStream(typeTemplate.toPath());
				}
			};
		}
		if (entry instanceof SubAppTypeEntry) {
			return new SubAppTImporter(entry.getFile()) {
				@Override
				protected InputStream getInputStream() throws IOException {
					return Files.newInputStream(typeTemplate.toPath());
				}
			};
		}
		if (entry instanceof GlobalConstantsEntry) {
			return new GlobalConstantsImporter(entry.getFile()) {
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

/********************************************************************************
 * Copyright (c) 2021, 2024 Johannes Kepler University Austria,
 *                          Primetals Technologies Austria GmbH, Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Antonio Garmendia,Bianca Wiesmayr
 *    - initial API and implementation and/or initial documentation
 *  Fabio Gandolfi
 *    - adapted for emf compare
 *  Martin Jobst
 *    - gracefully handle exceptions during load or save
 *    - add function FB type
 *    - add global constants
 *  Fabio Gandolfi
 *    - load types via inputstream
 ********************************************************************************/

package org.eclipse.fordiac.ide.model.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.AdapterExporter;
import org.eclipse.fordiac.ide.model.dataexport.AttributeTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.DataTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.FCTExporter;
import org.eclipse.fordiac.ide.model.dataexport.FbtExporter;
import org.eclipse.fordiac.ide.model.dataexport.GlobalConstantsExporter;
import org.eclipse.fordiac.ide.model.dataexport.SubApplicationTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.SystemExporter;
import org.eclipse.fordiac.ide.model.dataimport.ADPImporter;
import org.eclipse.fordiac.ide.model.dataimport.AttributeTypeImporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.DEVImporter;
import org.eclipse.fordiac.ide.model.dataimport.DataTypeImporter;
import org.eclipse.fordiac.ide.model.dataimport.FBTImporter;
import org.eclipse.fordiac.ide.model.dataimport.FCTImporter;
import org.eclipse.fordiac.ide.model.dataimport.GlobalConstantsImporter;
import org.eclipse.fordiac.ide.model.dataimport.RESImporter;
import org.eclipse.fordiac.ide.model.dataimport.SEGImporter;
import org.eclipse.fordiac.ide.model.dataimport.SubAppTImporter;
import org.eclipse.fordiac.ide.model.dataimport.SystemImporter;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public class FordiacTypeResource extends ResourceImpl {

	public FordiacTypeResource(final URI uri) {
		super(uri);
	}

	@Override
	protected void doLoad(final InputStream inputStream, final Map<?, ?> options) throws IOException {
		IFile typeFile = null;
		if (uri.isPlatformResource()) {
			typeFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(this.uri.toPlatformString(true)));
		} else if (uri.isFile()) {
			typeFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toFileString()));
			if (!typeFile.exists()) {
				throw new IOException(
						MessageFormat.format(Messages.FordiacTypeResource_NotInWorkspace, uri.toString()));
			}
		} else {
			throw new IOException(
					MessageFormat.format(Messages.FordiacTypeResource_LoadFromUnsupportedURI, uri.toString()));
		}

		try {
			final CommonElementImporter importer = createImporterByFileExtensions(inputStream, typeFile);
			importer.loadElement();
			getErrors().addAll(importer.getErrors());
			getWarnings().addAll(importer.getWarnings());
			final LibraryElement element = importer.getElement();
			if (element != null) {
				final var typeEntryForFile = TypeLibraryManager.INSTANCE.getTypeEntryForFile(typeFile);
				if (typeEntryForFile != null) {
					element.setTypeEntry(typeEntryForFile);
				}
				getContents().add(element);
			}
		} catch (final TypeImportException e) {
			getErrors().add(new TypeImportDiagnostic(e.getMessage(), Messages.FordiacTypeResource_TypeImportError));
		} catch (final XMLStreamException e) {
			if (e.getLocation() != null) {
				getErrors().add(new TypeImportDiagnostic(e.getMessage(), Messages.FordiacTypeResource_XMLError,
						e.getLocation().getLineNumber()));
			} else {
				getErrors().add(new TypeImportDiagnostic(e.getMessage(), Messages.FordiacTypeResource_XMLError));
			}
		} catch (final IOException e) {
			throw e;
		} catch (final Exception e) {
			throw new IOWrappedException(e);
		}
	}

	@Override
	protected void doSave(final OutputStream outputStream, final Map<?, ?> options) throws IOException {
		if (!uri.isPlatformResource()) {
			throw new IOException(
					MessageFormat.format(Messages.FordiacTypeResource_SaveToUnsupportedURI, uri.toString()));
		}
		final AbstractTypeExporter exporter = getTypeExporter(getContents().get(0));
		try (InputStream inputStream = exporter.getFileContent()) {
			inputStream.transferTo(outputStream);
		}
	}

	private static AbstractTypeExporter getTypeExporter(final EObject content) throws IOException {
		return switch (content) {
		case final FunctionFBType funcType -> new FCTExporter(funcType);
		case final AdapterType adpType -> new AdapterExporter(adpType);
		case final SubAppType subAppType -> new SubApplicationTypeExporter(subAppType);
		case final FBType fbType -> new FbtExporter(fbType);
		case final AnyDerivedType dataType -> new DataTypeExporter(dataType);
		case final AutomationSystem system -> new SystemExporter(system);
		case final GlobalConstants globalConstants -> new GlobalConstantsExporter(globalConstants);
		case final AttributeDeclaration attributeType -> new AttributeTypeExporter(attributeType);
		default -> throw new IOException(
				MessageFormat.format(Messages.FordiacTypeResource_UnsupportedContent, content.toString()));
		};
	}

	@Override
	protected void doUnload() {
		getContents().clear();
	}

	private static CommonElementImporter createImporterByFileExtensions(final InputStream inputStream,
			final IFile typeFile) throws IOException {
		final TypeLibrary typeLib = TypeLibraryManager.INSTANCE.getTypeLibrary(typeFile.getProject());
		return switch (typeFile.getFileExtension().toUpperCase()) {
		case TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING -> new SystemImporter(inputStream, typeLib);
		case TypeLibraryTags.FB_TYPE_FILE_ENDING -> new FBTImporter(inputStream, typeLib);
		case TypeLibraryTags.FC_TYPE_FILE_ENDING -> new FCTImporter(inputStream, typeLib);
		case TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING -> new ADPImporter(inputStream, typeLib);
		case TypeLibraryTags.ATTRIBUTE_TYPE_FILE_ENDING -> new AttributeTypeImporter(inputStream, typeLib);
		case TypeLibraryTags.DATA_TYPE_FILE_ENDING -> new DataTypeImporter(inputStream, typeLib);
		case TypeLibraryTags.DEVICE_TYPE_FILE_ENDING -> new DEVImporter(inputStream, typeLib);
		case TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING -> new RESImporter(inputStream, typeLib);
		case TypeLibraryTags.SEGMENT_TYPE_FILE_ENDING -> new SEGImporter(inputStream, typeLib);
		case TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING -> new SubAppTImporter(inputStream, typeLib);
		case TypeLibraryTags.GLOBAL_CONST_FILE_ENDING -> new GlobalConstantsImporter(inputStream, typeLib);
		default ->
			throw new IOException(MessageFormat.format(Messages.FordiacTypeResource_UnsupportedFileType, typeFile));
		};
	}
}
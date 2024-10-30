/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor Gmbh, TU Wien ACIN, fortiss GmbH
 * 				 2018, 2020 Johannes Keppler University
 * 				 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *  Alois Zoitl - extracted this helper class from the CommonElementExporter
 *              - changed exporting the Saxx cursor api
 *  Alois Zoitl, Bianca Wiesmayr - extracted code to this common base class
 *  Benjamin Muttenthaler - extracted saveType to this base class, so it can be used by the DataTypeExporter too
 ********************************************************************************/

package org.eclipse.fordiac.ide.model.dataexport;

import static org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper.getArraySize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.stream.XMLStreamException;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FunctionFBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.GlobalConstantsEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public abstract class AbstractTypeExporter extends CommonElementExporter {
	private final LibraryElement type;

	protected AbstractTypeExporter(final LibraryElement type) {
		this.type = type;
	}

	protected AbstractTypeExporter(final CommonElementExporter parent) {
		super(parent);
		type = null;
	}

	public LibraryElement getType() {
		return type;
	}

	public InputStream getFileContent() {
		try {
			createXMLEntries();
			getWriter().writeCharacters(LINE_END);
			getWriter().writeEndDocument();
			getWriter().close();
			return new ByteBufferInputStream(getOutputStream().transferDataBuffers());
		} catch (final XMLStreamException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		} finally {
			try {
				getOutputStream().close();
			} catch (final IOException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}
		return null;
	}

	protected void createXMLEntries() throws XMLStreamException {
		createNamedElementEntry(getType(), getRootTag());
		addIdentification(getType());
		addVersionInfo(getType());
		createTypeSpecificXMLEntries();
		addEndElement();
	}

	// Save the model using the Outputstream
	public static void saveType(final TypeEntry entry, final OutputStream outputStream) {
		final AbstractTypeExporter exporter = getTypeExporter(entry);
		if (exporter != null) {

			try (InputStream inputStream = exporter.getFileContent()) {
				inputStream.transferTo(outputStream);
			} catch (final IOException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
			entry.setLastModificationTimestamp(entry.getFile().getModificationStamp());
		}
	}

	private static AbstractTypeExporter getTypeExporter(final TypeEntry entry) {
		if (entry instanceof final FunctionFBTypeEntry functionFBTypeEntry) {
			return new FCTExporter(functionFBTypeEntry.getTypeEditable());
		}
		if (entry instanceof final FBTypeEntry functionFBTypeEntry) {
			return new FbtExporter(functionFBTypeEntry.getTypeEditable());
		}
		if (entry instanceof final AdapterTypeEntry adapterTypeEntry) {
			return new AdapterExporter(adapterTypeEntry.getTypeEditable());
		}
		if (entry instanceof final SubAppTypeEntry subAppTypeEntry) {
			return new SubApplicationTypeExporter(subAppTypeEntry.getTypeEditable());
		}
		if (entry instanceof final DataTypeEntry dataTypeEntry) {
			return new DataTypeExporter(dataTypeEntry.getTypeEditable());
		}
		if (entry instanceof final SystemEntry systemEntry) {
			return new SystemExporter(systemEntry.getSystem());
		}
		if (entry instanceof final GlobalConstantsEntry globalConstantsEntry) {
			return new GlobalConstantsExporter(globalConstantsEntry.getTypeEditable());
		}
		if (entry instanceof final AttributeTypeEntry attributeTypeEntry) {
			return new AttributeTypeExporter(attributeTypeEntry.getTypeEditable());
		}
		return null;
	}

	protected abstract String getRootTag();

	protected abstract void createTypeSpecificXMLEntries() throws XMLStreamException;

	protected void addCompilerInfo(final CompilerInfo compilerInfo) throws XMLStreamException {
		if (null != compilerInfo) {
			addStartElement(LibraryElementTags.COMPILER_INFO_ELEMENT);
			if ((null != compilerInfo.getHeader()) && !"".equals(compilerInfo.getHeader())) { //$NON-NLS-1$
				getWriter().writeAttribute(LibraryElementTags.HEADER_ATTRIBUTE, compilerInfo.getHeader());
			}
			if ((null != compilerInfo.getClassdef()) && !"".equals(compilerInfo.getClassdef())) { //$NON-NLS-1$
				getWriter().writeAttribute(LibraryElementTags.CLASSDEF_ATTRIBUTE, compilerInfo.getClassdef());
			}
			if ((null != compilerInfo.getPackageName()) && !"".equals(compilerInfo.getPackageName())) { //$NON-NLS-1$
				getWriter().writeAttribute(LibraryElementTags.PACKAGE_NAME_ATTRIBUTE, compilerInfo.getPackageName());
			}
			for (final Import imp : compilerInfo.getImports()) {
				addImport(imp);
			}
			for (final org.eclipse.fordiac.ide.model.libraryElement.Compiler compiler : compilerInfo.getCompiler()) {
				addCompiler(compiler);
			}
			addEndElement();
		}
	}

	/**
	 * Adds the compiler.
	 *
	 * @param compiler the compiler
	 * @throws XMLStreamException
	 */
	private void addCompiler(final org.eclipse.fordiac.ide.model.libraryElement.Compiler compiler)
			throws XMLStreamException {
		addEmptyStartElement(LibraryElementTags.COMPILER_ELEMENT);
		getWriter().writeAttribute(LibraryElementTags.LANGUAGE_ATTRIBUTE,
				(null != compiler.getLanguage()) ? compiler.getLanguage().getName() : ""); //$NON-NLS-1$
		getWriter().writeAttribute(LibraryElementTags.VENDOR_ATTRIBUTE,
				(null != compiler.getVendor()) ? compiler.getVendor() : ""); //$NON-NLS-1$
		getWriter().writeAttribute(LibraryElementTags.PRODUCT_ATTRIBUTE,
				(null != compiler.getProduct()) ? compiler.getProduct() : ""); //$NON-NLS-1$
		getWriter().writeAttribute(LibraryElementTags.VERSION_ATTRIBUTE,
				(null != compiler.getVersion()) ? compiler.getVersion() : ""); //$NON-NLS-1$
	}

	/**
	 * Adds the import.
	 *
	 * @param imp the import
	 * @throws XMLStreamException
	 */
	private void addImport(final Import imp) throws XMLStreamException {
		addEmptyStartElement(LibraryElementTags.IMPORT_ELEMENT);
		getWriter().writeAttribute(LibraryElementTags.DECLARATION_ATTRIBUTE,
				(null != imp.getImportedNamespace()) ? imp.getImportedNamespace() : ""); //$NON-NLS-1$
	}

	/**
	 * Adds the variable.
	 *
	 * @param varDecl the var decl
	 * @throws XMLStreamException
	 */
	@SuppressWarnings("unlikely-arg-type")
	protected void addVarDeclaration(final VarDeclaration varDecl) throws XMLStreamException {
		final boolean hasAttributes = !varDecl.getAttributes().isEmpty()
				|| (varDecl.isInOutVar() && !varDecl.getInOutVarOpposite().getAttributes().isEmpty());
		final boolean hasOutAttributes = varDecl.getAttributes().stream().map(Attribute::getName).toList()
				.contains(LibraryElementTags.ELEMENT_INOUTVISIBLEOUT);
		if (hasAttributes) {
			addStartElement(LibraryElementTags.VAR_DECLARATION_ELEMENT);
		} else {
			addEmptyStartElement(LibraryElementTags.VAR_DECLARATION_ELEMENT);
		}

		addNameTypeCommentAttribute(varDecl, varDecl.getType());
		if (varDecl.isArray()) {
			getWriter().writeAttribute(LibraryElementTags.ARRAYSIZE_ATTRIBUTE, getArraySize(varDecl));
		}
		if ((null != varDecl.getValue()) && (!varDecl.getValue().getValue().isEmpty())) {
			getWriter().writeAttribute(LibraryElementTags.INITIALVALUE_ATTRIBUTE, varDecl.getValue().getValue());
		}

		if (hasAttributes) {
			if (varDecl.isInOutVar() && !varDecl.getInOutVarOpposite().isVisible() && !hasOutAttributes) {
				addAttributeElement(LibraryElementTags.ELEMENT_INOUTVISIBLEOUT, null, "false", null); //$NON-NLS-1$
			} else if (varDecl.isInOutVar() && varDecl.getInOutVarOpposite().isVisible() && hasOutAttributes) {
				varDecl.deleteAttribute(LibraryElementTags.ELEMENT_INOUTVISIBLEOUT);
			} else {
				addAttributes(varDecl.getAttributes());
			}
			addEndElement();
		}
	}

}
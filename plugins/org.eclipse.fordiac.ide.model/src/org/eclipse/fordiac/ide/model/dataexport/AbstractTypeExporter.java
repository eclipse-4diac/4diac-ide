/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor Gmbh, TU Wien ACIN, fortiss GmbH
 * 				 2018, 2020 Johannes Keppler University
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
 ********************************************************************************/

package org.eclipse.fordiac.ide.model.dataexport;

import javax.xml.stream.XMLStreamException;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public abstract class AbstractTypeExporter extends CommonElementExporter {
	private final LibraryElement type;

	protected AbstractTypeExporter(final LibraryElement type) {
		super();
		this.type = type;
	}

	protected AbstractTypeExporter(final CommonElementExporter parent) {
		super(parent);
		type = null;
	}

	protected LibraryElement getType() {
		return type;
	}

	protected void createXMLEntries() throws XMLStreamException {
		createNamedElementEntry(getType(), getRootTag());
		addIdentification(getType());
		addVersionInfo(getType());
		createTypeSpecificXMLEntries();
		addEndElement();
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
	 * Adds the variable.
	 *
	 * @param varDecl the var decl
	 * @throws XMLStreamException
	 */
	protected void addVarDeclaration(final VarDeclaration varDecl) throws XMLStreamException {
		addEmptyStartElement(LibraryElementTags.VAR_DECLARATION_ELEMENT);
		addNameTypeCommentAttribute(varDecl, varDecl.getType());
		if (varDecl.isArray()) {
			getWriter().writeAttribute(LibraryElementTags.ARRAYSIZE_ATTRIBUTE,
					Integer.toString(varDecl.getArraySize()));
		}
		if ((null != varDecl.getValue()) && (!varDecl.getValue().getValue().isEmpty())) {
			getWriter().writeAttribute(LibraryElementTags.INITIALVALUE_ATTRIBUTE, varDecl.getValue().getValue());
		}
	}

}

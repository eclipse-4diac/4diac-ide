/********************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2013, 2014 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Martijn Rooker, Gerhard Ebenhofer, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.CompilableType;
import org.eclipse.fordiac.ide.model.libraryElement.Compiler;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.Language;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class CompilableElementImporter {

	/**
	 * Parses the compiler info.
	 * 
	 * @param ctype the ctype
	 * @param node the node
	 * 
	 * @return the compiler info
	 * 
	 * @throws TypeImportException the FBT import exception
	 */
	public static CompilerInfo parseCompilerInfo(final CompilableType ctype,
			final Node node) throws TypeImportException {
		NamedNodeMap map = node.getAttributes();
		CompilerInfo compilerInfo = LibraryElementFactory.eINSTANCE
				.createCompilerInfo();
		ctype.setCompilerInfo(compilerInfo);
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node compiler = childNodes.item(i);
			if (compiler.getNodeName().equals(
					LibraryElementTags.COMPILER_ELEMENT)) {
				parseCompiler(compilerInfo, compiler);
			}
		}

		Node header = map.getNamedItem(LibraryElementTags.HEADER_ATTRIBUTE);
		if (header != null) {
			compilerInfo.setHeader(header.getNodeValue());
		}
		Node classdef = map.getNamedItem(LibraryElementTags.CLASSDEF_ATTRIBUTE);
		if (classdef != null) {
			compilerInfo.setClassdef(classdef.getNodeValue());
		}

		return compilerInfo;
	}

	/**
	 * Parses the compiler.
	 * 
	 * @param compilerInfo
	 *            the compiler info
	 * @param compiler
	 *            the compiler
	 * 
	 * @throws TypeImportException
	 *             the FBT import exception
	 */
	private static void parseCompiler(final CompilerInfo compilerInfo,
			final Node compiler) throws TypeImportException {
		NamedNodeMap map = compiler.getAttributes();
		Compiler comp = LibraryElementFactory.eINSTANCE.createCompiler();
		Node language = map.getNamedItem(LibraryElementTags.LANGUAGE_ATTRIBUTE);

		if (language != null) {
			if ("C".equalsIgnoreCase(language.getNodeValue())) { //$NON-NLS-1$
				comp.setLanguage(Language.C);
			} else if ("CPP".equalsIgnoreCase(language.getNodeValue())) { //$NON-NLS-1$
				comp.setLanguage(Language.CPP);
			} else if ("JAVA".equalsIgnoreCase(language.getNodeValue())) { //$NON-NLS-1$
				comp.setLanguage(Language.JAVA);
			} else if ("OTHER".equalsIgnoreCase(language.getNodeValue())) { //$NON-NLS-1$
				comp.setLanguage(Language.OTHER);
			} else {
				throw new TypeImportException(
						Messages.CompilableElementImporter_ERROR_UnsupportedLanguage);
			}
		} else {
			throw new TypeImportException(
					Messages.CompilableElementImporter_ERROR_LanguageNotDefined);
		}
		Node vendor = map.getNamedItem(LibraryElementTags.VENDOR_ATTRIBUTE);
		if (vendor != null) {
			comp.setVendor(vendor.getNodeValue());
		} else {
			throw new TypeImportException(
					Messages.CompilableElementImporter_ERROR_VendorNotSet);
		}
		Node product = map.getNamedItem(LibraryElementTags.PRODUCT_ATTRIBUTE);
		if (product != null) {
			comp.setProduct(product.getNodeValue());
		} else {
			throw new TypeImportException(
					Messages.CompilableElementImporter_ERROR_ProductNotSet);
		}
		Node version = map.getNamedItem(LibraryElementTags.VERSION_ATTRIBUTE);
		if (version != null) {
			comp.setVersion(version.getNodeValue());
		} else {
			throw new TypeImportException(
					Messages.CompilableElementImporter_ERROR_VersionNotSet);
		}
		compilerInfo.getCompiler().add(comp);
	}
	
	private CompilableElementImporter() {
		throw new UnsupportedOperationException("Utility class CompilableElementImporter should not be insantiated!");
	}
}

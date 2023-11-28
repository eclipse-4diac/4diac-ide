/********************************************************************************
 * Copyright (c) 2010, 2011, 2013, 2014, 2018  Profactor GmbH, TU Wien ACIN,
 * 										 fortiss GmbH
 * 				 2018, 2020 Johannes Kepler University, Linz
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *  Alois Zoitl - Changed XML parsing to Staxx cursor interface for improved
 *  			  parsing performance
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/**
 * Managing class for importing *.atp files
 *
 */

public class ADPImporter extends TypeImporter {

	private AdapterFBType adapterFBType;

	public ADPImporter(final IFile typeFile) {
		super(typeFile);
	}

	public ADPImporter(final InputStream inputStream, final TypeLibrary typeLibrary) {
		super(inputStream, typeLibrary);
	}

	@Override
	public void loadElement() throws IOException, XMLStreamException, TypeImportException {
		super.loadElement();
		// set adapterFB type to correctly set plug
		getElement().setAdapterFBType(adapterFBType);
	}

	@Override
	public AdapterType getElement() {
		return (AdapterType) super.getElement();
	}

	@Override
	public LibraryElement createRootModelElement() {
		final AdapterType newType = LibraryElementFactory.eINSTANCE.createAdapterType();
		adapterFBType = LibraryElementFactory.eINSTANCE.createAdapterFBType();
		adapterFBType.setService(LibraryElementFactory.eINSTANCE.createService());
		return newType;
	}

	@Override
	protected void readNameCommentAttributes(final INamedElement namedElement) throws TypeImportException {
		super.readNameCommentAttributes(namedElement);
		adapterFBType.setName(getElement().getName());
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.ADAPTER_TYPE;
	}

	@Override
	protected IChildHandler getBaseChildrenHandler() {
		final FBTImporter importer = new FBTImporter(this) {
			@Override
			public IChildHandler getBaseChildrenHandler() {
				return name -> {
					switch (name) {
					case LibraryElementTags.IDENTIFICATION_ELEMENT:
						parseIdentification(adapterFBType);
						break;
					case LibraryElementTags.VERSION_INFO_ELEMENT:
						parseVersionInfo(adapterFBType);
						break;
					case LibraryElementTags.COMPILER_INFO_ELEMENT:
						adapterFBType.setCompilerInfo(parseCompilerInfo());
						break;
					case LibraryElementTags.INTERFACE_LIST_ELEMENT:
						adapterFBType.setInterfaceList(parseInterfaceList(LibraryElementTags.INTERFACE_LIST_ELEMENT));
						break;
					case LibraryElementTags.SERVICE_ELEMENT:
						parseService(adapterFBType);
						break;
					case LibraryElementTags.ATTRIBUTE_ELEMENT:
						parseGenericAttributeNode(getElement());
						proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
						break;
					default:
						return false;
					}
					return true;
				};
			}
		};
		return importer.getBaseChildrenHandler();
	}

}

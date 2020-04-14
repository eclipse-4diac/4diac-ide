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

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/**
 * Managing class for importing *.atp files
 *
 */

public final class ADPImporter extends TypeImporter {

	private AdapterFBType adapterFBType;

	@Override
	public LibraryElement importType(IFile typeFile) throws TypeImportException {
		try (ImporterStreams streams = createInputStreams(typeFile.getContents())) {
			setType(createType());
			proceedToStartElementNamed(getStartElementName());
			readNameCommentAttributes(getType());

			FBTImporter importer = new FBTImporter(getReader(), TypeLibrary.getTypeLibrary(typeFile.getProject())) {
				@Override
				public IChildHandler getTypeChildrenHandler() {
					return name -> {
						switch (name) {
						case LibraryElementTags.IDENTIFICATION_ELEMENT:
							parseIdentification(adapterFBType);
							break;
						case LibraryElementTags.VERSION_INFO_ELEMENT:
							parseVersionInfo(adapterFBType);
							break;
						case LibraryElementTags.COMPILER_INFO_ELEMENT:
							parseCompilerInfo(adapterFBType);
							break;
						case LibraryElementTags.INTERFACE_LIST_ELEMENT:
							adapterFBType
									.setInterfaceList(parseInterfaceList(LibraryElementTags.INTERFACE_LIST_ELEMENT));
							break;
						case LibraryElementTags.SERVICE_ELEMENT:
							parseService(adapterFBType);
							break;
						default:
							return false;
						}
						return true;
					};
				}
			};
			processChildren(getStartElementName(), importer.getTypeChildrenHandler());
		} catch (TypeImportException e) {
			throw e;
		} catch (Exception e) {
			throw new TypeImportException(e.getMessage(), e);
		}
		return getType();
	}

	@Override
	protected LibraryElement createType() {
		AdapterType newType = LibraryElementFactory.eINSTANCE.createAdapterType();
		adapterFBType = LibraryElementFactory.eINSTANCE.createAdapterFBType();
		newType.setAdapterFBType(adapterFBType);
		adapterFBType.setAdapterType(newType);
		adapterFBType.setService(LibraryElementFactory.eINSTANCE.createService());
		return newType;
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.ADAPTER_TYPE;
	}

	@Override
	protected IChildHandler getTypeChildrenHandler() {
		return name -> false;
	}

}

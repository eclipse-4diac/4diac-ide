/*******************************************************************************
 * Copyright (c) 2016, 2020 fortiss GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *  			 - Changed XML parsing to Staxx cursor interface for improved
 *  			   parsing performance
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.text.MessageFormat;

import javax.xml.stream.XMLStreamException;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Palette.PaletteFactory;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.helpers.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerRef;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

class SubAppNetworkImporter extends FBNetworkImporter {

	public SubAppNetworkImporter(final CommonElementImporter importer) {
		super(importer);
	}

	public SubAppNetworkImporter(final CommonElementImporter importer, final InterfaceList interfaceList) {
		super(importer, LibraryElementFactory.eINSTANCE.createFBNetwork(), interfaceList);
	}

	protected SubAppNetworkImporter(final CommonElementImporter importer, final FBNetwork fbNetwork) {
		super(importer, fbNetwork);
	}

	@Override
	protected boolean handleFBNetworkChild(final String name) throws XMLStreamException, TypeImportException {
		if (LibraryElementTags.SUBAPP_ELEMENT.equals(name)) {
			parseSubApp();
			return true;
		}
		return super.handleFBNetworkChild(name);
	}

	private void parseSubApp() throws TypeImportException, XMLStreamException {
		final String type = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		final FBNetworkElement subApp = createSubapp(type);
		readNameCommentAttributes(subApp);
		getXandY(subApp);
		getFbNetwork().getNetworkElements().add(subApp);

		if (type == null) {
			parseUntypedSubapp((SubApp) subApp);
		} else {
			parseFBChildren(subApp, LibraryElementTags.SUBAPP_ELEMENT);
		}

		for (final VarDeclaration inVar : subApp.getInterface().getInputVars()) {
			if (null == inVar.getValue()) {
				inVar.setValue(LibraryElementFactory.eINSTANCE.createValue());
			}
		}

		fbNetworkElementMap.put(subApp.getName(), subApp);

		if ((null == subApp.getPaletteEntry() && type != null) || (subApp instanceof ErrorMarkerRef)) {
			final ErrorMarkerBuilder e = FordiacMarkerHelper.createErrorMarker(
					MessageFormat.format("Type ({0}) could not be loaded for Subapplication: {1}", type, //$NON-NLS-1$
							subApp.getName()),
					subApp, getLineNumber());
			errorMarkerAttributes.add(e);
			e.setErrorMarkerRef((ErrorMarkerRef) subApp);
		}

	}

	public FBNetworkElement createSubapp(final String type) {
		final FBNetworkElement subApp = LibraryElementFactory.eINSTANCE.createSubApp();
		if (type == null) {
			return subApp;
		}

		final SubApplicationTypePaletteEntry subEntry = getPalette().getSubAppTypeEntry(type);
		if (subEntry == null) {
			return FordiacMarkerHelper.createTypeErrorMarkerFB(type, getTypeLibrary(),
					LibraryElementFactory.eINSTANCE.createSubAppType().eClass(),
					PaletteFactory.eINSTANCE.createSubApplicationTypePaletteEntry().eClass());
		}
		subApp.setPaletteEntry(subEntry);
		subApp.setInterface(subEntry.getType().getInterfaceList().copy());
		return subApp;
	}


	private void parseUntypedSubapp(final SubApp subApp) throws TypeImportException, XMLStreamException {
		processChildren(LibraryElementTags.SUBAPP_ELEMENT, name -> {
			switch (name) {
			case LibraryElementTags.SUBAPPINTERFACE_LIST_ELEMENT:
				final SubAppTImporter interfaceImporter = new SubAppTImporter(this);
				subApp.setInterface(
						interfaceImporter.parseInterfaceList(LibraryElementTags.SUBAPPINTERFACE_LIST_ELEMENT));
				return true;
			case LibraryElementTags.SUBAPPNETWORK_ELEMENT:
				final SubAppNetworkImporter subAppImporter = new SubAppNetworkImporter(this, subApp.getInterface());
				subApp.setSubAppNetwork(subAppImporter.getFbNetwork());
				subAppImporter.parseFBNetwork(LibraryElementTags.SUBAPPNETWORK_ELEMENT);
				return true;
			case LibraryElementTags.PARAMETER_ELEMENT:
				parseParameter(subApp);
				return true;
			case LibraryElementTags.ATTRIBUTE_ELEMENT:
				parseGenericAttributeNode(subApp);
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				return true;
			default:
				return false;
			}
		});
	}

}

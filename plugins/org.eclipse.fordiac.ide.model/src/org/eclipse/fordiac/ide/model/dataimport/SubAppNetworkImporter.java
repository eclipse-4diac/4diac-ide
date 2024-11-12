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

import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;

class SubAppNetworkImporter extends FBNetworkImporter {

	public SubAppNetworkImporter(final CommonElementImporter importer) {
		super(importer);
	}

	public SubAppNetworkImporter(final CommonElementImporter importer, final InterfaceList interfaceList) {
		super(importer, LibraryElementFactory.eINSTANCE.createFBNetwork(), interfaceList);
	}

	protected SubAppNetworkImporter(final CommonElementImporter importer, final FBNetwork fbNetwork,
			final Map<String, FBNetworkElement> fbNetworkElementMap) {
		super(importer, fbNetwork, fbNetworkElementMap);
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
		final FBNetworkElement subApp;
		if (type == null) {
			subApp = LibraryElementFactory.eINSTANCE.createUntypedSubApp();
		} else {
			subApp = createTypedSubapp(type);
		}

		readNameCommentAttributes(subApp);
		getXandY(subApp);
		addFBNetworkElement(subApp);

		if (subApp instanceof final UntypedSubApp untypedSubApp) {
			parseUntypedSubapp(untypedSubApp);
		} else {
			parseFBChildren(subApp, LibraryElementTags.SUBAPP_ELEMENT);
		}

		for (final VarDeclaration inVar : subApp.getInterface().getInputVars()) {
			if (null == inVar.getValue()) {
				inVar.setValue(LibraryElementFactory.eINSTANCE.createValue());
			}
		}
		for (final VarDeclaration inVar : subApp.getInterface().getInOutVars()) {
			if (null == inVar.getValue()) {
				inVar.setValue(LibraryElementFactory.eINSTANCE.createValue());
			}
		}
		for (final VarDeclaration inOutVar : subApp.getInterface().getInOutVars()) {
			if (inOutVar.getAttributes().stream().map(Attribute::getName).anyMatch(
					LibraryElementTags.ELEMENT_INOUTVISIBLEOUT::equals) && inOutVar.getInOutVarOpposite().isVisible()) {
				inOutVar.getInOutVarOpposite().setVisible(false);
				inOutVar.deleteAttribute(LibraryElementTags.ELEMENT_INOUTVISIBLEOUT);
			}
		}

	}

	public FBNetworkElement createTypedSubapp(final String type) {
		final TypedSubApp subApp = LibraryElementFactory.eINSTANCE.createTypedSubApp();
		final SubAppTypeEntry subEntry = addDependency(getTypeLibrary().getSubAppTypeEntry(type));
		if (subEntry == null) {
			return addDependency(FordiacMarkerHelper.createTypeErrorMarkerFB(type, getTypeLibrary(),
					LibraryElementPackage.eINSTANCE.getSubAppType()));
		}
		subApp.setTypeEntry(subEntry);
		subApp.setInterface(subEntry.getType().getInterfaceList().copy());
		return subApp;
	}

	private void parseUntypedSubapp(final UntypedSubApp subApp) throws TypeImportException, XMLStreamException {
		processChildren(LibraryElementTags.SUBAPP_ELEMENT, name -> (switch (name) {
		case LibraryElementTags.SUBAPPINTERFACE_LIST_ELEMENT -> {
			final SubAppTImporter interfaceImporter = new SubAppTImporter(this);
			subApp.setInterface(interfaceImporter.parseInterfaceList(LibraryElementTags.SUBAPPINTERFACE_LIST_ELEMENT));
			yield true;
		}
		case LibraryElementTags.SUBAPPNETWORK_ELEMENT -> {
			final SubAppNetworkImporter subAppImporter = new SubAppNetworkImporter(this, subApp.getInterface());
			subApp.setSubAppNetwork(subAppImporter.getFbNetwork());
			subAppImporter.parseFBNetwork(LibraryElementTags.SUBAPPNETWORK_ELEMENT);
			yield true;
		}
		case LibraryElementTags.PARAMETER_ELEMENT -> {
			parseParameter(subApp);
			yield true;
		}
		case LibraryElementTags.ATTRIBUTE_ELEMENT -> {
			parseUntypedSubappAttributes(subApp);
			yield true;
		}
		default -> false;
		}));
	}

	private void parseUntypedSubappAttributes(final SubApp subApp) throws XMLStreamException, TypeImportException {
		final String name = getAttributeValue(LibraryElementTags.NAME_ATTRIBUTE);
		if (name != null) {
			switch (name) {
			case LibraryElementTags.WIDTH_ATTRIBUTE:
				final String widthValue = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
				if (widthValue != null) {
					subApp.setWidth(Double.parseDouble(widthValue));
				}
				break;
			case LibraryElementTags.HEIGHT_ATTRIBUTE:
				final String heightValue = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
				if (heightValue != null) {
					subApp.setHeight(Double.parseDouble(heightValue));
				}
				break;
			case LibraryElementTags.LOCKED_ATTRIBUTE:
				final String isLocked = getAttributeValue(LibraryElementTags.VALUE_ATTRIBUTE);
				subApp.setLocked(Boolean.parseBoolean(isLocked));
				break;
			default:
				parseGenericAttributeNode(subApp);
				break;
			}
		} else {
			parseGenericAttributeNode(subApp);
		}
		proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
	}

}

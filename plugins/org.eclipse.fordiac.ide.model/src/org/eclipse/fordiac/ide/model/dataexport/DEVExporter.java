/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import javax.xml.stream.XMLStreamException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeName;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class DEVExporter extends AbstractTypeExporter {

	public DEVExporter(final DeviceType type) {
		super(type);
	}

	@Override
	public DeviceType getType() {
		return (DeviceType) super.getType();
	}

	@Override
	protected String getRootTag() {
		return LibraryElementTags.DEVICETYPE_ELEMENT;
	}

	@Override
	protected void createTypeSpecificXMLEntries() throws XMLStreamException {
		addCompilerInfo(getType().getCompilerInfo());

		for (final VarDeclaration varDecl : getType().getVarDeclaration()) {
			addVarDeclaration(varDecl);
		}
		for (final ResourceTypeName rName : getType().getResourceTypeName()) {
			addStartElement(LibraryElementTags.RESOURCETYPE_NAME_ELEMENT);
			addNameAttribute(rName.getName());
			addEndElement();
		}
		addResources(getType().getResource());

		final FBNetwork network = getType().getFBNetwork();
		if (network != null && !network.getNetworkElements().isEmpty()) {
			new FBNetworkExporter(this).createFBNetworkElement(network);
		}

		addAttributes(getType().getAttributes());
	}

	private void addResources(final EList<Resource> resourceList) throws XMLStreamException {
		for (final Resource resource : resourceList) {
			addStartElement(LibraryElementTags.RESOURCE_ELEMENT);
			addNameTypeCommentAttribute(resource, resource.getType());
			addParamsConfig(resource.getVarDeclarations());

			new FBNetworkExporter(this).createFBNetworkElement(resource.getFBNetwork());

			addAttributes(getType().getAttributes());
			addEndElement();
		}
	}
}

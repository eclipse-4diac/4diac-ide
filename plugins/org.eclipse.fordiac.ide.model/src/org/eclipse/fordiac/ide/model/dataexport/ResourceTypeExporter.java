/*******************************************************************************
 * Copyright (c) 2025 ANURAG SISODIYA
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   ANURAG SISODIYA - Initial implementation of ResourceTypeExporter
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.dataexport;

import javax.xml.stream.XMLStreamException;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class ResourceTypeExporter extends AbstractTypeExporter {

	public ResourceTypeExporter(final ResourceType type) {
		super(type);
	}

	@Override
	protected String getRootTag() {
		return LibraryElementTags.RESOURCETYPE_ELEMENT;
	}

	@Override
	protected void createTypeSpecificXMLEntries() throws XMLStreamException {

		addCompilerInfo(getType().getCompilerInfo());

		for (final VarDeclaration varDecl : getType().getVarDeclaration()) {
			addVarDeclaration(varDecl);
		}

		exportFBNetwork();

	}

	private void exportFBNetwork() throws XMLStreamException {
		final FBNetwork network = getType().getFBNetwork();
		if (network != null && !network.getNetworkElements().isEmpty()) {
			new FBNetworkExporter(this).createFBNetworkElement(network);
		}
	}

	@Override
	public ResourceType getType() {
		return (ResourceType) super.getType();
	}
}
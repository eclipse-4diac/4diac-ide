/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.subapptypeeditor.viewer;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.AbstractFbNetworkInstanceViewer;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.SubappViewerEditPartFactory;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.gef.EditPartFactory;

public class SubappInstanceViewer extends AbstractFbNetworkInstanceViewer {



	@Override
	public EditPartFactory getEditPartFactory() {
		return new SubappViewerEditPartFactory(this, fbNetworkElement, fbEditPart);
	}

	@Override
	protected CompositeFBType createFbType(final CompositeFBType type, final InterfaceList interfaceList) {
		final SubAppType typeCopy = LibraryElementFactory.eINSTANCE.createSubAppType();
		typeCopy.setInterfaceList(EcoreUtil.copy(interfaceList));
		typeCopy.setFBNetwork(FBNetworkHelper.copyFBNetWork(type.getFBNetwork(), typeCopy.getInterfaceList()));
		return typeCopy;
	}

}

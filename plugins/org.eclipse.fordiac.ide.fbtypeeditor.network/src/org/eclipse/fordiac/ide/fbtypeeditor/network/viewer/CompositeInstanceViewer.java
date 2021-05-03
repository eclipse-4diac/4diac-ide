/*******************************************************************************
 * Copyright (c) 2013 - 2017 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - copying the FB type to fix issues in monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.viewer;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.EditPartFactory;

public class CompositeInstanceViewer extends AbstractFbNetworkInstanceViewer {

	@Override
	public EditPartFactory getEditPartFactory() {
		return new CompositeViewerEditPartFactory(this, fbNetworkElement, fbEditPart);
	}

	@Override
	protected CompositeFBType createFbType(final CompositeFBType type,
			final InterfaceList interfaceList) {
		final CompositeFBType typeCopy = LibraryElementFactory.eINSTANCE.createCompositeFBType();
		typeCopy.setInterfaceList(EcoreUtil.copy(interfaceList));
		typeCopy.setFBNetwork(FBNetworkHelper.copyFBNetWork(type.getFBNetwork(), typeCopy.getInterfaceList()));
		return typeCopy;
	}

}

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

import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.AbstractFbNetworkInstanceViewer;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.subapptypeeditor.editparts.SubappViewerEditPartFactory;
import org.eclipse.gef.EditPartFactory;

public class SubappInstanceViewer extends AbstractFbNetworkInstanceViewer {


	@Override
	public EditPartFactory getEditPartFactory() {
		return new SubappViewerEditPartFactory(this, getFbNetworkElement(), getEditPart());
	}

	@Override
	public FBNetwork getModel() {
		FBNetwork network = getFbNetworkElement().getSubAppNetwork();
		if (null == network) {
			// the contained network has not been loaded yet, load it now.
			network = getFbNetworkElement().loadSubAppNetwork();
		}
		return network;
	}

	@Override
	protected SubApp getFbNetworkElement() {
		return (SubApp) super.getFbNetworkElement();
	}

}

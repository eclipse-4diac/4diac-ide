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

import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.gef.EditPartFactory;

public class CompositeInstanceViewer extends AbstractFbNetworkInstanceViewer {

	private FBNetwork fbNetworkToView; // the FBNetwork contained in the fbNetworkelement

	@Override
	public EditPartFactory getEditPartFactory() {
		return new CompositeViewerEditPartFactory(this, getFbNetworkElement(), getEditPart());
	}

	@Override
	public FBNetwork getModel() {
		if (null == fbNetworkToView) {
			loadFBNetwork();
		}
		return fbNetworkToView;
	}

	private void loadFBNetwork() {
		final CompositeFBType type = (CompositeFBType) getFbNetworkElement().getType();
		fbNetworkToView = FBNetworkHelper.copyFBNetWork(type.getFBNetwork(), getFbNetworkElement().getInterface());
	}

}

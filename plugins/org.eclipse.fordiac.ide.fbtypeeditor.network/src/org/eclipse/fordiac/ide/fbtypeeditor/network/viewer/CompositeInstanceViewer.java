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

import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.gef.EditPartFactory;

public class CompositeInstanceViewer extends AbstractFbNetworkInstanceViewer {

	@Override
	public EditPartFactory getEditPartFactory() {
		return new CompositeViewerEditPartFactory(this, getFbNetworkElement());
	}


	@Override
	public FBNetwork getModel() {
		FBNetwork network = getFbNetworkElement().getCfbNetwork();
		if (null == network) {
			// the contained network has not been loaded yet, load it now.
			network = getFbNetworkElement().loadCFBNetwork();
		}
		return network;
	}

	@Override
	protected CFBInstance getFbNetworkElement() {
		return (CFBInstance) super.getFbNetworkElement();
	}

}

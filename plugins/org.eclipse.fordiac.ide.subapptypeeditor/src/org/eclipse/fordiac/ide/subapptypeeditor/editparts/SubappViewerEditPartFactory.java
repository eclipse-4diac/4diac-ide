/*******************************************************************************
 * Copyright (c) 2021 Primemetals Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.subapptypeeditor.editparts;

import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeViewerEditPartFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;

public class SubappViewerEditPartFactory extends CompositeViewerEditPartFactory {

	public SubappViewerEditPartFactory(final GraphicalEditor editor, final FBNetworkElement fbInstance) {
		super(editor, fbInstance);
	}

	@Override
	protected EditPart getPartForFBNetwork(final FBNetwork fbNetwork) {
		if (getFbInstance() == fbNetwork.eContainer()) {
			return new SubAppInstanceViewerEditPart();
		}
		return super.getPartForFBNetwork(fbNetwork);
	}

}

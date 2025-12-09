/*******************************************************************************
 * Copyright (c) 2014, 2016, 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.subapptypeeditor.editparts;

import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeNetworkEditPartFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;

public class SubAppTypeNetworkEditPartFactory extends CompositeNetworkEditPartFactory {

	public SubAppTypeNetworkEditPartFactory(final GraphicalEditor editor) {
		super(editor);
	}

	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
		if (modelElement instanceof SubApp) {
			return new SubAppForFBNetworkEditPart();
		}
		return super.getPartForElement(context, modelElement);
	}

	@Override
	protected EditPart getPartForFBNetwork(final FBNetwork fbNetwork) {
		return new TypedSubAppNetworkEditPart();
	}

}

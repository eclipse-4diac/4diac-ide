/*******************************************************************************
 * Copyright (c) 2021, 2024 Primemetals Austria GmbH
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

import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.TargetInterfaceElement;
import org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeViewerEditPartFactory;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;

public class SubappViewerEditPartFactory extends CompositeViewerEditPartFactory {

	public SubappViewerEditPartFactory(final GraphicalEditor editor, final FBNetworkElement fbInstance) {
		super(editor, fbInstance);
	}

	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
		if (modelElement instanceof TargetInterfaceElement) {
			return new TargetInterfaceElementEditPartRO();
		}
		return super.getPartForElement(context, modelElement);
	}

	@Override
	protected EditPart getPartForFBNetwork(final FBNetwork fbNetwork) {
		if (getFbInstance() == fbNetwork.eContainer()) {
			return new SubAppInstanceViewerEditPart();
		}
		return super.getPartForFBNetwork(fbNetwork);
	}

	@Override
	protected EditPart getPartForInterfaceElement(final EditPart context, final IInterfaceElement ie) {
		if ((ie.getFBNetworkElement() instanceof UntypedSubApp) && (context instanceof SubAppForFBNetworkEditPart)) {
			return new UntypedSubAppInterfaceElementEditPartRO();
		}
		return super.getPartForInterfaceElement(context, ie);
	}

}

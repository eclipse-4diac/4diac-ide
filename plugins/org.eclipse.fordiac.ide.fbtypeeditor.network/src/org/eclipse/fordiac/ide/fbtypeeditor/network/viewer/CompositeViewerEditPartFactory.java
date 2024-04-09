/*******************************************************************************
 * Copyright (c) 2013 - 2017 Profactor GmbH, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.network.viewer;

import org.eclipse.fordiac.ide.application.policies.FBNElementSelectionPolicy;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.AdapterFBEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.network.editparts.CompositeNetworkEditPartFactory;
import org.eclipse.fordiac.ide.gef.policies.EmptyXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.ui.parts.GraphicalEditor;

/** A factory for creating ECCEditPart objects. */
public class CompositeViewerEditPartFactory extends CompositeNetworkEditPartFactory {

	private final FBNetworkElement fbInstance;

	protected FBNetworkElement getFbInstance() {
		return fbInstance;
	}

	public CompositeViewerEditPartFactory(final GraphicalEditor editor, final FBNetworkElement fbInstance) {
		super(editor);
		this.fbInstance = fbInstance;
	}

	/**
	 * Maps an object to an EditPart.
	 *
	 * @param context      the context
	 * @param modelElement the model element
	 *
	 * @return the part for element
	 *
	 * @throws RuntimeException if no match was found (programming error)
	 */
	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
		if (modelElement instanceof final IInterfaceElement iElement) {
			return getPartForInterfaceElement(iElement);
		}
		if (modelElement instanceof AdapterFB) {
			return new AdapterFBEditPart() {
				@Override
				protected void createEditPolicies() {
					// Highlight In and Outconnections of the selected fb, allow alignment of FBs
					installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new FBNElementSelectionPolicy());
					installEditPolicy(EditPolicy.LAYOUT_ROLE, new EmptyXYLayoutEditPolicy());
				}

				@Override
				public void performDirectEdit() {
					// don't do anything here to avoid direct edit
				}
			};
		}
		if (modelElement instanceof FB) {
			return new FBEditPartRO();
		}
		if (modelElement instanceof Connection) {
			return new ConnectionEditPartRO();
		}
		return super.getPartForElement(context, modelElement);
	}

	protected EditPart getPartForInterfaceElement(final IInterfaceElement ie) {
		if (fbInstance == ie.eContainer().eContainer()) {
			return new CompositeInternalInterfaceEditPartRO();
		}
		return new InterfaceEditPartForFBNetworkRO();
	}

	@Override
	protected EditPart getPartForFBNetwork(final FBNetwork fbNetwork) {
		return new CompositeNetworkViewerEditPart();
	}

}

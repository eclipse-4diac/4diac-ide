/*******************************************************************************
 * Copyright (c) 2022, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.view;

import org.eclipse.fordiac.ide.debug.EvaluatorProcess;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.DebugViewWithEditPart;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.EmptyDebugViewRootEditPart;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.EventValueEditPart;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.EventValueEntity;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.FBDebugViewRootEditPart;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.InputEventValueEditPart;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.InterfaceValueEditPart;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.InterfaceValueEntity;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.EventInputContainer;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.EventOutputContainer;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBInterfaceEditPartFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBTypeEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InterfaceContainerEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.PlugContainer;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.SocketContainer;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.VariableInputContainer;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.VariableOutputContainer;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.gef.EditPart;

public class FBDebugViewEditPartFactory extends FBInterfaceEditPartFactory {

	public FBDebugViewEditPartFactory() {
		super(null, null); // in our case both can safely be null
	}

	@Override
	protected EditPart getPartForElement(final EditPart context, final Object modelElement) {
		if (modelElement == null && context == null) {
			// no debug is active show an empty view
			return new EmptyDebugViewRootEditPart();
		}
		if (modelElement instanceof EvaluatorProcess) {
			return new FBDebugViewRootEditPart();
		}
		if (modelElement instanceof FBType) {
			// we can not use the version of parent as this expects a FBTypeRootEditPart as
			// context which we don't have
			// here
			return new FBTypeEditPart();
		}
		if (modelElement instanceof InterfaceValueEntity) {
			return new InterfaceValueEditPart();
		}
		if (modelElement instanceof final EventValueEntity ev) {
			return ev.getEvent().isIsInput() ? new InputEventValueEditPart() : new EventValueEditPart();
		}

		if (modelElement instanceof EventInputContainer || modelElement instanceof EventOutputContainer
				|| modelElement instanceof VariableInputContainer || modelElement instanceof VariableOutputContainer
				|| modelElement instanceof SocketContainer || modelElement instanceof PlugContainer) {
			return new InterfaceContainerEditPart() {
				@Override
				protected void createEditPolicies() {
					// we don't want dedicated editpolicies
				}
			};
		}

		if (modelElement instanceof With) {
			return new DebugViewWithEditPart();
		}

		return super.getPartForElement(context, modelElement);
	}
}

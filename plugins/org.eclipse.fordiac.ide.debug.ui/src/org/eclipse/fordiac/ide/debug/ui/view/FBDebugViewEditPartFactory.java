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

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.debug.EvaluatorProcess;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.DebugViewWithEditPart;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.EmptyDebugViewRootEditPart;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.EventValueEditPart;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.EventValueEntity;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.FBDebugViewRootEditPart;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.InnerValueEditPart;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.InnerValueEntity;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.InputEventValueEditPart;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.InterfaceValueEditPart;
import org.eclipse.fordiac.ide.debug.ui.view.editparts.InterfaceValueEntity;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBInterfaceEditPartFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.FBTypeEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InterfaceContainerEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.model.AbstractContainerElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

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

		return switch (modelElement) {
		case final EvaluatorProcess _ -> new FBDebugViewRootEditPart();
		case final FBType _ ->
			// we can not use the version of parent as this expects a FBTypeRootEditPart as
			// context which we don't have here
			new FBTypeEditPart() {
				@Override
				public void activate() {
					super.activate();
					// in the debug view we have to manually position the FBType figure
					((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(),
							new Rectangle(0, 0, -1, -1));
				}
			};
		case final InnerValueEntity _ -> new InnerValueEditPart();
		case final InterfaceValueEntity _ -> new InterfaceValueEditPart();
		case final EventValueEntity ev ->
			ev.getEvent().isIsInput() ? new InputEventValueEditPart() : new EventValueEditPart();
		case final With _ -> new DebugViewWithEditPart();
		case final AbstractContainerElement _ -> new InterfaceContainerEditPart() {
			@Override
			protected void createEditPolicies() {
				// we don't want dedicated editpolicies
			}
		};
		case null -> super.getPartForElement(context, modelElement);
		default -> super.getPartForElement(context, modelElement);
		};
	}
}

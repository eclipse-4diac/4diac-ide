/*******************************************************************************
 * Copyright (c) 2012 - 2016 fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions;

import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateECActionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editors.ActionCreationFactory;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionAlgorithmEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECActionOutputEventEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class AddECCActionAction extends SelectionAction {

	/**
	 * Add ECC Action action id. Value:
	 * <code>"org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.CreateStateAction"</code>
	 */
	public static final String ADD_ECC_ACTION = "org.eclipse.fordiac.ide.fbtypeeditor.ecc.actions.AddECCActionAction";//$NON-NLS-1$

	private final ActionCreationFactory actionFactory = new ActionCreationFactory();

	public AddECCActionAction(final IWorkbenchPart part) {
		super(part);
		setId(ADD_ECC_ACTION);
		setText(Messages.ECCActions_AddAction);
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
	}

	@Override
	protected boolean calculateEnabled() {
		if (1 == getSelectedObjects().size()) {
			final ECState state = getState(getSelectedObjects());
			if (null != state) {
				// only allow to add actions if we are not the the initial state
				return !state.isStartState();
			}
			return false;
		}
		return false;
	}

	@Override
	public void run() {
		final IFBTEditorPart editor = (IFBTEditorPart) getWorkbenchPart();
		final ECAction action = (ECAction) actionFactory.getNewObject();
		execute(new CreateECActionCommand<>(action, getState(getSelectedObjects())));
		editor.outlineSelectionChanged(action);
	}

	private static ECState getState(final List<Object> selectedObjects) {
		if (selectedObjects == null || selectedObjects.isEmpty()) {
			return null;
		}
		return switch (selectedObjects.get(0)) {
		case final ECStateEditPart stateEP -> stateEP.getModel();
		case final ECActionAlgorithmEditPart algoEP -> algoEP.getAction().getECState();
		case final ECActionOutputEventEditPart eventEP -> eventEP.getAction().getECState();
		default -> null;
		};
	}
}

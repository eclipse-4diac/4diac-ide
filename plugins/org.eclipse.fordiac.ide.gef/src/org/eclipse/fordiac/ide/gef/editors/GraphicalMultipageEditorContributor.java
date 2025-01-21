/*******************************************************************************
 * Copyright (c) 2012, 2024 TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.gef.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Adapters;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentRetargetAction;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;

public class GraphicalMultipageEditorContributor extends MultiPageEditorActionBarContributor {

	private final ActionRegistry registry = new ActionRegistry();
	private final List<RetargetAction> retargetActions = new ArrayList<>();
	private final List<String> globalActionKeys = new ArrayList<>();

	@Override
	public void setActiveEditor(final IEditorPart editor) {
		setGlobalActionHandler(Adapters.adapt(editor, ActionRegistry.class));
		super.setActiveEditor(editor);

	}

	@Override
	public void setActivePage(final IEditorPart activeEditor) {
		setGlobalActionHandler(Adapters.adapt(activeEditor, ActionRegistry.class));
	}

	private void setGlobalActionHandler(final ActionRegistry editorRegistry) {
		final IActionBars bars = getActionBars();
		for (final String id : globalActionKeys) {
			if (null != editorRegistry) {
				bars.setGlobalActionHandler(id, editorRegistry.getAction(id));
			} else {
				bars.setGlobalActionHandler(id, null);
			}
		}
	}

	protected void buildActions() {
		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		addRetargetAction(new DeleteRetargetAction());

		addRetargetAction(new AlignmentRetargetAction(PositionConstants.LEFT));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.CENTER));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.RIGHT));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.TOP));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.MIDDLE));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.BOTTOM));
	}

	protected IAction getAction(final String id) {
		return getActionRegistry().getAction(id);
	}

	protected ActionRegistry getActionRegistry() {
		return registry;
	}

	protected void addRetargetAction(final RetargetAction action) {
		addAction(action);
		retargetActions.add(action);
		getPage().addPartListener(action);
		addGlobalActionKey(action.getId());
	}

	protected void addAction(final IAction action) {
		getActionRegistry().registerAction(action);
	}

	protected void addGlobalActionKey(final String key) {
		globalActionKeys.add(key);
	}

	protected void declareGlobalActionKeys() {
		this.addGlobalActionKey(ActionFactory.SELECT_ALL.getId());
	}

	@Override
	public void init(final IActionBars bars) {
		buildActions();
		declareGlobalActionKeys();
		super.init(bars);
	}

}

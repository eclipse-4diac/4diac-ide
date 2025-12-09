/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.ui.properties;

import org.eclipse.core.runtime.Adapters;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class GlobalConstantsPropertySheetPage extends TabbedPropertySheetPage {

	private final ITabbedPropertySheetPageContributor contributor;

	public GlobalConstantsPropertySheetPage(final ITabbedPropertySheetPageContributor contributor) {
		super(contributor);
		this.contributor = contributor;
	}

	@Override
	public void setActionBars(final IActionBars actionBars) {
		// Override the undo and redo global action handlers
		// to use the editor action handlers
		final ActionRegistry actionRegistry = Adapters.adapt(contributor, ActionRegistry.class);
		if (actionRegistry != null) {
			IAction action = actionRegistry.getAction(ActionFactory.UNDO.getId());
			if (action != null) {
				actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), action);
			}
			action = actionRegistry.getAction(ActionFactory.REDO.getId());
			if (action != null) {
				actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), action);
			}
		} else {
			super.setActionBars(actionBars);
		}
	}
}

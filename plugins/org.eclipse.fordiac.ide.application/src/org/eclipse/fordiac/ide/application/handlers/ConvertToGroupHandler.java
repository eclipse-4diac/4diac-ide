/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.commands.ConvertSubappToGroupCommand;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UISubAppNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConvertToGroupHandler extends AbstractHandler implements CommandStackEventListener {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		final SubApp subApp = getSelectedSubApp(selection);
		if (null != subApp) {
			final CommandStack commandStack = HandlerHelper.getCommandStack(editor);
			final ConvertSubappToGroupCommand cmd = new ConvertSubappToGroupCommand(subApp);
			if (cmd.canExecute()) {
				commandStack.execute(cmd);
			}
			commandStack.addCommandStackEventListener(this);
			SystemManager.INSTANCE.notifyListeners();
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		final SubApp subApp = getSelectedSubApp(selection);
		setBaseEnabled((null != subApp) // the type check has to be done before group containment
				&& !(subApp.isTyped() || subApp.isContainedInTypedInstance()) 
				&& !isGroupContainedInSubapp(subApp));
	}

	private static boolean isGroupContainedInSubapp(final SubApp subApp) {
		final FBNetworkElement group = subApp.getSubAppNetwork().getNetworkElements().stream()
				.filter(Group.class::isInstance).findFirst().orElse(null);
		return group != null;
	}

	private static SubApp getSelectedSubApp(final Object selection) {
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection structSel = ((IStructuredSelection) selection);
			if (!structSel.isEmpty() && (structSel.size() == 1)) {
				return getSubApp(structSel.getFirstElement());
			}
		}
		return null;
	}

	private static SubApp getSubApp(final Object currentElement) {
		if (currentElement instanceof SubApp) {
			return (SubApp) currentElement;
		}
		if (currentElement instanceof SubAppForFBNetworkEditPart) {
			return ((SubAppForFBNetworkEditPart) currentElement).getModel();
		}
		if (currentElement instanceof UISubAppNetworkEditPart) {
			return (SubApp) ((UISubAppNetworkEditPart) currentElement).getModel().eContainer();
		}
		return null;
	}

	@Override
	public void stackChanged(final CommandStackEvent event) {
		// nothing to do here, selection is handled by command
	}

}

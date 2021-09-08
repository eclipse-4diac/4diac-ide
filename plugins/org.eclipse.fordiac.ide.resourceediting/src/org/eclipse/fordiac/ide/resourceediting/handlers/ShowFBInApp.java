/*******************************************************************************
 * Copyright (c) 2011, 2021 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz
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
 *   Alois Zoitl - Clean-up and making the implementation more robust
 *******************************************************************************/
package org.eclipse.fordiac.ide.resourceediting.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler to open the corresponding FB in its application if selected in a
 * Resource.
 *
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ShowFBInApp extends AbstractHandler {


	/**
	 * the command has been executed, so extract extract the needed information from
	 * the application context.
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		final FBNetworkElement appFB = getAppFB(selection);

		final Application app = getApplication(appFB);
		if (app != null && appFB != null) {
			final IEditorPart editor = OpenListenerManager.openEditor(app);
			HandlerHelper.selectElement(appFB, HandlerHelper.getViewer(editor));
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		final FBNetworkElement appFB = getAppFB(selection);
		setBaseEnabled(appFB != null && getApplication(appFB) != null);
	}

	/**
	 * Finds the corresponding application of a <em>mapped</em> FB.
	 *
	 * @param fbView
	 * @return the application of the fbView if available
	 */
	private static Application getApplication(final FBNetworkElement fb) {
		if (fb != null) {
			return fb.getFbNetwork().getApplication();
		}
		return null;
	}

	private static FBNetworkElement getAppFB(final Object selection) {
		if (selection instanceof IStructuredSelection && ((IStructuredSelection) selection).size() == 1) {
			final Object first = ((IStructuredSelection) selection).getFirstElement();
			if (first instanceof EditPart && ((EditPart) first).getModel() instanceof FBNetworkElement) {
				final FBNetworkElement fb = (FBNetworkElement) ((EditPart) first).getModel();
				return fb.getOpposite();
			}
		}
		return null;
	}

}

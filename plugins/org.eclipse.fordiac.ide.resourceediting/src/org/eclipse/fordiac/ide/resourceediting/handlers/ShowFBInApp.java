/*******************************************************************************
 * Copyright (c) 2011, 2013, 2016, 2017 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.resourceediting.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editparts.AbstractFBNElementEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
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
		if (selection instanceof IStructuredSelection) {
			final Object first = ((IStructuredSelection) selection).getFirstElement();
			if (first instanceof AbstractFBNElementEditPart) {
				final FBNetworkElement fb = ((AbstractFBNElementEditPart) first).getModel();
				final FBNetworkElement appFB = fb.getOpposite();
				final Application app = getApplication(appFB);
				if (app != null && appFB != null) {
					final IEditorPart editor = OpenListenerManager.openEditor(app);
					if (editor instanceof FBNetworkEditor) {
						final AdvancedScrollingGraphicalViewer viewer = ((FBNetworkEditor) editor).getViewer();
						if (viewer != null) {
							final Object fbToSelect = viewer.getEditPartRegistry().get(appFB);
							if (fbToSelect instanceof EditPart) {
								viewer.selectAndRevealEditPart((EditPart) fbToSelect);
							}
						}
					}
				}
			}
		}
		return null;
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

	@Override
	public void setEnabled(final Object evaluationContext) {
		final IEvaluationContext ctx = (IEvaluationContext) evaluationContext;
		Object obj = ctx.getDefaultVariable();

		if (obj instanceof List) {
			final List<?> list = (List<?>) obj;
			if (!list.isEmpty()) {
				obj = list.get(0);
			}
		}
		if (obj instanceof AbstractFBNElementEditPart) {
			final FBNetworkElement fb = ((AbstractFBNElementEditPart) obj).getModel();
			final FBNetworkElement appFB = fb.getOpposite();
			final Application app = getApplication(appFB);
			setBaseEnabled((app != null && appFB != null));
		} else {
			setBaseEnabled(false);
		}
	}
}

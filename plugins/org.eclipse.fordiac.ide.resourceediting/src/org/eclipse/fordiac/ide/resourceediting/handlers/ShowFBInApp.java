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
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.resourceediting.editparts.ResFBEditPart;
import org.eclipse.fordiac.ide.util.OpenListenerManager;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
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
	 * The constructor.
	 */
	public ShowFBInApp() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			Object first = ((IStructuredSelection) selection).getFirstElement();
			if (first instanceof ResFBEditPart) {
				FB fb = ((ResFBEditPart) first).getModel();
				FB appFB = (FB)fb.getOpposite();
				Application app = getApplication(appFB);
				if (app != null && appFB != null) {
					IEditorPart editor = OpenListenerManager.openEditor(app);
					if (editor instanceof FBNetworkEditor) {
						GraphicalViewer viewer = ((FBNetworkEditor) editor).getViewer();
						if (viewer != null) {
							Map<?, ?> map = viewer.getEditPartRegistry();
							Object fbToSelect = map.get(appFB);
							if (fbToSelect instanceof EditPart) {
								viewer.setSelection(new StructuredSelection(fbToSelect));
								viewer.reveal((EditPart) fbToSelect);
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
	 * @param fbView
	 * @return the application of the fbView if available
	 */
	private Application getApplication(FB fb) {
		if (fb != null) {
			return fb.getFbNetwork().getApplication();
		}
		return null;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		IEvaluationContext ctx = (IEvaluationContext) evaluationContext;
		Object obj = ctx.getDefaultVariable();

		if (obj instanceof List) {
			List<?> list = (List<?>) obj;
			if (list.size() > 0) {
				obj = list.get(0);
			}
		}
		if(obj instanceof ResFBEditPart){
			FB fb = ((ResFBEditPart) obj).getModel();
			FB appFB = (FB)fb.getOpposite();
			Application app = getApplication(appFB);
			setBaseEnabled((app != null && appFB != null));
		}
		else{
			setBaseEnabled(false);
		}
	}
}

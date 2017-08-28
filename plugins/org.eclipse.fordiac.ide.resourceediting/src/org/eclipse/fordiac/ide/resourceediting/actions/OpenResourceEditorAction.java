/*******************************************************************************
 * Copyright (c) 2008 - 2016 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.resourceediting.actions;

import org.eclipse.fordiac.ide.model.libraryElement.I4DIACElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl;
import org.eclipse.fordiac.ide.resourceediting.Activator;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceDiagramEditor;
import org.eclipse.fordiac.ide.resourceediting.editors.ResourceEditorInput;
import org.eclipse.fordiac.ide.resourceediting.editparts.VirtualInOutputEditPart;
import org.eclipse.fordiac.ide.util.OpenListener;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * The Class OpenResourceEditorAction.
 */
public class OpenResourceEditorAction extends OpenListener {

	/** The res. */
	private Resource res;

	// hack
	Object model;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
	 * action .IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(final IAction action,
			final IWorkbenchPart targetPart) {
		// not used
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(final IAction action) {
		if(null != res){
			ResourceEditorInput input = new ResourceEditorInput(res);
	
			IWorkbenchPage activePage = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			
			// TODO find better place for the following code
			try {
				editor = activePage.openEditor(input,
						ResourceDiagramEditor.class.getName());
				ResourceDiagramEditor resEditor = (ResourceDiagramEditor) editor;
				Object obj = resEditor.getViewer().getEditPartRegistry().get(model);
				if (obj instanceof EditPart) {
					EditPart editPart = (EditPart) obj;
					resEditor.getViewer().setSelection(new StructuredSelection(editPart));
					resEditor.getViewer().reveal(editPart);
				}
			} catch (PartInitException e) {
				editor = null;
				Activator.getDefault().logError(e.getMessage(), e);
			}
		}
		else{
			MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Other end of connection not mapped", "The other end of this connection has not been mapped to a resource. Therefore we can not open it.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(final IAction action,
			final ISelection selection) {
		res = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSel = (IStructuredSelection) selection;
			if (structuredSel.getFirstElement() instanceof Resource) {
				res = (Resource) structuredSel.getFirstElement();
			}
			if (structuredSel.getFirstElement() instanceof VirtualInOutputEditPart) {
				VirtualInOutputEditPart temp = (VirtualInOutputEditPart) structuredSel
						.getFirstElement();
				// TODO model refactoring - implement when mapping is defined!
//				FB fb = (FB) temp.getModel().getIInterfaceElement().getFB();
//				ResourceFBNetwork resFBNet = fb.getResource();
//
				
				/*
 * 
 * 				if(resFBNet != null){
					res = (Resource) resFBNet.eContainer();
					ResourceView resourceView = SystemManager.getInstance()
							.getResourceViewForResource(res);
					for (Iterator<View> iterator = resourceView
							.getUIResourceDiagram().getChildren().iterator(); iterator
							.hasNext();) {
						View view = iterator.next();
						if (view instanceof FBView) {
							if (((FBView) view).getFb().equals(fb)) {
								model = view;
								break;
							}
						}
					}
				}
 */

				// model = temp.getCastedModel();
				// TODO Exception Handling!

			}
		}
	}

	@Override
	public boolean supportsObject(final Class<? extends I4DIACElement> clazz) {
		return clazz != null && clazz.equals(ResourceImpl.class);
	}

	@Override
	public Action getOpenListenerAction() {
		return new OpenListenerAction(this);
	}

}

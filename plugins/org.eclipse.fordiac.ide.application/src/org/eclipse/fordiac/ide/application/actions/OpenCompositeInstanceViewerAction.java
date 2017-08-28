/*******************************************************************************
 * Copyright (c) 2013, 2014 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.application.actions;

import org.eclipse.fordiac.ide.application.ApplicationPlugin;
import org.eclipse.fordiac.ide.application.editparts.FBEditPart;
import org.eclipse.fordiac.ide.application.viewer.composite.CompositeInstanceViewerInput;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.I4DIACElement;
import org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl;
import org.eclipse.fordiac.ide.util.IOpenListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * The Class OpenSubApplicationEditorAction.
 */
public class OpenCompositeInstanceViewerAction extends Action implements
		IOpenListener {

	private static final String COMPOSITE_INSTANCE_VIEWER_ID = "org.eclipse.fordiac.ide.fbtypeeditor.network.viewer.CompositeInstanceViewer"; //$NON-NLS-1$
	/** The fb. */
	private FB fb;
	private FBEditPart fbEditPart;

	/**
	 * Constructor of the Action.
	 * 
	 * @param fb
	 *            the fb
	 */
	public OpenCompositeInstanceViewerAction(FBEditPart fbEditPart, final FB fb) {
		this.fb = fb;
		this.fbEditPart = fbEditPart;
	}

	/**
	 * Consturctor.
	 */
	public OpenCompositeInstanceViewerAction() {
		// empty constructor for OpenListener
	}

	/**
	 * Opens the editor for the specified Model or sets the focus to the editor
	 * if already opened.
	 */
	@Override
	public void run() {
		CompositeInstanceViewerInput input = new CompositeInstanceViewerInput(fbEditPart, fb, fb.getName(),
				fb.getName());

		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		try {
			activePage.openEditor(input, COMPOSITE_INSTANCE_VIEWER_ID);
			
		} catch (PartInitException e) {
			ApplicationPlugin.getDefault().logError(
					"Composite Instance editor can not be opened: ", e); //$NON-NLS-1$
		}

	}

	@Override
	public Action getOpenListenerAction() {
		return new SubAppOpenListenerAction(this);
	}

	@Override
	public boolean supportsObject(Class<? extends I4DIACElement> clazz) {
		return clazz != null && clazz.equals(SubAppImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
	 * action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// nothing to do

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		run();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSel = (IStructuredSelection) selection;
			if (structuredSel.getFirstElement() instanceof FB) {
				fb = (FB) structuredSel
						.getFirstElement();
			}
		}

	}

	@Override
	public IEditorPart getOpenedEditor() {
		return null;
	}

}

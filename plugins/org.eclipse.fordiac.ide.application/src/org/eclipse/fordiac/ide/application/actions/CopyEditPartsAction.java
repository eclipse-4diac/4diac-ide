/*******************************************************************************
 * Copyright (c) 2008 - 2012, 2016, 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

/**
 * The Class CopyEditPartsAction.
 */
public class CopyEditPartsAction extends WorkbenchPartAction implements
		ISelectionChangedListener {

	private static final String COPY = "Copy";

	/** The templates. */
	private List<EObject> templates = null;

	/**
	 * Instantiates a new copy edit parts action.
	 * 
	 * @param editor the editor
	 */
	public CopyEditPartsAction(IEditorPart editor) {
		super(editor);
		setId(ActionFactory.COPY.getId());
		setText(COPY);		
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages(); 
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
	    setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return templates != null && !templates.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org
	 * .eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection s = event.getSelection();
		if (!(s instanceof IStructuredSelection)) {
			return;
		}
		IStructuredSelection selection = (IStructuredSelection) s;
		templates = new ArrayList<EObject>();
		// only if all selected objects are uifbs copy is possible --> all
		// selected items are added to the template
		for (Iterator iter = selection.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			if (obj instanceof EditPart) {
				Object model = ((EditPart) obj).getModel();
				if ((model instanceof FBNetworkElement)|| (model instanceof Connection)){
					templates.add((EObject) model);
				} 
			}
		}
		refresh();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		Clipboard.getDefault().setContents(templates);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#dispose()
	 */
	@Override
	public void dispose() {
		templates = null;
	}
}

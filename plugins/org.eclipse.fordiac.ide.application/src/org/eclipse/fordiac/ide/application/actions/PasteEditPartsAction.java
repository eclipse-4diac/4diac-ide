/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Filip Andren
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.application.commands.PasteCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

/**
 * The Class PasteEditPartsAction.
 */
public class PasteEditPartsAction extends SelectionAction {

	private static final String PASTE_TEXT = "Paste";
	
	private Point pasteRefPosition;

	/**
	 * Instantiates a new paste edit parts action.
	 * 
	 * @param editor the editor
	 */
	public PasteEditPartsAction(IWorkbenchPart editor) {
		super(editor);
	}

	@Override
	protected boolean calculateEnabled() {
		FBNetwork fbNetwork = getFBNetwork();
		return null != fbNetwork && !getClipboardContents().isEmpty();
	}

	protected Command createPasteCommand() {
		FBNetwork fbNetwork = getFBNetwork();
		if(null != fbNetwork){
			return new PasteCommand(getClipboardContents(), fbNetwork, pasteRefPosition);			
		}
		return new CompoundCommand();
	}

	@SuppressWarnings("rawtypes")
	private List getClipboardContents() {
		List list = Collections.EMPTY_LIST;
		Object obj = Clipboard.getDefault().getContents();
		if (obj instanceof List) {
			list = (List) obj;
		}
		return list;
	}

	@Override
	protected void init() {
		setId(ActionFactory.PASTE.getId());
		setText(PASTE_TEXT);
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages(); 
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
        setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		execute(createPasteCommand());
		pasteRefPosition = null;
	}

	@SuppressWarnings("rawtypes")
	protected FBNetwork getFBNetwork() {
		List selection = getSelectedObjects();
		if (selection != null && selection.size() >= 1 && selection.get(0) instanceof EditPart) {
			EditPart part = (EditPart)selection.get(0);
			while(null != part){
				if(part.getModel() instanceof FBNetwork){
					return (FBNetwork)part.getModel();
				}
				part = part.getParent();
			}
		}		
		return null;
	}

	public void setPastRefPosition(Point pt) {
		pasteRefPosition = pt;
	}

}

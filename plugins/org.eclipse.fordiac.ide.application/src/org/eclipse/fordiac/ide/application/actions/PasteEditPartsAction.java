/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, AIT, fortiss GmbH
 * 				 2018 - 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Filip Andren
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - fixed copy/paste handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.commands.PasteCommand;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
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
		return (null != fbNetwork) && !getClipboardContents().isEmpty();
	}

	protected Command createPasteCommand() {
		FBNetwork fbNetwork = getFBNetwork();
		if (null != fbNetwork) {
			return new PasteCommand(getClipboardContents(), fbNetwork, pasteRefPosition);
		}
		return new CompoundCommand();
	}

	@SuppressWarnings("rawtypes")
	private List getClipboardContents() {
		List list = Collections.emptyList();
		Object obj = Clipboard.getDefault().getContents();
		if (obj instanceof List) {
			list = (List) obj;
		}
		return list;
	}

	@Override
	protected void init() {
		setId(ActionFactory.PASTE.getId());
		setText(Messages.PasteEditPartsAction_Text);
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		execute(createPasteCommand());
		pasteRefPosition = null;
	}

	protected FBNetwork getFBNetwork() {
		if (getWorkbenchPart() instanceof FBNetworkEditor) {
			return ((FBNetworkEditor) getWorkbenchPart()).getModel();
		}
		return null;
	}

	public void setPastRefPosition(Point pt) {
		pasteRefPosition = pt;
	}

	public void setPastRefPosition(org.eclipse.draw2d.geometry.Point point) {
		setPastRefPosition(new Point(point.x, point.y));
	}

}

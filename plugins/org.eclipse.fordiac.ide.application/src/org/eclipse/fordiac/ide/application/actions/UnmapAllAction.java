/*******************************************************************************
 * Copyright (c) 2013, 2016, 2017 fortiss GmbH, Profactor GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.DiagramEditorWithFlyoutPalette;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.ui.IWorkbenchPart;

public class UnmapAllAction extends UnmapAction {

	/** The Constant ID. */
	public static final String ID = "UnmapAll"; //$NON-NLS-1$

	/**
	 * Instantiates a new unmap all action.
	 * 
	 * @param part the part
	 */
	public UnmapAllAction(final IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText(Messages.UnmapAllAction_Text);
	}

	@Override
	protected boolean calculateEnabled() {
		if (!super.calculateEnabled()) {
			FBNetwork fbNetwork = (FBNetwork) ((DiagramEditorWithFlyoutPalette) getWorkbenchPart()).getModel();
			for (FBNetworkElement element : fbNetwork.getNetworkElements()) {
				checkSelectedModelElement(element);
			}
			return (!getSelectedNetworkElements().isEmpty());
		}
		return false;
	}

}

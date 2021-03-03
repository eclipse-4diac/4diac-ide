/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
public class CutAndPasteFromSubAppCommand extends MoveElementFromSubAppCommand {

	private CopyStatus copyStatus;

	/** This enum describes the status from the element which should be removed from the subapp Either is in the initial
	 * position (BEFORE_REMOVED_FROM_SUBAPP) or in the clipboard(REMOVED_FROM_SUBAPP), or afterwards pasted(INSERTED)
	 *
	 * @author Michael Oberlehner */
	private enum CopyStatus {
		BEFORE_REMOVED_FROM_SUBAPP, REMOVED_FROM_SUBAPP, INSERTED
	}

	public CutAndPasteFromSubAppCommand(final FBNetworkElement element, final Rectangle targetRect) {
		super(element, targetRect, MoveOperation.CONTEXT_MENU);
		this.copyStatus = CopyStatus.BEFORE_REMOVED_FROM_SUBAPP;

	}

	@Override
	public void undo() {
		switch (copyStatus) {
		case BEFORE_REMOVED_FROM_SUBAPP:
			break;
		case REMOVED_FROM_SUBAPP:
			undoRemoveFromSubApp();
			copyStatus = CopyStatus.INSERTED;
			break;
		case INSERTED:
			undoRemoveFromSubApp();
			undoMoveToParent();
			copyStatus = CopyStatus.BEFORE_REMOVED_FROM_SUBAPP;
			break;
		default:
			break;
		}
	}

	@Override
	public void redo() {
		switch (copyStatus) {
		case BEFORE_REMOVED_FROM_SUBAPP:
			redoMoveToParent();
			redoRemoveFromSubapp();
			copyStatus = CopyStatus.INSERTED;
			break;
		case REMOVED_FROM_SUBAPP:
			redoMoveToParent();
			copyStatus = CopyStatus.INSERTED;
			break;
		case INSERTED:
			redoRemoveFromSubapp();
			copyStatus = CopyStatus.REMOVED_FROM_SUBAPP;
			break;
		default:
			break;
		}
	}

	@Override
	public void execute() {
		switch (copyStatus) {
		case BEFORE_REMOVED_FROM_SUBAPP:
			removeElementFromSubapp();
			copyStatus = CopyStatus.REMOVED_FROM_SUBAPP;
			break;
		case REMOVED_FROM_SUBAPP:
			moveElementToParent();
			copyStatus = CopyStatus.INSERTED;
			break;
		default:
			break;
		}
	}

	public SubApp getSourceSubApp() {
		return sourceSubApp;
	}

	@Override
	public FBNetworkElement getElement() {
		return element;
	}
}

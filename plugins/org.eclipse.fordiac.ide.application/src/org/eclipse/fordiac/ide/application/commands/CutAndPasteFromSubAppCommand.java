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

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.swt.graphics.Point;

public class CutAndPasteFromSubAppCommand extends MoveElementsFromSubAppCommand {

	private CopyStatus copyStatus;

	/** This enum describes the status from the element which should be removed from the subapp Either is in the initial
	 * position (BEFORE_REMOVED_FROM_SUBAPP) or in the clipboard(REMOVED_FROM_SUBAPP), or afterwards pasted(INSERTED)
	 *
	 * @author Michael Oberlehner */
	private enum CopyStatus {
		BEFORE_REMOVED_FROM_SUBAPP, REMOVED_FROM_SUBAPP, INSERTED
	}

	public CutAndPasteFromSubAppCommand(final Collection<FBNetworkElement> elements, final Point destination) {
		super(elements, destination);
		this.copyStatus = CopyStatus.BEFORE_REMOVED_FROM_SUBAPP;

	}

	@Override
	public boolean canExecute() {
		return copyStatus == CopyStatus.REMOVED_FROM_SUBAPP || super.canExecute();
	}

	@Override
	public void undo() {
		switch (copyStatus) {
		case BEFORE_REMOVED_FROM_SUBAPP:
			break;
		case REMOVED_FROM_SUBAPP:
			undoRemoveElementsFromSubapp();
			copyStatus = CopyStatus.INSERTED;
			break;
		case INSERTED:
			undoRemoveElementsFromSubapp();
			undoAddElementsToDestination();
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
			redoAddElementsToDestination();
			redoRemoveElementsFromSubapp();
			copyStatus = CopyStatus.INSERTED;
			break;
		case REMOVED_FROM_SUBAPP:
			redoAddElementsToDestination();
			copyStatus = CopyStatus.INSERTED;
			break;
		case INSERTED:
			redoRemoveElementsFromSubapp();
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
			removeElementsFromSubapp();
			if (!createSubAppInterfaceElementCommands.isEmpty()) {
				createSubAppInterfaceElementCommands.undo();
			}
			copyStatus = CopyStatus.REMOVED_FROM_SUBAPP;
			break;
		case REMOVED_FROM_SUBAPP:
			if (!createSubAppInterfaceElementCommands.isEmpty()) {
				createSubAppInterfaceElementCommands.redo();
			}
			addElementsToDestination();
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
	public List<FBNetworkElement> getElements() {
		return elements;
	}

	public void setPastePos(final Point pasteRefPosition) {
		setDestination(pasteRefPosition);
	}
}

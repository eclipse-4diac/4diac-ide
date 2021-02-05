/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2018 - 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *   - initial API and implementation and/or initial documentation
 *   Alois Zoitl  - reworked to make it usable for AddElementsToSubApp
 *   Alois Zoitl - moved openEditor helper function to EditorUtils
 *   Bianca Wiesmayr - fix position of elements within subapp
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.List;

import org.eclipse.fordiac.ide.gef.handlers.BreadcrumbUtil;
import org.eclipse.fordiac.ide.model.commands.change.MapToCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;

public class NewSubAppCommand extends AbstractCreateFBNetworkElementCommand {
	private final List<?> parts;
	private final AddElementsToSubAppCommand addElements;
	private MapToCommand mapSubappCmd; // can not be in the compound command as it needs to be performed when
	// subapp interface is finished

	public NewSubAppCommand(FBNetwork fbNetwork, List<?> selection, int x, int y) {
		super(fbNetwork, LibraryElementFactory.eINSTANCE.createSubApp(), x, y);
		getSubApp().setSubAppNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());
		addElements = new AddElementsToSubAppCommand(getSubApp(), selection);
		checkMapping(selection);
		parts = selection;
	}

	@Override
	public boolean canExecute() {
		return super.canExecute() && allElementsInSameFBnetwork();
	}

	private boolean allElementsInSameFBnetwork() {
		final FBNetworkElement el;
		for (final Object o : parts) {
			if (o instanceof EditPart) {
				final Object model = ((EditPart) o).getModel();
				if ((model instanceof FBNetworkElement)
						&& (((FBNetworkElement) model).getFbNetwork() != getFBNetwork())) {
					// an element is not in the target fbnetwork
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void execute() {
		super.execute();
		addElements.execute();
		if (null != mapSubappCmd) {
			mapSubappCmd.execute();
		}
	}

	@Override
	public void redo() {
		super.redo();
		addElements.redo();
		if (null != mapSubappCmd) {
			mapSubappCmd.redo();
		}
	}

	@Override
	public void undo() {
		closeOpenedSubApp();
		if (null != mapSubappCmd) {
			mapSubappCmd.undo();
		}
		addElements.undo(); // this has to be done bevor super.undo() as otherwise addElements does not have
		// the correct networks.
		super.undo();
	}

	private void checkMapping(List<?> selection) {
		Resource res = null;
		for (final Object ne : selection) {
			if ((ne instanceof EditPart) && (((EditPart) ne).getModel() instanceof FBNetworkElement)) {
				final FBNetworkElement element = (FBNetworkElement) ((EditPart) ne).getModel();
				if (element.isMapped()) {
					if (null == res) {
						// this is the first element
						res = element.getResource();
					} else if (res != element.getResource()) {
						return; // we have elements mapped to different entities
					}
				} else {
					return; // we have at least one unmapped element so we will not mapp the whole subapp
				}
			}
		}
		if (null != res) {
			mapSubappCmd = new MapToCommand(getSubApp(), res);
		}
	}

	@Override
	protected final InterfaceList getTypeInterfaceList() {
		return LibraryElementFactory.eINSTANCE.createInterfaceList();
	}

	@Override
	protected String getInitalInstanceName() {
		return "SubApp"; //$NON-NLS-1$
	}

	private void closeOpenedSubApp() {
		BreadcrumbUtil.openParentEditor(getSubApp());
	}

	private SubApp getSubApp() {
		return (SubApp) getElement();
	}
}

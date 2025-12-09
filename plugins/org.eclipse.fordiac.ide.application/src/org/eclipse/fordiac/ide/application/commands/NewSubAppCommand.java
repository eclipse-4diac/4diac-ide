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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.model.commands.QualNameAffectedCommand;
import org.eclipse.fordiac.ide.model.commands.change.MapToCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractCreateFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;

public class NewSubAppCommand extends AbstractCreateFBNetworkElementCommand implements QualNameAffectedCommand {
	private final List<?> parts;
	private final AddElementsToSubAppCommand addElements;
	private Command mapSubappCmd; // can not be in the compound command as it needs to be performed when
	// subapp interface is finished

	private final Map<INamedElement, String> oldQualNames = new HashMap<>(); // store qualnames for plant hierchachy
																				// update
	private final Map<INamedElement, String> newQualNames = new HashMap<>();

	public NewSubAppCommand(final FBNetwork fbNetwork, final List<?> selection, final Position pos) {
		super(fbNetwork, LibraryElementFactory.eINSTANCE.createUntypedSubApp(), pos);
		getElement().setSubAppNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());
		addElements = new AddElementsToSubAppCommand(getElement(), selection);
		checkMapping(selection);
		parts = selection;
	}

	public NewSubAppCommand(final FBNetwork fbNetwork, final List<?> selection, final int x, final int y) {
		super(fbNetwork, LibraryElementFactory.eINSTANCE.createUntypedSubApp(), x, y);
		getElement().setSubAppNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());
		addElements = new AddElementsToSubAppCommand(getElement(), selection);
		checkMapping(selection);
		parts = selection;
	}

	@Override
	public boolean canExecute() {
		return super.canExecute() && allElementsInSameFBnetwork();
	}

	private boolean allElementsInSameFBnetwork() {
		for (final Object o : parts) {
			if (o instanceof final EditPart editPart) {
				final Object model = editPart.getModel();
				if ((model instanceof final FBNetworkElement networkElement)
						&& (networkElement.getFbNetwork() != getFBNetwork())) {
					// an element is not in the target fbnetwork
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void execute() {
		addElements.getElements().forEach(e -> oldQualNames.put(e, e.getQualifiedName()));
		super.execute();
		addElements.execute();
		if (null != mapSubappCmd) {
			mapSubappCmd.execute();
		}
		addElements.getElements().forEach(e -> newQualNames.put(e, e.getQualifiedName()));
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
		if (null != mapSubappCmd) {
			mapSubappCmd.undo();
		}
		// this has to be done before super.undo() as otherwise addElements does not
		// have the correct networks.
		addElements.undo();
		super.undo();
	}

	private void checkMapping(final List<?> selection) {
		Resource res = null;
		for (final Object ne : selection) {
			if ((ne instanceof final EditPart editPart)
					&& (editPart.getModel() instanceof final FBNetworkElement element)) {
				if (!element.isMapped()) {
					return; // we have at least one unmapped element so we will not mapp the whole subapp
				}
				if (null == res) {
					// this is the first element
					res = element.getResource();
				} else if (res != element.getResource()) {
					return; // we have elements mapped to different entities
				}
			}
		}
		if (null != res) {
			mapSubappCmd = MapToCommand.createMapToCommand(getElement(), res);
		}
	}

	@Override
	protected final InterfaceList createInterfaceList() {
		return LibraryElementFactory.eINSTANCE.createInterfaceList();
	}

	@Override
	protected String getInitialInstanceName() {
		return "SubApp"; //$NON-NLS-1$
	}

	@Override
	public UntypedSubApp getElement() {
		return (UntypedSubApp) super.getElement();
	}

	@Override
	public String getOldQualName(final INamedElement elemt) {
		return oldQualNames.get(elemt);
	}

	@Override
	public String getNewQualName(final INamedElement element) {
		return newQualNames.get(element);
	}

	@Override
	public List<INamedElement> getChangedElements() {
		return new ArrayList<>(addElements.getElements());
	}

}

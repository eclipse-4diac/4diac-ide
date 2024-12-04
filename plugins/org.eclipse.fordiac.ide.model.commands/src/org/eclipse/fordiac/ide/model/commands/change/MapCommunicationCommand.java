/*******************************************************************************
 * Copyright (c) 2023, 2024 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiemayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.helpers.InterfaceListCopier;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationChannel;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

public class MapCommunicationCommand extends MapToCommand {
	private final CommunicationMappingTarget target;

	public MapCommunicationCommand(final FBNetworkElement srcElement, final CommunicationMappingTarget segmentPart) {
		super(srcElement, segmentPart);
		this.target = segmentPart;
	}

	@Override
	public boolean canExecute() {
		return srcElement instanceof CommunicationChannel;
	}

	@Override
	protected void checkConnections() {
		// nothing to be done for communication channels
	}

	@Override
	protected FBNetworkElement createTargetElement() {
		final CommunicationChannel comm = LibraryElementFactory.eINSTANCE.createCommunicationChannel();
		comm.setName(srcElement.getName());
		comm.setPosition(EcoreUtil.copy(srcElement.getPosition()));
		comm.setTypeEntry(srcElement.getTypeEntry());
		comm.setInterface(InterfaceListCopier.copy(srcElement.getInterface(), true, true));
		target.getMappedElements().add(comm);
		return comm;
	}

	@Override
	protected void addMappedElements() {
		target.getMappedElements().add((CommunicationChannel) targetElement);
	}

	@Override
	protected void removeMappedElements() {
		target.getMappedElements().remove(targetElement);
	}

	@Override
	protected FBNetwork getTargetFBNetwork() {
		return null;
	}
}

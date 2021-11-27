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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

public class CreateGroupCommand extends AbstractCreateFBNetworkElementCommand {

	private static final String INITIAL_GROUP_NAME = "__Group01"; //$NON-NLS-1$

	protected CreateGroupCommand(final FBNetwork fbNetwork, final int x, final int y) {
		super(fbNetwork, LibraryElementFactory.eINSTANCE.createGroup(), x, y);
	}

	@Override
	protected final InterfaceList getTypeInterfaceList() {
		return LibraryElementFactory.eINSTANCE.createInterfaceList();
	}

	@Override
	protected String getInitalInstanceName() {
		return INITIAL_GROUP_NAME;
	}

}

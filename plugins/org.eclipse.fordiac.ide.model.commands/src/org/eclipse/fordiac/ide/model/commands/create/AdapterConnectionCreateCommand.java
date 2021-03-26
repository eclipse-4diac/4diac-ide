/*******************************************************************************
 * Copyright (c) 2016, 2017 fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - reworked and harmonized source/target checking 551042
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

public class AdapterConnectionCreateCommand extends AbstractConnectionCreateCommand {

	public AdapterConnectionCreateCommand(final FBNetwork parent) {
		super(parent);
	}

	@Override
	protected Connection createConnectionElement() {
		return LibraryElementFactory.eINSTANCE.createAdapterConnection();
	}

	@Override
	public boolean canExecute() {
		if (!super.canExecute()) {
			return false;
		}
		return LinkConstraints.canCreateAdapterConnection((AdapterDeclaration) getSource(),
				(AdapterDeclaration) getDestination(), getParent());
	}

	@Override
	protected AbstractConnectionCreateCommand createMirroredConnectionCommand(final FBNetwork fbNetwork) {
		return new AdapterConnectionCreateCommand(fbNetwork);
	}

	@Override
	protected Class getInterfaceType() {
		return AdapterDeclaration.class;
	}

}

/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer
 *       - initial API and implementation and/or initial documentation
 *   Alois Zoitl - extracted from ChangeDataTypeCommand
 *   Martin Jobst - refactored additional commands
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

public final class ChangeAdapterFBCommand extends UpdateFBTypeCommand {

	public ChangeAdapterFBCommand(final AdapterDeclaration adpDecl) {
		super(adpDecl.getAdapterFB(), null);
	}

	@Override
	protected FBNetworkElement createCopiedFBEntry(final FBNetworkElement srcElement) {
		final AdapterFB copy = LibraryElementFactory.eINSTANCE.createAdapterFB();
		if (null == getEntry()) {
			copy.setTypeEntry(srcElement.getTypeEntry());
		} else {
			copy.setTypeEntry(getEntry());
		}
		copy.setAdapterDecl(((AdapterFB) srcElement).getAdapterDecl());
		return copy;
	}

	@Override
	public void execute() {
		// use the type of the adapterDecl for changing the adapterfbs type
		setEntry(getOldElement().getAdapterDecl().getType().getTypeEntry());
		super.execute();
		setAdapterFB((AdapterFB) newElement);
	}

	@Override
	public void undo() {
		super.undo();
		setAdapterFB((AdapterFB) oldElement);
	}

	@Override
	public void redo() {
		super.redo();
		setAdapterFB((AdapterFB) newElement);
	}

	private static void setAdapterFB(final AdapterFB fb) {
		fb.getAdapterDecl().setAdapterFB(fb);
	}

	@Override
	public AdapterFB getOldElement() {
		return (AdapterFB) super.getOldElement();
	}
}

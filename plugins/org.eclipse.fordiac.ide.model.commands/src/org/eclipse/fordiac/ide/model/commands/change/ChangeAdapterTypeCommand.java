/*******************************************************************************
 * Copyright (c) 2008, 2022 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;

public class ChangeAdapterTypeCommand extends ChangeDataTypeCommand {

	private UpdateFBTypeCommand updateTypeCmd = null;

	public ChangeAdapterTypeCommand(final AdapterDeclaration adapter, final AdapterType adpType) {
		super(adapter, adpType);
	}

	@Override
	public void execute() {
		super.execute();
		handleAdapter();
	}

	@Override
	public void undo() {
		super.undo();
		if (null != updateTypeCmd) {
			updateTypeCmd.undo();
		}
	}

	@Override
	public void redo() {
		super.redo();
		if (null != updateTypeCmd) {
			updateTypeCmd.redo();
		}
	}

	private void handleAdapter() {
		if ((getInterfaceElement().eContainer().eContainer() instanceof CompositeFBType)
				&& (!(getInterfaceElement().eContainer().eContainer() instanceof SubAppType))) {
			updateTypeCmd = new ChangeAdapterFBCommand((AdapterDeclaration) getInterfaceElement());
			updateTypeCmd.execute();
		}
	}

	private static final class ChangeAdapterFBCommand extends UpdateFBTypeCommand {

		public ChangeAdapterFBCommand(final AdapterDeclaration adpDecl) {
			super(adpDecl.getAdapterFB(), null);
			setEntry(adpDecl.getType().getTypeEntry());
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
	}

}

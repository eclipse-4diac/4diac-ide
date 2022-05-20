/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014 Profactor GmbH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
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
 *   Alois Zoitl - fixed issues in adapter update
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class ChangeDataTypeCommand extends Command {
	private final VarDeclaration interfaceElement;
	private final DataType dataType;
	private DataType oldDataType;
	private UpdateFBTypeCommand updateTypeCmd = null;

	public ChangeDataTypeCommand(final VarDeclaration interfaceElement, final DataType dataType) {
		super();
		this.interfaceElement = interfaceElement;
		this.dataType = dataType;
	}

	@Override
	public void execute() {
		oldDataType = interfaceElement.getType();
		setNewType();
		handleAdapter();
	}

	public void handleAdapter() {
		if ((dataType instanceof AdapterType) && (interfaceElement.eContainer().eContainer() instanceof CompositeFBType)
				&& (!(interfaceElement.eContainer().eContainer() instanceof SubAppType))) {
			updateTypeCmd = new ChangeAdapterFBCommand((AdapterDeclaration) interfaceElement);
			updateTypeCmd.execute();
		}
	}

	@Override
	public void undo() {
		interfaceElement.setType(oldDataType);
		interfaceElement.setTypeName(oldDataType.getName());
		if (null != updateTypeCmd) {
			updateTypeCmd.undo();
		}
	}

	@Override
	public void redo() {
		setNewType();
		if (null != updateTypeCmd) {
			updateTypeCmd.redo();
		}
	}

	private void setNewType() {
		interfaceElement.setType(dataType);
		interfaceElement.setTypeName(dataType.getName());
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

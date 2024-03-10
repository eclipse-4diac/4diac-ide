/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Michael Hoffmann, Alois Zoitl, Monika Wenger
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.helpers.BlockInstanceFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;

public class FBCreateCommand extends AbstractCreateFBNetworkElementCommand {
	private FBTypeEntry typeEntry;

	public FBCreateCommand(final FBTypeEntry typeEntry, final FBNetwork fbNetwork, final int x, final int y) {
		super(fbNetwork, createNewFb(typeEntry), x, y);
		this.typeEntry = typeEntry;
		setLabel(Messages.FBCreateCommand_LABEL_CreateFunctionBlock);
		getFB().setTypeEntry(typeEntry);
	}

	private static FB createNewFb(final FBTypeEntry typeEntry) {
		return BlockInstanceFactory.createFBInstanceForTypeEntry(typeEntry);
	}

	// constructor to reuse this command for adapter creation
	protected FBCreateCommand(final FBTypeEntry typeEntry, final FBNetwork fbNetwork, final FBNetworkElement adapter,
			final int x, final int y) {
		super(fbNetwork, adapter, x, y);
		this.typeEntry = typeEntry;
		setLabel(Messages.FBCreateCommand_LABEL_CreateFunctionBlock);
		getFB().setTypeEntry(typeEntry);
	}

	public FB getFB() {
		return (FB) getElement();
	}

	public FBTypeEntry getTypeEntry() {
		return typeEntry;
	}

	public void setTypeEntry(final FBTypeEntry typeEntry) {
		this.typeEntry = typeEntry;
	}

	@Override
	public void execute() {
		super.execute();
		if (getFB() instanceof final Multiplexer multiplexer) {
			multiplexer.setStructTypeElementsAtInterface(
					(StructuredType) typeEntry.getType().getInterfaceList().getOutputVars().get(0).getType());
		} else if (getFB() instanceof final Demultiplexer demux) {
			demux.setStructTypeElementsAtInterface(
					(StructuredType) typeEntry.getType().getInterfaceList().getInputVars().get(0).getType());
		}
	}

	@Override
	public boolean canExecute() {
		return (typeEntry != null) && super.canExecute();
	}

	@Override
	protected InterfaceList createInterfaceList() {
		return typeEntry.getType().getInterfaceList().copy();
	}

}
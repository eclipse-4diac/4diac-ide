/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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

import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;

public class FBCreateCommand extends AbstractCreateFBNetworkElementCommand {
	private FBTypePaletteEntry paletteEntry;

	public FBCreateCommand(final FBTypePaletteEntry paletteEntry, final FBNetwork fbNetwork, final int x, final int y) {
		super(fbNetwork, createNewFb(paletteEntry), x, y);
		this.paletteEntry = paletteEntry;
		setLabel(Messages.FBCreateCommand_LABLE_CreateFunctionBlock);
		getFB().setPaletteEntry(paletteEntry);
	}

	private static FB createNewFb(final FBTypePaletteEntry paletteEntry) {
		if (paletteEntry.getType().getName().equals("STRUCT_MUX")) { //$NON-NLS-1$
			return LibraryElementFactory.eINSTANCE.createMultiplexer();
		} else if (paletteEntry.getType().getName().equals("STRUCT_DEMUX")) { //$NON-NLS-1$
			return LibraryElementFactory.eINSTANCE.createDemultiplexer();
		} else if (paletteEntry.getType() instanceof CompositeFBType) {
			return LibraryElementFactory.eINSTANCE.createCFBInstance();
		} else {
			return LibraryElementFactory.eINSTANCE.createFB();
		}
	}

	// constructor to reuse this command for adapter creation
	protected FBCreateCommand(final FBNetwork fbNetwork, final FBNetworkElement adapter, final int x, final int y) {
		super(fbNetwork, adapter, x, y);
		this.paletteEntry = null;
		setLabel(Messages.FBCreateCommand_LABLE_CreateFunctionBlock);
		getFB().setPaletteEntry(paletteEntry);
	}

	public FB getFB() {
		return (FB) getElement();
	}

	public FBTypePaletteEntry getPaletteEntry() {
		return paletteEntry;
	}

	public void setPaletteEntry(final FBTypePaletteEntry paletteEntry) {
		this.paletteEntry = paletteEntry;
	}

	@Override
	public void execute() {
		super.execute();
		if (getFB() instanceof Multiplexer) {
			((Multiplexer) getFB()).setStructTypeElementsAtInterface(
					(StructuredType) paletteEntry.getType().getInterfaceList().getOutputVars().get(0).getType());
		} else if (getFB() instanceof Demultiplexer) {
			((Demultiplexer) getFB()).setStructTypeElementsAtInterface(
					(StructuredType) paletteEntry.getType().getInterfaceList().getInputVars().get(0).getType());
		}
	}

	@Override
	public boolean canExecute() {
		return (paletteEntry != null) && super.canExecute();
	}

	@Override
	protected InterfaceList getTypeInterfaceList() {
		return paletteEntry.getType().getInterfaceList();
	}

}
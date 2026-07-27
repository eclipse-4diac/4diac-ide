/*******************************************************************************
 * Copyright (c) 2008, 2025 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz,
 *                          Primetals Technologies Austria GmbH
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
 *   Bianca Wiesmayr - adapted ChangeTypeCommand for multiplexer use, sets struct
 *   Daniel Lindhuber - struct update
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class ChangeStructCommand extends AbstractUpdateBlockFBNElementCommand {

	private final TypeEntry newStructTypeEntry;
	private final String newVisibleChildren;

	public ChangeStructCommand(final BlockFBNetworkElement fb, final DataType newStruct) {
		super(fb);
		this.newStructTypeEntry = (newStruct != null) ? newStruct.getTypeEntry() : null;
		this.entry = fb.getTypeEntry();
		this.newVisibleChildren = null;
	}

	public ChangeStructCommand(final StructManipulator mux) {
		this(mux, mux.getDataType(), "");
	}

	public ChangeStructCommand(final StructManipulator mux, final DataType newStruct) {
		this(mux, newStruct, "");
	}

	public ChangeStructCommand(final Demultiplexer demux, final String newVisibleChildren) {
		this(demux, demux.getDataType(), newVisibleChildren);
	}

	// only to avoid code duplication, public constructors ensure correct set-up
	private ChangeStructCommand(final StructManipulator demux, final DataType datatype,
			final String newVisibleChildren) {
		super(demux);
		// use type entry to ensure that the latest version is loaded, for unconfigured
		// datatype is null
		newStructTypeEntry = (datatype != null) ? datatype.getTypeEntry() : null;
		entry = demux.getTypeEntry();
		this.newVisibleChildren = newVisibleChildren;
	}

	@Override
	protected BlockFBNetworkElement createCopiedFBEntry(final BlockFBNetworkElement srcElement) {
		BlockFBNetworkElement copy = null;

		if (srcElement instanceof Multiplexer) {
			copy = LibraryElementFactory.eINSTANCE.createMultiplexer();
		} else if (srcElement instanceof Demultiplexer) {
			copy = LibraryElementFactory.eINSTANCE.createDemultiplexer();
		} else if (srcElement instanceof ConfigurableFB) {
			copy = LibraryElementFactory.eINSTANCE.createConfigurableMoveFB();
		}
		if (copy != null) {
			copy.setTypeEntry(entry);
		}
		return copy;
	}

	@Override
	protected void handleConfigurableFB() {
		if (newStructTypeEntry != null) {
			if (getNewElement() instanceof StructManipulator) {
				getNewMux().setDataType(getDataTypeFromTypeEntry());
			} else if (getNewElement() instanceof final ConfigurableFB confFB) {
				confFB.setDataType(getDataTypeFromTypeEntry());
			}

		}
		if (isDemuxConfiguration()) {
			getNewMux().loadConfiguration(LibraryElementTags.DEMUX_VISIBLE_CHILDREN, newVisibleChildren);
		} else if (getNewElement() instanceof final ConfigurableFB confFB) {
			confFB.updateConfiguration();
		}
	}

	private boolean isDemuxConfiguration() {
		return newElement instanceof Demultiplexer && newVisibleChildren != null;
	}

	public StructManipulator getNewMux() {
		return (StructManipulator) newElement;
	}

	public StructManipulator getOldMux() {
		return (StructManipulator) oldElement;
	}

	private DataType getDataTypeFromTypeEntry() {
		if (newStructTypeEntry == null) {
			return IecTypes.GenericTypes.ANY_STRUCT;
		}

		return (newStructTypeEntry.getType() instanceof final DataType dt) ? dt : IecTypes.GenericTypes.ANY_STRUCT;
	}
}

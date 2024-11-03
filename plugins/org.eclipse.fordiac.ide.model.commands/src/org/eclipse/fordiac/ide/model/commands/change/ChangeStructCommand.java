/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014 Profactor GmbH, fortiss GmbH
 * 				 2019 - 2021 Johannes Kepler University Linz
 * 				 2021 Primetals Technologies Austria GmbH
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

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableFBManagement;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.ErrorDataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class ChangeStructCommand extends AbstractUpdateFBNElementCommand {

	private final TypeEntry newStructTypeEntry;
	private final String newVisibleChildren;
	private boolean reloadDatatype = true;

	public ChangeStructCommand(final StructManipulator mux) {
		this(mux, mux.getDataType(), getOldVisibleChildren(mux));
	}

	public ChangeStructCommand(final StructManipulator mux, final DataType newStruct) {
		this(mux, newStruct, getOldVisibleChildren(mux));
	}

	public ChangeStructCommand(final StructManipulator mux, final DataType newStruct, final boolean doNotReload) {
		this(mux, newStruct, getOldVisibleChildren(mux));
		reloadDatatype = !doNotReload;
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

	private static String getOldVisibleChildren(final StructManipulator mux) {
		if (mux instanceof final Demultiplexer demux && demux.isIsConfigured()) {
			return ConfigurableFBManagement.buildVisibleChildrenString(demux.getMemberVars());
		}
		return null;
	}

	@Override
	protected FBNetworkElement createCopiedFBEntry(final FBNetworkElement srcElement) {
		FBNetworkElement copy = null;

		if (srcElement instanceof Multiplexer) {
			copy = LibraryElementFactory.eINSTANCE.createMultiplexer();
		} else if (srcElement instanceof Demultiplexer) {
			copy = LibraryElementFactory.eINSTANCE.createDemultiplexer();
		}
		if (copy != null) {
			copy.setTypeEntry(entry);
		}
		return copy;
	}

	@Override
	protected void handleConfigurableFB() {
		if (newStructTypeEntry != null) {
			getNewMux().setDataType(getDataTypeFromTypeEntry());
		}
		if (isDemuxConfiguration()) {
			getNewMux().loadConfiguration(LibraryElementTags.DEMUX_VISIBLE_CHILDREN, newVisibleChildren);
		} else {
			getNewMux().updateConfiguration();
		}
	}

	private boolean isDemuxConfiguration() {
		if (newElement instanceof final Demultiplexer demux) {
			return demux.isIsConfigured() || newVisibleChildren != null;
		}
		return false;
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

		LibraryElement type = newStructTypeEntry.getType();
		if (reloadDatatype) {
			final DataTypeLibrary datatypeLib = entry.getTypeLibrary().getDataTypeLibrary();
			final TypeEntry reloadedTypeEntry = datatypeLib.getDerivedTypeEntry(newStructTypeEntry.getFullTypeName());
			if (newStructTypeEntry instanceof ErrorDataTypeEntry) {
				if (reloadedTypeEntry != null && reloadedTypeEntry != newStructTypeEntry) {
					// type exists now
					type = reloadedTypeEntry.getType();
				}
			} else if (reloadedTypeEntry == null) {
				// type was deleted, create error marker
				type = datatypeLib.createErrorMarkerType(newStructTypeEntry.getFullTypeName(), MessageFormat
						.format("Typeentry for StructManipulator `{0}` not available!", getOldMux().getName())); //$NON-NLS-1$
			}
		}
		return (type instanceof final DataType dt) ? dt : IecTypes.GenericTypes.ANY_STRUCT;
	}
}

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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableFBManagement;

public class ChangeStructCommand extends AbstractUpdateFBNElementCommand {

	private final DataType newStruct;
	private final String newVisibleChildren;

	public ChangeStructCommand(final StructManipulator mux) {
		this(mux, mux.getDataType(), getOldVisibleChildren(mux));
	}

	public ChangeStructCommand(final StructManipulator mux, final StructuredType newStruct) {
		this(mux, newStruct, getOldVisibleChildren(mux));
	}

	public ChangeStructCommand(final Demultiplexer demux, final String newVisibleChildren) {
		this(demux, demux.getDataType(), newVisibleChildren);
	}

	// only to avoid code duplication, public constructors ensure correct set-up
	private ChangeStructCommand(final StructManipulator demux, final DataType datatype,
			final String newVisibleChildren) {
		super(demux);
		newStruct = datatype;
		entry = demux.getTypeEntry();
		this.newVisibleChildren = newVisibleChildren;
	}

	private static String getOldVisibleChildren(final StructManipulator mux) {
		if (mux instanceof final Demultiplexer demux && demux.isIsConfigured()) {
			return ConfigurableFBManagement.buildVisibleChildrenString(demux);
		}
		return null;
	}

	@Override
	protected void createNewFB() {
		if (oldElement instanceof Multiplexer) {
			newElement = LibraryElementFactory.eINSTANCE.createMultiplexer();
		} else if (oldElement instanceof Demultiplexer) {
			newElement = LibraryElementFactory.eINSTANCE.createDemultiplexer();
		}
		newElement.setTypeEntry(entry);
		setInterface();
		handleConfigurableFB();
		newElement.setName(oldElement.getName());

		newElement.setPosition(EcoreUtil.copy(oldElement.getPosition()));
		copyAttributes();

		createValues();
		transferInstanceComments();
	}

	@Override
	protected void handleConfigurableFB() {
		if (newStruct != null) {
			getNewMux().setDataType(newStruct);
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

	protected void copyAttributes() {
		newElement.getAttributes().addAll(EcoreUtil.copyAll(oldElement.getAttributes()));
	}

	public StructManipulator getNewMux() {
		return (StructManipulator) newElement;
	}

	public StructManipulator getOldMux() {
		return (StructManipulator) oldElement;
	}
}

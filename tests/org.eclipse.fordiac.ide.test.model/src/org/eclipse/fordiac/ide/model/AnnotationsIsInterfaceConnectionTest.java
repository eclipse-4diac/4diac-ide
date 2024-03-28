/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.junit.jupiter.api.Test;

class AnnotationsIsInterfaceConnectionTest {

	static final String STRUCT_NAME = "aStruct"; //$NON-NLS-1$
	static final String STRUCT_ELEMENT_1 = "first"; //$NON-NLS-1$
	static final String STRUCT_ELEMENT_2 = "second"; //$NON-NLS-1$
	static final String SUBAPP_NAME = "aSubApp"; //$NON-NLS-1$
	static final String SUBAPP_INPUT_NAME = "interfaceInput"; //$NON-NLS-1$
	static final String SUBAPP_OUTPUT_NAME = "interfaceOutput"; //$NON-NLS-1$
	static final String MUXDEMUX_EVENT_IN = "eIn"; //$NON-NLS-1$
	static final String MUXDEMUX_EVENT_OUT = "eOut"; //$NON-NLS-1$
	static final String MUX_OUT = "OUT"; //$NON-NLS-1$
	static final String DEMUX_IN = "IN"; //$NON-NLS-1$
	static final String MUX_NAME = "MUX"; //$NON-NLS-1$
	static final String DEMUX_NAME = "DEMUX"; //$NON-NLS-1$

	@SuppressWarnings("static-method")
	@Test
	void busOnOutside() {
		final var struct = createStruct();
		final var muxblock = createMux(struct);
		final var demuxblock = createDemux(struct);
		final var subapp = createSubappBusOutside(struct, muxblock, demuxblock);

		addVerifyConnection(subapp, //
				subapp.getInterfaceElement(SUBAPP_INPUT_NAME), //
				demuxblock.getInterfaceElement(DEMUX_IN), //
				true //
		);

		addVerifyConnection(subapp, //
				muxblock.getInterfaceElement(MUX_OUT), //
				subapp.getInterfaceElement(SUBAPP_OUTPUT_NAME), //
				true //
		);

		addVerifyConnection(subapp, //
				demuxblock.getInterfaceElement(STRUCT_ELEMENT_1), //
				muxblock.getInterfaceElement(STRUCT_ELEMENT_1), //
				true //
		);

		addVerifyConnection(subapp, //
				demuxblock.getInterfaceElement(STRUCT_ELEMENT_2), //
				muxblock.getInterfaceElement(STRUCT_ELEMENT_2), //
				true //
		);

	}

	@SuppressWarnings("squid:S5960")
	private static DataConnection addVerifyConnection(final SubApp subapp, final IInterfaceElement source,
			final IInterfaceElement destination, final boolean expected) {
		final var conn = LibraryElementFactory.eINSTANCE.createDataConnection();
		conn.setSource(source);
		conn.setDestination(destination);
		subapp.getSubAppNetwork().addConnection(conn);

		assertEquals(Boolean.valueOf(expected), Boolean.valueOf(Annotations.isInterfaceConnection(conn)));
		return conn;
	}

	private static SubApp createSubappBusOutside(final StructuredType struct, final Multiplexer muxblock,
			final Demultiplexer demuxblock) {
		final var subapp = LibraryElementFactory.eINSTANCE.createUntypedSubApp();

		final var input = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		input.setType(struct);
		input.setName(SUBAPP_INPUT_NAME);
		final var output = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		output.setType(struct);
		output.setName(SUBAPP_OUTPUT_NAME);

		subapp.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());

		subapp.setSubAppNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());

		subapp.getInterface().getInputVars().add(input);
		subapp.getInterface().getOutputVars().add(output);

		subapp.getSubAppNetwork().getNetworkElements().add(muxblock);

		subapp.getSubAppNetwork().getNetworkElements().add(demuxblock);
		return subapp;
	}

	@SuppressWarnings("static-method")
	@Test
	void busOnInside() {

		final var struct = createStruct();
		final var muxblock = createMux(struct);
		final var demuxblock = createDemux(struct);
		final var subapp = createSubappBusInside(muxblock, demuxblock);

		addVerifyConnection(subapp, //
				subapp.getInterfaceElement(SUBAPP_INPUT_NAME), //
				muxblock.getInterfaceElement(STRUCT_ELEMENT_1), //
				true //
		);

		addVerifyConnection(subapp, //
				subapp.getInterfaceElement(SUBAPP_INPUT_NAME), //
				muxblock.getInterfaceElement(STRUCT_ELEMENT_2), //
				true //
		);

		addVerifyConnection(subapp, //
				demuxblock.getInterfaceElement(STRUCT_ELEMENT_1), //
				subapp.getInterfaceElement(SUBAPP_OUTPUT_NAME), //
				true //
		);

		addVerifyConnection(subapp, //
				demuxblock.getInterfaceElement(STRUCT_ELEMENT_2), //
				subapp.getInterfaceElement(SUBAPP_OUTPUT_NAME), //
				true //
		);

		addVerifyConnection(subapp, //
				muxblock.getInterfaceElement(MUX_OUT), //
				demuxblock.getInterfaceElement(DEMUX_IN), //
				true //
		);

	}

	private static SubApp createSubappBusInside(final Multiplexer muxblock, final Demultiplexer demuxblock) {
		final var subapp = LibraryElementFactory.eINSTANCE.createUntypedSubApp();

		final var input = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		input.setType(IecTypes.ElementaryTypes.DINT);
		input.setName(SUBAPP_INPUT_NAME);
		final var output = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		output.setType(IecTypes.ElementaryTypes.DINT);
		output.setName(SUBAPP_OUTPUT_NAME);

		subapp.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());

		subapp.setSubAppNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());

		subapp.getInterface().getInputVars().add(input);
		subapp.getInterface().getOutputVars().add(output);

		subapp.getSubAppNetwork().getNetworkElements().add(muxblock);

		subapp.getSubAppNetwork().getNetworkElements().add(demuxblock);
		return subapp;
	}

	private static StructuredType createStruct() {
		final var struct = DataFactory.eINSTANCE.createStructuredType();
		struct.setName(STRUCT_NAME);

		final var a = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		a.setName(STRUCT_ELEMENT_1);
		a.setType(IecTypes.ElementaryTypes.DINT);
		final var b = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		b.setName(STRUCT_ELEMENT_2);
		b.setType(IecTypes.ElementaryTypes.DINT);

		struct.getMemberVariables().add(a);
		struct.getMemberVariables().add(b);
		return struct;
	}

	private static Multiplexer createMux(final StructuredType struct) {
		final var muxblock = LibraryElementFactory.eINSTANCE.createMultiplexer();
		muxblock.setName(MUX_NAME);
		muxblock.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());

		final var muxEvIn = LibraryElementFactory.eINSTANCE.createEvent();
		muxEvIn.setName(MUXDEMUX_EVENT_IN);
		final var muxEvOut = LibraryElementFactory.eINSTANCE.createEvent();
		muxEvOut.setName(MUXDEMUX_EVENT_OUT);
		muxblock.getInterface().getEventInputs().add(muxEvIn);
		muxblock.getInterface().getEventOutputs().add(muxEvOut);

		final var muxOut = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		muxOut.setName(MUX_OUT);
		muxblock.getInterface().getOutputVars().add(muxOut);

		muxblock.setStructTypeElementsAtInterface(struct);
		return muxblock;
	}

	private static Demultiplexer createDemux(final StructuredType struct) {
		final var demuxblock = LibraryElementFactory.eINSTANCE.createDemultiplexer();
		demuxblock.setName(DEMUX_NAME);
		demuxblock.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());

		final var demuxEvIn = LibraryElementFactory.eINSTANCE.createEvent();
		demuxEvIn.setName(MUXDEMUX_EVENT_IN);
		final var demuxEvOut = LibraryElementFactory.eINSTANCE.createEvent();
		demuxEvOut.setName(MUXDEMUX_EVENT_OUT);
		demuxblock.getInterface().getEventInputs().add(demuxEvIn);
		demuxblock.getInterface().getEventOutputs().add(demuxEvOut);

		final var demuxIn = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		demuxIn.setName(DEMUX_IN);
		demuxblock.getInterface().getInputVars().add(demuxIn);

		demuxblock.setStructTypeElementsAtInterface(struct);
		return demuxblock;
	}

}

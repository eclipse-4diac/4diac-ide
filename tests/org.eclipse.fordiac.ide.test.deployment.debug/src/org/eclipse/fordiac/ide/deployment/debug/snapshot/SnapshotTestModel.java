/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.snapshot;

import java.util.List;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.test.model.typelibrary.FBTypeEntryMock;

/** Builds the model elements for the snapshot tests. */
final class SnapshotTestModel {

	static final String RESOURCE_NAME = "RES"; //$NON-NLS-1$
	static final String APPLICATION_NAME = "APP"; //$NON-NLS-1$
	static final String FB_NAME = "Counter"; //$NON-NLS-1$

	private static final LibraryElementFactory FACTORY = LibraryElementFactory.eINSTANCE;

	/**
	 * A simple FB type with the events INC and CNF, the input STEP, the output
	 * COUNT, the internal variable total and the internal constant MAX_STEP. The
	 * variables have no initial values, the tests run without the structured text
	 * evaluator.
	 */
	static SimpleFBType createSimpleFBType() {
		final SimpleFBType type = FACTORY.createSimpleFBType();
		type.setName("SD_Counter"); //$NON-NLS-1$
		final InterfaceList interfaceList = FACTORY.createInterfaceList();
		type.setInterfaceList(interfaceList);
		interfaceList.getEventInputs().add(createEvent("INC", true)); //$NON-NLS-1$
		interfaceList.getEventOutputs().add(createEvent("CNF", false)); //$NON-NLS-1$
		interfaceList.getInputVars().add(createVariable("STEP", ElementaryTypes.INT, true)); //$NON-NLS-1$
		interfaceList.getOutputVars().add(createVariable("COUNT", ElementaryTypes.INT, false)); //$NON-NLS-1$
		type.getInternalVars().add(createVariable("total", ElementaryTypes.DINT, false)); //$NON-NLS-1$
		type.getInternalConstVars().add(createVariable("MAX_STEP", ElementaryTypes.INT, false)); //$NON-NLS-1$
		type.setTypeEntry(new FBTypeEntryMock(type, null, null));
		return type;
	}

	/**
	 * A simple FB type like FB_R_TRIG with the event REQ, the input CLK, the output
	 * Q and the internal variable MEM.
	 */
	static SimpleFBType createEdgeFBType() {
		final SimpleFBType type = withTypeEntry(FACTORY.createSimpleFBType(), "SD_Edge"); //$NON-NLS-1$
		final InterfaceList interfaceList = type.getInterfaceList();
		interfaceList.getEventInputs().add(createEvent("REQ", true)); //$NON-NLS-1$
		interfaceList.getInputVars().add(createVariable("CLK", ElementaryTypes.BOOL, true)); //$NON-NLS-1$
		interfaceList.getOutputVars().add(createVariable("Q", ElementaryTypes.BOOL, false)); //$NON-NLS-1$
		type.getInternalVars().add(createVariable("MEM", ElementaryTypes.BOOL, false)); //$NON-NLS-1$
		return type;
	}

	/**
	 * A basic FB type with the event TOGGLE, the output Q, the internal variable
	 * count and the ECC states START, HIGH and LOW.
	 */
	static BasicFBType createToggleFBType() {
		final BasicFBType type = withTypeEntry(FACTORY.createBasicFBType(), "SD_Toggle"); //$NON-NLS-1$
		final InterfaceList interfaceList = type.getInterfaceList();
		interfaceList.getEventInputs().add(createEvent("TOGGLE", true)); //$NON-NLS-1$
		interfaceList.getOutputVars().add(createVariable("Q", ElementaryTypes.BOOL, false)); //$NON-NLS-1$
		type.getInternalVars().add(createVariable("count", ElementaryTypes.UDINT, false)); //$NON-NLS-1$
		final ECC ecc = FACTORY.createECC();
		type.setECC(ecc);
		for (final String name : List.of("START", "HIGH", "LOW")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			final ECState state = FACTORY.createECState();
			state.setName(name);
			ecc.getECState().add(state);
		}
		ecc.setStart(ecc.getECState().getFirst());
		return type;
	}

	/**
	 * A function with the events REQ and CNF, the input IN and the output OUT; it
	 * has no body.
	 */
	static FunctionFBType createFunctionFBType() {
		final FunctionFBType type = withTypeEntry(FACTORY.createFunctionFBType(), "SD_Double"); //$NON-NLS-1$
		final InterfaceList interfaceList = type.getInterfaceList();
		interfaceList.getEventInputs().add(createEvent("REQ", true)); //$NON-NLS-1$
		interfaceList.getEventOutputs().add(createEvent("CNF", false)); //$NON-NLS-1$
		interfaceList.getInputVars().add(createVariable("IN", ElementaryTypes.INT, true)); //$NON-NLS-1$
		interfaceList.getOutputVars().add(createVariable("OUT", ElementaryTypes.INT, false)); //$NON-NLS-1$
		return type;
	}

	static <T extends FBType> T withTypeEntry(final T type, final String name) {
		type.setName(name);
		type.setInterfaceList(FACTORY.createInterfaceList());
		type.setTypeEntry(new FBTypeEntryMock(type, null, null));
		return type;
	}

	static Event createEvent(final String name, final boolean input) {
		final Event event = FACTORY.createEvent();
		event.setName(name);
		event.setIsInput(input);
		event.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
		return event;
	}

	static VarDeclaration createVariable(final String name, final DataType type, final boolean input) {
		final VarDeclaration variable = FACTORY.createVarDeclaration();
		variable.setName(name);
		variable.setType(type);
		variable.setIsInput(input);
		return variable;
	}

	static FB createFB(final String name, final FBType type) {
		final FB fb = FACTORY.createFB();
		fb.setName(name);
		fb.setTypeEntry(type.getTypeEntry());
		return fb;
	}

	/** Internal FBs of loaded types have an interface, the evaluator uses it. */
	static FB createInternalFB(final String name, final FBType type) {
		final FB fb = createFB(name, type);
		fb.setInterface(type.getInterfaceList().instanceCopy());
		return fb;
	}

	/** Place the function block directly into the network of a resource. */
	static Resource addToResource(final FB fb) {
		final Device device = FACTORY.createDevice();
		device.setName("DEV"); //$NON-NLS-1$
		final Resource resource = FACTORY.createResource();
		resource.setName(RESOURCE_NAME);
		device.getResource().add(resource);
		final FBNetwork network = FACTORY.createFBNetwork();
		resource.setFBNetwork(network);
		network.getNetworkElements().add(fb);
		return resource;
	}

	/** Place the function block into an application of a system. */
	static Application addToApplication(final FB fb) {
		final AutomationSystem system = FACTORY.createAutomationSystem();
		system.setName("SYS"); //$NON-NLS-1$
		final Application application = FACTORY.createApplication();
		application.setName(APPLICATION_NAME);
		system.getApplication().add(application);
		final FBNetwork network = FACTORY.createFBNetwork();
		application.setFBNetwork(network);
		network.getNetworkElements().add(fb);
		return application;
	}

	/** Map the application function block to its copy in a resource. */
	static void map(final FB applicationFB, final FB resourceFB) {
		// setting the mapping checks the connections of the interface
		applicationFB.setInterface(applicationFB.getType().getInterfaceList().instanceCopy());
		resourceFB.setInterface(resourceFB.getType().getInterfaceList().instanceCopy());
		final Mapping mapping = FACTORY.createMapping();
		mapping.setFrom(applicationFB);
		mapping.setTo(resourceFB);
		applicationFB.setMapping(mapping);
		resourceFB.setMapping(mapping);
	}

	private SnapshotTestModel() {
		throw new UnsupportedOperationException();
	}
}

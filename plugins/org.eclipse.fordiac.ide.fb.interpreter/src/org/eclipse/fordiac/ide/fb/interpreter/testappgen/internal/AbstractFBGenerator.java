/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Melanie Winter - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fb.interpreter.testappgen.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public abstract class AbstractFBGenerator {

	protected BasicFBType destinationFB;
	protected TestSuite testSuite;
	protected TypeEntry entry;
	protected FBType sourceType;
	protected TestCase testCase;

	protected List<Event> inputEventList;
	protected List<Event> outputEventList;

	protected AbstractFBGenerator(final FBType type, final TestSuite testSuite, final TestCase testCase) {
		this.sourceType = type;
		this.testCase = testCase;
		this.testSuite = testSuite;
	}

	protected AbstractFBGenerator(final FBType type, final TestSuite testSuite) {
		this.sourceType = type;
		this.testSuite = testSuite;
	}

	protected void createBasicFB() {
		destinationFB = LibraryElementFactory.eINSTANCE.createBasicFBType();
		destinationFB.setECC(LibraryElementFactory.eINSTANCE.createECC());
		final ECState start = LibraryElementFactory.eINSTANCE.createECState();
		destinationFB.getECC().getECState().add(start);
		start.setName("START"); //$NON-NLS-1$
		destinationFB.getECC().setStart(start);
		final Position p0 = LibraryElementFactory.eINSTANCE.createPosition();
		p0.setX(0);
		p0.setY(0);
		start.setPosition(p0);
		final Identification id = LibraryElementFactory.eINSTANCE.createIdentification();
		destinationFB.setIdentification(id);
		id.setStandard("IEC 61499"); //$NON-NLS-1$

		destinationFB.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		destinationFB.setName(getTypeName());
		destinationFB.setService(LibraryElementFactory.eINSTANCE.createService());

		final IProject project = getSourceFB().getTypeLibrary().getProject();
		final String s = sourceType.getTypeEntry().getFile().getFullPath().toString();
		final IFolder folder = project.getFolder(s.substring(project.getName().length() + 2, s.lastIndexOf('/')));
		final IFile destfile = folder.getFile(getTypeName() + ".fbt"); //$NON-NLS-1$
		entry = getSourceFB().getTypeLibrary().createTypeEntry(destfile);
		entry.setType(destinationFB);

		addEvents();
		addDataPins();
		createWiths();
		generateECC();
	}

	private void createWiths() {
		for (final Event input : destinationFB.getInterfaceList().getEventInputs()) {
			for (final VarDeclaration varD : destinationFB.getInterfaceList().getInputVars()) {
				final With w = LibraryElementFactory.eINSTANCE.createWith();
				w.setVariables(varD);
				input.getWith().add(w);
			}
		}

		for (final Event output : destinationFB.getInterfaceList().getEventOutputs()) {
			for (final VarDeclaration varD : destinationFB.getInterfaceList().getOutputVars()) {
				final With w = LibraryElementFactory.eINSTANCE.createWith();
				w.setVariables(varD);
				output.getWith().add(w);
			}
		}
	}

	protected abstract List<Event> createInputEventList();

	protected abstract List<Event> createOutputEventList();

	protected abstract List<VarDeclaration> createInputDataList();

	protected abstract List<VarDeclaration> createOutputDataList();

	protected void addEvents() {
		destinationFB.getInterfaceList().getEventInputs().addAll(createInputEventList());
		destinationFB.getInterfaceList().getEventOutputs().addAll(createOutputEventList());

	}

	protected void addDataPins() {
		final List<VarDeclaration> varIn = createInputDataList();
		for (final VarDeclaration varD : varIn) {
			varD.setValue(LibraryElementFactory.eINSTANCE.createValue());
			varD.getValue().setValue(""); //$NON-NLS-1$
			destinationFB.getInterfaceList().getInputVars().add(varD);
		}
		final List<VarDeclaration> varOut = createOutputDataList();
		for (final VarDeclaration varD : varOut) {
			varD.setValue(LibraryElementFactory.eINSTANCE.createValue());
			varD.getValue().setValue(""); //$NON-NLS-1$
			destinationFB.getInterfaceList().getOutputVars().add(varD);
		}
	}

	protected abstract void generateECC();

	protected abstract String getTypeName();

	protected abstract FBType getSourceFB();

	protected List<Event> getExpectedEvents(final boolean isInput) {
		final List<Event> list = new ArrayList<>();
		for (final TestCase testC : testSuite.getTestCases()) {
			final StringBuilder sb = new StringBuilder();
			for (final TestState testState : testC.getTestStates()) {
				for (final OutputPrimitive prim : testState.getTestOutputs()) {
					sb.append(prim.getEvent() + "_"); //$NON-NLS-1$
				}
			}
			sb.append("expected"); //$NON-NLS-1$
			final String name = sb.toString();
			if (!containsEvent(list, name)) {
				list.add(createEvent(name, isInput));
			}
		}
		return list;
	}

	protected static String createEventName(final List<OutputPrimitive> testOutputs) {
		final StringBuilder sb = new StringBuilder();
		for (final OutputPrimitive outP : testOutputs) {
			sb.append(outP.getEvent() + "_"); //$NON-NLS-1$
		}
		sb.append("expected"); //$NON-NLS-1$
		return sb.toString();
	}

	protected static Event createEvent(final String name, final boolean isInput) {
		final Event newEv = LibraryElementFactory.eINSTANCE.createEvent();
		newEv.setIsInput(isInput);
		newEv.setType(EventTypeLibrary.getInstance().getType(EventTypeLibrary.EVENT));
		newEv.setName(name);
		return newEv;
	}

	protected static Event createEvent(final String name, final DataType dataType, final boolean isInput) {
		final Event newEv = LibraryElementFactory.eINSTANCE.createEvent();
		newEv.setIsInput(isInput);
		newEv.setType(dataType);
		newEv.setName(name);
		return newEv;
	}

	protected static VarDeclaration createVarDeclaration(final String name, final boolean isInput) {
		final VarDeclaration varDecl = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		varDecl.setName(name);
		varDecl.setIsInput(isInput);
		varDecl.setValue(LibraryElementFactory.eINSTANCE.createValue());
		varDecl.getValue().setValue(""); //$NON-NLS-1$
		return varDecl;
	}

	protected static VarDeclaration createVarDeclaration(final VarDeclaration varD, final String name,
			final boolean isInput) {
		final VarDeclaration varDecl = EcoreUtil.copy(varD);
		varDecl.setName(name);
		varDecl.setIsInput(isInput);
		varDecl.setValue(LibraryElementFactory.eINSTANCE.createValue());
		varDecl.getValue().setValue(""); //$NON-NLS-1$
		return varDecl;
	}

	protected static boolean containsEvent(final List<Event> list, final String name) {
		boolean retBool = false;
		for (final Event ev : list) {
			if (ev.getName().equals(name)) {
				retBool = true;
			}
		}
		return retBool;
	}

	public TypeEntry getTypeEntry() {
		return entry;
	}
}

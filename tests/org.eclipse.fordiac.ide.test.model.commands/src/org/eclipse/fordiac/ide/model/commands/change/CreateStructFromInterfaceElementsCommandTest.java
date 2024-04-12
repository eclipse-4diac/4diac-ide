/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha, Michael Oberlehner
 *   Lukas Wais - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.create.CreateInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateStructFromInterfaceElementsCommand;
import org.eclipse.fordiac.ide.model.commands.testinfra.CommandTestBase;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.helpers.InterfaceListCopier;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.CompoundCommand;
import org.junit.jupiter.params.provider.Arguments;

public class CreateStructFromInterfaceElementsCommandTest
		extends CommandTestBase<CreateStructFromInterfaceElementsCommandTest.State> {
	private static final String SET_STRUCT_NAME = "setStruct"; //$NON-NLS-1$

	public static class State extends CommandTestBase.StateBase {
		private final SubApp subApp;

		public State() {
			subApp = LibraryElementFactory.eINSTANCE.createUntypedSubApp();
			subApp.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
		}

		private State(final State state) {
			subApp = EcoreUtil.copy(state.subApp);
		}

		@Override
		public Object getClone() {
			return new State(this);
		}

		public SubApp getSubApp() {
			return subApp;
		}
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Prepare Functionblocks", //$NON-NLS-1$
						CreateStructFromInterfaceElementsCommandTest::createInterfaceElements, //
						CreateStructFromInterfaceElementsCommandTest::verifyFBCreation //
				), //
				new ExecutionDescription<>("Replace first element", //$NON-NLS-1$
						CreateStructFromInterfaceElementsCommandTest::testWithOneElement, //
						CreateStructFromInterfaceElementsCommandTest::verifyTestWithOneElement //
				) //
		);
		final Collection<Arguments> a = new ArrayList<>();
		a.addAll(describeCommand("FB with two inputs and outputs", State::new, //$NON-NLS-1$
				(StateVerifier<State>) CreateStructFromInterfaceElementsCommandTest::verifyInitalValues,
				executionDescriptions, CommandTestBase::defaultUndoCommand, CommandTestBase::defaultRedoCommand));
		return a;
	}

	private static void verifyInitalValues(final State s, final State o, final TestFunction t) {
		t.test(s.getSubApp());
		t.test(s.getSubApp().getInterface());
		t.test(s.getSubApp().getInterface().getAllInterfaceElements().isEmpty());
	}

	private static State createInterfaceElements(final State state) {
		final CompoundCommand c = new CompoundCommand();
		final SubApp subApp = state.getSubApp();

		c.add(new CreateInterfaceElementCommand(IecTypes.ElementaryTypes.BOOL, subApp.getInterface(),
				/* isInput */ true, /* index */ 0));
		c.add(new CreateInterfaceElementCommand(IecTypes.ElementaryTypes.BOOL, subApp.getInterface(),
				/* isInput */ false, /* index */ 0));

		c.add(new CreateInterfaceElementCommand(IecTypes.ElementaryTypes.DWORD, subApp.getInterface(),
				/* isInput */ true, /* index */ 1));
		c.add(new CreateInterfaceElementCommand(IecTypes.ElementaryTypes.DWORD, subApp.getInterface(),
				/* isInput */ false, /* index */ 1));

		state.setCommand(c);

		return commandExecution(state);
	}

	private static void verifyFBCreation(final State state, final State oldState, final TestFunction t) {
		t.test(state.getSubApp().getInterface().getInputVars().size(), 2);
		t.test(state.getSubApp().getInterface().getOutputVars().size(), 2);
	}

	private static State testWithOneElement(final State state) {
		final VarDeclaration dataInput = state.getSubApp().getInterface().getInputVars().get(0);
		final StructuredType structuredType = DataFactory.eINSTANCE.createStructuredType();
		structuredType.setName(SET_STRUCT_NAME);
		final List<VarDeclaration> dataInputList = List.of(dataInput);
		InterfaceListCopier.copyVarList(structuredType.getMemberVariables(), dataInputList, true);

		state.setCommand(new CreateStructFromInterfaceElementsCommand(dataInputList, structuredType));

		return commandExecution(state);
	}

	private static void verifyTestWithOneElement(final State state, final State oldState, final TestFunction t) {
		t.test(state.getSubApp().getInterface().getOutputVars().size(),
				oldState.getSubApp().getInterface().getOutputVars().size());
		t.test(state.getSubApp().getInterface().getOutputVars().size(), 2);
		t.test(state.getSubApp().getInterface().getInputVars().size(), 2);
		final DataType type = state.getSubApp().getInterface().getInputVars().get(0).getType();
		t.test(type.getName(), SET_STRUCT_NAME);
		t.test(type instanceof StructuredType);
		t.test(((StructuredType) type).getMemberVariables().size(), 1);
		t.test(((StructuredType) type).getMemberVariables().get(0).getType(), IecTypes.ElementaryTypes.BOOL);
		t.test(state.getSubApp().getInterface().getInputVars().get(1).getType(), IecTypes.ElementaryTypes.DWORD);
	}
}

/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.insert;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.commands.testinfra.InsertVariableCommandTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.junit.jupiter.params.provider.Arguments;

public class InsertVariableCommandTest extends InsertVariableCommandTestBase {

	private static VarDeclaration createTestVarDec(String name) {
		final VarDeclaration varDec = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		varDec.setName(name);
		varDec.setType(new DataTypeLibrary().getType(FordiacKeywords.BOOL));
		return varDec;
	}

	// insert into empty list
	private static State executeCommandVar1(State state) {
		final VarDeclaration varDec = createTestVarDec("first"); //$NON-NLS-1$
		state.setVarDec(varDec);
		state.setCommand(new InsertVariableCommand(state.getList(), varDec, 0));

		return commandExecution(state);
	}

	// insert at the beginning
	private static State executeCommandVar2(State state) {
		final VarDeclaration varDec = createTestVarDec("second"); //$NON-NLS-1$
		state.setVarDec(varDec);
		state.setCommand(new InsertVariableCommand(state.getList(), varDec, 0));

		return commandExecution(state);
	}

	// insert in the middle
	private static State executeCommandVar3(State state) {
		final VarDeclaration varDec = createTestVarDec("third"); //$NON-NLS-1$
		state.setVarDec(varDec);
		state.setCommand(new InsertVariableCommand(state.getList(), varDec, 1));

		return commandExecution(state);
	}

	// insert at the end
	private static State executeCommandVar4(State state) {
		final VarDeclaration varDec = createTestVarDec("fourth"); //$NON-NLS-1$
		state.setVarDec(varDec);
		state.setCommand(new InsertVariableCommand(state.getList(), varDec, 3));

		return commandExecution(state);
	}

	private static void verifyStateVarWithIndex(State state, State oldState, TestFunction t, int index) {
		t.test(!state.getList().isEmpty());
		t.test(state.getList().size(), (oldState.getList().size() + 1));
		t.test(validateVarDeclaration(state.getList().get(index), state.getVarDec()));
	}

	// empty list
	private static void verifyStateVar1(State state, State oldState, TestFunction t) {
		verifyStateVarWithIndex(state, oldState, t, 0);
	}

	// beginning
	private static void verifyStateVar2(State state, State oldState, TestFunction t) {
		verifyStateVarWithIndex(state, oldState, t, 0);
	}

	// middle
	private static void verifyStateVar3(State state, State oldState, TestFunction t) {
		verifyStateVarWithIndex(state, oldState, t, 1);
	}

	// end
	private static void verifyStateVar4(State state, State oldState, TestFunction t) {
		verifyStateVarWithIndex(state, oldState, t, 3);
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<State>("Insert an internal variable into an empty list", //$NON-NLS-1$
						InsertVariableCommandTest::executeCommandVar1, //
						InsertVariableCommandTest::verifyStateVar1 //
				), //
				new ExecutionDescription<State>("Insert an internal variable at the beginning of the list", //$NON-NLS-1$
						InsertVariableCommandTest::executeCommandVar2, //
						InsertVariableCommandTest::verifyStateVar2 //
				), //
				new ExecutionDescription<State>("Add an internal variable in the middle of the list", //$NON-NLS-1$
						InsertVariableCommandTest::executeCommandVar3, //
						InsertVariableCommandTest::verifyStateVar3 //
				), //
				new ExecutionDescription<State>("Add an internal variable at the end of the list", //$NON-NLS-1$
						InsertVariableCommandTest::executeCommandVar4, //
						InsertVariableCommandTest::verifyStateVar4 //
				) //
		);

		return createCommands(executionDescriptions);
	}

}

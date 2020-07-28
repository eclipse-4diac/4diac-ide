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
package org.eclipse.fordiac.ide.test.model.commands.insert;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.insert.InsertVariableCommand;
import org.eclipse.fordiac.ide.model.commands.testinfra.InsertVariableCommandTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.junit.runners.Parameterized.Parameters;

public class InsertVariableCommandTest extends InsertVariableCommandTestBase {

	private static VarDeclaration createTestVarDec(String name) {
		VarDeclaration varDec = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		varDec.setName(name);
		return varDec;
	}

	// insert into empty list
	private static State executeCommandVar1(State state) {
		VarDeclaration varDec = createTestVarDec("first");
		state.setVarDec(varDec);
		state.setCommand(new InsertVariableCommand(state.getList(), varDec, 0));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();

		return state;
	}

	// insert at the beginning
	private static State executeCommandVar2(State state) {
		VarDeclaration varDec = createTestVarDec("second");
		state.setVarDec(varDec);
		state.setCommand(new InsertVariableCommand(state.getList(), varDec, 0));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();

		return state;
	}

	// insert in the middle
	private static State executeCommandVar3(State state) {
		VarDeclaration varDec = createTestVarDec("third");
		state.setVarDec(varDec);
		state.setCommand(new InsertVariableCommand(state.getList(), varDec, 1));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();

		return state;
	}

	// insert at the end
	private static State executeCommandVar4(State state) {
		VarDeclaration varDec = createTestVarDec("fourth");
		state.setVarDec(varDec);
		state.setCommand(new InsertVariableCommand(state.getList(), varDec, 3));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();

		return state;
	}

	// empty list
	private static void verifyStateVar1(State state, State oldState, TestFunction t) {
		t.test(!state.getList().isEmpty());
		t.test(validateVarDeclaration(state.getList().get(0), state.getVarDec()));
	}

	// beginning
	private static void verifyStateVar2(State state, State oldState, TestFunction t) {
		t.test(!state.getList().isEmpty());
		t.test(state.getList().size() == 2);
		t.test(validateVarDeclaration(state.getList().get(0), state.getVarDec()));
	}

	// middle
	private static void verifyStateVar3(State state, State oldState, TestFunction t) {
		t.test(!state.getList().isEmpty());
		t.test(state.getList().size() == 3);
		t.test(validateVarDeclaration(state.getList().get(1), state.getVarDec()));
	}

	// end
	private static void verifyStateVar4(State state, State oldState, TestFunction t) {
		t.test(!state.getList().isEmpty());
		t.test(state.getList().size() == 4);
		t.test(validateVarDeclaration(state.getList().get(3), state.getVarDec()));
	}

	// parameter creation function, also contains description of how the textual
	// description will be used
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		final List<Object> executionDescriptions = ExecutionDescription.commandList( //
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


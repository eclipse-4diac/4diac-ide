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
 *   Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.create;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.testinfra.CreateMemberVariableCommandTestBase;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.junit.runners.Parameterized.Parameters;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class CreateMemberVariableCommandTest extends CreateMemberVariableCommandTestBase {

	private static State executeSimpleInsertion(State state) {
		state.setCommand(new CreateMemberVariableCommand(state.getStructuredType(), datatypeLib));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();
		return state;
	}

	private static State executeAdvancedCommand(State state) {
		int index = 0; // TODO does that go into the base class
		String name = null; // TODO
		DataType datatype = null; // TODO
		state.setCommand(
				new CreateMemberVariableCommand(state.getStructuredType(), index, name, datatype, datatypeLib));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();
		return state;
	}

	private static void verifyState(State state, State oldState, TestFunction t) {
		// verify that new variable is there
		// verify that old variables are also still there
		// verify that the variable is inserted in the correct place
		// verify the datatype
		// verify the name
	}

	private static void verifySimpleInsertion(State state, State oldState, TestFunction t) {
		t.test(state.getStructuredType().getMemberVariables()
				.size() == (oldState.getStructuredType().getMemberVariables().size() + 1));
		VarDeclaration inserted = state.getStructuredType().getMemberVariables()
				.get(state.getStructuredType().getMemberVariables().size() - 1);
		t.test(inserted.getName().equals("VAR1"));
		t.test(inserted.getTypeName().equals("BOOL"));
		t.test(inserted.getArraySize() == 0);
		t.test(inserted.getValue().getValue().equals(""));
		t.test(inserted.getComment().equals(""));
	}

	// parameter creation function, also contains description of how the textual
	// description will be used
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		List<Object> executionDescriptions = ExecutionDescription.commandList( //
				new ExecutionDescription<>("Create a default member at the end of the list", //
						CreateMemberVariableCommandTest::executeSimpleInsertion, //
						CreateMemberVariableCommandTest::verifySimpleInsertion //
				)
				);

		return createCommands(executionDescriptions);
	}

}

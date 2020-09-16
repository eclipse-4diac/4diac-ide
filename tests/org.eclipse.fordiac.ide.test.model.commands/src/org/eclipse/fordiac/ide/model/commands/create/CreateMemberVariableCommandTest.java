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

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.commands.testinfra.CreateMemberVariableCommandTestBase;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class CreateMemberVariableCommandTest extends CreateMemberVariableCommandTestBase {

	private static final String STRUCT_NAME = "mystruct"; //$NON-NLS-1$
	private static final String TESTMEMBER_NAME = "testmember"; //$NON-NLS-1$

	private static State executeSimpleInsertion(State state) {
		state.setCommand(new CreateMemberVariableCommand(state.getStructuredType(), datatypeLib));

		return commandExecution(state);
	}

	private static State executeAdvancedInsertion(State state, int index, String name, DataType type) {
		state.setCommand(new CreateMemberVariableCommand(state.getStructuredType(), index, name, type, datatypeLib));

		return commandExecution(state);
	}

	private static void verifyAdvancedInsertion(State state, State oldState, TestFunction t, int index, String name,
			DataType type) {
		// verify that new variable is there at correct position
		final VarDeclaration varDecl = state.getStructuredType().getMemberVariables().get(index);
		// verify that old variables are also still there
		t.test((oldState.getStructuredType().getMemberVariables().size() + 1), state.getStructuredType()
				.getMemberVariables().size());
		// verify the datatype
		t.test(varDecl.getTypeName(), type.getName());
		// verify the name
		t.test(varDecl.getName(), name);
		// verify empty comment
		t.test(varDecl.getComment(), ""); //$NON-NLS-1$
		// verify value not null
		t.test(varDecl.getValue() instanceof Value);
		t.test(varDecl.getValue().getValue(), ""); //$NON-NLS-1$

		// check that members of type are there
		if (type instanceof StructuredType) {
			t.test(varDecl.getType() instanceof StructuredType);
			t.test(!((StructuredType) varDecl.getType()).getMemberVariables().isEmpty());
			t.test(((StructuredType) varDecl.getType()).getMemberVariables().get(0).getName(), TESTMEMBER_NAME);
		}
	}

	private static void verifySimpleInsertion(State state, State oldState, TestFunction t) {
		t.test(state.getStructuredType().getMemberVariables()
				.size(), (oldState.getStructuredType().getMemberVariables().size() + 1));
		final VarDeclaration inserted = state.getStructuredType().getMemberVariables()
				.get(state.getStructuredType().getMemberVariables().size() - 1);

		final String defaultVarName = ((CreateMemberVariableCommand) state.getCommand()).getDefaultVarName();
		t.test(inserted.getName(), defaultVarName);

		t.test(inserted.getTypeName(), FordiacKeywords.BOOL);
		t.test(inserted.getArraySize() == 0);
		t.test(inserted.getValue().getValue(), ""); //$NON-NLS-1$
		t.test(inserted.getComment(), ""); //$NON-NLS-1$
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		initializeVariables();

		final List<ExecutionDescription<?>> autofilledExecutionDescriptions = List.of( //
				new ExecutionDescription<>("Create first default member var", // //$NON-NLS-1$
						CreateMemberVariableCommandTest::executeSimpleInsertion, //
						CreateMemberVariableCommandTest::verifySimpleInsertion), //
				new ExecutionDescription<>("Create second default member var", // //$NON-NLS-1$
						CreateMemberVariableCommandTest::executeSimpleInsertion, //
						CreateMemberVariableCommandTest::verifySimpleInsertion //
				) //
		);

		final List<ExecutionDescription<?>> configuredExecutionDescriptions = List.of(
				new ExecutionDescription<>("Create first configured member var", //$NON-NLS-1$
						(State state) -> executeAdvancedInsertion(state, 0, "test1", //$NON-NLS-1$
								datatypeLib.getType(FordiacKeywords.BOOL)), //
						(State state, State oldState, TestFunction t) -> verifyAdvancedInsertion(state, oldState, t, 0,
								"test1", //$NON-NLS-1$
								datatypeLib.getType(FordiacKeywords.BOOL))), //

				new ExecutionDescription<>("Create second configured member var with same name", //$NON-NLS-1$
						(State state) -> executeAdvancedInsertion(state, 1, "test1", //$NON-NLS-1$
								datatypeLib.getType(FordiacKeywords.BOOL)), //
						(State state, State oldState, TestFunction t) -> verifyAdvancedInsertion(state, oldState, t, 1,
								"test2", //$NON-NLS-1$
								datatypeLib.getType(FordiacKeywords.BOOL))), //

				new ExecutionDescription<>("Create first configured member var at pos 0", //$NON-NLS-1$
						(State state) -> executeAdvancedInsertion(state, 0, "test3", //$NON-NLS-1$
								datatypeLib.getType(FordiacKeywords.BOOL)), //
						(State state, State oldState, TestFunction t) -> verifyAdvancedInsertion(state, oldState, t, 0,
								"test3", //$NON-NLS-1$
								datatypeLib.getType(FordiacKeywords.BOOL))), //

				new ExecutionDescription<>("Create first configured member struct at pos 0", //$NON-NLS-1$
						(State state) -> executeAdvancedInsertion(state, 0, "test4", //$NON-NLS-1$
								struct), //
						(State state, State oldState, TestFunction t) -> verifyAdvancedInsertion(state, oldState, t, 0,
								"test4", //$NON-NLS-1$
								struct)), //

				new ExecutionDescription<>("Create first configured member var in the middle", //$NON-NLS-1$
						(State state) -> executeAdvancedInsertion(state, 2, "test5", //$NON-NLS-1$
								datatypeLib.getType(FordiacKeywords.BOOL)), //
						(State state, State oldState, TestFunction t) -> verifyAdvancedInsertion(state, oldState, t, 2,
								"test5", //$NON-NLS-1$
								datatypeLib.getType(FordiacKeywords.BOOL))) //
		);

		return createCommands(autofilledExecutionDescriptions, configuredExecutionDescriptions);
	}

	private static void initializeVariables() {
		struct.setName(STRUCT_NAME);
		final VarDeclaration varDecl = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		varDecl.setType(datatypeLib.getType(FordiacKeywords.BOOL));
		varDecl.setName(TESTMEMBER_NAME);
		struct.getMemberVariables().add(varDecl);
	}

	private static StructuredType struct = DataFactory.eINSTANCE.createStructuredType();
}

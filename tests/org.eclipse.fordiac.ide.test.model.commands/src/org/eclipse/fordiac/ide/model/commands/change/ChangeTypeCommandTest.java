/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.commands.create.WithCreateTest;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.junit.jupiter.params.provider.Arguments;

public class ChangeTypeCommandTest extends FBNetworkTestBase {

	private static State changeDataInputType(final State state) {
		final InterfaceList fb = state.getFunctionblock().getType().getInterfaceList();

		state.setCommand(ChangeDataTypeCommand.forDataType(fb.getInputVars().get(0),
				getDatatypelib().getType(FordiacKeywords.LWORD)));
		return commandExecution(state);
	}

	private static void validateDataInputType(final State state, final State oldState, final TestFunction t) {
		final InterfaceList fb = state.getFunctionblock().getType().getInterfaceList();
		t.test(fb.getInputVars().get(0).getTypeName(), FordiacKeywords.LWORD);
		t.test(fb.getInputVars().get(0).getType(), getDatatypelib().getType(FordiacKeywords.LWORD));
	}

	private static void validateDataInputTypeNetworkElements(final State state, final State oldState,
			final TestFunction t) {
		final InterfaceList fb = state.getFunctionblock().getType().getInterfaceList();
		final InterfaceList fb1 = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		final InterfaceList fb2 = state.getFbNetwork().getNetworkElements().get(1).getInterface();

		t.test(fb1.getInputVars().get(0).getTypeName(), fb.getInputVars().get(0).getTypeName());
		t.test(fb2.getInputVars().get(0).getTypeName(), fb.getInputVars().get(0).getTypeName());
		t.test(fb1.getInputVars().get(0).getType(), fb.getInputVars().get(0).getType());
		t.test(fb2.getInputVars().get(0).getType(), fb.getInputVars().get(0).getType());
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Prepare Functionblocks", //$NON-NLS-1$
						WithCreateTest::createInterfaceElements, //
						WithCreateTest::verifyFBCreation //
				), //
				new ExecutionDescription<>("Change Datatype of DataInput of FBType", //$NON-NLS-1$
						ChangeTypeCommandTest::changeDataInputType, //
						ChangeTypeCommandTest::validateDataInputType //
				), //
				new ExecutionDescription<>("Update FBType to Network elements", //$NON-NLS-1$
						WithCreateTest::updateNetworkElements, //
						ChangeTypeCommandTest::validateDataInputTypeNetworkElements //
				) //
		);
		return createCommands(executionDescriptions);
	}

}

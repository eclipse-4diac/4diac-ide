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
 *   Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.create.ConnectionCommandsTest;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.junit.jupiter.params.provider.Arguments;

public class FlattenSubAppCommandTest extends NewSubAppCommandTest {

	public static State flattenSubApp(final State s) {
		final SubApp subapp = (SubApp) s.getFbNetwork().getNetworkElements().stream().filter(SubApp.class::isInstance)
				.findFirst().orElse(null);
		assertion.test(subapp);
		s.setCommand(new FlattenSubAppCommand(subapp));
		return commandExecution(s);
	}


	// parameter creation function
	protected static Collection<Arguments> data() {
		final Collection<Arguments> a = new ArrayList<>();
		a.addAll(describeCommand("Start with an empty FBNetwork", //$NON-NLS-1$
				FlattenSubAppCommandTest::initEmptyState, //
				(StateVerifier<State>) FlattenSubAppCommandTest::verifyEmptyInitialState, //
				List.of( //
						new ExecutionDescription<>("Create empty SubApp", //$NON-NLS-1$
								FlattenSubAppCommandTest::createEmptySubApp, //
								FlattenSubAppCommandTest::verifyEmptySubAppCreation //
								), //
						new ExecutionDescription<>("Flatten empty SubApp", //$NON-NLS-1$
								FlattenSubAppCommandTest::flattenSubApp, //
								FlattenSubAppCommandTest::verifyEmptyInitialState //
								))//
				));

		//

		a.addAll(describeCommand("Start with two FBs in FBNetwork", //$NON-NLS-1$
				FlattenSubAppCommandTest::initState, //
				(StateVerifier<State>) FlattenSubAppCommandTest::verifyInitialState, //
				List.of( //
						new ExecutionDescription<>("Create SubApp", //$NON-NLS-1$
								FlattenSubAppCommandTest::createSubApp, //
								FlattenSubAppCommandTest::verifySubAppCreation //
								), //
						new ExecutionDescription<>("Flatten SubApp", //$NON-NLS-1$
								FlattenSubAppCommandTest::flattenSubApp, //
								FlattenSubAppCommandTest::verifyInitialState //
								)) //
				));

		a.addAll(describeCommand("Start with two FBs in FBNetwork", //$NON-NLS-1$
				FlattenSubAppCommandTest::initState, //
				(StateVerifier<State>) FlattenSubAppCommandTest::verifyInitialState, //
				List.of( //
						new ExecutionDescription<>("Create Data Connections", //$NON-NLS-1$
								ConnectionCommandsTest::workingAddDataConnection, //
								ConnectionCommandsTest::verifyDataConnection //
								), //
						new ExecutionDescription<>("Create Event Connections", //$NON-NLS-1$
								ConnectionCommandsTest::workingAddEventConnection, //
								ConnectionCommandsTest::verifyEventConnection //
								), //
						new ExecutionDescription<>("Create SubApp", //$NON-NLS-1$
								FlattenSubAppCommandTest::createSubApp, //
								FlattenSubAppCommandTest::verifySubAppCreationWithConnections //
								), //
						new ExecutionDescription<>("Flatten SubApp", //$NON-NLS-1$
								FlattenSubAppCommandTest::flattenSubApp, //
								FlattenSubAppCommandTest::verifyInitialState //
								)) //
				));

		return a;
	}

}

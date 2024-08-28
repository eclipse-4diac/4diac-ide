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
 *   alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.annotations.FBNetworkElementAnnotations;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.gef.commands.CompoundCommand;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class GroupCreateCommandTest extends FBNetworkTestBase {

	private static final String NAME_GROUP1 = "Group01"; //$NON-NLS-1$
	private static final String NAME_GROUP2 = "Group02"; //$NON-NLS-1$
	private static final String NAME_GROUP3 = "Group03"; //$NON-NLS-1$

	public static State createOneGroup(final State state) {
		state.setCommand(new CreateGroupCommand(state.getFbNetwork(), Collections.emptyList(), new Rectangle()));
		tester.get().test(state.getCommand() instanceof CreateGroupCommand);
		return commandExecution(state);
	}

	public static void verifyCreateOneGroup(final State state, final State oldState, final TestFunction t) {
		t.test(!state.getFbNetwork().isSubApplicationNetwork());
		t.test(!state.getFbNetwork().getNetworkElements().isEmpty());
		t.test(state.getFbNetwork().getElementNamed(NAME_GROUP1));
		t.test(state.getFbNetwork().getNetworkElements().get(0) instanceof Group);

		final Group group = (Group) state.getFbNetwork().getNetworkElements().get(0);
		t.test(group.getInterface() == FBNetworkElementAnnotations.EMPTY_INTERFACE_LIST);
		t.test(group.getGroupElements().isEmpty());
		t.test(null == group.getOpposite());

		t.test(state.getFbNetwork().equals(group.eContainer()));
		t.test(state.getFbNetwork().equals(group.getFbNetwork()));
	}

	public static State createTwoGroups(final State state) {
		final CompoundCommand cmd = new CompoundCommand();
		cmd.add(new CreateGroupCommand(state.getFbNetwork(), Collections.emptyList(), new Rectangle()));
		cmd.add(new CreateGroupCommand(state.getFbNetwork(), Collections.emptyList(), new Rectangle()));
		state.setCommand(cmd);
		return commandExecution(state);
	}

	public static void verifyCreatetwoGroups(final State state, final State oldState, final TestFunction t) {
		t.test(!state.getFbNetwork().isSubApplicationNetwork());
		t.test(!state.getFbNetwork().getNetworkElements().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().size() == 3); // the state is not purged and the first group
		// is still in
		t.test(state.getFbNetwork().getElementNamed(NAME_GROUP1));
		t.test(state.getFbNetwork().getElementNamed(NAME_GROUP2));
		t.test(state.getFbNetwork().getElementNamed(NAME_GROUP3));
		t.test(state.getFbNetwork().getNetworkElements().get(0) instanceof Group);
		t.test(state.getFbNetwork().getNetworkElements().get(1) instanceof Group);
		t.test(state.getFbNetwork().getNetworkElements().get(2) instanceof Group);

		final Group group1 = (Group) state.getFbNetwork().getNetworkElements().get(0);
		final Group group2 = (Group) state.getFbNetwork().getNetworkElements().get(1);
		final Group group3 = (Group) state.getFbNetwork().getNetworkElements().get(2);
		t.test(NAME_GROUP1.equals(group1.getName()));
		t.test(NAME_GROUP2.equals(group2.getName()));
		t.test(NAME_GROUP3.equals(group3.getName()));
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Create one group", //$NON-NLS-1$
						GroupCreateCommandTest::createOneGroup, //
						GroupCreateCommandTest::verifyCreateOneGroup //
				), //
				new ExecutionDescription<>("Create two groups", //$NON-NLS-1$
						GroupCreateCommandTest::createTwoGroups, //
						GroupCreateCommandTest::verifyCreatetwoGroups //
				) //
		);

		return createCommands(executionDescriptions);
	}

}

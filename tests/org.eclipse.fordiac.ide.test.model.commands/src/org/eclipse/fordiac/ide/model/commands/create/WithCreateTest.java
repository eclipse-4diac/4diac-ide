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
package org.eclipse.fordiac.ide.model.commands.create;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteWithCommand;
import org.eclipse.fordiac.ide.model.commands.testinfra.CommandTestBase;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.gef.commands.CompoundCommand;
import org.junit.jupiter.params.provider.Arguments;

public class WithCreateTest extends FBNetworkTestBase {

	private static State createInterfaceElements(State state) {
		CompoundCommand c = new CompoundCommand();

		c.add(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null),
				state.getFunctionblock().getFBType().getInterfaceList(), /* isInput */ true, /* index */ 0));
		c.add(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null),
				state.getFunctionblock().getFBType().getInterfaceList(), /* isInput */ false, /* index */ 0));

		c.add(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.BOOL),
				state.getFunctionblock().getFBType().getInterfaceList(), /* isInput */ true, /* index */ 0));
		c.add(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.BOOL),
				state.getFunctionblock().getFBType().getInterfaceList(), /* isInput */ false, /* index */ 0));

		c.add(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.DWORD),
				state.getFunctionblock().getFBType().getInterfaceList(), /* isInput */ true, /* index */ 1));
		c.add(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.DWORD),
				state.getFunctionblock().getFBType().getInterfaceList(), /* isInput */ false, /* index */ 1));

		c.add(new FBCreateCommand(state.getFunctionblock(), state.getFbNetwork(), 0, 0));

		c.add(new FBCreateCommand(state.getFunctionblock(), state.getFbNetwork(), 0, 0));

		state.setCommand(c);

		return commandExecution(state);
	}

	private static void verifyFBCreation(State state, State oldState, TestFunction t) {
		t.test(state.getFbNetwork().getNetworkElements().size(), 2);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getType(),
				state.getFbNetwork().getNetworkElements().get(1).getType());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().size(), 1);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventOutputs().size(), 1);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getInputVars().size(), 2);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getOutputVars().size(), 2);
		t.test(state.getFunctionblock().getFBType().getInterfaceList().getEventInputs().get(0).getWith().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().get(0).getWith()
				.isEmpty());
	}

	private static State addWith(State state) {
		state.setCommand(
				new WithCreateCommand(state.getFunctionblock().getFBType().getInterfaceList().getEventInputs().get(0),
						state.getFunctionblock().getFBType().getInterfaceList().getInputVars().get(0)));

		return commandExecution(state);
	}

	private static void verifyAddWith(State state, State oldState, TestFunction t) {
		t.test(!state.getFunctionblock().getFBType().getInterfaceList().getEventInputs().get(0).getWith().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().get(0).getWith()
				.isEmpty());

		WithCreateCommand c = new WithCreateCommand(
				state.getFunctionblock().getFBType().getInterfaceList().getEventInputs().get(0),
				state.getFunctionblock().getFBType().getInterfaceList().getInputVars().get(0));
		t.test(c.getEvent());
		t.test(c.getVarDeclaration());
	}

	private static State updateNetworkElements(State state) {
		CompoundCommand c = new CompoundCommand();

		c.add(new UpdateFBTypeCommand(state.getFbNetwork().getNetworkElements().get(0), state.getFunctionblock()));
		c.add(new UpdateFBTypeCommand(state.getFbNetwork().getNetworkElements().get(1), state.getFunctionblock()));

		state.setCommand(c);
		return commandExecution(state);
	}

	private static void verifyUpdateNetworkElementsAddedWith(State state, State oldState, TestFunction t) {
		t.test(!state.getFunctionblock().getFBType().getInterfaceList().getEventInputs().get(0).getWith().isEmpty());
		t.test(!state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().get(0).getWith()
				.isEmpty());
	}

	private static State deleteWith(State state) {
		state.setCommand(new DeleteWithCommand(
				state.getFunctionblock().getFBType().getInterfaceList().getEventInputs().get(0).getWith().get(0)));
		return commandExecution(state);
	}

	private static void verifyDeleteWith(State state, State oldState, TestFunction t) {
		t.test(state.getFunctionblock().getFBType().getInterfaceList().getEventInputs().get(0).getWith().isEmpty());
		t.test(!state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().get(0).getWith()
				.isEmpty());
	}

	private static void verifyUpdateNetworkElementsDeletedWith(State state, State oldState, TestFunction t) {
		t.test(state.getFunctionblock().getFBType().getInterfaceList().getEventInputs().get(0).getWith().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().get(0).getWith()
				.isEmpty());
	}

	private static State nullAll(State state) {
		state.setCommand(new WithCreateCommand());
		return disabledCommandExecution(state);
	}

	private static State nullEvent(State state) {
		state.setCommand(new WithCreateCommand(null,
				state.getFunctionblock().getFBType().getInterfaceList().getInputVars().get(0)));
		return disabledCommandExecution(state);
	}

	private static State nullInput(State state) {
		state.setCommand(new WithCreateCommand(
				state.getFunctionblock().getFBType().getInterfaceList().getEventInputs().get(0), null));
		return disabledCommandExecution(state);
	}

	private static State onEventOutput(State state) {
		WithCreateCommand c = new WithCreateCommand();
		c.setEvent(state.getFunctionblock().getFBType().getInterfaceList().getEventOutputs().get(0));
		c.setVarDeclaration(state.getFunctionblock().getFBType().getInterfaceList().getInputVars().get(0));

		state.setCommand(c);

		return disabledCommandExecution(state);
	}

	private static State onOutput(State state) {
		state.setCommand(
				new WithCreateCommand(state.getFunctionblock().getFBType().getInterfaceList().getEventInputs().get(0),
						state.getFunctionblock().getFBType().getInterfaceList().getOutputVars().get(0)));
		return disabledCommandExecution(state);
	}

	private static State addExistingWith(State state) {
		state.setCommand(
				new WithCreateCommand(state.getFunctionblock().getFBType().getInterfaceList().getEventInputs().get(0),
						state.getFunctionblock().getFBType().getInterfaceList().getInputVars().get(0)));
		return disabledCommandExecution(state);
	}

	private static void verifyAddExistingWith(State state, State oldState, TestFunction t) {
		if (!state.getViaUndo()) {
			t.test(state.getMessages().size(), 1);
			t.test(state.getMessages().get(0), Messages.WithExists);
		}
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<State>("Prepare Functionblocks", //$NON-NLS-1$
						WithCreateTest::createInterfaceElements, //
						WithCreateTest::verifyFBCreation //
				), //
				new ExecutionDescription<State>("Add with at functionblock", //$NON-NLS-1$
						WithCreateTest::addWith, //
						WithCreateTest::verifyAddWith //
				), //
				new ExecutionDescription<State>("Update Network Elements", //$NON-NLS-1$
						WithCreateTest::updateNetworkElements, //
						WithCreateTest::verifyUpdateNetworkElementsAddedWith //
				), //
				new ExecutionDescription<State>("Delete With from functionblock", //$NON-NLS-1$
						WithCreateTest::deleteWith, //
						WithCreateTest::verifyDeleteWith //
				), //
				new ExecutionDescription<State>("Update Network Elements", //$NON-NLS-1$
						WithCreateTest::updateNetworkElements, //
						WithCreateTest::verifyUpdateNetworkElementsDeletedWith //
				), //
				new ExecutionDescription<State>("Create With without inferface data", //$NON-NLS-1$
						WithCreateTest::nullAll, //
						CommandTestBase::verifyNothing //
				), //
				new ExecutionDescription<State>("Create With without event", //$NON-NLS-1$
						WithCreateTest::nullEvent, //
						CommandTestBase::verifyNothing //
				), //
				new ExecutionDescription<State>("Create With without input", //$NON-NLS-1$
						WithCreateTest::nullInput, //
						CommandTestBase::verifyNothing //
				), //
				new ExecutionDescription<State>("Create With on event-output", //$NON-NLS-1$
						WithCreateTest::onEventOutput, //
						WithCreateTest::verifyNothing //
				), //
				new ExecutionDescription<State>("Create With on output", //$NON-NLS-1$
						WithCreateTest::onOutput, //
						WithCreateTest::verifyNothing //
				), //
				new ExecutionDescription<State>("Add with at functionblock", //$NON-NLS-1$
						WithCreateTest::addWith, //
						WithCreateTest::verifyAddWith //
				), //
				new ExecutionDescription<State>("Add with at functionblock that already exists", //$NON-NLS-1$
						WithCreateTest::addExistingWith, //
						WithCreateTest::verifyAddExistingWith //
				) //
		);
		return createCommands(executionDescriptions);
	}

}

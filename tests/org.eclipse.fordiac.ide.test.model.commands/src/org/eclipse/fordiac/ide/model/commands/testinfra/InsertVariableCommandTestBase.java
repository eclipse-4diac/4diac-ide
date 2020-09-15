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
package org.eclipse.fordiac.ide.model.commands.testinfra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;
import org.junit.jupiter.params.provider.Arguments;

public abstract class InsertVariableCommandTestBase extends CommandTestBase<InsertVariableCommandTestBase.State> {

	private static StructuredType struct = DataFactory.eINSTANCE.createStructuredType();

	public static class State implements CommandTestBase.StateBase {
		private final EList<VarDeclaration> list;
		private VarDeclaration varDec;

		private Command cmd;

		public State() {
			list = struct.getMemberVariables();
			list.clear();
		}

		private State(State s) {
			list = new BasicEList<>();
			ECollections.setEList(list, (List<VarDeclaration>) EcoreUtil.copyAll(s.list));
			varDec = EcoreUtil.copy(s.varDec);
		}

		@Override
		public Object getClone() {
			return new State(this);
		}

		public VarDeclaration getVarDec() {
			return varDec;
		}

		public void setVarDec(VarDeclaration varDec) {
			this.varDec = varDec;
		}

		public EList<VarDeclaration> getList() {
			return list;
		}

		@Override
		public Command getCommand() {
			return cmd;
		}

		@Override
		public void setCommand(Command cmd) {
			this.cmd = cmd;
		}
	}

	protected static Collection<Arguments> createCommands(List<ExecutionDescription<?>> executionDescriptions) {
		final Collection<Arguments> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start from default values", // //$NON-NLS-1$
				State::new, //
				(State state, State oldState, TestFunction t) -> verifyDefaultInitialValues(state, oldState, t), //
				executionDescriptions //
		));

		return commands;
	}

	protected static Collection<Arguments> describeCommand(String description, StateInitializer<?> initializer,
			StateVerifier<?> initialVerifier, List<ExecutionDescription<?>> commands) {
		return describeCommand(description, initializer, initialVerifier, commands,
				CommandTestBase::defaultUndoCommand, CommandTestBase::defaultRedoCommand);
	}

	protected static void verifyDefaultInitialValues(State state, State oldState, TestFunction t) {
		t.test(state.getList().isEmpty());
	}

	protected static boolean validateVarDeclaration(VarDeclaration newVar, VarDeclaration oldVar) {
		/*
		 * can not check the name because if it is already taken a unique one will be
		 * generated
		 */
		if (!newVar.getType().getName().equals(oldVar.getType().getName())) {
			return false;
		}
		if (!newVar.getComment().equals(oldVar.getComment())) {
			return false;
		}
		if (newVar.getArraySize() != oldVar.getArraySize()) {
			return false;
		}
		return true;
	}

}

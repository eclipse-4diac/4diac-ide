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
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.junit.jupiter.params.provider.Arguments;

public abstract class InsertVariableCommandTestBase extends CommandTestBase<InsertVariableCommandTestBase.State> {

	private static StructuredType struct = DataFactory.eINSTANCE.createStructuredType();

	public static class State extends CommandTestBase.StateBase {
		private final LibraryElement libraryElement;
		private final EList<VarDeclaration> list;
		private VarDeclaration varDec;

		public State() {
			libraryElement = LibraryElementFactory.eINSTANCE.createLibraryElement();
			list = struct.getMemberVariables();
			list.clear();
		}

		private State(final State s) {
			libraryElement = EcoreUtil.copy(s.libraryElement);
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

		public void setVarDec(final VarDeclaration varDec) {
			this.varDec = varDec;
		}

		public LibraryElement getLibraryElement() {
			return libraryElement;
		}

		public EList<VarDeclaration> getList() {
			return list;
		}

	}

	protected static Collection<Arguments> createCommands(final List<ExecutionDescription<?>> executionDescriptions) {
		final Collection<Arguments> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start from default values", // //$NON-NLS-1$
				State::new, //
				(StateVerifier<State>) InsertVariableCommandTestBase::verifyDefaultInitialValues, //
				executionDescriptions //
		));

		return commands;
	}

	protected static Collection<Arguments> describeCommand(final String description,
			final StateInitializer<?> initializer, final StateVerifier<?> initialVerifier,
			final List<ExecutionDescription<?>> commands) {
		return describeCommand(description, initializer, initialVerifier, commands, CommandTestBase::defaultUndoCommand,
				CommandTestBase::defaultRedoCommand);
	}

	protected static void verifyDefaultInitialValues(final State state, final State oldState, final TestFunction t) {
		t.test(state.getList().isEmpty());
	}

}

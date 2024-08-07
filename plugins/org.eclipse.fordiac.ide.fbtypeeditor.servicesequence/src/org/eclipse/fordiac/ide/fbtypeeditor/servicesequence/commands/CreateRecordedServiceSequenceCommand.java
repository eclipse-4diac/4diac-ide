/*******************************************************************************
 * Copyright (c) 2021, 2024 Johannes Kepler University Linz and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *     - extracted code from RecordServiceSequenceHandler
 *  Paul Pavlicek
 *     - cleanup and extracting code, added random generation
 *  Felix Roithmayr
 *     - added extra support for context menu entry
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class CreateRecordedServiceSequenceCommand extends Command {
	private final FBType fbType;
	private final ServiceSequence oldSequence;
	private CreateServiceSequenceCommand cmd;
	private final List<String> events;
	private final List<List<String>> parameters;
	final boolean forceOverwrite;
	String startState = null;

	/** the newly generated sequence */
	private ServiceSequence newSequence;

	/** create new service sequence and track recording result */
	public CreateRecordedServiceSequenceCommand(final FBType fbType, final List<String> events,
			final List<List<String>> parameters) {
		this(fbType, null, events, parameters);
	}

	/** record into existing service sequence */
	public CreateRecordedServiceSequenceCommand(final FBType fbType, final ServiceSequence sequence,
			final List<String> events, final List<List<String>> parameters) {
		this(fbType, sequence, events, parameters, false);
	}

	/** record into existing service sequence */
	public CreateRecordedServiceSequenceCommand(final FBType fbType, final ServiceSequence sequence,
			final List<String> events, final List<List<String>> parameters, final boolean forceOverwrite) {
		this.fbType = Objects.requireNonNull(fbType);
		this.oldSequence = sequence;
		this.events = events;
		this.parameters = parameters;
		this.forceOverwrite = forceOverwrite;
	}

	public void setStartState(final String startState) {
		if (fbType instanceof BasicFBType) {
			this.startState = startState;
		}
	}

	@Override
	public boolean canExecute() {
		if (oldSequence != null) {
			return fbType.equals(oldSequence.getService().getFBType());
		}
		return true;
	}

	@Override
	public void execute() {
		if (cmd != null) {
			cmd.execute();
		}
		if (oldSequence != null && !forceOverwrite) {
			newSequence = EcoreUtil.copy(oldSequence);
		} else {
			cmd = new CreateServiceSequenceCommand(fbType.getService());
			cmd.execute();
			newSequence = cmd.getCreatedElement();
		}
		updateSequences(oldSequence, newSequence);
		runInterpreter(newSequence, events, parameters, fbType, startState);
	}

	private void updateSequences(final ServiceSequence toRemove, final ServiceSequence toAdd) {
		if (toRemove != null) {
			fbType.getService().getServiceSequence().remove(toRemove);
		}
		if (toAdd != null) {
			fbType.getService().getServiceSequence().add(toAdd);
		}
	}

	@Override
	public void undo() {
		updateSequences(newSequence, oldSequence);
	}

	@Override
	public void redo() {
		updateSequences(oldSequence, newSequence);
	}

	private void runInterpreter(final ServiceSequence seq, final List<String> eventNames,
			final List<List<String>> parameters, final FBType fbType, final String startState) {
		final List<Event> events = ServiceSequenceUtils.getEvents(fbType, eventNames);
		final EventManager eventManager = EventManagerFactory.createFrom(events, EcoreUtil.copy(fbType));
		for (int i = 0; i < parameters.size(); i++) {
			if (i < eventManager.getTransactions().size()) {
				setParameters((FBTransaction) eventManager.getTransactions().get(i), parameters.get(i));
			}
		}
		final EObject runType = eventManager.getTransactions().get(0).getInputEventOccurrence().getFbRuntime();
		if (runType instanceof final BasicFBTypeRuntime basic && startState != null && !startState.isEmpty()) {
			ECState state = basic.getModel().getECC().getECState().stream().filter(st -> startState.equals(st.getName())).findFirst().orElse(null);
			if (state!=null) {
				basic.setActiveState(startState);
				newSequence.setStartState(startState);
			}
		}
		eventManager.process();
		final List<String> parameter = parameters.stream().map(
				list -> sumParameter(list)) //
				.toList();
		ServiceSequenceUtils.convertEventManagerToServiceModel(seq, fbType, eventManager, parameter);
	}

	private static String sumParameter(final List<String> list) {
		final StringBuilder sb = new StringBuilder();
		for (final String s : list) {
			sb.append(s);
			sb.append(";"); //$NON-NLS-1$
		}
		return sb.toString();
	}

	private static void setParameters(final FBTransaction transaction, final List<String> parameters) {
		final List<VarDeclaration> paramVars = new ArrayList<>();
		for (final String s : parameters) {
			final VarDeclaration var = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			final List<String> param = ServiceSequenceUtils.splitParameter(s);
			if (param.size() == 2) {
				var.setName(param.get(0));
				var.setValue(LibraryElementFactory.eINSTANCE.createValue());
				var.getValue().setValue(param.get(1));
			}
			paramVars.add(var);
		}
		transaction.getInputVariables().addAll(paramVars);
	}

}

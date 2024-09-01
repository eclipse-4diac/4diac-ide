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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.CoverageCalculator;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.ExecutionTrace;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
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

	/** if a service sequence is provided, it can be extended or overridden */
	private final boolean forceOverwrite;
	/** a start state may be provided via setStartState method */
	private String startState = null;
	/** enable tracing in interpreter so that coverage info is available */
	private boolean traceInfo = false;

	/** the newly generated sequence */
	private ServiceSequence newSequence;
	private EventManager eventManager;

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

	public void enableTraceInfo(final boolean traceInfo) {
		this.traceInfo = traceInfo;
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
			// potential improvement: calculate the end state of the old sequence first
			startState = oldSequence.getStartState();
		} else {
			cmd = new CreateServiceSequenceCommand(fbType.getService());
			cmd.execute();
			newSequence = cmd.getCreatedElement();
		}
		updateSequences(oldSequence, newSequence);
		runInterpreter();
		final List<String> parameter = parameters.stream().map(CreateRecordedServiceSequenceCommand::sumParameter)
				.toList();
		ServiceSequenceUtils.convertEventManagerToServiceModel(newSequence, fbType, eventManager, parameter);
		newSequence.setStartState(startState);
		if (traceInfo) {
			newSequence.setComment("Coverage: " //$NON-NLS-1$
					+ CoverageCalculator.calculateCoverageOfSequence(eventManager.getTransactions(), fbType));
		}
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

	private void runInterpreter() {
		// setup
		final List<Event> eventPins = ServiceSequenceUtils.getEvents(fbType, events);
		eventManager = EventManagerFactory.createFrom(eventPins, EcoreUtil.copy(fbType));
		for (int i = 0; i < parameters.size(); i++) {
			if (i < eventManager.getTransactions().size()) {
				setParameters((FBTransaction) eventManager.getTransactions().get(i), parameters.get(i));
			}
		}
		TransactionFactory.setStartState(eventManager.getTransactions().getFirst(), startState);
		if (traceInfo) {
			TransactionFactory.addTraceInfoTo(eventManager.getTransactions());
		}
		// execute
		eventManager.process();
	}

	private static String sumParameter(final List<String> list) {
		final StringBuilder sb = new StringBuilder();
		for (final String s : list) {
			sb.append(s);
			sb.append(ServiceSequenceUtils.PARAMETER_SEPARATOR);
		}
		return sb.toString();
	}

	private static void setParameters(final FBTransaction transaction, final List<String> parameters) {
		final List<VarDeclaration> paramVars = new ArrayList<>();
		for (final String s : parameters) {
			final VarDeclaration varDecl = LibraryElementFactory.eINSTANCE.createVarDeclaration();
			final List<String> param = ServiceSequenceUtils.splitParameter(s);
			if (param.size() == 2) {
				varDecl.setName(param.get(0));
				varDecl.setValue(LibraryElementFactory.eINSTANCE.createValue());
				varDecl.getValue().setValue(param.get(1));
			}
			paramVars.add(varDecl);
		}
		transaction.getInputVariables().addAll(paramVars);
	}

	public ExecutionTrace getTraceInfo() {
		if (eventManager == null) {
			runInterpreter();
		}
		return ExecutionTrace.of(eventManager);
	}
}

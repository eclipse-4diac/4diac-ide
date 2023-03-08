/*******************************************************************************
 * Copyright (c) 2021, 2022, 2023 Johannes Kepler University Linz and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmend�a, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *   Paul Pavlicek - cleanup
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventOccFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.IRunFBTypeVisitor;
import org.eclipse.fordiac.ide.fb.interpreter.api.LambdaVisitor;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.VariableUtils;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.fb.BasicFBEvaluator;
import org.eclipse.fordiac.ide.model.eval.value.BoolValue;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class DefaultRunFBType implements IRunFBTypeVisitor {

	private final EventOccurrence eventOccurrence;
	private final Map<String, FBNetworkElement> nameToFBNetwork;

	public DefaultRunFBType(final EventOccurrence eventOccurrence) {
		this.eventOccurrence = eventOccurrence;
		this.nameToFBNetwork = new HashMap<>();
		if (this.eventOccurrence.getFbRuntime() instanceof FBNetworkRuntime) {
			final FBNetworkRuntime fbNetworkRuntime = (FBNetworkRuntime) this.eventOccurrence.getFbRuntime();
			final FBNetwork fbNetwork = fbNetworkRuntime.getFbnetwork();
			fbNetwork.getNetworkElements()
					.forEach(networkElement -> nameToFBNetwork.put(networkElement.getName(), networkElement));
		}
	}

	public static Function<Object, Object> of(final IRunFBTypeVisitor runTypeVisitor) {
		return new LambdaVisitor<>() //
				.on(BasicFBTypeRuntime.class).then(runTypeVisitor::runBasicFBType) //
				.on(SimpleFBTypeRuntime.class).then(runTypeVisitor::runSimpleFBType) //
				.on(FBNetworkRuntime.class).then(runTypeVisitor::runFBNetwork);
	}

	@SuppressWarnings("unchecked")
	public static EList<EventOccurrence> runFBType(final FBRuntimeAbstract fbTypeRuntime,
			final EventOccurrence eventOccurrence) {
		final var defaultRun = new DefaultRunFBType(eventOccurrence);
		return (EList<EventOccurrence>) of(defaultRun).apply(fbTypeRuntime);
	}

	@Override
	public EList<EventOccurrence> runBasicFBType(final BasicFBTypeRuntime basicFBTypeRuntime) {
		// Initialization of variables
		VariableUtils.fBVariableInitialization(basicFBTypeRuntime.getBasicfbtype());
		final var outputEvents = new BasicEList<EventOccurrence>();
		final var eCC = basicFBTypeRuntime.getBasicfbtype().getECC();
		// Active State
		final var eCState = basicFBTypeRuntime.getActiveState();
		if (eCState == null) {
			basicFBTypeRuntime.setActiveState(eCC.getStart());
		}
		// apply event and evaluate transitions
		var firedTransition = evaluateOutTransitions(basicFBTypeRuntime);
		while (firedTransition != null) {
			isConsumed();
			basicFBTypeRuntime.setActiveState(firedTransition.getDestination());// fire transition
			outputEvents.addAll(performEntryAction(basicFBTypeRuntime));
			firedTransition = evaluateOutTransitions(basicFBTypeRuntime);
		}
		// apply result
		for (final EventOccurrence eo : outputEvents) {
			((BasicFBTypeRuntime) eo.getFbRuntime()).setActiveState(basicFBTypeRuntime.getActiveState());
		}
		return outputEvents;
	}

	private static EList<EventOccurrence> performEntryAction(final BasicFBTypeRuntime basicFBTypeRuntime) {
		final var outputEvents = new BasicEList<EventOccurrence>();
		// Copy FBType for execution
		final var executedFbtype = EcoreUtil.copy(basicFBTypeRuntime.getBasicfbtype());
		for (final ECAction action : basicFBTypeRuntime.getActiveState().getECAction()) {
			if (action.getAlgorithm() != null) {
				processAlgorithmWithEvaluator(executedFbtype, action.getAlgorithm());
			}
			if (action.getOutput() != null) {
				processOutputEvent(basicFBTypeRuntime, action.getOutput(), outputEvents, executedFbtype);
			}
		}
		return outputEvents;
	}

	private static void processAlgorithmWithEvaluator(final BaseFBType basefbtype, final Algorithm algorithm) {
		if (!(algorithm instanceof STAlgorithm)) {
			throw new IllegalArgumentException("StructuredTextAlgorithm object could not be found"); //$NON-NLS-1$
		}
		final List<VarDeclaration> varDecls = new ArrayList<>(basefbtype.getInterfaceList().getInputVars());
		varDecls.addAll(basefbtype.getInterfaceList().getOutputVars());
		varDecls.addAll(basefbtype.getInternalVars());
		varDecls.addAll(basefbtype.getInternalConstVars());
		final List<Variable<?>> vars = varDecls.stream()
				.map(v -> VariableOperations.newVariable(v, v.getValue().getValue())).collect(Collectors.toList());
		final FBVariable fbVar = new FBVariable("THIS", basefbtype, Collections.emptyList()); //$NON-NLS-1$
		Class<? extends FBType> baseFBClass = null;
		if (basefbtype instanceof BasicFBType) {
			baseFBClass = BasicFBType.class;
		} else if (basefbtype instanceof SimpleFBType) {
			baseFBClass = SimpleFBType.class;
		}
		final Evaluator fbEval = EvaluatorFactory.createEvaluator(basefbtype, baseFBClass, fbVar, vars, null);
		final Evaluator algoEval = fbEval.getChildren().entrySet().stream()
				.filter(entry -> entry.getKey().getName().equals(algorithm.getName())).findAny().map(Entry::getValue)
				.orElse(null);
		if (algoEval != null) {
			try {
				algoEval.evaluate();
				vars.forEach(v -> {
					final VarDeclaration varDecl = varDecls.stream().filter(vd -> vd.getName().equals(v.getName()))
							.findAny().orElse(null);
					final String value = v.getValue().toString();
					varDecl.getValue().setValue(value);
				});
			} catch (final EvaluatorException e) {
				FordiacLogHelper.logError("Algorithm: " + algorithm.getName(), e);//$NON-NLS-1$
			} catch (final InterruptedException e) {
				FordiacLogHelper.logError("Algorithm: " + algorithm.getName(), e);//$NON-NLS-1$
				Thread.currentThread().interrupt();
			}
		}
	}

	private static boolean processConditionWithEvaluator(final BasicFBType basicFBType,
			final ECTransition ecTransition) {

		if (ecTransition.getConditionExpression().isEmpty()) {
			throw new IllegalArgumentException("ConditionExpression object cannot be empty"); //$NON-NLS-1$
		}
		final List<VarDeclaration> varDecls = new ArrayList<>(basicFBType.getInterfaceList().getInputVars());
		varDecls.addAll(basicFBType.getInterfaceList().getOutputVars());
		varDecls.addAll(basicFBType.getInternalVars());
		final List<Variable<?>> vars = varDecls.stream()
				.map(v -> VariableOperations.newVariable(v, v.getValue().getValue())).collect(Collectors.toList());
		final FBVariable fbVar = new FBVariable("THIS", basicFBType, Collections.emptyList()); //$NON-NLS-1$
		final Class<? extends FBType> baseFBClass = BasicFBType.class;

		final Evaluator fbEval = EvaluatorFactory.createEvaluator(basicFBType, baseFBClass, fbVar, vars, null);
		if (fbEval instanceof BasicFBEvaluator) {
			final BasicFBEvaluator fbEvaluator = (BasicFBEvaluator) fbEval;
			final Map<ECTransition, Evaluator> ecTransitionToEvaluator = fbEvaluator.getTransitionEvaluators();
			final Optional<Entry<ECTransition, Evaluator>> findEvaluator = ecTransitionToEvaluator.entrySet().stream()
					.filter(entryEvaluator -> entryEvaluator.getKey().getConditionExpression()
							.contentEquals(ecTransition.getConditionExpression()))
					.findFirst();
			if (findEvaluator.isEmpty()) {
				return false;
			}

			final Evaluator evaluator = findEvaluator.get().getValue();
			try {
				final org.eclipse.fordiac.ide.model.eval.value.Value value = evaluator.evaluate();
				if (value instanceof BoolValue) {
					return ((BoolValue) value).boolValue();
				}
				throw new IllegalStateException("The evaluator does not return a boolean value"); //$NON-NLS-1$
			} catch (final EvaluatorException e) {
				FordiacLogHelper.logError("Condition Expression: " + evaluator.getName(), //$NON-NLS-1$
						e);
			} catch (final InterruptedException e) {
				FordiacLogHelper.logError("Condition Expression: " + evaluator.getName(), //$NON-NLS-1$
						e);
				Thread.currentThread().interrupt();
			}

		}
		return false;
	}

	private static void processOutputEvent(final FBRuntimeAbstract runtime, final Event output,
			final BasicEList<EventOccurrence> outputEvents/* , Resource fBTypeResource */,
			final FBType executedFbtype) {
		// Copy FBTypeRuntime
		final FBRuntimeAbstract newFBTypeRT = EcoreUtil.copy(runtime);
		// Copy FBType
		final FBType copyFBType = EcoreUtil.copy(executedFbtype);
		// Add copy FBType to the RuntimeFBType
		if (runtime instanceof BasicFBTypeRuntime) {
			((BasicFBTypeRuntime) newFBTypeRT).setBasicfbtype((BasicFBType) copyFBType);
		} else if (runtime instanceof SimpleFBTypeRuntime) {
			((SimpleFBTypeRuntime) newFBTypeRT).setSimpleFBType((SimpleFBType) copyFBType);
		} else {
			throw new UnsupportedOperationException();
		}
		final var newEventOccurrence = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
		newEventOccurrence.setFbRuntime(newFBTypeRT);
		// Event
		newEventOccurrence.setEvent(output);
		outputEvents.add(newEventOccurrence);
	}

	private ECTransition evaluateOutTransitions(final BasicFBTypeRuntime basicFBTypeRuntime) {
		final var outTransitions = basicFBTypeRuntime.getActiveState().getOutTransitions();
		for (final ECTransition outTransition : outTransitions) {
			if (transitionCanFire(outTransition, basicFBTypeRuntime)) {
				return outTransition;
			}
		}
		return null;
	}

	private boolean transitionCanFire(final ECTransition outTransition, final BasicFBTypeRuntime basicFBTypeRuntime) {
		final var event = outTransition.getConditionEvent();
		if (transitionHoldsFor(event)) {
			final var condExpression = outTransition.getConditionExpression();
			if (condExpression.isEmpty() || "1".equals(condExpression)) { //$NON-NLS-1$
				return true;
			}
			return processConditionWithEvaluator(basicFBTypeRuntime.getBasicfbtype(), outTransition);

		}
		return false;
	}

	private boolean transitionHoldsFor(final Event event) {
		return (event == null) || (event.getName().equals(this.eventOccurrence.getEvent().getName())
				&& this.eventOccurrence.isActive());
	}

	private void isConsumed() {
		this.eventOccurrence.setActive(false);
		// The event was consumed, so it was not ignored
		this.eventOccurrence.setIgnored(false);
	}

	@Override
	public EList<EventOccurrence> runSimpleFBType(final SimpleFBTypeRuntime simpleFBTypeRuntime) {
		// Initialization of variables
		final SimpleFBType simpleFBType = simpleFBTypeRuntime.getSimpleFBType();
		VariableUtils.fBVariableInitialization(simpleFBType);
		final var outputEvents = new BasicEList<EventOccurrence>();
		// Make a copy to execute the SimpleFBType
		final SimpleFBType executedSimpleFBType = EcoreUtil.copy(simpleFBType);
		processAlgorithmWithEvaluator(executedSimpleFBType, simpleFBType.getAlgorithm().get(0));
		isConsumed();
		final Event event = simpleFBType.getInterfaceList().getEventOutputs().get(0);
		processOutputEvent(simpleFBTypeRuntime, event, outputEvents,
				executedSimpleFBType/* (FBType) fBTypeResource.getContents().get(0) */);
		return outputEvents;
	}

	@Override
	public EList<EventOccurrence> runFBNetwork(final FBNetworkRuntime fBNetworkRuntime) {
		// run FB Type to get the output events for the instance in the network
		FBRuntimeAbstract runtime = fBNetworkRuntime.getTypeRuntimes().get(eventOccurrence.getParentFB());
		if (runtime == null) {
			runtime = RuntimeFactory.createFrom(EcoreUtil.copy(eventOccurrence.getParentFB().getType()));
			RuntimeFactory.setStartState(runtime, "START"); //$NON-NLS-1$
			fBNetworkRuntime.getTypeRuntimes().put(eventOccurrence.getParentFB(), runtime);
		}
		// sample Data Input
		sampleDataInput(runtime, fBNetworkRuntime);

		// process event by running the respective type
		final EList<EventOccurrence> outputEvents = runFBType(runtime, eventOccurrence);

		// mapping the output event occurrences to the network
		createTransactionsForConnectedPins(outputEvents, fBNetworkRuntime);

		return outputEvents;
	}

	private void createTransactionsForConnectedPins(final EList<EventOccurrence> typeOutputEos,
			final FBNetworkRuntime fBNetworkRuntime) {
		// Extract the returned values from the FBTypeRuntime to FBNetwork
		typeOutputEos.forEach(eo -> {
			extractOutputDataFromTypeRuntime(eo);
			eo.setParentFB(eventOccurrence.getParentFB());
		});
		typeOutputEos.forEach(eo -> writeDataOutputsToConnections(eo, fBNetworkRuntime));
		typeOutputEos.forEach(typeEo -> {
			// generate transactions for triggering all subsequent blocks
			final EventOccurrence networkEo = getCorrespondingNetworkEvent(typeEo, fBNetworkRuntime);
			typeEo.getCreatedTransactions().addAll(processEventConns(fBNetworkRuntime, networkEo));
		});
	}

	private EventOccurrence getCorrespondingNetworkEvent(final EventOccurrence typeEo,
			final FBNetworkRuntime fBNetworkRuntime) {
		final Event mappedEvent = (Event) eventOccurrence.getParentFB()
				.getInterfaceElement(typeEo.getEvent().getName());
		final EventOccurrence networkEo = EventOccFactory.createFrom(mappedEvent, EcoreUtil.copy(fBNetworkRuntime));
		networkEo.setParentFB(eventOccurrence.getParentFB());
		return networkEo;
	}

	private void extractOutputDataFromTypeRuntime(final EventOccurrence outputEo) {
		final FBType typeAfterExecution = (FBType) outputEo.getFbRuntime().getModel();
		final Event returnedEvent = (Event) typeAfterExecution.getInterfaceList()
				.getInterfaceElement(outputEo.getEvent().getName());
		for (final With w : returnedEvent.getWith()) {
			final VarDeclaration associatedVar = w.getVariables();
			final VarDeclaration destVar = (VarDeclaration) eventOccurrence.getParentFB()
					.getInterfaceElement(associatedVar.getName());
			destVar.setValue(associatedVar.getValue());
		}
	}

	private void sampleDataInput(final FBRuntimeAbstract runtime, final FBNetworkRuntime fBNetworkRuntime) {
		final EList<VarDeclaration> networkVarsSample = getAssociatedDataPins(this.eventOccurrence);
		networkVarsSample.forEach(varDec -> {
			Value value = null;
			if (varDec.getInputConnections().isEmpty()) {
				// Input parameter
				value = varDec.getValue();
			} else {
				// Only one data input allowed
				final Connection conn = varDec.getInputConnections().get(0);
				value = fBNetworkRuntime.getTransferData().get(conn);
				if ((value == null) || value.getValue().isBlank()) {
					value = varDec.getValue();
				}
			}
			final VarDeclaration typeVarDec = (VarDeclaration) ((FBType) runtime.getModel()).getInterfaceList()
					.getInterfaceElement(varDec.getName());
			typeVarDec.setValue(EcoreUtil.copy(value));
		});
	}

	private static List<FBTransaction> processEventConns(final FBNetworkRuntime fBNetworkRuntime,
			final EventOccurrence outputEo) {
		final List<FBTransaction> generatedT = new ArrayList<>();
		if (outputEo.getEvent().isIsInput()) {
			// very first transaction (if needed) / initial trigger pin
			generatedT.add(createNewInitialTransaction(outputEo.getEvent(), fBNetworkRuntime));
		} else {
			// Find the Original Pins for all connected FBs
			final List<IInterfaceElement> destinations = findConnectedPins(outputEo.getEvent());
			for (final IInterfaceElement dest : destinations) {
				generatedT.add(createNewTransaction(dest, fBNetworkRuntime, outputEo));
			}
		}
		return generatedT;
	}

	private static FBTransaction createNewInitialTransaction(final IInterfaceElement dest,
			final FBNetworkRuntime fBNetworkRuntime) {
		final FBNetworkRuntime copiedRt = EcoreUtil.copy(fBNetworkRuntime);
		final EventOccurrence destinationEventOccurence = EventOccFactory.createFrom((Event) dest, copiedRt);
		destinationEventOccurence.setParentFB(dest.getFBNetworkElement());
		return TransactionFactory.createFrom(destinationEventOccurence);
	}

	private static FBTransaction createNewTransaction(final IInterfaceElement dest,
			final FBNetworkRuntime fBNetworkRuntime, final EventOccurrence sourceEventOccurrence) {
		final FBNetworkRuntime copyFBNetworkRuntime = EcoreUtil.copy(fBNetworkRuntime);
		final EventOccurrence destinationEventOccurence = EventOccFactory.createFrom((Event) dest,
				copyFBNetworkRuntime);
		destinationEventOccurence.setParentFB(dest.getFBNetworkElement());
		final FBTransaction transaction = TransactionFactory.createFrom(destinationEventOccurence);
		sourceEventOccurrence.getCreatedTransactions().add(transaction);
		return transaction;
	}

	private static void writeDataOutputsToConnections(final EventOccurrence eo, final FBNetworkRuntime runtime) {
		final EList<VarDeclaration> networkVarsSample = getAssociatedDataPins(eo);
		final EMap<Connection, Value> map = runtime.getTransferData();
		networkVarsSample.forEach(variable -> variable.getOutputConnections().stream()
				.forEach(outputConnection -> map.put(outputConnection, EcoreUtil.copy(variable.getValue()))));
	}

	private static EList<VarDeclaration> getAssociatedDataPins(final EventOccurrence sourceEventOcurrence) {
		// Sample data
		final Event sourceTypeEvent = (Event) findTypeOfPinInNetwork(sourceEventOcurrence);
		final EList<VarDeclaration> varsToSample = sourceTypeEvent.getWith().stream().map(With::getVariables)
				.collect(Collectors.toCollection(BasicEList::new));

		// Find the pins on the network
		final EList<VarDeclaration> networkVarsSample = new BasicEList<>();
		varsToSample.forEach(iel -> {
			final IInterfaceElement interfaceElement = sourceEventOcurrence.getParentFB().getInterface()
					.getInterfaceElement(iel.getName());
			networkVarsSample.add((VarDeclaration) interfaceElement);
		});
		return networkVarsSample;
	}

	private static IInterfaceElement findTypeOfPinInNetwork(final EventOccurrence sourceEventOcurrence) {
		return sourceEventOcurrence.getParentFB().getType().getInterfaceList()
				.getInterfaceElement(sourceEventOcurrence.getEvent().getName());
	}

	private static List<IInterfaceElement> findConnectedPins(final IInterfaceElement interfaceElement) {
		final List<IInterfaceElement> destinations = new ArrayList<>();
		interfaceElement.getOutputConnections().forEach(conn -> destinations.add(conn.getDestination()));
		return destinations;
	}

}

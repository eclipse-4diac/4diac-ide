/*******************************************************************************
 * Copyright (c) 2021, 2025 Johannes Kepler University Linz and others
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

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
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
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EccTrace;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FunctionFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventOccFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.IRunFBTypeVisitor;
import org.eclipse.fordiac.ide.fb.interpreter.api.LambdaVisitor;
import org.eclipse.fordiac.ide.fb.interpreter.api.RuntimeFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.VariableUtils;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.EvaluatorThreadPoolExecutor;
import org.eclipse.fordiac.ide.model.eval.fb.BasicFBEvaluator;
import org.eclipse.fordiac.ide.model.eval.value.BoolValue;
import org.eclipse.fordiac.ide.model.eval.value.FBValue;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;

public class DefaultRunFBType implements IRunFBTypeVisitor {

	private final EventOccurrence eventOccurrence;
	private static Map<String, Evaluator> evaluatorCache = new HashMap<>();

	public DefaultRunFBType(final EventOccurrence eventOccurrence) {
		this.eventOccurrence = eventOccurrence;
	}

	public static void clearCaches() {
		evaluatorCache.clear();
	}

	private static String getCacheKey(final EventOccurrence eventOccurrence) {
		if (eventOccurrence.getParentFB() != null) {
			return eventOccurrence.getParentFB().getQualifiedName();
		}
		if (eventOccurrence.getFbRuntime() != null) {
			return eventOccurrence.getFbRuntime().getModel().getName();
		}
		return "no-name"; //$NON-NLS-1$
	}

	public static Function<Object, Object> of(final IRunFBTypeVisitor runTypeVisitor) {
		return new LambdaVisitor<>() //
				.on(BasicFBTypeRuntime.class).then(runTypeVisitor::runBasicFBType) //
				.on(SimpleFBTypeRuntime.class).then(runTypeVisitor::runSimpleFBType) //
				.on(FunctionFBTypeRuntime.class).then(runTypeVisitor::runFunctionFBType) //
				.on(CompositeFBTypeRuntime.class).then(runTypeVisitor::runCompositeFBType) //
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
			basicFBTypeRuntime.setActiveState(eCC.getStart().getName());
		}
		// apply event and evaluate transitions
		var firedTransition = evaluateOutTransitions(basicFBTypeRuntime);
		addToTrace(firedTransition, basicFBTypeRuntime.eContainer().eContainer());
		while (firedTransition != null) {
			isConsumed(this.eventOccurrence);
			basicFBTypeRuntime.setActiveState(firedTransition.getDestination().getName());// fire transition
			outputEvents.addAll(performEntryAction(basicFBTypeRuntime, eventOccurrence));
			firedTransition = evaluateOutTransitions(basicFBTypeRuntime);
			addToTrace(firedTransition, basicFBTypeRuntime.eContainer().eContainer());
		}
		return outputEvents;
	}

	private static void addToTrace(final ECTransition firedTransition, final EObject transaction) {
		if (transaction instanceof final FBTransaction fbTransaction
				&& fbTransaction.getTrace() instanceof final EccTrace eccTrace && (firedTransition != null)) {
			final TransitionTrace trace = OperationalSemanticsFactory.eINSTANCE.createTransitionTrace();
			if (firedTransition.getConditionEvent() != null) {
				trace.setCondEvent(firedTransition.getConditionEvent().getName());
			}
			trace.setCondExpression(firedTransition.getConditionExpression());
			trace.setSourceState(firedTransition.getSource().getName());
			trace.setDestinationState(firedTransition.getDestination().getName());
			eccTrace.getTransitionTraces().add(trace);
		}
	}

	private static EList<EventOccurrence> performEntryAction(final BasicFBTypeRuntime basicFBTypeRuntime,
			final EventOccurrence eventOccurrence) {
		final var outputEvents = new BasicEList<EventOccurrence>();
		for (final ECAction action : basicFBTypeRuntime.getActiveState(basicFBTypeRuntime.getActiveState())
				.getECAction()) {
			if (action.getAlgorithm() != null) {
				processAlgorithmWithEvaluator(basicFBTypeRuntime.getBasicfbtype(), action.getAlgorithm(),
						eventOccurrence);
			}
			if (action.getOutput() != null) {
				outputEvents.add(createOutputEventOccurrence(basicFBTypeRuntime, action.getOutput(),
						basicFBTypeRuntime.getBasicfbtype()));
			}
		}
		return outputEvents;
	}

	private static Variable<?> mapVar(final VarDeclaration vdec) {
		return VariableOperations.newVariable(vdec, vdec.getValue().getValue());
	}

	private static void processAlgorithmWithEvaluator(final BaseFBType basefbtype, final Algorithm algorithm,
			final EventOccurrence eventOccurrence) {
		if (!(algorithm instanceof STAlgorithm)) {
			throw new IllegalArgumentException("StructuredTextAlgorithm object could not be found"); //$NON-NLS-1$
		}
		final List<VarDeclaration> varDecls = new ArrayList<>(basefbtype.getInterfaceList().getInputVars());
		varDecls.addAll(basefbtype.getInterfaceList().getOutputVars());
		varDecls.addAll(basefbtype.getInternalVars());
		varDecls.addAll(basefbtype.getInternalConstVars());

		Evaluator eval = evaluatorCache.get(getCacheKey(eventOccurrence));
		if (eval == null) {
			Class<? extends FBType> baseFBClass = null;
			if (basefbtype instanceof BasicFBType) {
				baseFBClass = BasicFBType.class;
			} else if (basefbtype instanceof SimpleFBType) {
				baseFBClass = SimpleFBType.class;
			}
			final FBVariable fbVar = new FBVariable("THIS", basefbtype, Collections.emptyList()); //$NON-NLS-1$
			eval = EvaluatorFactory.createEvaluator(basefbtype, baseFBClass, fbVar, List.of(), null);
			evaluatorCache.put(getCacheKey(eventOccurrence), eval);
		}
		final Optional<Evaluator> algoEval = eval.getChildren().entrySet().stream()
				.filter(entry -> entry.getKey().getName().equals(algorithm.getName())).findAny().map(Entry::getValue);
		if (algoEval.isPresent()) {
			executeEvaluator(algoEval.get(), varDecls, basefbtype, eventOccurrence, algorithm.getName());
		}
	}

	private static void processFunctionWithEvaluator(final FunctionFBType functionFBType,
			final EventOccurrence eventOccurrence) {
		final List<VarDeclaration> varDecls = new ArrayList<>(functionFBType.getInterfaceList().getInputVars());
		varDecls.addAll(functionFBType.getInterfaceList().getOutputVars());
		// internal variables of function could be added by calling
		// STFunctionParseUtil2.parseFunctionBody, getting the root element which is a
		// STFunctionSource and then getting functions -> varDeclarationBlock ->
		// varDeclaration + converting it with VariableOperations.newVariable

		Evaluator eval = evaluatorCache.get(getCacheKey(eventOccurrence));
		if (eval == null) {
			final FBVariable fbVar = new FBVariable("THIS", functionFBType, Collections.emptyList()); //$NON-NLS-1$
			eval = EvaluatorFactory.createEvaluator(functionFBType, FunctionFBType.class, fbVar, List.of(), null);
			evaluatorCache.put(getCacheKey(eventOccurrence), eval);
		}
		executeEvaluator(eval, varDecls, functionFBType, eventOccurrence, functionFBType.getName());
	}

	private static void executeEvaluator(final Evaluator eval, final List<VarDeclaration> varDecls, final FBType type,
			final EventOccurrence eventOccurrence, final String name) {
		setEvaluatorInputState(eval, type.getInterfaceList().getInputVars());

		if (!(eventOccurrence.eContainer() instanceof final Transaction t)) {
			throw new IllegalArgumentException("Container of EO was not a Transaction"); //$NON-NLS-1$
		}

		try (final EvaluatorThreadPoolExecutor tpe = new EvaluatorThreadPoolExecutor(name)) {
			final Clock clock = Clock.fixed(Instant.ofEpochMilli(eventOccurrence.getStartTime()), ZoneOffset.UTC);
			tpe.setMonotonicClock(clock);
			tpe.execute(() -> {
				try {
					eval.evaluate();
					getEvaluatorOutputState(eval, varDecls);
				} catch (final EvaluatorException e) {
					t.getExceptions().add(e);
				} catch (final InterruptedException e) {
					t.getExceptions().add(e);
					Thread.currentThread().interrupt();
				}
			});
		}
	}

	private static void setEvaluatorInputState(final Evaluator eval, final List<VarDeclaration> inputVars) {
		if (!(eval.getContext().getValue() instanceof final FBValue fbval)) {
			return;
		}
		for (final VarDeclaration varDecl : inputVars) {
			final var member = fbval.getMembers().get(varDecl.getName());
			if (member != null) {
				member.setValue(varDecl.getValue().getValue());
			}
		}
	}

	private static void getEvaluatorOutputState(final Evaluator eval, final List<VarDeclaration> outputVars) {
		if (!(eval.getContext().getValue() instanceof final FBValue fbval)) {
			return;
		}
		for (final VarDeclaration varDecl : outputVars) {
			final var member = fbval.getMembers().get(varDecl.getName());
			if (member != null) {
				varDecl.getValue().setValue(member.getValue().toString());
			}
		}
	}

	private static boolean processConditionWithEvaluator(final EventOccurrence eventOccurrence,
			final BasicFBType basicFBType, final ECTransition ecTransition) {

		if (ecTransition.getConditionExpression().isEmpty()) {
			throw new IllegalArgumentException("ConditionExpression object cannot be empty"); //$NON-NLS-1$
		}
		if (!(eventOccurrence.eContainer() instanceof final Transaction t)) {
			throw new IllegalArgumentException("Container of EO was not a Transaction"); //$NON-NLS-1$
		}
		final List<VarDeclaration> varDecls = new ArrayList<>(basicFBType.getInterfaceList().getInputVars());
		varDecls.addAll(basicFBType.getInterfaceList().getOutputVars());
		varDecls.addAll(basicFBType.getInternalVars());
		final List<Variable<?>> vars = varDecls.stream().map(DefaultRunFBType::mapVar).collect(Collectors.toList());

		final FBVariable fbVar = new FBVariable("THIS", basicFBType, Collections.emptyList()); //$NON-NLS-1$
		final Class<? extends FBType> baseFBClass = BasicFBType.class;

		final Evaluator fbEval = EvaluatorFactory.createEvaluator(basicFBType, baseFBClass, fbVar, vars, null);
		if (fbEval instanceof final BasicFBEvaluator fbEvaluator) {
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
				final var value = evaluator.evaluate();
				if (value instanceof final BoolValue boolValue) {
					return boolValue.boolValue();
				}
				throw new IllegalStateException("The evaluator does not return a boolean value"); //$NON-NLS-1$
			} catch (final EvaluatorException e) {
				t.getExceptions().add(e);
			} catch (final InterruptedException e) {
				t.getExceptions().add(e);
				Thread.currentThread().interrupt();
			}
		}
		return false;
	}

	private static EventOccurrence createOutputEventOccurrence(final FBRuntimeAbstract runtime, final Event output,
			final FBType executedFbtype) {
		// Copy FBTypeRuntime
		final FBRuntimeAbstract newFBTypeRT = EcoreUtil.copy(runtime);
		// Copy FBType
		final FBType copyFBType = EcoreUtil.copy(executedFbtype);
		// Add copy FBType to the RuntimeFBType
		switch (newFBTypeRT) {
		case final BasicFBTypeRuntime b -> b.setBasicfbtype((BasicFBType) copyFBType);
		case final SimpleFBTypeRuntime s -> s.setSimpleFBType((SimpleFBType) copyFBType);
		case final FunctionFBTypeRuntime f -> f.setFunctionFBType((FunctionFBType) copyFBType);
		default -> throw new UnsupportedOperationException();
		}

		final var newEventOccurrence = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
		newEventOccurrence.setFbRuntime(newFBTypeRT);
		newEventOccurrence.setEvent(output);
		if (runtime instanceof FBNetworkRuntime) {
			newEventOccurrence.setParentFB(output.getBlockFBNetworkElement());
		}
		return newEventOccurrence;
	}

	private ECTransition evaluateOutTransitions(final BasicFBTypeRuntime basicFBTypeRuntime) {
		final var outTransitions = basicFBTypeRuntime.getActiveState(basicFBTypeRuntime.getActiveState())
				.getOutTransitions();
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
			return processConditionWithEvaluator(eventOccurrence, basicFBTypeRuntime.getBasicfbtype(), outTransition);

		}
		return false;
	}

	private boolean transitionHoldsFor(final Event event) {
		return (event == null) || (event.getName().equals(this.eventOccurrence.getEvent().getName())
				&& this.eventOccurrence.isActive());
	}

	private static void isConsumed(final EventOccurrence eo) {
		eo.setActive(false);
		// The event was consumed, so it was not ignored
		eo.setIgnored(false);
	}

	@Override
	public EList<EventOccurrence> runSimpleFBType(final SimpleFBTypeRuntime simpleFBTypeRuntime) {
		// Initialization of variables
		final SimpleFBType simpleFBType = simpleFBTypeRuntime.getSimpleFBType();
		VariableUtils.fBVariableInitialization(simpleFBType);

		final var actions = getActions(simpleFBType, eventOccurrence.getEvent().getName());
		final var outputEvents = new BasicEList<EventOccurrence>(actions.size());

		for (final SimpleECAction action : actions) {
			simpleFBType.getAlgorithm().stream().filter(a -> a.getName().equals(action.getAlgorithm())).findAny()
					.ifPresent(a -> processAlgorithmWithEvaluator(simpleFBType, a, eventOccurrence));
			outputEvents.add(createOutputEventOccurrence(simpleFBTypeRuntime, action.getOutput(), simpleFBType));
		}
		isConsumed(this.eventOccurrence);
		return outputEvents;
	}

	@Override
	public EList<EventOccurrence> runFunctionFBType(final FunctionFBTypeRuntime fBTypeRuntime) {
		final FunctionFBType functionFBType = fBTypeRuntime.getFunctionFBType();
		VariableUtils.fBVariableInitialization(functionFBType);

		// function types always have exactly 1 output event
		final Event event = functionFBType.getInterfaceList().getEventOutputs().get(0);
		processFunctionWithEvaluator(functionFBType, this.eventOccurrence);
		isConsumed(this.eventOccurrence);

		return ECollections.newBasicEList(createOutputEventOccurrence(fBTypeRuntime, event, functionFBType));
	}

	private static List<SimpleECAction> getActions(final SimpleFBType simpleFBType, final String inEvent) {
		// if we don't have ECStates, use first output/algorithm as fallback
		if (simpleFBType.getSimpleECStates() == null || simpleFBType.getSimpleECStates().isEmpty()) {
			final SimpleECAction action = LibraryElementFactory.eINSTANCE.createSimpleECAction();
			action.setAlgorithm(simpleFBType.getAlgorithm().get(0).getName());
			action.setOutput(simpleFBType.getInterfaceList().getEventOutputs().get(0));
			return List.of(action);
		}

		// find ECStates matching the input event and return their actions
		return simpleFBType.getSimpleECStates().stream().filter(state -> state.getName().equals(inEvent))
				.flatMap(state -> state.getSimpleECActions().stream()).toList();
	}

	@Override
	public EList<EventOccurrence> runCompositeFBType(final CompositeFBTypeRuntime fbTypeRuntime) {
		final CompositeFBType compType = fbTypeRuntime.getModel();
		fbTypeRuntime.setFbElement(eventOccurrence.getParentFB());

		final FBNetworkRuntime innerRT = fbTypeRuntime.getNetworkRuntime();
		final FBNetworkRuntime outerRT = innerRT.getOuterNetworkRuntime();
		final String eventName = eventOccurrence.getEvent().getName();
		final EList<Connection> outputs;
		final FBNetworkRuntime networkRT;

		if (eventOccurrence.getEvent().isIsInput()) {
			outputs = compType.getInterfaceList().getEvent(eventName).getOutputConnections();
			networkRT = innerRT;
			copyTransferDataToInner(outerRT, innerRT, fbTypeRuntime);
		} else {
			outputs = fbTypeRuntime.getFbElement().getInterface().getEvent(eventName).getOutputConnections();
			networkRT = outerRT;
			copyTransferDataToOuter(outerRT, innerRT, fbTypeRuntime);
		}
		return switchNetwork(outputs, networkRT);
	}

	private void copyTransferDataToInner(final FBNetworkRuntime outer, final FBNetworkRuntime inner,
			final CompositeFBTypeRuntime compRT) {
		for (final VarDeclaration varDec : getAssociatedDataPins(this.eventOccurrence, outer)) {
			Value value;
			final var dataInputs = varDec.getInputConnections();
			if (dataInputs.isEmpty()) {
				// Input parameter
				value = varDec.getValue();
			} else {
				// Only one data input allowed
				final Connection conn = dataInputs.get(0);
				value = outer.getTransferData().get(conn);
				if (VariableUtils.isEmptyValue(value)) {
					value = varDec.getValue();
				}
			}

			// write value to inner network transfer data
			final List<Connection> dataOutputs = compRT.getCompositeFBType().getInterfaceList()
					.getInput(varDec.getName()).getOutputConnections();
			for (final Connection output : dataOutputs) {
				inner.getTransferData().put(output, value);
			}
		}
	}

	private void copyTransferDataToOuter(final FBNetworkRuntime outer, final FBNetworkRuntime inner,
			final CompositeFBTypeRuntime compRT) {
		for (final VarDeclaration varDec : getAssociatedDataPins(this.eventOccurrence, outer)) {
			Value value;
			final var inputs = compRT.getCompositeFBType().getInterfaceList().getOutput(varDec.getName())
					.getInputConnections();
			if (inputs.isEmpty()) {
				// Input parameter
				value = varDec.getValue();
			} else {
				// Only one data input allowed
				final Connection conn = inputs.get(0);
				value = inner.getTransferData().get(conn);
				if (VariableUtils.isEmptyValue(value)) {
					value = varDec.getValue();
				}
			}

			// write value to outer network transfer data
			final List<Connection> dataOutputs = compRT.getFbElement().getInterface().getOutput(varDec.getName())
					.getOutputConnections();
			for (final Connection output : dataOutputs) {
				outer.getTransferData().put(output, value);
			}
		}
	}

	@Override
	public EList<EventOccurrence> runFBNetwork(final FBNetworkRuntime fBNetworkRuntime) {
		if (eventOccurrence.getParentFB() instanceof final UntypedSubApp uSubApp) {
			FBNetworkRuntime runtime;
			if (eventOccurrence.getEvent().isIsInput()) { // we are entering the inner SubApp networks
				// try to get existing network runtime for this SubApp or create new
				runtime = (FBNetworkRuntime) fBNetworkRuntime.getTypeRuntimes().get(uSubApp);
				if (runtime == null) {
					runtime = RuntimeFactory.createFrom(uSubApp.getSubAppNetwork());
					runtime.setOuterNetworkRuntime(fBNetworkRuntime);
					fBNetworkRuntime.getTypeRuntimes().put(uSubApp, runtime);
				}
			} else { // we are leaving the inner SubAPp network
				runtime = fBNetworkRuntime.getOuterNetworkRuntime();
			}
			return switchNetwork(eventOccurrence.getEvent().getOutputConnections(), runtime);
		}

		// run FB Type to get the output events for the instance in the network
		FBRuntimeAbstract runtime = fBNetworkRuntime.getTypeRuntimes().get(eventOccurrence.getParentFB());
		if (runtime == null) {
			final FBType copiedType = EcoreUtil.copy(eventOccurrence.getParentFB().getType());
			runtime = RuntimeFactory.createFrom(copiedType);
			fBNetworkRuntime.getTypeRuntimes().put(eventOccurrence.getParentFB(), runtime);
		}

		// sampling input & writing output is special for composite types
		if (runtime instanceof final CompositeFBTypeRuntime compTypeRT) {
			if (compTypeRT.getNetworkRuntime() == null) {
				final FBNetworkRuntime rt = RuntimeFactory.createFrom(compTypeRT.getCompositeFBType().getFBNetwork());
				rt.setOuterNetworkRuntime(fBNetworkRuntime);
				// put the composite runtime into the inner network, so we will find our way
				// back to the outer network
				rt.getTypeRuntimes().put(eventOccurrence.getParentFB(), compTypeRT);
				compTypeRT.setNetworkRuntime(rt);
			}
			return runFBType(runtime, eventOccurrence);
		}

		// sample Data Input
		sampleDataInput(runtime, fBNetworkRuntime);

		// process event by running the respective type
		final EList<EventOccurrence> typeOutputEos = runFBType(runtime, eventOccurrence);

		// Extract the returned values from the FBTypeRuntime to FBNetwork
		writeDataOutput(fBNetworkRuntime, typeOutputEos);

		// mapping the output event occurrences to the network
		createTransactionsForConnectedPins(typeOutputEos, fBNetworkRuntime);

		return typeOutputEos;
	}

	private EList<EventOccurrence> switchNetwork(final List<Connection> outputs, final FBNetworkRuntime runtime) {
		final var outputEvents = new BasicEList<EventOccurrence>();

		for (final Connection conn : outputs) {
			final EventOccurrence outputEO = EventOccFactory.createFrom(eventOccurrence.getEvent(), runtime);

			// add transaction
			final EventConnection eventConn = (EventConnection) conn;
			final EventOccurrence inputEO = EventOccFactory.createFrom(eventConn.getEventDestination());
			inputEO.setResultFBRuntime(runtime);
			final FBTransaction fbTrans = TransactionFactory.createFrom(inputEO);
			outputEO.getCreatedTransactions().add(fbTrans);

			outputEvents.add(outputEO);
		}
		return outputEvents;
	}

	private void writeDataOutput(final FBNetworkRuntime fBNetworkRuntime, final EList<EventOccurrence> typeOutputEos) {
		typeOutputEos.forEach(eo -> {
			extractOutputDataFromTypeRuntime(eo, fBNetworkRuntime);
			eo.setParentFB(eventOccurrence.getParentFB());
		});
		typeOutputEos.forEach(eo -> writeDataOutputsToConnections(eo, fBNetworkRuntime));
	}

	private void createTransactionsForConnectedPins(final EList<EventOccurrence> typeOutputEos,
			final FBNetworkRuntime fBNetworkRuntime) {
		typeOutputEos.forEach(typeEo -> {
			// generate transactions for triggering all subsequent blocks
			final EventOccurrence networkEo = getCorrespondingNetworkEvent(typeEo, fBNetworkRuntime);
			typeEo.getCreatedTransactions().addAll(processEventConns(fBNetworkRuntime, networkEo));
		});
	}

	private EventOccurrence getCorrespondingNetworkEvent(final EventOccurrence typeEo,
			final FBNetworkRuntime fBNetworkRuntime) {
		final Event mappedEvent = eventOccurrence.getParentFB().getInterface().getEvent(typeEo.getEvent().getName());
		final EventOccurrence networkEo = EventOccFactory.createFrom(mappedEvent, EcoreUtil.copy(fBNetworkRuntime));
		networkEo.setParentFB(eventOccurrence.getParentFB());
		return networkEo;
	}

	private void extractOutputDataFromTypeRuntime(final EventOccurrence outputEo, final FBNetworkRuntime destRuntime) {
		final FBType typeAfterExecution = outputEo.getFbRuntime().getModel();
		final Event returnedEvent = (Event) typeAfterExecution.getInterfaceList()
				.getInterfaceElement(outputEo.getEvent().getName());
		final FB instance = destRuntime.getFbnetwork().getFBNamed(eventOccurrence.getParentFB().getName());
		for (final With w : returnedEvent.getWith()) {
			final VarDeclaration associatedVar = w.getVariables();
			final VarDeclaration destVar = (VarDeclaration) instance.getInterfaceElement(associatedVar.getName());
			destVar.setValue(EcoreUtil.copy(associatedVar.getValue()));
		}
	}

	private void sampleDataInput(final FBRuntimeAbstract runtime, final FBNetworkRuntime fBNetworkRuntime) {
		final EList<VarDeclaration> networkVarsSample = getAssociatedDataPins(this.eventOccurrence, fBNetworkRuntime);
		networkVarsSample.forEach(varDec -> {
			Value value = null;
			if (varDec.getInputConnections().isEmpty()) {
				// Input parameter
				value = varDec.getValue();
			} else {
				// Only one data input allowed
				final Connection conn = varDec.getInputConnections().get(0);
				value = fBNetworkRuntime.getTransferData().get(conn);
				if (VariableUtils.isEmptyValue(value)) {
					value = varDec.getValue();
				}
			}
			final VarDeclaration typeVarDec = getEquivalentDataPinFromType(runtime, varDec);
			if (!VariableUtils.isEmptyValue(value)) {
				typeVarDec.setValue(EcoreUtil.copy(value));
			}
		});
	}

	protected static VarDeclaration getEquivalentDataPinFromType(final FBRuntimeAbstract runtime,
			final VarDeclaration varDec) {
		final FBType type = runtime.getModel();
		return (VarDeclaration) type.getInterfaceList().getInterfaceElement(varDec.getName());
	}

	private static Event getEquivalentEventTypePin(final EventOccurrence sourceEventOcurrence) {
		final BlockFBNetworkElement fbElem = sourceEventOcurrence.getParentFB();
		return (Event) fbElem.getInterface().getInterfaceElement(sourceEventOcurrence.getEvent().getName());
	}

	private static List<FBTransaction> processEventConns(final FBNetworkRuntime fBNetworkRuntime,
			final EventOccurrence outputEo) {
		final List<FBTransaction> generatedT = new ArrayList<>();
		if (outputEo.getEvent().isIsInput()) {
			// very first transaction (if needed) / initial trigger pin
			generatedT.add(createNewInitialTransaction(outputEo.getEvent(), fBNetworkRuntime));
		} else {
			// Find the Original Pins for all connected FBs
			for (final Connection conn : outputEo.getEvent().getOutputConnections()) {
				generatedT.add(createNewTransaction(conn.getDestination(), outputEo, fBNetworkRuntime));
			}
		}
		return generatedT;
	}

	private static FBTransaction createNewInitialTransaction(final IInterfaceElement dest,
			final FBNetworkRuntime fBNetworkRuntime) {
		final FBNetworkRuntime copiedRt = EcoreUtil.copy(fBNetworkRuntime);
		final EventOccurrence destinationEventOccurence = EventOccFactory.createFrom((Event) dest, copiedRt);
		destinationEventOccurence.setParentFB(dest.getBlockFBNetworkElement());
		return TransactionFactory.createFrom(destinationEventOccurence);
	}

	private static FBTransaction createNewTransaction(final IInterfaceElement dest, final EventOccurrence sourceEO,
			final FBNetworkRuntime fbNetworkRuntime) {
		final EventOccurrence destEO = EventOccFactory.createFrom((Event) dest, null);

		// if the destination EO does not have a parent, it might be the outgoing
		// connection for the network inside a composite
		if (destEO.getParentFB() == null
				// Interface List -> FB Type -> FB Type Runtime
				&& dest.eContainer().eContainer().eContainer() instanceof final CompositeFBTypeRuntime rt) {
			destEO.setParentFB(rt.getFbElement());
		}

		final FBTransaction transaction = TransactionFactory.createFrom(destEO);
		sourceEO.getCreatedTransactions().add(transaction);
		return transaction;
	}

	private static void writeDataOutputsToConnections(final EventOccurrence eo, final FBNetworkRuntime runtime) {
		final EMap<Connection, Value> map = runtime.getTransferData();
		// if connection does not have a value yet, initialize it with the default value
		final FBType type = eo.getParentFB().getType();
		type.getInterfaceList().getOutputVars().forEach(pin -> getEquivalentNetworkPin(runtime, eo.getParentFB(), pin)
				.getOutputConnections().stream().forEach(conn -> {
					if (map.get(conn) == null) {
						final String val = InitialValueHelper.getInitialOrDefaultValue(pin);
						final Value value = LibraryElementFactory.eINSTANCE.createValue();
						value.setValue(val);
						map.put(conn, value);
					}
				}));

		final EList<VarDeclaration> networkVarsSample = getAssociatedDataPins(eo, runtime);

		networkVarsSample.forEach(variable -> variable.getOutputConnections().stream().forEach(
				outputConnection -> map.put(outputConnection, EcoreUtil.copy(getOutputValue(variable, runtime)))));
	}

	private static IInterfaceElement getEquivalentNetworkPin(final FBNetworkRuntime runtime,
			final FBNetworkElement parentFB, final VarDeclaration pin) {
		return runtime.getFbnetwork().getFBNamed(parentFB.getName()).getInterfaceElement(pin.getName());
	}

	protected static Value getOutputValue(final VarDeclaration variable, final FBNetworkRuntime runtime) {
		if (!VariableUtils.isEmptyValue(variable.getValue())) {
			return variable.getValue();
		}
		final var typePin = getEquivalentDataPinFromType(runtime, variable);
		if (!VariableUtils.isEmptyValue(typePin.getValue())) {
			return typePin.getValue();
		}
		final Value value = LibraryElementFactory.eINSTANCE.createValue();
		value.setValue(InitialValueHelper.getDefaultValue(variable));
		return value;
	}

	private static EList<VarDeclaration> getAssociatedDataPins(final EventOccurrence sourceEventOcurrence,
			final FBNetworkRuntime runtime) {
		// Sample data
		final Event sourceTypeEvent = getEquivalentEventTypePin(sourceEventOcurrence);
		final EList<VarDeclaration> varsToSample = sourceTypeEvent.getWith().stream().map(With::getVariables)
				.collect(Collectors.toCollection(BasicEList::new));

		// Find the pins on the network
		final EList<VarDeclaration> networkVarsSample = new BasicEList<>();
		for (final VarDeclaration iel : varsToSample) {
			final IInterfaceElement interfaceElement = getEquivalentNetworkPin(runtime,
					sourceEventOcurrence.getParentFB(), iel);
			networkVarsSample.add((VarDeclaration) interfaceElement);
		}
		return networkVarsSample;
	}
}

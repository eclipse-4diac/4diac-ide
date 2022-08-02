/*******************************************************************************
 * Copyright (c) 2021, 2022 Johannes Kepler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.api.IRunFBTypeVisitor;
import org.eclipse.fordiac.ide.fb.interpreter.api.LambdaVisitor;
import org.eclipse.fordiac.ide.fb.interpreter.impl.EvaluateExpressionImpl;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.VariableUtils;
import org.eclipse.fordiac.ide.fb.interpreter.parser.ConditionExpressionXMI;
import org.eclipse.fordiac.ide.fb.interpreter.parser.DefaultParserXMI;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
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
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Expression;
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
			fbNetwork.getNetworkElements().forEach(networkElement -> {
				nameToFBNetwork.put(networkElement.getName(), networkElement);
			});
		}
	}

	public static Function<Object, Object> of(final IRunFBTypeVisitor runTypeVisitor, final EventManager eventManager) {
		return new LambdaVisitor<>().on(BasicFBTypeRuntime.class).then(runTypeVisitor::runBasicFBType)
				.on(SimpleFBTypeRuntime.class).then(runTypeVisitor::runSimpleFBType).on(FBNetworkRuntime.class)
				.then(fb -> runTypeVisitor.runFBNetwork(fb, eventManager));
	}

	@SuppressWarnings("unchecked")
	public static EList<EventOccurrence> runFBType(final FBRuntimeAbstract fbTypeRuntime,
			final EventOccurrence eventOccurrence, final EventManager eventManager) {
		final var defaultRun = new DefaultRunFBType(eventOccurrence);
		return (EList<EventOccurrence>) of(defaultRun, eventManager).apply(fbTypeRuntime);
	}

	@Override
	public EList<EventOccurrence> runBasicFBType(final BasicFBTypeRuntime basicFBTypeRuntime) {
		// Initialization of variables
		VariableUtils.fBVariableInitialization(basicFBTypeRuntime.getBasicfbtype());
		final var outputEvents = new BasicEList<EventOccurrence>();
		final var eCC = basicFBTypeRuntime.getBasicfbtype().getECC();
		// Create a resource if the BasicFBType does not have one
		final var fBTypeResource = new DefaultParserXMI().createFBResource(basicFBTypeRuntime.getBasicfbtype());
		// Active State
		final var eCState = basicFBTypeRuntime.getActiveState();
		if (eCState == null) {
			basicFBTypeRuntime.setActiveState(eCC.getStart());
		}
		var firedTransition = evaluateOutTransitions(basicFBTypeRuntime, fBTypeResource);
		while (firedTransition != null) {
			isConsumed();
			basicFBTypeRuntime.setActiveState(firedTransition.getDestination());// fire transition
			outputEvents.addAll(performEntryAction(basicFBTypeRuntime));
			firedTransition = evaluateOutTransitions(basicFBTypeRuntime, fBTypeResource);
		}
		basicFBTypeRuntime.setBasicfbtype((BasicFBType) fBTypeResource.getContents().get(0));
		// TODO can probably be improved by copying better
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
				processOutputEvent(basicFBTypeRuntime, action.getOutput(), outputEvents,
						/* fBTypeResource, */ executedFbtype);
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
		final List<Variable<?>> vars = varDecls.stream().map(VariableOperations::newVariable)
				.collect(Collectors.toList());
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

	private ECTransition evaluateOutTransitions(final BasicFBTypeRuntime basicFBTypeRuntime,
			final Resource fBTypeResource) {
		final var outTransitions = basicFBTypeRuntime.getActiveState().getOutTransitions();
		for (final ECTransition outTransition : outTransitions) {
			if (transitionCanFire(outTransition, fBTypeResource)) {
				return outTransition;
			}
		}
		return null;
	}

	private boolean transitionCanFire(final ECTransition outTransition, final Resource fBTypeResource) {
		final var event = outTransition.getConditionEvent();
		if (transitionHoldsFor(event)) {
			final var condExpression = outTransition.getConditionExpression();
			if (condExpression.isEmpty() || "1".equals(condExpression)) { //$NON-NLS-1$
				return true;
			}
			final var resource = new ConditionExpressionXMI(fBTypeResource.getResourceSet())
					.createXtextResourceFromConditionExp(condExpression);
			final var rootEObject = resource.getContents().get(0);
			if (rootEObject instanceof Expression) {
				final var evaluation = (Boolean) EvaluateExpressionImpl.of().apply(rootEObject);
				if (Boolean.TRUE.equals(evaluation)) {
					return true;
				}
			}
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
	public EList<EventOccurrence> runFBNetwork(final FBNetworkRuntime fBNetworkRuntime, final EventManager manager) {
		// run FB Type to get the output events for the instance in the network
		// TODO reuse the runtimes
		final BasicFBTypeRuntime runtime = OperationalSemanticsFactory.eINSTANCE.createBasicFBTypeRuntime();
		runtime.setBasicfbtype((BasicFBType) EcoreUtil.copy(eventOccurrence.getParentFB().getType()));
		// this.nameToFBNetwork.get(eventOccurrence.getParentFB().getType().getName())
		// TODO is this copy correct?
		runtime.setActiveState(runtime.getBasicfbtype().getECC().getStart());

		// Sample Data Input
		sampleDataInput(runtime, fBNetworkRuntime);

		final EList<EventOccurrence> outputEvents = runBasicFBType(runtime);

		final EList<EventOccurrence> networkEvents = new BasicEList<>();
		outputEvents.forEach(event -> createNetworkEvent(networkEvents, event));

		eventConnections(fBNetworkRuntime, manager, networkEvents);

		return networkEvents;
	}

	private void createNetworkEvent(final EList<EventOccurrence> networkEvents, final EventOccurrence event) {
		final EventOccurrence newEventOccurrence = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
		newEventOccurrence.setParentFB(eventOccurrence.getParentFB());
		newEventOccurrence.setFbRuntime(EcoreUtil.copy(event.getFbRuntime()));
		final Event mappedEvent = (Event) eventOccurrence.getParentFB().getInterfaceElement(event.getEvent().getName());
		newEventOccurrence.setEvent(mappedEvent);
		// Extract the returned values from the FBTypeRuntime to FBNetwork
		final Event returnedEvent = (Event) ((BasicFBTypeRuntime) event.getFbRuntime()).getBasicfbtype()
				.getInterfaceList().getInterfaceElement(event.getEvent().getName());
		returnedEvent.getWith().stream().forEach(
				w -> ((VarDeclaration) eventOccurrence.getParentFB().getInterfaceElement(w.getVariables().getName()))
						.setValue(EcoreUtil.copy(w.getVariables().getValue())));

		networkEvents.add(newEventOccurrence);
	}

	private void sampleDataInput(final BasicFBTypeRuntime runtime, final FBNetworkRuntime fBNetworkRuntime) {
		final EList<VarDeclaration> networkVarsSample = sampleData(this.eventOccurrence);
		networkVarsSample.forEach(varDec -> {
			Value value = null;
			if (varDec.getInputConnections().isEmpty()) {
				// Input parameter
				value = varDec.getValue();
			} else {
				// Only one data input allowed
				final Connection conn = varDec.getInputConnections().get(0);
				value = fBNetworkRuntime.getTransferData().get(conn);
				// TODO check? value.getValue().isBlank()
				if (value == null) {
					value = varDec.getValue();
				}
			}
			final VarDeclaration typeVarDec = (VarDeclaration) runtime.getBasicfbtype().getInterfaceList()
					.getInterfaceElement(varDec.getName());
			typeVarDec.setValue(EcoreUtil.copy(value));
		});
	}

	private static void eventConnections(final FBNetworkRuntime fBNetworkRuntime, final EventManager manager,
			final EList<EventOccurrence> networkEvents) {
		networkEvents.forEach(e -> {
			// e.getEvent().getc
			if (e.getEvent().isIsInput()) {
				manager.getTransactions().add(createNewInitialTransaction(e.getEvent(), fBNetworkRuntime, e));
			} else {
				// Find the Original Pins
				final List<IInterfaceElement> destinations = findConnectedPins(e.getEvent());
				for (final IInterfaceElement dest : destinations) {
					manager.getTransactions().add(createNewTransaction(dest, fBNetworkRuntime, e));
				}
			}
		});
	}

	// TODO refactor createNewTransaction
	private static FBTransaction createNewInitialTransaction(final IInterfaceElement dest,
			final FBNetworkRuntime fBNetworkRuntime, final EventOccurrence sourceEventOcurrence) {
		final EventOccurrence destinationEventOccurence = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
		destinationEventOccurence.setEvent((Event) EcoreUtil.copy(dest));
		final FBNetworkRuntime copyFBNetworkRuntime = EcoreUtil.copy(fBNetworkRuntime);
		destinationEventOccurence.setFbRuntime(copyFBNetworkRuntime);
		destinationEventOccurence.setParentFB(dest.getFBNetworkElement());
		final FBTransaction transaction = OperationalSemanticsFactory.eINSTANCE.createFBTransaction();
		transaction.setInputEventOccurrence(destinationEventOccurence);

		return transaction;
	}

	private static FBTransaction createNewTransaction(final IInterfaceElement dest,
			final FBNetworkRuntime fBNetworkRuntime, final EventOccurrence sourceEventOcurrence) {
		final EventOccurrence destinationEventOccurence = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
		destinationEventOccurence.setEvent((Event) EcoreUtil.copy(dest));
		final FBNetworkRuntime copyFBNetworkRuntime = EcoreUtil.copy(fBNetworkRuntime);
		destinationEventOccurence.setFbRuntime(copyFBNetworkRuntime);
		destinationEventOccurence.setParentFB(dest.getFBNetworkElement());
		final FBTransaction transaction = OperationalSemanticsFactory.eINSTANCE.createFBTransaction();
		transaction.setInputEventOccurrence(destinationEventOccurence);

		sampleDataOutput(sourceEventOcurrence, destinationEventOccurence, copyFBNetworkRuntime, transaction);

		return transaction;
	}

	private static void sampleDataOutput(final EventOccurrence sourceEventOcurrence,
			final EventOccurrence destinationEventOccurence, final FBNetworkRuntime copyFBNetworkRuntime,
			final FBTransaction transaction) {

		final EList<VarDeclaration> networkVarsSample = sampleData(sourceEventOcurrence);

		final EMap<Connection, Value> map = copyFBNetworkRuntime.getTransferData();
		// TODO this should be a mapTo?
		networkVarsSample.forEach(variable -> variable.getOutputConnections().stream()
				.forEach(outputConnection -> map.put(outputConnection, EcoreUtil.copy(variable.getValue()))));

		destinationEventOccurence.getCreatedTransactions().add(transaction);
	}

	private static EList<VarDeclaration> sampleData(final EventOccurrence sourceEventOcurrence) {
		// Sample data
		final Event sourceTypeEvent = (Event) findPinInNetwork(sourceEventOcurrence);
		final EList<VarDeclaration> varsToSample = sourceTypeEvent.getWith().stream().map(With::getVariables)
				.collect(Collectors.toCollection(BasicEList::new));

		// Find the pins on the network
		final EList<VarDeclaration> networkVarsSample = new BasicEList<>();
		// TODO this should be a mapTo?
		varsToSample.forEach(iel -> {
			final IInterfaceElement interfaceElement = sourceEventOcurrence.getParentFB().getInterface()
					.getInterfaceElement(iel.getName());
			networkVarsSample.add((VarDeclaration) interfaceElement);
		});
		return networkVarsSample;
	}

	private static IInterfaceElement findPinInNetwork(final EventOccurrence sourceEventOcurrence) {
		return sourceEventOcurrence.getParentFB().getType().getInterfaceList()
				.getInterfaceElement(sourceEventOcurrence.getEvent().getName());
	}

	private static List<IInterfaceElement> findConnectedPins(final IInterfaceElement interfaceElement) {
		final List<IInterfaceElement> destinations = new ArrayList<>();
		interfaceElement.getOutputConnections().forEach(conn -> destinations.add(conn.getDestination()));
		return destinations;
	}
}

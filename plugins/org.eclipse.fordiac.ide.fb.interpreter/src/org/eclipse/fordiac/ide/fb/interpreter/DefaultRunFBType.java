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
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
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
import org.eclipse.fordiac.ide.fb.interpreter.impl.EvalStatementImpl;
import org.eclipse.fordiac.ide.fb.interpreter.impl.EvaluateExpressionImpl;
import org.eclipse.fordiac.ide.fb.interpreter.mm.utils.VariableUtils;
import org.eclipse.fordiac.ide.fb.interpreter.parser.AlgorithmStXMI;
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
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Expression;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextAlgorithm;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class DefaultRunFBType implements IRunFBTypeVisitor{

	private final EventOccurrence eventOccurrence;
	
	public DefaultRunFBType(EventOccurrence eventOccurrence) {
		this.eventOccurrence = eventOccurrence;
	}

	public static Function<Object,Object> of(IRunFBTypeVisitor runTypeVisitor, EventManager eventManager) {
		return new LambdaVisitor<>()
				.on(BasicFBTypeRuntime.class).then(runTypeVisitor::runBasicFBType)
				.on(SimpleFBTypeRuntime.class).then(runTypeVisitor::runSimpleFBType)
				.on(FBNetworkRuntime.class).then(fb -> runTypeVisitor.runFBNetwork(fb, eventManager))
				;
	}

	@SuppressWarnings("unchecked")
	public static EList<EventOccurrence> runFBType(FBRuntimeAbstract fbTypeRuntime, EventOccurrence eventOccurrence, EventManager eventManager) {
		final var defaultRun = new DefaultRunFBType(eventOccurrence);
		return (EList<EventOccurrence>) of(defaultRun, eventManager).apply(fbTypeRuntime);
	}

	@Override
	public EList<EventOccurrence> runBasicFBType(BasicFBTypeRuntime basicFBTypeRuntime) {
		// Initialization of variables
		VariableUtils.fBVariableInitialization(basicFBTypeRuntime.getBasicfbtype());
		final var outputEvents = new BasicEList<EventOccurrence>();
		final var eCC = basicFBTypeRuntime.getBasicfbtype().getECC();
		//Create a resource if the BasicFBType does not have one
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
		basicFBTypeRuntime.setBasicfbtype((BasicFBType)fBTypeResource.getContents().get(0));
		// TODO can probably be improved by copying better
		for (final EventOccurrence eo : outputEvents) {
			((BasicFBTypeRuntime) eo.getFbRuntime()).setActiveState(basicFBTypeRuntime.getActiveState());
		}
		return outputEvents;
	}

	private EList<EventOccurrence> performEntryAction(BasicFBTypeRuntime basicFBTypeRuntime) {
		final var outputEvents = new BasicEList<EventOccurrence>();
		// Copy FBType for execution
		final var executedFbtype = EcoreUtil.copy(basicFBTypeRuntime.getBasicfbtype());
		for (final ECAction action : basicFBTypeRuntime.getActiveState().getECAction()) {
			if (action.getAlgorithm() != null) {
				processAlgorithmWithEvaluator(executedFbtype, action.getAlgorithm());
			}
			if (action.getOutput() != null) {
				processOutputEvent(basicFBTypeRuntime, action.getOutput(), outputEvents, /*fBTypeResource, */ executedFbtype);
			}
		}
		return outputEvents;
	}
	
	private static void processAlgorithmWithEvaluator(BaseFBType basefbtype, Algorithm algorithm) {
		if (!(algorithm instanceof STAlgorithm)) {
			throw new IllegalArgumentException("StructuredTextAlgorithm object could not be found"); //$NON-NLS-1$
		}
		List<VarDeclaration> varDecls = new ArrayList<>(basefbtype.getInterfaceList().getInputVars());
		varDecls.addAll(basefbtype.getInterfaceList().getOutputVars());
		varDecls.addAll(basefbtype.getInternalVars());
		List<Variable<?>> vars = varDecls.stream().map(VariableOperations::newVariable)
				.collect(Collectors.toList());
		FBVariable fbVar = new FBVariable("THIS", basefbtype, Collections.emptyList()); //$NON-NLS-1$
		Class<? extends FBType> baseFBClass = null;
		if (basefbtype instanceof BasicFBType)
			baseFBClass = BasicFBType.class;
		else if (basefbtype instanceof SimpleFBType) 
			baseFBClass = SimpleFBType.class;
		Evaluator fbEval = EvaluatorFactory.createEvaluator(basefbtype, baseFBClass, fbVar,
				vars, null);
		Evaluator algoEval = fbEval.getChildren().entrySet().stream()
				.filter(entry -> entry.getKey().getName().equals(algorithm.getName())).findAny()
				.map(Entry::getValue).orElse(null);
		try {
			algoEval.evaluate();
			vars.forEach(v -> {
				VarDeclaration varDecl = varDecls.stream().filter(vd -> vd.getName().equals(v.getName())).findAny().orElse(null);
				String value = v.getValue().toString();
				varDecl.getValue().setValue(value);
			});
		} catch (EvaluatorException | InterruptedException e) {
			FordiacLogHelper.logError("Algorithm: " + algorithm.getName(), e);//$NON-NLS-1$
		}
	}

	private static void processOutputEvent(FBRuntimeAbstract runtime, Event output,
			BasicEList<EventOccurrence> outputEvents/*, Resource fBTypeResource*/, FBType executedFbtype) {
		final var newEventOccurrence = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
		// Copy FBTypeRuntime
		final FBRuntimeAbstract newFBTypeRT = EcoreUtil.copy(runtime);
		// Copy FBType
		FBType copyFBType = EcoreUtil.copy(executedFbtype);
		//Add copy FBType to the RuntimeFBType
		if (runtime instanceof BasicFBTypeRuntime) {
			((BasicFBTypeRuntime) newFBTypeRT).setBasicfbtype((BasicFBType) copyFBType);
		} else if (runtime instanceof SimpleFBTypeRuntime) {
			((SimpleFBTypeRuntime) newFBTypeRT).setSimpleFBType((SimpleFBType) copyFBType);
		} else {
			throw new UnsupportedOperationException();
		}
		newEventOccurrence.setFbRuntime(newFBTypeRT);
		// Event
		newEventOccurrence.setEvent(output);
		outputEvents.add(newEventOccurrence);
	}

	private ECTransition evaluateOutTransitions(BasicFBTypeRuntime basicFBTypeRuntime, Resource fBTypeResource) {
		final var outTransitions = basicFBTypeRuntime.getActiveState().getOutTransitions();
		for (final ECTransition outTransition : outTransitions) {
			if (transitionCanFire(outTransition, fBTypeResource)) {
				return outTransition;
			}
		}
		return null;
	}

	private boolean transitionCanFire(ECTransition outTransition, Resource fBTypeResource) {
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
		return (event == null) ||
				(event.getName().equals(this.eventOccurrence.getEvent().getName()) && this.eventOccurrence.isActive());
	}

	private void isConsumed() {
		this.eventOccurrence.setActive(false);
		// The event was consumed, so it was not ignored
		this.eventOccurrence.setIgnored(false);
	}

	@Override
	public EList<EventOccurrence> runSimpleFBType(SimpleFBTypeRuntime simpleFBTypeRuntime) {
		// Initialization of variables
		SimpleFBType simpleFBType = simpleFBTypeRuntime.getSimpleFBType();
		VariableUtils.fBVariableInitialization(simpleFBType);
		final var outputEvents = new BasicEList<EventOccurrence>();
		//Make a copy to execute the SimpleFBType
		SimpleFBType executedSimpleFBType = EcoreUtil.copy(simpleFBType);
		processAlgorithmWithEvaluator(executedSimpleFBType, simpleFBType.getAlgorithm().get(0));
		isConsumed();
		Event event = simpleFBType.getInterfaceList().getEventOutputs().get(0);
		processOutputEvent(simpleFBTypeRuntime, event, outputEvents, executedSimpleFBType/*(FBType) fBTypeResource.getContents().get(0)*/);
		return outputEvents;
	}

	@Override
	public EList<EventOccurrence> runFBNetwork(FBNetworkRuntime fBNetworkRuntime, EventManager manager) {
		// run FB Type to get the output events for the instance in the network
		// TODO reuse the runtimes
		BasicFBTypeRuntime runtime = OperationalSemanticsFactory.eINSTANCE.createBasicFBTypeRuntime();
		runtime.setBasicfbtype((BasicFBType) EcoreUtil.copy(eventOccurrence.getParentFB().getType()));
		runtime.setActiveState(runtime.getBasicfbtype().getECC().getStart());
		EList<EventOccurrence> outputEvents = runBasicFBType(runtime);

		EList<EventOccurrence> networkEvents = new BasicEList<>();
		outputEvents.forEach(event -> {
			EventOccurrence newEventOccurrence =  OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
			Event mappedEvent = (Event) eventOccurrence.getParentFB().getInterfaceElement(event.getEvent().getName());
			newEventOccurrence.setEvent(mappedEvent);
			networkEvents.add(newEventOccurrence);
		});
		
//		EList<IInterfaceElement> interfaceElements = eventOccurrence.getParentFB()
//															.getInterface()
//															.getAllInterfaceElements()
//															.stream().filter(iel -> outputEvents.get(0).getEvent().getName().equals(iel.getName()))//TODO several output events of one FB
//															.collect(Collectors.toCollection(BasicEList::new))
//															;
//		interfaceElements.forEach(iel -> {
//				EventOccurrence eventOccurrencce =  OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
//				eventOccurrencce.setEvent((Event) iel);
//				networkEvents.add(eventOccurrence);
//		});
				
		networkEvents.forEach(e -> {
			if (e.getEvent().isIsInput()) {
				manager.getTransactions().add(createNewTransaction(e.getEvent(), fBNetworkRuntime));
			} else {
				//TODO Find the Original Pins 
				List<IInterfaceElement> destinations = findConnectedPins(e);
				for (IInterfaceElement dest : destinations) {
					manager.getTransactions().add(createNewTransaction(dest, fBNetworkRuntime));
//					final EventOccurrence networkEo = mapFBTypeEventToFBNetworkInstance(e);
//					networkEvents.add(networkEo);
				}
			}
		});
		// TODO make sure that the correct events are returned (those from the fb network, based on what the fb type returned)
		
		return networkEvents;
	}

	private EventOccurrence mapFBTypeEventToFBNetworkInstance(EventOccurrence e) throws IllegalAccessError {
		IInterfaceElement networkEvent = eventOccurrence.getParentFB().getInterface().getAllInterfaceElements().stream()
				.filter(iel -> e.getEvent().getName().equals(iel.getName()))
				.findFirst().orElseThrow(() -> new IllegalAccessError("Cannot find the event:" + e.getEvent().getName()));

		final EventOccurrence networkEo = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
		networkEo.setEvent((Event) EcoreUtil.copy(networkEvent));
		networkEo.setParentFB(networkEvent.getFBNetworkElement());
		networkEo.setActive(true);
		return networkEo;
	}

	private static FBTransaction createNewTransaction(IInterfaceElement dest, FBNetworkRuntime fBNetworkRuntime) {
		EventOccurrence newEo = OperationalSemanticsFactory.eINSTANCE.createEventOccurrence();
		newEo.setEvent((Event) EcoreUtil.copy(dest));
		newEo.setFbRuntime(EcoreUtil.copy(fBNetworkRuntime));
		newEo.setParentFB(dest.getFBNetworkElement());
		FBTransaction transaction = OperationalSemanticsFactory.eINSTANCE.createFBTransaction();
		transaction.setInputEventOccurrence(newEo);

		newEo.getCreatedTransactions().add(transaction);
		return transaction;
	}

	private List<IInterfaceElement> findConnectedPins(EventOccurrence e) {
		List<IInterfaceElement> destinations = new ArrayList<>();
		e.getEvent().getOutputConnections().forEach(conn -> destinations.add(conn.getDestination()));
		return destinations;
	}
}

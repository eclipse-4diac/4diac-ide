package org.eclipse.fordiac.ide.fb.interpreter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EccTrace;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransitionTrace;
import org.eclipse.fordiac.ide.fb.interpreter.mm.InterfacePinUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.VariableUtils;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.fb.BasicFBEvaluator;
import org.eclipse.fordiac.ide.model.eval.value.BoolValue;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class BasicFBTypeDefaultInterpreter extends FBTypeWithEvaluatorDefaultInterpreter {

	public BasicFBTypeDefaultInterpreter(final EventOccurrence eventOccurrence,
			final Map<String, Evaluator> evaluatorCache) {
		super(eventOccurrence, evaluatorCache);
	}

	public EList<EventOccurrence> run(final BasicFBTypeRuntime basicFBTypeRuntime) {
		// Initialization of variables
		VariableUtils.fBVariableInitialization(basicFBTypeRuntime.getBasicfbtype(), null);
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
			Utils.isConsumed(this.eventOccurrence);
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

	private EList<EventOccurrence> performEntryAction(final BasicFBTypeRuntime basicFBTypeRuntime,
			final EventOccurrence eventOccurrence) {
		final var outputEvents = new BasicEList<EventOccurrence>();
		for (final ECAction action : basicFBTypeRuntime.getActiveState(basicFBTypeRuntime.getActiveState())
				.getECAction()) {
			if (action.getAlgorithm() != null) {
				processAlgorithmWithEvaluator(basicFBTypeRuntime.getBasicfbtype(), action.getAlgorithmModel(),
						eventOccurrence);
			}
			if (action.getOutput() != null) {
				outputEvents.add(Utils.createOutputEventOccurrence(basicFBTypeRuntime, action.getOutput(),
						basicFBTypeRuntime.getBasicfbtype()));
			}
		}
		return outputEvents;
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

		final FBVariable fbVar = new FBVariable("THIS", basicFBType, Collections.emptyList()); //$NON-NLS-1$
		final Class<? extends FBType> baseFBClass = BasicFBType.class;

		final List<Variable<?>> vars = varDecls.stream().map(BasicFBTypeDefaultInterpreter::mapVar)
				.collect(Collectors.toList());
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
		if (transitionHoldsFor(event, this.eventOccurrence)) {
			final var condExpression = outTransition.getConditionExpression();
			if (condExpression.isEmpty() || "1".equals(condExpression)) { //$NON-NLS-1$
				return true;
			}
			return processConditionWithEvaluator(eventOccurrence, basicFBTypeRuntime.getBasicfbtype(), outTransition);

		}
		return false;
	}

	private static boolean transitionHoldsFor(final Event transitionCondition, final EventOccurrence eo) {
		return (transitionCondition == null) || InterfacePinUtils.compareEventNames(eo.getEvent(),
				InterfacePinUtils.getFullName(transitionCondition)) && eo.isActive();
	}

}

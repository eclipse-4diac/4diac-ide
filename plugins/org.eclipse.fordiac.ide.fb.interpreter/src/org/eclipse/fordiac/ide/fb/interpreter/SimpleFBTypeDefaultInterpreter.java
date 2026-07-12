package org.eclipse.fordiac.ide.fb.interpreter;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.mm.VariableUtils;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;

public class SimpleFBTypeDefaultInterpreter extends FBTypeWithEvaluatorDefaultInterpreter {

	public SimpleFBTypeDefaultInterpreter(final EventOccurrence eventOccurrence,
			final Map<String, Evaluator> evaluatorCache) {
		super(eventOccurrence, evaluatorCache);
	}

	public EList<EventOccurrence> run(final SimpleFBTypeRuntime simpleFBTypeRuntime) {
		if (!eventOccurrence.isActive()) {
			return ECollections.emptyEList();
		}
		// Initialization of variables
		final SimpleFBType simpleFBType = simpleFBTypeRuntime.getSimpleFBType();
		VariableUtils.fBVariableInitialization(simpleFBType, null);

		final var actions = getActions(simpleFBType, eventOccurrence.getEvent().getName());
		final var outputEvents = new BasicEList<EventOccurrence>(actions.size());

		for (final SimpleECAction action : actions) {
			simpleFBType.getAlgorithm().stream().filter(a -> a.getName().equals(action.getAlgorithm())).findAny()
					.ifPresent(a -> processAlgorithmWithEvaluator(simpleFBType, a, eventOccurrence));
			outputEvents.add(Utils.createOutputEventOccurrence(simpleFBTypeRuntime, action.getOutput(), simpleFBType));
		}
		Utils.isConsumed(this.eventOccurrence);
		return outputEvents;
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

}

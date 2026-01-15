package org.eclipse.fordiac.ide.fb.interpreter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FunctionFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.mm.VariableUtils;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class FunctionFBTypeDefaultInterpreter extends FBTypeWithEvaluatorDefaultInterpreter {

	public FunctionFBTypeDefaultInterpreter(final EventOccurrence eventOccurrence, final Map<String, Evaluator> evaluatorCache) {
		super(eventOccurrence, evaluatorCache);
	}

	public EList<EventOccurrence> run(final FunctionFBTypeRuntime fBTypeRuntime) {
		if (!eventOccurrence.isActive()) {
			return ECollections.emptyEList();
		}
		final FunctionFBType functionFBType = fBTypeRuntime.getFunctionFBType();
		VariableUtils.fBVariableInitialization(functionFBType);

		// function types always have exactly 1 output event
		final Event event = functionFBType.getInterfaceList().getEventOutputs().get(0);
		processFunctionWithEvaluator(functionFBType, this.eventOccurrence);
		Utils.isConsumed(this.eventOccurrence);

		return ECollections.newBasicEList(createOutputEventOccurrence(fBTypeRuntime, event, functionFBType));
	}

	private void processFunctionWithEvaluator(final FunctionFBType functionFBType,
			final EventOccurrence eventOccurrence) {
		final List<VarDeclaration> varDecls = new ArrayList<>(functionFBType.getInterfaceList().getInputVars());
		varDecls.addAll(functionFBType.getInterfaceList().getOutputVars());
		// internal variables of function could be added by calling
		// STFunctionParseUtil2.parseFunctionBody, getting the root element which is a
		// STFunctionSource and then getting functions -> varDeclarationBlock ->
		// varDeclaration + converting it with VariableOperations.newVariable

		Evaluator eval = evaluatorCache.get(Utils.getCacheKey(eventOccurrence));
		if (eval == null) {
			final FBVariable fbVar = new FBVariable("THIS", functionFBType, Collections.emptyList()); //$NON-NLS-1$
			eval = EvaluatorFactory.createEvaluator(functionFBType, FunctionFBType.class, fbVar, List.of(), null);
			evaluatorCache.put(Utils.getCacheKey(eventOccurrence), eval);
		}
		executeEvaluator(eval, varDecls, functionFBType, eventOccurrence, functionFBType.getName());
	}
}

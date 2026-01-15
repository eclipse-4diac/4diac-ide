package org.eclipse.fordiac.ide.fb.interpreter;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventOccurrence;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FunctionFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsFactory;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.SimpleFBTypeRuntime;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.EvaluatorThreadPoolExecutor;
import org.eclipse.fordiac.ide.model.eval.value.FBValue;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class FBTypeWithEvaluatorDefaultInterpreter {

	protected final EventOccurrence eventOccurrence;
	protected final Map<String, Evaluator> evaluatorCache;

	public FBTypeWithEvaluatorDefaultInterpreter(final EventOccurrence eventOccurrence, final Map<String, Evaluator> evaluatorCache) {
		this.eventOccurrence = eventOccurrence;
		this.evaluatorCache = evaluatorCache;
	}

	protected void processAlgorithmWithEvaluator(final BaseFBType basefbtype, final Algorithm algorithm,
			final EventOccurrence eventOccurrence) {
		if (!(algorithm instanceof STAlgorithm)) {
			throw new IllegalArgumentException("StructuredTextAlgorithm object could not be found"); //$NON-NLS-1$
		}
		final List<VarDeclaration> varDecls = new ArrayList<>(basefbtype.getInterfaceList().getInputVars());
		varDecls.addAll(basefbtype.getInterfaceList().getOutputVars());
		varDecls.addAll(basefbtype.getInternalVars());
		varDecls.addAll(basefbtype.getInternalConstVars());

		Evaluator eval = evaluatorCache.get(Utils.getCacheKey(eventOccurrence));
		if (eval == null) {
			Class<? extends FBType> baseFBClass = null;
			if (basefbtype instanceof BasicFBType) {
				baseFBClass = BasicFBType.class;
			} else if (basefbtype instanceof SimpleFBType) {
				baseFBClass = SimpleFBType.class;
			}
			final FBVariable fbVar = new FBVariable("THIS", basefbtype, Collections.emptyList()); //$NON-NLS-1$
			eval = EvaluatorFactory.createEvaluator(basefbtype, baseFBClass, fbVar, List.of(), null);
			evaluatorCache.put(Utils.getCacheKey(eventOccurrence), eval);
		}
		final Optional<Evaluator> algoEval = eval.getChildren().entrySet().stream()
				.filter(entry -> entry.getKey().getName().equals(algorithm.getName())).findAny().map(Entry::getValue);
		if (algoEval.isPresent()) {
			executeEvaluator(algoEval.get(), varDecls, basefbtype, eventOccurrence, algorithm.getName());
		}
	}

	protected static void executeEvaluator(final Evaluator eval, final List<VarDeclaration> varDecls, final FBType type,
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

	protected static Variable<?> mapVar(final VarDeclaration vdec) {
		return VariableOperations.newVariable(vdec, vdec.getValue().getValue());
	}

	protected static EventOccurrence createOutputEventOccurrence(final FBRuntimeAbstract runtime, final Event output,
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
}

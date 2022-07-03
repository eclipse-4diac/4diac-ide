package org.eclipse.fordiac.ide.fb.interpreter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class EvaluatorFbType {

	private final FBType type;
	private EList<VarDeclaration> varDecls;

	public EvaluatorFbType(final FBType fbType) {
		this.type = fbType;
		collectVarDecls();
	}

	private void collectVarDecls() {
		varDecls =  new BasicEList<>(type.getInterfaceList().getInputVars());
		varDecls.addAll(type.getInterfaceList().getOutputVars());
		if (type instanceof BaseFBType) {
			varDecls.addAll(((BaseFBType) type).getInternalVars());
		}
		// TODO variables in adapters?
	}

	public void evaluate(final Algorithm algorithm) {
		if (!(algorithm instanceof STAlgorithm)) {
			throw new IllegalArgumentException("StructuredTextAlgorithm object could not be found"); //$NON-NLS-1$
		}

		// convert to st interpreter terminology
		final List<Variable<?>> vars = varDecls.stream().map(VariableOperations::newVariable).collect(Collectors.toList());

		final FBVariable fbVar = new FBVariable("THIS", type, Collections.emptyList()); //$NON-NLS-1$
		final Evaluator fbEval = EvaluatorFactory.createEvaluator(type, BasicFBType.class, fbVar,
				vars, null);
		// find algorithm to execute by name
		final Evaluator algoEval = fbEval.getChildren().entrySet().stream()
				.filter(entry -> entry.getKey().getName().equals(algorithm.getName())).findAny()
				.map(Entry::getValue).orElse(null);
		try {
			algoEval.evaluate();
			getValuesFromEvaluator(vars);
		} catch (EvaluatorException | NullPointerException e) {
			FordiacLogHelper.logError("Interpreter could not execute the algorithm " + algorithm.getName(), e); //$NON-NLS-1$
		} catch (final InterruptedException e) {
			FordiacLogHelper.logError("Algorithm: " + algorithm.getName(), e);//$NON-NLS-1$
			Thread.currentThread().interrupt();
		}
	}

	private void getValuesFromEvaluator(final List<Variable<?>> vars) {
		// write values from interpreter back to the runtime's fb type
		vars.forEach(v -> {
			final VarDeclaration varDecl = varDecls.stream().filter(vd -> vd.getName().equals(v.getName())).findAny().orElse(null);
			final String value = v.getValue().toString();
			varDecl.getValue().setValue(value);
		});
	}

	public void processAlgorithmWithEvaluatorOld(final Algorithm algorithm) {
		if (!(algorithm instanceof STAlgorithm)) {
			throw new IllegalArgumentException("StructuredTextAlgorithm object could not be found"); //$NON-NLS-1$
		}
		final List<VarDeclaration> varDecls = new ArrayList<>(this.type.getInterfaceList().getInputVars());
		varDecls.addAll(this.type.getInterfaceList().getOutputVars());
		if (type instanceof BaseFBType) {
			varDecls.addAll(((BaseFBType) this.type).getInternalVars());
		}
		final List<Variable<?>> vars = varDecls.stream().map(VariableOperations::newVariable)
				.collect(Collectors.toList());
		final BasicFBType copiedType = (BasicFBType) EcoreUtil.copy(this.type);
		// ((BasicFBTypeRuntime) copiedType.eContainer()).setBasicfbtype(null);
		// Algorithm copiedAlgo = EcoreUtil.copy(algorithm);
		final FBVariable fbVar = new FBVariable("THIS", copiedType, Collections.emptyList());
		// TODO do we need fbEval?
		final Evaluator fbEval = EvaluatorFactory.createEvaluator(copiedType, BasicFBType.class, fbVar,
				vars, null);
		final Evaluator algoEval = fbEval.getChildren().entrySet().stream()
				.filter(entry -> entry.getKey().getName().equals(algorithm.getName())).findAny()
				.map(Entry::getValue).orElse(null);
		// Evaluator algoEval = EvaluatorFactory.createEvaluator(copiedAlgo,
		// STAlgorithm.class, fbVar, vars, fbEval);
		try {
			fbEval.evaluate();
			algoEval.evaluate();
			vars.forEach(v -> {
				final VarDeclaration varDecl = varDecls.stream().filter(vd -> vd.getName().equals(v.getName())).findAny().orElse(null);
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

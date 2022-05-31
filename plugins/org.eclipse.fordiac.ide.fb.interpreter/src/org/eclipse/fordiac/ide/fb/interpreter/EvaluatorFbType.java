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

	private FBType type;
	private EList<VarDeclaration> varDecls;

	public EvaluatorFbType(FBType fbType) {
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

	public void evaluate(Algorithm algorithm) {
		if (!(algorithm instanceof STAlgorithm)) {
			throw new IllegalArgumentException("StructuredTextAlgorithm object could not be found"); //$NON-NLS-1$
		}

		// convert to st interpreter terminology
		List<Variable<?>> vars = varDecls.stream().map(VariableOperations::newVariable).collect(Collectors.toList());
		
		//Copy
		BasicFBType copiedType = (BasicFBType) EcoreUtil.copy(this.type);
		
		FBVariable fbVar = new FBVariable("THIS", type, Collections.emptyList()); //$NON-NLS-1$
		Evaluator fbEval = EvaluatorFactory.createEvaluator(type, BasicFBType.class, fbVar,
				vars, null);
		// find algorithm to execute by name
		Evaluator algoEval = fbEval.getChildren().entrySet().stream()
				.filter(entry -> entry.getKey().getName().equals(algorithm.getName())).findAny()
				.map(Entry::getValue).orElse(null);
		try {
			algoEval.evaluate();
			getValuesFromEvaluator(vars);
		} catch (EvaluatorException | InterruptedException | NullPointerException e) {
			FordiacLogHelper.logError("Interpreter could not execute the algorithm " + algorithm.getName()); //$NON-NLS-1$
		}
	}

	private void getValuesFromEvaluator(List<Variable<?>> vars) {
		// write values from interpreter back to the runtime's fb type
		vars.forEach(v -> {
			VarDeclaration varDecl = varDecls.stream().filter(vd -> vd.getName().equals(v.getName())).findAny().orElse(null);
			String value = v.getValue().toString();
			varDecl.getValue().setValue(value);
		});
	}
	
	public void processAlgorithmWithEvaluatorOld(Algorithm algorithm) {
		if (!(algorithm instanceof STAlgorithm)) {
			throw new IllegalArgumentException("StructuredTextAlgorithm object could not be found"); //$NON-NLS-1$
		}
		List<VarDeclaration> varDecls = new ArrayList<>(this.type.getInterfaceList().getInputVars());
		varDecls.addAll(this.type.getInterfaceList().getOutputVars());		
		if (type instanceof BaseFBType) {
			varDecls.addAll(((BaseFBType) this.type).getInternalVars());
		}
		List<Variable<?>> vars = varDecls.stream().map(iel -> VariableOperations.newVariable(iel))
				.collect(Collectors.toList());
		BasicFBType copiedType = (BasicFBType) EcoreUtil.copy(this.type);
		// ((BasicFBTypeRuntime) copiedType.eContainer()).setBasicfbtype(null);
		// Algorithm copiedAlgo = EcoreUtil.copy(algorithm);
		FBVariable fbVar = new FBVariable("THIS", copiedType, Collections.emptyList());
		System.out.println("doings");
		// TODO do we need fbEval?
		Evaluator fbEval = EvaluatorFactory.createEvaluator(copiedType, BasicFBType.class, fbVar,
				vars, null);
		Evaluator algoEval = fbEval.getChildren().entrySet().stream()
				.filter(entry -> entry.getKey().getName().equals(algorithm.getName())).findAny()
				.map(Entry::getValue).orElse(null);
		// Evaluator algoEval = EvaluatorFactory.createEvaluator(copiedAlgo,
		// STAlgorithm.class, fbVar, vars, fbEval);
		try {
			fbEval.evaluate();
			algoEval.evaluate();
			vars.forEach(v -> {
				VarDeclaration varDecl = varDecls.stream().filter(vd -> vd.getName().equals(v.getName())).findAny().orElse(null);
				String value = v.getValue().toString();
				varDecl.getValue().setValue(value);
			});
		} catch (EvaluatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}

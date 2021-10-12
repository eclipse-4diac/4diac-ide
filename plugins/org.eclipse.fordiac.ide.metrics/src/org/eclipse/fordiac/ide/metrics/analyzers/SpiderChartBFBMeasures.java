/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Lisa Sonnleithner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.metrics.analyzers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.export.forte_ng.st.STAlgorithmFilter;
import org.eclipse.fordiac.ide.metrics.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.CaseStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ForStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.IfStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.RepeatStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Statement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StatementList;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.WhileStatement;

public class SpiderChartBFBMeasures extends AbstractCodeMetricAnalyzer {

	@Override
	public List<MetricResult> getResults() {
		final List<MetricResult> results = new ArrayList<>();

		final SpiderChartBFBData scData = (SpiderChartBFBData) this.data;

		results.add(new MetricResult(Messages.NumberOfStates, scData.states));
		results.add(new MetricResult(Messages.NumberOfTransitions, scData.transitions));
		results.add(new MetricResult(Messages.AlgorithmLOC, scData.loc));
		results.add(new MetricResult(Messages.NumberOfActions, scData.actions));
		results.add(new MetricResult(Messages.NumberOfInternalVars, scData.internalVar));
		results.add(new MetricResult(Messages.NumberOfIndependentPaths, scData.independentPaths));
		results.add(new MetricResult(Messages.NumberOfInterfaceElements, scData.interfaceEl));

		return results;
	}

	@Override
	protected MetricData analyzeBFB(final BasicFBType basicFBType) {
		final SpiderChartBFBData data = new SpiderChartBFBData();
		final ECC ecc = basicFBType.getECC();
		data.states = ecc.getECState().size();

		data.independentPaths = (ecc.getECTransition().size() - ecc.getECState().size() + 2);
		data.transitions = ecc.getECTransition().size();

		for (final Algorithm alg : basicFBType.getAlgorithm()) {
			data.loc += calculateLOC(alg);
		}

		data.internalVar = basicFBType.getInternalVars().size();
		data.interfaceEl += countInterfaceElements(basicFBType.getInterfaceList());

		for (final ECState state : ecc.getECState()) {
			for (final ECAction action : state.getECAction()) {
				data.actions += state.getECAction().size();
				if (null != action.getAlgorithm()) {
					data.independentPaths += analyzeAlgorithm(action.getAlgorithm());
				}
			}
		}
		return data;
	}

	protected int countInterfaceElements(final InterfaceList interfaceList) {
		int count = 0;
		final int[] adpCount = { 0 };
		count += interfaceList.getInputVars().size();
		count += interfaceList.getOutputVars().size();
		count += interfaceList.getEventInputs().size();
		count += interfaceList.getEventOutputs().size();
		interfaceList.getPlugs().forEach(plug -> adpCount[0] += countInterfaceElements(plug.getType().getInterfaceList()));
		interfaceList.getSockets()
		.forEach(socket -> adpCount[0] += countInterfaceElements(socket.getType().getInterfaceList()));
		count += adpCount[0];
		return count;
	}

	// LOC of algorithm, blank lines and comments are not counted
	private static double calculateLOC(final Algorithm alg) {

		if (!(alg instanceof STAlgorithm)) {
			return Double.NaN;
		}

		final STAlgorithmFilter filter = new STAlgorithmFilter();
		final List<String> errors = new ArrayList<>();
		final var ast = filter.parse((STAlgorithm) alg, errors);
		return errors.isEmpty() ? //
				countStatements(ast.getStatements()) + //
				countLocalVariables(ast.getLocalVariables()) //
				: Double.NaN;

	}

	private static int countLocalVariables(final EList<VarDeclaration> vars) {
		if (vars.isEmpty()) {
			return 0;
		}
		return vars.size() + 2;
	}

	private static int countStatements(final StatementList list) {
		return list.getStatements().stream().mapToInt(SpiderChartBFBMeasures::dispatchCountStatements).sum();
	}

	private static int dispatchCountStatements(final Statement stmt) {
		if (stmt instanceof IfStatement) {
			final IfStatement ifStmt = (IfStatement) stmt;

			int elseCount = 0;
			if (ifStmt.getElse() != null) {
				elseCount = 1 + countStatements(ifStmt.getElse().getStatements());
			}

			return 2 + countStatements(ifStmt.getStatments()) + //
					elseCount + //
					ifStmt.getElseif().size() + //
					ifStmt.getElseif().stream().mapToInt(v -> countStatements(v.getStatements())).sum();
		}
		if (stmt instanceof ForStatement) {
			final ForStatement forStmt = (ForStatement) stmt;
			return 2 + countStatements(forStmt.getStatements());
		}
		if (stmt instanceof WhileStatement) {
			final WhileStatement whileStmt = (WhileStatement) stmt;
			return 2 + countStatements(whileStmt.getStatements());
		}
		if (stmt instanceof RepeatStatement) {
			final RepeatStatement repeatStmt = (RepeatStatement) stmt;
			return 2 + countStatements(repeatStmt.getStatements());
		}
		if (stmt instanceof CaseStatement) {
			final CaseStatement caseStmt = (CaseStatement) stmt;
			int elseCount = 0;
			if (caseStmt.getElse() != null) {
				elseCount = 1 + countStatements(caseStmt.getElse().getStatements());
			}
			return 2 + elseCount + //
					caseStmt.getCase().stream().mapToInt(v -> countStatements(v.getStatements())).sum();
		}
		return 1;
	}

	private static double analyzeAlgorithm(final Algorithm algorithm) {
		return CyclomaticComplexity.analyzeAlgorithm(algorithm);
	}

	@Override
	protected MetricData createDataType() {
		return new SpiderChartBFBData();
	}

	@Override
	protected MetricData analyzeSFB(final SimpleFBType simpleFBType) {
		final SpiderChartBFBData data = new SpiderChartBFBData();
		data.states = 2;

		data.independentPaths = 2;
		data.transitions = 2;

		data.loc = calculateLOC(simpleFBType.getAlgorithm());

		data.internalVar = simpleFBType.getInternalVars().size();
		data.interfaceEl += countInterfaceElements(simpleFBType.getInterfaceList());

		data.actions = 1;
		data.independentPaths += analyzeAlgorithm(simpleFBType.getAlgorithm());

		return data;
	}

}

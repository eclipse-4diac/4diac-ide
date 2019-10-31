/*******************************************************************************
 * Copyright (c) 2019 TU Wien, ACIN, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Peter Gsellmann - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Changed analysis result to key value pairs
 *******************************************************************************/
package org.eclipse.fordiac.ide.metrics.analyzers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;

public class HalsteadMetric extends AbstractCodeMetricAnalyzer {
	private static final int ST_OPERATOR_COUNT = 16;
	private static final String[] ST_OPERATORS = { "**", ":=", "<=", ">=", "<>", "-", "NOT", "*", "/", "MOD", "+", "<", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
			">", //$NON-NLS-1$
			"=", "AND", "OR", "XOR" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	private static final int[] ST_OPERANDS_WEIGHT = { 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 };

	private double operandST = 0.0;
	private double operatorST = 0.0;
	private double uniqueOperatorST = 0.0;
	private double operators = 0;
	private double operands = 0;
	private double uniqueOperands = 0;
	private double uniqueOperator = 0;
	private int actionCount = 0;
	private int[] opCount = new int[ST_OPERATOR_COUNT];
	private int uniqueTrans = 0;
	private List<String> alg = new ArrayList<>();
	private List<String> transCond = new ArrayList<>();
	private List<String> actions = new ArrayList<>();
	private List<String> event = new ArrayList<>();

	@Override
	public void calculateMetrics(Application app) {

		super.calculateMetrics(app);

		uniqueOperands += uniqueTrans;
		uniqueOperator += uniqueTrans;
		operators += actions.size() + transCond.size();
		operands += (event.size() + alg.size() + transCond.size());

		for (int i = 0; i < opCount.length; i++) {
			if (opCount[i] >= 1.0) {
				uniqueOperatorST++;
			}
		}

	}

	@Override
	protected void analyzeBFB(BasicFBType basicFBType) {
		ECC ecc = basicFBType.getECC();
		for (ECTransition trans : ecc.getECTransition()) {
			if (!transCond.contains(trans.getConditionExpression())) {
				uniqueTrans += 1;
			}
			transCond.add(trans.getConditionExpression());
		}

		for (ECState state : ecc.getECState()) {
			for (ECAction action : state.getECAction()) {
				analyzeAction(action);
			}
		}
	}

	private void analyzeAction(ECAction action) {
		if (!actions.contains("Action " + actionCount)) {
			uniqueOperator += 1;
		}
		actions.add("Action " + actionCount);

		if (null != action.getOutput()) {
			if (!event.contains(action.getOutput().getName())) {
				uniqueOperands += 1;
			}
			event.add(action.getOutput().getName());
		}

		if (null != action.getAlgorithm()) {
			analyzeAlgorithm(action.getAlgorithm());
		}
		actionCount++;
	}

	private void analyzeAlgorithm(Algorithm algorithm) {
		if (!alg.contains(algorithm.getName())) {
			uniqueOperands += 1;
		}
		alg.add(algorithm.getName());
		String algo = algorithm.toString();
		int count = 0;
		for (String op : ST_OPERATORS) {
			int lastIndex = 0;
			while (-1 != lastIndex) {
				lastIndex = algo.indexOf(op, lastIndex);
				if (-1 != lastIndex) {
					operatorST++;
					opCount[count] += 1;
					operandST += ST_OPERANDS_WEIGHT[count];
					String sub1 = algo.substring(0, lastIndex);
					String sub2 = algo.substring(lastIndex + op.length(), algo.length());
					algo = sub1.concat(sub2);
				}
			}
			count++;
		}
	}

	@Override
	public List<MetricData> getResults() {
		double n2Major = operands + operandST;
		double n2 = uniqueOperands + operandST;
		double n1Major = operators + operatorST;
		double n1 = uniqueOperator + uniqueOperatorST;

		List<MetricData> results = new ArrayList<>();

		results.add(new MetricData("Distinct operators ", n1));
		results.add(new MetricData("Distinct operands ", n2));
		results.add(new MetricData("Total number of operators ", n1Major));
		results.add(new MetricData("Total number of operands ", n2Major));
		results.add(new MetricData("Distinct operators ST ", uniqueOperatorST));
		results.add(new MetricData("Distinct operands ST ", operandST));
		results.add(new MetricData("Total number of operators ST ", operatorST));
		results.add(new MetricData("Total number of operands ST ", operandST));

		double nMAjor = n1Major + n2Major;
		double n = n1 + n2;
		double nHat = (n1 * Math.log(n1) / Math.log(2) + n2 * Math.log(n2) / Math.log(2));
		double pr = nHat / nMAjor;
		double v = nMAjor * Math.log(n) / Math.log(2);
		double d = n1 / 2 * n2Major / n2;
		double e = d * v;

		results.add(new MetricData("Program Length ", nMAjor));
		results.add(new MetricData("Program vocabulary ", n));
		results.add(new MetricData("Estimated length ", nHat));
		results.add(new MetricData("Purity ratio ", pr));
		results.add(new MetricData("Program volume ", v));
		results.add(new MetricData("Difficulty ", d));
		results.add(new MetricData("Program Effort ", e));

		return results;
	}

}

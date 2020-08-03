/*******************************************************************************
 * Copyright (c) 2019 TU Wien, ACIN
 * 				 2019-2020 Johannes Kepler University Linz
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
 *   Lisa Sonnleithner - Adjustments to change calculation method to average 
 *******************************************************************************/
package org.eclipse.fordiac.ide.metrics.analyzers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class HalsteadMetric extends AbstractCodeMetricAnalyzer {
	
	@Override
	public void calculateMetrics(INamedElement element) {

		super.calculateMetrics(element);

	}

	@Override
	protected HalsteadData analyzeBFB(BasicFBType basicFBType) {
		HalsteadData data = new HalsteadData();
		ECC ecc = basicFBType.getECC();
		for (ECTransition trans : ecc.getECTransition()) {
			if (!data.transCond.contains(trans.getConditionExpression())) {
				data.uniqueTrans += 1;
			}
			data.transCond.add(trans.getConditionExpression());
		}

		for (ECState state : ecc.getECState()) {
			for (ECAction action : state.getECAction()) {
				analyzeAction(action, data);
			}
		}
		
		data.uniqueOperands += data.uniqueTrans;
		data.uniqueOperator += data.uniqueTrans;
		data.operators +=data. actions.size() + data.transCond.size();
		data.operands += (data.event.size() + data.alg.size() + data.transCond.size());

		for (int i = 0; i < data.opCount.length; i++) {
			if (data.opCount[i] >= 1.0) {
				data.uniqueOperatorST++;
			}
		}
		data.n2Major = data.operands + data.operandST;
		data.n2 = data.uniqueOperands + data.operandST;
		data.n1Major = data.operators + data.operatorST;
		data.n1 = data.uniqueOperator + data.uniqueOperatorST;
		return data;
	}

	private void analyzeAction(ECAction action, HalsteadData data) {
		if (!data.actions.contains("Action " + data.actionCount)) {
			data.uniqueOperator += 1;
		}
		data.actions.add("Action " + data.actionCount);

		if (null != action.getOutput()) {
			if (!data.event.contains(action.getOutput().getName())) {
				data.uniqueOperands += 1;
			}
			data.event.add(action.getOutput().getName());
		}

		if (null != action.getAlgorithm()) {
			analyzeAlgorithm(action.getAlgorithm(), data);
		}
		data.actionCount++;
	}

	private void analyzeAlgorithm(Algorithm algorithm, HalsteadData data) {
		if (!data.alg.contains(algorithm.getName())) {
			data.uniqueOperands += 1;
		}
		data.alg.add(algorithm.getName());
		String algo = algorithm.toString();
		int count = 0;
		for (String op : HalsteadData.ST_OPERATORS) {
			int lastIndex = 0;
			while (-1 != lastIndex) {
				lastIndex = algo.indexOf(op, lastIndex);
				if (-1 != lastIndex) {
					data.operatorST++;
					data.opCount[count] += 1;
					data.operandST += HalsteadData.ST_OPERANDS_WEIGHT[count];
					String sub1 = algo.substring(0, lastIndex);
					String sub2 = algo.substring(lastIndex + op.length(), algo.length());
					algo = sub1.concat(sub2);
				}
			}
			count++;
		}
	}
	

	

	@Override
	public List<MetricResult> getResults() {

		List<MetricResult> results = new ArrayList<>();
		
		HalsteadData hData= (HalsteadData) this.data;
		
		results.add(new MetricResult("Distinct operators n1 ",hData.n1));
		results.add(new MetricResult("Distinct operands n2", hData.n2));
		results.add(new MetricResult("Total number of operators N1", hData.n1Major));
		results.add(new MetricResult("Total number of operands N2", hData.n2Major));
		
		double nMAjor = hData.n1Major + hData.n2Major;
		double n = hData.n1 + hData.n2;
		double nHat = (hData.n1 * Math.log(hData.n1) / Math.log(2) + hData.n2 * Math.log(hData.n2) / Math.log(2));
		double pr = nHat / nMAjor;
		double v = nMAjor * Math.log(n) / Math.log(2);
		double d = hData.n1 / 2 * hData.n2Major / hData.n2;
		double e = d * v;

		results.add(new MetricResult("Program Length N", nMAjor));
		results.add(new MetricResult("Program vocabulary n", n));
		results.add(new MetricResult("Estimated length N^", nHat));
		results.add(new MetricResult("Purity ratio PR", pr));
		results.add(new MetricResult("Program volume V", v));
		results.add(new MetricResult("Difficulty D", d));
		results.add(new MetricResult("Program Effort E", e));

		return results;
	}

	@Override
	protected MetricData createDataType() {
	
		return new HalsteadData();
	}

}

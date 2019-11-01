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
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class CyclomaticComplexity extends AbstractCodeMetricAnalyzer {
	static final String[] CONDITIONS = { "IF", "FOR", "WHILE", "REPEAT" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	List<MetricData> metrics = new ArrayList<>();
	double ccapp = 0.0;

	@Override
	public void calculateMetrics(INamedElement element) {
		super.calculateMetrics(element);
		metrics.add(0, new MetricData("Cyclomatic Number " + element.getName(), ccapp));
	}

	@Override
	public List<MetricData> getResults() {
		return metrics;
	}

	@Override
	protected void analyzeBFB(BasicFBType basicFBType) {
		ECC ecc = basicFBType.getECC();

		double ccfb = (ecc.getECTransition().size() - ecc.getECState().size() + 2);

		for (ECState state : ecc.getECState()) {
			for (ECAction action : state.getECAction()) {
				if (null != action.getAlgorithm()) {
					ccfb += analyzeAlgorithm(action.getAlgorithm());
				}
			}
		}

		ccapp += ccfb;
		metrics.add(new MetricData("Cyclomatic Number " + basicFBType.getName(), ccfb));
	}

	private static double analyzeAlgorithm(Algorithm algorithm) {
		double ccAlg = 0.0;
		String algText = algorithm.toString();
		int saveIndex = 0;
		for (String cond : CONDITIONS) {
			int lastIndex = 0;
			while (-1 != lastIndex) {
				if (cond.equals("REPEAT")) {
					saveIndex = algText.indexOf(cond + "\r\n", lastIndex);
				} else {
					saveIndex = algText.toString().indexOf(cond + " ", lastIndex);
				}
				if (0 != saveIndex) {
					lastIndex = saveIndex;
					if (-1 != lastIndex) {
						ccAlg++;
						lastIndex += cond.length();
					}
				}
			}
		}
		return ccAlg;
	}

}

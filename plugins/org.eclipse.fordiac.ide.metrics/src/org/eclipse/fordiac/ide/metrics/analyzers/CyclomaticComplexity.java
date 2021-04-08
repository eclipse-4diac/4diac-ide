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
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class CyclomaticComplexity extends AbstractCodeMetricAnalyzer {
	static final String[] CONDITIONS = { "IF", "FOR", "WHILE", "REPEAT" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	List<MetricResult> metrics = new ArrayList<>();
	double ccapp = 0.0;

	@Override
	public void calculateMetrics(final INamedElement element) {
		super.calculateMetrics(element);
		final CyclomaticData cData = (CyclomaticData) this.data;
		metrics.add(0, new MetricResult("Cyclomatic Number " + element.getName(), cData.cc));
		metrics = removeDuplicates(metrics);
	}

	private List<MetricResult> removeDuplicates(final List<MetricResult> list) {
		final List<MetricResult> newList = new ArrayList<>();
		boolean dupl = false;
		for (final MetricResult m : list) {
			for (final MetricResult n : newList) {
				if (m.equals(n)) {
					dupl = true;
				}
			}
			if (!dupl) {
				newList.add(m);
			}
			dupl = false;
		}
		return newList;
	}

	@Override
	public List<MetricResult> getResults() {
		return metrics;
	}

	@Override
	protected MetricData analyzeBFB(final BasicFBType basicFBType) {
		final CyclomaticData data = new CyclomaticData();
		final ECC ecc = basicFBType.getECC();

		double ccfb = (ecc.getECTransition().size() - ecc.getECState().size() + 2);

		for (final ECState state : ecc.getECState()) {
			for (final ECAction action : state.getECAction()) {
				if (null != action.getAlgorithm()) {
					ccfb += analyzeAlgorithm(action.getAlgorithm());
				}
			}
		}

		data.cc += ccfb;
		metrics.add(new MetricResult("Cyclomatic Number " + basicFBType.getName(), ccfb));
		return data;
	}

	private static double analyzeAlgorithm(final Algorithm algorithm) {
		double ccAlg = 0.0;
		final String algText = algorithm.toString();
		int saveIndex = 0;
		for (final String cond : CONDITIONS) {
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

	@Override
	protected MetricData analyzeCFB(final CompositeFBType compositeFBType) {
		final MetricData data = analyzeFBNetwork(compositeFBType.getFBNetwork(), true);
		metrics.add(new MetricResult("Cyclomatic Number " + compositeFBType.getName(), ((CyclomaticData) data).cc));
		return data;

	}

	@Override
	protected MetricData createDataType() {
		return new CyclomaticData();
	}

}

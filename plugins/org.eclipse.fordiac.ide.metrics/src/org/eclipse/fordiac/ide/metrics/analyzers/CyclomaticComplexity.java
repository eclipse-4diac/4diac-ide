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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.metrics.Messages;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class CyclomaticComplexity extends AbstractCodeMetricAnalyzer {
	static final String[] CONDITIONS = { FordiacKeywords.IF, FordiacKeywords.FOR, FordiacKeywords.WHILE,
			FordiacKeywords.REPEAT };

	List<MetricResult> metrics = new ArrayList<>();
	double ccapp = 0.0;

	private static String formatResultName(final String name) {
		return MessageFormat.format(Messages.CyclomaticNumber, name);
	}

	@Override
	public void calculateMetrics(final INamedElement element) {
		super.calculateMetrics(element);
		final ComplexityData cData = (ComplexityData) this.data;
		metrics.add(0, new MetricResult(formatResultName(element.getName()), cData.cc));
		metrics = removeDuplicateResults(metrics);
	}

	@Override
	public List<MetricResult> getResults() {
		return metrics;
	}

	@Override
	protected MetricData analyzeBFB(final BasicFBType basicFBType) {
		final ComplexityData data = new ComplexityData();
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
		metrics.add(new MetricResult(formatResultName(basicFBType.getName()), ccfb));
		return data;
	}

	private static double analyzeAlgorithm(final Algorithm algorithm) {
		double ccAlg = 0.0;
		final String algText = algorithm.toString();
		int saveIndex = 0;
		for (final String cond : CONDITIONS) {
			int lastIndex = 0;
			while (-1 != lastIndex) {
				if (cond.equals(FordiacKeywords.REPEAT)) {
					saveIndex = algText.indexOf(cond + "\\r?\\n", lastIndex); //$NON-NLS-1$
				} else {
					saveIndex = algText.indexOf(cond + " ", lastIndex); //$NON-NLS-1$
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
		metrics.add(new MetricResult(formatResultName(compositeFBType.getName()), ((ComplexityData) data).cc));
		return data;

	}

	@Override
	protected MetricData createDataType() {
		return new ComplexityData();
	}

}

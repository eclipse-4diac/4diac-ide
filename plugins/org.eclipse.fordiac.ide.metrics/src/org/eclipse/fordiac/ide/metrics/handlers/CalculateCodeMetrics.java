/*******************************************************************************
 * Copyright (c) 2019 TU Wien, ACIN
 * 				 2019-2021 Johannes Kepler University Linz
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
 *   				   - extracted superclass
 *   Ernst Blecha - add cognitive complexity
 *******************************************************************************/
package org.eclipse.fordiac.ide.metrics.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.metrics.analyzers.AbstractCodeMetricAnalyzer;
import org.eclipse.fordiac.ide.metrics.analyzers.CognitiveComplexity;
import org.eclipse.fordiac.ide.metrics.analyzers.CyclomaticComplexity;
import org.eclipse.fordiac.ide.metrics.analyzers.HalsteadMetric;

public class CalculateCodeMetrics extends AbstractCodeMetricHandler {

	@Override
	protected List<AbstractCodeMetricAnalyzer> getAnalyzers() {
		final List<AbstractCodeMetricAnalyzer> analyzers = new ArrayList<>();
		analyzers.add(new CyclomaticComplexity());
		analyzers.add(new CognitiveComplexity());
		analyzers.add(new HalsteadMetric());
		return analyzers;
	}



}

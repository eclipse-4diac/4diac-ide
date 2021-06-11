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
 *   Lisa Sonnleithner - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.metrics.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.metrics.analyzers.AbstractCodeMetricAnalyzer;
import org.eclipse.fordiac.ide.metrics.analyzers.SpiderChartBFBMeasures;

public class CalculateSpiderChartBFBMeasures extends AbstractCodeMetricHandler {

	@Override
	protected List<AbstractCodeMetricAnalyzer> getAnalyzers() {
		final List<AbstractCodeMetricAnalyzer> analyzers = new ArrayList<>();
		analyzers.add(new SpiderChartBFBMeasures());
		return analyzers;
	}

}

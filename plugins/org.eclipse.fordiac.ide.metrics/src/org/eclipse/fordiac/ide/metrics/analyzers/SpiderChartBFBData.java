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

public class SpiderChartBFBData extends MetricData {
	int states;
	int transitions;
	double loc;
	int actions;
	int internalVar;
	int independentPaths; // cyclomatic complexity
	int interfaceEl;


	// these two methods are not necessary yet, because the SCBFB Measures can only be applied to BFBs
	@Override
	protected void add(final MetricData data) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void divide(final int div) {
		// TODO Auto-generated method stub
	}
}

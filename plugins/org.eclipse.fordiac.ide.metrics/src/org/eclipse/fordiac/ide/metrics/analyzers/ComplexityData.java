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
 *   Lisa Sonnleithner - Adjustments to change calculation method to average 
 *******************************************************************************/

package org.eclipse.fordiac.ide.metrics.analyzers;

class ComplexityData extends MetricData{
	double cc = 0;

	@Override
	protected void add(MetricData data) {
		if (data instanceof ComplexityData) {
			cc += ((ComplexityData) data).cc;
		}

	}

	@Override
	protected void divide(int div) {
		cc /= div;	
	}

}

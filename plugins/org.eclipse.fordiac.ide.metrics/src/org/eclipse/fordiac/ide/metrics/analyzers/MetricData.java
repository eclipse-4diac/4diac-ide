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

abstract class MetricData {
	int cnt = 0;

	protected abstract void add(MetricData data);

	protected abstract void divide(int div);
}

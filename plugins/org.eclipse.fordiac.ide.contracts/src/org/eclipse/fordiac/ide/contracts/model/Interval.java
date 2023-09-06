/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.model;

public class Interval extends AbstractTime {
	private final int minTime;
	private final int maxTime;

	public Interval(final int minTime, final int maxTime) {
		this.minTime = minTime;
		this.maxTime = maxTime;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public int getMinTime() {
		return minTime;
	}

}

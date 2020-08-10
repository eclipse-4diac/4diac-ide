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

import java.util.ArrayList;
import java.util.List;

 class HalsteadData extends MetricData {
	 static final int ST_OPERATOR_COUNT = 16;
	 static final String[] ST_OPERATORS = { "**", ":=", "<=", ">=", "<>", "-", "NOT", "*", "/", "MOD", "+", "<", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
			">", //$NON-NLS-1$
			"=", "AND", "OR", "XOR" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	 static final int[] ST_OPERANDS_WEIGHT = { 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 };

	 double operandST = 0.0;
	 double operatorST = 0.0;
	 double uniqueOperatorST = 0.0;
	 double operators = 0;
	 double operands = 0;
	 double uniqueOperands = 0;
	 double uniqueOperator = 0;
	 double n2Major = 0;
	 double n2 = 0;
	 double n1Major = 0;
	 double n1 = 0;

	 int actionCount = 0;
	 int[] opCount = new int[ST_OPERATOR_COUNT];
	 int uniqueTrans = 0;
	 List<String> alg = new ArrayList<>();
	 List<String> transCond = new ArrayList<>();
	 List<String> actions = new ArrayList<>();
	 List<String> event = new ArrayList<>();
	

	@Override
	protected void add(MetricData data) {
		if(data instanceof HalsteadData) {
			n2Major += ((HalsteadData)data).n2Major;
			n2 += ((HalsteadData)data).n2;
			n1Major += ((HalsteadData)data).n1Major;
			n1 += ((HalsteadData)data).n1;
		}
	}
	
	@Override
	protected void divide(int div) {
		n2Major /= div;
		n2 /= div;
		n1Major /= div;
		n1 /= div;
	}


}

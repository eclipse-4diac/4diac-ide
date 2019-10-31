/*******************************************************************************
 * Copyright (c) 2019 TU Wien, ACIN
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Peter Gsellmann - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.metrics.analyzers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;

public class CyclomaticComplexity implements ICodeMetricAnalyzer {
	static final String[] CONDITIONS = { "IF", "FOR", "WHILE", "REPEAT" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	List<String> metric = new ArrayList<>();

	@Override
	public void calculateMetrics(Application app) {
		double ccapp = 0.0;

		for (FBNetworkElement fb : app.getFBNetwork().getNetworkElements()) {
			if (fb.getType() instanceof BasicFBType) {
				double ccfb = analyseBFB(((BasicFBType) fb.getType()));
				ccapp += ccfb;
				metric.add("Cyclomatic Number " + app.getName() + " : " + fb.getTypeName() + " " + ccfb);
			}
		}
		metric.add(0, "Cyclomatic Number " + app.getName() + " " + ccapp);

	}

	@Override
	public void printMetrics(StringBuilder result) {
		metric.forEach(el -> {
			result.append(el);
			result.append("\n");
		});
	}

	private static double analyseBFB(BasicFBType fbType) {
		ECC ecc = fbType.getECC();

		int nrstates = ecc.getECState().size();
		int nrtransitions = ecc.getECTransition().size();

		double ccfb = (nrtransitions - nrstates + 2);

		int saveIndex = 0;
		for (ECState state : ecc.getECState()) {
			for (ECAction action : state.getECAction()) {
				if (null != action.getAlgorithm()) {
					for (String cond : CONDITIONS) {
						int lastIndex = 0;
						while (-1 != lastIndex) {
							if (cond.equals("REPEAT")) {
								saveIndex = action.getAlgorithm().toString().indexOf(cond + "\r\n", lastIndex);
							} else {
								saveIndex = action.getAlgorithm().toString().indexOf(cond + " ", lastIndex);
							}
							if (0 != saveIndex) {
								lastIndex = saveIndex;
								if (-1 != lastIndex) {
									ccfb++;
									lastIndex += cond.length();
								}
							}
						}
					}
				}
			}
		}

		return ccfb;
	}

}

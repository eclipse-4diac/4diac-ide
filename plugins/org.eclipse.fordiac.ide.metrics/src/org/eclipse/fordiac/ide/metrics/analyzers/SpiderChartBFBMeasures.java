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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.metrics.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;

public class SpiderChartBFBMeasures extends AbstractCodeMetricAnalyzer {
	static final String[] CONDITIONS = { "IF", "FOR", "WHILE", "REPEAT" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	@Override
	public List<MetricResult> getResults() {
		final List<MetricResult> results = new ArrayList<>();

		final SpiderChartBFBData scData = (SpiderChartBFBData) this.data;

		results.add(new MetricResult(Messages.NumberOfStates, scData.states));
		results.add(new MetricResult(Messages.NumberOfTransitions, scData.transitions));
		results.add(new MetricResult(Messages.AlgorithmLOC, scData.loc));
		results.add(new MetricResult(Messages.NumberOfActions, scData.actions));
		results.add(new MetricResult(Messages.NumberOfInternalVars, scData.internalVar));
		results.add(new MetricResult(Messages.NumberOfIndependentPaths, scData.independentPaths));
		results.add(new MetricResult(Messages.NumberOfInterfaceElements, scData.interfaceEl));

		return results;
	}

	@Override
	protected MetricData analyzeBFB(final BasicFBType basicFBType) {
		final SpiderChartBFBData data = new SpiderChartBFBData();
		final ECC ecc = basicFBType.getECC();
		data.states = ecc.getECState().size();

		data.independentPaths = (ecc.getECTransition().size() - ecc.getECState().size() + 2);

		for (final Algorithm alg : basicFBType.getAlgorithm()) {
			data.loc += calculateLOC(alg.toString());
		}

		data.internalVar = basicFBType.getInternalVars().size();
		data.interfaceEl += countInterfaceElements(basicFBType.getInterfaceList());

		for (final ECState state : ecc.getECState()) {
			for (final ECAction action : state.getECAction()) {
				data.actions += state.getECAction().size();
				if (null != action.getAlgorithm()) {
					data.independentPaths += analyzeAlgorithm(action.getAlgorithm());
				}
			}
		}
		return data;
	}

	protected int countInterfaceElements(final InterfaceList interfaceList) {
		int count = 0;
		final int[] adpCount = { 0 };
		count += interfaceList.getInputVars().size();
		count += interfaceList.getOutputVars().size();
		count += interfaceList.getEventInputs().size();
		count += interfaceList.getEventOutputs().size();
		interfaceList.getPlugs().forEach(plug -> adpCount[0] += countInterfaceElements(plug.getType().getInterfaceList()));
		interfaceList.getSockets()
				.forEach(socket -> adpCount[0] += countInterfaceElements(socket.getType().getInterfaceList()));
		count += adpCount[0];
		return count;
	}

	// LOC of algorithm, blank lines and comments are not counted
	protected int calculateLOC(final String algString) {
		int loc = 0;
		//cut overhead
		final int indStart = algString.indexOf("text:"); //$NON-NLS-1$
		final int indEnd = algString.lastIndexOf(')');
		final String trimmedAlgString = algString.substring(indStart, indEnd);

		final String[] lines = trimmedAlgString.split("\\r?\\n"); //$NON-NLS-1$

		boolean isComment = false;
		int indCommentStart1;
		int indCommentStart2;
		int indCommentEnd1;
		int indCommentEnd2;
		for (final String s : lines) {
			if (!s.isEmpty()) {
				indCommentStart1 = s.trim().indexOf("(*"); //$NON-NLS-1$
				indCommentStart2 = s.trim().indexOf("/*"); //$NON-NLS-1$
				if ((indCommentStart1 > 0) || (indCommentStart2 > 0)) { // blockcomment starts somewhere in this line
					isComment = true;
					loc++;
				} else if ((indCommentStart1 == 0) || (indCommentStart2 == 0)) { // blockcomment starts at the beginning
					isComment = true;
				} else if (!isComment && !s.trim().startsWith("//")) { //$NON-NLS-1$
					loc++;
				}

				indCommentEnd1 = s.trim().indexOf("*)"); //$NON-NLS-1$
				indCommentEnd2 = s.trim().indexOf("*/"); //$NON-NLS-1$
				if (isComment && ((indCommentEnd1 != -1) || (indCommentEnd2 != -1))) { // end of blockcomment
					isComment = false;
					if (((indCommentEnd1 < s.trim().length() - 2) && (indCommentEnd1 != -1))
							|| ((indCommentEnd2 < s.trim().length() - 2) && (indCommentEnd2 != -1))) { // code after
						// comment
						loc++;
					}
				}
			}
		}
		return loc;
	}

	private static double analyzeAlgorithm(final Algorithm algorithm) {
		double ccAlg = 0.0;
		final String algText = algorithm.toString();
		int saveIndex = 0;
		for (final String cond : CONDITIONS) {
			int lastIndex = 0;
			while (-1 != lastIndex) {
				if (cond.equals("REPEAT")) { //$NON-NLS-1$
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
	protected MetricData createDataType() {
		return new SpiderChartBFBData();
	}

}

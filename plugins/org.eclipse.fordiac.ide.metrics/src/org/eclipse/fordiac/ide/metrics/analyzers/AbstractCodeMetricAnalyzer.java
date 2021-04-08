/*******************************************************************************
 * Copyright (c) 2019 TU Wien, ACIN,
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
 *   Lisa Sonnleithner - Changed calculation method to average
 *******************************************************************************/
package org.eclipse.fordiac.ide.metrics.analyzers;

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public abstract class AbstractCodeMetricAnalyzer {

	MetricData data;

	public void calculateMetrics(INamedElement element) {

		if (element instanceof FB) {
			element = ((FB) element).getPaletteEntry().getType();
		}
		if (element instanceof Application) {
			data = analyzeFBNetwork(((Application) element).getFBNetwork(), true);
		} else if (element instanceof SubApp) {
			data = analyzeSubApp((SubApp) element, true);
		} else if (element instanceof FBType) { // typed subapp, CFB or BFB
			data = analyzeFBType((FBType) element);
		}
	}

	public abstract List<MetricResult> getResults();

	protected MetricData analyzeSubApp(final SubApp subApp, final boolean calcAvg) {
		if (subApp.isTyped()) {
			return analyzeFBNetwork(subApp.getType().getFBNetwork(), true);
		}
		return analyzeFBNetwork(subApp.getSubAppNetwork(), calcAvg);
	}

	protected MetricData analyzeFBType(final FBType type) {
		MetricData tempData = null;
		if (type instanceof BasicFBType) {
			tempData = analyzeBFB((BasicFBType) type);
		} else if (type instanceof CompositeFBType) {
			tempData = analyzeCFB((CompositeFBType) type);
		}
		return tempData;
	}

	protected MetricData analyzeFBNetwork(final FBNetwork fbNetwork, final boolean calcAvg) {
		final MetricData tempData = createDataType();
		int cnt = 0;
		for (final FBNetworkElement fb : fbNetwork.getNetworkElements()) {
			if (fb instanceof SubApp) {
				final MetricData tempData2 = analyzeSubApp((SubApp) fb, false);
				if (tempData2.cnt == 0) {
					cnt++;
				} else {
					cnt += tempData2.cnt;
				}
				tempData.add(tempData2);
			} else {
				tempData.add(analyzeFBType(fb.getType()));
				cnt++;
			}
		}
		if (calcAvg) {
			tempData.divide(cnt);
		} else {
			tempData.cnt = cnt;
		}
		return tempData;
	}

	protected abstract MetricData analyzeBFB(BasicFBType basicFBType);

	protected MetricData analyzeCFB(final CompositeFBType compositeFBType) {
		return analyzeFBNetwork(compositeFBType.getFBNetwork(), true);
	}

	protected abstract MetricData createDataType();

}

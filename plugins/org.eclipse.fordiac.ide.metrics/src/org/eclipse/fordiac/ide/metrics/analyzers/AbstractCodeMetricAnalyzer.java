/*******************************************************************************
 * Copyright (c) 2019 TU Wien, ACIN, Johannes Kepler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.metrics.analyzers;

import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public abstract class AbstractCodeMetricAnalyzer {

	public void calculateMetrics(INamedElement element) {
		if (element instanceof Application) {
			analyzeFBNetwork(((Application) element).getFBNetwork());
		} else if (element instanceof SubApp) {
			analyzeSubApp((SubApp) element);
		} else if (element instanceof FBType) {
			analyzeFBType((FBType) element);
		}
	}

	public abstract List<MetricData> getResults();

	private void analyzeFBNetwork(FBNetwork fbNetwork) {
		for (FBNetworkElement fb : fbNetwork.getNetworkElements()) {
			if (fb instanceof SubApp) {
				analyzeSubApp((SubApp) fb);
			} else {
				analyzeFBType(fb.getType());
			}
		}
	}

	private void analyzeSubApp(SubApp subApp) {
		analyzeFBNetwork((null != subApp.getType()) ? subApp.getType().getFBNetwork() : subApp.getSubAppNetwork());
	}

	private void analyzeFBType(FBType type) {
		if (type instanceof BasicFBType) {
			analyzeBFB((BasicFBType) type);
		} else if (type instanceof CompositeFBType) {
			analyzeFBNetwork(((CompositeFBType) type).getFBNetwork());
		}
	}

	protected abstract void analyzeBFB(BasicFBType basicFBType);

}

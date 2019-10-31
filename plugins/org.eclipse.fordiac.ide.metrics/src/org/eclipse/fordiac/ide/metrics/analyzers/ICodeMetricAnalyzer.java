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

import org.eclipse.fordiac.ide.model.libraryElement.Application;

public interface ICodeMetricAnalyzer {

	void calculateMetrics(Application app);

	void printMetrics(StringBuilder result);

}

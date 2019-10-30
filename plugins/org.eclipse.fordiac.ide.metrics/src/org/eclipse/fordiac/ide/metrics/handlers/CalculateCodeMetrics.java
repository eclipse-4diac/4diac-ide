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
package org.eclipse.fordiac.ide.metrics.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.fordiac.ide.metrics.analyzers.CyclomaticComplexity;
import org.eclipse.fordiac.ide.metrics.analyzers.HalsteadMetric;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class CalculateCodeMetrics extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);

		StringBuilder result = new StringBuilder();

		for (Object element : selection.toList()) {
			if (element instanceof Application) {
				calculateApplicationMetrics((Application) element, result);
			}
		}

		displayResults(result, event);
		return null;
	}

	private static void calculateApplicationMetrics(Application element, StringBuilder result) {
		CyclomaticComplexity cyclo = new CyclomaticComplexity();
		cyclo.calculateMetrics(element);
		cyclo.printMetrics(result);

		HalsteadMetric halstead = new HalsteadMetric();
		halstead.calculateMetrics(element);
		halstead.printMetrics(result);
	}

	private static void displayResults(StringBuilder result, ExecutionEvent event)
			throws org.eclipse.core.commands.ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(window.getShell(), "Calculated Metrics", result.toString());
	}

}

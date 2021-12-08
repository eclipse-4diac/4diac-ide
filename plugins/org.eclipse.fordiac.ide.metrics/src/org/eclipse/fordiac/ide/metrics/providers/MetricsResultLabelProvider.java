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
package org.eclipse.fordiac.ide.metrics.providers;

import java.text.DecimalFormat;

import org.eclipse.fordiac.ide.metrics.analyzers.MetricResult;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class MetricsResultLabelProvider extends LabelProvider implements ITableLabelProvider {
	private static final DecimalFormat decimalFormat = new DecimalFormat("#0.00"); //$NON-NLS-1$

	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(final Object element, final int columnIndex) {
		if (element instanceof MetricResult) {
			switch (columnIndex) {
			case 0:
				return ((MetricResult) element).getName();
			case 1:
				return decimalFormat.format(((MetricResult) element).getValue());
			default:
				break;
			}
		}
		return element.toString();
	}

}
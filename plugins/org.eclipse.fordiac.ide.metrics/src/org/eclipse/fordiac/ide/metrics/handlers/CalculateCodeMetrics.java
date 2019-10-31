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
package org.eclipse.fordiac.ide.metrics.handlers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.fordiac.ide.metrics.analyzers.CyclomaticComplexity;
import org.eclipse.fordiac.ide.metrics.analyzers.HalsteadMetric;
import org.eclipse.fordiac.ide.metrics.analyzers.ICodeMetricAnalyzer;
import org.eclipse.fordiac.ide.metrics.analyzers.MetricData;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class CalculateCodeMetrics extends AbstractHandler {

	public static class MetricsResultLabelProvider extends LabelProvider implements ITableLabelProvider {
		private static final DecimalFormat decimalFormat = new DecimalFormat("#0.00");

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof MetricData) {
				switch (columnIndex) {
				case 0:
					return ((MetricData) element).getName();
				case 1:
					return decimalFormat.format(((MetricData) element).getValue());
				default:
					break;
				}
			}
			return element.toString();
		}

	}

	private static class MetricsResultDialog extends MessageDialog {

		private final List<MetricData> data;

		public MetricsResultDialog(Shell parent, final List<MetricData> data) {
			super(parent, "Calculated Metrics", null, null, INFORMATION, 0, IDialogConstants.OK_LABEL);
			this.data = data;
		}

		@Override
		protected Control createMessageArea(Composite composite) {
			final Composite body = (Composite) super.createMessageArea(composite);

			final TableViewer viewer = new TableViewer(body, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false)
					.hint(convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH), SWT.DEFAULT)
					.applyTo(viewer.getTable());
			configureTableColumns(viewer.getTable());

			viewer.setContentProvider(new ArrayContentProvider());
			viewer.setLabelProvider(new MetricsResultLabelProvider());
			viewer.setInput(data);

			return body;
		}

		private static void configureTableColumns(Table table) {
			new TableColumn(table, SWT.LEFT).setText("Metric");
			new TableColumn(table, SWT.RIGHT).setText("Value");

			TableLayout layout = new TableLayout();
			layout.addColumnData(new ColumnWeightData(65, 100));
			layout.addColumnData(new ColumnWeightData(25, 10));
			table.setLayout(layout);

			table.setLinesVisible(true);
			table.setHeaderVisible(true);
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);

		List<MetricData> result = new ArrayList<>();

		for (Object element : selection.toList()) {
			if (element instanceof Application) {
				calculateApplicationMetrics((Application) element, result);
			}
		}

		displayResults(result, HandlerUtil.getActiveWorkbenchWindowChecked(event));
		return null;
	}

	private static void calculateApplicationMetrics(Application app, List<MetricData> result) {
		List<ICodeMetricAnalyzer> analyzers = getAnalyzers();
		for (ICodeMetricAnalyzer analyzer : analyzers) {
			analyzer.calculateMetrics(app);
			result.addAll(analyzer.getResults());
		}
	}

	private static List<ICodeMetricAnalyzer> getAnalyzers() {
		List<ICodeMetricAnalyzer> analyzers = new ArrayList<>();
		analyzers.add(new CyclomaticComplexity());
		analyzers.add(new HalsteadMetric());
		return analyzers;
	}

	private static void displayResults(List<MetricData> result, IWorkbenchWindow workbenchWindow) {
		MetricsResultDialog resultDialog = new MetricsResultDialog(workbenchWindow.getShell(), result);
		resultDialog.open();
	}

}

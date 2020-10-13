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

package org.eclipse.fordiac.ide.metrics.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


import org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeEditor;
import org.eclipse.fordiac.ide.metrics.Messages;
import org.eclipse.fordiac.ide.metrics.analyzers.AbstractCodeMetricAnalyzer;
import org.eclipse.fordiac.ide.metrics.analyzers.MetricResult;
import org.eclipse.fordiac.ide.metrics.analyzers.SpiderChartBFBMeasures;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;

public class MetricsView extends ViewPart {
	TableViewer viewer;
	Label currentBlock;
	private List<MetricResult> data = new ArrayList<>();

	public static class MetricsResultLabelProvider extends LabelProvider implements ITableLabelProvider {
		private static final DecimalFormat decimalFormat = new DecimalFormat("#0.00"); //$NON-NLS-1$

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
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

	private void calculateMetrics(INamedElement element, List<MetricResult> result) {
		AbstractCodeMetricAnalyzer analyzer = new SpiderChartBFBMeasures();
		analyzer.calculateMetrics(element);
		result.addAll(analyzer.getResults());

	}

	@Override
	public void createPartControl(Composite parent) {

		parent.setLayout(new GridLayout(2, true));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		Composite composite = toolkit.createComposite(parent);
		composite.setLayout(new GridLayout(1, true));
		composite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));

		createSCBFBSection(composite, toolkit);

	}

	private void createSCBFBSection(Composite parent, FormToolkit toolkit) {
		Composite composite = toolkit.createComposite(parent);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));

		Button button = toolkit.createButton(composite, "", SWT.PUSH);
		toolkit.createLabel(composite, Messages.SpiderChartBFBMeasuresOf);
		currentBlock = toolkit.createLabel(composite, null);

		button.setImage(FordiacImage.ICON_REFRESH.getImage());
		updateMetrics();

		viewer = TableWidgetFactory.createPropertyTableViewer(parent,
				SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(viewer.getTable());
		configureTableColumns(viewer.getTable());
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new MetricsResultLabelProvider());
		viewer.setInput(data);
		button.addListener(SWT.Selection, e -> {
			updateMetrics();
			viewer.setInput(data);
		});
	}

	protected void updateMetrics() {
		data.clear();
		IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		if (activeEditor instanceof FBTypeEditor) {
			FBType type = ((FBTypeEditor) activeEditor).getFBType();

			if ((type instanceof BasicFBType)) {
				currentBlock.setText(type.getName());
				calculateMetrics(((FBTypeEditor) activeEditor).getFBType(), data);
			}

		}
		if (data.isEmpty()) {
			currentBlock.setText("Messages.SpiderChartBFBError");
		}
	}

	private static void configureTableColumns(Table table) {
		new TableColumn(table, SWT.LEFT).setText(Messages.Measure);
		new TableColumn(table, SWT.RIGHT).setText(Messages.Value);

		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(65, 100));
		layout.addColumnData(new ColumnWeightData(25, 10));
		table.setLayout(layout);
		table.setVisible(true);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}

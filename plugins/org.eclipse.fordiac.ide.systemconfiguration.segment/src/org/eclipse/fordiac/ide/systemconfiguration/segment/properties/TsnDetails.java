/*******************************************************************************
 * Copyright (c) 2022 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.segment.properties;

import java.util.List;

import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.systemconfiguration.CommunicationConfigurationDetails;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Messages;
import org.eclipse.fordiac.ide.systemconfiguration.segment.TsnParameters;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.CommunicationFactory;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.segment.Communication.TsnWindow;
import org.eclipse.fordiac.ide.systemconfiguration.segment.commands.ChangeTsnCycleTimeCommand;
import org.eclipse.fordiac.ide.systemconfiguration.segment.commands.ChangeTsnWindowDurationCommand;
import org.eclipse.fordiac.ide.systemconfiguration.segment.commands.ChangeTsnWindowOrderCommand;
import org.eclipse.fordiac.ide.systemconfiguration.segment.commands.CreateTsnWindowCommand;
import org.eclipse.fordiac.ide.systemconfiguration.segment.commands.DeleteTsnWindowCommand;
import org.eclipse.fordiac.ide.systemconfiguration.segment.providers.TsnWindowLabelProvider;
import org.eclipse.fordiac.ide.systemconfiguration.segment.widget.MappedFbMenu;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class TsnDetails extends CommunicationConfigurationDetails {
	private static final String NAME_COL = "name"; //$NON-NLS-1$
	private static final String VALUE_COL = "value"; //$NON-NLS-1$
	private static final String TYPE_COL = "type"; //$NON-NLS-1$
	private static final String COMMENT_COL = "comment"; //$NON-NLS-1$

	@Override
	public Composite createUi(final Composite parent, final CommunicationConfiguration config,
			final CommandExecutor executor, final TabbedPropertySheetWidgetFactory widgetFactory) {
		final TsnConfiguration tsnConfig = config == null ? CommunicationFactory.eINSTANCE.createTsnConfiguration()
				: (TsnConfiguration) config;
		final Composite detailsComp = widgetFactory.createComposite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).applyTo(detailsComp);
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(detailsComp);
		createCycleTimeArea(widgetFactory, tsnConfig, detailsComp, executor);
		createTsnWindowArea(widgetFactory, tsnConfig, detailsComp, executor);
		return detailsComp;
	}

	private static void createCycleTimeArea(final TabbedPropertySheetWidgetFactory widgetFactory,
			final TsnConfiguration tsnConfig, final Composite detailsComp, final CommandExecutor executor) {
		final Label cycleTimeLbl = widgetFactory.createLabel(detailsComp, Messages.TsnDetails_CycleTime);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).applyTo(cycleTimeLbl);
		final Text cycleTimeValue = widgetFactory.createText(detailsComp, String.valueOf(tsnConfig.getCycleTime()));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(cycleTimeValue);
		cycleTimeValue.requestLayout();
		final Label msLbl = widgetFactory.createLabel(detailsComp, Messages.TsnDetails_MS);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).applyTo(msLbl);

		cycleTimeValue.addModifyListener(event -> executor
				.executeCommand(new ChangeTsnCycleTimeCommand(tsnConfig, Integer.parseInt(cycleTimeValue.getText()))));
	}

	private static void createTsnWindowArea(final TabbedPropertySheetWidgetFactory widgetFactory,
			final TsnConfiguration tsnConfig, final Composite detailsComp, final CommandExecutor executor) {
		final AddDeleteReorderListWidget buttons = new AddDeleteReorderListWidget();
		buttons.createControls(detailsComp, widgetFactory);
		final TableViewer windowViewer = TableWidgetFactory.createTableViewer(detailsComp);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).span(2, 1)
				.applyTo(windowViewer.getTable());

		final Table table = windowViewer.getTable();
		configureTableLayout(table);
		windowViewer.setContentProvider(new ArrayContentProvider());
		windowViewer.setLabelProvider(new TsnWindowLabelProvider());
		windowViewer.setCellEditors(new CellEditor[] { new TextCellEditor(table), new TextCellEditor(table),
				new TextCellEditor(table), new TextCellEditor(table) });
		windowViewer.setColumnProperties(new String[] { NAME_COL, VALUE_COL, TYPE_COL, COMMENT_COL });
		windowViewer.setCellModifier(new TimeSlotCellModifier(windowViewer, executor));
		MappedFbMenu.addContextMenu(windowViewer);
		windowViewer.setInput(tsnConfig.getWindows());
		configureButtonList(windowViewer, buttons, tsnConfig, executor);
	}

	private static void configureButtonList(final TableViewer windowViewer, final AddDeleteReorderListWidget buttons,
			final TsnConfiguration tsnConfig, final CommandExecutor executor) {
		buttons.bindToTableViewer(windowViewer, executor, ref -> new CreateTsnWindowCommand(tsnConfig, 1),
				ref -> new DeleteTsnWindowCommand(tsnConfig, (TsnWindow) ref),
				ref -> new ChangeTsnWindowOrderCommand((TsnWindow) ref, tsnConfig, true),
				ref -> new ChangeTsnWindowOrderCommand((TsnWindow) ref, tsnConfig, false));
	}

	private static void configureTableLayout(final Table table) {
		final TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText(NAME_COL);
		final TableColumn column2 = new TableColumn(table, SWT.LEFT);
		column2.setText(VALUE_COL);
		final TableColumn column3 = new TableColumn(table, SWT.LEFT);
		column3.setText(TYPE_COL);
		final TableColumn column4 = new TableColumn(table, SWT.LEFT);
		column4.setText(COMMENT_COL);
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(20, 70));
		layout.addColumnData(new ColumnWeightData(30, 70));
		layout.addColumnData(new ColumnWeightData(30, 70));
		layout.addColumnData(new ColumnWeightData(50, 90));
		table.setLayout(layout);
	}

	@Override
	public CommunicationConfiguration createModel() {
		return CommunicationFactory.eINSTANCE.createTsnConfiguration();
	}

	private static class TimeSlotCellModifier implements ICellModifier {
		private final CommandExecutor executor;
		private final TableViewer viewer;

		public TimeSlotCellModifier(final TableViewer windowViewer, final CommandExecutor executor) {
			this.executor = executor;
			this.viewer = windowViewer;
		}

		@Override
		public boolean canModify(final Object element, final String property) {
			return (element instanceof TsnWindow) && !(NAME_COL.equals(property)) && !(TYPE_COL.equals(property));
		}

		@Override
		public String getValue(final Object element, final String property) {
			switch (property) {
			case NAME_COL:
				return ((TsnWindow) element).getName();
			case VALUE_COL:
				return String.valueOf(((TsnWindow) element).getDuration());
			case TYPE_COL:
				return "TIME"; //$NON-NLS-1$
			case COMMENT_COL:
				return ((TsnWindow) element).getComment();
			default:
				return null;
			}
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			final TsnWindow data = (TsnWindow) ((TableItem) element).getData();
			Command cmd = null;
			switch (property) {
			case VALUE_COL:
				cmd = new ChangeTsnWindowDurationCommand(data, Integer.parseInt(value.toString()));
				break;
			case COMMENT_COL:
				cmd = new ChangeCommentCommand(data, value.toString());
				break;
			default:
			}
			executor.executeCommand(cmd);
			viewer.refresh();
		}
	}

	@Override
	public CommunicationConfiguration createModel(final List<VarDeclaration> parameters) {
		final TsnConfiguration configuration = (TsnConfiguration) createModel();
		for (final VarDeclaration parameter : parameters) {
			if (parameter.getName().startsWith(TsnParameters.TSN_WINDOW_NAME)) {
				final TsnWindow window = CommunicationFactory.eINSTANCE.createTsnWindow();
				window.setDuration(Integer.parseInt(parameter.getValue().getValue()));
				configuration.getWindows().add(window);
			} else if (parameter.getName().equals(TsnParameters.TSN_CYCLE_NAME)) {
				configuration.setCycleTime(Integer.parseInt(parameter.getValue().getValue()));
			}
		}
		int sum;
		if ((sum = configuration.getWindows().stream().mapToInt(TsnWindow::getDuration).sum()) > configuration
				.getCycleTime()) {
			configuration.setCycleTime(sum);
		}
		return configuration;
	}

}

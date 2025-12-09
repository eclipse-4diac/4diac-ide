/*******************************************************************************
 * Copyright (c) 2014, 2024 fortiss GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr
 *     - extract table viewer creation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.properties;

import java.util.Arrays;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.fbtypeeditor.contentprovider.VarContentProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.contentprovider.VarDeclarationLabelProvider;
import org.eclipse.fordiac.ide.model.commands.create.WithCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteWithCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.ui.nat.EventTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.EventTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.ITypeSelectionContentProvider;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class EventInterfaceElementSection extends AdapterInterfaceElementSection {

	private TableViewer withEventsViewer;
	private Group eventComposite;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createEventSection(getRightComposite());
	}

	private void createEventSection(final Composite parent) {
		eventComposite = getWidgetFactory().createGroup(parent, FordiacMessages.With);
		eventComposite.setLayout(new GridLayout(1, false));
		eventComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		withEventsViewer = TableWidgetFactory.createPropertyTableViewer(eventComposite, SWT.CHECK);
		withEventsViewer.setContentProvider(new VarContentProvider());
		withEventsViewer.setLabelProvider(new VarDeclarationLabelProvider());

		final Table tableWith = withEventsViewer.getTable();
		configureTableLayout(tableWith);
		tableWith.addListener(SWT.Selection, event -> {
			if (event.detail == SWT.CHECK) {
				final TableItem checkedItem = (TableItem) event.item;
				final VarDeclaration variable = (VarDeclaration) checkedItem.getData();
				final With with = variable.getWiths().stream().filter(w -> w.eContainer().equals(getType())).findFirst()
						.orElse(null);
				if (checkedItem.getChecked()) {
					if (null == with) {
						executeCommand(new WithCreateCommand(getType(), variable));
					}
				} else if (null != with) {
					executeCommand(new DeleteWithCommand(with));
				}
			}
		});
	}

	private void configureTableLayout(final Table tableWith) {
		final TableColumn column1 = new TableColumn(withEventsViewer.getTable(), SWT.LEFT);
		column1.setText(FordiacMessages.Variable);
		final TableColumn column2 = new TableColumn(withEventsViewer.getTable(), SWT.LEFT);
		column2.setText(FordiacMessages.DataType);
		final TableColumn column3 = new TableColumn(withEventsViewer.getTable(), SWT.LEFT);
		column3.setText(FordiacMessages.Comment);
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(20, 100));
		layout.addColumnData(new ColumnWeightData(20, 70));
		layout.addColumnData(new ColumnWeightData(20, 100));
		tableWith.setLayout(layout);
	}

	@Override
	protected void setInputInit() {
		super.setInputInit();
		if (null == getCurrentCommandStack()) { // disable all fields
			withEventsViewer.setInput(null);
			Arrays.stream(withEventsViewer.getTable().getItems()).forEach(item -> item.setGrayed(true));
		}
	}

	@Override
	protected void performRefresh() {
		super.performRefresh();
		final EObject container = getType().eContainer();
		if (container != null && !(container.eContainer() instanceof SubAppType)) {
			eventComposite.setVisible(true);
			withEventsViewer.setInput(getType());
			withEventsViewer.getTable().setEnabled(isEditable());
			Arrays.stream(withEventsViewer.getTable().getItems()).forEach(item -> item.setChecked(false));
			getType().getWith().stream().filter(with -> (with.getVariables() != null))
					.map(with -> withEventsViewer.testFindItem(with.getVariables())).filter(TableItem.class::isInstance)
					.forEach(item -> ((TableItem) item).setChecked(true));
		} else {
			eventComposite.setVisible(false);
		}
	}

	@Override
	protected Event getType() {
		return (Event) super.getType();
	}

	@Override
	protected ITypeSelectionContentProvider getTypeSelectionContentProvider() {
		return EventTypeSelectionContentProvider.INSTANCE;
	}

	@Override
	protected ITreeContentProvider getTypeSelectionTreeContentProvider() {
		return EventTypeSelectionTreeContentProvider.INSTANCE;
	}
}

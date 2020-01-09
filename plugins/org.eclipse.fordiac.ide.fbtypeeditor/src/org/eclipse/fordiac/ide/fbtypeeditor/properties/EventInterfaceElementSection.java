/*******************************************************************************
 * Copyright (c) 2014 - 2017 fortiss GmbH
 *               2019 Johannes Kepler University Linz
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

import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.fbtypeeditor.contentprovider.VarContentProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.contentprovider.VarDeclarationLabelProvider;
import org.eclipse.fordiac.ide.model.commands.create.WithCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteWithCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
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
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class EventInterfaceElementSection extends AdapterInterfaceElementSection {

	private TableViewer withEventsViewer;
	private Group eventComposite;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createEventSection(getRightComposite());
	}

	private void createEventSection(Composite parent) {
		eventComposite = getWidgetFactory().createGroup(parent, "With");
		eventComposite.setLayout(new GridLayout(1, false));
		eventComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		withEventsViewer = TableWidgetFactory.createPropertyTableViewer(eventComposite, SWT.CHECK);
		withEventsViewer.setContentProvider(new VarContentProvider());
		withEventsViewer.setLabelProvider(new VarDeclarationLabelProvider());

		Table tableWith = withEventsViewer.getTable();
		configureTableLayout(tableWith);
		tableWith.addListener(SWT.Selection, event -> {
			if (event.detail == SWT.CHECK) {
				TableItem checkedItem = (TableItem) event.item;
				VarDeclaration variable = (VarDeclaration) checkedItem.getData();
				With with = variable.getWiths().stream().filter(w -> w.eContainer().equals(getType())).findFirst()
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

	private void configureTableLayout(Table tableWith) {
		TableColumn column1 = new TableColumn(withEventsViewer.getTable(), SWT.LEFT);
		column1.setText("Variable");
		TableColumn column2 = new TableColumn(withEventsViewer.getTable(), SWT.LEFT);
		column2.setText("DataType");
		TableColumn column3 = new TableColumn(withEventsViewer.getTable(), SWT.LEFT);
		column3.setText("Comment");
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(20, 100));
		layout.addColumnData(new ColumnWeightData(20, 70));
		layout.addColumnData(new ColumnWeightData(20, 100));
		tableWith.setLayout(layout);
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		super.setInput(part, selection);
		Assert.isTrue(selection instanceof IStructuredSelection);
		// hide with part for sub app type events
		eventComposite.setVisible(!(getType().eContainer().eContainer() instanceof SubAppType));
		if (null == commandStack) { // disable all field
			withEventsViewer.setInput(null);
			Arrays.stream(withEventsViewer.getTable().getItems()).forEach(item -> item.setGrayed(true));
		}
	}

	@Override
	public void refresh() {
		super.refresh();
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != getType()) {
			withEventsViewer.setInput(getType());
			Arrays.stream(withEventsViewer.getTable().getItems()).forEach(item -> item.setChecked(false));
			getType().getWith().stream().filter(with -> (with.getVariables() != null))
					.map(with -> withEventsViewer.testFindItem(with.getVariables()))
					.filter(item -> (item instanceof TableItem)).forEach(item -> ((TableItem) item).setChecked(true));
		}
		commandStack = commandStackBuffer;
	}

	@Override
	public void setTypeDropdown() {
		typeCombo.removeAll();
		typeCombo.add("Event");
		typeCombo.select(0);
	}

	@Override
	protected DataType getTypeForSelection(String text) {
		// currently we only have one kind of data type therefore we will return null
		// here so that it is not changed
		return null;
	}

	@Override
	protected Event getType() {
		return (Event) super.getType();
	}
}

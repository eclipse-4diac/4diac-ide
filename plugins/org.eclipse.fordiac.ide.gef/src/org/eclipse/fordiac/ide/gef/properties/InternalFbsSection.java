/*******************************************************************************
 * Copyright (c) 2021, 2024 Primetals Technologies Germany GmbH,
 *                          Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *     - initial API and implementation and/or initial documentation
 *   Fabio Gandolfi - added NewInstanceCellEditor in "Type" cell
 *   Prankur Agarwal - switch to NatTable
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import java.util.Collections;

import org.eclipse.fordiac.ide.gef.nat.FBColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.TypedElementConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.TypedElementTableColumn;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInternalFBOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.IndexUpDown;
import org.eclipse.fordiac.ide.model.commands.create.CreateInternalFBCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInternalFBCommand;
import org.eclipse.fordiac.ide.model.commands.insert.InsertFBCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.ui.nat.FBTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.FBTreeNodeLabelProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.FBTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.TypeSelectionButton;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderToolbarWidget;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.IChangeableRowDataProvider;
import org.eclipse.fordiac.ide.ui.widget.ISelectionProviderSection;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.selection.RowPostSelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InternalFbsSection extends AbstractSection implements I4diacNatTableUtil, ISelectionProviderSection {
	protected IChangeableRowDataProvider<FB> provider;
	protected NatTable table;
	private RowPostSelectionProvider<FB> selectionProvider;
	private AddDeleteReorderToolbarWidget buttons;

	@Override
	protected BaseFBType getType() {
		return (BaseFBType) type;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createInternalFbsControls(parent);
	}

	public void createInternalFbsControls(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		buttons = new AddDeleteReorderToolbarWidget();
		buttons.createControls(composite, getWidgetFactory());

		provider = new ChangeableListDataProvider<>(new FBColumnAccessor(this));
		final DataLayer dataLayer = new DataLayer(provider);
		dataLayer.setConfigLabelAccumulator(
				new TypedElementConfigLabelAccumulator<>(provider, this::getAnnotationModel));
		table = NatTableWidgetFactory.createRowNatTable(composite, dataLayer,
				new NatTableColumnProvider<>(TypedElementTableColumn.DEFAULT_COLUMNS), IEditableRule.ALWAYS_EDITABLE,
				new TypeSelectionButton(this::getTypeLibrary, FBTypeSelectionContentProvider.INSTANCE,
						FBTypeSelectionTreeContentProvider.INSTANCE, FBTreeNodeLabelProvider.INSTANCE),
				this, false);
		table.configure();

		buttons.bindToTableViewer(table, this,
				ref -> new CreateInternalFBCommand(getType(), getInsertionIndex(), getName(), getFBTypeEntry()),
				ref -> new DeleteInternalFBCommand((FB) ref),
				ref -> new ChangeInternalFBOrderCommand(getType(), (FB) ref, IndexUpDown.UP),
				ref -> new ChangeInternalFBOrderCommand(getType(), (FB) ref, IndexUpDown.DOWN));

		selectionProvider = new RowPostSelectionProvider<>(table, NatTableWidgetFactory.getSelectionLayer(table),
				provider, false);
	}

	private FBTypeEntry getFBTypeEntry() {
		final FB fb = getLastSelectedFB();
		return (null != fb) ? (FBTypeEntry) fb.getTypeEntry() : null;
	}

	private String getName() {
		final FB fb = getLastSelectedFB();
		return (null != fb) ? fb.getName() : null;
	}

	private int getInsertionIndex() {
		final FB fb = getLastSelectedFB();
		if (null == fb) {
			return getType().getInternalFbs().size();
		}
		return getType().getInternalFbs().indexOf(fb) + 1;
	}

	private FB getLastSelectedFB() {
		return (FB) NatTableWidgetFactory.getLastSelectedVariable(table);
	}

	@Override
	public void addEntry(final Object entry, final boolean isInput, final int index, final CompoundCommand cmd) {
		if (entry instanceof final FB fb) {
			cmd.add(new InsertFBCommand(getType(), fb, index));
		}

	}

	@Override
	protected void performRefresh() {
		provider.setInput(getType() != null ? getType().getInternalFbs() : Collections.emptyList());
		table.refresh();
	}

	@Override
	protected void performRefreshAnnotations() {
		table.refresh(false);
	}

	@Override
	public void executeCompoundCommand(final CompoundCommand cmd) {
		executeCommand(cmd);
		table.refresh();

	}

	@Override
	public boolean isEditable() {
		return true;
	}

	@Override
	protected Object getInputType(final Object input) {
		return BaseFBFilter.getFBTypeFromSelectedElement(input);
	}

	@Override
	protected void setInputCode() {
		// nothing to do here
	}

	@Override
	protected void setInputInit() {
		final BaseFBType currentType = getType();
		provider.setInput(currentType != null ? currentType.getInternalFbs() : Collections.emptyList());
		table.refresh();
	}

	@Override
	public void dispose() {
		super.dispose();
		if (buttons != null) {
			buttons.dispose();
		}
	}

	@Override
	public void removeEntry(final Object entry, final CompoundCommand cmd) {
		if (entry instanceof final FB fb) {
			cmd.add(new DeleteInternalFBCommand(fb));
		}
	}

	@Override
	public ISelectionProvider getSelectionProvider() {
		return selectionProvider;
	}
}

/*******************************************************************************
 * Copyright (c) 2020, 2023 Johannes Kepler University, Linz,
 *                          Primetals Technologies Germany GmbH,
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber, Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *   Alexander Lumplecker
 *     - changed ChangeMemberVariableOrderCommand to ChangeVariableOrderCommand
 *   Martin Jobst - add initial value cell editor support
 *******************************************************************************/
package org.eclipse.fordiac.ide.datatypeeditor.widgets;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.datatypeeditor.Messages;
import org.eclipse.fordiac.ide.gef.nat.InitialValueEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.TypeDeclarationEditorConfiguration;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationColumnAccessor;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationConfigLabelAccumulator;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationDataLayer;
import org.eclipse.fordiac.ide.gef.nat.VarDeclarationTableColumn;
import org.eclipse.fordiac.ide.model.commands.change.ChangeVariableOrderCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateMemberVariableCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteMemberVariableCommand;
import org.eclipse.fordiac.ide.model.commands.insert.InsertVariableCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.IChangeableRowDataProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.edit.event.DataUpdateEvent;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.command.ClearAllSelectionsCommand;
import org.eclipse.nebula.widgets.nattable.selection.event.RowSelectionEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class StructEditingComposite extends Composite
		implements CommandExecutor, I4diacNatTableUtil, ISelectionProvider {

	private NatTable natTable;
	private final CommandStack cmdStack;
	private StructuredType structType;
	private final IChangeableRowDataProvider<VarDeclaration> structMemberProvider;

	private ConfigurableObject currentConfigurableObject = null;
	private ConfigurablObjectListener configObjectListener = null;
	private boolean blockRefresh;

	private final Adapter adapter = new AdapterImpl() {

		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (!notification.isTouch() && !blockRefresh) {
				notifyRefresh();
			}
		}

		private void notifyRefresh() {
			Display.getDefault().syncExec(() -> {
				if (null != structType && natTable != null && !natTable.isDisposed()) {
					natTable.refresh();
				}
			});
		}
	};

	public StructEditingComposite(final Composite parent, final CommandStack cmdStack,
			final StructuredType structType) {
		super(parent, SWT.NONE);
		structMemberProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this));
		this.cmdStack = cmdStack;
		setStructType(structType);
		createPartControl();
	}

	private void createPartControl() {
		final TabbedPropertySheetWidgetFactory widgetFactory = new TabbedPropertySheetWidgetFactory();
		setLayout(new GridLayout(2, false));
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		showLabel(this);

		final AddDeleteReorderListWidget buttons = new AddDeleteReorderListWidget();
		buttons.createControls(this, widgetFactory);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(final MouseEvent e) {
				natTable.doCommand(new ClearAllSelectionsCommand());
				fireConfigurablObjectChanged(getType());
			}
		});

		createNatTable();

		buttons.bindToTableViewer(natTable, this,
				ref -> new CreateMemberVariableCommand(getType(), getInsertionIndex(), getVarName(), getDataType()),
				ref -> new DeleteMemberVariableCommand(getType(), (VarDeclaration) ref),
				ref -> new ChangeVariableOrderCommand(getType().getMemberVariables(), (VarDeclaration) ref, true),
				ref -> new ChangeVariableOrderCommand(getType().getMemberVariables(), (VarDeclaration) ref, false));
	}

	private static void showLabel(final Composite parent) {
		final Label label = new Label(parent, SWT.LEFT);
		label.setText(Messages.StructViewingComposite_Headline);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).span(2, 1).applyTo(label);
	}

	private void createNatTable() {
		final DataLayer inputDataLayer = new VarDeclarationDataLayer(structMemberProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS);
		inputDataLayer.setConfigLabelAccumulator(new VarDeclarationConfigLabelAccumulator(structMemberProvider));
		natTable = NatTableWidgetFactory.createRowNatTable(this, inputDataLayer,
				new NatTableColumnProvider<>(VarDeclarationTableColumn.DEFAULT_COLUMNS), IEditableRule.ALWAYS_EDITABLE,
				null, this, false);
		natTable.addConfiguration(new InitialValueEditorConfiguration(structMemberProvider));
		natTable.addConfiguration(new TypeDeclarationEditorConfiguration(structMemberProvider));
		natTable.configure();
		final SelectionLayer selectionLayer = NatTableWidgetFactory.getSelectionLayer(natTable);
		selectionLayer.addLayerListener(event -> {
			if (event instanceof final DataUpdateEvent dataUpdateEvent && dataUpdateEvent.getColumnPosition() == 0
					&& currentConfigurableObject instanceof final VarDeclaration varDecl) {
				fireConfigurablObjectChanged(varDecl);
			} else if (event instanceof final RowSelectionEvent rowSelectionEvent
					&& rowSelectionEvent.getSelectionLayer().getSelectedRowCount() == 1) {
				final int row = rowSelectionEvent.getRowPositionToMoveIntoViewport();
				fireConfigurablObjectChanged(structMemberProvider.getRowObject(row));
			}
		});
	}

	public ConfigurableObject setConfigurablObjectListener(final ConfigurablObjectListener listener) {
		configObjectListener = listener;
		if (currentConfigurableObject == null) {
			currentConfigurableObject = getStruct();
		}
		return currentConfigurableObject;
	}

	private void fireConfigurablObjectChanged(final ConfigurableObject newObject) {
		currentConfigurableObject = newObject;
		if (configObjectListener != null) {
			configObjectListener.handleObjectChanged(newObject);
		}
	}

	private DataType getDataType() {
		final VarDeclaration memVar = getLastSelectedVariable();
		return (null != memVar) ? memVar.getType() : null;
	}

	private String getVarName() {
		final VarDeclaration memVar = getLastSelectedVariable();
		return (null != memVar) ? memVar.getName() : null;
	}

	private int getInsertionIndex() {
		final VarDeclaration memVar = getLastSelectedVariable();
		if (null == memVar) {
			return getType().getMemberVariables().size();
		}
		return getType().getMemberVariables().indexOf(memVar) + 1;
	}

	private VarDeclaration getLastSelectedVariable() {
		return (VarDeclaration) NatTableWidgetFactory.getLastSelectedVariable(natTable);
	}

	private StructuredType getType() {
		return structType;
	}

	public final void setStructType(final StructuredType newStructType) {
		if (structType != newStructType) {
			if (structType != null) {
				structType.eAdapters().remove(adapter);
			}
			structType = newStructType;
			if (structType != null) {
				structMemberProvider.setInput(structType.getMemberVariables());
				structType.eAdapters().add(adapter);
			}
		}
	}

	@Override
	public void executeCommand(final Command cmd) {
		if ((null != getType()) && (null != cmdStack) && (null != cmd) && cmd.canExecute()) {
			blockRefresh = true;
			cmdStack.execute(cmd);
			blockRefresh = false;
		}
	}

	@Override
	public void addEntry(final Object entry, final boolean isInput, final int index, final CompoundCommand cmd) {
		if (entry instanceof final VarDeclaration varEntry) {
			cmd.add(new InsertVariableCommand(getType().getMemberVariables(), varEntry, index));
		}
	}

	@Override
	public void executeCompoundCommand(final CompoundCommand cmd) {
		executeCommand(cmd);
	}

	public Object removeEntry(final int index, final CompoundCommand cmd) {
		final VarDeclaration entry = (VarDeclaration) getEntry(index);
		cmd.add(new DeleteMemberVariableCommand(getType(), entry));
		return entry;
	}

	public TypeLibrary getTypeLibrary() {
		return getType().getTypeLibrary();
	}

	public DataType getStruct() {
		return getType();
	}

	public Object getEntry(final int index) {
		return getType().getMemberVariables().get(index);
	}

	@Override
	public ISelection getSelection() {
		// for now return the whole object so that property sheets and other stuff can
		// filter on it.
		return new StructuredSelection(this);
	}

	@Override
	public boolean isEditable() {
		return true;
	}

	@Override
	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		// currently nothing to be done here
	}

	@Override
	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		// currently nothing to be done here
	}

	@Override
	public void setSelection(final ISelection selection) {
		// currently nothing to be done here
	}

	@Override
	public void removeEntry(final Object entry, final CompoundCommand cmd) {
		if (entry instanceof final VarDeclaration varDecl) {
			cmd.add(new DeleteMemberVariableCommand(getType(), varDecl));
		}
	}

	public interface ConfigurablObjectListener {
		void handleObjectChanged(ConfigurableObject newObject);
	}

	@Override
	public void dispose() {
		super.dispose();
		if (getType() != null) {
			getType().eAdapters().remove(adapter);
		}
	}
}

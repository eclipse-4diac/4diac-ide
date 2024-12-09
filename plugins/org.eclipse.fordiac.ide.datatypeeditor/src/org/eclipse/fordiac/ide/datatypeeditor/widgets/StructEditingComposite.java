/*******************************************************************************
 * Copyright (c) 2020, 2024 Johannes Kepler University, Linz,
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

import java.util.Collections;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModel;
import org.eclipse.fordiac.ide.gef.annotation.GraphicalAnnotationModelListener;
import org.eclipse.fordiac.ide.gef.nat.DefaultImportCopyPasteLayerConfiguration;
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
import org.eclipse.fordiac.ide.model.emf.SingleRecursiveContentAdapter;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderToolbarWidget;
import org.eclipse.fordiac.ide.ui.widget.ChangeableListDataProvider;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.I4diacNatTableUtil;
import org.eclipse.fordiac.ide.ui.widget.IChangeableRowDataProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableColumnProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.selection.RowPostSelectionProvider;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.command.ClearAllSelectionsCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class StructEditingComposite extends Composite implements CommandExecutor, I4diacNatTableUtil {

	private NatTable natTable;
	private AddDeleteReorderToolbarWidget buttons;
	private final CommandStack cmdStack;
	private GraphicalAnnotationModel annotationModel;
	private StructuredType structType;
	private final IChangeableRowDataProvider<VarDeclaration> structMemberProvider;
	private RowPostSelectionProvider<VarDeclaration> selectionProvider;
	private final IWorkbenchSite site;
	protected boolean blockRefresh = false;

	private final Adapter adapter = new SingleRecursiveContentAdapter() {

		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (!notification.isTouch()) {
				notifyRefresh();
			}
		}
	};

	private void notifyRefresh() {
		Display.getDefault().syncExec(() -> {
			if (null != structType && natTable != null && !natTable.isDisposed() && !blockRefresh) {
				natTable.refresh();
			}
		});
	}

	private final GraphicalAnnotationModelListener annotationModelListener = event -> notifyRefresh();

	public StructEditingComposite(final Composite parent, final CommandStack cmdStack, final StructuredType structType,
			final GraphicalAnnotationModel annotationModel, final IWorkbenchSite site) {
		super(parent, SWT.NONE);
		structMemberProvider = new ChangeableListDataProvider<>(new VarDeclarationColumnAccessor(this));
		this.cmdStack = cmdStack;
		this.annotationModel = annotationModel;
		this.site = site;
		setStructType(structType);
		createPartControl();
		addAnnotationModelListener();
	}

	private void createPartControl() {
		final TabbedPropertySheetWidgetFactory widgetFactory = new TabbedPropertySheetWidgetFactory();
		setLayout(new GridLayout(2, false));
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		buttons = new AddDeleteReorderToolbarWidget();
		buttons.createControls(this, widgetFactory, site);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(final MouseEvent e) {
				natTable.doCommand(new ClearAllSelectionsCommand());
			}
		});

		createNatTable();

		buttons.bindToTableViewer(natTable, this,
				ref -> new CreateMemberVariableCommand(getType(), getInsertionIndex(), getVarName(), getDataType()),
				ref -> new DeleteMemberVariableCommand(getType(), (VarDeclaration) ref),
				ref -> new ChangeVariableOrderCommand(getType().getMemberVariables(), (VarDeclaration) ref, true),
				ref -> new ChangeVariableOrderCommand(getType().getMemberVariables(), (VarDeclaration) ref, false));
	}

	private void createNatTable() {
		final DataLayer inputDataLayer = new VarDeclarationDataLayer(structMemberProvider,
				VarDeclarationTableColumn.DEFAULT_COLUMNS);
		inputDataLayer.setConfigLabelAccumulator(
				new VarDeclarationConfigLabelAccumulator(structMemberProvider, this::getAnnotationModel));
		final NatTableColumnProvider<VarDeclarationTableColumn> columnProvider = new NatTableColumnProvider<>(
				VarDeclarationTableColumn.DEFAULT_COLUMNS);
		natTable = NatTableWidgetFactory.createRowNatTable(this, inputDataLayer, columnProvider,
				IEditableRule.ALWAYS_EDITABLE, null, this, false);
		natTable.addConfiguration(new InitialValueEditorConfiguration(structMemberProvider));
		natTable.addConfiguration(new TypeDeclarationEditorConfiguration(structMemberProvider));
		natTable.addConfiguration(new DefaultImportCopyPasteLayerConfiguration(columnProvider, this));
		natTable.configure();

		selectionProvider = new StructEditingCompositeSelectionProvider(natTable,
				NatTableWidgetFactory.getSelectionLayer(natTable), structMemberProvider);
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
			} else {
				structMemberProvider.setInput(Collections.emptyList());
			}
			if (natTable != null && !natTable.isDisposed()) {
				natTable.refresh();
			}
		}
	}

	protected void removeAnnotationModelListener() {
		if (annotationModel != null) {
			annotationModel.removeAnnotationModelListener(annotationModelListener);
		}
	}

	protected void addAnnotationModelListener() {
		if (annotationModel != null) {
			annotationModel.addAnnotationModelListener(annotationModelListener, true);
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
			cmd.add(new InsertVariableCommand(getType(), getType().getMemberVariables(), varEntry, index));
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

	public GraphicalAnnotationModel getAnnotationModel() {
		return annotationModel;
	}

	public void setAnnotationModel(final GraphicalAnnotationModel annotationModel) {
		removeAnnotationModelListener();
		this.annotationModel = annotationModel;
		addAnnotationModelListener();
	}

	public ISelectionProvider getSelectionProvider() {
		return selectionProvider;
	}

	@Override
	public boolean isEditable() {
		return true;
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
		if (buttons != null) {
			buttons.dispose();
		}
		removeAnnotationModelListener();
		if (getType() != null) {
			getType().eAdapters().remove(adapter);
		}
	}

	private class StructEditingCompositeSelectionProvider extends RowPostSelectionProvider<VarDeclaration> {

		public StructEditingCompositeSelectionProvider(final NatTable natTable, final SelectionLayer selectionLayer,
				final IRowDataProvider<VarDeclaration> rowDataProvider) {
			super(natTable, selectionLayer, rowDataProvider, false);
		}

		@Override
		public ISelection getSelection() {
			final ISelection selection = super.getSelection();
			if (selection.isEmpty()) {
				return new StructuredSelection(structType);
			}
			return selection;
		}
	}
}

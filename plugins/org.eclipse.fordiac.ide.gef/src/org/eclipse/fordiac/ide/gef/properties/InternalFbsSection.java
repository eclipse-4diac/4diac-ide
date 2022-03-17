/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Germany GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.fordiac.ide.gef.editors.NewInstanceCellEditor;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeFbTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeInternalFBOrderCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.IndexUpDown;
import org.eclipse.fordiac.ide.model.commands.create.CreateInternalFBCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInternalFBCommand;
import org.eclipse.fordiac.ide.model.commands.insert.InsertFBCommand;
import org.eclipse.fordiac.ide.model.edit.providers.InternalFBLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.I4diacTableUtil;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class InternalFbsSection extends AbstractSection implements I4diacTableUtil {
	private static final String FB_NAME = "NAME"; //$NON-NLS-1$
	private static final String FB_TYPE = "TYPE"; //$NON-NLS-1$
	private static final String FB_COMMENT = "COMMENT"; //$NON-NLS-1$

	private TableViewer internalFbsViewer;
	private NewInstanceCellEditor fbTypeEditor;

	@Override
	protected BaseFBType getType() {
		return (BaseFBType) type;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		createInternalFbsControls(parent);
		TableWidgetFactory.enableCopyPasteCut(tabbedPropertySheetPage);
		OpenStructMenu.addTo(internalFbsViewer);
	}

	public void createInternalFbsControls(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final AddDeleteReorderListWidget buttons = new AddDeleteReorderListWidget();
		buttons.createControls(composite, getWidgetFactory());

		internalFbsViewer = TableWidgetFactory.createTableViewer(composite);
		configureTableLayout(internalFbsViewer.getTable());

		internalFbsViewer.setColumnProperties(new String[] { FB_NAME, FB_TYPE, FB_COMMENT });
		internalFbsViewer.setContentProvider(new ArrayContentProvider());
		internalFbsViewer.setLabelProvider(new InternalFBLabelProvider());
		internalFbsViewer.setCellModifier(new InternalFBsCellModifier());

		buttons.bindToTableViewer(internalFbsViewer, this,
				ref -> new CreateInternalFBCommand(getType(), getInsertionIndex(), getName(), getFBTypePaletteEntry()),
				ref -> new DeleteInternalFBCommand(getType(), getLastSelectedFB()),
				ref -> new ChangeInternalFBOrderCommand(getType(), (FB) ref, IndexUpDown.UP),
				ref -> new ChangeInternalFBOrderCommand(getType(), (FB) ref, IndexUpDown.DOWN));
	}

	private FBTypePaletteEntry getFBTypePaletteEntry() {
		final FB fb = getLastSelectedFB();
		return (null != fb) ? (FBTypePaletteEntry) fb.getPaletteEntry() : null;
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
		final IStructuredSelection selection = internalFbsViewer.getStructuredSelection();
		if (selection.isEmpty()) {
			return null;
		}
		return (FB) selection.toList().get(selection.toList().size() - 1);
	}

	private static void configureTableLayout(final Table table) {
		final TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText(FordiacMessages.Name);
		final TableColumn column2 = new TableColumn(table, SWT.LEFT);
		column2.setText(FordiacMessages.Type);
		final TableColumn column3 = new TableColumn(table, SWT.LEFT);
		column3.setText(FordiacMessages.Comment);
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(2, 30, true));
		layout.addColumnData(new ColumnWeightData(2, 30, true));
		layout.addColumnData(new ColumnWeightData(1, 20, true));
		table.setLayout(layout);
	}

	private CellEditor[] createCellEditors(final Table table) {
		final TextCellEditor fbNameEditor = new TextCellEditor(table);
		((Text) fbNameEditor.getControl()).addVerifyListener(new IdentifierVerifyListener());

		fbTypeEditor = new InternalFBNewInstanceCellEditor(table, SWT.ON_TOP, true);
		fbTypeEditor.setPalette(getPalette());
		fbTypeEditor.getMenuButton().dispose();

		return new CellEditor[] { fbNameEditor, fbTypeEditor, new TextCellEditor(table), new TextCellEditor(table),
				new TextCellEditor(table) };
	}

	@Override
	protected Object getInputType(final Object input) {
		return BaseFBFilter.getFBTypeFromSelectedElement(input);
	}

	@Override
	protected void setInputCode() {
		internalFbsViewer.setCellModifier(null);
	}

	@Override
	public void refresh() {
		final CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			internalFbsViewer.setInput(getType().getInternalFbs());
		}
		commandStack = commandStackBuffer;
	}

	@Override
	protected void setInputInit() {
		internalFbsViewer.setCellEditors(createCellEditors(internalFbsViewer.getTable()));
	}

	private static class InternalFBNewInstanceCellEditor extends NewInstanceCellEditor {

		public InternalFBNewInstanceCellEditor(final Table parent, final int style, final boolean insideCell) {
			super(parent, style, insideCell);
		}

		@Override
		public void activate(final ColumnViewerEditorActivationEvent activationEvent) {
			final ViewerCell cell = (ViewerCell) activationEvent.getSource();
			final TableItem item = (TableItem) cell.getViewerRow().getItem();
			final Rectangle rect = item.getBounds(cell.getVisualIndex());
			final Point screenPos = item.getParent().toDisplay(new Point(rect.x, rect.y));
			getControl().setBounds(screenPos.x, screenPos.y, rect.width, rect.height);
			super.activate(activationEvent);
		}

	}

	private final class InternalFBsCellModifier implements ICellModifier {
		@Override
		public boolean canModify(final Object element, final String property) {
			return true;
		}

		@Override
		public Object getValue(final Object element, final String property) {
			final FB fb = (FB) element;
			switch (property) {
			case FB_NAME:
				return fb.getName();
			case FB_TYPE:
				return fb.getTypeName();
			case FB_COMMENT:
				return fb.getComment();
			default:
				return ""; //$NON-NLS-1$
			}
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			final TableItem tableItem = (TableItem) element;
			final FB fb = (FB) tableItem.getData();
			Command cmd = null;
			switch (property) {
			case FB_NAME:
				cmd = new ChangeNameCommand(fb, value.toString());
				break;
			case FB_TYPE:
				final FBTypePaletteEntry fbTypeEntry = (FBTypePaletteEntry) value;
				if (null == fbTypeEntry) {
					return;
				}
				cmd = new ChangeFbTypeCommand(fb, fbTypeEntry);
				break;
			default:
				cmd = new ChangeCommentCommand(fb, value.toString());
				break;
			}

			executeCommand(cmd);
			internalFbsViewer.refresh(fb);
		}
	}

	@Override
	public TableViewer getViewer() {
		return internalFbsViewer;
	}

	public Object getEntry(final int index) {
		return getType().getInternalFbs().get(index);
	}

	@Override
	public void addEntry(final Object entry, final int index, final CompoundCommand cmd) {
		if (entry instanceof FB) {
			final FBType fbTypeEntry = (FBType) entry;
			cmd.add(new InsertFBCommand(getType().getInternalFbs(), fbTypeEntry, index));
		}
	}

	@Override
	public Object removeEntry(final int index, final CompoundCommand cmd) {
		final FB fbEntry = (FB) getEntry(index);
		cmd.add(new DeleteInternalFBCommand(getType(), fbEntry));
		return fbEntry;
	}

	@Override
	public void executeCompoundCommand(final CompoundCommand cmd) {
		executeCommand(cmd);
		getViewer().refresh();
	}
}

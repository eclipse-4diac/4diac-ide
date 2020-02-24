/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
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
 *   Alois Zoitl - Harmonized look and feel, added multi line selection
 *   Virendra Ashiwal, Bianca Wiesmayr
 *     - added "[none]" as showing text in ECC->State->Property when no
 *       algorithm is selected
 *     - change TransitionViewer to table and make it editable
 *   Alois Zoitl - extracted helper for ComboCellEditors that unfold on activation
 *   Bianca Wiesmayr - externalized code from StateSection and cleanup
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.widgets;

import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeActionOrderCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeAlgorithmCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeOutputCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateECActionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteECActionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ActionContentProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ECCContentAndLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class ActionEditingComposite {
	private static class ActionListLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof ECAction) {
				switch (columnIndex) {
				case ACTION_COLUMN_ALGORITHM:
					return (null != ((ECAction) element).getAlgorithm()) ? ((ECAction) element).getAlgorithm().getName()
							: ECCContentAndLabelProvider.EMPTY_FIELD;
				case ACTION_COLUMN_EVENT:
					return (null != ((ECAction) element).getOutput()) ? ((ECAction) element).getOutput().getName()
							: ECCContentAndLabelProvider.EMPTY_FIELD;
				default:
					break;
				}
			}
			return ECCContentAndLabelProvider.EMPTY_FIELD;
		}
	}

	private class ActionViewerCellModifier implements ICellModifier {
		@Override
		public boolean canModify(final Object element, final String property) {
			return true;
		}

		@Override
		public Object getValue(final Object element, final String property) {
			ECAction selectedAction = (ECAction) element;
			switch (property) {
			case ACTION_ALGORITHM:
				List<Algorithm> algorithms = ECCContentAndLabelProvider.getAlgorithms(getBasicFBType());
				return Integer.valueOf(
						(null != selectedAction.getAlgorithm()) ? algorithms.indexOf(selectedAction.getAlgorithm())
								: algorithms.size());
			case ACTION_EVENT:
				List<String> events = ECCContentAndLabelProvider.getOutputEventNames(getBasicFBType());
				return Integer.valueOf(
						(null != selectedAction.getOutput()) ? events.indexOf(selectedAction.getOutput().getName())
								: events.size());
			default:
				return ""; //$NON-NLS-1$
			}
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			TableItem tableItem = (TableItem) element;
			ECAction selectedAction = (ECAction) tableItem.getData();
			int selected = ((Integer) value).intValue();
			Command cmd = null;

			switch (property) {
			case ACTION_ALGORITHM:
				List<Algorithm> algorithms = ECCContentAndLabelProvider.getAlgorithms(getBasicFBType());
				Algorithm alg = (selected < algorithms.size()) ? algorithms.get(selected) : null;
				cmd = new ChangeAlgorithmCommand(selectedAction, alg);
				break;
			case ACTION_EVENT:
				List<Event> events = ECCContentAndLabelProvider.getOutputEvents(getBasicFBType());
				Event ev = ((0 <= selected) && (selected < events.size())) ? events.get(selected) : null;
				cmd = new ChangeOutputCommand(selectedAction, ev);
				break;
			default:
				break;
			}
			if ((null != cmd) && (null != commandStack)) {
				commandStack.execute(cmd);
				refresh();
			}
		}

	}

	private static final String ACTION_ALGORITHM = "Algorithm"; //$NON-NLS-1$
	private static final String ACTION_EVENT = "Event"; //$NON-NLS-1$
	private static final int ACTION_COLUMN_ALGORITHM = 0;
	private static final int ACTION_COLUMN_EVENT = 1;

	private TableViewer actionViewer;
	private AddDeleteReorderListWidget actionMgmButtons;
	private TabbedPropertySheetWidgetFactory actionWidgetFactory;
	private Group actionGroup;

	private CommandStack commandStack;
	private CommandExecutor commandExecutor; // the parent section
	private ECState type;

	public ActionEditingComposite(Composite parent, TabbedPropertySheetWidgetFactory actionWidgetFactory,
			CommandExecutor commandExecutor) {
		this.actionWidgetFactory = actionWidgetFactory;
		this.commandExecutor = commandExecutor;
		actionGroup = actionWidgetFactory.createGroup(parent, Messages.ActionEditingComposite_Actions);
		commandStack = null;
		type = null;
		createGroupLayout();

	}

	private void createGroupLayout() {
		actionGroup.setLayout(new GridLayout(2, false));
		actionGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		actionMgmButtons = new AddDeleteReorderListWidget();
		actionMgmButtons.createControls(actionGroup, actionWidgetFactory);

		actionViewer = TableWidgetFactory.createTableViewer(actionGroup);
		configureActionTableLayout(actionViewer.getTable());
		actionViewer.setContentProvider(new ActionContentProvider());
		actionViewer.setLabelProvider(new ActionListLabelProvider());

		actionMgmButtons.bindToTableViewer(actionViewer, commandExecutor,
				ref -> new CreateECActionCommand(LibraryElementFactory.eINSTANCE.createECAction(), type),
				ref -> new DeleteECActionCommand((ECAction) ref),
				ref -> new ChangeActionOrderCommand(type, (ECAction) ref, true),
				ref -> new ChangeActionOrderCommand(type, (ECAction) ref, false));
	}

	private void configureActionTableLayout(final Table table) {
		new TableColumn(table, SWT.LEFT).setText(Messages.ActionEditingComposite_ConfigureActionTableLayout_Algorithm);
		new TableColumn(table, SWT.LEFT).setText(Messages.ActionEditingComposite_ConfigureActionTableLayout_Event);

		TableLayout tabLayout = new TableLayout();
		tabLayout.addColumnData(new ColumnWeightData(1, 50));
		tabLayout.addColumnData(new ColumnWeightData(2, 50));

		table.setLayout(tabLayout);
		actionViewer.setColumnProperties(new String[] { ACTION_ALGORITHM, ACTION_EVENT });
	}

	private CellEditor[] createActionViewerCellEditors(Table table) {
		BasicFBType fbType = getBasicFBType();
		return new CellEditor[] {
				ComboBoxWidgetFactory.createComboBoxCellEditor(table,
						ECCContentAndLabelProvider.getAlgorithmNames(fbType).toArray(new String[0]), SWT.READ_ONLY),
				ComboBoxWidgetFactory.createComboBoxCellEditor(table,
						ECCContentAndLabelProvider.getOutputEventNames(fbType).toArray(new String[0]), SWT.READ_ONLY) };
	}

	private BasicFBType getBasicFBType() {
		return type.getECC().getBasicFBType();
	}

	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			actionViewer.setInput(type);
		}
		commandStack = commandStackBuffer;
	}

	public void setTypeAndCommandStack(ECState type, CommandStack commandStack) {
		this.type = type;
		this.commandStack = commandStack;

		// if the selected state is the start state disable adding actions
		actionMgmButtons.setCreateButtonEnablement(!type.isStartState());

		// have to do that here, because now we have a valid type
		actionViewer.setCellEditors(createActionViewerCellEditors(actionViewer.getTable()));
		actionViewer.setCellModifier(new ActionViewerCellModifier());
	}
}

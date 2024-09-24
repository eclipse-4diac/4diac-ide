/*******************************************************************************
 * Copyright (c) 2015, 2023 fortiss GmbH, Johannes Kepler University Linz
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
 *   Alois Zoitl - updated for new adapter FB handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.widgets;

import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeConditionEventCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeConditionExpressionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeECTransitionCommentCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeTransitionDestinationCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeTransitionPriorityCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateTransitionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteTransitionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ECCContentAndLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.StateContentProvider;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
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
import org.eclipse.jface.viewers.TextCellEditor;
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

public class TransitionEditingComposite {
	private static class TransitionListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			if (element instanceof final ECTransition ecTransition) {
				switch (columnIndex) {
				case TRANSITION_COLUMN_PRIORITY:
					return Integer.toString(ecTransition.getPriority());

				case TRANSITION_COLUMN_DESTINATION:
					if (null != ecTransition.getDestination()) {
						return ecTransition.getDestination().getName();
					}
					break;

				case TRANSITION_COLUMN_EVENT:
					if (null != ecTransition.getConditionEvent()) {
						return ECCContentAndLabelProvider.getEventName(ecTransition.getConditionEvent());
					}
					break;

				case TRANSITION_COLUMN_CONDITION:
					return ecTransition.getConditionExpression();

				case TRANSITION_COLUMN_COMMENT:
					return ecTransition.getComment();

				default:
					break;
				}
			}
			return ECCContentAndLabelProvider.EMPTY_FIELD;
		}
	}

	private class TransitionViewerCellModifier implements ICellModifier {
		@Override
		public boolean canModify(final Object element, final String property) {
			return !property.equals(TRANSITION_PRIORITY); // modify all other columns
		}

		@Override
		public Object getValue(final Object element, final String property) {
			final ECTransition selectedTransition = (ECTransition) element;
			return switch (property) {
			case TRANSITION_DESTINATION: // int for Combobox
			{
				final List<String> dest = ECCContentAndLabelProvider.getStateNames(getBasicFBType());
				yield Integer.valueOf((null != selectedTransition.getDestination())
						? dest.indexOf(selectedTransition.getDestination().getName())
						: dest.size());
			}
			case TRANSITION_EVENT: {
				final List<String> events = ECCContentAndLabelProvider
						.getTransitionConditionEventNames(getBasicFBType());
				yield Integer.valueOf((null != selectedTransition.getConditionEvent())
						? events.indexOf(selectedTransition.getConditionEvent().getName())
						: events.size());
			}
			case TRANSITION_CONDITION: // String for TextCellEditor
				yield selectedTransition.getConditionExpression();
			case TRANSITION_COMMENT:
				yield selectedTransition.getComment();
			default:
				yield ""; //$NON-NLS-1$
			};
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			final TableItem tableItem = (TableItem) element;
			final ECTransition selectedTransition = (ECTransition) tableItem.getData();

			Command cmd = null;
			switch (property) {
			case TRANSITION_DESTINATION:
				final int selectedDest = ((Integer) value).intValue();
				final List<ECState> destinations = ECCContentAndLabelProvider.getStates(getBasicFBType());
				final ECState dest = ((0 <= selectedDest) && (selectedDest < destinations.size()))
						? destinations.get(selectedDest)
						: null;
				cmd = new ChangeTransitionDestinationCommand(selectedTransition, dest);
				break;

			case TRANSITION_EVENT:
				final int selectedEv = ((Integer) value).intValue();
				final List<String> events = ECCContentAndLabelProvider
						.getTransitionConditionEventNames(getBasicFBType());
				final String ev = ((0 <= selectedEv) && (selectedEv < events.size())) ? events.get(selectedEv) : null;
				cmd = new ChangeConditionEventCommand(selectedTransition, ev);
				break;

			case TRANSITION_COMMENT:
				cmd = new ChangeECTransitionCommentCommand(selectedTransition, (String) value);
				break;

			case TRANSITION_CONDITION:
				cmd = new ChangeConditionExpressionCommand(selectedTransition, (String) value);
				break;

			default:
				break;
			}
			if ((null != cmd) && (null != commandStack)) {
				commandStack.execute(cmd);
				refresh();
			}
		}

		private void refresh() {
			transitionsOutViewer.setInput(type);
		}

	}

	private static final String TRANSITION_PRIORITY = "TransitionPriority"; //$NON-NLS-1$
	private static final String TRANSITION_DESTINATION = "TransitionDestination"; //$NON-NLS-1$
	private static final String TRANSITION_EVENT = "TransitionEvent"; //$NON-NLS-1$
	private static final String TRANSITION_CONDITION = "TransitionCondition"; //$NON-NLS-1$
	private static final String TRANSITION_COMMENT = "TransitionComment"; //$NON-NLS-1$

	private static final int TRANSITION_COLUMN_PRIORITY = 0;
	private static final int TRANSITION_COLUMN_DESTINATION = 1;
	private static final int TRANSITION_COLUMN_EVENT = 2;
	private static final int TRANSITION_COLUMN_CONDITION = 3;
	private static final int TRANSITION_COLUMN_COMMENT = 4;

	private TableViewer transitionsOutViewer;
	private final TabbedPropertySheetWidgetFactory transitionWidgetFactory;
	private final Group transitionGroup;

	private final CommandExecutor commandExecutor; // the parent section
	private ECState type;
	private CommandStack commandStack;

	public TransitionEditingComposite(final Composite parent,
			final TabbedPropertySheetWidgetFactory transitionWidgetFactory, final CommandExecutor commandExecutor) {
		this.transitionWidgetFactory = transitionWidgetFactory;
		this.transitionGroup = transitionWidgetFactory.createGroup(parent,
				Messages.TransitionEditingComposite_TransitionsFromSelectedState);
		this.commandExecutor = commandExecutor;
		type = null;
		commandStack = null;
		createGroupLayout();
	}

	private void createGroupLayout() {
		transitionGroup.setLayout(new GridLayout(2, false));
		transitionGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		final AddDeleteReorderListWidget transitionMgmButtons = new AddDeleteReorderListWidget();
		transitionMgmButtons.createControls(transitionGroup, transitionWidgetFactory);

		transitionsOutViewer = TableWidgetFactory.createTableViewer(transitionGroup);
		configureTransitionTableLayout(transitionsOutViewer.getTable());
		transitionsOutViewer.setContentProvider(new StateContentProvider());
		transitionsOutViewer.setLabelProvider(new TransitionListLabelProvider());

		transitionMgmButtons.bindToTableViewer(transitionsOutViewer, commandExecutor,
				ref -> new CreateTransitionCommand(type, getBasicFBType().getECC().getStart(), null),
				ref -> new DeleteTransitionCommand((ECTransition) ref),
				ref -> new ChangeTransitionPriorityCommand(type, (ECTransition) ref, true),
				ref -> new ChangeTransitionPriorityCommand(type, (ECTransition) ref, false));
	}

	private void configureTransitionTableLayout(final Table table) {
		addColumn(table); // creates a table column without headline for the order numbering
		addColumn(table).setText(Messages.TransitionEditingComposite_ConfigureTransitionTableLayout_Destination);
		addColumn(table).setText(Messages.TransitionEditingComposite_ConfigureTransitionTableLayout_Event);
		addColumn(table).setText(Messages.TransitionEditingComposite_ConfigureTransitionTableLayout_Condition);
		addColumn(table).setText(Messages.TransitionEditingComposite_ConfigureTransitionTableLayout_Comment);

		final TableLayout tabLayout = new TableLayout();
		tabLayout.addColumnData(new ColumnWeightData(1, 50));
		tabLayout.addColumnData(new ColumnWeightData(2, 50));
		tabLayout.addColumnData(new ColumnWeightData(3, 50));
		tabLayout.addColumnData(new ColumnWeightData(4, 50));
		tabLayout.addColumnData(new ColumnWeightData(5, 50));

		table.setLayout(tabLayout);

		transitionsOutViewer.setColumnProperties(new String[] { TRANSITION_PRIORITY, TRANSITION_DESTINATION,
				TRANSITION_EVENT, TRANSITION_CONDITION, TRANSITION_COMMENT });
	}

	private static TableColumn addColumn(final Table table) {
		return new TableColumn(table, SWT.LEFT);
	}

	private CellEditor[] createTransitionViewerCellEditors(final Table table) {
		final BasicFBType fbType = getBasicFBType();
		return new CellEditor[] { null,
				ComboBoxWidgetFactory.createComboBoxCellEditor(table,
						ECCContentAndLabelProvider.getStateNames(fbType).toArray(new String[0]), SWT.READ_ONLY),
				ComboBoxWidgetFactory.createComboBoxCellEditor(table,
						ECCContentAndLabelProvider.getTransitionConditionEventNames(fbType).toArray(new String[0]),
						SWT.READ_ONLY),
				new TextCellEditor(table), new TextCellEditor(table) };
	}

	private BasicFBType getBasicFBType() {
		return type.getECC().getBasicFBType();
	}

	public void refresh() {
		final CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			transitionsOutViewer.setInput(type);
		}
		commandStack = commandStackBuffer;
	}

	public void setTypeAndCommandStack(final ECState type, final CommandStack commandStack) {
		this.type = type;
		this.commandStack = commandStack;

		// have to do that here, because now we have a valid type
		transitionsOutViewer.setCellEditors(createTransitionViewerCellEditors(transitionsOutViewer.getTable()));
		transitionsOutViewer.setCellModifier(new TransitionViewerCellModifier());
	}
}
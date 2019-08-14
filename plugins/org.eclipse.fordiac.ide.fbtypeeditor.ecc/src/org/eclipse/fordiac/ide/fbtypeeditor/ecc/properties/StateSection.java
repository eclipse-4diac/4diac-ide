/*******************************************************************************
 * Copyright (c) 2015 - 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized look and feel, added multi line selection  
 *   Virendra Ashiwal - added "[none]" as showing text in ECC->State->Property when no
 *   					algorithm is selected
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import java.util.List;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeActionOrderCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeAlgorithmCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeOutputCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeTransitionPriorityCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateECActionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.DeleteECActionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ActionContentProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.ECCContentAndLabelProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider.StateContentProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class StateSection extends AbstractECSection {

	private static final String ACTION_ALGORITHM = "Algorithm"; //$NON-NLS-1$
	private static final String ACTION_EVENT = "Event"; //$NON-NLS-1$

	private Text nameText;
	private Text commentText;
	private TableViewer actionViewer;
	private AddDeleteReorderListWidget actionMgmButtons;
	private TreeViewer transitionsOutViewer;

	private Button transitionDown;
	private Button transitionUp;

	private static class ActionListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof ECAction) {
				switch (columnIndex) {
				case 0:
					if (null != ((ECAction) element).getAlgorithm()) {
						return ((ECAction) element).getAlgorithm().getName();
					}
					break;
				case 1:
					if (null != ((ECAction) element).getOutput()) {
						return ((ECAction) element).getOutput().getName();
					}
					break;
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
			// only allow editing if only one element is selected and if the selected is
			// also the
			// element to be requested for editing. This improves the usability of
			// multi-line selection.
			return 1 == actionViewer.getStructuredSelection().size()
					&& element.equals(actionViewer.getStructuredSelection().getFirstElement());
		}

		@Override
		public Object getValue(final Object element, final String property) {
			ECAction selectedAction = (ECAction) element;
			switch (property) {
			case ACTION_ALGORITHM:
				List<Algorithm> algorithms = ECCContentAndLabelProvider.getAlgorithms(getBasicFBType());
				return (null != selectedAction.getAlgorithm()) ? algorithms.indexOf(selectedAction.getAlgorithm())
						: algorithms.size();
			case ACTION_EVENT:
				List<String> events = ECCContentAndLabelProvider.getOutputEventNames(getBasicFBType());
				return (null != selectedAction.getOutput()) ? events.indexOf(selectedAction.getOutput().getName())
						: events.size();
			default:
				return ""; //$NON-NLS-1$
			}
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			TableItem tableItem = (TableItem) element;
			ECAction selectedAction = (ECAction) tableItem.getData();
			int selected = (int) value;
			Command cmd = null;

			switch (property) {
			case ACTION_ALGORITHM:
				List<Algorithm> algorithms = ECCContentAndLabelProvider.getAlgorithms(getBasicFBType());
				Algorithm alg = null;
				if (selected < algorithms.size()) {
					alg = algorithms.get(selected);
				}
				cmd = new ChangeAlgorithmCommand(selectedAction, alg);
				break;
			case ACTION_EVENT:
				List<Event> events = ECCContentAndLabelProvider.getOutputEvents(getBasicFBType());
				Event ev = null;
				if (0 <= selected && selected < events.size()) {
					ev = events.get(selected);
				}
				cmd = new ChangeOutputCommand(selectedAction, ev);
				break;
			default:
				break;
			}
			if ((null != cmd) && (null != commandStack)) {
				executeCommand(cmd);
				refresh();
			}
		}
	}

	@Override
	protected ECState getType() {
		return (ECState) type;
	}

	private BasicFBType getBasicFBType() {
		return getType().getECC().getBasicFBType();
	}

	@Override
	protected Object getInputType(Object input) {
		if (input instanceof ECStateEditPart) {
			return ((ECStateEditPart) input).getCastedModel();
		}
		if (input instanceof ECState) {
			return input;
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(2, true));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		createNameCommentControls(parent);
		createActionTransitionControls(parent);
	}

	public void createNameCommentControls(final Composite parent) {
		Composite nameComposite = getWidgetFactory().createComposite(parent);
		nameComposite.setLayout(new GridLayout(2, false));
		nameComposite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(nameComposite, "Name:");
		nameText = createGroupText(nameComposite, true);
		nameText.addVerifyListener(new IdentifierVerifyListener());
		nameText.addListener(SWT.Modify, e -> {
			removeContentAdapter();
			executeCommand(new ChangeNameCommand(getType(), nameText.getText()));
			addContentAdapter();
		});

		Composite commentComposite = getWidgetFactory().createComposite(parent);
		commentComposite.setLayout(new GridLayout(2, false));
		commentComposite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(commentComposite, "Comment:");
		commentText = createGroupText(commentComposite, true);
		commentText.addListener(SWT.Modify, e -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});
	}

	public void createActionTransitionControls(final Composite parent) {
		Group actionGroup = getWidgetFactory().createGroup(parent, "Actions");
		actionGroup.setLayout(new GridLayout(2, false));
		actionGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		actionMgmButtons = new AddDeleteReorderListWidget();
		actionMgmButtons.createControls(actionGroup, getWidgetFactory());
		createActionViewer(actionGroup);
		actionMgmButtons.bindToTableViewer(actionViewer, this,
				ref -> new CreateECActionCommand(LibraryElementFactory.eINSTANCE.createECAction(), getType()),
				ref -> new DeleteECActionCommand((ECAction) ref),
				ref -> new ChangeActionOrderCommand(getType(), (ECAction) ref, true),
				ref -> new ChangeActionOrderCommand(getType(), (ECAction) ref, false));

		Group transitionGroup = getWidgetFactory().createGroup(parent, "Outgoing Transitions");
		transitionGroup.setLayout(new GridLayout(2, false));
		transitionGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite buttonComp = new Composite(transitionGroup, SWT.NONE);
		createTransitionViewer(transitionGroup);
		createTransitionButtons(buttonComp); // buttons have to be created after the transition viewer so that selection
												// handlers can be bound to it
	}

	private void createTransitionButtons(Composite parent) {
		parent.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false));
		parent.setLayout(new FillLayout(SWT.VERTICAL));
		createTransitionUpButton(parent);
		createTransitionDownButton(parent);
		transitionsOutViewer.addSelectionChangedListener(
				ev -> setTransitionButtonEnablement(!transitionsOutViewer.getSelection().isEmpty()));

		// initially nothing should be selected therefore deactivate the buttons
		setTransitionButtonEnablement(false);
	}

	private void setTransitionButtonEnablement(boolean somethingSelected) {
		transitionUp.setEnabled(somethingSelected);
		transitionDown.setEnabled(somethingSelected);
	}

	private void createActionViewer(Group actionGroup) {
		actionViewer = TableWidgetFactory.createTableViewer(actionGroup);
		configureActionTableLayout(actionViewer.getTable());

		actionViewer.setContentProvider(new ActionContentProvider());
		actionViewer.setLabelProvider(new ActionListLabelProvider());
	}

	private void configureActionTableLayout(final Table table) {
		TableColumn tc = new TableColumn(table, SWT.LEFT);
		tc.setText("Algorithm");

		tc = new TableColumn(table, SWT.LEFT);
		tc.setText("Event");

		TableLayout tabLayout = new TableLayout();
		tabLayout.addColumnData(new ColumnWeightData(1, 50));
		tabLayout.addColumnData(new ColumnWeightData(2, 50));
		table.setLayout(tabLayout);

		actionViewer.setColumnProperties(new String[] { ACTION_ALGORITHM, ACTION_EVENT });
	}

	private void createTransitionViewer(Group transactionGroup) {
		transitionsOutViewer = new TreeViewer(transactionGroup, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.heightHint = 150;
		gridData.widthHint = 80;
		transitionsOutViewer.getTree().setLayoutData(gridData);
		transitionsOutViewer.setContentProvider(new StateContentProvider());
		transitionsOutViewer.setLabelProvider(new AdapterFactoryLabelProvider(getAdapterFactory()));
	}

	@SuppressWarnings("unchecked")
	private void createTransitionDownButton(Composite buttonComp) {
		transitionDown = getWidgetFactory().createButton(buttonComp, "", SWT.ARROW | SWT.DOWN); //$NON-NLS-1$
		transitionDown.setToolTipText("Move transition down");
		transitionDown.addListener(SWT.Selection, e -> {
			if (!transitionsOutViewer.getStructuredSelection().isEmpty()) {
				CompoundCommand cmd = new CompoundCommand();
				transitionsOutViewer.getStructuredSelection().toList().forEach(
						elem -> cmd.add(new ChangeTransitionPriorityCommand(getType(), (ECTransition) elem, false)));
				executeCommand(cmd);
				transitionsOutViewer.refresh();
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void createTransitionUpButton(Composite buttonComp) {
		transitionUp = getWidgetFactory().createButton(buttonComp, "", SWT.ARROW | SWT.UP); //$NON-NLS-1$
		transitionUp.setToolTipText("Move transition up");
		transitionUp.addListener(SWT.Selection, e -> {
			if (!transitionsOutViewer.getStructuredSelection().isEmpty()) {
				CompoundCommand cmd = new CompoundCommand();
				transitionsOutViewer.getStructuredSelection().toList().forEach(
						elem -> cmd.add(new ChangeTransitionPriorityCommand(getType(), (ECTransition) elem, true)));
				executeCommand(cmd);
				transitionsOutViewer.refresh();
			}
		});
	}

	@Override
	protected void setInputCode() {
		commentText.setEnabled(false);
		nameText.setEnabled(false);
		transitionsOutViewer.setInput(null);
		actionViewer.setInput(null);
	}

	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
			transitionsOutViewer.setInput(getType());
			actionViewer.setInput(getType());
		}
		commandStack = commandStackBuffer;
	}

	@Override
	protected void setInputInit() {
		// we have to do this here because at this point in time we have a valid type
		actionViewer.setCellEditors(createActionViewerCellEditors(actionViewer.getTable()));
		actionViewer.setCellModifier(new ActionViewerCellModifier());

		// if the selected state is the start state disable adding actions
		actionMgmButtons.setCreateButtonEnablement(!getType().isStartState());
	}

	private CellEditor[] createActionViewerCellEditors(Table table) {
		BasicFBType fbType = getBasicFBType();
		return new CellEditor[] {
				new ComboBoxCellEditor(table,
						ECCContentAndLabelProvider.getAlgorithmNames(fbType).toArray(new String[0]), SWT.READ_ONLY),
				new ComboBoxCellEditor(table,
						ECCContentAndLabelProvider.getOutputEventNames(fbType).toArray(new String[0]), SWT.READ_ONLY) };
	}

}

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
 *   Alois Zoitl - cleaned command stack handling for property sections
 *   Melanie Winter, Bianca Wiesmayr - modernize and cleanup section
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.properties;

import java.util.Arrays;

import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveEventCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveInterfaceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveParameterCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateOutputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteOutputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.TransactionEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.widgets.InterfaceSelectorButton;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeOutputPrimitiveOrderCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class TransactionSection extends AbstractSection {

	private static final int PARAMETER_COL_WIDTH = 400;
	private static final int EVENT_COL_WIDTH = 200;
	private static final int INDEX_COL_WIDTH = 80;
	private static final int INTERFACE_COL_WIDTH = 30;
	private TableViewer outputPrimitivesViewer;
	private Group outputsGroup;
	private InterfaceSelectorButton interfaceSelector;
	private Composite composite;
	private CCombo eventNameInput;
	private Text parameterNameInput;

	private static final String INTERFACE = ""; //$NON-NLS-1$
	private static final String INDEX = "index"; //$NON-NLS-1$
	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String PARAM = "parameter"; //$NON-NLS-1$

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		final Composite inputsComp = getWidgetFactory().createComposite(parent);
		inputsComp.setLayout(new GridLayout(1, false));
		inputsComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createInputPrimitiveGroup(inputsComp);

		final Composite outputsComp = getWidgetFactory().createComposite(parent);
		outputsComp.setLayout(new GridLayout(1, false));
		outputsComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createOutputsEdit(outputsComp);
		outputPrimitivesViewer.setContentProvider(new TransactionContentProvider());
		TableWidgetFactory.enableCopyPasteCut(tabbedPropertySheetPage);
	}

	private void createInputPrimitiveGroup(final Composite parent) {
		final Group inputsGroup;
		inputsGroup = getWidgetFactory().createGroup(parent, Messages.TransactionSection_InputPrimitive);
		inputsGroup.setLayout(new GridLayout(1, false));
		inputsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		composite = getWidgetFactory().createComposite(inputsGroup);
		composite.setLayout(new GridLayout(5, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.pack();

		interfaceSelector = new InterfaceSelectorButton(composite, cmd -> {
			executeCommand(cmd);
			refresh();
		});

		getWidgetFactory().createCLabel(composite, Messages.ServiceSection_Name);

		eventNameInput = ComboBoxWidgetFactory.createCombo(getWidgetFactory(), composite);
		eventNameInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		eventNameInput.setSize(100, getMinimumHeight());
		eventNameInput.addListener(SWT.Selection, event -> {
			final Command cmd = new ChangePrimitiveEventCommand(getType().getInputPrimitive(),
					eventNameInput.getText());
			executeCommand(cmd);
			eventNameInput.redraw();
		});

		getWidgetFactory().createCLabel(composite, Messages.TransactionSection_Parameter);

		parameterNameInput = new Text(composite, SWT.BORDER);
		parameterNameInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		parameterNameInput.addModifyListener(e -> {
			final Command cmd = new ChangePrimitiveParameterCommand(getType().getInputPrimitive(),
					parameterNameInput.getText());
			executeCommand(cmd);
			parameterNameInput.redraw();
		});
	}

	private static TableViewer createTableViewer(final Group parent) {
		final TableViewer viewer = TableWidgetFactory.createTableViewer(parent);
		viewer.getTable().setLayout(createTableLayout(viewer.getTable()));

		viewer.setColumnProperties(getColumnProperties());
		viewer.setLabelProvider(new TransactionLabelProvider());

		return viewer;
	}

	private CellEditor[] createCellEditors(final Table table) {
		final CellEditor interfaceEditor = new CheckboxCellEditor(table);
		return new CellEditor[] { interfaceEditor, new TextCellEditor(table),
				ComboBoxWidgetFactory.createComboBoxCellEditor(table, getOutputEventNames(), SWT.READ_ONLY),
				new TextCellEditor(table) };

	}

	private String[] getOutputEventNames() {
		return (getType().getServiceSequence().getService().getFBType()).getInterfaceList().getEventOutputs().stream()
				.map(Event::getName).toArray(String[]::new);
	}

	private static String[] getColumnProperties() {
		return new String[] { INTERFACE, INDEX, NAME, PARAM };
	}

	private static Layout createTableLayout(final Table table) {
		final TableColumn interfaceCol = new TableColumn(table, SWT.LEFT);
		interfaceCol.setText(""); //$NON-NLS-1$
		final TableColumn indexCol = new TableColumn(table, SWT.LEFT);
		indexCol.setText(Messages.TransactionSection_CreateTableLayout_Index);
		final TableColumn eventCol = new TableColumn(table, SWT.LEFT);
		eventCol.setText(FordiacMessages.Event);
		final TableColumn paramCol = new TableColumn(table, SWT.LEFT);
		paramCol.setText(Messages.TransactionSection_CreateTableLayout_Parameter);
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(INTERFACE_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(INDEX_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(EVENT_COL_WIDTH));
		layout.addColumnData(new ColumnPixelData(PARAMETER_COL_WIDTH));
		return layout;
	}

	private void configureButtonList(final AddDeleteReorderListWidget buttons, final TableViewer primitiveViewer) {
		buttons.bindToTableViewer(primitiveViewer, this, ref -> newCreateCommand((OutputPrimitive) ref, true),
				ref -> newDeleteCommand((OutputPrimitive) ref), ref -> newOrderCommand((OutputPrimitive) ref, true),
				ref -> newOrderCommand((OutputPrimitive) ref, false));
	}

	private static Command newOrderCommand(final OutputPrimitive ref, final boolean up) {
		return new ChangeOutputPrimitiveOrderCommand(ref, up);
	}

	private static Command newDeleteCommand(final OutputPrimitive ref) {
		return new DeleteOutputPrimitiveCommand(ref);
	}

	private CreateOutputPrimitiveCommand newCreateCommand(final OutputPrimitive ref, final boolean isLeftInterface) {
		return new CreateOutputPrimitiveCommand(getType(), ref, isLeftInterface);
	}

	private void createOutputsEdit(final Composite parent) {
		outputsGroup = getWidgetFactory().createGroup(parent, getInterfaceNames());
		outputsGroup.setLayout(new GridLayout(2, false));
		outputsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final AddDeleteReorderListWidget buttons = new AddDeleteReorderListWidget();
		buttons.createControls(outputsGroup, getWidgetFactory());
		outputPrimitivesViewer = createTableViewer(outputsGroup);
		configureButtonList(buttons, outputPrimitivesViewer);
	}

	private String getInterfaceNames() {
		if (type == null) {
			return Messages.TransactionSection_OutputPrimitives;
		}
		return collectOutputPrimitiveGroupName();
	}

	private String collectOutputPrimitiveGroupName() {
		final StringBuilder sb = new StringBuilder();
		sb.append(Messages.TransactionSection_CollectOutputPrimitiveGroupName_PrimitivesRightInterface);
		sb.append(" ("); //$NON-NLS-1$
		sb.append(getRightInterfaceName());
		sb.append(") "); //$NON-NLS-1$
		sb.append(Messages.TransactionSection_CollectOutputPrimitiveGroupName_AndLeftInterface);
		sb.append(" ("); //$NON-NLS-1$
		sb.append(getLeftInterfaceName());
		sb.append(")"); //$NON-NLS-1$
		return sb.toString();
	}

	private String getLeftInterfaceName() {
		final ServiceInterface leftInterface = getType().getServiceSequence().getService().getLeftInterface();
		if (null != leftInterface) {
			return leftInterface.getName();
		}
		return ""; //$NON-NLS-1$
	}

	private String getRightInterfaceName() {
		final ServiceInterface rightInterface = getType().getServiceSequence().getService().getRightInterface();
		if (null != rightInterface) {
			return rightInterface.getName();
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	protected ServiceTransaction getType() {
		return (ServiceTransaction) type;
	}

	@Override
	protected void performRefresh() {
		interfaceSelector.setType(getType().getInputPrimitive());
		outputPrimitivesViewer.setInput(getType());
		outputsGroup.setText(getInterfaceNames());
		fillEventNameInputDropdown();
		eventNameInput.setText(getType().getInputPrimitive().getEvent());
		if (getType().getInputPrimitive().getParameters() == null) {
			parameterNameInput.setText(""); //$NON-NLS-1$
		} else {
			parameterNameInput.setText(getType().getInputPrimitive().getParameters());
		}
	}

	private void fillEventNameInputDropdown() {
		eventNameInput.removeAll();
		final String[] inputEvents = getType().getServiceSequence().getService().getFBType().getInterfaceList()
				.getEventInputs().stream().map(Event::getName).toArray(String[]::new);
		for (final String s : inputEvents) {
			eventNameInput.add(s);
		}
		composite.layout();
	}

	@Override
	protected Object getInputType(final Object input) {
		if (input instanceof final TransactionEditPart transEP) {
			return transEP.getModel();
		}
		if (input instanceof ServiceTransaction) {
			return input;
		}
		return null;
	}

	@Override
	protected void setInputCode() {
		// nothing to do here
	}

	@Override
	protected void setInputInit() {
		outputPrimitivesViewer.setCellEditors(createCellEditors(outputPrimitivesViewer.getTable()));
		outputPrimitivesViewer.setCellModifier(new TransactionCellModifier());
	}

	protected static class TransactionLabelProvider extends LabelProvider implements ITableLabelProvider {
		public static final int INTERFACE_COL_INDEX = 0;
		public static final int INDEX_COL_INDEX = 1;
		public static final int NAME_COL_INDEX = 2;
		public static final int PARAM_COL_INDEX = 3;

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			if (element instanceof final Primitive primitive) {
				switch (columnIndex) {
				case INTERFACE_COL_INDEX:
					return ""; //$NON-NLS-1$
				case INDEX_COL_INDEX:
					return String
							.valueOf(primitive.getServiceTransaction().getOutputPrimitive().indexOf(primitive) + 1);
				case NAME_COL_INDEX:
					return primitive.getEvent();
				case PARAM_COL_INDEX:
					if (primitive.getParameters() == null) {
						return ""; //$NON-NLS-1$
					}
					return primitive.getParameters();
				default:
					break;
				}
			}
			return element.toString();
		}

		@Override
		public Image getColumnImage(final Object object, final int columnIndex) {
			if ((object instanceof final Primitive primitive) && (columnIndex == INTERFACE_COL_INDEX)) {
				if (primitive.getInterface().isLeftInterface()) {
					return FordiacImage.ICON_LEFT_INPUT_PRIMITIVE.getImage();
				}
				return FordiacImage.ICON_RIGHT_INPUT_PRIMITIVE.getImage();
			}
			return null;
		}
	}

	private class TransactionCellModifier implements ICellModifier {
		@Override
		public boolean canModify(final Object element, final String property) {
			return !property.equals(INDEX);
		}

		@Override
		public Object getValue(final Object element, final String property) {
			if (element instanceof final Primitive primitive) {
				switch (property) {
				case INDEX:
					return String
							.valueOf(primitive.getServiceTransaction().getOutputPrimitive().indexOf(primitive) + 1);
				case NAME:
					return getNameOfCurrentEvent(primitive);
				case PARAM:
					if (primitive.getParameters() == null) {
						return ""; //$NON-NLS-1$
					}
					return primitive.getParameters();
				case INTERFACE:
					return Boolean.valueOf(true);
				default:
					break;
				}
			}
			return element;
		}

		@Override
		public void modify(final Object element, final String property, final Object value) {
			final TableItem tableItem = (TableItem) element;
			final OutputPrimitive primitive = (OutputPrimitive) tableItem.getData();
			Command cmd = null;
			switch (property) {
			case NAME:
				final int selectedEv = ((Integer) value).intValue();
				final String[] eventNames = getOutputEventNames();
				cmd = new ChangePrimitiveEventCommand(primitive, eventNames[selectedEv]);
				break;
			case PARAM:
				cmd = new ChangePrimitiveParameterCommand(primitive, value.toString());
				break;
			case INTERFACE:
				if (primitive.getInterface().isLeftInterface()) {
					cmd = new ChangePrimitiveInterfaceCommand(primitive, primitive.getService().getRightInterface());
				} else {
					cmd = new ChangePrimitiveInterfaceCommand(primitive, primitive.getService().getLeftInterface());
				}
				break;
			default:
				break;
			}
			if (null != cmd) {
				executeCommand(cmd);
				refresh();
			}
		}

		private Object getNameOfCurrentEvent(final Primitive primitive) {
			final String event = primitive.getEvent();
			if (event == null) {
				return Integer.valueOf(0);
			}
			final String[] eventNames = getOutputEventNames();
			final int indexOfEvent = Arrays.asList(eventNames).indexOf(event);
			if (indexOfEvent < 0) {
				return Integer.valueOf(0);
			}
			return Integer.valueOf(indexOfEvent);
		}

	}

	protected static class TransactionContentProvider extends ArrayContentProvider {
		@Override
		public Object[] getElements(final Object inputElement) {
			return ((ServiceTransaction) inputElement).getOutputPrimitive().toArray();
		}
	}
}

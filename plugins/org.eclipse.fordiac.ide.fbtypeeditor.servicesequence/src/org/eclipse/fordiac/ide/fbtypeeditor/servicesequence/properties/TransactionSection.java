/*******************************************************************************
 * Copyright (c) 2014, 2017 fortiss GmbH
 * 		 2019, 2021 Johannes Kepler University Linz
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveEventCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveInterfaceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangePrimitiveParameterCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateOutputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteOutputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.TransactionEditPart;
import org.eclipse.fordiac.ide.model.commands.change.ChangeOutputPrimitiveOrderCommand;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.fordiac.ide.util.IdentifierVerifyListener;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class TransactionSection extends AbstractServiceSection {

	private TableViewer leftPrimitivesViewer;
	private TableViewer rightPrimitivesViewer;
	private Group inputsGroup;
	private Group outputsGroup;
	private Button interfaceSelector;
	private static final String NAME = "name"; //$NON-NLS-1$
	private static final String PARAM = "parameter"; //$NON-NLS-1$
	private static final String INTERFACE = "interface"; //$NON-NLS-1$


	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		final Composite section = getWidgetFactory().createComposite(parent);
		section.setLayout(new GridLayout(1, false));
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Composite inputsComp = getWidgetFactory().createComposite(section);
		inputsComp.setLayout(new FillLayout());
		createInputPrimitiveGroup(inputsComp);

		final Composite outputsComp = getWidgetFactory().createComposite(section);
		outputsComp.setLayout(new GridLayout(2, false));
		outputsComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createLeftInterfaceOutputsEdit(outputsComp);
		createRightInterfaceOutputsEdit(outputsComp);

		leftPrimitivesViewer.setContentProvider(new TransactionContentProvider(true));
		rightPrimitivesViewer.setContentProvider(new TransactionContentProvider(false));

		TableWidgetFactory.enableCopyPasteCut(tabbedPropertySheetPage);
	}


	private void createInputPrimitiveGroup(final Composite parent) {
		inputsGroup = getWidgetFactory().createGroup(parent, "Input Primitive");
		inputsGroup.setLayout(new GridLayout(2, true));

		final Text nameOfInput = new Text(inputsGroup, SWT.BORDER);
		nameOfInput.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				// TODO Auto-generated method stub

			}

		});
		interfaceSelector = new Button(inputsGroup, SWT.PUSH);
		interfaceSelector.setText("Interface");

		interfaceSelector.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				final ServiceInterface current = getType().getInputPrimitive().getInterface();
				ServiceInterface other;
				if (current.isLeftInterface()) {
					other = current.getService().getRightInterface();
				} else {
					other = current.getService().getLeftInterface();
				}
				final Command cmd = new ChangePrimitiveInterfaceCommand(getType().getInputPrimitive(), other);
				executeCommand(cmd);
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// nothing to do here
			}

		});
	}


	private void setInputPrimitiveIcon() {
		final InputPrimitive inputPrimitive = getType().getInputPrimitive();
		if (inputPrimitive.getInterface().isLeftInterface()) {
			interfaceSelector.setImage(FordiacImage.ICON_LEFT_INPUT_PRIMITIVE.getImage());
		} else {
			interfaceSelector.setImage(FordiacImage.ICON_RIGHT_INPUT_PRIMITIVE.getImage());
		}
	}

	private TableViewer createTableViewer(final Group parent, boolean isLeftViewer) {
		final TableViewer viewer = TableWidgetFactory.createTableViewer(parent);
		viewer.getTable().setLayout(createTableLayout(viewer.getTable()));
		viewer.setCellEditors(createCellEditors(viewer.getTable()));
		viewer.setColumnProperties(getColumnProperties());
		viewer.setLabelProvider(new TransactionLabelProvider());
		viewer.setCellModifier(new TransactionCellModifier(isLeftViewer));
		return viewer;
	}

	private static CellEditor[] createCellEditors(Table table) {
		final TextCellEditor editor = new TextCellEditor(table);
		((Text) editor.getControl()).addVerifyListener(new IdentifierVerifyListener());
		return new CellEditor[] { editor, new TextCellEditor(table), new TextCellEditor(table) };

	}


	private static String[] getColumnProperties() {
		return new String[] { NAME, PARAM, INTERFACE };
	}

	private static Layout createTableLayout(final Table table) {
		final TableColumn eventColumn = new TableColumn(table, SWT.LEFT);
		eventColumn.setText(FordiacMessages.Event);
		final TableColumn paramColumn = new TableColumn(table, SWT.LEFT);
		paramColumn.setText("Parameter");
		final TableColumn interfaceColumn = new TableColumn(table, SWT.LEFT);
		interfaceColumn.setText(FordiacMessages.Interface);
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(80));
		layout.addColumnData(new ColumnPixelData(200));
		layout.addColumnData(new ColumnPixelData(200));
		return layout;
	}

	private void createLeftInterfaceOutputsEdit(final Composite parent) {
		inputsGroup = getWidgetFactory().createGroup(parent, getLeftInterfaceGroupName());
		inputsGroup.setLayout(new GridLayout(2, false));
		inputsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final AddDeleteReorderListWidget buttons = new AddDeleteReorderListWidget();
		buttons.createControls(inputsGroup, getWidgetFactory());
		leftPrimitivesViewer = createTableViewer(inputsGroup, true);
		configureButtonList(buttons, leftPrimitivesViewer, true);
	}

	private String getLeftInterfaceGroupName() {
		if (type == null) {
			return "Output Primitives at Left Interface";
		}
		final ServiceInterface leftInterface = getType().getServiceSequence().getService().getLeftInterface();
		if (null != leftInterface) {
			return "Output Primitives at Left Interface ("
					+ leftInterface.getName() + ")";
		}
		return "";
	}

	private void configureButtonList(final AddDeleteReorderListWidget buttons, final TableViewer primitiveViewer,
			final boolean isLeftInterface) {
		buttons.bindToTableViewer(primitiveViewer, this,
				ref -> newCreateCommand((OutputPrimitive) ref, isLeftInterface),
				ref -> newDeleteCommand((OutputPrimitive) ref), ref -> newOrderCommand((OutputPrimitive) ref, true),
				ref -> newOrderCommand((OutputPrimitive) ref, false));
	}

	private static Command newOrderCommand(OutputPrimitive ref, boolean up) {
		return new ChangeOutputPrimitiveOrderCommand(ref, up);
	}

	private static Command newDeleteCommand(OutputPrimitive ref) {
		return new DeleteOutputPrimitiveCommand(ref);
	}

	private CreateOutputPrimitiveCommand newCreateCommand(final OutputPrimitive ref,
			final boolean isLeftInterface) {
		return new CreateOutputPrimitiveCommand(getType(), ref, isLeftInterface);
	}


	private void createRightInterfaceOutputsEdit(final Composite parent) {
		outputsGroup = getWidgetFactory().createGroup(parent, getRightInterfaceGroupName());
		outputsGroup.setLayout(new GridLayout(2, false));
		outputsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final AddDeleteReorderListWidget buttons = new AddDeleteReorderListWidget();
		buttons.createControls(outputsGroup, getWidgetFactory());
		rightPrimitivesViewer = createTableViewer(outputsGroup, false);
		configureButtonList(buttons, rightPrimitivesViewer, false);
	}

	private String getRightInterfaceGroupName() {
		if (type == null) {
			return "Output Primitives at Right Interface";
		}
		return "Output Primitives at Right Interface ("
		+ ((Service) getType().eContainer().eContainer()).getRightInterface().getName() + ")";
	}

	@Override
	protected ServiceTransaction getType() {
		return (ServiceTransaction) type;
	}

	@Override
	public void refresh() {
		super.refresh();
		final CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			Display.getDefault().asyncExec(() -> {
				leftPrimitivesViewer.setInput(getType());
				rightPrimitivesViewer.setInput(getType());
				inputsGroup.setText(getLeftInterfaceGroupName());
				outputsGroup.setText(getRightInterfaceGroupName());
				setInputPrimitiveIcon();
			});
		}
		commandStack = commandStackBuffer;
	}




	@Override
	protected Object getInputType(final Object input) {
		if (input instanceof TransactionEditPart) {
			return ((TransactionEditPart) input).getCastedModel();
		}
		if (input instanceof ServiceTransaction) {
			return input;
		}
		return null;
	}

	@Override
	protected void setInputCode() {
		// evtl hier buttons deaktivieren
	}

	@Override
	protected void setInputInit() {
		// currently nothing to be done here
	}

	protected static class TransactionLabelProvider extends LabelProvider implements ITableLabelProvider {
		public static final int NAME_COL_INDEX = 0;
		public static final int PARAM_COL_INDEX = 1;
		public static final int INTERFACE_COL_INDEX = 2;

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			if (element instanceof Primitive) {
				final Primitive primitive = (Primitive) element;
				switch (columnIndex) {
				case NAME_COL_INDEX:
					return primitive.getEvent();
				case PARAM_COL_INDEX:
					return primitive.getParameters();
				case INTERFACE_COL_INDEX:
					return primitive.getInterface().getName();
				default:
					break;
				}
			}
			return element.toString();
		}

		@Override
		public Image getColumnImage(final Object object, final int columnIndex) {
			return null;
		}
	}

	private class TransactionCellModifier implements ICellModifier {
		private final boolean isLeftViewer;

		public TransactionCellModifier(boolean isLeftViewer) {
			this.isLeftViewer = isLeftViewer;
		}

		@Override
		public boolean canModify(Object element, String property) {
			final boolean leftInterface = ((Primitive) element).getInterface().isLeftInterface();
			if (isLeftViewer) {
				return leftInterface;
			} else {
				return !leftInterface;
			}
		}

		@Override
		public Object getValue(Object element, String property) {
			// hü melanie siehe TransactionLabelProvider/getColumnText
			if (element instanceof Primitive) {
				switch (property) {
				case NAME:
					return "NAME";
				case PARAM:
					return "PARAM";
				case INTERFACE:
					return "i";
				}
			}
			return element;
		}

		@Override
		public void modify(Object element, String property, Object value) {
			final TableItem tableItem = (TableItem) element;
			final OutputPrimitive primitive = (OutputPrimitive) tableItem.getData();
			Command cmd = null;
			switch (property) {
			case NAME:
				cmd = new ChangePrimitiveEventCommand(primitive, value.toString());
				break;
			case PARAM:
				cmd = new ChangePrimitiveParameterCommand(primitive, value.toString());
				break;
			default:
				break;
			}
			if (null != cmd) {
				executeCommand(cmd);
				refresh();
			}
		}

	}

	protected static class TransactionContentProvider extends ArrayContentProvider {
		private final boolean isLeftInterface;

		public TransactionContentProvider(boolean isLeftInterface) {
			this.isLeftInterface = isLeftInterface;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (isLeftInterface) {
				return getLeftOutputPrimitives((ServiceTransaction) inputElement).toArray();
			}
			return getRightOutputPrimitives((ServiceTransaction) inputElement).toArray();

		}

		private List<OutputPrimitive> getLeftOutputPrimitives(ServiceTransaction t) {
			return t.getOutputPrimitive().stream().filter(p -> p.getInterface().isLeftInterface())
					.collect(Collectors.toCollection(ArrayList::new));
		}

		private List<OutputPrimitive> getRightOutputPrimitives(ServiceTransaction t) {
			return t.getOutputPrimitive().stream().filter(p -> !p.getInterface().isLeftInterface())
					.collect(Collectors.toCollection(ArrayList::new));

		}
	}
}

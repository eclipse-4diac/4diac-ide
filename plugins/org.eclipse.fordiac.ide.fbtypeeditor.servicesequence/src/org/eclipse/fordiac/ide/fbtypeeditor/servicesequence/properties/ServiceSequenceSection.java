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
 *   Melanie Winter - renewed section, use tableviewer
 *   Felix Roithmayr - added startstate and type support
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.properties;

import java.util.Arrays;
import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangeSequenceNameCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangeSequenceStartStateCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.ChangeSequenceTypeCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateTransactionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteTransactionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.contentprovider.ServiceSequenceContentProvider;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.widgets.StateComboHelper;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.ServiceSequenceTypes;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeTransactionOrderCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.ui.widget.AddDeleteReorderListWidget;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class ServiceSequenceSection extends AbstractSection {

	private TableViewer transactionsViewer;
	private Text nameText;
	private Text commentText;
	private CCombo startState;
	private CCombo serviceSequencetype;

	private static final String INDEX = "index"; //$NON-NLS-1$
	private static final String INPUT_PRIMIIVE = "input primitive"; //$NON-NLS-1$
	private static final String OUTPUT_PRIMIIVES = "output primitives"; //$NON-NLS-1$

	@Override
	protected ServiceSequence getType() {
		return (ServiceSequence) type;
	}

	@Override
	protected ServiceSequence getInputType(final Object input) {
		if (input instanceof final ServiceSequenceEditPart serSeqEP) {
			return serSeqEP.getModel();
		}
		if (input instanceof final ServiceSequence serSeq) {
			return serSeq;
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		final Composite typeAndComment = getWidgetFactory().createComposite(parent);
		typeAndComment.setLayout(new GridLayout(1, false));
		typeAndComment.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createTypeAndCommentSection(typeAndComment);

		final Composite transactionSection = getWidgetFactory().createComposite(parent);
		transactionSection.setLayout(new GridLayout(1, false));
		transactionSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createTransactionSection(transactionSection);

		transactionsViewer.setContentProvider(new ServiceSequenceContentProvider());
		TableWidgetFactory.enableCopyPasteCut(tabbedPropertySheetPage);
	}

	private void createTypeAndCommentSection(final Composite parent) {
		final Group typeAndCommentGroup = getWidgetFactory().createGroup(parent,
				Messages.ServiceSequenceSection_ServiceSequence);
		typeAndCommentGroup.setLayout(new GridLayout(4, false));
		typeAndCommentGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		getWidgetFactory().createCLabel(typeAndCommentGroup, Messages.ServiceSection_Name);
		nameText = createGroupText(typeAndCommentGroup, true);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false));
		nameText.addModifyListener(e -> {
			final Command cmd = new ChangeSequenceNameCommand(nameText.getText(), getType());
			executeCommand(cmd);
		});

		getWidgetFactory().createCLabel(typeAndCommentGroup, Messages.ServiceSection_Comment);
		commentText = createGroupText(typeAndCommentGroup, true);
		commentText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		commentText.addModifyListener(e -> {
			final Command cmd = new ChangeCommentCommand(getType(), commentText.getText());
			executeCommand(cmd);
		});

		getWidgetFactory().createCLabel(typeAndCommentGroup, Messages.ServiceSection_StartState);
		startState = createStartStateSelector(typeAndCommentGroup);
		startState.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		startState.addModifyListener(e -> {
			final Command cmd = new ChangeSequenceStartStateCommand(startState.getText(), getType());
			executeCommand(cmd);
		});

		getWidgetFactory().createCLabel(typeAndCommentGroup, Messages.ServiceSection_Type);
		serviceSequencetype = createTypeSelector(typeAndCommentGroup);
		serviceSequencetype.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		serviceSequencetype.addModifyListener(e -> {
			final Command cmd = new ChangeSequenceTypeCommand(serviceSequencetype.getText(), getType());
			executeCommand(cmd);
		});
	}

	private void createTransactionSection(final Composite parent) {
		final Group transactionGroup = getWidgetFactory().createGroup(parent,
				Messages.ServiceSequenceSection_Transaction);
		transactionGroup.setLayout(new GridLayout(2, false));
		transactionGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final AddDeleteReorderListWidget buttons = new AddDeleteReorderListWidget();
		buttons.createControls(transactionGroup, getWidgetFactory());
		transactionsViewer = createTableViewer(transactionGroup);
		configureButtonList(buttons, transactionsViewer);
	}

	private CCombo createTypeSelector(final Group parent) {
		final CCombo combo = getWidgetFactory().createCCombo(parent);
		final List<String> items = ServiceSequenceTypes.getAllTypes();
		combo.setItems(items.toArray(new String[0]));
		return combo;
	}

	private static CCombo createStartStateSelector(final Group parent) {
		return ComboBoxWidgetFactory.createCombo(parent);
	}

	private void configureButtonList(final AddDeleteReorderListWidget buttons, final TableViewer transactionsViewer) {
		buttons.bindToTableViewer(transactionsViewer, this,
				ref -> newCreateCommand(getType(), (ServiceTransaction) ref),
				ref -> newDeleteCommand((ServiceTransaction) ref),
				ref -> newOrderCommand((ServiceTransaction) ref, true),
				ref -> newOrderCommand((ServiceTransaction) ref, false));

	}

	private static Command newOrderCommand(final ServiceTransaction ref, final boolean up) {
		return new ChangeTransactionOrderCommand(ref, up);
	}

	private static Command newDeleteCommand(final ServiceTransaction ref) {
		return new DeleteTransactionCommand(ref);
	}

	private static CreateTransactionCommand newCreateCommand(final ServiceSequence serviceSequence,
			final ServiceTransaction ref) {
		return new CreateTransactionCommand(serviceSequence, ref);
	}

	private static TableViewer createTableViewer(final Group parent) {
		final TableViewer viewer = TableWidgetFactory.createTableViewer(parent);
		viewer.getTable().setLayout(createTableLayout(viewer.getTable()));
		viewer.setColumnProperties(getColumnProperties());
		viewer.setLabelProvider(new TransactionLabelProvider());

		return viewer;
	}

	private static String[] getColumnProperties() {
		return new String[] { INDEX, INPUT_PRIMIIVE, OUTPUT_PRIMIIVES };
	}

	private static Layout createTableLayout(final Table table) {
		final TableColumn indexColumn = new TableColumn(table, SWT.LEFT);
		indexColumn.setText(Messages.ServiceSequenceSection_Index);
		final TableColumn inputPrimitiveColumn = new TableColumn(table, SWT.LEFT);
		inputPrimitiveColumn.setText(Messages.ServiceSequenceSection_InputPrimitive);
		final TableColumn outputPrimitiveColumn = new TableColumn(table, SWT.LEFT);
		outputPrimitiveColumn.setText(Messages.ServiceSequenceSection_OutputPrimitives);
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(80));
		layout.addColumnData(new ColumnPixelData(125));
		layout.addColumnData(new ColumnPixelData(300));
		return layout;
	}

	@Override
	protected void performRefresh() {
		nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
		commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
		final int i = Arrays.asList(serviceSequencetype.getItems()).indexOf(getType().getServiceSequenceType());
		serviceSequencetype.select(i >= 0 ? i : 0);
		final FBType fbtype = getType().getService().getFBType();
		StateComboHelper.setup(fbtype, getType(), startState);
		transactionsViewer.setInput(getType());
	}

	@Override
	protected void setInputCode() {
		nameText.setEnabled(false);
		commentText.setEnabled(false);
		transactionsViewer.setInput(null);
	}

	@Override
	protected void setInputInit() {
		// currently nothing to be done here
	}

	protected static class TransactionLabelProvider extends LabelProvider implements ITableLabelProvider {
		private static final int INDEX_COL_INDEX = 0;
		private static final int INPUT_PRIMITIVE_COL_INDEX = 1;
		private static final int OUTPUT_PRIMITIVE_COL_INDEX = 2;

		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			// currently nothing to be done here
			return null;
		}

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			if (element instanceof final ServiceTransaction transaction) {
				switch (columnIndex) {
				case INDEX_COL_INDEX:
					return String
							.valueOf(transaction.getServiceSequence().getServiceTransaction().indexOf(transaction) + 1);
				case INPUT_PRIMITIVE_COL_INDEX:
					return transaction.getInputPrimitive().getEvent();
				case OUTPUT_PRIMITIVE_COL_INDEX:
					return getOutputPrimitives(transaction);
				default:
					break;
				}
			}
			return element.toString();
		}

		private static String getOutputPrimitives(final ServiceTransaction transaction) {
			final StringBuilder sb = new StringBuilder();
			for (final OutputPrimitive outputPrimitive : transaction.getOutputPrimitive()) {
				sb.append(outputPrimitive.getEvent());
				sb.append("; "); //$NON-NLS-1$
			}
			return sb.toString();
		}
	}
}

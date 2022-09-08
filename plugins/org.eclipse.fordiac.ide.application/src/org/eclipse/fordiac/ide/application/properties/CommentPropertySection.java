/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - multline comments and cleanup
 *   Sebastian Hollersbacher - change to nebula grid
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class CommentPropertySection extends AbstractSection { // implements I4diacTableUtil {

	private static final int ONE_COLUMN = 1;
	private static final int TWO_COLUMNS = 2;

	private static final int NAME = 0;
	private static final int TYPE = 1;
	private static final int INITIAL_VALUE = 2;
	private static final int COMMENT = 3;

	private Text nameText;
	private Text commentText;

	private NatTable inputTable;
	private NatTable outputTable;

	private VarDeclarationDataProvider inputDataProvider;
	private VarDeclarationDataProvider outputDataProvider;

	private TabbedPropertySheetPage tabbedPropertySheetPage;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		this.tabbedPropertySheetPage = tabbedPropertySheetPage;
		setLeftComposite(parent);
		parent.setLayout(new GridLayout(ONE_COLUMN, true));
		GridDataFactory.fillDefaults().grab(true, true).applyTo(parent);
		createFBInfoGroup(parent);
		createTableSection(parent);
	}

	@Override
	public void refresh() {
		if ((getType() != null) && !nameText.isDisposed() && !nameText.getParent().isDisposed()) {
			final CommandStack commandStackBuffer = commandStack;
			commandStack = null;
			nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			commandStack = commandStackBuffer;
		}
	}

	private void createTableSection(final Composite parent) {
		final Composite tableSectionComposite = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(TWO_COLUMNS).applyTo(tableSectionComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tableSectionComposite);

		final Group inputComposite = getWidgetFactory().createGroup(tableSectionComposite, Messages.CommentPropertySection_DataInputs);
		final Group outputComposite = getWidgetFactory().createGroup(tableSectionComposite, Messages.CommentPropertySection_DataOutputs);

		inputComposite.setText(Messages.CommentPropertySection_DataInputs);
		outputComposite.setText(Messages.CommentPropertySection_DataOutputs);

		inputComposite.setLayout(new GridLayout(ONE_COLUMN, false));
		outputComposite.setLayout(new GridLayout(ONE_COLUMN, false));

		inputDataProvider = new VarDeclarationDataProvider(true);
		outputDataProvider = new VarDeclarationDataProvider(false);

		inputTable = NatTableWidgetFactory.createNatTable(inputComposite, inputDataProvider, new ColumnDataProvider());
		outputTable = NatTableWidgetFactory.createNatTable(outputComposite, outputDataProvider,
				new ColumnDataProvider());

		GridDataFactory.fillDefaults().grab(true, true).applyTo(inputComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(outputComposite);

		tableSectionComposite.layout();
	}

	@Override
	public TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return tabbedPropertySheetPage.getWidgetFactory();
	}

	protected void createFBInfoGroup(final Composite parent) {
		final Composite fbInfoGroup = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(TWO_COLUMNS).applyTo(fbInfoGroup);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).applyTo(fbInfoGroup);

		getWidgetFactory().createCLabel(fbInfoGroup, FordiacMessages.Name + ":"); //$NON-NLS-1$
		nameText = createGroupText(fbInfoGroup, true);
		nameText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeNameCommand(getType(), nameText.getText()));
			addContentAdapter();
		});

		final CLabel commentLabel = getWidgetFactory().createCLabel(fbInfoGroup, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.TOP).grab(false, false).applyTo(commentLabel);

		commentText = createGroupText(fbInfoGroup, true, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).hint(SWT.DEFAULT, 3 * commentText.getLineHeight()).applyTo(commentText);
		commentText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});
	}


	@Override
	protected Object getInputType(final Object input) {
		if (input instanceof EditPart) {
			return ((EditPart) input).getModel();
		}
		if (input instanceof FBNetworkElement) {
			return input;
		}
		return null;
	}

	@Override
	protected INamedElement getType() {
		if (type instanceof FBNetworkElement) {
			return (FBNetworkElement) type;
		}
		return null;
	}

	@Override
	protected void setInputCode() {
		// Nothing for now
	}

	@Override
	protected void setInputInit() {
		inputDataProvider.setInput(getType());
		outputDataProvider.setInput(getType());

		inputTable.refresh();
		outputTable.refresh();
	}

	// @Override
	// public AbstractTableViewer getViewer() {
	// // return isInputsViewer ? inputCommentsViewer : outputCommentsViewer;
	// return null;
	// }
	//
	// // NOTE These methods are supposed to work for table rows, but here we use them for single cells.
	// // An inconsistency that will be redressed once the new copy/paste handling has been approved.
	//
	// @Override
	// public void addEntry(final Object entry, final int index, final CompoundCommand cmd) {
	// // nothing to do here
	// }
	//
	// @Override
	// public Object removeEntry(final int index, final CompoundCommand cmd) {
	// // nothing to do here
	// return null;
	// }
	//
	// @Override
	// public void executeCompoundCommand(final CompoundCommand cmd) {
	// commandStack.execute(cmd);
	// }


	private class VarDeclarationDataProvider implements IDataProvider {
		private final boolean isInputData;
		private EList<VarDeclaration> varList;

		public VarDeclarationDataProvider(final boolean isInputData) {
			this.isInputData = isInputData;
		}

		public void setInput(final Object inputElement) {
			if (inputElement instanceof FBNetworkElement) {
				if (isInputData) {
					varList = ((FBNetworkElement) inputElement).getInterface().getInputVars();
				} else {
					varList = ((FBNetworkElement) inputElement).getInterface().getOutputVars();
				}
			}
		}

		@Override
		public Object getDataValue(final int columnIndex, final int rowIndex) {
			final VarDeclaration item = varList.get(rowIndex);
			if (item == null) {
				return null;
			}

			switch (columnIndex) {
			case NAME:
				return item.getName();
			case TYPE:
				return item.getTypeName();
			case INITIAL_VALUE:
				return InitialValueHelper.getInitalOrDefaultValue(item);
			case COMMENT:
				return item.getComment();

			default:
				return null;
			}
		}

		@Override
		public void setDataValue(final int columnIndex, final int rowIndex, final Object newValue) {
			Command cmd = null;
			switch (columnIndex) {
			case INITIAL_VALUE:
				cmd = new ChangeValueCommand(varList.get(rowIndex), (String) newValue);
				break;
			case COMMENT:
				cmd = new ChangeCommentCommand(varList.get(rowIndex), (String) newValue);
				break;

			default:
				return;
			}
			executeCommand(cmd);
			refresh();
		}

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public int getRowCount() {
			if (varList != null) {
				return varList.size();
			}

			return 0;
		}
	}


	private class ColumnDataProvider implements IDataProvider {

		@Override
		public Object getDataValue(final int columnIndex, final int rowIndex) {
			switch (columnIndex) {
			case NAME:
				return FordiacMessages.Name;
			case TYPE:
				return FordiacMessages.Type;
			case INITIAL_VALUE:
				return FordiacMessages.InitialValue;
			case COMMENT:
				return FordiacMessages.Comment;

			default:
				return FordiacMessages.EmptyField;
			}
		}

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public int getRowCount() {
			return 1;
		}

		@Override
		public void setDataValue(final int columnIndex, final int rowIndex, final Object newValue) {
			// Setting data values to the header is not supported
		}
	}
}

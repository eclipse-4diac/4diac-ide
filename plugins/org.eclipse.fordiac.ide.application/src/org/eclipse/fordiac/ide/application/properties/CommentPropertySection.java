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
 *   Sebastian Hollersbacher - change to nebula NatTable
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import java.util.List;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
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
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class CommentPropertySection extends AbstractSection {

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

	private VarDeclarationListProvider inputDataProvider;
	private VarDeclarationListProvider outputDataProvider;

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
			outputTable.refresh();
			inputTable.refresh();
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

		inputDataProvider = new VarDeclarationListProvider(null, true);
		outputDataProvider = new VarDeclarationListProvider(null, false);

		final DataLayer inputDataLayer = new DataLayer(inputDataProvider);
		configureDataLayerLabels(inputDataLayer, true);
		final DataLayer outputDataLayer = new DataLayer(outputDataProvider);
		configureDataLayerLabels(outputDataLayer, false);

		inputTable = NatTableWidgetFactory.createNatTable(inputComposite, inputDataLayer, new ColumnDataProvider(),
				inputDataProvider.getEditableRule());
		outputTable = NatTableWidgetFactory.createNatTable(outputComposite, outputDataLayer,
				new ColumnDataProvider(), outputDataProvider.getEditableRule());

		GridDataFactory.fillDefaults().grab(true, true).applyTo(inputComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(outputComposite);

		tableSectionComposite.layout();
	}

	private void configureDataLayerLabels(final DataLayer dataLayer, final boolean isInput) {
		dataLayer.setConfigLabelAccumulator((configLabels, columnPosition, rowPosition) -> {
			final VarDeclaration rowItem;
			String defaultComment = null;
			if (isInput) {
				rowItem = inputDataProvider.getRowObject(rowPosition);
				final FBType fbType = rowItem.getFBNetworkElement().getType();
				if (fbType != null) {
					defaultComment = fbType.getInterfaceList().getInputVars().get(rowPosition).getComment();
				}

				if (columnPosition == INITIAL_VALUE && rowItem.getValue().hasError()) {
					configLabels.addLabelOnTop(NatTableWidgetFactory.ERROR_CELL);
				}
			} else {
				rowItem = outputDataProvider.getRowObject(rowPosition);
				final FBType fbType = rowItem.getFBNetworkElement().getType();
				if (fbType != null) {
					defaultComment = fbType.getInterfaceList().getOutputVars().get(rowPosition).getComment();
				}
			}

			if (columnPosition == INITIAL_VALUE && !InitialValueHelper.hasInitalValue(rowItem)
					|| columnPosition == COMMENT && defaultComment != null
							&& rowItem.getComment().equals(defaultComment)) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.DEFAULT_CELL);
			}
		});
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


	private class VarDeclarationListProvider extends ListDataProvider<VarDeclaration> {
		private final boolean isInputData;

		public VarDeclarationListProvider(final List<VarDeclaration> list,
				final boolean isInputData) {

			super(list, new VarDeclarationColumnAccessor(isInputData));
			this.isInputData = isInputData;
		}

		@Override
		public int getRowCount() {
			if (this.list != null) {
				return super.getRowCount();
			}
			return 0;
		}

		public void setInput(final Object inputElement) {
			if (inputElement instanceof FBNetworkElement) {
				if (isInputData) {
					this.list = ((FBNetworkElement) inputElement).getInterface().getInputVars();
				} else {
					this.list = ((FBNetworkElement) inputElement).getInterface().getOutputVars();
				}
			}
		}

		public IEditableRule getEditableRule() {
			return new IEditableRule() {
				@Override
				public boolean isEditable(final int columnIndex, final int rowIndex) {
					return (columnIndex == INITIAL_VALUE && isInputData) || columnIndex == COMMENT;
				}

				@Override
				public boolean isEditable(final ILayerCell cell, final IConfigRegistry configRegistry) {
					return (cell.getColumnIndex() == INITIAL_VALUE && isInputData) || cell.getColumnIndex() == COMMENT;
				}
			};
		}
	}

	private class VarDeclarationColumnAccessor implements IColumnAccessor<VarDeclaration> {
		private final boolean isInputData;

		public VarDeclarationColumnAccessor(final boolean isInputData) {
			this.isInputData = isInputData;
		}

		@Override
		public Object getDataValue(final VarDeclaration rowObject, final int columnIndex) {
			switch (columnIndex) {
			case NAME:
				return rowObject.getName();
			case TYPE:
				return rowObject.getTypeName();
			case INITIAL_VALUE:
				return InitialValueHelper.getInitalOrDefaultValue(rowObject);
			case COMMENT:
				return rowObject.getComment();

			default:
				return null;
			}
		}

		@Override
		public void setDataValue(final VarDeclaration rowObject, final int columnIndex, final Object newValue) {
			Command cmd = null;
			switch (columnIndex) {
			case INITIAL_VALUE:
				if (!isInputData) {
					return;
				}
				cmd = new ChangeValueCommand(rowObject, (String) newValue);
				break;
			case COMMENT:
				if ((String) newValue != null) {
					cmd = new ChangeCommentCommand(rowObject, (String) newValue);
				} else {
					cmd = new ChangeCommentCommand(rowObject, ""); //$NON-NLS-1$
				}
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

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
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.commands.change.HidePinCommand;
import org.eclipse.fordiac.ide.model.edit.helper.InitialValueHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.fordiac.ide.ui.widget.CheckBoxConfigurationNebula;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
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
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class CommentPropertySection extends AbstractSection {

	private static final int ONE_COLUMN = 1;
	private static final int TWO_COLUMNS = 2;

	private static final int NAME = 0;
	private static final int TYPE = 1;
	private static final int INITIAL_VALUE = 2;
	private static final int COMMENT = 3;
	public static final int VISIBLE = 4;

	private static final int COL_COUNT = 5;

	private Text nameText;
	private Text commentText;

	private NatTable inputTable;
	private NatTable outputTable;

	private VarDeclarationListProvider inputDataProvider;
	private VarDeclarationListProvider outputDataProvider;

	IAction[] defaultCopyPasteCut = new IAction[3];
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

		inputTable.addConfiguration(new CheckBoxConfigurationNebula());
		outputTable.addConfiguration(new CheckBoxConfigurationNebula());

		inputTable.configure();
		outputTable.configure();

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
				final INamedElement currentFB = getType();

				if (currentFB instanceof StructManipulator) {
					final List<VarDeclaration> variableList = ((StructManipulator) currentFB).getStructType()
							.getMemberVariables();
					if (!variableList.isEmpty()) {
						defaultComment = variableList.get(rowPosition).getComment();
					}
				} else if (fbType != null) {
					defaultComment = fbType.getInterfaceList().getInputVars().get(rowPosition).getComment();
				}

				if (columnPosition == INITIAL_VALUE && rowItem.getValue().hasError()) {
					configLabels.addLabelOnTop(NatTableWidgetFactory.ERROR_CELL);
				}
			} else {
				rowItem = outputDataProvider.getRowObject(rowPosition);
				final FBType fbType = rowItem.getFBNetworkElement().getType();
				final INamedElement currentFB = getType();
				if (currentFB instanceof StructManipulator) {
					final List<VarDeclaration> variableList = ((StructManipulator) currentFB).getStructType()
							.getMemberVariables();
					if (!variableList.isEmpty()) {
						defaultComment = variableList.get(rowPosition).getComment();
					}
				} else if (fbType != null) {
					defaultComment = fbType.getInterfaceList().getOutputVars().get(rowPosition).getComment();
				}
			}

			if (columnPosition == INITIAL_VALUE && !InitialValueHelper.hasInitalValue(rowItem)
					|| columnPosition == COMMENT && defaultComment != null
					&& rowItem.getComment().equals(defaultComment)) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.DEFAULT_CELL);
			}
			// We add a label for the checkbox column
			if (columnPosition == VISIBLE) {
				configLabels.addLabelOnTop(NatTableWidgetFactory.VISIBILITY_CELL);
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
			if (EditorUtils.getGraphicalViewerFromCurrentActiveEditor() != null && getType() instanceof SubApp) {
				final Object subAppforFBNetowrkEditPart = EditorUtils.getGraphicalViewerFromCurrentActiveEditor()
						.getEditPartRegistry().get(getType());
				if (subAppforFBNetowrkEditPart instanceof SubAppForFBNetworkEditPart
						&& ((SubAppForFBNetworkEditPart) subAppforFBNetowrkEditPart).getContentEP() != null) {
					executeCommand(new ResizeGroupOrSubappCommand(
							((SubAppForFBNetworkEditPart) subAppforFBNetowrkEditPart).getContentEP(),
							new ChangeCommentCommand(getType(), commentText.getText())));
				}
			} else {
				executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			}
			addContentAdapter();
		});
	}

	@Override
	public void aboutToBeShown() {
		// this can be removed once copy/paste for old tables is no longer used
		final IActionBars bars = tabbedPropertySheetPage.getSite().getActionBars();
		defaultCopyPasteCut[0] = bars.getGlobalActionHandler(ActionFactory.COPY.getId());
		bars.setGlobalActionHandler(ActionFactory.COPY.getId(), null);
		defaultCopyPasteCut[1] = bars.getGlobalActionHandler(ActionFactory.PASTE.getId());
		bars.setGlobalActionHandler(ActionFactory.PASTE.getId(), null);
		defaultCopyPasteCut[2] = bars.getGlobalActionHandler(ActionFactory.CUT.getId());
		bars.setGlobalActionHandler(ActionFactory.CUT.getId(), null);
		bars.updateActionBars();

		super.aboutToBeShown();
	}

	@Override
	public void aboutToBeHidden() {
		// this can be removed once copy/paste for old tables is no longer used
		final IActionBars bars = tabbedPropertySheetPage.getSite().getActionBars();
		bars.setGlobalActionHandler(ActionFactory.COPY.getId(), defaultCopyPasteCut[0]);
		bars.setGlobalActionHandler(ActionFactory.PASTE.getId(), defaultCopyPasteCut[1]);
		bars.setGlobalActionHandler(ActionFactory.CUT.getId(), defaultCopyPasteCut[2]);
		bars.updateActionBars();

		super.aboutToBeHidden();
	}
	@Override
	protected Object getInputType(final Object input) {
		return InstanceSectionFilter.getFBNetworkElementFromSelectedElement(input);
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

		inputTable.addListener(SWT.Selection, event -> {
			if (event.index == VISIBLE) {
				final Object o = event.data;
			}
		});

		outputTable.addListener(SWT.Selection, event -> {
			if (event.index == VISIBLE) {
				final Object o = event.data;
			}
		});
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
					return (columnIndex == INITIAL_VALUE && isInputData) || columnIndex == COMMENT
							|| columnIndex == VISIBLE;
				}

				@Override // Added the visible column stuff
				public boolean isEditable(final ILayerCell cell, final IConfigRegistry configRegistry) {
					return (cell.getColumnIndex() == INITIAL_VALUE && isInputData) || cell.getColumnIndex() == COMMENT
							|| cell.getColumnIndex() == VISIBLE;
				}
			};
		}
	}

	private class VarDeclarationColumnAccessor implements IColumnPropertyAccessor<VarDeclaration> { // IColumnPropertyAccessor
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
			case VISIBLE: // I added
				return rowObject.isVisible();
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
			case VISIBLE:
				// It's a true/false checkbox; if it's visible -> hide; if it's hidden -> show
				// newValue atm is a string...
				cmd = new HidePinCommand(rowObject, (Boolean) newValue);
				break;
			default:
				return;
			}
			executeCommand(cmd);
			refresh();
		}

		@Override
		public int getColumnCount() {
			return COL_COUNT;
		}

		@Override
		public String getColumnProperty(final int columnIndex) {
			switch (columnIndex) {
			case NAME:
				return FordiacMessages.Name;
			case TYPE:
				return FordiacMessages.Type;
			case INITIAL_VALUE:
				return FordiacMessages.InitialValue;
			case COMMENT:
				return FordiacMessages.Comment;
			case VISIBLE:
				return FordiacMessages.Visible;
			default:
				return null;
			}
		}

		@Override
		public int getColumnIndex(final String propertyName) {
			switch (propertyName) {
			case "Name":
				return NAME;
			case "Type":
				return TYPE;
			case "Initial Value":
				return INITIAL_VALUE;
			case "Comment":
				return COMMENT;
			case "Visible":
				return VISIBLE;
			default:
				return -1;
			}
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
			case VISIBLE:
				return FordiacMessages.Visible;
			default:
				return FordiacMessages.EmptyField;
			}
		}

		@Override
		public int getColumnCount() {
			return COL_COUNT;
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

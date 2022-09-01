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

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.edit.providers.CommentLabelProvider;
import org.eclipse.fordiac.ide.model.edit.providers.InitialValueColumLabelProvider;
import org.eclipse.fordiac.ide.model.edit.providers.NameLabelProvider;
import org.eclipse.fordiac.ide.model.edit.providers.TypeLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.I4diacTableUtil;
import org.eclipse.fordiac.ide.ui.widget.NebulaGridWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.AbstractTableViewer;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.nebula.jface.gridviewer.GridColumnLayout;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.jface.gridviewer.GridViewerColumn;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridHeaderRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class CommentPropertySection extends AbstractSection implements I4diacTableUtil {

	private static final int ONE_COLUMN = 1;
	private static final int TWO_COLUMNS = 2;
	private static final String INITIAL_VALUE = "Initial Value"; //$NON-NLS-1$
	private static final String COMMENT = "Comment"; //$NON-NLS-1$

	private Text nameText;
	private Text commentText;

	private GridTableViewer inputCommentsViewer;
	private GridTableViewer outputCommentsViewer;
	private GridColumnLayout layout;
	private TabbedPropertySheetPage tabbedPropertySheetPage;

	private CommentLabelProvider commentLabelProvider;
	private InitialValueColumLabelProvider initialValueLabelProvider;

	private boolean isInputsViewer;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		this.tabbedPropertySheetPage = tabbedPropertySheetPage;
		setLeftComposite(parent);
		parent.setLayout(new GridLayout(ONE_COLUMN, true));
		GridDataFactory.fillDefaults().grab(true, true).applyTo(parent);
		createFBInfoGroup(parent);
		createTableSection(parent);
		configureTableViewer(inputCommentsViewer, new InputViewerContentProvider());
		configureTableViewer(outputCommentsViewer, new OutputViewerContentProvider());
		setFocusListeners();
		TableWidgetFactory.enableCopyPasteCut(tabbedPropertySheetPage);
	}

	@Override
	public void refresh() {
		if ((getType() != null) && !nameText.isDisposed() && !nameText.getParent().isDisposed()) {
			final CommandStack commandStackBuffer = commandStack;
			commandStack = null;
			nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			inputCommentsViewer.setInput(getType());
			outputCommentsViewer.setInput(getType());
			commandStack = commandStackBuffer;
		}
	}

	private void setFocusListeners() {
		outputCommentsViewer.getGrid().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(final FocusEvent e) {
				isInputsViewer = false;
			}

			@Override
			public void focusLost(final FocusEvent e) {
				// Nothing to do
			}

		});
		inputCommentsViewer.getGrid().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(final FocusEvent e) {
				isInputsViewer = true;
			}

			@Override
			public void focusLost(final FocusEvent e) {
				// Nothing to do
			}

		});
	}

	private void configureTableViewer(final GridTableViewer gridTableViewer,
			final IStructuredContentProvider contentProvider) {
		final Grid grid = gridTableViewer.getGrid();

		final CellModifierForCommentSection modifier = new CellModifierForCommentSection();
		gridTableViewer.setCellModifier(modifier);

		gridTableViewer.setColumnProperties(new String[] { FordiacMessages.Name, FordiacMessages.Type,
				FordiacMessages.InitialValue, FordiacMessages.Comment });

		final TextCellEditor cellEditor = new TextCellEditor(grid) {
			@Override
			protected void doSetFocus() {
				super.doSetFocus();
				text.setSelection(text.getText().length());
			}
		};
		gridTableViewer.setCellEditors(new CellEditor[] { null, null, cellEditor, cellEditor });

		gridTableViewer.setContentProvider(contentProvider);

		grid.setHeaderVisible(true);
		grid.setLinesVisible(true);
		grid.setCellSelectionEnabled(true);
		grid.setAutoWidth(true);

		final GridViewerColumn col1 = new GridViewerColumn(gridTableViewer, SWT.NONE);
		final GridViewerColumn col2 = new GridViewerColumn(gridTableViewer, SWT.NONE);
		final GridViewerColumn col3 = new GridViewerColumn(gridTableViewer, SWT.NONE);
		final GridViewerColumn col4 = new GridViewerColumn(gridTableViewer, SWT.NONE);

		initialValueLabelProvider = new InitialValueColumLabelProvider();
		commentLabelProvider = new CommentLabelProvider();

		col1.setLabelProvider(new NameLabelProvider());
		col2.setLabelProvider(new TypeLabelProvider());
		col3.setLabelProvider(initialValueLabelProvider);
		col4.setLabelProvider(commentLabelProvider);

		col1.getColumn().setText(FordiacMessages.Name);
		col2.getColumn().setText(FordiacMessages.Type);
		col3.getColumn().setText(FordiacMessages.InitialValue);
		col4.getColumn().setText(FordiacMessages.Comment);

		grid.setEmptyColumnHeaderRenderer(NebulaGridWidgetFactory.createSimpleEmptyColumnHeader());
		final GridHeaderRenderer headerRenderer = NebulaGridWidgetFactory.createSimpleColumnHeader();
		col1.getColumn().setHeaderRenderer(headerRenderer);
		col2.getColumn().setHeaderRenderer(headerRenderer);
		col3.getColumn().setHeaderRenderer(headerRenderer);
		col4.getColumn().setHeaderRenderer(headerRenderer);

		layout.setColumnData(col1.getColumn(), new ColumnWeightData(25));
		layout.setColumnData(col2.getColumn(), new ColumnWeightData(25));
		layout.setColumnData(col3.getColumn(), new ColumnWeightData(25));
		layout.setColumnData(col4.getColumn(), new ColumnWeightData(25));

		grid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				final ViewerCell focusCell = gridTableViewer.getColumnViewerEditor().getFocusCell();
				final CellEditor editor = gridTableViewer.getCellEditors()[focusCell.getColumnIndex()];

				if (editor != null && !Character.isISOControl(e.character)) {
					gridTableViewer.editElement(focusCell.getElement(), focusCell.getColumnIndex());
					editor.setValue(Character.toString(e.character));
					editor.setFocus();
				}
			}
		});
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

		layout = new GridColumnLayout();
		inputCommentsViewer = NebulaGridWidgetFactory.createPropertyGridTableViewer(inputComposite, layout);
		outputCommentsViewer = NebulaGridWidgetFactory.createPropertyGridTableViewer(outputComposite, layout);

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
		// Nothing for now

	}

	private class CellModifierForCommentSection implements ICellModifier {

		// Can modify if it's an initial value or a comment
		@Override
		public boolean canModify(final Object element, final String property) {
			return FordiacMessages.InitialValue.equals(property) || FordiacMessages.Comment.equals(property);
		}

		@Override
		public Object getValue(final Object element, final String property) {
			if (element instanceof VarDeclaration) {
				switch (property) {
				case INITIAL_VALUE:
					return initialValueLabelProvider.getText(element);
				case COMMENT:
					return commentLabelProvider.getText(element);
				default:
					break;
				}
			}
			return null;
		}

		@Override
		public void modify(Object element, final String property, final Object value) {
			Command cmd = null;
			if (element instanceof Item) {
				element = ((Item) element).getData();
				if (element instanceof VarDeclaration) {
					final VarDeclaration varDec = (VarDeclaration) element;
					switch (property) {
					case INITIAL_VALUE:
						cmd = new ChangeValueCommand(varDec, (String) value);
						break;
					case COMMENT:
						cmd = new ChangeCommentCommand(varDec, (String) value);
						break;
					default:
						break;
					}
					executeCommand(cmd);
					refresh();
				}
			}
		}
	}

	private static class InputViewerContentProvider implements IStructuredContentProvider {

		private static final Object[] EMPTY_ARR = new Object[0];

		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof FBNetworkElement) {
				final FBNetworkElement fbNetworkElem = (FBNetworkElement) inputElement;
				return fbNetworkElem.getInterface().getInputVars().toArray();
			}
			return EMPTY_ARR;
		}

	}

	private static class OutputViewerContentProvider implements IStructuredContentProvider {

		private static final Object[] EMPTY_ARR = new Object[0];

		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof FBNetworkElement) {
				final FBNetworkElement fbNetworkElem = (FBNetworkElement) inputElement;
				return fbNetworkElem.getInterface().getOutputVars().toArray();
			}
			return EMPTY_ARR;
		}

	}

	@Override
	public AbstractTableViewer getViewer() {
		return isInputsViewer ? inputCommentsViewer : outputCommentsViewer;
	}

	// NOTE These methods are supposed to work for table rows, but here we use them for single cells.
	//		An inconsistency that will be redressed once the new copy/paste handling has been approved.

	@Override
	public void addEntry(final Object entry, final int index, final CompoundCommand cmd) {
		// nothing to do here
	}

	@Override
	public Object removeEntry(final int index, final CompoundCommand cmd) {
		// nothing to do here
		return null;
	}

	@Override
	public void executeCompoundCommand(final CompoundCommand cmd) {
		commandStack.execute(cmd);
	}

}

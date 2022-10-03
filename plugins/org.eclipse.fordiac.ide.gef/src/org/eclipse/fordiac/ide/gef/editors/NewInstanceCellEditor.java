/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 * 				 2022 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Fabio Gandolfi - insideCell parameter to use the CellEditor inside TableViewer cells
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editors;

import java.util.List;

import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.utilities.CellEditorLayoutFactory;
import org.eclipse.fordiac.ide.model.edit.providers.ResultListLabelProvider;
import org.eclipse.fordiac.ide.model.typelibrary.PaletteFilter;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewInstanceCellEditor extends TextCellEditor {

	private static final int NUM_COLUMNS = 2;

	// if NewInstanceCellEditor is used inside a TableCell = true
	// if not for example like in main editor for creating new FBs = false(default)
	private boolean insideCell;

	private Composite container;
	private Button menuButton;
	protected Shell popupShell;
	protected TableViewer tableViewer;
	private PaletteFilter paletteFilter;
	private boolean blockTableSelection = false;
	private TypeEntry selectedEntry = null;
	protected Text textControl;

	private ResultListLabelProvider resultListLabelProvider;

	public NewInstanceCellEditor() {
		super();
	}

	public NewInstanceCellEditor(final Composite parent) {
		this(parent, SWT.NONE);
	}

	public NewInstanceCellEditor(final Composite parent, final int style) {
		this(parent, style, false);
	}

	public NewInstanceCellEditor(final Composite parent, final int style, final boolean insideCell) {
		super(parent, style | SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
		this.insideCell = insideCell;
	}


	public Button getMenuButton() {
		return menuButton;
	}

	public void setTypeLibrary(final TypeLibrary typeLib) {
		paletteFilter = new PaletteFilter(typeLib);
	}

	@Override
	protected Control createControl(final Composite parent) {
		container = createContainer(parent);
		textControl = (Text) super.createControl(container);
		configureTextControl();
		createTypeMenuButton(container);
		createPopUpList(container);
		// initial population of the selection list
		updateSelectionList();
		return container;
	}

	public Text getText() {
		return text;
	}

	@Override
	public void focusLost() {

		if (!insideAnyEditorArea()) {
			// when we loose focus we want to fire cancel so that have entered text is not
			// applied
			fireCancelEditor();
		}
	}

	// make the fireCancleEditor publicly available for the direct edit manager
	@Override
	public void fireCancelEditor() {
		super.fireCancelEditor();
	}

	@Override
	public void deactivate() {
		if ((null != popupShell) && !popupShell.isDisposed()) {
			popupShell.setVisible(false);
		}
		super.deactivate();
	}

	@Override
	protected void handleDefaultSelection(final SelectionEvent event) {
		if (!((Text) event.getSource()).getText().isEmpty()) {
			super.handleDefaultSelection(event);
		}
	}

	@Override
	public Object doGetValue() {
		if (null != selectedEntry) {
			return selectedEntry;
		}
		return super.doGetValue();
	}

	public boolean insideAnyEditorArea() {
		final Point cursorLocation = popupShell.getDisplay().getCursorLocation();
		final Point containerRelativeCursor = container.getParent().toControl(cursorLocation);
		return container.getBounds().contains(containerRelativeCursor)
				|| popupShell.getBounds().contains(cursorLocation);
	}

	protected Composite createContainer(final Composite parent) {
		final Composite newContainer = new Composite(parent, SWT.NONE) {

			@Override
			public void setBounds(final int x, final int y, final int width, final int height) {
				super.setBounds(x, y, width, height);

				final Point screenPos;
				if(insideCell) {
					screenPos = new Point(x, y);
				} else {
					screenPos = getParent().toDisplay(getLocation());
				}
				final Rectangle compositeBounds = getBounds();
				popupShell.setBounds(screenPos.x, screenPos.y + compositeBounds.height, compositeBounds.width, 150);
				if (!popupShell.isVisible()) {
					popupShell.setVisible(true);
				}
			}
		};
		newContainer.setBackground(parent.getBackground());
		newContainer.setForeground(parent.getForeground());

		// set layout with minimal space to keep the cell editor compact

		newContainer.setLayout(CellEditorLayoutFactory.getNewGridZeroLayout(NUM_COLUMNS));
		return newContainer;
	}

	public void configureTextControl() {
		textControl.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		textControl.setMessage(Messages.NewInstanceCellEditor_SearchForType);
		textControl.addListener(SWT.Modify, event -> updateSelectionList());
		textControl.addListener(SWT.KeyDown, event -> handleKeyPress(event, textControl));
	}

	protected void updateSelectionList() {
		blockTableSelection = true;
		if (textControl.getText().length() >= 2) {
			final List<TypeEntry> entries = paletteFilter.findFBAndSubappTypes((textControl.getText()));
			resultListLabelProvider.setSearchString(textControl.getText());
			tableViewer.setInput(entries);
			if (!entries.isEmpty()) {
				selectItemAtIndex(0);
			}
		} else {
			tableViewer.setInput(null);
		}
		blockTableSelection = false;
	}

	private void handleKeyPress(final Event event, final Text textControl) {
		switch (event.keyCode) {
		case SWT.ARROW_DOWN:
			if (tableViewer.getTable().getItemCount() > 0) {
				final int index = (tableViewer.getTable().getSelectionIndex() + 1) % tableViewer.getTable().getItemCount();
				selectItemAtIndex(index);
			}
			event.doit = false;
			break;
		case SWT.ARROW_UP:
			if (tableViewer.getTable().getItemCount() > 0) {
				int index = tableViewer.getTable().getSelectionIndex() - 1;
				if (index < 0) {
					index = tableViewer.getTable().getItemCount() - 1;
				}
				selectItemAtIndex(index);
			}
			event.doit = false;
			break;
		case SWT.CR:
			if (popupShell.isVisible() && (tableViewer.getTable().getSelectionIndex() != -1)) {
				selectedEntry = (TypeEntry) tableViewer.getStructuredSelection().getFirstElement();
				textControl.setText(selectedEntry.getTypeName());
			} else {
				event.doit = false;
			}
			break;
		default:
			break;
		}
	}

	private void selectItemAtIndex(final int index) {
		blockTableSelection = true;
		final Object element = tableViewer.getElementAt(index);
		tableViewer.setSelection(new StructuredSelection(element), true);
		blockTableSelection = false;
	}

	private void createPopUpList(final Composite container) {
		popupShell = new Shell(container.getShell(), SWT.ON_TOP | SWT.NO_FOCUS | SWT.NO_TRIM);
		popupShell.setLayout(new FillLayout());

		tableViewer = new TableViewer(popupShell, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		ColumnViewerToolTipSupport.enableFor(tableViewer);
		tableViewer.setContentProvider(new ArrayContentProvider());

		resultListLabelProvider = new ResultListLabelProvider();
		final DelegatingStyledCellLabelProvider delegatingStyledCellLabelProvider = new DelegatingStyledCellLabelProvider(
				resultListLabelProvider);
		tableViewer.setLabelProvider(delegatingStyledCellLabelProvider);

		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(100));
		tableViewer.getTable().setLayout(layout);

		tableViewer.getControl().addListener(SWT.KeyDown, event -> {
			if (event.keyCode == SWT.ESC) {
				fireCancelEditor();
			}
		});

		tableViewer.addSelectionChangedListener(event -> {
			if (!blockTableSelection) {
				selectedEntry = (TypeEntry) tableViewer.getStructuredSelection().getFirstElement();
				fireApplyEditorValue();
			}
		});
	}

	private void createTypeMenuButton(final Composite container) {
		menuButton = new Button(container, SWT.FLAT);
		menuButton.setImage(FordiacImage.ICON_TYPE_NAVIGATOR.getImage());
	}

}
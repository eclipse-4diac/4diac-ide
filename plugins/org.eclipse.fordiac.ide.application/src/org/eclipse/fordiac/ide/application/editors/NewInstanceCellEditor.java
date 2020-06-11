/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editors;

import java.util.List;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.PaletteFilter;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class NewInstanceCellEditor extends TextCellEditor {

	private Composite container;
	private Button menuButton;
	private Shell popupShell;
	private TableViewer tableViewer;
	private PaletteFilter paletteFilter;
	private boolean blockTableSelection = false;
	private PaletteEntry selectedEntry = null;

	public NewInstanceCellEditor() {
		super();
	}

	public NewInstanceCellEditor(Composite parent) {
		this(parent, SWT.NONE);
	}

	public NewInstanceCellEditor(Composite parent, int style) {
		super(parent, style | SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
	}

	public Button getMenuButton() {
		return menuButton;
	}

	public void setPalette(Palette palette) {
		paletteFilter = new PaletteFilter(palette);
	}

	@Override
	protected Control createControl(Composite parent) {
		container = createContainer(parent);
		Text textControl = (Text) super.createControl(container);
		configureTextControl(textControl);
		createTypeMenuButton(container);
		createPopUpList(container);
		// initial population of the selection list
		updateSelectionList(textControl.getText());
		return container;
	}

	public Text getText() {
		return text;
	}

	@Override
	protected void focusLost() {

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
	protected void handleDefaultSelection(SelectionEvent event) {
		if (!((Text) event.getSource()).getText().isEmpty()) {
			super.handleDefaultSelection(event);
		}
	}

	@Override
	protected Object doGetValue() {
		if (null != selectedEntry) {
			return selectedEntry;
		}
		return super.doGetValue();
	}

	private boolean insideAnyEditorArea() {
		Point cursorLocation = popupShell.getDisplay().getCursorLocation();
		Point containerRelativeCursor = container.getParent().toControl(cursorLocation);
		return container.getBounds().contains(containerRelativeCursor)
				|| popupShell.getBounds().contains(cursorLocation);
	}

	private Composite createContainer(Composite parent) {
		Composite newContainer = new Composite(parent, SWT.NONE) {
			@Override
			public void setBounds(int x, int y, int width, int height) {
				super.setBounds(x, y, width, height);
				Point screenPos = getParent().toDisplay(getLocation());
				Rectangle compositeBounds = getBounds();
				popupShell.setBounds(screenPos.x, screenPos.y + compositeBounds.height, compositeBounds.width, 150);
				if (!popupShell.isVisible()) {
					popupShell.setVisible(true);
				}
			}
		};
		newContainer.setBackground(parent.getBackground());
		newContainer.setForeground(parent.getForeground());

		// set layout with minimal space to keep the cell editor compact
		GridLayout contLayout = new GridLayout(2, false);
		contLayout.horizontalSpacing = 0;
		contLayout.marginTop = 0;
		contLayout.marginBottom = 0;
		contLayout.marginWidth = 0;
		contLayout.marginHeight = 0;
		contLayout.verticalSpacing = 0;
		contLayout.horizontalSpacing = 0;
		newContainer.setLayout(contLayout);
		return newContainer;
	}

	private void configureTextControl(final Text textControl) {
		textControl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		textControl.setMessage(Messages.NewInstanceCellEditor_SearchForType);
		textControl.addListener(SWT.Modify, event -> {
			updateSelectionList(textControl.getText());
		});
		textControl.addListener(SWT.KeyDown, event -> {
			switch (event.keyCode) {
			case SWT.ARROW_DOWN:
				int index = (tableViewer.getTable().getSelectionIndex() + 1) % tableViewer.getTable().getItemCount();
				selectItemAtIndex(index);
				event.doit = false;
				break;
			case SWT.ARROW_UP:
				index = tableViewer.getTable().getSelectionIndex() - 1;
				if (index < 0) {
					index = tableViewer.getTable().getItemCount() - 1;
				}
				selectItemAtIndex(index);
				event.doit = false;
				break;
			case SWT.CR:
				if (popupShell.isVisible() && (tableViewer.getTable().getSelectionIndex() != -1)) {
					selectedEntry = (PaletteEntry) tableViewer.getStructuredSelection().getFirstElement();
					textControl.setText(selectedEntry.getLabel());
				} else {
					event.doit = false;
				}
				break;
			default:
				break;
			}
		});
	}

	private void updateSelectionList(String searchString) {
		blockTableSelection = true;
		if (searchString.length() >= 2) {
			List<PaletteEntry> entries = paletteFilter.findFBAndSubappTypes(searchString);
			tableViewer.setInput(entries);
			if (!entries.isEmpty()) {
				selectItemAtIndex(0);
			}
		} else {
			tableViewer.setInput(null);
		}
		blockTableSelection = false;
	}

	private void selectItemAtIndex(int index) {
		blockTableSelection = true;
		Object element = tableViewer.getElementAt(index);
		tableViewer.setSelection(new StructuredSelection(element), true);
		blockTableSelection = false;
	}

	private void createPopUpList(Composite container) {
		popupShell = new Shell(container.getShell(), SWT.ON_TOP | SWT.NO_FOCUS | SWT.NO_TRIM);
		popupShell.setLayout(new FillLayout());

		tableViewer = new TableViewer(popupShell, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		ColumnViewerToolTipSupport.enableFor(tableViewer);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new StyledCellLabelProvider() {

			public Image getImage(Object element) {
				if (element instanceof PaletteEntry) {
					PaletteEntry entry = (PaletteEntry) element;
					if (entry.getType() instanceof SubAppType) {
						return FordiacImage.ICON_SUB_APP.getImage();
					} else if (entry.getType() instanceof BasicFBType) {
						return FordiacImage.ICON_BASIC_FB.getImage();
					} else if (entry.getType() instanceof SimpleFBType) {
						return FordiacImage.ICON_SIMPLE_FB.getImage();
					} else if (entry.getType() instanceof CompositeFBType) {
						return FordiacImage.ICON_COMPOSITE_FB.getImage();
					} else {
						return FordiacImage.ICON_SIFB.getImage();
					}
				}
				return null;
			}

			@Override
			public void update(ViewerCell cell) {
				Object obj = cell.getElement();
				StyledString styledString = null;
				if (obj instanceof PaletteEntry) {
					PaletteEntry entry = (PaletteEntry) obj;
					styledString = new StyledString(entry.getLabel());
					styledString.append(" - " + entry.getType().getComment(), //$NON-NLS-1$
							StyledString.QUALIFIER_STYLER);
				} else {
					styledString = new StyledString(obj.toString());
				}

				cell.setText(styledString.toString());
				cell.setStyleRanges(styledString.getStyleRanges());
				cell.setImage(getImage(obj));
				super.update(cell);
			}

			@Override
			public String getToolTipText(Object element) {
				if (element instanceof PaletteEntry) {
					return ((PaletteEntry) element).getFile().getProjectRelativePath().toString() + "\n\n"
							+ ((PaletteEntry) element).getType().getComment();
				}
				return super.getToolTipText(element);
			}

		});

		new TableColumn(tableViewer.getTable(), SWT.NONE);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(100));
		tableViewer.getTable().setLayout(layout);

		tableViewer.getControl().addListener(SWT.KeyDown, event -> {
			if (event.keyCode == SWT.ESC) {
				fireCancelEditor();
			}
		});

		tableViewer.addSelectionChangedListener(event -> {
			if (!blockTableSelection) {
				selectedEntry = (PaletteEntry) tableViewer.getStructuredSelection().getFirstElement();
				fireApplyEditorValue();
			}
		});
	}

	private void createTypeMenuButton(Composite container) {
		menuButton = new Button(container, SWT.FLAT);
		menuButton.setImage(FordiacImage.ICON_TYPE_NAVIGATOR.getImage());
	}

}
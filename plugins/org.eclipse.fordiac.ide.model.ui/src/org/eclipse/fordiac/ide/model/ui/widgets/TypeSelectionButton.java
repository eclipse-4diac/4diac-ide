/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *                    Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - changed the now AbstractSelectionButton class and extracted
 *   this class for easier handling
 *   Martin Erich Jobst - refactored to accept generic content provider
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.widgets;

import java.util.function.Supplier;

import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.model.ui.nat.TypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.ui.widget.NatTableWidgetFactory;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.nattable.edit.editor.TextCellEditor;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;
import org.eclipse.nebula.widgets.nattable.widget.EditModeEnum;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class TypeSelectionButton extends TextCellEditor {

	private final Supplier<TypeLibrary> supplier;
	private final TypeSelectionTreeContentProvider treeContentProvider;

	protected Button button;

	public TypeSelectionButton(final Supplier<TypeLibrary> supplier,
			final ITypeSelectionContentProvider contentProvider,
			final TypeSelectionTreeContentProvider treeContentProvider) {
		this.supplier = supplier;
		this.treeContentProvider = treeContentProvider;
		enableContentProposal(supplier, contentProvider);
	}

	protected void enableContentProposal(final Supplier<TypeLibrary> supplier,
			final ITypeSelectionContentProvider contentProvider) {
		enableContentProposal(new TextContentAdapter(), new TypeSelectionProposalProvider(supplier, contentProvider),
				null, NatTableWidgetFactory.getActivationChars());
	}

	@Override
	protected Control activateCell(final Composite parent, final Object originalCanonicalValue) {
		button = new Button(parent, SWT.FLAT);
		button.setText("..."); //$NON-NLS-1$
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final DataTypeTreeSelectionDialog dialog = new DataTypeTreeSelectionDialog(
						Display.getCurrent().getActiveShell(), treeContentProvider);
				dialog.setHelpAvailable(false);
				dialog.setInput(supplier.get());
				dialog.setTitle(treeContentProvider.getTitle());

				if (dialog.open() != Window.OK) {
					if (editMode != EditModeEnum.DIALOG) {
						close();
					}
					return;
				}

				final TypeNode selected = (TypeNode) dialog.getFirstResult();
				if (selected != null) {
					setEditorValue(selected.getFullName());
					if (editMode == EditModeEnum.INLINE) {
						commit(MoveDirectionEnum.NONE, true);
					}
				}
			}
		});

		final Text text = (Text) super.activateCell(parent, originalCanonicalValue);
		if (editMode == EditModeEnum.DIALOG) {
			final Composite composite = new Composite(parent, 0);
			composite.setLayout(new GridLayout(2, false));
			GridDataFactory.fillDefaults().grab(true, false).applyTo(composite);
			text.setParent(composite);
			button.setParent(composite);
			return parent;
		}
		return parent;
	}

	@Override
	protected Text createEditorControl(final Composite parent, final int style) {
		focusListener = new FocusAdapter() {
			// remove focus Listener for button popup
		};
		return super.createEditorControl(parent, style);
	}

	@Override
	public Rectangle calculateControlBounds(final Rectangle cellBounds) {
		button.setBounds(cellBounds.x + cellBounds.width - 25, cellBounds.y, 25, cellBounds.height);
		cellBounds.width = cellBounds.width - 25;
		return super.calculateControlBounds(cellBounds);
	}

	@Override
	public void close() {
		super.close();
		button.dispose();
	}
}

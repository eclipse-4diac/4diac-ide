/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.widgets;

import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.model.ui.editors.DataTypeTreeSelectionDialog;
import org.eclipse.fordiac.ide.model.ui.nat.TypeNode;
import org.eclipse.fordiac.ide.model.ui.nat.TypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.ui.widget.AbstractSelectionButton;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;
import org.eclipse.nebula.widgets.nattable.widget.EditModeEnum;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class DataTypeSelectionButton extends AbstractSelectionButton {

	private TypeSelectionTreeContentProvider typeSelectionTreeContentProvider;

	public DataTypeSelectionButton(final Map<String, List<String>> elements) {
		super(elements);
	}

	public DataTypeSelectionButton(final Map<String, List<String>> elements, final TypeSelectionTreeContentProvider typeSelectionTreeContentProvider) {
		super(elements);
		this.typeSelectionTreeContentProvider = typeSelectionTreeContentProvider;
	}

	@Override
	protected Control activateCell(final Composite parent, final Object originalCanonicalValue) {
		refreshProposal();
		button = new Button(parent, SWT.FLAT);
		button.setText("..."); //$NON-NLS-1$
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				DataTypeTreeSelectionDialog dialog = null;
				if (typeSelectionTreeContentProvider != null) {
					dialog = new DataTypeTreeSelectionDialog(Display.getCurrent().getActiveShell(),
							typeSelectionTreeContentProvider);
				} else {
					dialog = new DataTypeTreeSelectionDialog(Display.getCurrent().getActiveShell());
				}

				dialog.setHelpAvailable(false);
				dialog.setInput(elements);

				if (dialog.open() != Window.OK) {
					if (editMode != EditModeEnum.DIALOG) {
						close();
					}
					return;
				}

				final TypeNode selected = (TypeNode) dialog.getFirstResult();
				if (selected != null) {
					setEditorValue(selected.getName());
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
}

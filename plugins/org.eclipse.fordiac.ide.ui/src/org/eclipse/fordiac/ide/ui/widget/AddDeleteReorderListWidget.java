/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Alois Zoitl - initial implementation
 * Bianca Wiesmayr - enhanced add functionality
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.ui.providers.CommandProvider;
import org.eclipse.fordiac.ide.ui.providers.CreationCommandProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class AddDeleteReorderListWidget extends AddDeleteWidget {
	private Button upButton;
	private Button downButton;

	@Override
	public void createControls(final Composite parent, final FormToolkit widgetFactory) {
		final Composite container = createContainer(widgetFactory, parent);
		container.setLayout(new FillLayout(SWT.VERTICAL));


		createAddButton(widgetFactory, container);

		upButton = widgetFactory.createButton(container, "", SWT.ARROW | SWT.UP); //$NON-NLS-1$
		upButton.setToolTipText("Move element(s) up");
		upButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());

		downButton = widgetFactory.createButton(container, "", SWT.ARROW | SWT.DOWN); //$NON-NLS-1$
		downButton.setToolTipText("Move element(s) down");
		downButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());

		createDeleteButton(widgetFactory, container);

		// initially nothing should be selected therefore deactivate the buttons
		setButtonEnablement(false);
	}


	@Override
	public void setButtonEnablement(final boolean enable) {
		upButton.setEnabled(enable);
		downButton.setEnabled(enable);
		super.setButtonEnablement(enable);
	}

	public void addUpListener(final Listener upListener) {
		upButton.addListener(SWT.Selection, upListener);
	}

	public void addDownListener(final Listener downListener) {
		downButton.addListener(SWT.Selection, downListener);
	}

	public void bindToTableViewer(final TableViewer viewer, final CommandExecutor executor, final CreationCommandProvider addCommand,
			final CommandProvider deleteCommand, final CommandProvider moveUpCommand, final CommandProvider moveDownCommand) {
		super.bindToTableViewer(viewer, executor, addCommand, deleteCommand);
		addUpListener(getSelectionListener(viewer, executor, moveUpCommand));
		addDownListener(getReverseSelectionListener(viewer, executor, moveDownCommand));
	}

	// this is needed for correct execution of move down with multiselection
	public static Listener getReverseSelectionListener(final TableViewer viewer, final CommandExecutor executor,
			final CommandProvider commandProvider) {
		return ev -> {
			if (!viewer.getStructuredSelection().isEmpty()) {
				final List<Object> bottomup = viewer.getStructuredSelection().toList();
				Collections.reverse(bottomup);
				executeCompoundCommandForList(viewer, bottomup, executor, commandProvider);
			}
		};
	}

}

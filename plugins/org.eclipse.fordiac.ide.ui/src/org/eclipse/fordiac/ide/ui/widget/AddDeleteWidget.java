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
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.widget;

import org.eclipse.fordiac.ide.ui.providers.CommandProvider;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class AddDeleteWidget {
	private Button createButton;
	private Button deleteButton;

	public void createControls(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		Composite container = createContainer(parent);

		createAddButton(widgetFactory, container);

		createDeleteButton(widgetFactory, container);

		// initially nothing should be selected therefore deactivate the buttons
		setButtonEnablement(false);
	}

	protected void createDeleteButton(TabbedPropertySheetWidgetFactory widgetFactory, Composite container) {
		deleteButton = widgetFactory.createButton(container, "", SWT.PUSH); //$NON-NLS-1$
		deleteButton.setToolTipText("Delete selected interface element"); //$NON-NLS-1$
		deleteButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE));
	}

	protected void createAddButton(TabbedPropertySheetWidgetFactory widgetFactory, Composite container) {
		createButton = widgetFactory.createButton(container, "", SWT.PUSH); //$NON-NLS-1$
		createButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		createButton.setToolTipText("Create element");
	}

	protected static Composite createContainer(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridData buttonCompLayoutData = new GridData(SWT.CENTER, SWT.TOP, false, false);
		container.setLayoutData(buttonCompLayoutData);
		container.setLayout(new FillLayout(SWT.VERTICAL));
		return container;
	}

	public void setButtonEnablement(boolean enable) {
		deleteButton.setEnabled(enable);
		deleteButton
				.setImage((enable) ? PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE)
						: PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_DELETE_DISABLED));
	}

	public void setCreateButtonEnablement(boolean enable) {
		createButton.setEnabled(enable);
	}

	public void addCreateListener(Listener createListener) {
		createButton.addListener(SWT.Selection, createListener);
	}

	public void addDeleteListener(Listener deleteListener) {
		deleteButton.addListener(SWT.Selection, deleteListener);
	}

	public void bindToTableViewer(TableViewer viewer, CommandExecutor executor, CommandProvider addCommand,
			CommandProvider deleteCommand) {

		Listener createListener = getAddListener(viewer, executor, addCommand);

		Listener deleteListener = getSelectionListener(viewer, executor, deleteCommand);

		bindToTableViewer(viewer, createListener, deleteListener);
	}

	public void bindToTableViewer(TableViewer viewer, Listener createListener, Listener deleteListener) {

		addCreateListener(createListener);
		addDeleteListener(deleteListener);

		viewer.addSelectionChangedListener(ev -> setButtonEnablement(!viewer.getSelection().isEmpty()));

		viewer.getTable().addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				// Nothing to do here
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.keyCode == SWT.INSERT) && (e.stateMask == 0)) {
					createListener.handleEvent(null);
				} else if ((e.character == SWT.DEL) && (e.stateMask == 0)) {
					deleteListener.handleEvent(null);
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	public static Listener getSelectionListener(TableViewer viewer, CommandExecutor executor,
			CommandProvider commandProvider) {
		return ev -> {
			if (!viewer.getStructuredSelection().isEmpty()) {
				CompoundCommand cmd = new CompoundCommand();
				viewer.getStructuredSelection().toList().stream()
						.forEach(elem -> cmd.add(commandProvider.getCommand(elem)));
				executor.executeCommand(cmd);
				viewer.refresh();
			}
		};
	}

	@SuppressWarnings("unchecked")
	private static Listener getAddListener(TableViewer viewer, CommandExecutor executor,
			CommandProvider commandProvider) {
		return ev -> {
			if (viewer.getStructuredSelection().isEmpty()) {
				executor.executeCommand(commandProvider.getCommand(null));
				viewer.refresh();
			} else {
				Command cmd = commandProvider.getCommand(
						viewer.getStructuredSelection().toList().get(viewer.getStructuredSelection().size() - 1));
				executor.executeCommand(cmd);
				viewer.setSelection(null);
				viewer.refresh();
			}
		};
	}
}

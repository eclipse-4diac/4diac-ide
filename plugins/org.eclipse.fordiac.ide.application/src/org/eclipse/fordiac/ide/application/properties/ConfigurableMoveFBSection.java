/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.core.runtime.Assert;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.editparts.ConfigurableMoveFBEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.gef.widgets.TypeSelectionWidget;
import org.eclipse.fordiac.ide.model.commands.change.ConfigureFBCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.DataTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class ConfigurableMoveFBSection extends AbstractSection implements CommandStackEventListener {
	protected TypeSelectionWidget typeSelectionWidget;

	protected CLabel configurableFbLabel;

	@Override
	protected FBNetworkElement getInputType(final Object input) {
		if (input instanceof final ConfigurableMoveFBEditPart ep) {
			return ep.getModel();
		}
		if (input instanceof final ConfigurableMoveFB fb) {
			return fb;
		}
		return null;
	}

	@Override
	protected ConfigurableMoveFB getType() {
		if (type instanceof final ConfigurableMoveFB fb) {
			return fb;
		}
		return null;
	}

	private void createDataTypeSelector(final Composite composite) {
		final Composite structComp = getWidgetFactory().createComposite(composite);
		structComp.setLayout(new GridLayout(2, false));
		structComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		configurableFbLabel = getWidgetFactory().createCLabel(structComp, Messages.ConfigurableMoveFBSection_DataType);

		typeSelectionWidget = new TypeSelectionWidget(getWidgetFactory(), this::handleSelectionChanged);
		typeSelectionWidget.createControls(structComp);
		typeSelectionWidget.setEditable(true);
	}

	protected void handleSelectionChanged(final String newTypeName) {
		if (null != getType() && newDataTypeSelected(newTypeName)) {
			final DataType newDtp = getDataTypeLib().getTypeIfExists(newTypeName);
			final ConfigureFBCommand cmd = new ConfigureFBCommand(getType(), newDtp);
			executeCommand(cmd);
			updateFB(cmd.getNewElement());
		}
	}

	public boolean newDataTypeSelected(final String newDtpName) {
		return !newDtpName.equalsIgnoreCase(PackageNameHelper.getFullTypeName(getType().getDataType()))
				&& getDataTypeLib().getTypeIfExists(newDtpName) != null;
	}

	protected static void updateFB(final FB newFb) {
		final EditorPart activeEditor = (EditorPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor();
		final GraphicalViewer viewer = activeEditor.getAdapter(GraphicalViewer.class);
		if (null != viewer) {
			viewer.flush();
			EditorUtils.refreshPropertySheetWithSelection(activeEditor, viewer, viewer.getEditPartForModel(newFb));
		}
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createDataTypeSelector(parent);
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		if (getCurrentCommandStack() != null) {
			getCurrentCommandStack().removeCommandStackEventListener(this);
		}
		Assert.isTrue(selection instanceof IStructuredSelection);
		final Object input = ((IStructuredSelection) selection).getFirstElement();

		setCurrentCommandStack(part, input);
		if (null == getCurrentCommandStack()) { // disable all fields
			configurableFbLabel.setEnabled(false);
		}

		setType(input);

		typeSelectionWidget.initialize(getType(), DataTypeSelectionContentProvider.INSTANCE,
				DataTypeSelectionTreeContentProvider.INSTANCE);

		if (getCurrentCommandStack() != null) {
			getCurrentCommandStack().addCommandStackEventListener(this);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		if (getCurrentCommandStack() != null) {
			getCurrentCommandStack().removeCommandStackEventListener(this);
		}
	}

	@Override
	public void stackChanged(final CommandStackEvent event) {
		// this method is also run as part of the commandstackeventlistener and may
		// change command stack listener list, to avoid concurrent modifications run it
		// asynchronously
		Display.getDefault().asyncExec(() -> {
			if (event.getDetail() == CommandStack.POST_UNDO || event.getDetail() == CommandStack.POST_REDO) {
				final Command command = event.getCommand();
				if ((command instanceof final ConfigureFBCommand cmd)
						&& (cmd.getOldElement() == getType() || cmd.getNewElement() == getType())) {
					if (event.getDetail() == CommandStack.POST_UNDO) {
						updateFB(cmd.getOldElement());
					} else if (event.getDetail() == CommandStack.POST_REDO) {
						updateFB(cmd.getNewElement());
					}
				}
			}
		});
	}

	@Override
	protected void setInputCode() {
		// Currently nothing needs to be done here
	}

	@Override
	protected void setInputInit() {
		// Currently nothing needs to be done here
	}

	@Override
	protected void performRefresh() {
		// Currently nothing needs to be done here
	}
}

/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - extracted from ConfigurableMoveFBSection and
 *                 StructManipulatorSection
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.widgets.TypeSelectionWidget;
import org.eclipse.fordiac.ide.model.commands.change.ConfigureFBCommand;
import org.eclipse.fordiac.ide.model.commands.create.AddNewImportCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.model.helpers.ModelHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.ui.nat.DataTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.nat.StructuredTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.DataTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.StructuredTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

public class ConfigFBInstancePropertySection extends InstancePropertySection implements CommandStackEventListener {

	private TypeSelectionWidget typeSelectionWidget;
	private Label typeSelectionLabel;

	@Override
	protected ConfigurableFB getInputType(final Object input) {
		return ConfigFBInstanceSectionFilter.getConfigFbFromSelectedElement(input);
	}

	@Override
	protected ConfigurableFB getType() {
		return (ConfigurableFB) super.getType();
	}

	@Override
	protected void createFBInfoGroup(final Composite parent) {
		final Composite fbInfoGroup = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(fbInfoGroup);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).applyTo(fbInfoGroup);
		createNameInput(fbInfoGroup);

		createDataTypeSelector(fbInfoGroup);
	}

	@Override
	protected void performRefresh() {
		super.performRefresh();
		typeSelectionWidget.refresh();
	}

	@Override
	protected void setCurrentCommandStack(final CommandStack commandStack) {
		if (getCurrentCommandStack() != null) {
			getCurrentCommandStack().removeCommandStackEventListener(this);
		}
		super.setCurrentCommandStack(commandStack);
		if (getCurrentCommandStack() != null) {
			getCurrentCommandStack().addCommandStackEventListener(this);
		}
	}

	@Override
	protected void setInputInit() {
		super.setInputInit();
		if (getType() instanceof StructManipulator) {
			typeSelectionWidget.initialize(getType(), StructuredTypeSelectionContentProvider.INSTANCE,
					StructuredTypeSelectionTreeContentProvider.INSTANCE);
			typeSelectionLabel.setText(Messages.ConfigFBInstancePropertySection_StructType);
		} else {
			typeSelectionWidget.initialize(getType(), DataTypeSelectionContentProvider.INSTANCE,
					DataTypeSelectionTreeContentProvider.INSTANCE);
			typeSelectionLabel.setText(Messages.ConfigFBInstancePropertySection_DataType);
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

	private void createDataTypeSelector(final Composite composite) {
		typeSelectionLabel = getWidgetFactory().createLabel(composite, ""); //$NON-NLS-1$ label is set in setInputInit
																			// when the type is known
		typeSelectionWidget = new TypeSelectionWidget(getWidgetFactory(), this::handleSelectionChanged);
		typeSelectionWidget.createControls(composite);
		typeSelectionWidget.setEditable(true);
	}

	protected void handleSelectionChanged(final String newTypeName) {
		if (null != getType() && newDataTypeSelected(newTypeName)) {
			final DataType newDtp = getDataTypeLib().getTypeIfExists(newTypeName);
			final ConfigureFBCommand cmd = new ConfigureFBCommand(getType(), newDtp);
			AddNewImportCommand importCommand = null;

			if (newDtp instanceof StructuredType && newDtp != GenericTypes.ANY_STRUCT) {
				// if we have a struct we need to check for a potential import
				final StructuredType packageStruct = ImportHelper
						.resolveImport(PackageNameHelper.extractPlainTypeName(newTypeName), getType(), name -> {
							final StructuredType temp = getDataTypeLib().getStructuredType(name);
							return GenericTypes.isAnyType(temp) ? null : temp;
						}, _ -> null);

				if (packageStruct == null) {
					importCommand = new AddNewImportCommand(ModelHelper.getLibraryElementFromContextChecked(getType()),
							newTypeName);
				}
			}

			executeCommand(cmd.chain(importCommand));
			updateFB(cmd.getNewElement());
		}
	}

	public boolean newDataTypeSelected(final String newDtpName) {
		if (newDtpName.equalsIgnoreCase(PackageNameHelper.getFullTypeName(getType().getDataType()))) {
			return false;
		}

		final DataType newDT = getDataTypeLib().getTypeIfExists(newDtpName);
		if (getType() instanceof StructManipulator) {
			return newDT instanceof StructuredType;
		}

		return newDT != null;
	}

	private static void updateFB(final FB newFb) {
		final EditorPart activeEditor = (EditorPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor();
		final GraphicalViewer viewer = activeEditor.getAdapter(GraphicalViewer.class);
		if (null != viewer) {
			viewer.flush();
			EditorUtils.refreshPropertySheetWithSelection(activeEditor, viewer, viewer.getEditPartForModel(newFb));
		}
	}

}

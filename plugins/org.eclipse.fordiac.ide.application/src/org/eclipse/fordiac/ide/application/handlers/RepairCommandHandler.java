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
 *   Dario Romano
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.application.editparts.ErrorMarkerFBNEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractConnectableEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerDataTypeImpl;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.wizards.NewFBTypeWizardPage;
import org.eclipse.fordiac.ide.typemanagement.wizards.NewTypeWizard;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

public class RepairCommandHandler extends AbstractHandler {
	private static final String PACKAGE_NAME = ""; //$NON-NLS-1$
	private TypeLibrary typeLib;
	private IEditorPart editor;
	private IStructuredSelection sel;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		sel = HandlerUtil.getCurrentStructuredSelection(event);
		editor = HandlerUtil.getActiveEditor(event);
		final AbstractConnectableEditPart editPart = getEditPartFromSelection(sel);
		typeLib = getTypeLibraryFromEditorInput(editor.getEditorInput());
		if (editPart != null) {
			repairEditPart(editPart, typeLib.getProject().getName());
		}
		typeLib.reload();
		return null;
	}

	private static TypeLibrary getTypeLibraryFromEditorInput(final IEditorInput input) {
		return TypeLibraryManager.INSTANCE.getTypeEntryForFile(((FileEditorInput) input).getFile()).getTypeLibrary();
	}

	private void repairEditPart(final AbstractConnectableEditPart editPart, final String projectName) {
		final ErrorMarkerDataType dataTypeMarker = editPart.getAdapter(ErrorMarkerDataTypeImpl.class);
		if (dataTypeMarker != null) {
			showRestrictedWizard(dataTypeMarker.getTypeEntry().getTypeName(),
					TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT, projectName);
		}
		final ErrorMarkerFBNEditPart fBNEditPartMarker = editPart.getAdapter(ErrorMarkerFBNEditPart.class);
		if (fBNEditPartMarker != null) {
			showRestrictedWizard(((FBNetworkElement) fBNEditPartMarker.getINamedElement()).getTypeName(),
					TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT, projectName);
		}
	}

	private static AbstractConnectableEditPart getEditPartFromSelection(final IStructuredSelection sel) {
		final Optional<AbstractConnectableEditPart> part = sel.toList().stream()
				.filter(AbstractConnectableEditPart.class::isInstance).map(AbstractConnectableEditPart.class::cast)
				.findFirst();
		return part.isPresent() ? part.get() : null;
	}

	private void showRestrictedWizard(final String name, final String fileEnding, final String projectName) {
		final NewTypeWizard wizard = new NewTypeWizard() {
			@Override
			protected NewFBTypeWizardPage createNewFBTypeWizardPage() {
				return new NewFBTypeWizardPage(sel);
			}
		};
		final WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor().getSite().getShell(), wizard);
		dialog.create();
		final NewFBTypeWizardPage page = (NewFBTypeWizardPage) dialog.getCurrentPage();
		page.setTemplateFileFilter(pathname -> pathname.getName().toUpperCase().endsWith(fileEnding));
		page.setFileName(name);

		final TableViewer tv = page.getTemplateViewer();
		final Object selection = tv.getElementAt(0);

		// have to check if any template at all could be found
		if (selection != null) {
			tv.setSelection(new StructuredSelection(selection), true);
			// unlock table if there are possible selections
			if (tv.getElementAt(1) == null) {
				tv.getTable().setEnabled(false);
			}
		}
		restrictName(page.getControl());
		restrictProjectSelection(page.getControl(), projectName);
		if (Window.OK == dialog.open()) {
			// the type could be created new execute the command
			final TypeEntry newEntry = wizard.getTypeEntry();
			newEntry.refresh();
		}
	}

	private static void restrictName(final Control control) {
		final Composite root = (Composite) control;

		final List<Control> children = getFlattenedList(root);
		final List<Control> textfields = children.stream().filter(Text.class::isInstance).toList();
		//@formatter:off
		textfields.stream()
			.filter(c -> Arrays.stream(c.getParent().getChildren())
					.filter(Label.class::isInstance)
					.map(Label.class::cast)
					.anyMatch(l -> l.getText().equals(FordiacMessages.TypeName+":"))) //$NON-NLS-1$
			.forEach(c -> c.setEnabled(false));
		//@formatter:on

	}

	private static void restrictProjectSelection(final Control control, final String projectName) {
		final Composite root = (Composite) control;
		final List<Control> elements = getFlattenedList(root);
		//@formatter:off
		elements.stream()
			.filter(Tree.class::isInstance)
			.map(Tree.class::cast) //get the tree
			.flatMap(a-> Arrays.stream(a.getItems())) //get all root elements
			.filter(i -> !i.getText().equals(projectName)) //find all elements which are not the current project
			.forEach(TreeItem::dispose); //dispose them, so only the current project is left
		//@formatter:on
		control.redraw();
	}

	private static List<Control> getFlattenedList(final Composite root) {
		final List<Control> flattenedSet = new ArrayList<>();
		if (root.getChildren().length == 0) {
			flattenedSet.add(root);
			return flattenedSet;
		}
		for (final Control c : root.getChildren()) {
			if (c instanceof final Composite com) {
				flattenedSet.addAll(getFlattenedList(com));
			} else {
				flattenedSet.add(c);
			}
		}
		return flattenedSet;
	}

}

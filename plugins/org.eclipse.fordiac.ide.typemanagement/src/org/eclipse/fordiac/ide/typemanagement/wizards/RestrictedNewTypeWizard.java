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
 * 	 Dario Romano
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class RestrictedNewTypeWizard extends NewTypeWizard {

	private RestrictedNewTypeWizard() {
	}

	@Override
	protected NewFBTypeWizardPage createNewFBTypeWizardPage() {
		return new NewFBTypeWizardPage(new StructuredSelection());
	}

	public static TypeEntry showRestrictedNewTypeWizard(final Shell shell, final String fullName,
			final String fileEnding, final String projectName) {
		final RestrictedNewTypeWizard wizard = new RestrictedNewTypeWizard();
		final WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.create();

		final NewFBTypeWizardPage page = (NewFBTypeWizardPage) dialog.getCurrentPage();
		page.setTemplateFileFilter(pathname -> pathname.getName().toUpperCase().endsWith(fileEnding.toUpperCase()));
		page.setFileName(PackageNameHelper.extractPlainTypeName(fullName));
		page.setPackageName(PackageNameHelper.extractPackageName(fullName));

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
		if (projectName != null) {
			restrictProjectSelection(page.getControl(), projectName);
		}
		if (Window.OK == dialog.open()) {
			// the type could be created new execute the command
			final TypeEntry newEntry = wizard.getTypeEntry();
			newEntry.refresh();
			return newEntry;
		}
		return null;
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

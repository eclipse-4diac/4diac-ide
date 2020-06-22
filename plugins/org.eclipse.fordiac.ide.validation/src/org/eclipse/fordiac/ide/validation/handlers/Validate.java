/*******************************************************************************
 * Copyright (c) 2020 Sandor Bacsi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sandor Bacsi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.validation.Activator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.expressions.Variable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class Validate extends AbstractHandler {
	private static class ValidationResultDialog extends MessageDialog {

		private final List<String> data;

		public ValidationResultDialog(Shell parent, INamedElement element, final List<String> data) {
			super(parent, "Validation Results for " + element.getName(), null, null, INFORMATION, 0,
					IDialogConstants.OK_LABEL);
			this.data = data;
		}

		@Override
		protected Control createMessageArea(Composite composite) {
			final Composite body = (Composite) super.createMessageArea(composite);

			final TableViewer viewer = new TableViewer(body, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).hint(SWT.MAX, SWT.DEFAULT)
					.applyTo(viewer.getTable());
			configureTableColumns(viewer.getTable());

			viewer.setContentProvider(new ArrayContentProvider());
			viewer.setInput(data);

			return body;
		}

		private static void configureTableColumns(Table table) {
			new TableColumn(table, SWT.LEFT).setText("Errors");
			TableLayout layout = new TableLayout();
			layout.addColumnData(new ColumnWeightData(65, 100));
			table.setLayout(layout);
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		List<String> result = new ArrayList<String>();
		List<Constraint> constraints = OCLParser.loadOCLConstraints();

		INamedElement selectedElement = getSelectedElement(
				(StructuredSelection) HandlerUtil.getCurrentSelection(event));

		for (Constraint constraint : constraints) {
			Variable<EClassifier, EParameter> context = constraint.getSpecification().getContextVariable();
			String contextName = (context.getType().getName());
			for (TreeIterator<?> iterator = selectedElement.eAllContents(); iterator.hasNext();) {
				EObject object = (EObject) iterator.next();
				String objectName = object.eClass().getName();
				if (contextName.equals(objectName)) 
					if (!Activator.getDefault().getOclInstance().check(object, constraint)) 
						result.add("The " + constraint.getName() + " constraint is violated on " + object.toString());
			}
		}

		displayResults(selectedElement, result, HandlerUtil.getActiveWorkbenchWindowChecked(event));
		return null;
	}

	private static INamedElement getSelectedElement(StructuredSelection currentSelection) {
		Object obj = currentSelection.getFirstElement();

		if (obj instanceof IFile) {
			return checkSelectedFile((IFile) obj);
		}
		return (obj instanceof INamedElement) ? (INamedElement) obj : null;
	}

	private static INamedElement checkSelectedFile(IFile file) {
		PaletteEntry entry = TypeLibrary.getPaletteEntryForFile(file);
		if (entry instanceof FBTypePaletteEntry) {
			return ((FBTypePaletteEntry) entry).getFBType();
		}
		return null;
	}

	private static void displayResults(INamedElement element, List<String> result, IWorkbenchWindow workbenchWindow) {
		ValidationResultDialog resultDialog = new ValidationResultDialog(workbenchWindow.getShell(), element, result);
		resultDialog.open();
	}
}

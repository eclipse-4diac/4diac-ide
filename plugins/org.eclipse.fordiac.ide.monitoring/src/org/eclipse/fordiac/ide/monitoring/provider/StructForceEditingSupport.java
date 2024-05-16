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
 *   Daniel Lindhuber - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.provider;

import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.monitoring.views.WatchValueTreeNode;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Tree;

public class StructForceEditingSupport extends EditingSupport {

	private final TextCellEditor cellEditor;

	public StructForceEditingSupport(final ColumnViewer viewer, final Tree tree) {
		super(viewer);
		cellEditor = new TextCellEditor(tree);
	}

	@Override
	protected CellEditor getCellEditor(final Object element) {
		return cellEditor;
	}

	@Override
	protected boolean canEdit(final Object element) {
		if (element instanceof final WatchValueTreeNode tn) {
			return tn.isStructLeaf();
		}
		return false;
	}

	@Override
	protected Object getValue(final Object element) {
		if (element instanceof final WatchValueTreeNode tn) {
			return tn.getValue();
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	protected void setValue(final Object element, final Object value) {
		if ((element instanceof final WatchValueTreeNode tn) && isValid((String) value, tn.getVariable())) {
			tn.setValue((String) value);
			getViewer().refresh();
		}
	}

	public static boolean isValid(final String value, final VarDeclaration varDec) {
		if (!value.isBlank()) {
			final String validationMsg = VariableOperations.validateValue(varDec, value);
			if ((validationMsg != null) && (!validationMsg.trim().isEmpty())) {
				ErrorMessenger.popUpErrorMessage(validationMsg);
				return false;
			}
		}
		return true;
	}

}

/*******************************************************************************
 * Copyright (c) 2012, 2014, 2017 Profactor GmbH, fortiss GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *    - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester;

import org.eclipse.fordiac.ide.fbtester.model.testdata.TestData;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.TableColumn;

public class TestDataEditingSupport extends EditingSupport {

	private final TableColumn col;

	public TestDataEditingSupport(ColumnViewer viewer, TableColumn column) {
		super(viewer);
		this.col = column;
	}

	@Override
	protected boolean canEdit(Object element) {
		if (col.getData(FBTester.COLUMN_TYPE).equals(FBTester.NAME)) {
			return true;
		} else if (col.getData(FBTester.COLUMN_TYPE).equals(FBTester.INPUT_VARIABLE)) {
			return true;
		} else if (col.getData(FBTester.COLUMN_TYPE).equals(FBTester.OUTPUT_VARIABLE)) {
			return true;
		}
		return false;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		if (col.getData(FBTester.COLUMN_TYPE).equals(FBTester.NAME)
				|| col.getData(FBTester.COLUMN_TYPE).equals(FBTester.INPUT_VARIABLE)
				|| col.getData(FBTester.COLUMN_TYPE).equals(FBTester.OUTPUT_VARIABLE)) {
			return new TextCellEditor(((TableViewer) getViewer()).getTable());
		}
		return null;
	}

	@Override
	protected Object getValue(Object element) {
		if (col.getData(FBTester.COLUMN_TYPE).equals(FBTester.NAME)) {
			return ((TestData) element).getTestName();
		} else if (col.getData(FBTester.COLUMN_TYPE).equals(FBTester.INPUT_VARIABLE)) {
			Object obj = col.getData(FBTester.INPUT_VARIABLE);
			if (obj instanceof VarDeclaration) {
				return ((TestData) element).getValueFor(((VarDeclaration) obj).getName());
			}
		} else if (col.getData(FBTester.COLUMN_TYPE).equals(FBTester.OUTPUT_VARIABLE)) {
			Object obj = col.getData(FBTester.OUTPUT_VARIABLE);
			if (obj instanceof VarDeclaration) {
				return ((TestData) element).getResultFor(((VarDeclaration) obj).getName());
			}
		}
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		if (col.getData(FBTester.COLUMN_TYPE).equals(FBTester.INPUT_VARIABLE)) {
			Object obj = col.getData(FBTester.INPUT_VARIABLE);
			if (obj instanceof VarDeclaration) {
				((TestData) element).setValueFor(((VarDeclaration) obj).getName(), value.toString());
				getViewer().refresh();
				return;
			}
		}
		if (col.getData(FBTester.COLUMN_TYPE).equals(FBTester.OUTPUT_VARIABLE)) {
			Object obj = col.getData(FBTester.OUTPUT_VARIABLE);
			if (obj instanceof VarDeclaration) {
				((TestData) element).setResultFor(((VarDeclaration) obj).getName(), value.toString());
				getViewer().refresh();
				return;
			}
		}
		if (value instanceof String) {
			if (!("".equals(value))) { //$NON-NLS-1$
				((TestData) element).setTestName(value.toString());
			}
		}

	}

}

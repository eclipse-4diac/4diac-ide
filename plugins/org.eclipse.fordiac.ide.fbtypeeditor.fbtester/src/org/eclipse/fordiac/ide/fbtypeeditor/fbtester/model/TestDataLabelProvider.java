/*******************************************************************************
 * Copyright (c) 2012, 2013, 2015 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *    - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.model;

import org.eclipse.fordiac.ide.fbtester.model.testdata.TestData;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.FBTester;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;


public class TestDataLabelProvider extends LabelProvider implements
		ITableLabelProvider, ITableColorProvider, ILabelDecorator {

	private final Table table;

	public TestDataLabelProvider(FBType type, Table table) {
		this.table = table;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.
	 * Object, int)
	 */
	@Override
	public Image getColumnImage(final Object element, final int columnIndex) {
		if (columnIndex == 0) {

			DecorationOverlayIcon overlay = FordiacImage.createOverlayImage(FordiacImage.ICON_TesterTemplate.getImage(),
					FordiacImage.ICON_OK.getImageDescriptor());			
			return overlay.createImage();
		}
		return null;
	}

	public String getLabel(TestData data, int index) {
		TableColumn col = table.getColumn(index);
		String columnType = (String) col.getData(FBTester.COLUMN_TYPE);

		if (columnType.equals(FBTester.NAME)) {
			return data.getTestName();
		} else if (columnType.equals(FBTester.EVENT)) {
			return data.getEvent().getName();
		} else if (columnType.equals(FBTester.INPUT_VARIABLE)) {
			VarDeclaration varDecl = (VarDeclaration) col.getData(FBTester.INPUT_VARIABLE);
			return data.getValueFor(varDecl.getName());
		} else if (columnType.equals(FBTester.OUTPUT_VARIABLE)) {
			VarDeclaration varDecl = (VarDeclaration) col.getData(FBTester.OUTPUT_VARIABLE);
			return data.getResultFor(varDecl.getName());
		} else if (columnType.equals(FBTester.EXPECTED_EVENTS)) {
			return data.getOutputEvents();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object
	 * , int)
	 */
	@Override
	public String getColumnText(final Object element, final int columnIndex) {

		if (element instanceof TestData) {
			String result = getLabel((TestData) element, columnIndex);
			return result != null ? result : "";
		}
		return element.toString();
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		if (element instanceof TestData) {
			if (getLabel((TestData) element, columnIndex) == null) {
				return Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
			}
		}
		return null;
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {
		return null;
	}

	@Override
	public Image decorateImage(Image image, Object element) {
		return null;
	}

	@Override
	public String decorateText(String text, Object element) {
		return null;
	}
}
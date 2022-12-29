/*******************************************************************************
 * Copyright (c) 2018, 2022 TU Wien/ACIN, Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Peter Gsellmann
 *   - extraction from
 *     org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties.AlgorithmGroup
 *   Martin Melik-Merkumians
 *   	- made several methods abstract and moved implementation to respective
 *   sub-classes, also removed non-shareable methods to specialized sub-classes
 *   Alois Zoitl - harmonized code from algorithm group and simple alg editing
 *               - removed editing features
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.widgets;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editors.IAlgorithmEditor;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editors.TextEditor;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.forms.widgets.FormToolkit;

public abstract class AlgorithmEditingComposite {

	private Composite codeEditors;
	private final StackLayout stack;
	private IAlgorithmEditor editor;
	private Algorithm currentAlgorithm;

	private final boolean blockUpdates = false;

	protected AlgorithmEditingComposite() {
		stack = new StackLayout();
	}

	public void createControls(final Composite parent, final FormToolkit toolkit) {
		final GridData codeEditorsGridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		codeEditorsGridData.horizontalSpan = 1;
		codeEditorsGridData.minimumHeight = 250;
		codeEditors = new Group(parent, SWT.SHADOW_NONE);
		codeEditors.setBackground(toolkit.getColors().getBackground());
		codeEditors.setForeground(toolkit.getColors().getForeground());
		toolkit.adapt(codeEditors);

		codeEditors.setLayout(stack);
		codeEditors.setLayoutData(codeEditorsGridData);
		editor = new TextEditor(codeEditors, null, null);

		disableAllFields();
	}

	protected BaseFBType getFBType() {
		return (BaseFBType) currentAlgorithm.eContainer();
	}

	protected Algorithm getAlgorithm() {
		return currentAlgorithm;
	}

	public void setAlgorithm(final Algorithm algorithm) {
		if (!blockUpdates) {
			// set commandStack to null so that an update will not lead to a changed type
			if (this.currentAlgorithm != algorithm) {
				currentAlgorithm = algorithm;
				if (null != currentAlgorithm) {
					initializeEditor();
					enableAllFields();
				} else {
					stack.topControl = null;
					codeEditors.layout();
					disableAllFields();
				}
			}
			updateAlgFields();
		}
	}

	protected void enableAllFields() {
		// nothing to be done
	}

	protected void disableAllFields() {
		// nothing to be done
	}

	protected void updateAlgFields() {
		final Algorithm alg = getAlgorithm();
		if (alg instanceof TextAlgorithm) {
			editor.setAlgorithmText(((TextAlgorithm) alg).getText());
		}
	}

	private void initializeEditor() {
		stack.topControl = editor.getControl();
		codeEditors.layout();
	}

}
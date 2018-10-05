/*******************************************************************************
 * Copyright (c) 2018 TU Wien/ACIN
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Peter Gsellmann 
 *   - initial implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.simplefb.widgets;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.widgets.AlgorithmEditingComposite;
import org.eclipse.fordiac.ide.fbtypeeditor.simplefb.commands.ChangeAlgorithmTypeCommandSimpleFB;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class AlgorithmEditingCompositeSimpleFB extends AlgorithmEditingComposite {

	public AlgorithmEditingCompositeSimpleFB(final Composite parent) {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		parent.setLayout(gridLayout);

		Composite langAndComents = new Composite(parent, SWT.NONE | Window.getDefaultOrientation());
		langAndComents.setBackground(parent.getBackground());

		langAndComents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				((Control) e.widget).setFocus();
			}
		});

		if (langAndComents.getParent() != null)
			langAndComents.setMenu(langAndComents.getParent().getMenu());
		langAndComents.setBackground(parent.getBackground());

		langAndComents.setLayout(new GridLayout(2, false));
		langAndComents.setLayoutData(new GridData(GridData.FILL, 0, true, false));

		languageLabel = new CLabel(langAndComents, SWT.BEGINNING);
		languageLabel.setBackground(langAndComents.getBackground());
		languageLabel.setText("Language:");

		languageCombo = new Combo(langAndComents, SWT.BEGINNING | SWT.SINGLE | SWT.READ_ONLY);

		fillLanguageDropDown();
		languageCombo.addListener(SWT.Selection,
				event -> executeCommand(getChangeAlgorithmTypeCommand(getSimpleFBType(), getAlgorithm(), languageCombo.getText())));

		commentLabel = new CLabel(langAndComents, SWT.BEGINNING);
		commentLabel.setBackground(langAndComents.getBackground());
		commentLabel.setText("Comment:");

		commentText = new Text(langAndComents, SWT.BEGINNING | SWT.BORDER | Window.getDefaultOrientation());
		commentText.setText("");
		commentText.setEditable(true);
		commentText.setEnabled(true);
		commentText.setForeground(langAndComents.getForeground());
		commentText.setLayoutData(new GridData(GridData.FILL, 0, true, false));
		commentText.addListener(SWT.Modify,
				e -> executeCommand(new ChangeCommentCommand(getAlgorithm(), commentText.getText())));

		GridData codeEditorsGridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		codeEditorsGridData.horizontalSpan = 2;
		codeEditorsGridData.minimumHeight = 250;

		codeEditors = new Composite(parent, SWT.SHADOW_NONE); 
		codeEditors.setLayout(stack = new StackLayout());
		codeEditors.setLayoutData(codeEditorsGridData);

		disableAllFields();

	}

	@Override
	protected void enableAllFields() {
		languageLabel.setEnabled(true);
		commentLabel.setEnabled(true);
		commentText.setEnabled(true);
		languageCombo.setEnabled(true);
	}

	@Override
	protected void disableAllFields() {
		languageLabel.setEnabled(false);
		commentLabel.setEnabled(false);
		commentText.setEnabled(false);
		languageCombo.setEnabled(false);
	}

	@Override
	protected void updateAlgFields() {
		commentText.setText(getAlgorithm().getComment());
		languageCombo.select(languageCombo.indexOf(getAlgorithmTypeString(getAlgorithm())));
		if (null != currentAlgEditor) {
			currentAlgEditor.setAlgorithmText(((TextAlgorithm) getAlgorithm()).getText());
		}
	}

	@Override
	protected Command getChangeAlgorithmTypeCommand(BaseFBType fbType, Algorithm oldAlgorithm, String algorithmType) {
		return new ChangeAlgorithmTypeCommandSimpleFB(fbType, getAlgorithm(), languageCombo.getText());
	}
	
	protected SimpleFBType getSimpleFBType() {
		return (SimpleFBType) currentAlgorithm.eContainer();
	}
}

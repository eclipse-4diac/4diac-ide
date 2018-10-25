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
 *   Martin Melik-Merkumians
 *   - changes access modifiers for OO encapsulation
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

		Composite langAndComments = new Composite(parent, SWT.NONE | Window.getDefaultOrientation());
		langAndComments.setBackground(parent.getBackground());

		langAndComments.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				((Control) e.widget).setFocus();
			}
		});

		if (langAndComments.getParent() != null) {
			langAndComments.setMenu(langAndComments.getParent().getMenu());
		}

		langAndComments.setBackground(parent.getBackground());

		langAndComments.setLayout(new GridLayout(2, false));
		langAndComments.setLayoutData(new GridData(GridData.FILL, 0, true, false));

		languageLabel = new CLabel(langAndComments, SWT.BEGINNING);
		languageLabel.setBackground(langAndComments.getBackground());
		languageLabel.setText("Language:");

		languageCombo = new Combo(langAndComments, SWT.BEGINNING | SWT.SINGLE | SWT.READ_ONLY);

		fillLanguageDropDown();

		languageCombo.addListener(SWT.Selection, event -> {
			Command changeAlgorithmTypeCommand = getChangeAlgorithmTypeCommand(getSimpleFBType(), getAlgorithm(),
					languageCombo.getText());
			executeCommand(changeAlgorithmTypeCommand);
			setAlgorithm(((ChangeAlgorithmTypeCommandSimpleFB) changeAlgorithmTypeCommand).getNewAlgorithm());
		});
		commentLabel = new CLabel(langAndComments, SWT.BEGINNING);
		commentLabel.setBackground(langAndComments.getBackground());
		commentLabel.setText("Comment:");

		commentText = new Text(langAndComments, SWT.BEGINNING | SWT.BORDER | Window.getDefaultOrientation());
		commentText.setText("");
		commentText.setEditable(true);
		commentText.setEnabled(true);
		commentText.setForeground(langAndComments.getForeground());
		commentText.setLayoutData(new GridData(GridData.FILL, 0, true, false));
		commentText.addListener(SWT.Modify,
				e -> executeCommand(new ChangeCommentCommand(getAlgorithm(), commentText.getText())));

		GridData codeEditorsGridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		codeEditorsGridData.horizontalSpan = 2;
		codeEditorsGridData.minimumHeight = 250;

		codeEditors = new Composite(parent, SWT.SHADOW_NONE);
		codeEditors.setLayout(stack);
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
		return new ChangeAlgorithmTypeCommandSimpleFB(fbType, oldAlgorithm, algorithmType);
	}

	private SimpleFBType getSimpleFBType() {
		return (SimpleFBType) currentAlgorithm.eContainer();
	}
}

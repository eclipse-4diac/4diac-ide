/*******************************************************************************
 * Copyright (c) 2016 - 2018 fortiss GmbH, Johannes Kepler University Linz (JKU)
 * 				 2018 TU Wien/ACIN
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl 
 *   - initial API and implementation and/or initial documentation
 *   Peter Gsellmann
 *   - extraction to AlgorithmEditingComposite
 *   Martin Melik-Merkumians
 *   - moved AlgorithmGroup specific methods from base class to this class
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeAlgorithmTypeCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.widgets.AlgorithmEditingComposite;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

class AlgorithmGroup extends AlgorithmEditingComposite {

	AlgorithmGroup(final Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		algorithmGroup = widgetFactory.createGroup(parent, Messages.ECAlgorithmGroup_Title);
		GridData algorithmGroupLayoutData = new GridData(GridData.FILL, GridData.FILL, true, true);
		algorithmGroupLayoutData.horizontalSpan = 2;
		algorithmGroup.setLayoutData(algorithmGroupLayoutData);
		algorithmGroup.setLayout(new GridLayout(1, true));

		Composite langAndComments = widgetFactory.createComposite(algorithmGroup);
		langAndComments.setLayout(new GridLayout(4, false));
		langAndComments.setLayoutData(new GridData(GridData.FILL, 0, true, false));

		languageLabel = widgetFactory.createCLabel(langAndComments, "Language: ");
		languageCombo = new Combo(langAndComments, SWT.SINGLE | SWT.READ_ONLY);
		fillLanguageDropDown();
		languageCombo.addListener(SWT.Selection, event -> executeCommand(
				getChangeAlgorithmTypeCommand(getBasicFBType(), getAlgorithm(), languageCombo.getText())));

		commentLabel = widgetFactory.createCLabel(langAndComments, "Comment:");
		commentText = widgetFactory.createText(langAndComments, ""); //$NON-NLS-1$
		commentText.setEditable(true);
		commentText.setEnabled(true);
		commentText.setLayoutData(new GridData(GridData.FILL, 0, true, false));
		commentText.addListener(SWT.Modify,
				e -> executeCommand(new ChangeCommentCommand(getAlgorithm(), commentText.getText())));

		GridData codeEditorsGridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		codeEditorsGridData.horizontalSpan = 1;
		codeEditorsGridData.minimumHeight = 250;
		codeEditors = widgetFactory.createGroup(algorithmGroup, ""); //$NON-NLS-1$
		codeEditors.setLayout(stack);
		codeEditors.setLayoutData(codeEditorsGridData);

		disableAllFields();
	}

	private BasicFBType getBasicFBType() {
		return (BasicFBType) currentAlgorithm.eContainer();
	}

	@Override
	protected void enableAllFields() {
		languageLabel.setEnabled(true);
		algorithmGroup.setEnabled(true);
		commentLabel.setEnabled(true);
		commentText.setEnabled(true);
		languageCombo.setEnabled(true);
	}

	@Override
	protected void disableAllFields() {
		languageLabel.setEnabled(false);
		algorithmGroup.setEnabled(false);
		commentLabel.setEnabled(false);
		commentText.setEnabled(false);
		languageCombo.setEnabled(false);
	}

	@Override
	protected void updateAlgFields() {
		algorithmGroup.setText(Messages.ECAlgorithmGroup_Title + " " + currentAlgorithm.getName());
		commentText.setText(getAlgorithm().getComment());
		languageCombo.select(languageCombo.indexOf(getAlgorithmTypeString(getAlgorithm())));
		if (null != currentAlgEditor) {
			currentAlgEditor.setAlgorithmText(((TextAlgorithm) getAlgorithm()).getText());
		}
	}

	@Override
	protected Command getChangeAlgorithmTypeCommand(BaseFBType fbType, Algorithm oldAlgorithm, String algorithmType) {
		return new ChangeAlgorithmTypeCommand(fbType, oldAlgorithm, algorithmType);
	}

}

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
 *   
 *   Peter Gsellmann
 *   - extraction to AlgorithmEditingComposite
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ChangeAlgorithmTypeCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.widgets.AlgorithmEditingComposite;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
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

		Composite langAndComents = widgetFactory.createComposite(algorithmGroup);
		langAndComents.setLayout(new GridLayout(4, false));
		langAndComents.setLayoutData(new GridData(GridData.FILL, 0, true, false));

		languageLabel = widgetFactory.createCLabel(langAndComents, "Langugage: ");
		languageCombo = new Combo(langAndComents, SWT.SINGLE | SWT.READ_ONLY);
		fillLanguageDropDown();
		languageCombo.addListener(SWT.Selection, event -> executeCommand(getChangeAlgorithmTypeCommand(getBasicFBType(), getAlgorithm(), languageCombo.getText())));

		commentLabel = widgetFactory.createCLabel(langAndComents, "Comment:");
		commentText = widgetFactory.createText(langAndComents, ""); //$NON-NLS-1$
		commentText.setEditable(true);
		commentText.setEnabled(true);
		commentText.setLayoutData(new GridData(GridData.FILL, 0, true, false));
		commentText.addListener(SWT.Modify,
				e -> executeCommand(new ChangeCommentCommand(getAlgorithm(), commentText.getText())));

		GridData codeEditorsGridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		codeEditorsGridData.horizontalSpan = 1;
		codeEditorsGridData.minimumHeight = 250;
		codeEditors = widgetFactory.createGroup(algorithmGroup, ""); // widgetFactory.createComposite(algorithmGroup, //$NON-NLS-1$
																		// SWT.BORDER);
		codeEditors.setLayout(stack = new StackLayout());
		codeEditors.setLayoutData(codeEditorsGridData);

		disableAllFields();
	}

	@Override
	protected Command getChangeAlgorithmTypeCommand(BaseFBType fbType, Algorithm oldAlgorithm, String algorithmType) {
		return new ChangeAlgorithmTypeCommand(fbType, oldAlgorithm, algorithmType);
	}

}

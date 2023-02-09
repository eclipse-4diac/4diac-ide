/*******************************************************************************
 * Copyright (c) 2016 - 2018 fortiss GmbH, Johannes Kepler University Linz (JKU)
 * 				 2018 TU Wien/ACIN
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Peter Gsellmann - extraction to AlgorithmEditingComposite
 *   Martin Melik-Merkumians - moved AlgorithmGroup specific methods from base
 *   						   class to this class
 *   Alois Zoitl - moved more code into AlgorithmEditingComposite
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.properties;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.widgets.AlgorithmEditingComposite;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

class AlgorithmGroup extends AlgorithmEditingComposite {

	private Group group;

	@Override
	public void createControls(final Composite parent, final FormToolkit toolkit) {
		if (toolkit instanceof TabbedPropertySheetWidgetFactory) {
			group = ((TabbedPropertySheetWidgetFactory) toolkit).createGroup(parent,
					MessageFormat.format(Messages.ECAlgorithmGroup_Title, "")); //$NON-NLS-1$
		} else {
			group = new Group(parent, SWT.SHADOW_NONE);
			group.setText(MessageFormat.format(Messages.ECAlgorithmGroup_Title, "")); //$NON-NLS-1$
		}
		final GridData algorithmGroupLayoutData = new GridData(GridData.FILL, GridData.FILL, true, true);
		algorithmGroupLayoutData.horizontalSpan = 2;
		group.setLayoutData(algorithmGroupLayoutData);
		group.setLayout(new GridLayout(1, true));

		super.createControls(group, toolkit);
	}

	@Override
	protected void enableAllFields() {
		group.setEnabled(true);
		super.enableAllFields();
	}

	@Override
	protected void disableAllFields() {
		group.setEnabled(false);
		super.disableAllFields();
	}

	@Override
	protected void updateAlgFields() {
		final Algorithm alg = getAlgorithm();
		group.setText(MessageFormat.format(Messages.ECAlgorithmGroup_Title, (null != alg) ? alg.getName() : "")); //$NON-NLS-1$
		super.updateAlgFields();
	}


}

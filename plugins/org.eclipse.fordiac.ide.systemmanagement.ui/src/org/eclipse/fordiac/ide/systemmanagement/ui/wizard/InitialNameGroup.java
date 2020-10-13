/*******************************************************************************
 * Copyright (c) 2014  fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - extracted the initial name handling from the wizard page to
 *                 make it more reusable
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.wizard;

import org.eclipse.fordiac.ide.model.IdentifierVerifyer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/* Widget that maintains an text field which is updated automatically with an initial name.
 * If the user specifies an own name the automatic update is not performed anymore.
 */

public class InitialNameGroup extends Composite {

	private Text name;

	// flag indicating if the user changed the initial application name if yes do
	// not update the name any more
	private boolean nameManuallyChanged = false;

	private boolean blockListeners = false;

	private Listener applicationNameModifyListener = e -> {
		if (!blockListeners) {
			nameManuallyChanged = true;
		}
	};

	public InitialNameGroup(Composite parent, String labelText) {
		super(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		setLayout(layout);
		setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label applicationLabel = new Label(this, SWT.NONE);
		applicationLabel.setText(labelText);
		applicationLabel.setFont(parent.getFont());

		// new project name entry field
		name = new Text(this, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(data);
		name.setFont(parent.getFont());
		name.addListener(SWT.Modify, applicationNameModifyListener);
	}

	public String getInitialName() {
		return name.getText();
	}

	public boolean validateName(String projectName) {
		if (!nameManuallyChanged) {
			blockListeners = true;
			name.setText(projectName);
			blockListeners = false;
		} else if (!IdentifierVerifyer.isValidIdentifier(getInitialName())) {
			return false;
		}
		return true;
	}

	public void addNameModifyListener(Listener nameModifyListener) {
		name.addListener(SWT.Modify, nameModifyListener);
	}

}

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

import org.eclipse.fordiac.ide.model.IdentifierVerifier;
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

	private final Text name;

	// flag indicating if the user changed the initial application name if yes do
	// not update the name any more
	private boolean nameManuallyChanged = false;

	private boolean blockListeners = false;

	private final Listener applicationNameModifyListener = e -> {
		if (!blockListeners) {
			nameManuallyChanged = true;
		}
	};

	public InitialNameGroup(final Composite parent, final String labelText) {
		super(parent, SWT.NONE);
		final GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		setLayout(layout);
		setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Label applicationLabel = new Label(this, SWT.NONE);
		applicationLabel.setText(labelText);
		applicationLabel.setFont(parent.getFont());

		// new project name entry field
		name = new Text(this, SWT.BORDER);
		final GridData data = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(data);
		name.setFont(parent.getFont());
		name.addListener(SWT.Modify, applicationNameModifyListener);
	}

	public String getInitialName() {
		return name.getText();
	}

	public boolean validateName(final String projectName) {
		if (!nameManuallyChanged) {
			blockListeners = true;
			name.setText(projectName);
			blockListeners = false;
		} else if (IdentifierVerifier.verifyIdentifier(getInitialName()).isPresent()) {
			return false;
		}
		return true;
	}

	public void addNameModifyListener(final Listener nameModifyListener) {
		name.addListener(SWT.Modify, nameModifyListener);
	}

}

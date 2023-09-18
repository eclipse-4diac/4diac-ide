/*******************************************************************************
 * Copyright (c) 2023 Fabio Gandolfi
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.view;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.fordiac.ide.fb.interpreter.api.CoverageCalculator;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class GetCoverageDialog extends MessageDialog {

	HashMap<String, Integer> visitedStates;
	HashMap<ArrayList<String>, Integer> visitedPaths;

	public GetCoverageDialog(final Shell parentShell, final HashMap<String, Integer> visitedStates,
			final HashMap<ArrayList<String>, Integer> visitedPaths) {
		super(parentShell, Messages.Coverage_NAME, null, "Shows the Coverage of the given Tests:", //$NON-NLS-1$
				MessageDialog.INFORMATION, 0, "OK"); //$NON-NLS-1$
		this.visitedStates = visitedStates;
		this.visitedPaths = visitedPaths;
	}

	@Override
	protected Control createCustomArea(final Composite parent) {
		parent.setLayout(new FillLayout());
		final Composite dialogArea = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout(2, false);
		dialogArea.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));
		dialogArea.setLayout(layout);

		final Group vistedStatesGroup = new Group(dialogArea, SWT.NONE);
		vistedStatesGroup.setText(Messages.Coverage_VISITED_STATES);
		vistedStatesGroup.setLayout(new GridLayout(1, false));
		vistedStatesGroup.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true));

		visitedStates.entrySet().forEach(entry -> {
			final Label label = new Label(vistedStatesGroup, SWT.None);
			label.setText(entry.getKey() + "   :    " + entry.getValue() + " \n");
		});

		Label label = new Label(vistedStatesGroup, SWT.None);
		label.setText(Messages.Coverage_NODECOVERAGE + ": "
				+ CoverageCalculator.calculateNodeCoverageOfSuiteBy(visitedStates) * 100 + "% \n");

		final Group vistedPathsGroup = new Group(dialogArea, SWT.NONE);
		vistedPathsGroup.setText(Messages.Coverage_VISITED_PATHS);
		vistedPathsGroup.setLayout(new GridLayout(1, false));
		vistedPathsGroup.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, true));

		visitedPaths.entrySet().forEach(entry -> {
			final Label label2 = new Label(vistedPathsGroup, SWT.None);
			String pathString = "";

			for (final String state : entry.getKey()) {
				pathString += state + " -> ";
			}
			pathString = pathString.substring(0, pathString.length() - 4);
			label2.setText(pathString + "   :    " + entry.getValue() + " \n");
		});

		label = new Label(vistedPathsGroup, SWT.None);
		label.setText(Messages.Coverage_PATHCOVERAGE + ": "
				+ CoverageCalculator.calculatePathCoverageOfSuiteBy(visitedPaths) * 100 + "% \n");

		return dialogArea;
	}
}

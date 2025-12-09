/*******************************************************************************
 * Copyright (c) 2025 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.wizard;

import java.io.File;
import java.util.Arrays;

import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

public final class New4diacExampleProjectPage extends WizardNewProjectCreationPage {

	private ListViewer exampleListViewer;
	private Text projectNameText;

	public New4diacExampleProjectPage() {
		super(Messages.New4diacExampleWizard_WizardTitle);
		setPageComplete(false);
		setDescription(Messages.New4diacExampleWizard_WizardDesc);
	}

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);

		final Composite container = (Composite) getControl();
		projectNameText = (Text) ((Composite) container.getChildren()[0]).getChildren()[1];

		createExampleList(container);

		setControl(container);
	}

	@Override
	protected boolean validatePage() {
		final boolean validatePage = super.validatePage();
		if (validatePage && getExample() == null) {
			setErrorMessage(null);
			setMessage(Messages.New4diacExampleWizard_NoExampleSelected);
		}
		return validatePage;
	}

	public File getExample() {
		final IStructuredSelection structuredSelection = exampleListViewer.getStructuredSelection();
		if (!structuredSelection.isEmpty() && structuredSelection.getFirstElement() instanceof final File file) {
			return file;
		}
		return null;
	}

	public static String getExampleName(final File exampleFile) {
		final String exampleName = removeFileEnding(exampleFile.getName());

		// insert space before upper case letters
		final StringBuilder result = new StringBuilder();
		result.append(exampleName.charAt(0));
		for (int i = 1; i < exampleName.length(); i++) {
			final char c = exampleName.charAt(i);
			if (Character.isUpperCase(c) && i != 0) {
				result.append(' ');
			}
			result.append(c);
		}

		return result.toString();
	}

	private void createExampleList(final Composite parent) {
		final Composite listComposite = new Composite(parent, SWT.NONE);
		listComposite.setLayout(new GridLayout());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(listComposite);

		// move our example selection to the top
		listComposite.moveAbove(parent.getChildren()[0]);

		final Label label = new Label(listComposite, SWT.NONE);
		label.setText(Messages.New4diacExampleWizard_SelectExample);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(label);

		exampleListViewer = new ListViewer(listComposite, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(exampleListViewer.getControl());
		exampleListViewer.setContentProvider(new ArrayContentProvider());
		exampleListViewer.setLabelProvider(new ExamplesLabelProvider());
		exampleListViewer.setInput(getExamples());

		exampleListViewer.addSelectionChangedListener(ev -> handleExampleListSelection());
	}

	private void handleExampleListSelection() {
		final File example = getExample();
		if (example != null) {
			final String projectName = removeFileEnding(example.getName());
			projectNameText.setText(projectName);
			setInitialProjectName(projectName);
		}
		setPageComplete(validatePage());
	}

	private static File[] getExamples() {
		final File templateFolder = getTypeExamplesFolder();
		if (templateFolder.isDirectory()) {
			final File[] files = templateFolder.listFiles(pn -> pn.getName().toUpperCase().endsWith(".ZIP")); //$NON-NLS-1$
			if (null != files) {
				Arrays.sort(files);
				return files;
			}
		}
		return new File[0];
	}

	private static File getTypeExamplesFolder() {
		final String installLocPath = Platform.getInstallLocation().getURL().getFile();
		return new File(installLocPath + File.separatorChar + "examples"); //$NON-NLS-1$
	}

	private static final class ExamplesLabelProvider extends LabelProvider {

		@Override
		public String getText(final Object element) {
			if (element instanceof final File file) {
				return getExampleName(file);
			}
			return super.getText(element);
		}

	}

	private static String removeFileEnding(final String name) {
		String exampleName = name;
		final int lastDotIndex = exampleName.lastIndexOf('.');
		if (lastDotIndex > 0) {
			exampleName = exampleName.substring(0, lastDotIndex);
		}
		return exampleName;
	}
}

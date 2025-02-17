/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.search.ModelQuerySpec.SearchScope;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.widgets.LabelFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.search.ui.ISearchPage;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;

public class ModelSearchPage extends DialogPage implements ISearchPage {

	public static final int NUMBER_OF_SEARCH_OPTIONS = 4;
	public static final int NUMBER_OF_SCOPES = 2;

	private ISearchPageContainer container;
	private Button instanceName;
	private Button pinName;
	private Button type;
	private Button comment;
	private Text query;
	private Button caseSensitive;
	private Button exactNameMatching;
	private Button projectScope;
	private IProject curProject;

	public Button getInstanceName() {
		return instanceName;
	}

	public Button getPinName() {
		return pinName;
	}

	public Button getType() {
		return type;
	}

	public Button getComment() {
		return comment;
	}

	public ISearchPageContainer getContainer() {
		return container;
	}

	public Button getCaseSensitive() {
		return caseSensitive;
	}

	@Override
	public void createControl(final Composite parent) {
		GridLayoutFactory.fillDefaults().numColumns(1).margins(LayoutConstants.getMargins()).generateLayout(parent);

		// Factory for the big composite
		final Composite composite = WidgetFactory.composite(NONE).create(parent);
		GridLayoutFactory.fillDefaults().numColumns(1).generateLayout(composite);

		final Group fbInfoGroup = new Group(composite, SWT.NONE);
		fbInfoGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		fbInfoGroup.setLayout(new GridLayout(NUMBER_OF_SEARCH_OPTIONS, false));
		fbInfoGroup.setText(Messages.SearchFor);

		instanceName = WidgetFactory.button(SWT.CHECK).text(Messages.InstanceName).create(fbInfoGroup);
		pinName = WidgetFactory.button(SWT.CHECK).text(Messages.PinName).create(fbInfoGroup);
		type = WidgetFactory.button(SWT.CHECK).text(FordiacMessages.Type).create(fbInfoGroup);
		comment = WidgetFactory.button(SWT.CHECK).text(Messages.Comment).create(fbInfoGroup);

		instanceName.setSelection(true);
		pinName.setSelection(false);
		type.setSelection(true);
		comment.setSelection(true);

		final Composite searchBoxAndOptions = WidgetFactory.composite(NONE).create(composite);
		GridLayoutFactory.fillDefaults().numColumns(1).generateLayout(searchBoxAndOptions);

		// Text box for the actual search
		final LabelFactory labelFactory = LabelFactory.newLabel(SWT.NONE);
		labelFactory.text(Messages.ContainingText).create(composite);
		query = WidgetFactory.text(SWT.BORDER).message(Messages.TypeQuery).create(composite);
		query.setFocus();
		GridDataFactory.fillDefaults().grab(true, false).applyTo(query);

		labelFactory.text(Messages.Jokers).create(composite);

		caseSensitive = WidgetFactory.button(SWT.CHECK).text(Messages.CaseSensitive).create(composite);
		exactNameMatching = WidgetFactory.button(SWT.CHECK).text(Messages.ExactNameMatching).create(composite);

		final Group radioButtonScope = new Group(composite, SWT.NONE);
		radioButtonScope.setLayout(new RowLayout(SWT.VERTICAL));
		radioButtonScope.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		radioButtonScope.setLayout(new GridLayout(NUMBER_OF_SCOPES, false));
		radioButtonScope.setText(Messages.Scope);

		configureProjectScope(radioButtonScope);

		final Button workspaceScope = new Button(radioButtonScope, SWT.RADIO);
		workspaceScope.setText(Messages.WorkspaceScope);
		workspaceScope.setSelection(true); // This is the default

		setControl(composite);
	}

	private void configureProjectScope(final Group radioButtonScope) {
		projectScope = new Button(radioButtonScope, SWT.RADIO);
		curProject = getCurrentProject();
		projectScope.setEnabled(curProject != null); // only if we have a project allow to select it.
		projectScope.setText(
				MessageFormat.format(Messages.ProjectScope, (curProject != null) ? curProject.getName() : "[none]"));
	}

	@Override
	public boolean performAction() {
		// What type is the user searching for
		final boolean isCheckedInstanceName = instanceName.getSelection();
		final boolean isCheckedPinName = pinName.getSelection();
		final boolean isCheckedType = type.getSelection();
		final boolean isCheckedComment = comment.getSelection();
		final boolean isCaseSensitive = caseSensitive.getSelection();
		final boolean isExactNameMatching = exactNameMatching.getSelection();

		// Search string aka the name of it
		final String searchString = query.getText();

		// Check to see if at least one of the check boxes is ticked and if the search
		// string exists
		final boolean optionSelected = isCheckedInstanceName || isCheckedPinName || isCheckedType || isCheckedComment;
		if (!"".equals(searchString) && optionSelected) { //$NON-NLS-1$
			// @formatter:off
			final ModelQuerySpec modelQuerySpec = new ModelQuerySpec(
					searchString,
					isCheckedInstanceName,
					isCheckedPinName,
					isCheckedType,
					isCheckedComment,
					isCaseSensitive,
					isExactNameMatching,
					getScope(),
					curProject);
			// @formatter:on
			final ModelSearchQuery searchJob = new ModelSearchQuery(modelQuerySpec);
			NewSearchUI.runQueryInBackground(searchJob, NewSearchUI.getSearchResultView());

			// close the page
			return true;

		}
		errorDialogDisplay();
		// dialog remains open so the user can fix the search parameters
		return false;

	}

	private ModelQuerySpec.SearchScope getScope() {
		if (projectScope.getSelection() && curProject != null) {
			return SearchScope.PROJECT;
		}
		return SearchScope.WORKSPACE;
	}

	private void errorDialogDisplay() {
		MessageDialog.openWarning(getShell(), Messages.Warning, Messages.ErrorMessageSearch);
	}

	@Override
	public void setContainer(final ISearchPageContainer container) {
		this.container = container;
	}

	private static IProject getCurrentProject() {
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IProject project = getProjectFromActiveEditor(page);
		if (project == null) {
			project = getProjectFromProjectExplorerSelction(page);
		}
		return project;
	}

	private static IProject getProjectFromActiveEditor(final IWorkbenchPage page) {
		final IEditorPart openEditor = page.getActiveEditor();
		if (openEditor != null) {
			final IEditorInput editorInput = openEditor.getEditorInput();
			if (editorInput instanceof final IFileEditorInput ifEI) {
				return ifEI.getFile().getProject();
			}
		}
		return null;
	}

	private static IProject getProjectFromProjectExplorerSelction(final IWorkbenchPage page) {
		final IViewPart view = page.findView("org.eclipse.fordiac.ide.systemmanagement.ui.systemexplorer"); //$NON-NLS-1$

		if (view instanceof final CommonNavigator cn) {
			final ISelection selection = cn.getCommonViewer().getSelection();
			if (selection instanceof final StructuredSelection structSel && !structSel.isEmpty()) {
				Object selElement = structSel.getFirstElement();
				if (selElement instanceof final EObject eo) {
					selElement = getFileForModel(eo);
				}
				if (selElement instanceof final IResource iRes) {
					return iRes.getProject();
				}
			}
		}

		return null;
	}

	private static IFile getFileForModel(final EObject sel) {
		final EObject root = EcoreUtil.getRootContainer(sel);
		if (root instanceof final LibraryElement le) {
			return le.getTypeEntry().getFile();
		}
		return null;
	}

}

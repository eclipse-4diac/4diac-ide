/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.properties;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.buildpath.Attribute;
import org.eclipse.fordiac.ide.model.buildpath.Buildpath;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathAttributes;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathFactory;
import org.eclipse.fordiac.ide.model.buildpath.Pattern;
import org.eclipse.fordiac.ide.model.buildpath.SourceFolder;
import org.eclipse.fordiac.ide.model.buildpath.provider.BuildpathContentProvider;
import org.eclipse.fordiac.ide.model.buildpath.provider.BuildpathLabelProvider;
import org.eclipse.fordiac.ide.model.buildpath.util.BuildpathUtil;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.PropertyPage;

public class BuildPathPropertyPage extends PropertyPage {

	private Buildpath buildpath;
	private TreeViewer treeViewer;

	private Button addIncludeButton;
	private Button addExcludeButton;
	private Button editButton;
	private Button removeButton;

	@Override
	protected Control createContents(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);

		treeViewer = new TreeViewer(composite);
		treeViewer.setContentProvider(new BuildpathContentProvider());
		treeViewer.setLabelProvider(new BuildpathLabelProvider());
		treeViewer.setInput(getBuildpath());
		treeViewer.addSelectionChangedListener(event -> updateButtons());

		createButtons(composite);

		GridLayoutFactory.swtDefaults().numColumns(2).generateLayout(composite);
		return composite;
	}

	protected Composite createButtons(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);

		final Button addFolderButton = new Button(composite, SWT.PUSH);
		addFolderButton.setText(Messages.BuildPathPropertyPage_AddFolder);
		addFolderButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(this::performAddFolder));

		addIncludeButton = new Button(composite, SWT.PUSH);
		addIncludeButton.setText(Messages.BuildPathPropertyPage_AddInclude);
		addIncludeButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(this::performAddInclude));

		addExcludeButton = new Button(composite, SWT.PUSH);
		addExcludeButton.setText(Messages.BuildPathPropertyPage_AddExclude);
		addExcludeButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(this::performAddExclude));

		editButton = new Button(composite, SWT.PUSH);
		editButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(this::performEdit));

		removeButton = new Button(composite, SWT.PUSH);
		removeButton.setText(Messages.BuildPathPropertyPage_Remove);
		removeButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(this::performRemove));

		updateButtons();

		GridLayoutFactory.swtDefaults().generateLayout(composite);
		return composite;
	}

	@SuppressWarnings("unchecked")
	protected void updateButtons() {
		final IStructuredSelection selection = treeViewer.getStructuredSelection();
		addIncludeButton.setEnabled(selection.size() == 1 && selection.getFirstElement() instanceof final SourceFolder);
		addExcludeButton.setEnabled(selection.size() == 1 && selection.getFirstElement() instanceof final SourceFolder);
		editButton.setEnabled(selection.size() == 1);
		editButton.setText(selection.size() == 1 && selection.getFirstElement() instanceof final Attribute attribute
				&& isBooleanAttribute(attribute) ? Messages.BuildPathPropertyPage_Toggle
						: Messages.BuildPathPropertyPage_Edit);
		removeButton.setEnabled(!selection.isEmpty() && StreamSupport.stream(selection.spliterator(), false)
				.allMatch(((Predicate<Object>) SourceFolder.class::isInstance).or(Pattern.class::isInstance)));
	}

	protected void performAddFolder(final SelectionEvent event) {
		final ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), getProject(), false,
				Messages.BuildPathPropertyPage_AddSourceFolder);
		if (dialog.open() == Window.OK) {
			final Object[] result = dialog.getResult();
			if (result.length == 1 && result[0] instanceof final IPath path) {
				final SourceFolder sourceFolder = BuildpathFactory.eINSTANCE.createSourceFolder();
				sourceFolder.setName(path.makeRelativeTo(getProject().getFullPath()).toString());
				addDefaultAttributes(sourceFolder);
				getBuildpath().getSourceFolders().add(sourceFolder);
			}
		}
	}

	protected void performAddInclude(final SelectionEvent event) {
		final Object element = treeViewer.getStructuredSelection().getFirstElement();
		if (element instanceof final SourceFolder sourceFolder) {
			performAddPattern(sourceFolder.getIncludes(), Messages.BuildPathPropertyPage_AddIncludePattern);
		}
	}

	protected void performAddExclude(final SelectionEvent event) {
		final Object element = treeViewer.getStructuredSelection().getFirstElement();
		if (element instanceof final SourceFolder sourceFolder) {
			performAddPattern(sourceFolder.getExcludes(), Messages.BuildPathPropertyPage_AddExcludePattern);
		}
	}

	protected void performAddPattern(final List<Pattern> patterns, final String message) {
		final InputDialog dialog = new InputDialog(getShell(), message, Messages.BuildPathPropertyPage_EnterNewPattern,
				"", // $NON-NLS-2$
				this::validatePatternValue);
		if (dialog.open() == Window.OK) {
			final Pattern pattern = BuildpathFactory.eINSTANCE.createPattern();
			pattern.setValue(dialog.getValue());
			patterns.add(pattern);
		}
	}

	@SuppressWarnings("unchecked")
	protected void performRemove(final SelectionEvent event) {
		EcoreUtil.removeAll(StreamSupport.stream(treeViewer.getStructuredSelection().spliterator(), false)
				.filter(EObject.class::isInstance).map(EObject.class::cast).toList());
	}

	protected void performEdit(final SelectionEvent event) {
		final Object element = treeViewer.getStructuredSelection().getFirstElement();
		if (element instanceof final Attribute attribute) {
			performEdit(attribute);
		} else if (element instanceof final SourceFolder sourceFolder) {
			performEdit(sourceFolder);
		} else if (element instanceof final Pattern pattern) {
			performEdit(pattern);
		}
	}

	protected void performEdit(final Attribute attribute) {
		if (isBooleanAttribute(attribute)) {
			attribute.setValue(Boolean.toString(!Boolean.parseBoolean(attribute.getValue())));
		} else {
			final InputDialog dialog = new InputDialog(getShell(), Messages.BuildPathPropertyPage_EditAttribute,
					Messages.BuildPathPropertyPage_EnterNewValue, attribute.getValue(), null);
			if (dialog.open() == Window.OK) {
				attribute.setValue(dialog.getValue());
			}
		}
	}

	protected void performEdit(final SourceFolder sourceFolder) {
		final ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), getProject(), false,
				Messages.BuildPathPropertyPage_EditSourceFolder);
		if (dialog.open() == Window.OK) {
			final Object[] result = dialog.getResult();
			if (result.length == 1 && result[0] instanceof final IPath path) {
				sourceFolder.setName(path.makeRelativeTo(getProject().getFullPath()).toString());
			}
		}
	}

	protected void performEdit(final Pattern pattern) {
		final InputDialog dialog = new InputDialog(getShell(), Messages.BuildPathPropertyPage_EditPattern,
				Messages.BuildPathPropertyPage_EnterNewPattern, pattern.getValue(), this::validatePatternValue);
		if (dialog.open() == Window.OK) {
			pattern.setValue(dialog.getValue());
		}
	}

	protected String validatePatternValue(final String value) {
		if (value.isEmpty()) {
			return Messages.BuildPathPropertyPage_EmptyPatternError;
		}
		return null;
	}

	protected static boolean isBooleanAttribute(final Attribute attribute) {
		return BuildpathAttributes.getAttributeType(attribute.getName()) == Boolean.class;
	}

	@Override
	protected void performDefaults() {
		buildpath = BuildpathUtil.createDefaultBuildpath(getProject());
		addDefaultAttributes(buildpath);
		treeViewer.setInput(buildpath);
		updateButtons();
		super.performDefaults();
	}

	@Override
	public boolean performOk() {
		try {
			removeDefaultAttributes(buildpath);
			BuildpathUtil.saveBuildpath(buildpath);
			addDefaultAttributes(buildpath);
			TypeLibraryManager.INSTANCE.refreshTypeLib(getProject());
		} catch (final Exception e) {
			MessageDialog.openError(getShell(), Messages.BuildPathPropertyPage_SaveError, e.getLocalizedMessage());
			return false;
		}
		return true;
	}

	protected void addDefaultAttributes(final Buildpath buildpath) {
		buildpath.getSourceFolders().forEach(this::addDefaultAttributes);
	}

	protected void addDefaultAttributes(final SourceFolder sourceFolder) {
		BuildpathAttributes.addDefaultAttributes(sourceFolder.getAttributes());
	}

	protected void removeDefaultAttributes(final Buildpath buildpath) {
		buildpath.getSourceFolders().forEach(this::removeDefaultAttributes);
	}

	protected void removeDefaultAttributes(final SourceFolder sourceFolder) {
		BuildpathAttributes.removeDefaultAttributes(sourceFolder.getAttributes());
	}

	protected Buildpath getBuildpath() {
		if (buildpath == null) {
			buildpath = BuildpathUtil.loadBuildpath(getProject());
			addDefaultAttributes(buildpath);
		}
		return buildpath;
	}

	protected IProject getProject() {
		return Adapters.adapt(getElement(), IProject.class);
	}
}
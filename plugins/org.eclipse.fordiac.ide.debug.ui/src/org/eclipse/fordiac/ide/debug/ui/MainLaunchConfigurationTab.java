/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.ui;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.fordiac.ide.debug.LaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.gef.widgets.VariableWidget;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public abstract class MainLaunchConfigurationTab extends AbstractLaunchConfigurationTab {
	public static final String ID = "org.eclipse.fordiac.ide.debug.ui.mainTab"; //$NON-NLS-1$

	private Text resourceText;
	private Button stopOnFirstLineCheckbox;
	private VariableWidget argumentsWidget;

	private List<Variable<?>> arguments;

	@Override
	public void createControl(final Composite parent) {
		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(comp);
		setControl(comp);

		final Composite resourceComponent = createResourceComponent(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(resourceComponent);

		final Composite optionsComponent = createOptionsComponent(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(optionsComponent);
	}

	protected Composite createResourceComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().applyTo(group);
		group.setText("Target"); //$NON-NLS-1$

		final Composite comp = new Composite(group, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(3).applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(comp);

		final Label resourceLabel = new Label(comp, SWT.NONE);
		resourceLabel.setText("Location:"); //$NON-NLS-1$
		GridDataFactory.swtDefaults().applyTo(resourceLabel);

		resourceText = new Text(comp, SWT.BORDER);
		resourceText.setEnabled(false);
		resourceText.setMessage("Location"); //$NON-NLS-1$
		resourceText.addModifyListener(e -> scheduleUpdateJob());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(resourceText);

		final Button resourceButton = new Button(comp, SWT.BORDER);
		resourceButton.setText("Browse..."); //$NON-NLS-1$
		resourceButton.addSelectionListener(widgetSelectedAdapter(e -> handleResourceButtonSelected()));
		GridDataFactory.swtDefaults().applyTo(resourceButton);
		return group;
	}

	protected Composite createOptionsComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().applyTo(group);
		group.setText("Options"); //$NON-NLS-1$

		final Composite comp = new Composite(group, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(1).applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(comp);

		stopOnFirstLineCheckbox = new Button(comp, SWT.CHECK);
		stopOnFirstLineCheckbox.setText("Stop on first line"); //$NON-NLS-1$
		stopOnFirstLineCheckbox.addSelectionListener(widgetSelectedAdapter(e -> updateLaunchConfigurationDialog()));
		GridDataFactory.fillDefaults().applyTo(stopOnFirstLineCheckbox);
		return group;
	}

	protected Composite createArgumentsComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().applyTo(group);
		group.setText("Arguments"); //$NON-NLS-1$

		argumentsWidget = new VariableWidget();
		argumentsWidget
				.addVariableModificationListener((variable, oldValue, newValue) -> updateLaunchConfigurationDialog());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(argumentsWidget.createWidget(group));

		return group;
	}

	private void handleResourceButtonSelected() {
		final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(),
				new WorkbenchLabelProvider(), new WorkbenchContentProvider());
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		final IResource initialResource = getResource();
		if (initialResource != null) {
			dialog.setInitialSelection(initialResource);
		}
		dialog.setAllowMultiple(false);
		dialog.addFilter(new ViewerFilter() {

			@Override
			public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
				final IResource resource = Adapters.adapt(element, IResource.class);
				try {
					return filterTargetResource(resource);
				} catch (final CoreException e) {
					return false;
				}
			}
		});
		dialog.open();
		final Object[] result = dialog.getResult();
		if (result != null && result.length > 0 && result[0] instanceof final IResource resource) {
			final String resourceString = resource.getFullPath().toString();
			resourceText.setText(resourceString);
			handleResourceUpdated();
		}
	}

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.removeAttribute(LaunchConfigurationAttributes.RESOURCE);
		configuration.removeAttribute(LaunchConfigurationAttributes.ARGUMENTS);
		configuration.removeAttribute(LaunchConfigurationAttributes.STOP_ON_FIRST_LINE);
	}

	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		try {
			final String resourceAttribute = configuration.getAttribute(LaunchConfigurationAttributes.RESOURCE, ""); //$NON-NLS-1$
			resourceText.setText(resourceAttribute);
			stopOnFirstLineCheckbox.setSelection(LaunchConfigurationAttributes.isStopOnFirstLine(configuration));
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
	}

	protected void initializeArgumentsFrom(final ILaunchConfiguration configuration) {
		try {
			arguments = LaunchConfigurationAttributes.getArguments(configuration, getDefaultArguments());
		} catch (final CoreException e) {
			ErrorDialog.openError(getShell(), Messages.MainLaunchConfigurationTab_ConfigurationError,
					Messages.MainLaunchConfigurationTab_ErrorInitializingArguments, e.getStatus());
			arguments = Collections.emptyList();
		}
		argumentsWidget.setInput(arguments);
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		final String resourceString = resourceText.getText();
		configuration.setAttribute(LaunchConfigurationAttributes.RESOURCE, resourceString);
		if (arguments != null) {
			configuration.setAttribute(LaunchConfigurationAttributes.ARGUMENTS,
					arguments.stream().collect(Collectors.toMap(Variable::getName, Variable::toString)));
		} else {
			configuration.removeAttribute(LaunchConfigurationAttributes.ARGUMENTS);
		}
		configuration.setAttribute(LaunchConfigurationAttributes.STOP_ON_FIRST_LINE,
				stopOnFirstLineCheckbox.getSelection());
	}

	protected void handleResourceUpdated() {
		updateLaunchConfigurationDialog();
	}

	protected void updateArguments() {
		try {
			final var oldArguments = arguments;
			arguments = getDefaultArguments();
			if (oldArguments != null && arguments != null) {
				arguments.forEach(variable -> oldArguments.stream()
						.filter(arg -> Objects.equals(arg.getName(), variable.getName())).findFirst().ifPresent(arg -> {
							try {
								variable.setValue(arg.getValue().toString());
							} catch (final Exception e) {
								FordiacLogHelper.logWarning(e.getMessage(), e);
							}
						}));
			}
		} catch (final CoreException e) {
			ErrorDialog.openError(getShell(), Messages.MainLaunchConfigurationTab_ConfigurationError,
					Messages.MainLaunchConfigurationTab_ErrorUpdatingArguments, e.getStatus());
			arguments = Collections.emptyList();
		}
		argumentsWidget.setInput(arguments);
		updateLaunchConfigurationDialog();
	}

	protected abstract List<Variable<?>> getDefaultArguments() throws CoreException;

	protected boolean filterTargetResource(final IResource resource) throws CoreException {
		if (resource instanceof IFile) {
			return false;
		}
		if (resource instanceof final IContainer container) {
			for (final IResource child : container.members()) {
				if (filterTargetResource(child)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	@Override
	public String getName() {
		return "Main"; //$NON-NLS-1$
	}

	@Override
	public String getId() {
		return ID;
	}

	public IResource getResource() {
		final String resourceString = resourceText.getText();
		if (resourceString != null && !resourceString.isEmpty()) {
			return ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(resourceString));
		}
		return null;
	}
}

/*******************************************************************************
 * Copyright (c) 2025, 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging.ui;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.fordiac.ide.debug.replaydebugging.LaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * Tab for configuring the replay debugging launch configuration.
 */
public class LaunchConfigurationTab extends AbstractLaunchConfigurationTab {

	private static final String TRACE_PATH_SECTION_TEXT = "Trace Path"; //$NON-NLS-1$
	private static final String SELECT_PATH_DIALOG_TEXT = "Select trace directory"; //$NON-NLS-1$
	private static final String BROWSE_BUTTON_TEXT = "Browse Trace Path"; //$NON-NLS-1$

	private static final String SIMULATOR_SECTION_TEXT = "Simulator Options"; //$NON-NLS-1$
	private static final String REMOTE_TEXT = "Remote"; //$NON-NLS-1$

	private static final String COMPONENTS_SELECTION_SECTION_TEXT = "Select Components"; //$NON-NLS-1$
	private static final String SYSTEM_SELECTION_BUTTON_TEXT = "Browse System"; //$NON-NLS-1$

	private static final String LAUNCH_CONFIGURATION_TAB_NAME = "Replay Debugging"; //$NON-NLS-1$

	private Text systemText;
	private static final String SYSTEM_TEXT_DEFAULT = ""; //$NON-NLS-1$

	private Button remoteCheckbox;

	private CheckboxTreeViewer selectionTree;

	private Composite component;

	private Text tracerPathText;

	@Override
	public void createControl(final Composite parent) {
		component = new Composite(parent, SWT.FILL);
		component.setLayout(new GridLayout(1, false));

		createSimulatorSection();
		createPathSelectionComponent();
		createSelectionComponent();
	}

	private void createSimulatorSection() {
		final Group group = new Group(component, SWT.BORDER);
		group.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(group);
		group.setText(SIMULATOR_SECTION_TEXT);

		// Checkbox to indicate if the replay should use the remote simulator (forte)
		remoteCheckbox = new Button(group, SWT.CHECK);
		remoteCheckbox.setText(REMOTE_TEXT);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(remoteCheckbox);
		remoteCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
	}

	private void createPathSelectionComponent() {
		final Group group = new Group(component, SWT.BORDER);
		group.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(group);
		group.setText(TRACE_PATH_SECTION_TEXT);

		final Composite pathSelectionComposite = new Composite(group, SWT.FILL);
		pathSelectionComposite.setLayout(new GridLayout(3, false));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(pathSelectionComposite);

		final Button browseButton = new Button(pathSelectionComposite, SWT.PUSH);
		browseButton.setText(BROWSE_BUTTON_TEXT);
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final DirectoryDialog dialog = new DirectoryDialog(pathSelectionComposite.getShell());
				dialog.setText(SELECT_PATH_DIALOG_TEXT);
				final String selected = dialog.open();
				if (selected != null) {
					tracerPathText.setText(selected);
				}
			}
		});

		tracerPathText = new Text(pathSelectionComposite, SWT.BORDER);
		tracerPathText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		tracerPathText.addModifyListener(e -> updateLaunchConfigurationDialog());
	}

	protected void createSelectionComponent() {
		final Group group = new Group(component, SWT.BORDER);
		group.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().grab(true, true).applyTo(group);
		group.setText(COMPONENTS_SELECTION_SECTION_TEXT);

		final Composite sytemSelectionComposite = new Composite(group, SWT.NONE);
		sytemSelectionComposite.setLayout(new GridLayout(2, false));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(sytemSelectionComposite);

		final Button systemButton = new Button(sytemSelectionComposite, SWT.BORDER);
		systemButton.setText(SYSTEM_SELECTION_BUTTON_TEXT);
		systemButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> handleSystemButtonSelected()));

		systemText = new Text(sytemSelectionComposite, SWT.BORDER);
		systemText.setEnabled(false);
		systemText.addModifyListener(e -> scheduleUpdateJob());
		systemText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		selectionTree = new CheckboxTreeViewer(group,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		selectionTree.setContentProvider(new SelectionContentProvider());
		selectionTree.setLabelProvider(new DelegatingStyledCellLabelProvider(new SelectionLabelProvider()));
		selectionTree.addCheckStateListener(new SelectionCheckStateListener());
		selectionTree.setAutoExpandLevel(2);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(selectionTree.getTree());
	}

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(LaunchConfigurationDelegate.ATTR_TRACE_PATH,
				LaunchConfigurationDelegate.ATTR_TRACE_PATH_DEFAULT);
		configuration.removeAttribute(DeploymentLaunchConfigurationAttributes.SYSTEM);
		configuration.removeAttribute(DeploymentLaunchConfigurationAttributes.SELECTION);
		configuration.setAttribute(LaunchConfigurationDelegate.ATTR_REMOTE,
				LaunchConfigurationDelegate.ATTR_REMOTE_DEFAULT);
	}

	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		try {
			tracerPathText.setText(configuration.getAttribute(LaunchConfigurationDelegate.ATTR_TRACE_PATH,
					LaunchConfigurationDelegate.ATTR_TRACE_PATH_DEFAULT));
			systemText.setText(
					configuration.getAttribute(DeploymentLaunchConfigurationAttributes.SYSTEM, SYSTEM_TEXT_DEFAULT));
			remoteCheckbox.setSelection(configuration.getAttribute(LaunchConfigurationDelegate.ATTR_REMOTE,
					LaunchConfigurationDelegate.ATTR_REMOTE_DEFAULT));
			final AutomationSystem system = DeploymentLaunchConfigurationAttributes.getSystem(configuration);
			selectionTree.setInput(system);
			selectionTree.setCheckedElements(
					DeploymentLaunchConfigurationAttributes.getSelection(configuration, system).toArray());
		} catch (final CoreException e) {
			systemText.setText(SYSTEM_TEXT_DEFAULT);
		}
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(LaunchConfigurationDelegate.ATTR_TRACE_PATH, tracerPathText.getText());
		configuration.setAttribute(DeploymentLaunchConfigurationAttributes.SYSTEM, systemText.getText());
		configuration.setAttribute(DeploymentLaunchConfigurationAttributes.SELECTION,
				Stream.of(selectionTree.getCheckedElements()).filter(INamedElement.class::isInstance)
						.map(INamedElement.class::cast).map(INamedElement::getQualifiedName)
						.collect(Collectors.toSet()));
		configuration.setAttribute(LaunchConfigurationDelegate.ATTR_REMOTE, remoteCheckbox.getSelection());
	}

	@Override
	public String getName() {
		return LAUNCH_CONFIGURATION_TAB_NAME;
	}

	@Override
	public org.eclipse.swt.widgets.Control getControl() {
		return component;
	}

	@Override
	public void dispose() {
		// nothing to do here
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public boolean isValid(final ILaunchConfiguration launchConfig) {
		return tracerPathText.getText() != null;
	}

	@Override
	public boolean canSave() {
		return isValid(null);
	}

	@Override
	public void launched(final ILaunch launch) {
		// nothing to do here
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public void activated(final ILaunchConfigurationWorkingCopy workingCopy) {
		// nothing to do here
	}

	@Override
	public void deactivated(final ILaunchConfigurationWorkingCopy workingCopy) {
		// nothing to do here
	}

	private void handleSystemButtonSelected() {
		final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(),
				new WorkbenchLabelProvider(), new WorkbenchContentProvider());
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		final IResource initialResource = getSystemResource();
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
			systemText.setText(resourceString);
			handleSystemUpdated();
		}
	}

	public IResource getSystemResource() {
		final String systemString = systemText.getText();
		if (systemString != null && !systemString.isEmpty()) {
			return ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(systemString));
		}
		return null;
	}

	protected void handleSystemUpdated() {
		updateLaunchConfigurationDialog();
		final AutomationSystem system = getSystem();
		selectionTree.setInput(system);
		if (system == null) {
			return;
		}
		system.getSystemConfiguration().getDevices().forEach(device -> selectionTree.setSubtreeChecked(device, true));
	}

	protected boolean filterTargetResource(final IResource resource) throws CoreException {
		if (resource instanceof final IFile file) {
			return TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING.equalsIgnoreCase(file.getFileExtension());
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

	public AutomationSystem getSystem() {
		if (getSystemResource() instanceof final IFile systemFile && TypeLibraryManager.INSTANCE
				.getTypeEntryForFile(systemFile) instanceof final SystemEntry systemEntry) {
			return systemEntry.getType();
		}
		return null;
	}

	public static class SelectionContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof final AutomationSystem system) {
				return system.getSystemConfiguration().getDevices().toArray();
			}
			return new Object[0];
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			if (parentElement instanceof final Device device) {
				return device.getResource().toArray();
			}
			return new Object[0];
		}

		@Override
		public Object getParent(final Object element) {
			if (element instanceof final Resource resource) {
				return resource.eContainer();
			}
			return null;
		}

		@Override
		public boolean hasChildren(final Object element) {
			return element instanceof final Device device && !device.getResource().isEmpty();
		}
	}

	private static class SelectionLabelProvider extends LabelProvider implements IStyledLabelProvider {

		private static final Styler ERROR_STYLER = new Styler() {

			@Override
			public void applyStyles(final TextStyle textStyle) {
				textStyle.strikeout = true;
			}
		};

		@Override
		public StyledString getStyledText(final Object element) {
			if (element instanceof final INamedElement namedElement) {
				return new StyledString(namedElement.getName(), namedElement.eResource() == null ? ERROR_STYLER : null);
			}
			return null;
		}

		@Override
		public Image getImage(final Object element) {
			if (element instanceof Device) {
				return FordiacImage.ICON_DEVICE.getImage();
			}
			if (element instanceof final Resource resource) {
				if (resource.isDeviceTypeResource()) {
					return FordiacImage.ICON_FIRMWARE_RESOURCE.getImage();
				}
				return FordiacImage.ICON_RESOURCE.getImage();
			}
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	private class SelectionCheckStateListener implements ICheckStateListener {

		@Override
		public void checkStateChanged(final CheckStateChangedEvent event) {
			final Object element = event.getElement();
			selectionTree.setSubtreeChecked(element, event.getChecked());
			if (element instanceof final Resource resource && !event.getChecked()) {
				selectionTree.setChecked(resource.eContainer(), false);
			}
			updateLaunchConfigurationDialog();
		}
	}

}
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

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
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
import org.eclipse.emf.common.util.EList;
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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
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

	private static final String SELECT_PATH_DIALOG_TEXT = Messages.LaunchConfigurationTab_SelectPathDialogText;
	private static final String BROWSE_BUTTON_TEXT = Messages.LaunchConfigurationTab_BrowseButtonText;

	private static final String REPLAYER_SECTION_TEXT = Messages.LaunchConfigurationTab_ReplayerSectionText;
	private static final String REMOTE_TEXT = Messages.LaunchConfigurationTab_RemoteText;
	private static final String NO_DEVICES_SELECTED_TEXT = Messages.LaunchConfigurationTab_NoDevicesSelected;

	private static final String COMPONENTS_SELECTION_SECTION_TEXT = Messages.LaunchConfigurationTab_ComponentsSelectionText;
	private static final String SYSTEM_SELECTION_BUTTON_TEXT = Messages.LaunchConfigurationTab_SystemSelectionButtonText;

	private static final String LAUNCH_CONFIGURATION_TAB_NAME = Messages.LaunchConfigurationTab_LaunchConfigurationTabName;

	private Text systemText;
	private static final String SYSTEM_TEXT_DEFAULT = ""; //$NON-NLS-1$

	private CheckboxTreeViewer selectionTree;

	private Composite component;

	private Composite deviceRowsContainer;
	private Label noDevicesLabel;
	private GridData noDevicesLabelData;

	/**
	 * Live per-device rows, keyed by the current tree's Device instances. Rebuilt
	 * from scratch whenever the tree's model is replaced.
	 */
	private final Map<Device, DeviceReplayRow> deviceRows = new LinkedHashMap<>();

	@Override
	public void createControl(final Composite parent) {
		component = new Composite(parent, SWT.FILL);
		component.setLayout(new GridLayout(1, false));

		createSelectionComponent();
		createReplayerSection();
	}

	private void createReplayerSection() {
		final Group group = new Group(component, SWT.BORDER);
		group.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(group);
		group.setText(REPLAYER_SECTION_TEXT);

		deviceRowsContainer = new Composite(group, SWT.NONE);
		deviceRowsContainer.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(deviceRowsContainer);

		noDevicesLabel = new Label(deviceRowsContainer, SWT.NONE);
		noDevicesLabel.setText(NO_DEVICES_SELECTED_TEXT);
		noDevicesLabel.setEnabled(false);
		noDevicesLabelData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		noDevicesLabel.setLayoutData(noDevicesLabelData);
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
		configuration.removeAttribute(DeploymentLaunchConfigurationAttributes.SYSTEM);
		configuration.removeAttribute(DeploymentLaunchConfigurationAttributes.SELECTION);
		configuration.removeAttribute(LaunchConfigurationDelegate.ATTR_DEVICE_REMOTE_MAP);
		configuration.removeAttribute(LaunchConfigurationDelegate.ATTR_DEVICE_TRACE_PATH_MAP);
	}

	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		try {
			systemText.setText(
					configuration.getAttribute(DeploymentLaunchConfigurationAttributes.SYSTEM, SYSTEM_TEXT_DEFAULT));

			// Hard reset: whatever rows/tree-state existed before this call are tied to a
			// (possibly) different AutomationSystem instance and must not leak forward.
			resetDeviceRows();
			selectionTree.setInput(null);
			selectionTree.setCheckedElements(new Object[0]);

			final AutomationSystem system = DeploymentLaunchConfigurationAttributes.getSystem(configuration);
			selectionTree.setInput(system);
			selectionTree.setCheckedElements(
					DeploymentLaunchConfigurationAttributes.getSelection(configuration, system).toArray());

			if (system != null) {
				system.getSystemConfiguration().getDevices().forEach(this::updateDeviceCheckState);
			}

			refreshDeviceRows();

			final Map<String, String> remoteMap = configuration.getAttribute(
					LaunchConfigurationDelegate.ATTR_DEVICE_REMOTE_MAP,
					LaunchConfigurationDelegate.ATTR_DEVICE_MAP_DEFAULT);
			final Map<String, String> pathMap = configuration.getAttribute(
					LaunchConfigurationDelegate.ATTR_DEVICE_TRACE_PATH_MAP,
					LaunchConfigurationDelegate.ATTR_DEVICE_MAP_DEFAULT);

			deviceRows.forEach((device, row) -> {
				row.remoteCheckbox.setSelection(Boolean.parseBoolean(remoteMap.get(device.getQualifiedName())));
				row.tracePathText.setText(pathMap.getOrDefault(device.getQualifiedName(), "")); //$NON-NLS-1$
			});
		} catch (final CoreException e) {
			systemText.setText(SYSTEM_TEXT_DEFAULT);
			resetDeviceRows();
			refreshDeviceRows();
		}
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(DeploymentLaunchConfigurationAttributes.SYSTEM, systemText.getText());
		configuration.setAttribute(DeploymentLaunchConfigurationAttributes.SELECTION,
				Stream.of(selectionTree.getCheckedElements()).filter(INamedElement.class::isInstance)
						.map(INamedElement.class::cast).map(INamedElement::getQualifiedName)
						.collect(Collectors.toSet()));

		final Map<String, String> remoteMap = new HashMap<>();
		final Map<String, String> pathMap = new HashMap<>();
		deviceRows.forEach((device, row) -> {
			remoteMap.put(device.getQualifiedName(), Boolean.toString(row.remoteCheckbox.getSelection()));
			pathMap.put(device.getQualifiedName(), row.tracePathText.getText());
		});
		configuration.setAttribute(LaunchConfigurationDelegate.ATTR_DEVICE_REMOTE_MAP, remoteMap);
		configuration.setAttribute(LaunchConfigurationDelegate.ATTR_DEVICE_TRACE_PATH_MAP, pathMap);
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
		return !getActiveDevices().isEmpty();
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

	/**
	 * Called when loading a new AutomationSystem. Treats this as a full model
	 * replacement: every Device/Resource previously in the tree is discarded, all
	 * per-device rows are disposed, and the tree/rows are rebuilt from scratch
	 * against the freshly loaded AutomationSystem.
	 */
	protected void handleSystemUpdated() {
		resetDeviceRows();
		selectionTree.setInput(null);
		selectionTree.setCheckedElements(new Object[0]);

		final AutomationSystem system = getSystem();
		selectionTree.setInput(system);

		updateLaunchConfigurationDialog();
		refreshDeviceRows();
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

	/**
	 * Recomputes a device's checked/grayed state from its resources' checked
	 * states. 1. None checked -> plain unchecked. 2. All checked -> plain checked.
	 * 3. Some checked -> grayed+checked, which SWT renders as the classic tri-state
	 * "partial" box.
	 */
	private void updateDeviceCheckState(final Device device) {
		final EList<Resource> resources = device.getResource();
		if (resources.isEmpty()) {
			selectionTree.setGrayChecked(device, false);
			return;
		}
		final long checkedCount = resources.stream().filter(selectionTree::getChecked).count();
		if (checkedCount == 0) {
			selectionTree.setGrayChecked(device, false);
		} else if (checkedCount == resources.size()) {
			selectionTree.setChecked(device, true);
			selectionTree.setGrayed(device, false);
		} else {
			selectionTree.setGrayChecked(device, true);
		}
	}

	/**
	 * Devices considered "active" for the purposes of showing a Remote/Trace Path
	 * row: fully or partially checked.
	 */
	private Set<Device> getActiveDevices() {
		if (selectionTree.getInput() instanceof final AutomationSystem system) {
			return system.getSystemConfiguration().getDevices().stream().filter(selectionTree::getChecked)
					.collect(Collectors.toCollection(LinkedHashSet::new));
		}
		return Collections.emptySet();
	}

	/**
	 * Syncs deviceRows with the current active-device set: disposes rows for
	 * devices no longer active, creates rows for newly active devices, and
	 * preserves the widgets (and whatever the user already typed) for devices that
	 * remain active.
	 */
	private void refreshDeviceRows() {
		final Set<Device> active = getActiveDevices();

		// hide (not dispose) rows for devices no longer active, so their data survives
		// if the device gets re-checked later
		deviceRows.forEach((device, row) -> row.setActive(active.contains(device)));

		for (final Device device : active) {
			deviceRows.computeIfAbsent(device, this::createDeviceRow);
		}

		final boolean noneActive = active.isEmpty();
		noDevicesLabel.setVisible(noneActive);
		noDevicesLabelData.exclude = !noneActive;

		deviceRowsContainer.layout(true, true);
		component.layout(true, true);
		updateLaunchConfigurationDialog();
	}

	private DeviceReplayRow createDeviceRow(final Device device) {
		final DeviceReplayRow row = new DeviceReplayRow(deviceRowsContainer, device);
		row.remoteCheckbox
				.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> updateLaunchConfigurationDialog()));
		row.tracePathText.addModifyListener(e -> updateLaunchConfigurationDialog());
		return row;
	}

	/**
	 * Unconditionally disposes every per-device row and clears the tracking map.
	 * Must be called before the tree's input (AutomationSystem) is replaced, so no
	 * widget or text state from the old model can leak into the new one.
	 */
	private void resetDeviceRows() {
		deviceRows.values().forEach(DeviceReplayRow::dispose);
		deviceRows.clear();
	}

	/**
	 * One row of "Remote" checkbox + "Browse Path" text field for a single device.
	 */
	private static final class DeviceReplayRow {

		public final Button remoteCheckbox;
		public final Text tracePathText;

		private final Composite composite;
		private final GridData gridData;

		DeviceReplayRow(final Composite parent, final Device device) {
			composite = new Composite(parent, SWT.NONE);
			composite.setLayout(new GridLayout(4, false));
			gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
			composite.setLayoutData(gridData);

			final Label nameLabel = new Label(composite, SWT.NONE);
			nameLabel.setText(device.getName());
			GridDataFactory.swtDefaults().hint(120, SWT.DEFAULT).applyTo(nameLabel);

			remoteCheckbox = new Button(composite, SWT.CHECK);
			remoteCheckbox.setText(REMOTE_TEXT);

			final Button browseButton = new Button(composite, SWT.PUSH);
			browseButton.setText(BROWSE_BUTTON_TEXT);

			tracePathText = new Text(composite, SWT.BORDER);
			GridDataFactory.fillDefaults().grab(true, false).applyTo(tracePathText);

			browseButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
				final DirectoryDialog dialog = new DirectoryDialog(composite.getShell());
				dialog.setText(SELECT_PATH_DIALOG_TEXT);
				final String selected = dialog.open();
				if (selected != null) {
					tracePathText.setText(selected);
				}
			}));
		}

		/**
		 * Shows/hides the row without disposing it, preserving whatever the user typed.
		 */
		void setActive(final boolean active) {
			composite.setVisible(active);
			gridData.exclude = !active;
		}

		void dispose() {
			composite.dispose();
		}
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
			if (element instanceof final Device device) {
				// user toggled the device row itself: propagate fully, no partial state
				selectionTree.setSubtreeChecked(device, event.getChecked());
				selectionTree.setGrayed(device, false);
			} else if (element instanceof final Resource resource
					&& resource.eContainer() instanceof final Device device) {
				updateDeviceCheckState(device);
			}
			refreshDeviceRows();
		}
	}

}
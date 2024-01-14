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
package org.eclipse.fordiac.ide.deployment.debug.ui;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.typelibrary.SystemEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class DeploymentLaunchConfigurationTab extends AbstractLaunchConfigurationTab {
	public static final String ID = "org.eclipse.fordiac.ide.deployment.debug.ui.deployTab"; //$NON-NLS-1$

	private Text systemText;
	private CheckboxTreeViewer selectionTree;

	@Override
	public void createControl(final Composite parent) {
		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(comp);
		setControl(comp);

		final Composite selectionComponent = createSelectionComponent(comp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(selectionComponent);
	}

	protected Composite createSelectionComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().applyTo(group);
		group.setText("Selection"); //$NON-NLS-1$

		final Composite comp = new Composite(group, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(3).applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(comp);

		final Label systemLabel = new Label(comp, SWT.NONE);
		systemLabel.setText("System:"); //$NON-NLS-1$
		GridDataFactory.swtDefaults().applyTo(systemLabel);

		systemText = new Text(comp, SWT.BORDER);
		systemText.setEnabled(false);
		systemText.setMessage("System File"); //$NON-NLS-1$
		systemText.addModifyListener(e -> scheduleUpdateJob());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(systemText);

		final Button systemButton = new Button(comp, SWT.BORDER);
		systemButton.setText("Browse..."); //$NON-NLS-1$
		systemButton.addSelectionListener(widgetSelectedAdapter(e -> handleSystemButtonSelected()));
		GridDataFactory.swtDefaults().applyTo(systemButton);

		selectionTree = new CheckboxTreeViewer(comp,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		selectionTree.setContentProvider(new SelectionContentProvider());
		selectionTree.setLabelProvider(new DelegatingStyledCellLabelProvider(new SelectionLabelProvider()));
		selectionTree.addCheckStateListener(new SelectionCheckStateListener());
		selectionTree.setAutoExpandLevel(2);
		GridDataFactory.fillDefaults().span(3, 1).grab(true, true).applyTo(selectionTree.getTree());

		return group;
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

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.removeAttribute(DeploymentLaunchConfigurationAttributes.SYSTEM);
		configuration.removeAttribute(DeploymentLaunchConfigurationAttributes.SELECTION);
	}

	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		try {
			systemText.setText(configuration.getAttribute(DeploymentLaunchConfigurationAttributes.SYSTEM, "")); //$NON-NLS-1$
			final AutomationSystem system = DeploymentLaunchConfigurationAttributes.getSystem(configuration);
			selectionTree.setInput(system);
			selectionTree.setCheckedElements(
					DeploymentLaunchConfigurationAttributes.getSelection(configuration, system).toArray());
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(DeploymentLaunchConfigurationAttributes.SYSTEM, systemText.getText());
		configuration.setAttribute(DeploymentLaunchConfigurationAttributes.SELECTION,
				Stream.of(selectionTree.getCheckedElements()).filter(INamedElement.class::isInstance)
						.map(INamedElement.class::cast).map(INamedElement::getQualifiedName)
						.collect(Collectors.toSet()));
	}

	protected void handleSystemUpdated() {
		updateLaunchConfigurationDialog();
		final AutomationSystem system = getSystem();
		selectionTree.setInput(system);
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

	@Override
	public String getName() {
		return "Deploy"; //$NON-NLS-1$
	}

	@Override
	public String getId() {
		return ID;
	}

	public AutomationSystem getSystem() {
		if (getSystemResource() instanceof final IFile systemFile && TypeLibraryManager.INSTANCE
				.getTypeEntryForFile(systemFile) instanceof final SystemEntry systemEntry) {
			return systemEntry.getSystem();
		}
		return null;
	}

	public IResource getSystemResource() {
		final String systemString = systemText.getText();
		if (systemString != null && !systemString.isEmpty()) {
			return ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(systemString));
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

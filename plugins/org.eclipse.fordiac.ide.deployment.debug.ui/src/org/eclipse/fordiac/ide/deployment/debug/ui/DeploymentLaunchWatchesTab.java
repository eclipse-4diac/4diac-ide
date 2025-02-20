/*******************************************************************************
 * Copyright (c) 2022, 2025 Martin Erich Jobst
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugElement;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationAttributes.DeploymentLaunchWatchpoint;
import org.eclipse.fordiac.ide.deployment.debug.breakpoint.DeploymentWatchpoint;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class DeploymentLaunchWatchesTab extends AbstractLaunchConfigurationTab {
	public static final String ID = "org.eclipse.fordiac.ide.deployment.debug.ui.watchesTab"; //$NON-NLS-1$

	private AutomationSystem system;
	private List<DeploymentLaunchWatchpoint> watches;
	private TableViewer watchesTable;
	private Button addButton;
	private Button removeButton;

	@Override
	public void createControl(final Composite parent) {
		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(comp);
		setControl(comp);

		final Control watchesControl = createWatchesTable(comp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(watchesControl);

		final Control buttonsControl = createTableButtons(comp);
		GridDataFactory.fillDefaults().grab(false, true).applyTo(buttonsControl);
	}

	protected Control createWatchesTable(final Composite parent) {
		watchesTable = new TableViewer(parent,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);

		watchesTable.setContentProvider(ArrayContentProvider.getInstance());
		watchesTable.setLabelProvider(new DelegatingStyledCellLabelProvider(new WatchesLabelProvider()));
		watchesTable.addSelectionChangedListener(this::handleTableSelectionChanged);
		watchesTable.setComparator(new ViewerComparator());

		return watchesTable.getTable();
	}

	protected void handleTableSelectionChanged(final SelectionChangedEvent event) {
		final IStructuredSelection selection = event.getStructuredSelection();
		removeButton.setEnabled(!selection.isEmpty());
	}

	private Control createTableButtons(final Composite parent) {
		final Composite comp = new Composite(parent, SWT.NONE);

		addButton = new Button(comp, SWT.PUSH);
		addButton.setText(Messages.DeploymentLaunchWatchesTab_AddButton);
		addButton.setEnabled(false);
		addButton.addSelectionListener(widgetSelectedAdapter(e -> handleAddButtonSelected()));

		removeButton = new Button(comp, SWT.PUSH);
		removeButton.setText(Messages.DeploymentLaunchWatchesTab_RemoveButton);
		removeButton.setEnabled(false);
		removeButton.addSelectionListener(widgetSelectedAdapter(e -> handleRemoveButtonSelected()));

		GridLayoutFactory.swtDefaults().generateLayout(comp);

		return comp;
	}

	private void handleAddButtonSelected() {
		final List<DeploymentWatchpoint> watchpoints = getWatchpoints();
		if (watchpoints.isEmpty()) {
			MessageDialog.openError(getShell(), Messages.DeploymentLaunchWatchesTab_AddDialogTitle,
					Messages.DeploymentLaunchWatchesTab_AddDialogEmptyListMessage);
			return;
		}
		final ElementListSelectionDialog dialog = new ElementListSelectionDialog(getShell(),
				DebugUITools.newDebugModelPresentation());
		dialog.setTitle(Messages.DeploymentLaunchWatchesTab_AddDialogTitle);
		dialog.setMessage(Messages.DeploymentLaunchWatchesTab_AddDialogMessage);
		dialog.setEmptyListMessage(Messages.DeploymentLaunchWatchesTab_AddDialogEmptyListMessage);
		dialog.setEmptySelectionMessage(Messages.DeploymentLaunchWatchesTab_AddDialogEmptySelectionMessage);
		dialog.setStatusLineAboveButtons(true);
		dialog.setMultipleSelection(true);
		dialog.setElements(watchpoints.toArray());
		if (dialog.open() == Window.OK) {
			Stream.of(dialog.getResult()).map(DeploymentWatchpoint.class::cast).map(DeploymentLaunchWatchpoint::new)
					.forEachOrdered(watches::add);
			watchesTable.refresh(false);
			updateLaunchConfigurationDialog();
		}
	}

	protected List<DeploymentWatchpoint> getWatchpoints() {
		return Stream
				.of(DebugPlugin.getDefault().getBreakpointManager()
						.getBreakpoints(DeploymentDebugElement.MODEL_IDENTIFIER))
				.filter(DeploymentWatchpoint.class::isInstance).map(DeploymentWatchpoint.class::cast)
				.filter(this::isRelevant).sorted(Comparator.comparing(DeploymentWatchpoint::getLocation)).toList();
	}

	protected boolean isRelevant(final DeploymentWatchpoint watchpoint) {
		return watchpoint.isRelevant(system) && watches.stream().map(DeploymentLaunchWatchpoint::targetName)
				.noneMatch(watchpoint.getLocation()::equalsIgnoreCase);
	}

	private void handleRemoveButtonSelected() {
		watches.removeAll(watchesTable.getStructuredSelection().toList());
		watchesTable.refresh(false);
		updateLaunchConfigurationDialog();
	}

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		DeploymentLaunchConfigurationAttributes.setWatches(configuration, null);
	}

	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		try {
			system = DeploymentLaunchConfigurationAttributes.getSystem(configuration);
			watches = new ArrayList<>(DeploymentLaunchConfigurationAttributes.getWatches(configuration));
			watchesTable.setInput(watches);
			addButton.setEnabled(system != null);
		} catch (final CoreException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		DeploymentLaunchConfigurationAttributes.setWatches(configuration, watches);
	}

	@Override
	public String getName() {
		return Messages.DeploymentLaunchWatchesTab_Name;
	}

	@Override
	public Image getImage() {
		return DebugUITools.getImage(IDebugUIConstants.IMG_OBJS_WATCHPOINT);
	}

	@Override
	public String getId() {
		return ID;
	}

	private class WatchesLabelProvider extends LabelProvider implements IStyledLabelProvider {

		private static final Styler ERROR_STYLER = new Styler() {

			@Override
			public void applyStyles(final TextStyle textStyle) {
				textStyle.strikeout = true;
			}
		};

		@Override
		public StyledString getStyledText(final Object element) {
			if (element instanceof final DeploymentLaunchWatchpoint watchpoint) {
				return new StyledString(watchpoint.targetName(),
						system == null || watchpoint.getTarget(system).isEmpty() ? ERROR_STYLER : null);
			}
			return null;
		}

		@Override
		public Image getImage(final Object element) {
			if (element instanceof final DeploymentLaunchWatchpoint watchpoint) {
				if (!watchpoint.forceValue().isEmpty()) {
					return DebugUITools.getImage(IDebugUIConstants.IMG_OBJS_MODIFICATION_WATCHPOINT);
				}
				return DebugUITools.getImage(IDebugUIConstants.IMG_OBJS_ACCESS_WATCHPOINT);
			}
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
}

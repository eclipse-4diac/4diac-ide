/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.runtime.views;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.ILaunchesListener2;
import org.eclipse.fordiac.ide.runtime.Activator;
import org.eclipse.fordiac.ide.runtime.LaunchRuntimeUtils;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class RuntimesView implements ILaunchesListener2 {

	private static final String DEVICE_NAME = "device_name"; //$NON-NLS-1$
	private static final String PARAMETERS = "parameters"; //$NON-NLS-1$

	private static final int DEVICE_NAME_WIDTH = 100;
	private static final int PARAMETERS_WIDTH = 200;

	private final ILaunchManager lm;
	private final List<RuntimeData> runtimes = new ArrayList<>();

	private FormToolkit toolkit;
	private TableViewer runtimesViewer;
	private Button terminateButton;

	@Inject
	public RuntimesView() {
		lm = DebugPlugin.getDefault().getLaunchManager();
	}

	@PostConstruct
	public void postConstruct(final Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());

		final Form form = toolkit.createForm(parent);
		form.getBody().setLayout(new GridLayout(1, true));
		form.setText("Running Simulated Devices");

		final Composite composite = toolkit.createComposite(form.getBody());
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		setupButtonArea(composite);

		runtimesViewer = createConnectionsViewer(composite);
		runtimesViewer.addSelectionChangedListener(ev -> setButtonEnablement(!runtimesViewer.getSelection().isEmpty()));

		lm.addLaunchListener(this);
		runtimesViewer.setInput(runtimes);
	}

	@PreDestroy
	public void preDestroy() {
		lm.removeLaunchListener(this);
		runtimes.forEach(RuntimesView::terminateRuntime);
	}

	private void setButtonEnablement(final boolean enable) {
		terminateButton.setEnabled(enable);
		terminateButton
		.setImage((enable) ? PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_STOP)
				: PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_STOP_DISABLED));
	}

	private void setupButtonArea(final Composite parent) {
		final Composite container = toolkit.createComposite(parent, SWT.NONE);
		final GridData buttonCompLayoutData = new GridData(SWT.CENTER, SWT.TOP, false, false);
		container.setLayoutData(buttonCompLayoutData);
		container.setLayout(new GridLayout(1, true));

		terminateButton = toolkit.createButton(container, "", SWT.PUSH); //$NON-NLS-1$
		terminateButton.setToolTipText("Terminate the selected devices");
		terminateButton
		.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_STOP_DISABLED));
		terminateButton.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		terminateButton.addListener(SWT.Selection, ev -> terminateRuntimes());
	}

	private void terminateRuntimes() {
		if (!runtimesViewer.getSelection().isEmpty() && runtimesViewer.getSelection() instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) runtimesViewer.getSelection();
			for (final Object el : selection.toArray()) {
				if (el instanceof RuntimeData) {
					terminateRuntime((RuntimeData) el);
				}
			}
		}
	}

	private static void terminateRuntime(final RuntimeData runtimeData) {
		try {
			runtimeData.launch.terminate();
		} catch (final DebugException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	private static TableViewer createConnectionsViewer(final Composite parent) {
		final TableViewer viewer = TableWidgetFactory.createTableViewer(parent);
		viewer.getTable().setLayout(createTableLayout(viewer.getTable()));
		viewer.setColumnProperties(new String[] { DEVICE_NAME, PARAMETERS });
		viewer.setLabelProvider(new RuntimesTableLabelProvider());
		viewer.setContentProvider(new RuntimesContentProvider());
		return viewer;
	}

	private static TableLayout createTableLayout(final Table table) {
		final TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText("Device Name");
		final TableColumn column2 = new TableColumn(table, SWT.LEFT);
		column2.setText("Parameters");
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(DEVICE_NAME_WIDTH));
		layout.addColumnData(new ColumnPixelData(PARAMETERS_WIDTH));
		return layout;
	}


	@Override
	public void launchesRemoved(final ILaunch[] launches) {
		Arrays.stream(launches).forEach(this::removeRuntime);
		Display.getDefault().asyncExec(() -> runtimesViewer.refresh());
	}

	@Override
	public void launchesAdded(final ILaunch[] launches) {
		Arrays.stream(launches).forEach(this::addRuntime);
		Display.getDefault().asyncExec(() -> runtimesViewer.refresh());
	}

	@Override
	public void launchesChanged(final ILaunch[] launches) {
		// I think we don't need to do anything here
	}

	@Override
	public void launchesTerminated(final ILaunch[] launches) {
		// for now we remove the launches from the list, maybe in the future we want to show them terminated
		lm.removeLaunches(launches);
	}

	private void addRuntime(final ILaunch l) {
		String parameter = ""; //$NON-NLS-1$
		try {
			parameter = l.getLaunchConfiguration().getAttribute(LaunchRuntimeUtils.ATTR_TOOL_ARGUMENTS, ""); //$NON-NLS-1$
		} catch (final CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		runtimes.add(new RuntimeData(l, parameter));
	}

	private void removeRuntime(final ILaunch l) {
		for (int i = 0; i < runtimes.size(); i++) {
			if (runtimes.get(i).launch.equals(l)) {
				runtimes.remove(i);
				break;
			}
		}
	}

	private static class RuntimeData {
		final ILaunch launch;
		final String parameters;

		public RuntimeData(final ILaunch launch, final String parameters) {
			super();
			this.launch = launch;
			this.parameters = parameters;
		}

	}

	private static class RuntimesContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof List<?>) {
				return ((List<?>) inputElement).toArray();
			}
			return new Object[0];
		}

	}

	private static class RuntimesTableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public static final int DEVICE_NAME_COL_INDEX = 0;
		public static final int PARAMETERS_COL_INDEX = 1;

		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			if (element instanceof RuntimeData) {
				final RuntimeData runtimeData = ((RuntimeData) element);
				switch (columnIndex) {
				case DEVICE_NAME_COL_INDEX:
					return runtimeData.launch.getLaunchConfiguration().getName();
				case PARAMETERS_COL_INDEX:
					return runtimeData.parameters;
				default:
					break;
				}
			}
			return super.getText(element);
		}
	}

}
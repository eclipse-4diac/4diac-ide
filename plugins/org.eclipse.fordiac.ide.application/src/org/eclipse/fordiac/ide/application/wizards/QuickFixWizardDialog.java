/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.application.wizards;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

public class QuickFixWizardDialog {

	private QuickFixWizardDialog() {
	}

	public static int openDialog(final Shell parentShell, final IMarker[] selectedMarkers,
			final IMarkerResolution[] resolutions, final Map<IMarkerResolution, Collection<IMarker>> resolutionMap) {
		final QuickFixWizardPage wizardPage = new QuickFixWizardPage(selectedMarkers, resolutions, resolutionMap);
		final QuickFixWizard wizard = new QuickFixWizard(wizardPage);
		wizard.setWindowTitle(Messages.QuickFixDialog_Title);

		final WizardDialog dialog = new WizardDialog(parentShell, wizard);
		return dialog.open();
	}

	private static class QuickFixWizard extends Wizard {
		private final QuickFixWizardPage wizardPage;

		public QuickFixWizard(final QuickFixWizardPage wizardPage) {
			setDefaultPageImageDescriptor(
					ImageDescriptor.createFromFile(IDE.class, "/icons/full/wizban/quick_fix.png")); //$NON-NLS-1$
			this.wizardPage = wizardPage;
			addPage(wizardPage);
		}

		@Override
		public boolean performFinish() {
			final IRunnableWithProgress runnable = monitor -> {
				if (wizardPage.getSelectedResolution() instanceof final WorkbenchMarkerResolution workbenchResolution) {
					workbenchResolution.run(wizardPage.getSelectedMarker(), monitor);
				} else {
					wizardPage.getSelectedResolution().run(wizardPage.getSelectedMarker()[0]);
				}
			};

			try {
				getContainer().run(false, true, runnable);
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}

			return true;
		}
	}

	private static class QuickFixWizardPage extends WizardPage {
		private static final Image ERROR_IMG = ImageDescriptor
				.createFromFile(IDE.class, "/icons/full/obj16/error_tsk.png").createImage(); //$NON-NLS-1$

		private final IMarker[] initialMarkers;
		private final IMarkerResolution[] resolutions;
		private final Map<IMarkerResolution, Collection<IMarker>> resolutionMap;

		private TableViewer resolutionsList;
		private CheckboxTableViewer markersTable;

		protected QuickFixWizardPage(final IMarker[] initialMarkers, final IMarkerResolution[] resolutions,
				final Map<IMarkerResolution, Collection<IMarker>> resolutionsMap) {
			super(Messages.QuickFixDialog_Title);
			this.initialMarkers = initialMarkers;
			this.resolutions = resolutions;
			this.resolutionMap = resolutionsMap;

			setTitle(Messages.QuickFixDialog_Title);
			setMessage(MessageFormat.format(Messages.QuickFixDialog_Message,
					initialMarkers[0].getAttribute(IMarker.MESSAGE, ""))); //$NON-NLS-1$
		}

		@Override
		public void createControl(final Composite parent) {
			initializeDialogUnits(parent);

			final Composite pageComposite = new Composite(parent, SWT.NONE);
			pageComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			pageComposite.setLayout(new GridLayout());
			setControl(pageComposite);

			final Composite selectResolutionComposite = new Composite(pageComposite, SWT.NONE);
			selectResolutionComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			selectResolutionComposite.setLayout(new GridLayout());
			Label label = addLabel(selectResolutionComposite);
			label.setText(Messages.QuickFixDialog_Resolutions_List_Title);
			createResolutionList(selectResolutionComposite);

			final Composite selectMarkerComposite = new Composite(pageComposite, SWT.NONE);
			selectMarkerComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			selectMarkerComposite.setLayout(new GridLayout(2, false));
			label = addLabel(selectMarkerComposite);
			label.setText(Messages.QuickFixDialog_Problems_List_Title);
			addLabel(selectMarkerComposite);
			createMarkerList(selectMarkerComposite);

			resolutionsList.setSelection(new StructuredSelection(resolutionsList.getElementAt(0)));
			markersTable.setCheckedElements(initialMarkers);

			setPageComplete(markersTable.getCheckedElements().length > 0);
		}

		private static Label addLabel(final Composite selectMarkerComposite) {
			return new Label(selectMarkerComposite, SWT.NONE);
		}

		private void createResolutionList(final Composite parent) {
			resolutionsList = new TableViewer(parent, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);

			resolutionsList.setContentProvider(ArrayContentProvider.getInstance());
			resolutionsList.setLabelProvider(new LabelProvider() {
				@Override
				public String getText(final Object element) {
					return ((IMarkerResolution) element).getLabel();
				}
			});
			resolutionsList.setComparator(new ViewerComparator() {
				@Override
				public int compare(final Viewer viewer, final Object object1, final Object object2) {
					return ((IMarkerResolution) object1).getLabel().compareTo(((IMarkerResolution) object2).getLabel());
				}
			});

			resolutionsList.addSelectionChangedListener(event -> {
				if (event.getStructuredSelection().getFirstElement() instanceof final IMarkerResolution resolution) {
					markersTable.setInput(resolutionMap.get(resolution));
				}
				setPageComplete(markersTable.getCheckedElements().length > 0);
				markersTable.refresh();
			});

			resolutionsList.setInput(resolutions);
			final Table table = resolutionsList.getTable();
			final GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.minimumHeight = convertHeightInCharsToPixels(10);
			table.setLayoutData(data);
		}

		private void createMarkerList(final Composite parent) {
			markersTable = CheckboxTableViewer.newCheckList(parent, SWT.BORDER | SWT.V_SCROLL | SWT.SINGLE);

			createTableColumns();
			createTableButtons(parent);

			markersTable.setContentProvider(ArrayContentProvider.getInstance());
			markersTable.setComparator(new ViewerComparator() {
				@Override
				public int compare(final Viewer viewer, final Object object1, final Object object2) {
					return ((IMarker) object1).getResource().toString()
							.compareTo(((IMarker) object2).getResource().toString());
				}
			});
			markersTable.addCheckStateListener(event -> {
				if (event.getChecked()) {
					setPageComplete(true);
				} else {
					setPageComplete(markersTable.getCheckedElements().length > 0);
				}
			});
			markersTable.setInput(resolutionMap.get(resolutionsList.getElementAt(0)));
		}

		private void createTableColumns() {
			final Table table = markersTable.getTable();
			final GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = convertHeightInCharsToPixels(10);
			table.setLayoutData(data);

			final TableLayout layout = new TableLayout();
			table.setLayout(layout);
			table.setLinesVisible(true);
			table.setHeaderVisible(true);

			layout.addColumnData(new ColumnWeightData(70, true));
			TableColumn tableColumn = new TableColumn(table, SWT.NONE, 0);
			tableColumn.setText(Messages.QuickFixDialog_Problems_List_Location);
			TableViewerColumn tableViewerColumn = new TableViewerColumn(markersTable, tableColumn);
			tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(final Object element) {
					return ((IMarker) element).getAttribute(IMarker.LOCATION, ""); //$NON-NLS-1$
				}
			});

			layout.addColumnData(new ColumnWeightData(30, true));
			tableColumn = new TableColumn(table, SWT.NONE, 0);
			tableColumn.setText(Messages.QuickFixDialog_Problems_List_Resource);
			tableViewerColumn = new TableViewerColumn(markersTable, tableColumn);
			tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public Image getImage(final Object element) {
					return ERROR_IMG;
				}

				@Override
				public String getText(final Object element) {
					return ((IMarker) element).getResource().getName();
				}
			});
		}

		private void createTableButtons(final Composite control) {
			final Composite buttonComposite = new Composite(control, SWT.NONE);

			buttonComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
			final GridLayout layout = new GridLayout();
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			buttonComposite.setLayout(layout);

			final Button selectAll = new Button(buttonComposite, SWT.PUSH);
			selectAll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			selectAll.setText(Messages.QuickFixDialog_SelectAll);
			selectAll.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent event) {
					markersTable.setAllChecked(true);
					setPageComplete(!resolutionsList.getStructuredSelection().isEmpty());
				}
			});

			final Button deselectAll = new Button(buttonComposite, SWT.PUSH);
			deselectAll.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			deselectAll.setText(Messages.QuickFixDialog_DeselectAll);
			deselectAll.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent event) {
					markersTable.setAllChecked(false);
					setPageComplete(false);
				}
			});
		}

		public IMarkerResolution2 getSelectedResolution() {
			if (resolutionsList.getStructuredSelection()
					.getFirstElement() instanceof final IMarkerResolution2 selectedResolution) {
				return selectedResolution;
			}
			return null;
		}

		public IMarker[] getSelectedMarker() {
			return Arrays.stream(markersTable.getCheckedElements()).map(IMarker.class::cast).toArray(IMarker[]::new);
		}
	}
}

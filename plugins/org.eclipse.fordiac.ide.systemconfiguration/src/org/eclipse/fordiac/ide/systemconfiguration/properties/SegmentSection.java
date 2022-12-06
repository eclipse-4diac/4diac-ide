/*******************************************************************************
 * Copyright (c) 2022 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Bianca Wiesmayr
 *      - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.gef.properties.AbstractDoubleColumnSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ChangeCommunicationConfigurationCommand;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.fordiac.systemconfiguration.api.CommunicationConfigurationDetails;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class SegmentSection extends AbstractDoubleColumnSection {

	private static final String COMM_CONFIG_ID = "org.eclipse.fordiac.ide.systemconfiguration.communication";
	private Text nameText;
	private Text commentText;
	private Button checkbox;
	private CCombo commSelector;
	private Group commConfigGroup;
	private Composite commConfigContents;

	@Override
	protected CommandStack getCommandStack(final IWorkbenchPart part, final Object input) {
		final Segment type = getInputType(input);
		if (null != type) {
			return type.getAutomationSystem().getCommandStack();
		}
		return null;
	}

	@Override
	protected Segment getInputType(Object input) {
		if (input instanceof EditPart) {
			input = ((EditPart) input).getModel();
		}
		if (input instanceof Segment) {
			return ((Segment) input);
		}
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createSegmentInfoGroup(getLeftComposite());
		createCommunicationInfoGroup(getRightComposite());
	}

	private void createCommunicationInfoGroup(final Composite parent) {
		commConfigGroup = getWidgetFactory().createGroup(parent, "Communication Details");
		commConfigGroup.setLayout(new GridLayout(2, false));
		commConfigGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		commConfigContents = new Composite(commConfigGroup, SWT.BORDER);
	}

	protected void createSegmentInfoGroup(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, "Instance Name:");
		nameText = createGroupText(composite, true);
		nameText.addModifyListener(event -> {
			removeContentAdapter();
			executeCommand(new ChangeNameCommand(getType(), nameText.getText()));
			addContentAdapter();
		});

		getWidgetFactory().createCLabel(composite, "Instance Comment:");
		commentText = createGroupText(composite, true);
		final GridData gridData = new GridData(SWT.FILL, 0, true, false);
		commentText.setLayoutData(gridData);
		commentText.addModifyListener(event -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});

		checkbox = getWidgetFactory().createButton(parent, "Configure Communication", SWT.CHECK);
		checkbox.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (!checkbox.getSelection()) {
					removeContentAdapter();
					executeCommand(new ChangeCommunicationConfigurationCommand(getType()));
					addContentAdapter();
				}
				commSelector.setVisible(checkbox.getSelection());
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				// nothing to do here
			}

		});
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(false, false).applyTo(checkbox);

		commSelector = ComboBoxWidgetFactory.createCombo(parent);
		commSelector.setItems(getCommConfigNamesFromExtensionPoint());
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(commSelector);
		commSelector.layout();
		commSelector.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				final int selected = commSelector.getSelectionIndex();
				if (selected == 0) {
					// delete configuration
					removeContentAdapter();
					executeCommand(new ChangeCommunicationConfigurationCommand(getType()));
					addContentAdapter();
					getRightComposite().setVisible(false);
				}
				final String selectedName = commSelector.getItems()[selected];
				final CommunicationConfigurationDetails commConfig = getCommConfigUiFromExtensionPoint(selectedName);
				if (commConfig != null) {
					removeContentAdapter();
					executeCommand(new ChangeCommunicationConfigurationCommand(commConfig.createModel(), getType()));
					addContentAdapter();
					commConfigContents.dispose();
					commConfigContents = commConfig.createUi(commConfigGroup, getType().getCommunication(),
							getSection(), getWidgetFactory());
					GridDataFactory.fillDefaults().grab(true, true).applyTo(commConfigContents);
					refresh();
				}
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}

	private CommandExecutor getSection() {
		return this;
	}

	@Override
	protected Segment getType() {
		if (type instanceof Segment) {
			return (Segment) type;
		}
		return null;
	}

	@Override
	protected void setInputCode() {
		// currently nothing to do here
	}

	@Override
	protected void setInputInit() {
		// currently nothing to do here
	}

	@Override
	public void refresh() {
		super.refresh();

		if (null != getType()) {
			nameText.setText(getType().getName());
			commentText.setText(getType().getComment());
			checkbox.setSelection(getType().getCommunication() != null);
			commSelector.setVisible(checkbox.getSelection());
			commSelector.select(getIndexOfCommunication(commSelector.getItems(), getCommunicationType()));
			getRightComposite().setVisible(checkbox.getSelection() && (commSelector.getSelectionIndex() != 0));
		}
	}

	private static int getIndexOfCommunication(final String[] names, final String id) {
		final IConfigurationElement selected = getCommConfigExtensionPoint("id", id); //$NON-NLS-1$
		if ((selected == null) || (selected.getAttribute("label") == null)) {
			return 0;
		}
		return Arrays.asList(names).indexOf(selected.getAttribute("label")); //$NON-NLS-1$
	}

	private String getCommunicationType() {
		if (getType().getCommunication() == null) {
			return ""; //$NON-NLS-1$
		}
		return getType().getCommunication().getId();
	}

	private static String[] getCommConfigNamesFromExtensionPoint() {
		final IConfigurationElement[] config = getCommConfigExtensionPoint();
		final List<String> names = new ArrayList<>();
		names.add(""); //$NON-NLS-1$
		for (final IConfigurationElement e : config) {
			names.add(e.getAttribute("label")); //$NON-NLS-1$
		}
		return names.toArray(new String[0]);
	}

	private static CommunicationConfigurationDetails getCommConfigUiFromExtensionPoint(final String name) {
		final IConfigurationElement selected = getCommConfigExtensionPoint("label", name); //$NON-NLS-1$
		if (selected != null) {
			try {
				final Object o = selected.createExecutableExtension("class"); //$NON-NLS-1$
				if (o instanceof CommunicationConfigurationDetails) {
					return (CommunicationConfigurationDetails) o;
				}
			} catch (final CoreException ex) {
				FordiacLogHelper.logError(ex.getMessage());
			}
		}
		return null;
	}

	private static IConfigurationElement[] getCommConfigExtensionPoint() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		return registry.getConfigurationElementsFor(COMM_CONFIG_ID);
	}

	private static IConfigurationElement getCommConfigExtensionPoint(final String attribute, final String value) {
		for (final IConfigurationElement e : getCommConfigExtensionPoint()) {
			if (value.equals(e.getAttribute(attribute))) {
				return e;
			}
		}
		return null;
	}
}

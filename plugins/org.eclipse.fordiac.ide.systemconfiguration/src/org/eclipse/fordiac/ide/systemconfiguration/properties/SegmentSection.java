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

import org.eclipse.fordiac.ide.gef.properties.AbstractDoubleColumnSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.systemconfiguration.CommunicationConfigurationDetails;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ChangeCommunicationConfigurationCommand;
import org.eclipse.fordiac.ide.ui.widget.ComboBoxWidgetFactory;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
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
		commSelector.setItems(CommunicationConfigurationDetails.getCommConfigNamesFromExtensionPoint());
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
				final CommunicationConfigurationDetails commConfig = CommunicationConfigurationDetails
						.getCommConfigUiFromExtensionPoint(selectedName);
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
			commSelector.select(CommunicationConfigurationDetails.getIndexOfCommunication(commSelector.getItems(),
					getCommunicationType()));
			getRightComposite().setVisible(checkbox.getSelection() && (commSelector.getSelectionIndex() != 0));
		}
	}



	private String getCommunicationType() {
		return getType().getTypeName();
	}




}

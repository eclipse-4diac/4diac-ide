/*******************************************************************************
 * Copyright (c) 2022, 2024 Johannes Kepler University
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
import org.eclipse.fordiac.ide.systemconfiguration.Messages;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.fordiac.ide.ui.widget.CommandExecutor;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class SegmentSection extends AbstractDoubleColumnSection {

	private Text nameText;
	private Text commentText;
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
		if (input instanceof final EditPart ep) {
			input = ep.getModel();
		}
		if (input instanceof final Segment segment) {
			return segment;
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
			executeCommand(ChangeNameCommand.forName(getType(), nameText.getText()));
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
	}

	private CommandExecutor getSection() {
		return this;
	}

	@Override
	protected Segment getType() {
		if (type instanceof final Segment segment) {
			return segment;
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
	protected void performRefresh() {
		nameText.setText(getType().getName());
		commentText.setText(getType().getComment());
		// TODO this works, but it's a bit shit as it relies on the id of the extension
		// never being changed.
		// Maybe find a way to make it nicer eventually
		getRightComposite().setVisible(!"Ethernet".equals(getCommunicationType())); //$NON-NLS-1$
		final CommunicationConfigurationDetails commConfig = CommunicationConfigurationDetails
				.getCommConfigUiFromExtensionPoint(getCommunicationType(),
						CommunicationConfigurationDetails.COMM_EXT_ATT_ID);
		if (commConfig == null) {
			ErrorMessenger.popUpErrorMessage(Messages.Segment_NoConfigErrorMessage);
			return;
		}
		commConfigContents.dispose();
		commConfigContents = commConfig.createUi(commConfigGroup, getType().getCommunication(), getSection(),
				getWidgetFactory());
		commConfigContents.pack();
	}

	private String getCommunicationType() {
		return getType().getTypeName();
	}

}

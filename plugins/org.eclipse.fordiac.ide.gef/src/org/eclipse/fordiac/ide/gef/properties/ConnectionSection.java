/*******************************************************************************
 * Copyright (c) 2017, 2024 fortiss GmbH, Johannes Kepler University Linz,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr
 *     - merged the ConnectionSection classes
 *   Alois Zoitl - cleaned command stack handling for property sections
 *   Michael Oberlehner - added show connection section
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.model.commands.change.ChangeConnectionCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.HideConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.change.NegateConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class ConnectionSection extends AbstractSection {
	private Text commentText;
	private Text sourceText;
	private Text targetText;
	private Button showConnectionButton;
	private Button negateConnectionButton;
	private CLabel negateConnectionLabel;

	@Override
	protected Connection getType() {
		return (Connection) type;
	}

	private Connection getConnection() {
		return getType();
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, Messages.ConnectionSection_Source);
		sourceText = createGroupText(composite, false);
		getWidgetFactory().createCLabel(composite, Messages.ConnectionSection_Target);
		targetText = createGroupText(composite, false);
		getWidgetFactory().createCLabel(composite, Messages.ConnectionSection_Comment);
		commentText = createGroupText(composite, true);
		commentText.addModifyListener(event -> {
			removeContentAdapter();
			executeCommand(new ChangeConnectionCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});

		getWidgetFactory().createCLabel(composite, Messages.ConnectionSection_ShowConnection);
		showConnectionButton = getWidgetFactory().createButton(composite, "", SWT.CHECK); //$NON-NLS-1$
		showConnectionButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final Button btn = (Button) e.getSource();
				final boolean visible = btn.getSelection();
				executeCommand(new HideConnectionCommand(getConnection(), visible));
			}
		});

		negateConnectionLabel = getWidgetFactory().createCLabel(composite, Messages.ConnectionSection_NegateConnection);
		negateConnectionButton = getWidgetFactory().createButton(composite, "", SWT.CHECK);
		negateConnectionButton.setEnabled(false);

		negateConnectionButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final boolean negated = negateConnectionButton.getSelection();
				executeCommand(new NegateConnectionCommand(getConnection(), negated));
			}
		});

	}

	@Override
	protected void performRefresh() {
		commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
		if (null != getType().getSource()) {
			sourceText.setText(
					getFBNameFromIInterfaceElement(getType().getSource()) + "." + getType().getSource().getName()); //$NON-NLS-1$
			if (isViewer()) {
				commentText.setEditable(false);
				commentText.setEnabled(false);
			} else {
				commentText.setEditable(true);
				commentText.setEnabled(true);
			}
		}
		if (null != getType().getDestination()) {
			targetText.setText(getFBNameFromIInterfaceElement(getType().getDestination()) + "." //$NON-NLS-1$
					+ getType().getDestination().getName());
		}
		showConnectionButton.setSelection(getConnection().isVisible());

		final boolean showNegated = getConnection().supportsNegated() || getConnection().isNegated();
		negateConnectionLabel.setVisible(showNegated);
		negateConnectionButton.setVisible(showNegated);
		negateConnectionButton.setSelection(getConnection().isNegated());
		negateConnectionButton.setEnabled(showNegated);
	}

	private boolean isViewer() {
		final EObject sourceBlock = getType().getSource().eContainer().eContainer();
		return (sourceBlock instanceof CompositeFBType) || // connection to interface
				((sourceBlock instanceof final FBNetworkElement fbne) && fbne.isContainedInTypedInstance());
	}

	@Override
	protected Connection getInputType(final Object input) {
		final Object inputHelper = (input instanceof final EditPart ep) ? ep.getModel() : input;
		if ((inputHelper instanceof final Connection con) && (isValidConnection(con))) {
			return con;
		}
		return null;
	}

	private static boolean isValidConnection(final Connection con) {
		// only allow connections that are fully included in the model. In rare cases it
		// can be that during command
		// execution we may get half updated connections. This should protect against
		// it.
		return con.eContainer() != null && con.getSource() != null && con.getDestination() != null
				&& con.getSource().eContainer() != null && con.getDestination().eContainer() != null;
	}

	private static String getFBNameFromIInterfaceElement(final IInterfaceElement element) {
		return (element.eContainer().eContainer() instanceof final FBNetworkElement fbne) ? fbne.getName() : ""; //$NON-NLS-1$
	}

	@Override
	protected void setInputCode() {
		// nothing needed to be done here
	}

	@Override
	protected void setInputInit() {
		// nothing needed to be done here
	}
}

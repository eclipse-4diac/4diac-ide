/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.utilities.GetEditPartFromGraficalViewerHelper;
import org.eclipse.fordiac.ide.gef.properties.AbstractDoubleColumnSection;
import org.eclipse.fordiac.ide.gef.widgets.ConnectionDisplayWidget;
import org.eclipse.fordiac.ide.gef.widgets.InternalConnectionsViewer;
import org.eclipse.fordiac.ide.gef.widgets.PinInfoBasicWidget;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.nat.EventTypeSelectionTreeContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.EventTypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.ITypeSelectionContentProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class PinEventInfoSection extends AbstractDoubleColumnSection {

	private PinInfoBasicWidget pinInfo;
	private ConnectionDisplayWidget inConnections;
	private InternalConnectionsViewer outConnections;

	private static final int NUM_OF_CONN_DISPLAYS = 2;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		// Enforcing the layout so the connections would be side by side
		getRightComposite().setLayout(new GridLayout(NUM_OF_CONN_DISPLAYS, true));

		final Composite middleComposite = createSmallComposite(getRightComposite());
		final Composite rightComposite = createSmallComposite(getRightComposite());

		pinInfo = pinInfoCreation(getLeftComposite());
		inConnections = new ConnectionDisplayWidget(getWidgetFactory(), middleComposite, this);
		outConnections = new InternalConnectionsViewer(getWidgetFactory(), rightComposite, this);

	}

	protected PinInfoBasicWidget pinInfoCreation(final Composite parent) {
		return new PinInfoBasicWidget(parent, getWidgetFactory()) {
			@Override
			protected void onNameChange(final Text name) {
				final ChangeNameCommand changeNameCommand = ChangeNameCommand.forName(getType(), name.getText());
				if (isExpandedSubappPin()) {
					executeCommand(new ResizeGroupOrSubappCommand(GetEditPartFromGraficalViewerHelper
							.findAbstractContainerContentEditFromInterfaceElement(getType()), changeNameCommand));
				} else {
					executeCommand(changeNameCommand);
				}
			}

			private boolean isExpandedSubappPin() {
				final IInterfaceElement ie = getType();
				return (ie != null) && (ie.getFBNetworkElement() instanceof final SubApp subapp) && subapp.isUnfolded();
			}
		};
	}

	@Override
	protected IInterfaceElement getType() {
		return (IInterfaceElement) type;
	}

	@Override
	protected EObject getInputType(final Object input) {
		Object refType = input;
		if (input instanceof EditPart) {
			refType = ((EditPart) input).getModel();
		}
		return (refType instanceof IInterfaceElement) ? (IInterfaceElement) refType : null;
	}

	@Override
	public void refresh() {
		final CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != pinInfo && null != inConnections && null != outConnections && null != getType()) {
			pinInfo.refresh();
			inConnections.refreshConnectionsViewer(getType());
			outConnections.refreshConnectionsViewer(getType());
			final FBNetworkElement fb = getType().getFBNetworkElement();
			if (fb != null) {
				inConnections.setEditable(true);
				outConnections.setEditable(true);
			}
		}
		commandStack = commandStackBuffer;
	}

	@Override
	protected void setInputCode() {
		pinInfo.disableAllFields();
		inConnections.setEditable(false);
		outConnections.setEditable(false);
	}

	@Override
	protected void setInputInit() {
		if (pinInfo != null) {
			pinInfo.initialize(getType(), this::executeCommand);
			pinInfo.getTypeSelectionWidget().initialize(getType(), getTypeSelectionContentProvider(),
					getTypeSelectionTreeContentProvider());
		}
	}

	private Composite createSmallComposite(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		return composite;
	}

	@SuppressWarnings("static-method") // allow subclasses to override
	protected ITypeSelectionContentProvider getTypeSelectionContentProvider() {
		return EventTypeSelectionContentProvider.INSTANCE;
	}

	@SuppressWarnings("static-method") // allow subclasses to override
	protected ITreeContentProvider getTypeSelectionTreeContentProvider() {
		return EventTypeSelectionTreeContentProvider.INSTANCE;
	}
}

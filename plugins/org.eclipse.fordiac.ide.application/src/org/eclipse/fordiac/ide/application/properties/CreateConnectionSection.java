/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger - initial API and implementation and/or initial documentation
 *   Alois Zoitl - reworked the create connection section to work also in CFB
 *                 and subapp types
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class CreateConnectionSection extends AbstractSection {
	private Text commentText;
	private Text sourceText;
	private Text targetText;

	private List<IInterfaceElement> getSelectionList() {
		return (type instanceof List<?>) ? (List<IInterfaceElement>) type : Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<IInterfaceElement> getInputType(Object input) {
		List<IInterfaceElement> editParts = new ArrayList<>();
		if (input instanceof IStructuredSelection
				&& ((IStructuredSelection) input).getFirstElement() instanceof EditPart
				&& ((EditPart) ((IStructuredSelection) input).getFirstElement())
						.getModel() instanceof IInterfaceElement) {

			List<Object> selectionList = ((IStructuredSelection) input).toList();

			IInterfaceElement first = (IInterfaceElement) ((EditPart) selectionList.get(0)).getModel();
			IInterfaceElement second = (IInterfaceElement) ((EditPart) selectionList.get(1)).getModel();
			editParts.add(second);
			editParts.add(first);
		}
		return editParts;
	}

	@Override
	protected EObject getType() {
		return null;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(2, false));
		parent.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, org.eclipse.fordiac.ide.gef.Messages.ConnectionSection_Source);
		sourceText = createGroupText(composite, false);
		getWidgetFactory().createCLabel(composite, org.eclipse.fordiac.ide.gef.Messages.ConnectionSection_Target);
		targetText = createGroupText(composite, false);
		getWidgetFactory().createCLabel(composite, org.eclipse.fordiac.ide.gef.Messages.ConnectionSection_Comment);
		commentText = createGroupText(composite, true);

		Button createConnectionButton = getWidgetFactory().createButton(parent,
				Messages.CreateConnectionSection_CreateConnection, SWT.PUSH);
		createConnectionButton.setLayoutData(new GridData(SWT.NONE, SWT.FILL, false, true));
		createConnectionButton
				.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ADD));
		createConnectionButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				AbstractConnectionCreateCommand cmd = null;
				IInterfaceElement source = getSelectionList().get(0);
				IInterfaceElement dest = getSelectionList().get(1);
				FBNetwork nw = getFBNetwork(source, dest);
				if (source instanceof Event) {
					cmd = new EventConnectionCreateCommand(nw);
				} else if (source instanceof AdapterDeclaration) {
					cmd = new AdapterConnectionCreateCommand(nw);
				} else {
					cmd = new DataConnectionCreateCommand(nw);
				}
				cmd.setSource(source);
				cmd.setDestination(dest);
				executeCommand(cmd);
			}

		});
	}

	private static FBNetwork getFBNetwork(IInterfaceElement source, IInterfaceElement dest) {
		if (source.eContainer().eContainer() instanceof CompositeFBType) {
			return ((CompositeFBType) source.eContainer().eContainer()).getFBNetwork();
		} else if (source.getFBNetworkElement().getFbNetwork() != dest.getFBNetworkElement().getFbNetwork()) {
			// one of the both is a untyped subapp interface element
			if (source.getFBNetworkElement() instanceof SubApp) {
				if (((SubApp) source.getFBNetworkElement()).getSubAppNetwork() == dest.getFBNetworkElement()
						.getFbNetwork()) {
					return dest.getFBNetworkElement().getFbNetwork();
				} else {
					return source.getFBNetworkElement().getFbNetwork();
				}
			}
		}
		return source.getFBNetworkElement().getFbNetwork();
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		Assert.isTrue(selection instanceof IStructuredSelection);
		commandStack = getCommandStack(part, selection);
		if (null == commandStack) { // disable all fields
			commentText.setEnabled(false);
			sourceText.setEnabled(false);
			targetText.setEnabled(false);
		}
		setType(selection);
	}

	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			sourceText.setText(getInterfaceName(true));
			targetText.setText(getInterfaceName(false));
		}
		commandStack = commandStackBuffer;
	}

	private IInterfaceElement getInterfaceElement(boolean source) {
		if (source) {
			return (IInterfaceElement) ((List<?>) type).get(0);
		}
		return (IInterfaceElement) ((List<?>) type).get(1);
	}

	private String getInterfaceName(boolean source) {
		Object element = getInterfaceElement(source);
		return getFBName((INamedElement) element) + "." + ((INamedElement) element).getName(); //$NON-NLS-1$
	}

	private static String getFBName(INamedElement element) {
		return ((INamedElement) element.eContainer().eContainer()).getName();
	}

	@Override
	protected void setInputCode() {
		// nothing to do here
	}

	@Override
	protected void setInputInit() {
		// nothing to do here
	}
}

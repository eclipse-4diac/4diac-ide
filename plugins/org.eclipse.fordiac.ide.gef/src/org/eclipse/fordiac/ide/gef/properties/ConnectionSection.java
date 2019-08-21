/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 *               2019 Johannes Kepler University Linz
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr
 *     - merged the ConnectionSection classes
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class ConnectionSection extends AbstractSection {
	private Text commentText;
	private Text sourceText;
	private Text targetText;

	@Override
	protected Connection getType() {
		return (Connection) type;
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		createSuperControls = false;
		super.createControls(parent, tabbedPropertySheetPage);
		Composite composite = getWidgetFactory().createComposite(parent);
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
			executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});
	}

	@Override
	public void refresh() {
		CommandStack commandStackBuffer = commandStack;
		commandStack = null;
		if (null != type) {
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			if (null != getType().getSource()) {
				sourceText.setText(
						getFBNameFromIInterfaceElement(getType().getSource()) + "." + getType().getSource().getName()); //$NON-NLS-1$
			}
			if (null != getType().getDestination()) {
				targetText.setText(getFBNameFromIInterfaceElement(getType().getDestination()) + "." //$NON-NLS-1$
						+ getType().getDestination().getName());
			}
		}
		commandStack = commandStackBuffer;
	}

	@Override
	protected Connection getInputType(Object input) {
		Object inputHelper = input instanceof EditPart ? ((EditPart) input).getModel() : input;
		if (inputHelper instanceof Connection) {
			return ((Connection) inputHelper);
		}
		return null;
	}

	@Override
	protected CommandStack getCommandStack(IWorkbenchPart part, Object input) {
		return part.getAdapter(CommandStack.class);
	}

	private static String getFBNameFromIInterfaceElement(IInterfaceElement element) {
		return element.eContainer().eContainer() instanceof FBNetworkElement
				? ((FBNetworkElement) element.eContainer().eContainer()).getName()
				: ""; //$NON-NLS-1$
	}

	@Override
	protected void setInputCode() {
	}

	@Override
	protected void setInputInit() {
	}
}

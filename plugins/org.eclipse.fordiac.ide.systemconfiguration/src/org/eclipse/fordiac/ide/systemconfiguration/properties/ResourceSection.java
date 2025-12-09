/*******************************************************************************
 * Copyright (c) 2017, 2025 fortiss GmbH, Johannes Kepler University Linz
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Monika Wenger - initial API and implementation and/or initial documentation
 *    Bianca Wiesmayr - merge double ResourceInterfaceSection to one class
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractInterfaceSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class ResourceSection extends AbstractInterfaceSection {

	@Override
	protected Resource getInputType(final Object input) {
		final Object inputHelper = (input instanceof final EditPart e) ? e.getModel() : input;
		if (inputHelper instanceof final Resource res) {
			return res;
		}
		return null;
	}

	@Override
	protected void createFBInfoGroup(final Composite parent) {
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
		commentText.addModifyListener(event -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});
	}
}

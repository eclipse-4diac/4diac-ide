/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.hierarchymanager.ui.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Level;
import org.eclipse.fordiac.ide.hierarchymanager.ui.handlers.AbstractHierarchyHandler;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.ChangeLevelCommentOperation;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.RenameLevelOperation;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class CommentSection extends AbstractSection {

	private Text commentText;
	private Text nameText;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		final Composite fbInfoContainer = createFBInfoContainer(parent);
		createNameEntry(fbInfoContainer);
		createCommentEntry(fbInfoContainer);
	}

	@Override
	public void refresh() {
		if ((getType() != null)) {
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			nameText.setText(getType().getName() != null ? getType().getName() : "N/A"); //$NON-NLS-1$
		}
	}

	protected Composite createFBInfoContainer(final Composite parent) {
		final Composite fbInfoContainer = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(fbInfoContainer);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, true).applyTo(fbInfoContainer);
		return fbInfoContainer;
	}

	private void createNameEntry(final Composite parent) {
		final CLabel nameLabel = getWidgetFactory().createCLabel(parent, FordiacMessages.Name + ":"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.TOP).grab(false, false).applyTo(nameLabel);

		nameText = createGroupText(parent, true, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(nameText);
		nameText.addModifyListener(e -> {
			removeContentAdapter();
			AbstractHierarchyHandler
			.executeOperation(new RenameLevelOperation(getType(), nameText.getText()));
			addContentAdapter();
		});
	}

	private void createCommentEntry(final Composite parent) {
		final CLabel commentLabel = getWidgetFactory().createCLabel(parent, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.TOP).grab(false, false).applyTo(commentLabel);

		commentText = createGroupText(parent, true, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).hint(SWT.DEFAULT, 6 * commentText.getLineHeight())
		.grab(true, false).applyTo(commentText);
		commentText.addModifyListener(e -> {
			removeContentAdapter();
			AbstractHierarchyHandler
			.executeOperation(new ChangeLevelCommentOperation(getType(), commentText.getText()));
			addContentAdapter();
		});
	}

	@Override
	protected Object getInputType(final Object input) {
		return CommentSectionFilter.levelFromSelectedObject(input);
	}

	@Override
	protected Level getType() {
		if (type instanceof final Level lvl) {
			return lvl;
		}
		return null;
	}

	@Override
	protected void setInputCode() {
		// Nothing for now
	}

	@Override
	protected void setInputInit() {
		// nothing to do for now
	}

}
/*******************************************************************************
 * Copyright (c) 2023, 2024 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class CommentPropertySection extends AbstractSection {

	private Text commentText;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		final Composite fbInfoContainer = createFBInfoContainer(parent);
		createCommentEntry(fbInfoContainer);
	}

	@Override
	protected void performRefresh() {
		commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
	}

	protected Composite createFBInfoContainer(final Composite parent) {
		final Composite fbInfoContainer = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(fbInfoContainer);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(fbInfoContainer);
		return fbInfoContainer;
	}

	private void createCommentEntry(final Composite parent) {
		commentText = createGroupText(parent, true, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(commentText);
		commentText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});
	}

	@Override
	protected Object getInputType(final Object input) {
		return CommentSectionFilter.commentFromSelectedObject(input);
	}

	@Override
	protected Comment getType() {
		if (type instanceof final Comment comment) {
			return comment;
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

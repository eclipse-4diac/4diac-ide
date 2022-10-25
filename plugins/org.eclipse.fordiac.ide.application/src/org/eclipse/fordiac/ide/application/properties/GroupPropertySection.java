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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.editparts.GroupEditPart;
import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class GroupPropertySection extends AbstractSection {

	private Text nameText;
	private Text commentText;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		final Composite fbInfoContainer = createFBInfoContainer(parent);
		createNameEntry(fbInfoContainer);
		createCommentEntry(fbInfoContainer);
	}

	@Override
	public void refresh() {
		if ((getType() != null) && !nameText.isDisposed() && !nameText.getParent().isDisposed()) {
			final CommandStack commandStackBuffer = commandStack;
			commandStack = null;
			nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			commandStack = commandStackBuffer;
		}
	}

	protected Composite createFBInfoContainer(final Composite parent) {
		final Composite fbInfoContainer = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(fbInfoContainer);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(fbInfoContainer);
		return fbInfoContainer;
	}

	private void createNameEntry(final Composite fbInfoGroup) {
		getWidgetFactory().createCLabel(fbInfoGroup, FordiacMessages.Name + ":"); //$NON-NLS-1$
		nameText = createGroupText(fbInfoGroup, true);
		nameText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeNameCommand(getType(), nameText.getText()));
			addContentAdapter();
		});
	}

	private void createCommentEntry(final Composite parent) {
		final CLabel commentLabel = getWidgetFactory().createCLabel(parent, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.TOP).grab(false, false).applyTo(commentLabel);

		commentText = createGroupText(parent, true, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(commentText);
		commentText.addModifyListener(e -> {
			removeContentAdapter();

			if (EditorUtils.getGraphicalViewerFromCurrentActiveEditor() != null && getType() instanceof Group) {
				final Object groupforFBNetowrkEditPart = EditorUtils.getGraphicalViewerFromCurrentActiveEditor()
						.getEditPartRegistry().get(getType());
				if (groupforFBNetowrkEditPart instanceof GroupEditPart
						&& ((GroupEditPart) groupforFBNetowrkEditPart).getContentEP() != null) {
					executeCommand(new ResizeGroupOrSubappCommand(
							((GroupEditPart) groupforFBNetowrkEditPart).getContentEP(),
							new ChangeCommentCommand(getType(), commentText.getText())));
				}
			} else {
				executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			}

			addContentAdapter();
		});
	}


	@Override
	protected Object getInputType(final Object input) {
		return GroupSectionFilter.groupFromSelectedObejct(input);
	}

	@Override
	protected Group getType() {
		if (type instanceof Group) {
			return (Group) type;
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

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
import org.eclipse.fordiac.ide.gef.properties.AbstractDoubleColumnSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeGroupBoundsCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeGroupSizeLockCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class GroupPropertySection extends AbstractDoubleColumnSection {

	private static final int MAX_INPUT_LENGTH = 5;

	private Text nameText;
	private Text commentText;
	private Text heightText;
	private Text widthText;
	private Button lockCheckbox;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		final Composite fbInfoContainer = createFBInfoContainer(getLeftComposite());
		createNameEntry(fbInfoContainer);
		createCommentEntry(fbInfoContainer);

		final Composite sizeGroup = createSizeGroup(getRightComposite());
		final Composite hwGroup = createHeightAndWidthGroup(sizeGroup);
		createHeightEntry(hwGroup);
		createWidthEntry(hwGroup);
		createLockGroupCheckbox(sizeGroup);
	}

	@Override
	public void refresh() {
		if ((getType() != null) && !nameText.isDisposed() && !nameText.getParent().isDisposed()) {
			final CommandStack commandStackBuffer = commandStack;
			commandStack = null;
			nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			heightText.setText(Integer.toString(getType().getHeight()));
			widthText.setText(Integer.toString(getType().getWidth()));
			lockCheckbox.setSelection(getType().isLocked());
			commandStack = commandStackBuffer;
		}
	}

	protected Composite createFBInfoContainer(final Composite parent) {
		final Composite fbInfoContainer = getWidgetFactory().createComposite(parent);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(fbInfoContainer);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(fbInfoContainer);
		return fbInfoContainer;
	}

	private Composite createSizeGroup(final Composite parent) {
		final var group = new org.eclipse.swt.widgets.Group(parent, SWT.SHADOW_NONE);
		group.setText(FordiacMessages.Size);
		getWidgetFactory().adapt(group);

		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(group);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(group);
		return group;
	}

	private Composite createHeightAndWidthGroup(final Composite parent) {
		final var composite = new Composite(parent, SWT.SHADOW_NONE);
		getWidgetFactory().adapt(composite);

		GridLayoutFactory.fillDefaults().numColumns(4).applyTo(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(composite);
		return composite;
	}

	private void createNameEntry(final Composite fbInfoGroup) {
		getWidgetFactory().createCLabel(fbInfoGroup, FordiacMessages.Name + ":"); //$NON-NLS-1$
		nameText = createGroupText(fbInfoGroup, true);
		nameText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(ChangeNameCommand.forName(getType(), nameText.getText()));
			addContentAdapter();
		});
	}

	private void createHeightEntry(final Composite parent) {
		getWidgetFactory().createCLabel(parent, FordiacMessages.Height + ":"); //$NON-NLS-1$
		heightText = createGroupText(parent, true);
		heightText.setTextLimit(MAX_INPUT_LENGTH);
		heightText.addVerifyListener(GroupPropertySection::ensureTextContainsOnlyDigits);
		heightText.addModifyListener(e -> {
			if (getType() != null) {
				final int heightDiff;
				try {
					heightDiff = Integer.parseInt(heightText.getText()) - getType().getHeight();
				} catch (final Exception exc) {
					return;
				}
				removeContentAdapter();
				executeCommand(new ChangeGroupBoundsCommand(getType(), 0, 0, 0, heightDiff));
				addContentAdapter();
			}
		});
	}

	private void createWidthEntry(final Composite parent) {
		getWidgetFactory().createCLabel(parent, FordiacMessages.Width + ":"); //$NON-NLS-1$
		widthText = createGroupText(parent, true);
		widthText.setTextLimit(MAX_INPUT_LENGTH);
		widthText.addVerifyListener(GroupPropertySection::ensureTextContainsOnlyDigits);
		widthText.addModifyListener(e -> {
			if (getType() != null) {
				final int widthDiff;
				try {
					widthDiff = Integer.parseInt(widthText.getText()) - getType().getWidth();
				} catch (final Exception exc) {
					return;
				}
				removeContentAdapter();
				executeCommand(new ChangeGroupBoundsCommand(getType(), 0, 0, widthDiff, 0));
				addContentAdapter();
			}
		});
	}

	private void createLockGroupCheckbox(final Composite parent) {
		lockCheckbox = getWidgetFactory().createButton(parent, FordiacMessages.Group_LABEL_LockSize, SWT.CHECK);
		lockCheckbox.setToolTipText(FordiacMessages.Group_TOOLTIP_DisableAutoResize);
		lockCheckbox.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
			if (getType() != null) {
				removeContentAdapter();
				executeCommand(new ChangeGroupSizeLockCommand(getType(), lockCheckbox.getSelection()));
				addContentAdapter();
			}
		}));
	}

	private static void ensureTextContainsOnlyDigits(final VerifyEvent e) {
		e.doit = e.text.matches("\\d*"); //$NON-NLS-1$
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
				if (groupforFBNetowrkEditPart instanceof final GroupEditPart gep && gep.getContentEP() != null) {
					executeCommand(new ResizeGroupOrSubappCommand(gep.getContentEP(),
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
		if (type instanceof final Group group) {
			return group;
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

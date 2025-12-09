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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Level;
import org.eclipse.fordiac.ide.hierarchymanager.ui.handlers.AbstractHierarchyHandler;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.ChangeLevelCommentOperation;
import org.eclipse.fordiac.ide.hierarchymanager.ui.operations.RenameLevelOperation;
import org.eclipse.fordiac.ide.model.emf.SingleRecursiveContentAdapter;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class LevelSection extends AbstractPropertySection {

	private Text commentText;
	private Text nameText;

	private Level type;
	private Composite parent;
	private boolean blockListeners;

	private final Adapter contentAdapter = new SingleRecursiveContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			notifiyRefresh();
		}
	};

	protected void notifiyRefresh() {
		if ((null != type) && type.eAdapters().contains(contentAdapter)) {
			parent.getDisplay().asyncExec(() -> {
				if (!parent.isDisposed()) {
					refresh();
				}
			});
		}
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		this.parent = parent;
		final Composite fbInfoContainer = createFBInfoContainer(parent);
		createNameEntry(fbInfoContainer);
		createCommentEntry(fbInfoContainer);
	}

	@Override
	public void refresh() {
		if ((type != null)) {
			blockListeners = true;
			commentText.setText(type.getComment() != null ? type.getComment() : ""); //$NON-NLS-1$
			nameText.setText(type.getName() != null ? type.getName() : "N/A"); //$NON-NLS-1$
			blockListeners = false;
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
			if (!blockListeners) {
				removeContentAdapter();
				AbstractHierarchyHandler.executeOperation(new RenameLevelOperation(type, nameText.getText()));
				addContentAdapter();
			}
		});
	}

	private void createCommentEntry(final Composite parent) {
		final CLabel commentLabel = getWidgetFactory().createCLabel(parent, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.TOP).grab(false, false).applyTo(commentLabel);

		commentText = createGroupText(parent, true, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).hint(SWT.DEFAULT, 6 * commentText.getLineHeight())
		.grab(true, false).applyTo(commentText);
		commentText.addModifyListener(e -> {
			if (!blockListeners) {
				removeContentAdapter();
				AbstractHierarchyHandler.executeOperation(new ChangeLevelCommentOperation(type, commentText.getText()));
				addContentAdapter();
			}
		});
	}

	@Override
	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		Object input = selection;
		if (selection instanceof final IStructuredSelection sel) {
			input = sel.getFirstElement();
		}
		removeContentAdapter();
		type = LevelSectionFilter.levelFromSelectedObject(input);
		addContentAdapter();

	}

	protected Text createGroupText(final Composite group, final boolean editable, final int style) {
		final Text text = getWidgetFactory().createText(group, "", style); //$NON-NLS-1$
		text.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		text.setEditable(editable);
		text.setEnabled(editable);
		return text;
	}

	protected void removeContentAdapter() {
		if ((type != null) && type.eAdapters().contains(contentAdapter)) {
			type.eAdapters().remove(contentAdapter);
		}
	}

	protected void addContentAdapter() {
		if ((null != type) && !type.eAdapters().contains(contentAdapter)) {
			type.eAdapters().add(contentAdapter);
		}
	}
}
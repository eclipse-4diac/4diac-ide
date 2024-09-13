/*******************************************************************************
 * Copyright (c) 2014, 2024 fortiss GmbH, Johannes Kepler Universiy Linz
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
 *   Alois Zoitl - extracted the ui into own widget to make it usable in other
 *                 places (e.g., system editor)
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.properties;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.widgets.PackageInfoWidget;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/** Properties tab which shows the FB type information of the selected FB */
public abstract class TypeInfoSection extends AbstractDoubleColumnSection {

	private PackageInfoWidget typeInfo;

	private Text fbTypeNameText;
	private Text commentText;

	private final Adapter typeInfoAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			notifiyRefresh();
		}
	};

	@Override
	protected LibraryElement getType() {
		return (LibraryElement) type;
	}

	@Override
	protected void setInputInit() {
		typeInfo.initialize(getType(), this::executeCommand);
	}

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		createTypeAndCommentSection(getLeftComposite());
		typeInfo = new PackageInfoWidget(getWidgetFactory(), this::getAnnotationModel);
		typeInfo.createControls(getLeftComposite(), getRightComposite());
	}

	private void createTypeAndCommentSection(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		getWidgetFactory().createCLabel(composite, FordiacMessages.TypeName + ":"); //$NON-NLS-1$
		fbTypeNameText = createGroupText(composite, false);
		getWidgetFactory().createCLabel(composite, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		commentText = createGroupText(composite, true);
		commentText.addModifyListener(e -> executeCommand(new ChangeCommentCommand(getType(), commentText.getText())));
	}

	@Override
	public void setInputCode() {
		commentText.setEnabled(false);
		typeInfo.setEnabled(false);
	}

	@Override
	protected void performRefresh() {
		fbTypeNameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
		commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
		commentText.setEditable(!(getType() instanceof FunctionFBType));
		typeInfo.refresh();
	}

	@Override
	protected void performRefreshAnnotations() {
		typeInfo.refreshAnnotations();
	}

	@Override
	protected void addContentAdapter() {
		super.addContentAdapter();
		if (getType() != null) {
			getType().getIdentification().eAdapters().add(typeInfoAdapter);
		}
	}

	@Override
	protected void removeContentAdapter() {
		super.removeContentAdapter();
		if (getType() != null) {
			getType().getIdentification().eAdapters().remove(typeInfoAdapter);
		}
	}

}

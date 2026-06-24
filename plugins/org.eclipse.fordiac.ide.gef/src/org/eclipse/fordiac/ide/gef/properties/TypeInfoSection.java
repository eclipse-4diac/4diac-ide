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
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.TypeRefactoringHelper;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/** Properties tab which shows the FB type information of the selected FB */
public abstract class TypeInfoSection extends AbstractDoubleColumnSection {

	private static final String RENAME_ELEMENT_ICON = "icons/full/etool16/tricks.png"; //$NON-NLS-1$

	private PackageInfoWidget typeInfo;

	private Text fbTypeNameText;
	private Text commentText;
	private Button renameTypeButton;
	private Image renameTypeImage;

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
		GridLayoutFactory.fillDefaults().numColumns(3).equalWidth(false).applyTo(composite);
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		final Label typeNameLabel = getWidgetFactory().createLabel(composite, FordiacMessages.TypeName + ":"); //$NON-NLS-1$
		GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(typeNameLabel);
		fbTypeNameText = createGroupText(composite, false);
		fbTypeNameText.setEnabled(true);
		renameTypeButton = getWidgetFactory().createButton(composite, null, SWT.PUSH);
		renameTypeButton.setToolTipText(Messages.RenameType_Name);
		renameTypeButton.setImage(getRenameTypeImage());
		renameTypeButton.addDisposeListener(e -> disposeRenameTypeImage());
		renameTypeButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(ev -> openTypeRefactoring()));
		GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(renameTypeButton);
		final Label commentLabel = getWidgetFactory().createLabel(composite, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER).applyTo(commentLabel);
		commentText = createGroupText(composite, true);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(commentText);
		commentText.addModifyListener(e -> executeCommand(new ChangeCommentCommand(getType(), commentText.getText())));
	}

	private Image getRenameTypeImage() {
		if (renameTypeImage == null) {
			final ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(PlatformUI.PLUGIN_ID,
					RENAME_ELEMENT_ICON);
			if (imageDescriptor != null) {
				renameTypeImage = imageDescriptor.createImage();
			}
		}
		return renameTypeImage;
	}

	private void disposeRenameTypeImage() {
		if (renameTypeImage != null && !renameTypeImage.isDisposed()) {
			renameTypeImage.dispose();
		}
		renameTypeImage = null;
	}

	private void openTypeRefactoring() {
		TypeRefactoringHelper.openRenameResourceWizard(getType().getTypeEntry(), fbTypeNameText.getShell());
	}

	@Override
	public void setInputCode() {
		renameTypeButton.setEnabled(false);
		commentText.setEnabled(false);
		typeInfo.setEnabled(false);
	}

	@Override
	protected void performRefresh() {
		fbTypeNameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
		renameTypeButton.setEnabled(canRenameType());
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
		if (getType() != null && getType().getIdentification() != null) {
			getType().getIdentification().eAdapters().add(typeInfoAdapter);
		}
	}

	@Override
	protected void removeContentAdapter() {
		super.removeContentAdapter();
		if (getType() != null && getType().getIdentification() != null) {
			getType().getIdentification().eAdapters().remove(typeInfoAdapter);
		}
	}

	private boolean canRenameType() {
		final TypeEntry typeEntry = getType().getTypeEntry();
		return typeEntry != null && typeEntry.getFile() != null && typeEntry.getFile().exists();
	}

}

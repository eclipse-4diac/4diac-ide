/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner
 *   	- initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.editors;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.function.Predicate;

import org.eclipse.fordiac.ide.library.model.library.Product;
import org.eclipse.fordiac.ide.library.model.library.VersionInfo;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class ManifestEditorProductPage extends ManifestEditorPage<Product> {

	private static final String INVALID_DATE_KEY = "manifest.product.versioninfo.date"; //$NON-NLS-1$
	private static final String INVALID_VERSION_KEY = "manifest.product.versioninfo.version"; //$NON-NLS-1$

	protected ManifestEditorProductPage(final ManifestEditor editor, final String id, final String title) {
		super(editor, id, title);
	}

	@Override
	protected void createPageContent(final Composite parent, final FormToolkit toolkit, final IManagedForm form) {
		createProductInformationSection(parent, toolkit);
		createVersionInformationSection(parent, toolkit);
	}

	@Override
	protected Product getModel() {
		return getManifestEditor().getManifest().getProduct();
	}

	@Override
	protected boolean isValid() {
		final VersionInfo versionInfo = getModel().getVersionInfo();
		return VersionComparator.isValidVersion(versionInfo.getVersion()) && isValidDate(versionInfo.getDate());
	}

	private void createProductInformationSection(final Composite parent, final FormToolkit toolkit) {
		final Section productInformationSection = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR);
		productInformationSection.setText(Messages.ManifestEditor_ProductInformation);
		productInformationSection.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		final Composite sectionClient = toolkit.createComposite(productInformationSection);
		sectionClient.setLayout(new GridLayout(1, false));
		productInformationSection.setClient(sectionClient);

		final Composite nameComposite = toolkit.createComposite(sectionClient);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(nameComposite);
		nameComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		toolkit.createLabel(nameComposite, Messages.ManifestEditor_Name);
		toolkit.createLabel(nameComposite, Messages.ManifestEditor_SymbolicName);

		final Text nameText = toolkit.createText(nameComposite,
				getModel().getName() != null ? getModel().getName() : "", SWT.SINGLE | SWT.BORDER); //$NON-NLS-1$
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		bindText(nameText, getModel()::getName, getModel()::setName);

		final Text symbolicNameText = toolkit.createText(nameComposite,
				getModel().getSymbolicName() != null ? getModel().getSymbolicName() : "", //$NON-NLS-1$
				SWT.SINGLE | SWT.BORDER);
		symbolicNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		bindText(symbolicNameText, getModel()::getSymbolicName, getModel()::setSymbolicName);

		toolkit.createLabel(sectionClient, Messages.ManifestEditor_Comment);

		final Text commentText = toolkit.createText(sectionClient,
				getModel().getComment() != null ? getModel().getComment() : "", SWT.SINGLE | SWT.BORDER); //$NON-NLS-1$
		commentText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		bindText(commentText, getModel()::getComment, getModel()::setComment);
	}

	private void createVersionInformationSection(final Composite parent, final FormToolkit toolkit) {
		final Section section = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR);
		section.setText(Messages.ManifestEditor_VersionInformation);
		section.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		final Composite client = toolkit.createComposite(section);
		GridLayoutFactory.swtDefaults().spacing(20, 5).numColumns(2).applyTo(client);
		section.setClient(client);

		final VersionInfo versionInfo = getModel().getVersionInfo();

		toolkit.createLabel(client, Messages.ManifestEditor_Version);

		final Text versionText = toolkit.createText(client,
				versionInfo.getVersion() != null ? versionInfo.getVersion() : "", //$NON-NLS-1$
				SWT.SINGLE | SWT.BORDER);
		versionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		addValidation(versionText, INVALID_VERSION_KEY, Messages.ManifestEditor_InvalidVersion,
				VersionComparator::isValidVersion);
		bindText(versionText, versionInfo::getVersion, versionInfo::setVersion);

		toolkit.createLabel(client, Messages.ManifestEditor_Date);

		final Text dateText = toolkit.createText(client, versionInfo.getDate() != null ? versionInfo.getDate() : "",
				SWT.SINGLE | SWT.BORDER);
		dateText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		bindText(dateText, versionInfo::getDate, versionInfo::setDate);
		addValidation(dateText, INVALID_DATE_KEY, Messages.ManifestEditor_InvalidDate,
				ManifestEditorProductPage::isValidDate);

		toolkit.createLabel(client, Messages.ManifestEditor_Author);

		final Text authorText = toolkit.createText(client,
				versionInfo.getAuthor() != null ? versionInfo.getAuthor() : "", SWT.SINGLE | SWT.BORDER); //$NON-NLS-1$
		authorText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		bindText(authorText, versionInfo::getAuthor, versionInfo::setAuthor);

	}

	private static boolean isValidDate(final String value) {
		if (value == null || value.isBlank()) {
			return false;
		}

		try {
			LocalDate.parse(value);
			return true;
		} catch (final DateTimeParseException e) {
			return false;
		}
	}

	private void addValidation(final Text text, final String validationKey, final String message,
			final Predicate<String> validator) {

		final Runnable update = () -> {
			if (validator.test(text.getText())) {
				getManagedForm().getMessageManager().removeMessage(validationKey, text);
			} else {
				getManagedForm().getMessageManager().addMessage(validationKey, message, null, IMessageProvider.ERROR,
						text);
			}
		};

		text.addModifyListener(event -> update.run());
		update.run();
	}

}

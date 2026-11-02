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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fordiac.ide.library.model.library.LibraryPackage;
import org.eclipse.fordiac.ide.library.model.library.Product;
import org.eclipse.fordiac.ide.library.model.library.VersionInfo;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
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

	private record ValidationControl(Text control, EObject element, EStructuralFeature feature) {
	}

	private final List<ValidationControl> validationControls = new ArrayList<>();

	private record TextBinding(Text control, Supplier<String> getter) {
	}

	private final List<TextBinding> textBindings = new ArrayList<>();

	protected ManifestEditorProductPage(final ManifestEditor editor, final String id, final String title) {
		super(editor, id, title);
	}

	@Override
	protected void createPageContent(final Composite parent, final FormToolkit toolkit, final IManagedForm form) {
		validationControls.clear();
		textBindings.clear();

		createProductInformationSection(parent, toolkit);
		createVersionInformationSection(parent, toolkit);
	}

	@Override
	protected Product getModel() {
		return getManifestEditor().getManifest().getProduct();
	}

	@Override
	protected void refresh() {
		textBindings.forEach(binding -> {
			final Text control = binding.control();

			if (!control.isDisposed()) {
				final String value = Objects.toString(binding.getter().get(), ""); //$NON-NLS-1$

				if (!Objects.equals(control.getText(), value)) {
					control.setText(value);
				}
			}
		});
	}

	@Override
	public void updateValidation(final List<Diagnostic> diagnostics) {
		if (getManagedForm() == null) {
			return;
		}

		final var manager = getManagedForm().getMessageManager();
		manager.removeAllMessages();

		for (final Diagnostic diagnostic : diagnostics) {
			final EObject target = FordiacMarkerHelper.getDiagnosticTarget(diagnostic);

			if (!containsElement(target)) {
				continue;
			}

			final EStructuralFeature feature = FordiacMarkerHelper.getDiagnosticFeature(diagnostic);

			final int severity = toMessageType(diagnostic.getSeverity());

			if (severity == IMessageProvider.NONE) {
				continue;
			}

			final ValidationControl registration = validationControls.stream()
					.filter(entry -> entry.element() == target && entry.feature() == feature).findFirst().orElse(null);

			if (registration != null && !registration.control().isDisposed()) {
				manager.addMessage(diagnostic, diagnostic.getMessage(), null, severity, registration.control());
			} else {
				manager.addMessage(diagnostic, diagnostic.getMessage(), null, severity);
			}
		}

		manager.update();
	}

	private static int toMessageType(final int severity) {
		if ((severity & Diagnostic.ERROR) != 0) {
			return IMessageProvider.ERROR;
		}
		if ((severity & Diagnostic.WARNING) != 0) {
			return IMessageProvider.WARNING;
		}
		if ((severity & Diagnostic.INFO) != 0) {
			return IMessageProvider.INFORMATION;
		}
		return IMessageProvider.NONE;
	}

	private void createProductInformationSection(final Composite parent, final FormToolkit toolkit) {
		final Section section = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR);

		section.setText(Messages.ManifestEditor_ProductInformation);
		section.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		final Composite client = toolkit.createComposite(section);
		client.setLayout(new GridLayout(1, false));
		section.setClient(client);

		final Composite names = toolkit.createComposite(client);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(names);
		names.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		toolkit.createLabel(names, Messages.ManifestEditor_Name);
		toolkit.createLabel(names, Messages.ManifestEditor_SymbolicName);

		final Product product = getModel();

		createText(names, toolkit, product, LibraryPackage.Literals.PRODUCT__NAME, product::getName, product::setName,
				Messages.ManifestEditor_Name);

		createText(names, toolkit, product, LibraryPackage.Literals.PRODUCT__SYMBOLIC_NAME, product::getSymbolicName,
				product::setSymbolicName, Messages.ManifestEditor_SymbolicName);

		toolkit.createLabel(client, Messages.ManifestEditor_Comment);

		createText(client, toolkit, product, LibraryPackage.Literals.PRODUCT__COMMENT, product::getComment,
				product::setComment, Messages.ManifestEditor_Comment);
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
		createText(client, toolkit, versionInfo, LibraryPackage.Literals.VERSION_INFO__VERSION, versionInfo::getVersion,
				versionInfo::setVersion, Messages.ManifestEditor_Version);

		toolkit.createLabel(client, Messages.ManifestEditor_Date);
		createText(client, toolkit, versionInfo, LibraryPackage.Literals.VERSION_INFO__DATE, versionInfo::getDate,
				versionInfo::setDate, Messages.ManifestEditor_Date);

		toolkit.createLabel(client, Messages.ManifestEditor_Author);
		createText(client, toolkit, versionInfo, LibraryPackage.Literals.VERSION_INFO__AUTHOR, versionInfo::getAuthor,
				versionInfo::setAuthor, Messages.ManifestEditor_Author);
	}

	private Text createText(final Composite parent, final FormToolkit toolkit, final EObject element,
			final EStructuralFeature feature, final Supplier<String> getter, final Consumer<String> setter,
			final String label) {

		final Text text = toolkit.createText(parent, Objects.toString(getter.get(), ""), //$NON-NLS-1$
				SWT.SINGLE | SWT.BORDER);

		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		text.addModifyListener(_ -> setValue(label, getter, setter, text.getText()));

		textBindings.add(new TextBinding(text, getter));
		validationControls.add(new ValidationControl(text, element, feature));

		return text;
	}
}

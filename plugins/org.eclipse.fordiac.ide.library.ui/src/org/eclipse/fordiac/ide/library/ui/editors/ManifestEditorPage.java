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

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;

abstract class ManifestEditorPage<T extends EObject> extends FormPage {

	protected ManifestEditorPage(final ManifestEditor editor, final String id, final String title) {
		super(editor, id, title);
	}

	@Override
	protected final void createFormContent(final IManagedForm managedForm) {
		super.createFormContent(managedForm);

		managedForm.getForm().setText(getTitle());
		final Composite body = managedForm.getForm().getBody();
		body.setLayout(new GridLayout(1, false));

		createPageContent(body, managedForm.getToolkit(), managedForm);
	}

	protected boolean containsElement(final EObject element) {
		final T model = getModel();
		return model != null && element != null && (model == element || EcoreUtil.isAncestor(model, element));
	}

	protected abstract void createPageContent(Composite parent, FormToolkit toolkit, IManagedForm form);

	protected abstract T getModel();

	protected void reveal(final EObject element) {
		// do nothing
	}

	protected void refresh() {
		// do nothing
	}

	// use complex type instead
	protected abstract boolean isValid();

	protected final ManifestEditor getManifestEditor() {
		return (ManifestEditor) getEditor();
	}

	protected final void execute(final IUndoableOperation operation) {
		getManifestEditor().execute(operation);
	}

	protected final <V> void setValue(final String label, final Supplier<V> getter, final Consumer<V> setter,
			final V value) {
		if (!Objects.equals(value, getter.get())) {
			execute(new SetValueOperation<>(label, getter, setter, value));
		}
	}

	protected final void bindText(final Text text, final Supplier<String> getter, final Consumer<String> setter,
			final String label) {

		final Consumer<String> boundSetter = value -> {
			setter.accept(value);

			final String textValue = value != null ? value : ""; //$NON-NLS-1$
			if (!Objects.equals(text.getText(), textValue)) {
				text.setText(textValue);
			}
		};

		text.addModifyListener(_ -> {
			final String value = text.getText();

			if (!Objects.equals(value, getter.get())) {
				setValue(label, getter, boundSetter, value);
			}
		});
	}
}
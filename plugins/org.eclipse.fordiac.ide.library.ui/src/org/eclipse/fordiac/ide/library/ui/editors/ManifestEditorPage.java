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
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.library.ui.editors.operations.SetValueOperation;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;

public abstract class ManifestEditorPage<T extends EObject> extends FormPage {

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

	public final void execute(final IUndoableOperation operation) {
		getManifestEditor().execute(operation);
	}

	protected void addControlValidation(final Text text, final String validationKey, final String message,
			final Predicate<String> validator) {

		final Runnable update = () -> {
			if (validator.test(text.getText())) {
				getManagedForm().getMessageManager().removeMessage(validationKey, text);
			} else {
				getManagedForm().getMessageManager().addMessage(validationKey, message, null, IMessageProvider.ERROR,
						text);
			}
		};

		text.addModifyListener(_ -> update.run());
		update.run();
	}

	public final <V> void setValue(final String label, final Supplier<V> getter, final Consumer<V> setter,
			final V value) {
		if (!Objects.equals(value, getter.get())) {
			execute(new SetValueOperation<>(label, getter, setter, value, null));
		}
	}
}

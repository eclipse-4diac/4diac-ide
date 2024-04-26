package org.eclipse.fordiac.ide.library.ui.editors;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

public class ManifestUpdateFormPage extends FormPage {

	public ManifestUpdateFormPage(final String id, final String title) {
		super(id, title);
	}

	public ManifestUpdateFormPage(final FormEditor editor, final String id, final String title) {
		super(editor, id, title);
	}

	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		// TODO Auto-generated method stub
		super.createFormContent(managedForm);
	}
}

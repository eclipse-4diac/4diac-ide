package org.eclipse.fordiac.ide.library.ui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.editor.FormEditor;

public class ManifestEditor extends FormEditor {
	private TextEditor textEditor;

	@Override
	protected void addPages() {
		textEditor = new TextEditor();

		try {
			final int index = addPage(textEditor, getEditorInput());
			setPageText(index, textEditor.getTitle());
			setPageImage(index, textEditor.getTitleImage());

		} catch (final PartInitException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		textEditor.doSave(monitor);
	}

	@Override
	public void doSaveAs() {
		// do nothing
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

}

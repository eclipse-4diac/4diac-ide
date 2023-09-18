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
 *   Alois Zoitl, Lukas Wais
 *                - initial API and implementation and/or initial documentation
 *   Lukas Wais   - implemented base64 image inserting
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.doc.editors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDocumentationCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.nebula.widgets.richtext.RichTextEditor;
import org.eclipse.nebula.widgets.richtext.RichTextEditorConfiguration;
import org.eclipse.nebula.widgets.richtext.toolbar.ToolbarButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.osgi.framework.Bundle;

public class DescriptionEditor extends EditorPart implements IFBTEditorPart {
	// @formatter:off
	private static final String TOOLBAR_GROUP_CONFIGURATION =
			"[" 	 																	//$NON-NLS-1$
			+ "{ name: 'clipboard', groups: [ 'undo', 'clipboard'] },"					//$NON-NLS-1$
			+ "{ name: 'colors' },"  													//$NON-NLS-1$
			+ "{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] }," 			//$NON-NLS-1$
			+ "{ name: 'styles' }, " 													//$NON-NLS-1$
			+ "{ name: 'paragraph', groups: [ 'align', 'list', 'indent' ] }," 			//$NON-NLS-1$
			+ "{ name: 'find'}," 														//$NON-NLS-1$
			+ "{ name: 'insert' }," 													//$NON-NLS-1$
			+ "{ name: 'links' }," 														//$NON-NLS-1$
			+ "]"; 																		//$NON-NLS-1$

	// @formatter:on

	private CommandStack commandStack;
	private RichTextEditor editor;

	private FBType getFbType() {
		return getEditorInput().getContent();
	}

	@Override
	public FBTypeEditorInput getEditorInput() {
		return (FBTypeEditorInput) super.getEditorInput();
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// nothing to be done
	}

	@Override
	public void doSaveAs() {
		// nothing to be done
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setInput(input);
		setSite(site);
		setPartName("Description"); //$NON-NLS-1$
	}

	@Override
	public void dispose() {
		commandStack = null;
		super.dispose();
	}

	@Override
	public boolean isDirty() {
		return commandStack.isDirty();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	final Bundle bundle = Platform.getBundle("org.eclipse.fordiac.ide.fbtypeeditor.doc"); //$NON-NLS-1$
	final URL url = bundle.getEntry("icon/insert_image.png"); //$NON-NLS-1$

	private class InsertConvertedImageButton extends ToolbarButton {
		public InsertConvertedImageButton() {
			super("insert_image", "insert_base64_image", "insert converted image", "insert", url); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}

		@Override
		public Object execute() {
			insertImage();
			return null;
		}

		private void insertImage() {
			final FileDialog dialog = new FileDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
			final String filename = dialog.open();
			if (filename != null) {
				insertEncodedBase64Image(new File(filename));
			}
		}

		private void insertEncodedBase64Image(final File image) {
			final WorkspaceJob job = new WorkspaceJob("convert image to base64") { //$NON-NLS-1$
				@Override
				public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
					try (FileInputStream fileInputStreamReader = new FileInputStream(image)) {
						final byte[] bytes = fileInputStreamReader.readAllBytes();
						final String base64 = Base64.getEncoder().encodeToString(bytes);
						Display.getDefault()
								.asyncExec(() -> editor.insertHTML("<img src= data:image/png;base64," + base64 + ">")); //$NON-NLS-1$ //$NON-NLS-2$
						return Status.OK_STATUS;
					} catch (final IOException e) {
						FordiacLogHelper.logError(e.getMessage(), e);
					}
					return Status.CANCEL_STATUS;
				}
			};

			job.setRule(ResourcesPlugin.getWorkspace().getRoot());
			job.schedule();
		}
	}

	@Override
	public void createPartControl(final Composite parent) {
		GridLayoutFactory.fillDefaults().margins(0, 0).applyTo(parent);

		try {
			editor = new RichTextEditor(parent, createRichTextEditorConfiguration()) {
				// all of this sanitization should be done by the nebula richtext widget internally
				// FIXME: this anonymous class can be deleted when this has been fixed upstream
				private String sanitize(final String text) {
					// browser.evaluate will consume one layer of escaping and uses single quotes for string enclosing
					return text.replace("\\", "\\\\").replace("\'", "\\\'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				}

				private String sanitizeNewline(final String text) {
					// browser.evaluate takes a string, a string literal can not continue across a newline
					return text.replace("\n", "\\n").replace("\r", "\\r"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				}

				private boolean isEditorLoaded() {
					// the value of editorLoaded is not accessible by this class - we just work around that
					boolean editorLoaded = false;
					try {
						var editorLoadedField = RichTextEditor.class.getDeclaredField("editorLoaded"); //$NON-NLS-1$
						editorLoadedField.setAccessible(true);
						editorLoaded = ((Boolean) editorLoadedField.get(this)).booleanValue();
					} catch (Exception e) {
						FordiacLogHelper.logInfo(e.getMessage());
					}
					return editorLoaded;
				}
				
				@Override
				public void setText(final String text) {
					if (isEditorLoaded()) {
				        super.setText(sanitize(text));
					} else {
						super.setText(text);
					}
				}

				@Override
				public void insertText(final String text) {
					// currently unused
					super.insertText(sanitizeNewline(sanitize(text)));
				}

				@Override
				public void insertHTML(final String html) {
					// currently only used to insert images, would also work without sanitization
					super.insertHTML(sanitizeNewline(sanitize(html)));
				}

			};

			GridDataFactory.fillDefaults().grab(true, true).applyTo(editor);
			editor.setText(getFbType().getDocumentation());
			editor.addModifyListener(e -> {
				if (editor != null && editor.getText() != null
						&& !editor.getText().equals(getFbType().getDocumentation())) {
					executeCommand(new ChangeDocumentationCommand(getFbType(), editor.getText()));
				}
			});
			GridDataFactory.fillDefaults().grab(true, true).applyTo(editor);
			editor.setText(getFbType().getDocumentation());
			editor.addModifyListener(e -> {
				if (editor != null && editor.getText() != null
						&& !editor.getText().equals(getFbType().getDocumentation())) {
					executeCommand(new ChangeDocumentationCommand(getFbType(), editor.getText()));
				}
			});
		} catch (final SWTError e) {
			final Label errorLabel = new Label(parent, SWT.NONE);
			errorLabel.setText(e.getMessage());
			GridDataFactory.swtDefaults().applyTo(errorLabel);
		}
	}

	private RichTextEditorConfiguration createRichTextEditorConfiguration() {
		final RichTextEditorConfiguration editorConfig = new RichTextEditorConfiguration();

		editorConfig.setOption("toolbarGroups", TOOLBAR_GROUP_CONFIGURATION); //$NON-NLS-1$
		editorConfig.removeDefaultToolbarButton("Flash", "HorizontalRule", "SpecialChar" + "", "Smiley",  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$//$NON-NLS-5$
				"PageBreak", "Iframe"); //$NON-NLS-1$ //$NON-NLS-2$

		editorConfig.setRemoveFormat(false);

		// FIXME disable custom button on Gtk/WebKit, which causes the UI to hang for 10s
		// until https://bugs.eclipse.org/bugs/show_bug.cgi?id=581144 is fixed
		if (!"gtk".equals(SWT.getPlatform())) { //$NON-NLS-1$
			final InsertConvertedImageButton base64ImageInsert = new InsertConvertedImageButton();
			editorConfig.addToolbarButton(base64ImageInsert);
		}

		return editorConfig;
	}

	private void executeCommand(final Command cmd) {
		if (commandStack != null && cmd != null) {
			commandStack.execute(cmd);
		}
	}

	@Override
	public void setFocus() {
		if (editor != null && !editor.isDisposed()) {
			editor.setFocus();
		}
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		// nothing to be done
	}

	@Override
	public void gotoMarker(final IMarker marker) {
		// For now we don't handle markers in this editor
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		// For now we don't handle markers in this editor
		return false;
	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		return false;
	}

	@Override
	public void setCommonCommandStack(final CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	@Override
	public void reloadType(final FBType type) {
		getEditorInput().setFbType(type);
		if (editor != null && !editor.isDisposed()) {
			editor.setText(getFbType().getDocumentation());
		}
	}

	@Override
	public Object getSelectableEditPart() {
		return null;
	}

}
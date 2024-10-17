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
import org.eclipse.fordiac.ide.model.commands.change.ChangeDocumentationCommand;
import org.eclipse.fordiac.ide.typeeditor.ITypeEditorPage;
import org.eclipse.fordiac.ide.typeeditor.TypeEditorInput;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
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

public class DescriptionEditor extends EditorPart implements ITypeEditorPage {
	// @formatter:off
	private static final String TOOLBAR_GROUP_CONFIGURATION =
				"""
		[\
		{ name: 'clipboard', groups: [ 'undo', 'clipboard'] },\
		{ name: 'colors' },\
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },\
		{ name: 'styles' },\s\
		{ name: 'paragraph', groups: [ 'align', 'list', 'indent' ] },\
		{ name: 'find'},\
		{ name: 'insert' },\
		{ name: 'links' },\
		]"""; 																		//$NON-NLS-1$

	// @formatter:on

	private CommandStack commandStack;
	private RichTextEditor editor;

	@Override
	public TypeEditorInput getEditorInput() {
		return (TypeEditorInput) super.getEditorInput();
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
		setTitleImage(FordiacImage.ICON_DOCUMENTATION_EDITOR.getImage());
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
			editor = new RichTextEditor(parent, createRichTextEditorConfiguration());

			GridDataFactory.fillDefaults().grab(true, true).applyTo(editor);
			editor.setText(getType().getDocumentation());
			editor.addModifyListener(e -> {
				if (editor != null && editor.getText() != null
						&& !editor.getText().equals(getType().getDocumentation())) {
					executeCommand(new ChangeDocumentationCommand(getType(), editor.getText()));
				}
			});
			GridDataFactory.fillDefaults().grab(true, true).applyTo(editor);
			editor.setText(getType().getDocumentation());
			editor.addModifyListener(e -> {
				if (editor != null && editor.getText() != null
						&& !editor.getText().equals(getType().getDocumentation())) {
					executeCommand(new ChangeDocumentationCommand(getType(), editor.getText()));
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
		editorConfig.removeDefaultToolbarButton("Flash", "HorizontalRule", "SpecialChar" + "", "Smiley", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$//$NON-NLS-5$
				"PageBreak", "Iframe"); //$NON-NLS-1$ //$NON-NLS-2$

		editorConfig.setRemoveFormat(false);

		// FIXME disable custom button on Gtk/WebKit, which causes the UI to hang for
		// 10s
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
	public void setInput(final IEditorInput input) {
		super.setInputWithNotify(input);
	}

	@Override
	public void reloadType() {
		if (editor != null && !editor.isDisposed()) {
			editor.setText(getType().getDocumentation());
		}
	}

	@Override
	public Object getSelectableObject() {
		return null;
	}

}
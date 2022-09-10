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
 *   Lukas Wais   - enable image inserting
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.doc.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.fbtypeeditor.editors.IFBTEditorPart;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDocumentationCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.typemanagement.FBTypeEditorInput;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.nebula.widgets.richtext.RichTextEditor;
import org.eclipse.nebula.widgets.richtext.RichTextEditorConfiguration;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class DescriptionEditor extends EditorPart implements IFBTEditorPart {
	// @formatter:off
	private static final String TOOLBAR_GROUP_CONFIGURATION =
			"[" 	 																	//$NON-NLS-1$
			+ "{ name: 'clipboard', groups: [ 'undo', 'clipboard'] },"					//$NON-NLS-1$
			+ "{ name: 'colors' },"  													//$NON-NLS-1$
			+ "{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] }," 			//$NON-NLS-1$
			+ "{ name: 'styles' }," 													//$NON-NLS-1$
			+ "{ name: 'paragraph', groups: [ 'align', 'list', 'indent' ] }," 			//$NON-NLS-1$
			+ "{ name: 'find'}," 														//$NON-NLS-1$
			+ "{ name: 'insert' }," //$NON-NLS-1$
			+ "]"; 																		//$NON-NLS-1$
	// @formatter:on

	private CommandStack commandStack;
	private RichTextEditor editor;
	private boolean blockListeners = false;

	private final Adapter sysConfListener = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			if (!blockListeners && LibraryElementPackage.eINSTANCE.getIdentification_Description()
					.equals(notification.getFeature())) {
				final CommandStack comStackbuf = commandStack;
				commandStack = null;
				if (editor != null && !editor.isDisposed()) {
					editor.setText(getFbType().getIdentification().getDescription());
				}
				commandStack = comStackbuf;
			}
		}
	};

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
		setPartName("Description");
		getFbType().getIdentification().eAdapters().add(sysConfListener);
	}

	@Override
	public void dispose() {
		commandStack = null;
		getFbType().getIdentification().eAdapters().remove(sysConfListener);
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

	@Override
	public void createPartControl(final Composite parent) {
		GridLayoutFactory.fillDefaults().margins(0, 0).applyTo(parent);

		final RichTextEditorConfiguration editorConfig = new RichTextEditorConfiguration();

		editorConfig.setOption("toolbarGroups", TOOLBAR_GROUP_CONFIGURATION); //$NON-NLS-1$
		editorConfig.removeDefaultToolbarButton("Flash", "Table", "HorizontalRule", "SpecialChar" + "", "Smiley", //$NON-NLS-5$ //$NON-NLS-6$
				"PageBreak", "Iframe"); //$NON-NLS-1$ //$NON-NLS-2$
		editor = new RichTextEditor(parent, editorConfig);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(editor);
		editor.setText(getFbType().getDocumentation());
		editor.addModifyListener(e -> {
			if (editor != null && editor.getText() != null
					&& !editor.getText().equals(getFbType().getIdentification().getDescription())) {
				executeCommand(new ChangeDocumentationCommand(getFbType(), editor.getText()));
			}
		});
	}

	private void executeCommand(final Command cmd) {
		if (commandStack != null && cmd != null) {
			blockListeners = true;
			commandStack.execute(cmd);
			blockListeners = false;
		}
	}

	@Override
	public void setFocus() {
		editor.setFocus();
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
		editor.setText(getFbType().getDocumentation());
	}

	@Override
	public Object getSelectableEditPart() {
		return null;
	}

}

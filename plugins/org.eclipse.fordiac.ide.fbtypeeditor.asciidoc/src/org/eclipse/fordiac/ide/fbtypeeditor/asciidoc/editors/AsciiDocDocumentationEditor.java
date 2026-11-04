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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Andrea Zoitl - added toolbar
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.editors;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.handlers.UploadImageHandler;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementProvider;
import org.eclipse.fordiac.ide.typeeditor.ITypeEditorPage;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.mylyn.internal.wikitext.ui.editor.MarkupEditor;
import org.eclipse.mylyn.wikitext.parser.markup.MarkupLanguage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.part.FileEditorInput;

@SuppressWarnings("restriction")
public class AsciiDocDocumentationEditor extends MarkupEditor implements ITypeEditorPage {

	public static final String ASCIIDOC_CONTEXT = "org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.context"; //$NON-NLS-1$

	public static final String TOOLBAR_LOCATION = "toolbar:org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.toolbar"; //$NON-NLS-1$

	// The editor input of the type file given to us.
	private IEditorInput originEditorInput;
	private IMenuService menuService;
	private ToolBarManager toolBarManager;

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		// needs to be done first so that we can get always the type for which we are
		// shown
		originEditorInput = input;
		super.init(site, getDocumentationEditorInput(input));
		setPartName(Messages.AsciiDoc_Description);
		setTitleImage(FordiacImage.ICON_DOCUMENTATION_EDITOR.getImage());

		final IContextService contextService = site.getService(IContextService.class);
		contextService.activateContext(ASCIIDOC_CONTEXT);
	}

	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		setKeyBindingScopes(new String[] { ASCIIDOC_CONTEXT });
	}

	@Override
	public void createPartControl(final Composite parent) {
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(parent);

		final Composite toolbarContainer = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).spacing(0, 0).applyTo(toolbarContainer);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).applyTo(toolbarContainer);

		toolBarManager = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
		final ToolBar toolbarControl = toolBarManager.createControl(toolbarContainer);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).applyTo(toolbarControl);

		menuService = getSite().getService(IMenuService.class);
		if (menuService != null) {
			menuService.populateContributionManager(toolBarManager, TOOLBAR_LOCATION);
		}
		toolBarManager.update(true);

		final Label separator = new Label(toolbarContainer, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).applyTo(separator);

		final Composite newParent = new Composite(parent, SWT.NONE);
		newParent.setLayout(new FillLayout());
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(newParent);

		super.createPartControl(newParent);

		final Browser browser = findBrowser(parent);
		if (browser != null) {
			browser.addLocationListener(new ModelLocationListener());
		}

		final StyledText styledText = findStyledText(newParent);
		if (styledText != null) {
			setupDragAndDrop(styledText);
		}

		// Find the Mylyn tab folder and dynamically toggle toolbar visibility on switch
		final CTabFolder tabFolder = findTabFolder(newParent);
		if (tabFolder != null) {

			final Runnable updateToolbarVisibility = () -> {
				if (toolbarContainer != null && !toolbarContainer.isDisposed()) {
					// Index 0 represents the "Source" tab, Index 1 is the "Preview" tab
					final boolean isSourcePage = tabFolder.getSelectionIndex() == 0;
					toolbarContainer.setVisible(isSourcePage);
					if (toolbarContainer.getLayoutData() instanceof final GridData gridData) {
						gridData.exclude = !isSourcePage;
					}
					// Request parent to adjust bounds immediately to reclaim/release space
					parent.layout(true, true);
				}
			};

			tabFolder.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					updateToolbarVisibility.run();
				}
			});
			updateToolbarVisibility.run();
		}
	}

	// Helper method to traverse the widget tree and find the CTabFolder control
	private CTabFolder findTabFolder(final Control control) {
		if (control instanceof final CTabFolder tabFolder) {
			return tabFolder;
		}
		if (control instanceof final Composite comp) {
			for (final Control child : comp.getChildren()) {
				final CTabFolder tf = findTabFolder(child);
				if (tf != null) {
					return tf;
				}
			}
		}
		return null;
	}

	@Override
	public void dispose() {
		if (toolBarManager != null) {
			if (menuService != null) {
				menuService.releaseContributions(toolBarManager);
			}
			toolBarManager.dispose();
		}
		super.dispose();
	}

	private Browser findBrowser(final Control control) {
		if (control instanceof final Browser browser) {
			return browser;
		}
		if (control instanceof final Composite comp) {
			for (final Control child : comp.getChildren()) {
				final Browser b = findBrowser(child);
				if (b != null) {
					return b;
				}
			}
		}
		return null;
	}

	private static IEditorInput getDocumentationEditorInput(final IEditorInput input) {
		if (input instanceof final IFileEditorInput fileEI) {
			IFolder assetsFolder;
			try {
				assetsFolder = getAssetsFolder(fileEI.getFile());
				final IFile docFile = assetsFolder.getFile("type.adoc"); //$NON-NLS-1$
				if (!docFile.exists()) {
					createNewDocFile(docFile);
				}
				return new FileEditorInput(docFile);
			} catch (final CoreException e) {
				ILog.get().error("Cannot get AsciiDoc file!", e); //$NON-NLS-1$
			}
		}
		return null;
	}

	@Override
	public LibraryElement getType() {
		return LibraryElementProvider.INSTANCE.getLibraryElement(originEditorInput);
	}

	private static IFolder getAssetsFolder(final IFile file) throws CoreException {
		final IContainer parent = file.getParent();

		final String assetFolderName = "." + file.getName() + ".assets"; //$NON-NLS-1$ //$NON-NLS-2$
		final IFolder assetFolder = parent.getFolder(new Path(assetFolderName));
		if (!assetFolder.exists()) {
			assetFolder.create(true, true, new NullProgressMonitor());
		}
		return assetFolder;
	}

	@Override
	public void setMarkupLanguage(final MarkupLanguage markupLanguage, final boolean persistSetting) {
		if (markupLanguage != null) {
			markupLanguage.configure(new FordiacAsciiDocLanguageConfiguration(getType()));
		}
		super.setMarkupLanguage(markupLanguage, persistSetting);
	}

	/**
	 * Traverses the composite tree recursively to retrieve the native editor text
	 * widget.
	 */
	private StyledText findStyledText(final Control control) {
		if (control instanceof final StyledText styledText) {
			return styledText;
		}
		if (control instanceof final Composite comp) {
			for (final Control child : comp.getChildren()) {
				final StyledText styledText = findStyledText(child);
				if (styledText != null) {
					return styledText;
				}
			}
		}
		return null;
	}

	/**
	 * Safely registers File Drag-and-Drop onto the StyledText control. Disposes of
	 * the editor's default DropTarget to completely prevent focus-stealing and
	 * auto-opening of the dropped image file.
	 *
	 * Since Mylyn’s base MarkupEditor already registers a default DropTarget on the
	 * editor's text control, registering a second target would cause a crash during
	 * the editor's creation. To prevent this, setupDragAndDrop() retrieves the
	 * existing target, disposes of it, and configures a clean, custom DropTarget
	 * matching FileTransfer types. This ensures we intercept file drops cleanly
	 * without letting Mylyn trigger its default file-opening procedures.
	 */
	private void setupDragAndDrop(final StyledText styledText) {
		// Retrieve the existing DropTarget registered by Mylyn
		final DropTarget existingTarget = (DropTarget) styledText.getData("DropTarget"); //$NON-NLS-1$

		// Dispose of it to completely disable Mylyn's default file-drop/open
		// behavior
		if (existingTarget != null && !existingTarget.isDisposed()) {
			existingTarget.dispose();
		}

		// Create a brand new, clean DropTarget on the StyledText control
		final int operations = DND.DROP_COPY | DND.DROP_DEFAULT;
		final DropTarget target = new DropTarget(styledText, operations);
		target.setTransfer(FileTransfer.getInstance());
		target.addDropListener(createDropListener(styledText));

		// Update the widget data association
		styledText.setData("DropTarget", target); //$NON-NLS-1$
	}

	/**
	 * Constructs the SWT drop adapter listener.
	 */
	private DropTargetAdapter createDropListener(final StyledText styledText) {
		return new DropTargetAdapter() {
			@Override
			public void dragEnter(final DropTargetEvent event) {
				dragOperationChanged(event);
			}

			@Override
			public void dragOperationChanged(final DropTargetEvent event) {
				if (event.detail == DND.DROP_DEFAULT) {
					event.detail = (event.operations & DND.DROP_COPY) != 0 ? DND.DROP_COPY : DND.DROP_NONE;
				}
			}

			@Override
			public void dragOver(final DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_SCROLL | DND.FEEDBACK_SELECT;
			}

			@Override
			public void drop(final DropTargetEvent event) {
				if (FileTransfer.getInstance().isSupportedType(event.currentDataType)) {
					final String[] files = (String[]) event.data;
					handleFileDrop(styledText, files, event.x, event.y);
				}
			}
		};
	}

	/**
	 * Coordinates parsing the dropped files and triggering the drop operations.
	 */
	private void handleFileDrop(final StyledText styledText, final String[] files, final int x, final int y) {
		if (files == null || files.length == 0) {
			return;
		}

		final File imageFile = findFirstSupportedImage(files);
		if (imageFile != null) {
			updateCursorLocation(styledText, x, y);
			processAndUploadImage(styledText.getShell(), imageFile);
		}
	}

	/**
	 * Inspects dropped file paths and returns the first file matching the
	 * centralized validation criteria.
	 */
	private static File findFirstSupportedImage(final String[] files) {
		for (final String filePath : files) {
			final File file = new File(filePath);
			if (UploadImageHandler.isSupportedImage(file)) { // Clean consolidation of file check logic
				return file;
			}
		}
		return null;
	}

	/**
	 * Moves the carets position inside the editor to the target screen-drop
	 * coordinates.
	 */
	private static void updateCursorLocation(final StyledText styledText, final int x, final int y) {
		try {
			final Point pt = styledText.getDisplay().map(null, styledText, x, y);
			final int offset = styledText.getOffsetAtPoint(pt);
			if (offset != -1) {
				styledText.setSelection(offset, offset);
			}
		} catch (final IllegalArgumentException e) {
			// Quiet fallback if dropping outside character boundaries
		}
	}

	/**
	 * Performs the upload and markup operations via the centralized Handler APIs.
	 */
	private void processAndUploadImage(final Shell shell, final File imageFile) {
		final IContainer assetsFolder = UploadImageHandler.resolveAssetsFolder(this);
		if (assetsFolder != null && assetsFolder.exists()) {
			try {
				final boolean uploadSuccessful = UploadImageHandler.uploadImage(shell, assetsFolder, imageFile);
				if (uploadSuccessful) {
					final String safeFileName = UploadImageHandler.getSafeFileName(imageFile.getName());
					UploadImageHandler.insertImageMarkup(this, safeFileName);
				}
			} catch (final ExecutionException e) {
				ILog.get().error("Failed to process dropped image", e); //$NON-NLS-1$
			}
		}
	}

	@Override
	public void doSave(final IProgressMonitor progressMonitor) {
		// TODO Auto-generated method stub
		super.doSave(progressMonitor);
	}

	@Override
	protected void performSave(final boolean overwrite, final IProgressMonitor progressMonitor) {
		// TODO Auto-generated method stub
		super.performSave(overwrite, progressMonitor);
	}

	private static void createNewDocFile(final IFile docFile) throws CoreException {
		// for now create an empty file, here could be the place to inject a doc
		// template
		final ByteArrayInputStream emptyStream = new ByteArrayInputStream(new byte[0]);
		docFile.create(emptyStream, true, null);
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reloadType() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getSelectableObject() {
		// TODO Auto-generated method stub
		return null;
	}

}

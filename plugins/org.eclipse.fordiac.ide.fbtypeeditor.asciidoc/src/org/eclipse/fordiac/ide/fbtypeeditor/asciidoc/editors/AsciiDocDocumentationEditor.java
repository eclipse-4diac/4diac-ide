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
 *   Andrea Zoitl - added toolbar and changed editor to side-by-side layout
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.editors;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicLong;

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
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.handlers.UploadImageHandler;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.ui.editors.LibraryElementProvider;
import org.eclipse.fordiac.ide.typeeditor.ITypeEditorPage;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.mylyn.internal.wikitext.ui.editor.MarkupEditor;
import org.eclipse.mylyn.wikitext.asciidoc.AsciiDocLanguage;
import org.eclipse.mylyn.wikitext.html.HtmlLanguage;
import org.eclipse.mylyn.wikitext.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.parser.markup.MarkupLanguage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.plugin.AbstractUIPlugin;

@SuppressWarnings("restriction")
public class AsciiDocDocumentationEditor extends MarkupEditor implements ITypeEditorPage {

	public static final String ASCIIDOC_CONTEXT = "org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.context"; //$NON-NLS-1$
	public static final String TOOLBAR_LOCATION = "toolbar:org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.toolbar"; //$NON-NLS-1$
	private static final int PREVIEW_DELAY_MS = 300;

	private final AtomicLong renderGeneration = new AtomicLong();
	private Runnable pendingRefresh; // debounce handle for timerExec
	private Job previewJob; // background parse job
	private AsciiDocLanguage previewLanguage; // cached — expensive to construct
	private String cachedBaseUri; // raw directory URI, computed once
	private String lastHtml; // skip identical browser updates
	private boolean skeletonLoaded; // true after first full setText()

	// The editor input of the type file given to us.
	private IEditorInput originEditorInput;
	private IMenuService menuService;
	private ToolBarManager formattingToolBarManager;

	// Layout composites and widgets
	private Composite headerComposite;
	private Composite formattingToolbarComposite;
	private GridData formattingGridData;
	private SashForm sashForm;
	private Composite editorParent;
	private Composite previewParent;
	private Browser liveBrowser;

	// Layout Mode Switch Items
	private ToolItem editOnlyModeItem;
	private ToolItem splitModeItem;
	private ToolItem previewOnlyModeItem;

	// Allocated SWT Images (Must be manually disposed to prevent native OS handle
	// leaks)
	private Image editOnlyImage;
	private Image splitImage;
	private Image previewOnlyImage;

	// Document Listener tracking (Must be unregistered to prevent memory leaks)
	private IDocument currentDocument;
	private IDocumentListener documentListener;
	private boolean settingText; // true while our own setText() load is in flight

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

		// 1. Initialize top formatting and layout mode controls
		createHeader(parent);

		// 2. Setup the Side-by-Side split view
		createSashFormLayout(parent);

		// 3. Setup dynamic resizing & visibility behaviors
		bindFormattingToolbarWidth();
		setupLayoutModeListeners();

		// 4. Hook up drag-and-drop and live updates
		setupDragAndDropSupport();
		setupLivePreviewSupport();

		// 5. Apply the multi-page framework tab height workaround
		hideObsoleteTabs();
	}

	/**
	 * Setup Top Horizontal Toolbar Container (2 Columns: Formatting on left,
	 * display modes on right)
	 *
	 * @param parent
	 */
	private void createHeader(final Composite parent) {

		editOnlyImage = loadImage("icons/display_source.svg"); //$NON-NLS-1$
		splitImage = loadImage("icons/display_source_preview.svg"); //$NON-NLS-1$
		previewOnlyImage = loadImage("icons/display_preview.svg"); //$NON-NLS-1$

		headerComposite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).spacing(0, 0).applyTo(headerComposite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).applyTo(headerComposite);

		// Left Column: Holder for the Formatting Toolbar (Dynamically sized to match
		// editor)
		formattingToolbarComposite = new Composite(headerComposite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).spacing(0, 0).applyTo(formattingToolbarComposite);
		formattingGridData = GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).create();
		formattingToolbarComposite.setLayoutData(formattingGridData);

		formattingToolBarManager = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
		final ToolBar toolbarControl = formattingToolBarManager.createControl(formattingToolbarComposite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(toolbarControl);

		menuService = getSite().getService(IMenuService.class);
		if (menuService != null) {
			menuService.populateContributionManager(formattingToolBarManager, TOOLBAR_LOCATION);
		}
		formattingToolBarManager.update(true);

		// Right Column: Layout Mode Toggle switches
		final Composite displayModeComposite = new Composite(headerComposite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).spacing(0, 0).applyTo(displayModeComposite);
		GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER).grab(false, false).applyTo(displayModeComposite);

		final ToolBar displayModeToolBar = new ToolBar(displayModeComposite, SWT.FLAT | SWT.HORIZONTAL | SWT.RIGHT);
		GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER).grab(true, false).applyTo(displayModeToolBar);

		editOnlyModeItem = new ToolItem(displayModeToolBar, SWT.RADIO);
		if (editOnlyImage != null) {
			editOnlyModeItem.setImage(editOnlyImage);
		} else {
			editOnlyModeItem.setText("Edit Only"); //$NON-NLS-1$
		}
		editOnlyModeItem.setToolTipText(Messages.Display_EditOnly_Tooltip);

		splitModeItem = new ToolItem(displayModeToolBar, SWT.RADIO);
		if (splitImage != null) {
			splitModeItem.setImage(splitImage);
		} else {
			splitModeItem.setText("Side-by-Side"); //$NON-NLS-1$
		}
		splitModeItem.setToolTipText(Messages.Display_SideBySide_Tooltip);
		splitModeItem.setSelection(true); // Default mode

		previewOnlyModeItem = new ToolItem(displayModeToolBar, SWT.RADIO);
		if (previewOnlyImage != null) {
			previewOnlyModeItem.setImage(previewOnlyImage);
		} else {
			previewOnlyModeItem.setText("Preview Only"); //$NON-NLS-1$
		}
		previewOnlyModeItem.setToolTipText(Messages.Display_PreviewOnly_Tooltip);

		// Divider Line spanning both columns beneath the header
		final Label separator = new Label(headerComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).grab(true, false).span(2, 1).applyTo(separator);
	}

	private void createSashFormLayout(final Composite parent) {
		sashForm = new SashForm(parent, SWT.HORIZONTAL | SWT.SMOOTH);
		sashForm.setSashWidth(3); // vertical visible divider
		sashForm.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(sashForm);

		// Left Pane: The Editor Canvas
		editorParent = new Composite(sashForm, SWT.NONE);
		editorParent.setLayout(new FillLayout());
		super.createPartControl(editorParent);

		// Right Pane: Rendered live browser view
		previewParent = new Composite(sashForm, SWT.NONE);
		previewParent.setLayout(new FillLayout());
		liveBrowser = new Browser(previewParent, SWT.NONE);
		liveBrowser.addLocationListener(new ModelLocationListener());
		liveBrowser.addLocationListener(createPreviewLocationListener());
		liveBrowser.addProgressListener(ProgressListener.completedAdapter(e -> {
			if (settingText) { // only our own setText() marks the skeleton as ready
				settingText = false;
				skeletonLoaded = true;
			}
		}));

		sashForm.setWeights(50, 50);
	}

	/**
	 * Bind left toolbar column width dynamically to the editor's pixel width
	 */
	private void bindFormattingToolbarWidth() {
		editorParent.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(final ControlEvent e) {
				if (!editorParent.isDisposed() && !headerComposite.isDisposed()) {
					final int width = editorParent.getSize().x;
					final int newHint;
					final boolean newVisible;

					if (sashForm.getMaximizedControl() == editorParent) {
						// Edit Only: let formatting toolbar take standard layout space
						newHint = SWT.DEFAULT;
						newVisible = true;
					} else if (width <= 0) {
						// Preview Only: Collapse the width hint to 0, but do not exclude to pin the 3
						// buttons (source, both, preview) to the right.
						newHint = 0;
						newVisible = false;
					} else {
						// Side-by-Side: Align formatting toolbar perfectly with the editor's width
						newHint = width;
						newVisible = true;
					}
					if (formattingGridData.widthHint != newHint
							|| formattingToolbarComposite.getVisible() != newVisible) {
						formattingGridData.widthHint = newHint;
						formattingToolbarComposite.setVisible(newVisible);
						formattingGridData.exclude = false;
						headerComposite.layout(true);
					}
				}
			}
		});
	}

	private void setupLayoutModeListeners() {
		final SelectionAdapter layoutSelector = new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				// Discard the deselection event from the deactivated radio item
				if (e.widget instanceof final ToolItem item && !item.getSelection()) {
					return;
				}
				if (editOnlyModeItem.getSelection()) {
					sashForm.setMaximizedControl(editorParent);
				} else if (previewOnlyModeItem.getSelection()) {
					sashForm.setMaximizedControl(previewParent);
					triggerPreviewRefresh();
				} else if (splitModeItem.getSelection()) {
					sashForm.setMaximizedControl(null); // Restore Side-by-Side Split
					triggerPreviewRefresh();
				}
			}
		};
		editOnlyModeItem.addSelectionListener(layoutSelector);
		splitModeItem.addSelectionListener(layoutSelector);
		previewOnlyModeItem.addSelectionListener(layoutSelector);
	}

	private void setupDragAndDropSupport() {
		final StyledText styledText = findStyledText(editorParent);
		if (styledText != null) {
			setupDragAndDrop(styledText);
		}
	}

	/**
	 * Register IDocumentListener for instantaneous text and formatting updates
	 */
	private void setupLivePreviewSupport() {
		final IDocument document = getDocumentProvider().getDocument(getEditorInput());
		if (document != null) {
			currentDocument = document; // Retained for unregistering in dispose()
			schedulePreviewRefresh(document.get()); // initial render

			documentListener = new IDocumentListener() {
				@Override
				public void documentAboutToBeChanged(final DocumentEvent event) {
					// Do nothing
				}

				@Override
				public void documentChanged(final DocumentEvent event) {
					if (liveBrowser == null || liveBrowser.isDisposed()) {
						return;
					}
					// debounce — coalesce bursts of keystrokes into one refresh
					final Display display = Display.getCurrent();
					if (display == null) {
						return;
					}
					if (pendingRefresh != null) {
						display.timerExec(-1, pendingRefresh); // cancel previous schedule
					}
					pendingRefresh = () -> {
						pendingRefresh = null;
						if (liveBrowser != null && !liveBrowser.isDisposed()
								&& sashForm.getMaximizedControl() != editorParent) {
							schedulePreviewRefresh(event.getDocument().get());
						}
					};
					display.timerExec(PREVIEW_DELAY_MS, pendingRefresh);
				}
			};
			document.addDocumentListener(documentListener);
		}
	}

	/**
	 * Programmatically hide obsolete Source/Preview tabs to preserve Eclipse
	 * multi-page framework. Its core functionality—such as saving changes, tracking
	 * the "dirty" state, managing undo/redo stacks, and syncing selections—is
	 * tightly coupled to the existence of these two tab pages (page 0 for editing,
	 * page 1 for the preview). We altered the editor's visual presentation from the
	 * 2 tabs to a side-by-side layout but we do not want to loose the complex
	 * underlying infrastructure of the parent framework, so we set the height to 0.
	 */
	private void hideObsoleteTabs() {

		final CTabFolder tabFolder = findTabFolder(editorParent);
		if (tabFolder != null) {
			tabFolder.setSelection(0);
			tabFolder.setTabHeight(0);
		}
	}

	@Override
	public void setFocus() {
		if (sashForm != null && !sashForm.isDisposed() && sashForm.getMaximizedControl() == previewParent
				&& liveBrowser != null && !liveBrowser.isDisposed()) {
			liveBrowser.setFocus();
		} else {
			super.setFocus();
		}
		// The type may have been modified on another editor page (e.g. its comment,
		// rendered via fbt:THIS[comment]). Ordinary model edits do not trigger
		// reloadType(), so re-render whenever this page becomes active.
		refreshPreviewOnActivation();
	}

	private void refreshPreviewOnActivation() {
		if (liveBrowser == null || liveBrowser.isDisposed() || sashForm == null
				|| sashForm.getMaximizedControl() == editorParent) {
			return; // no visible preview to refresh
		}
		synchronized (this) {
			// Drop the cached language so it is re-configured with the current
			// state of the type (fbt:THIS[...] attributes may have changed).
			previewLanguage = null;
		}
		triggerPreviewRefresh();
	}

	/**
	 * Re-renders from the current document, used on mode switches.
	 */
	private void triggerPreviewRefresh() {
		final IDocument document = getDocumentProvider().getDocument(getEditorInput());
		if (document != null) {
			schedulePreviewRefresh(document.get());
		}
	}

	/**
	 * Parses in a background Job; only the browser update touches SWT.
	 */
	private void schedulePreviewRefresh(final String text) {
		final long gen = renderGeneration.incrementAndGet();

		if (previewJob != null) {
			previewJob.cancel();
		}

		previewJob = Job.create("Render AsciiDoc preview", monitor -> { //$NON-NLS-1$
			final AsciiDocLanguage language;
			final String baseTag;
			// Acquire the lock briefly to capture a snapshot of the required
			// configuration
			synchronized (this) {
				if (monitor.isCanceled() || gen != renderGeneration.get()) {
					return Status.CANCEL_STATUS;
				}
				language = getPreviewLanguage(); // Thread-safe retrieval under lock
				baseTag = getBaseTag(); // Thread-safe retrieval under lock
			}

			// Perform the expensive markup parsing outside the synchronized block
			final String html = renderHtml(text, language, baseTag);

			if (monitor.isCanceled() || gen != renderGeneration.get()) {
				return Status.CANCEL_STATUS;
			}
			Display.getDefault().asyncExec(() -> {
				if (gen == renderGeneration.get()) {
					updateBrowser(html);
				}
			});
			return Status.OK_STATUS;
		});
		previewJob.setSystem(true);
		previewJob.schedule();
	}

	/**
	 * Parse-only, thread-safe, uses cached language and base tag.
	 */
	private synchronized String renderHtml(final String text, final AsciiDocLanguage language, final String baseTag) {
		try {
			final StringWriter writer = new StringWriter();
			final MarkupParser parser = new MarkupParser(language);
			parser.setBuilder(new HtmlLanguage().createDocumentBuilder(writer));
			parser.parse(text);

			String htmlContent = writer.toString();
			if (baseTag != null) {
				final String head = "<head>"; //$NON-NLS-1$
				htmlContent = htmlContent.contains(head) ? htmlContent.replace(head, head + baseTag)
						: baseTag + htmlContent;
			}
			return htmlContent;
		} catch (final Exception e) {
			ILog.get().error("Error rendering AsciiDoc preview", e); //$NON-NLS-1$
			return "<html><head>" + (baseTag != null ? baseTag : "") + "</head><body><p>" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ "Error rendering preview: " + e.getMessage() + "</p></body></html>"; //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Cached language — construction compiles regex sets, do it once.
	 */
	private synchronized AsciiDocLanguage getPreviewLanguage() {
		if (previewLanguage == null) {
			previewLanguage = new AsciiDocLanguage();
			previewLanguage.configure(new FordiacAsciiDocLanguageConfiguration(getType()));
		}
		return previewLanguage;
	}

	private synchronized String getBaseUri() {
		if (cachedBaseUri == null && getEditorInput() instanceof final IFileEditorInput fileEI) {
			final IFile docFile = fileEI.getFile();
			if (docFile.getLocation() != null) {
				cachedBaseUri = docFile.getParent().getLocation().toFile().toURI().toString();
			}
		}
		return cachedBaseUri;
	}

	/**
	 * Formats the base href tag from the cached assets directory URI.
	 */
	private synchronized String getBaseTag() {
		final String baseUri = getBaseUri();
		return (baseUri != null) ? "<base href=\"" + baseUri + "\"/>" : null; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Skip identical output; patch the DOM instead of full reloads after the
	 * initial skeleton page has been loaded, preserving scroll position.
	 */
	private void updateBrowser(final String html) {
		if (liveBrowser == null || liveBrowser.isDisposed() || html.equals(lastHtml)) {
			return;
		}
		lastHtml = html;

		// Base64 transport: the payload cannot contain any JS-breaking characters, so
		// no manual escaping is required at all.
		final String b64 = java.util.Base64.getEncoder()
				.encodeToString(extractBody(html).getBytes(java.nio.charset.StandardCharsets.UTF_8));
		final String script = "var y=window.scrollY;" //$NON-NLS-1$
				+ "document.body.innerHTML=new TextDecoder().decode(" //$NON-NLS-1$
				+ "Uint8Array.from(atob('" + b64 + "'),function(c){return c.charCodeAt(0)}));" //$NON-NLS-1$ //$NON-NLS-2$
				+ "window.scrollTo(0,y);"; //$NON-NLS-1$

		// Fall back to a full reload if the skeleton isn't ready or the JS fails
		if (!skeletonLoaded || !liveBrowser.execute(script)) {
			skeletonLoaded = false; // flipped back to true by the ProgressListener for OUR load
			settingText = true;
			liveBrowser.setText(html);
		}
	}

	/** Returns only the content between <body> and </body>. */
	private static String extractBody(final String html) {
		final int start = html.indexOf("<body"); //$NON-NLS-1$
		final int open = (start >= 0) ? html.indexOf('>', start) : -1;
		final int end = html.lastIndexOf("</body>"); //$NON-NLS-1$
		return (open >= 0 && end > open) ? html.substring(open + 1, end) : html;
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
		if (pendingRefresh != null && liveBrowser != null && !liveBrowser.isDisposed()) {
			liveBrowser.getDisplay().timerExec(-1, pendingRefresh);
			pendingRefresh = null;
		}
		if (previewJob != null) {
			previewJob.cancel();
			previewJob = null;
		}

		// Unregister the document listener to prevent memory leaks
		if (currentDocument != null && documentListener != null) {
			currentDocument.removeDocumentListener(documentListener);
			documentListener = null;
			currentDocument = null;
		}

		// Dispose of custom loaded SWT Images to free native OS resources
		if (editOnlyImage != null && !editOnlyImage.isDisposed()) {
			editOnlyImage.dispose();
		}
		if (splitImage != null && !splitImage.isDisposed()) {
			splitImage.dispose();
		}
		if (previewOnlyImage != null && !previewOnlyImage.isDisposed()) {
			previewOnlyImage.dispose();
		}

		// Dispose of standard components
		if (formattingToolBarManager != null) {
			if (menuService != null) {
				menuService.releaseContributions(formattingToolBarManager);
			}
			formattingToolBarManager.dispose();
		}
		super.dispose();
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
		super.doSave(progressMonitor);
	}

	@Override
	protected void performSave(final boolean overwrite, final IProgressMonitor progressMonitor) {
		super.performSave(overwrite, progressMonitor);
	}

	private static void createNewDocFile(final IFile docFile) throws CoreException {
		// for now create an empty file, here could be the place to inject a doc
		// template
		final ByteArrayInputStream emptyStream = new ByteArrayInputStream(new byte[0]);
		docFile.create(emptyStream, true, null);
	}

	/**
	 * Helper method to load images cleanly from the plugin directory.
	 */
	private static Image loadImage(final String path) {
		final var descriptor = AbstractUIPlugin
				.imageDescriptorFromPlugin("org.eclipse.fordiac.ide.fbtypeeditor.asciidoc", path); //$NON-NLS-1$
		return (descriptor != null) ? descriptor.createImage() : null;
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
		// drop cached language so it is re-configured with the fresh type
		synchronized (this) {
			previewLanguage = null;
			lastHtml = null; // force a repaint even if HTML happens to match
		}
		triggerPreviewRefresh();

	}

	@Override
	public Object getSelectableObject() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the fragment if the location is a fragment-only link that resolved
	 * against the injected base URI, otherwise null.
	 */
	private String getLocalFragment(final String location) {
		final int hashIdx = location.indexOf('#');
		if (hashIdx < 0) {
			return null;
		}
		final String target = location.substring(0, hashIdx);
		final String base = getBaseUri();
		if (target.isEmpty() || "about:blank".equals(target) //$NON-NLS-1$
				|| (base != null && (target.equals(base) || (target + '/').equals(base)))) {
			return location.substring(hashIdx + 1);
		}
		return null;
	}

	private void scrollToFragment(final String fragment) {
		if (liveBrowser == null || liveBrowser.isDisposed()) {
			return;
		}
		final String id = fragment.replace("\\", "\\\\").replace("'", "\\'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		liveBrowser.execute("var id='" + id + "';try{id=decodeURIComponent(id);}catch(e){}" //$NON-NLS-1$ //$NON-NLS-2$
				+ "var t=document.getElementById(id)||document.getElementsByName(id)[0];" //$NON-NLS-1$
				+ "if(t){t.scrollIntoView();}"); //$NON-NLS-1$
	}

	private LocationListener createPreviewLocationListener() {
		return LocationListener.changingAdapter(event -> {
			if (!event.top) {
				return; // only top-level navigation replaces the preview document
			}
			final String location = event.location;
			if (location == null || "about:blank".equals(location) //$NON-NLS-1$
					|| location.startsWith("fbt-entry://")) { //$NON-NLS-1$
				return; // own setText() load, or handled by ModelLocationListener
			}
			// Keep fragment navigation inside the preview document
			final String fragment = getLocalFragment(location);
			if (fragment != null) {
				event.doit = false;
				scrollToFragment(fragment);
				return;
			}
			// Open ordinary hyperlinks outside the preview
			if (isExternalLink(location)) {
				event.doit = false;
				Program.launch(location);
				return;
			}
			// Anything else replaces the preview document — invalidate
			// the cached state so the next refresh performs a full reload instead
			// of patching a foreign page's body.
			invalidatePreviewDocumentState();
		});
	}

	private static boolean isExternalLink(final String location) {
		final String lower = location.toLowerCase(java.util.Locale.ROOT);
		return lower.startsWith("http://") || lower.startsWith("https://") //$NON-NLS-1$ //$NON-NLS-2$
				|| lower.startsWith("mailto:"); //$NON-NLS-1$
	}

	private void invalidatePreviewDocumentState() {
		synchronized (this) {
			lastHtml = null;
		}
		skeletonLoaded = false;
		settingText = false;
	}

}

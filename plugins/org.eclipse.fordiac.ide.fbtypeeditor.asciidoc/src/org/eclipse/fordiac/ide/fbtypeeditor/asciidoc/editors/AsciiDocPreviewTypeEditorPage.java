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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.editors;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.typeeditor.ITypeEditorPage;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.mylyn.wikitext.asciidoc.AsciiDocLanguage;
import org.eclipse.mylyn.wikitext.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.parser.builder.HtmlDocumentBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

public class AsciiDocPreviewTypeEditorPage extends EditorPart implements ITypeEditorPage {

	private Browser browser;

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(getDocumentationEditorInput(input));
		setPartName("AsciiDoc Description");
		setTitleImage(FordiacImage.ICON_DOCUMENTATION_EDITOR.getImage());
	}

	@Override
	public void createPartControl(final Composite parent) {
		browser = new Browser(parent, SWT.NONE);
		refreshHtml();
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		// nothing to be selected
	}

	@Override
	public void setInput(final IEditorInput input) {
		super.setInput(input);
	}

	@Override
	public void gotoMarker(final IMarker marker) {
		// we have no marker targets in the viewer
	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		return false;
	}

	@Override
	public void reloadType() {
	}

	@Override
	public Object getSelectableObject() {
		return null; // nothing can be selected
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		// no saving possible in viewer
	}

	@Override
	public void doSaveAs() {
		// no saving possible in viewer
	}

	@Override
	public boolean isDirty() {
		return false; // the viewer can never be dirty
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {
		if (browser != null && !browser.isDisposed()) {
			browser.setFocus();
		}
	}

	private static IEditorInput getDocumentationEditorInput(final IEditorInput input) {
		if (!(input instanceof final IFileEditorInput fileEI)) {
			return null;
		}
		final IFolder assetsFolder = getAssetsFolder(fileEI.getFile());
		final IFile docFile = assetsFolder.getFile("type.adoc"); //$NON-NLS-1$
		return new FileEditorInput(docFile);
	}

	private static IFolder getAssetsFolder(final IFile file) {
		final IContainer parent = file.getParent();
		final String assetFolderName = "." + file.getName() + ".assets"; //$NON-NLS-1$ //$NON-NLS-2$
		return parent.getFolder(new Path(assetFolderName));
	}

	private void refreshHtml() {
		if (browser == null || browser.isDisposed()) {
			return;
		}

		if (!(getEditorInput() instanceof final IFileEditorInput fileEI) || !fileEI.getFile().exists()) {
			browser.setText(
					"<html><body><p>Error rendering preview: no documentation file available!</p></body></html>");
			return;
		}

		try (InputStream is = fileEI.getFile().getContents(true);
				BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

			final StringWriter writer = new StringWriter();
			final HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);

			final AsciiDocLanguage markupLanguage = new AsciiDocLanguage();
			markupLanguage.setEnableMacros(true);
			markupLanguage.setFilterGenerativeContents(false);
			markupLanguage.setBlocksOnly(false);
			final MarkupParser parser = new MarkupParser(markupLanguage);
			parser.setBuilder(builder);
			parser.parse(reader);

			browser.setText(writer.toString());
		} catch (final Exception e) {
			browser.setText("<html><body><p>Error rendering preview: " + e.getMessage() + "</p></body></html>");
		}
	}

}

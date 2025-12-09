/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.contentoutline;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class MultiPageEditorContentOutlinePage extends Page
		implements IContentOutlinePage, IPostSelectionProvider, IAdaptable {

	protected static record PageRecord(IEditorPart editorPart, IContentOutlinePage page, IPageSite site) {
		protected void dispose() {
			page.dispose();
		}
	}

	private final MultiPageEditorPart editorPart;
	private final IContentOutlinePage defaultPage;
	private final CopyOnWriteArrayList<ISelectionChangedListener> selectionChangedListeners = new CopyOnWriteArrayList<>();
	private final CopyOnWriteArrayList<ISelectionChangedListener> postSelectionChangedListeners = new CopyOnWriteArrayList<>();
	private final Map<IEditorPart, PageRecord> pages = new HashMap<>();
	private final Set<MultiPageEditorPart> multiPageChildren = new HashSet<>();

	private final ISelectionChangedListener selectionChangedListener = this::selectionChanged;
	private final ISelectionChangedListener postSelectionChangedListener = this::postSelectionChanged;
	private final IPageChangedListener pageChangedListener = this::pageChanged;

	private PageBook book;
	private PageRecord currentPage;

	public MultiPageEditorContentOutlinePage(final MultiPageEditorPart editorPart,
			final IContentOutlinePage defaultPage) {
		this.editorPart = editorPart;
		this.defaultPage = defaultPage;
	}

	@Override
	public void createControl(final Composite parent) {
		book = new PageBook(parent, SWT.NONE);
		if (editorPart.getSelectedPage() instanceof final IEditorPart activeEditor) {
			showPage(getOrCreatePage(activeEditor));
		} else {
			showPage(getOrCreatePage(editorPart));
		}
		editorPart.addPageChangedListener(pageChangedListener);
	}

	private PageRecord getOrCreatePage(final IEditorPart editorPart) {
		return pages.computeIfAbsent(editorPart, this::createPage);
	}

	private PageRecord createPage(final IEditorPart editorPart) {
		if (editorPart != this.editorPart) {
			final IContentOutlinePage page = Adapters.adapt(editorPart, IContentOutlinePage.class);
			if (page != null) {
				return createPage(editorPart, page);
			}
		}
		return createPage(editorPart, defaultPage);
	}

	private PageRecord createPage(final IEditorPart editorPart, final IContentOutlinePage page) {
		IPageSite site;
		if (page instanceof final IPageBookViewPage pageBookViewPage) {
			if (pageBookViewPage.getSite() == null) {
				site = new MultiPageSite(getSite());
				SafeRunner.run(() -> pageBookViewPage.init(site));
			} else {
				site = pageBookViewPage.getSite();
			}
		} else {
			site = new MultiPageSite(getSite());
		}
		if (page.getControl() == null) {
			page.createControl(book);
			page.setActionBars(site.getActionBars());
		}
		return new PageRecord(editorPart, page, site);
	}

	protected void showPage(final PageRecord page) {
		if (page == currentPage) {
			return;
		}
		if (currentPage != null && page != null && currentPage.page() == page.page()) {
			currentPage = page;
			return;
		}
		if (currentPage != null) {
			if (currentPage.site() instanceof final MultiPageSite multiPageSite) {
				multiPageSite.getActionBars().deactivate();
				multiPageSite.deactivate();
			}
			currentPage.page().removeSelectionChangedListener(selectionChangedListener);
			if (currentPage.page() instanceof final IPostSelectionProvider postSelectionProvider) {
				postSelectionProvider.removePostSelectionChangedListener(postSelectionChangedListener);
			}
		}
		currentPage = page;
		if (currentPage != null && !currentPage.page().getControl().isDisposed()) {
			book.showPage(currentPage.page().getControl());
			if (currentPage.site() instanceof final MultiPageSite multiPageSite) {
				multiPageSite.getActionBars().activate();
				multiPageSite.activate();
			}
			currentPage.page().addSelectionChangedListener(selectionChangedListener);
			if (currentPage.page() instanceof final IPostSelectionProvider postSelectionProvider) {
				postSelectionProvider.addPostSelectionChangedListener(postSelectionChangedListener);
			}
			if (getSite().getActionBars() instanceof final SubActionBars parentSubActionBars) {
				parentSubActionBars.activate(); // make sure parent is activated
			}
			getSite().getActionBars().updateActionBars();
		}
		fireSelectionChanged(new SelectionChangedEvent(this, getSelection()));
		firePostSelectionChanged(new SelectionChangedEvent(this, getSelection()));
	}

	protected void pageChanged(final PageChangedEvent event) {
		if ((event.getSelectedPage() instanceof final MultiPageEditorPart multiPageEditor)
				&& multiPageChildren.add(multiPageEditor)) {
			// register to sub pages that are themselves again multipage editors e.g.,
			// breadcrumb editor in typed subapp
			multiPageEditor.addPageChangedListener(pageChangedListener);
		}

		if (event.getSelectedPage() instanceof final IEditorPart activeEditor) {
			showPage(getOrCreatePage(activeEditor));
		} else {
			showPage(getOrCreatePage(editorPart));
		}
	}

	@Override
	public ISelection getSelection() {
		if (currentPage != null) {
			final ISelection selection = currentPage.page().getSelection();
			if (selection != null) {
				return selection;
			}
		}
		return StructuredSelection.EMPTY;
	}

	@Override
	public void setSelection(final ISelection selection) {
		if (currentPage != null) {
			currentPage.page().setSelection(selection);
		}
	}

	@Override
	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	@Override
	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}

	@Override
	public void addPostSelectionChangedListener(final ISelectionChangedListener listener) {
		postSelectionChangedListeners.add(listener);
	}

	@Override
	public void removePostSelectionChangedListener(final ISelectionChangedListener listener) {
		postSelectionChangedListeners.remove(listener);
	}

	protected void selectionChanged(final SelectionChangedEvent event) {
		fireSelectionChanged(new SelectionChangedEvent(this, event.getSelection()));
	}

	protected void postSelectionChanged(final SelectionChangedEvent event) {
		firePostSelectionChanged(new SelectionChangedEvent(this, event.getSelection()));
	}

	protected void fireSelectionChanged(final SelectionChangedEvent event) {
		selectionChangedListeners.forEach(listener -> SafeRunner.run(() -> listener.selectionChanged(event)));
	}

	protected void firePostSelectionChanged(final SelectionChangedEvent event) {
		postSelectionChangedListeners.forEach(listener -> SafeRunner.run(() -> listener.selectionChanged(event)));
	}

	@Override
	public Control getControl() {
		return book;
	}

	@Override
	public void setFocus() {
		if (book != null && !book.isDisposed()) {
			book.setFocus();
		}
		if (currentPage != null) {
			currentPage.page().setFocus();
		}
	}

	@Override
	public <T> T getAdapter(final Class<T> key) {
		if (currentPage != null) {
			return Adapters.adapt(currentPage.page(), key);
		}
		return null;
	}

	@Override
	public void dispose() {
		editorPart.removePageChangedListener(pageChangedListener);
		multiPageChildren.forEach(child -> child.removePageChangedListener(pageChangedListener));
		showPage(null);
		pages.values().forEach(PageRecord::dispose);
		if (book != null) {
			book.dispose();
		}
		super.dispose();
	}
}

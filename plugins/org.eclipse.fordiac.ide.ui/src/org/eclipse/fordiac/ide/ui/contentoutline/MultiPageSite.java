/*******************************************************************************
 * Copyright (c) 2000, 2024 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Lars Vogel <Lars.Vogel@gmail.com> - Bug 440810
 *     Martin Erich Jobst - adapted from PageSite
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.contentoutline;

import java.util.ArrayList;
import java.util.HashSet;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.internal.PopupMenuExtender;
import org.eclipse.ui.internal.contexts.NestableContextService;
import org.eclipse.ui.internal.expressions.ActivePartExpression;
import org.eclipse.ui.internal.handlers.LegacyHandlerService;
import org.eclipse.ui.internal.part.IPageSiteHolder;
import org.eclipse.ui.internal.services.INestable;
import org.eclipse.ui.internal.services.IServiceLocatorCreator;
import org.eclipse.ui.internal.services.IWorkbenchLocationService;
import org.eclipse.ui.internal.services.ServiceLocator;
import org.eclipse.ui.internal.services.WorkbenchLocationService;
import org.eclipse.ui.part.IPageSite;

/**
 * This implementation of <code>IPageSite</code> provides a site for a page
 * within a <code>PageBookView</code>. Most methods are forwarded to the view's
 * site.
 */
@SuppressWarnings("restriction")
public class MultiPageSite implements IPageSite, INestable {

	/**
	 * The list of menu extender for each registered menu.
	 */
	private ArrayList<PopupMenuExtender> menuExtenders;

	/**
	 * The "parent" part site
	 */
	private final IWorkbenchPartSite partSite;

	/**
	 * The "parent" page site
	 */
	private final IPageSite parentSite;

	/**
	 * A selection provider set by the page. Value is <code>null</code> until set.
	 */
	private ISelectionProvider selectionProvider;

	/**
	 * The localized service locator for this page site. This locator is never
	 * <code>null</code>.
	 */
	private final ServiceLocator serviceLocator;

	/**
	 * The action bars for this site
	 */
	private final SubActionBars subActionBars;

	private final IEclipseContext e4Context;

	private NestableContextService contextService;

	private boolean active = false;

	/**
	 * Creates a new sub page site of the given parent page site.
	 *
	 * @param parentPageSite the parent page site
	 */
	public MultiPageSite(final IPageSite parentPageSite) {
		Assert.isNotNull(parentPageSite);
		parentSite = parentPageSite;
		partSite = parentSite.getService(IWorkbenchLocationService.class).getPartSite();
		subActionBars = new SubActionBars(parentPageSite.getActionBars(), this);

		// Initialize the service locator.
		final IServiceLocatorCreator slc = parentSite.getService(IServiceLocatorCreator.class);
		e4Context = parentPageSite.getService(IEclipseContext.class).createChild("PageSite"); //$NON-NLS-1$
		this.serviceLocator = (ServiceLocator) slc.createServiceLocator(parentPageSite, null, () -> {
			// do nothing
		}, e4Context);
		initializeDefaultServices();
	}

	/**
	 * Initialize the slave services for this site.
	 */
	private void initializeDefaultServices() {
		serviceLocator.registerService(IWorkbenchLocationService.class,
				new WorkbenchLocationService(getClass().getName(), getWorkbenchWindow().getWorkbench(),
						getWorkbenchWindow(), partSite, null, parentSite, 4));
		serviceLocator.registerService(IPageSiteHolder.class, (IPageSiteHolder) () -> MultiPageSite.this);

		final IHandlerService handlerService = new LegacyHandlerService(e4Context);
		e4Context.set(IHandlerService.class, handlerService);

		e4Context.set(IContextService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(final IEclipseContext context, final String contextKey) {
				if (contextService == null) {
					contextService = new NestableContextService(context.getParent().get(IContextService.class),
							new ActivePartExpression(partSite.getPart()));
				}
				return contextService;
			}
		});
	}

	/**
	 * Disposes of the menu extender contributions.
	 */
	protected void dispose() {
		if (menuExtenders != null) {
			final HashSet<MenuManager> managers = HashSet.newHashSet(menuExtenders.size());
			for (final PopupMenuExtender menuExtender : menuExtenders) {
				final PopupMenuExtender ext = menuExtender;
				managers.add(ext.getManager());
				ext.dispose();
			}
			if (!managers.isEmpty()) {
				for (final MenuManager mgr : managers) {
					mgr.dispose();
				}
			}
			menuExtenders = null;
		}
		subActionBars.dispose();

		if (contextService != null) {
			contextService.dispose();
		}

		serviceLocator.dispose();
		e4Context.dispose();
	}

	/**
	 * The PageSite implementation of this <code>IPageSite</code> method returns the
	 * <code>SubActionBars</code> for this site.
	 *
	 * @return the subactionbars for this site
	 */
	@Override
	public SubActionBars getActionBars() {
		return subActionBars;
	}

	@Override
	public <T> T getAdapter(final Class<T> adapter) {
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	@Override
	public IWorkbenchPage getPage() {
		return parentSite.getPage();
	}

	@Override
	public ISelectionProvider getSelectionProvider() {
		return selectionProvider;
	}

	@Override
	public final <T> T getService(final Class<T> key) {
		final T service = serviceLocator.getService(key);
		if (active && service instanceof final INestable nestable) {
			nestable.activate();
		}
		return service;
	}

	@Override
	public Shell getShell() {
		return parentSite.getShell();
	}

	@Override
	public IWorkbenchWindow getWorkbenchWindow() {
		return parentSite.getWorkbenchWindow();
	}

	@Override
	public final boolean hasService(final Class<?> key) {
		return serviceLocator.hasService(key);
	}

	@Override
	public void registerContextMenu(final String menuID, final MenuManager menuMgr,
			final ISelectionProvider selProvider) {
		if (menuExtenders == null) {
			menuExtenders = new ArrayList<>(1);
		}
		PartSite.registerContextMenu(menuID, menuMgr, selProvider, false, partSite.getPart(), e4Context, menuExtenders);
	}

	@Override
	public void setSelectionProvider(final ISelectionProvider provider) {
		selectionProvider = provider;
	}

	@Override
	public void activate() {
		active = true;

		serviceLocator.activate();

		if (contextService != null) {
			contextService.activate();
		}
	}

	@Override
	public void deactivate() {
		active = false;
		if (contextService != null) {
			contextService.deactivate();
		}

		serviceLocator.deactivate();
	}
}

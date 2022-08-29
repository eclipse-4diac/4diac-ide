/*******************************************************************************
 * Copyright (c) 2000, 2021 IBM Corporation and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Johannes Kepler University - ported the original ZoomComboContributionItem
 *                                 from GEF3 to the new
 *                                 WorkbenchWindowControlContribution
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.handlers;

import org.eclipse.gef.editparts.ZoomListener;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;
import org.eclipse.ui.part.MultiPageEditorPart;

/** This class is based on the {@link org.eclipse.gef.ui.actions.ZoomComboContributionItem} by @author Eric Bordeau
 *
 * @author Alois Zoitl ported it to the WorkbenchWindowControlContribution and extended it with support for multipage
 *         editors.
 *
 *         This class is supposed to be contributed back to a revamped GEF 3 project. */
public class ZoomComboContributionItem extends WorkbenchWindowControlContribution implements ZoomListener {

	private class ZoomContributorPartListener implements IPartListener {
		@Override
		public void partActivated(final IWorkbenchPart part) {
			setZoomManager(part.getAdapter(ZoomManager.class));
			if (part instanceof MultiPageEditorPart && part != activeMultiPageEditorPart) {
				activeMultiPageEditorPart = (MultiPageEditorPart) part;
				activeMultiPageEditorPart.addPageChangedListener(pageChangeListener);
			}
		}

		@Override
		public void partBroughtToTop(final IWorkbenchPart p) {
			// Nothing to be done here
		}

		@Override
		public void partClosed(final IWorkbenchPart part) {
			if (part == activeMultiPageEditorPart) {
				unHookPageChangeListener();
			}
		}

		@Override
		public void partDeactivated(final IWorkbenchPart part) {
			if (part == activeMultiPageEditorPart) {
				unHookPageChangeListener();
			}
		}

		@Override
		public void partOpened(final IWorkbenchPart p) {
			// Nothing to be done here
		}
	}

	private Combo combo;
	private ZoomManager zoomManager;
	private IPartListener partListener;
	private MultiPageEditorPart activeMultiPageEditorPart;
	private final IPageChangedListener pageChangeListener = event -> {
		if (activeMultiPageEditorPart != null) {
			setZoomManager(activeMultiPageEditorPart.getAdapter(ZoomManager.class));
		}
	};

	public ZoomComboContributionItem() {
		super(GEFActionConstants.ZOOM_TOOLBAR_WIDGET);
	}

	public ZoomComboContributionItem(final String id) {
		super(id);
		// nothing special to be done here
	}


	@Override
	protected Control createControl(final Composite parent) {
		hookPartListener();
		combo = new Combo(parent, SWT.DROP_DOWN);

		combo.addSelectionListener( new SelectionListener() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleComboSelected();
			}

			@Override
			public void widgetDefaultSelected(final SelectionEvent e) {
				handleComboSelected();
			}
		});
		combo.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(final FocusEvent e) {
				// do nothing
			}

			@Override
			public void focusLost(final FocusEvent e) {
				refresh();
			}
		});

		return combo;
	}


	@Override
	public void zoomChanged(final double zoom) {
		refresh();
	}

	@Override
	public void dispose() {
		super.dispose();
		if (partListener == null) {
			return;
		}
		getPartService().removePartListener(partListener);
		unHookPageChangeListener();
		if (zoomManager != null) {
			zoomManager.removeZoomListener(this);
			zoomManager = null;
		}
		combo = null;
		partListener = null;
	}

	private IPartService getPartService() {
		return getWorkbenchWindow().getPartService();
	}

	private ZoomManager getZoomManager() {
		return zoomManager;
	}

	private void hookPartListener() {
		partListener = new ZoomContributorPartListener();
		getPartService().addPartListener(partListener);
	}

	private void unHookPageChangeListener() {
		if (activeMultiPageEditorPart != null) {
			activeMultiPageEditorPart.removePageChangedListener(pageChangeListener);
			activeMultiPageEditorPart = null;
		}
	}

	private void setZoomManager(final ZoomManager zm) {
		if (zoomManager == zm) {
			return;
		}
		if (zoomManager != null) {
			zoomManager.removeZoomListener(this);
		}

		zoomManager = zm;
		if (zoomManager != null) {
			repopulateCombo();
		}
		refresh();

		if (zoomManager != null) {
			zoomManager.addZoomListener(this);
		}
	}

	private void repopulateCombo() {
		combo.setItems(getZoomManager().getZoomLevelsAsText());
	}

	private void refresh() {
		if (combo == null || combo.isDisposed()) {
			return;
		}
		if (zoomManager == null) {
			combo.setEnabled(false);
			combo.setText(""); //$NON-NLS-1$
		} else {
			final String zoom = getZoomManager().getZoomAsText();
			final int index = combo.indexOf(zoom);
			if (index == -1) {
				combo.setText(zoom);
			} else {
				combo.select(index);
			}
			combo.setEnabled(true);
		}
		combo.pack();
		combo.getParent().pack();
	}

	private void handleComboSelected() {
		if (zoomManager != null) {
			if (combo.getSelectionIndex() >= 0) {
				zoomManager.setZoomAsText(combo.getItem(combo.getSelectionIndex()));
			} else {
				zoomManager.setZoomAsText(combo.getText());
			}
		}
		refresh();
	}

}

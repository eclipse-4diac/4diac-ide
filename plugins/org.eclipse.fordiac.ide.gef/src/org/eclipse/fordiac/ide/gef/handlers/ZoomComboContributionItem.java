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

import org.eclipse.draw2d.zoom.ZoomListener;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.GEFActionConstants;
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

/**
 * This class is based on the
 * {@link org.eclipse.gef.ui.actions.ZoomComboContributionItem} by @author Eric
 * Bordeau
 *
 * @author Alois Zoitl ported it to the WorkbenchWindowControlContribution and
 *         extended it with support for multipage editors.
 *
 *         This class is supposed to be contributed back to a revamped GEF 3
 *         project.
 */
public class ZoomComboContributionItem extends WorkbenchWindowControlContribution implements ZoomListener {
	private Combo combo;
	private ZoomManager zoomManager;
	private IPartListener partListener = new IPartListener() {
		@Override
		public void partActivated(final IWorkbenchPart part) {
			final ZoomManager zm = part.getAdapter(ZoomManager.class);
			if (zm != null) {
				setZoomManager(zm);
			}
		}

		@Override
		public void partBroughtToTop(final IWorkbenchPart part) {
			// Nothing to be done here
		}

		@Override
		public void partClosed(final IWorkbenchPart part) {
			// Nothing to be done here
		}

		@Override
		public void partDeactivated(final IWorkbenchPart part) {
			// Nothing to be done here
		}

		@Override
		public void partOpened(final IWorkbenchPart part) {
			// Nothing to be done here
		}
	};

	public ZoomComboContributionItem() {
		super(GEFActionConstants.ZOOM_TOOLBAR_WIDGET);
	}

	public ZoomComboContributionItem(final String id) {
		super(id);
	}

	@Override
	protected Control createControl(final Composite parent) {
		getPartService().addPartListener(partListener);

		combo = new Combo(parent, SWT.DROP_DOWN);
		combo.addSelectionListener(new SelectionListener() {
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

		setZoomManager(getWorkbenchWindow().getActivePage().getActiveEditor().getAdapter(ZoomManager.class));

		return combo;
	}

	@Override
	public void zoomChanged(final double zoom) {
		refresh();
	}

	@Override
	public void dispose() {
		super.dispose();
		combo = null;
		if (zoomManager != null) {
			zoomManager.removeZoomListener(this);
			zoomManager = null;
		}
		if (partListener != null) {
			getPartService().removePartListener(partListener);
			partListener = null;
		}
	}

	private IPartService getPartService() {
		return getWorkbenchWindow().getPartService();
	}

	private ZoomManager getZoomManager() {
		return zoomManager;
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
			zoomManager.addZoomListener(this);
		}
		refresh();
	}

	private void repopulateCombo() {
		combo.setItems(getZoomManager().getZoomLevelsAsText());
	}

	private void refresh() {
		if (combo == null || combo.isDisposed()) {
			return;
		}

		final String zoom = getZoomManager().getZoomAsText();
		final int index = combo.indexOf(zoom);
		if (index == -1) {
			combo.setText(zoom);
		} else {
			combo.select(index);
		}
		combo.pack();
		combo.getParent().pack();
	}

	private void handleComboSelected() {
		if (zoomManager != null) {
			final int selected = combo.getSelectionIndex();
			if (selected >= 0) {
				zoomManager.setZoomAsText(combo.getItem(selected));
			} else {
				zoomManager.setZoomAsText(combo.getText());
			}
		}
		refresh();
	}
}

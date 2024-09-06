/*******************************************************************************
 * Copyright (c) 2015 - 2018 fortiss GmbH, Johannes Kepler University
 * 				 2022 Primetals Technologies Germany GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerd Kainz, Alois Zoitl, Monika Wenger
 *               - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *   Michael Oberlehner - added subapp monitoring
 *   Fabio Gandolfi - added decision remember dialog
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.handlers;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.MonitoringManagerUtils;
import org.eclipse.fordiac.ide.monitoring.editparts.MonitoringAdapterInterfaceEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class AddWatchHandler extends AbstractAddWatchesHandler {

	@Override
	protected void doExecute(final ExecutionEvent event, final StructuredSelection structSel)
			throws ExecutionException {
		final MonitoringManager manager = MonitoringManager.getInstance();
		final Set<IInterfaceElement> foundElements = getSelectedWatchedElements(manager, structSel);
		handleMonitoringState(foundElements, HandlerUtil.getActiveSiteChecked(event).getShell());
		for (final IInterfaceElement ie : foundElements) {
			createWatchForIE(manager, ie);
		}
		refreshEditor();
		MonitoringManager.getInstance().notifyWatchesChanged();
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled(selection instanceof final StructuredSelection structSel
				&& !getSelectedWatchedElements(MonitoringManager.getInstance(), structSel).isEmpty());
	}

	private static Set<IInterfaceElement> getSelectedWatchedElements(final MonitoringManager manager,
			final StructuredSelection selection) {
		final Set<IInterfaceElement> foundElements = new HashSet<>();
		for (final Object selectedObject : selection) {
			if ((selectedObject instanceof final EditPart ep)
					&& (!(selectedObject instanceof MonitoringAdapterInterfaceEditPart))) {
				final Object model = ep.getModel();

				if (model instanceof final FBNetworkElement fbnEl) {
					if (MonitoringManagerUtils.canBeMonitored(fbnEl)) {
						foundElements.addAll(fbnEl.getInterface().getAllInterfaceElements());
					}
				} else if ((model instanceof final IInterfaceElement ie)
						&& (MonitoringManagerUtils.canBeMonitored(ie, false) && !manager.containsPort(ie))) {
					foundElements.add(ie);
				}
			}
		}
		return foundElements;
	}

}

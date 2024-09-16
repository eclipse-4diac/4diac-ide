/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.monitoring.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.InternalVarInstance;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringFactory;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.MonitoringManagerUtils;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class WatchInternalVarsHandler extends AbstractAddWatchesHandler {

	@Override
	protected void doExecute(final ExecutionEvent event, final StructuredSelection structSel)
			throws ExecutionException {
		final List<FB> foundFBs = getSelectedBaseFBInstances(structSel);
		handleMonitoringState(foundFBs, HandlerUtil.getActiveSiteChecked(event).getShell());
		addWatchesForFbs(foundFBs);
		MonitoringManager.getInstance().notifyWatchesChanged();
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof final StructuredSelection structSel) {
			setBaseEnabled(!getSelectedBaseFBInstances(structSel).isEmpty());
		} else {
			setBaseEnabled(false);
		}
	}

	private static List<FB> getSelectedBaseFBInstances(final StructuredSelection structSel) {
		final List<FB> fbInstances = new ArrayList<>();

		for (final Object el : structSel.toList()) {
			if ((el instanceof final EditPart ep) && (ep.getModel() instanceof final FB fb)
					&& (fb.getType() instanceof BaseFBType) && MonitoringManagerUtils.canBeMonitored(fb)) {
				fbInstances.add(fb);
			}
		}
		return fbInstances;
	}

	private void addWatchesForFbs(final List<FB> foundFBs) {
		final MonitoringManager manager = MonitoringManager.getInstance();

		for (final FB fb : foundFBs) {
			if (fb.getType() instanceof final BaseFBType baseFBType) {
				for (final VarDeclaration internalVar : baseFBType.getInternalVars()) {
					createWatchForIE(manager, createInternalVarInstance(fb, internalVar));
				}
			}
		}
	}

	public static InternalVarInstance createInternalVarInstance(final FB fb, final VarDeclaration internalVar) {
		final InternalVarInstance intVar = MonitoringFactory.eINSTANCE.createInternalVarInstance();
		intVar.setArraySize(EcoreUtil.copy(internalVar.getArraySize()));
		intVar.setFb(fb);
		intVar.setName(internalVar.getName());
		intVar.setType(internalVar.getType());
		return intVar;
	}

}

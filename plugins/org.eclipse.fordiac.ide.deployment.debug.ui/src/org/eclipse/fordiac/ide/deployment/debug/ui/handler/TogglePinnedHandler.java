/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.fordiac.ide.deployment.debug.breakpoint.DeploymentWatchpoint;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.fordiac.ide.deployment.debug.ui.breakpoint.DeploymentWatchpointUtil;
import org.eclipse.fordiac.ide.deployment.debug.watch.IWatch;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;
import org.eclipse.ui.services.IEvaluationService;

public class TogglePinnedHandler extends AbstractHandler implements IElementUpdater {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		getWatches(HandlerUtil.getCurrentStructuredSelection(event))
				.forEachOrdered(watch -> togglePinned(watch, event));
		return null;
	}

	private static void togglePinned(final IWatch watch, final ExecutionEvent event) {
		final IFile file = watch.getDebugTarget().getSystem().getTypeEntry().getFile();
		final Optional<DeploymentWatchpoint> watchpoint = DeploymentWatchpointUtil.findExistingWatchpoint(file,
				watch.getQualifiedName());
		if (watchpoint.isEmpty()) {
			createWatchpoint(file, watch.getWatchedElement(), event);
		} else {
			updateWatchpoint(watchpoint.get(), event);
		}
	}

	private static void createWatchpoint(final IResource resource, final ITypedElement element,
			final ExecutionEvent event) {
		try {
			final DeploymentWatchpoint watchpoint = new DeploymentWatchpoint(resource, element);
			watchpoint.setPinned(true);
			DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(watchpoint);
		} catch (final CoreException e) {
			ErrorDialog.openError(HandlerUtil.getActiveShell(event), null, null, e.getStatus());
		}
	}

	private static void updateWatchpoint(final DeploymentWatchpoint watchpoint, final ExecutionEvent event) {
		try {
			watchpoint.setPinned(!watchpoint.isPinned());
		} catch (final CoreException e) {
			ErrorDialog.openError(HandlerUtil.getActiveShell(event), null, null, e.getStatus());
		}
	}

	@Override
	public void updateElement(final UIElement element, final Map parameters) {
		final IEvaluationContext evaluationContext = PlatformUI.getWorkbench().getService(IEvaluationService.class)
				.getCurrentState();
		if (HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME) instanceof final IStructuredSelection selection) {
			if (getWatches(selection).noneMatch(IWatch::isPinned)) {
				element.setText(Messages.TogglePinnedHandler_PinWatch);
			} else if (getWatches(selection).allMatch(IWatch::isPinned)) {
				element.setText(Messages.TogglePinnedHandler_UnpinWatch);
			}
		}
	}

	private static Stream<IWatch> getWatches(final IStructuredSelection selection) {
		return selection.stream().filter(IWatch.class::isInstance).map(IWatch.class::cast);
	}
}

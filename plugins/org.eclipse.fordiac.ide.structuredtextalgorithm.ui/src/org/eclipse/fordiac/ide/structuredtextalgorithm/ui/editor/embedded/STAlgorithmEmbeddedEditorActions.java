/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.embedded;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ActiveShellExpression;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorActions;

@SuppressWarnings("restriction")
public class STAlgorithmEmbeddedEditorActions extends EmbeddedEditorActions {

	public static class Factory extends EmbeddedEditorActions.Factory {

		@Override
		protected EmbeddedEditorActions createActions(final ISourceViewer viewer) {
			return new STAlgorithmEmbeddedEditorActions(viewer, workbench);
		}
	}

	public STAlgorithmEmbeddedEditorActions(final ISourceViewer viewer, final IWorkbench workbench) {
		super(viewer, workbench);
	}

	@Override
	protected void createFocusAndDisposeListeners() {
		final List<IHandlerActivation> handlerActivations = new ArrayList<>(allActions.size());
		final IHandlerService handlerService = workbench.getAdapter(IHandlerService.class);
		final IContextService contextService = workbench.getAdapter(IContextService.class);
		final Shell shell = viewer.getTextWidget().getShell();
		final ActiveShellExpression expression = new ActiveShellExpression(shell);
		final IContextActivation contextActivation = contextService.activateContext(EMBEDDED_TEXT_EDITOR_SCOPE,
				expression);
		viewer.getTextWidget().addDisposeListener(e -> {
			handlerService.deactivateHandlers(handlerActivations);
			contextService.deactivateContext(contextActivation);
		});

		viewer.getTextWidget().addFocusListener(new FocusListener() {
			@Override
			public void focusLost(final FocusEvent e) {
				handlerService.deactivateHandlers(handlerActivations);
				handlerActivations.clear();
			}

			@Override
			public void focusGained(final FocusEvent e) {
				if (handlerActivations.isEmpty()) {
					for (final IAction action : allActions.values()) {
						handlerActivations.add(handlerService.activateHandler(action.getActionDefinitionId(),
								new ActionHandler(action), expression, true));
					}
				}
			}

		});
	}
}

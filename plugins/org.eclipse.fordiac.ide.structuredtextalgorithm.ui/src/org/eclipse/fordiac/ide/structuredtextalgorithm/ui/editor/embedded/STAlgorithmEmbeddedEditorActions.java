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

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.swt.IFocusService;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorActions;

@SuppressWarnings("restriction")
public class STAlgorithmEmbeddedEditorActions extends EmbeddedEditorActions {

	private static final String CONTROL_ID = "org.eclipse.xtext.ui.embeddedTextEditor"; //$NON-NLS-1$

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
		final IFocusService focusService = workbench.getService(IFocusService.class);
		focusService.addFocusTracker(viewer.getTextWidget(), CONTROL_ID);
		final Expression expression = new ActiveFocusExpression(viewer.getTextWidget());
		final IContextActivation contextActivation = contextService.activateContext(EMBEDDED_TEXT_EDITOR_SCOPE,
				expression);

		viewer.getTextWidget().addFocusListener(FocusListener.focusGainedAdapter(e -> {
			if (handlerActivations.isEmpty()) {
				for (final IAction action : allActions.values()) {
					handlerActivations.add(handlerService.activateHandler(action.getActionDefinitionId(),
							new ActionHandler(action), expression, true));
				}
			}
		}));

		viewer.getTextWidget().addDisposeListener(e -> {
			handlerService.deactivateHandlers(handlerActivations);
			contextService.deactivateContext(contextActivation);
		});
	}

	protected static class ActiveFocusExpression extends Expression {
		private final Control control;

		public ActiveFocusExpression(final Control control) {
			this.control = control;
		}

		@Override
		public EvaluationResult evaluate(final IEvaluationContext context) {
			return context.getVariable(ISources.ACTIVE_FOCUS_CONTROL_NAME) == control ? EvaluationResult.TRUE
					: EvaluationResult.FALSE;
		}

		@Override
		public void collectExpressionInfo(final ExpressionInfo info) {
			info.addVariableNameAccess(ISources.ACTIVE_FOCUS_CONTROL_NAME);
		}
	}
}

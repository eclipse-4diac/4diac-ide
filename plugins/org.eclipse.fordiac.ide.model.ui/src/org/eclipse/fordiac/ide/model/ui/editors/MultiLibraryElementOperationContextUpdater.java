/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 * 					  Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Erich Jobst - initial API and implementation and/or initial documentation
 *   Sebastian Hollersbacher - Extracted to own class
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.ui.editors;

import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.ScopedOperation;
import org.eclipse.ui.IEditorInput;

public class MultiLibraryElementOperationContextUpdater implements IOperationHistoryListener {

	private final Supplier<IUndoContext> editorUndoContext;
	private final Set<IEditorInput> editorInputs;

	public MultiLibraryElementOperationContextUpdater(final Supplier<IUndoContext> editorUndoContext,
			final Set<IEditorInput> editorInputs) {
		this.editorUndoContext = editorUndoContext;
		this.editorInputs = editorInputs;
	}

	@Override
	public void historyNotification(final OperationHistoryEvent event) {
		if (event.getEventType() == OperationHistoryEvent.ABOUT_TO_EXECUTE
				&& event.getOperation().hasContext(editorUndoContext.get())
				&& event.getOperation() instanceof final ScopedOperation scopedOperation) {
			final Set<EObject> affectedElements = scopedOperation.getAffectedObjects().stream()
					.map(EcoreUtil::getRootContainer).collect(Collectors.toSet());
			editorInputs.stream()
					.filter(input -> affectedElements
							.contains(LibraryElementProvider.INSTANCE.getLibraryElement(input)))
					.map(LibraryElementProvider.INSTANCE::getUndoContext).filter(Objects::nonNull)
					.forEachOrdered(event.getOperation()::addContext);
		}
	}
}

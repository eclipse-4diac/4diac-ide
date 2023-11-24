/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.commands.Command;

public class DeleteErrorMarkerCommand extends Command implements ScopedCommand {

	private final ErrorMarkerInterface errorIe;
	private DeleteInterfaceCommand deleteErrorMarkerIECmd;
	private final DeleteFBNetworkElementCommand deleteErrorMarkerFBN;

	public DeleteErrorMarkerCommand(final ErrorMarkerInterface errorIe, final FBNetworkElement errorFb) {
		this.errorIe = Objects.requireNonNull(errorIe);
		deleteErrorMarkerFBN = createDeleteFBNCommand(errorIe, errorFb);
	}

	@Override
	public void execute() {
		// if the error interface element has no container it was already deleted by
		// another command (e.g., connection
		// and pin where selected at the same time and connection got deleted first)
		if (errorIe.eContainer() != null) {
			deleteErrorMarkerIECmd = new DeleteInterfaceCommand(errorIe);
			deleteErrorMarkerIECmd.execute();
			if (deleteErrorMarkerFBN != null) {
				deleteErrorMarkerFBN.execute();
			}
		}
	}

	@Override
	public void undo() {
		if (deleteErrorMarkerIECmd != null) {
			if (deleteErrorMarkerFBN != null) {
				deleteErrorMarkerFBN.undo();
			}
			deleteErrorMarkerIECmd.undo();
		}
	}

	@Override
	public void redo() {
		if (deleteErrorMarkerIECmd != null) {
			deleteErrorMarkerIECmd.redo();
			if (deleteErrorMarkerFBN != null) {
				deleteErrorMarkerFBN.redo();
			}
		}
	}

	private static DeleteFBNetworkElementCommand createDeleteFBNCommand(final ErrorMarkerInterface errorIe,
			final FBNetworkElement errorFb) {
		final FBNetworkElement fbNetworkElement = errorIe.getFBNetworkElement();
		// only delete the fbnetworkelement if this delete error marker command was not
		// called becuase of an fb delete
		// and if the fbelement is an error maker fb and we are the last errormarker at
		// this fb
		if (fbNetworkElement != errorFb && fbNetworkElement instanceof ErrorMarkerFBNElement
				&& fbNetworkElement.getInterface().getErrorMarker().size() == 1) {
			return new DeleteFBNetworkElementCommand(fbNetworkElement);
		}
		return null;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		if (errorIe.getFBNetworkElement() != null) {
			return Set.of(errorIe.getFBNetworkElement());
		}
		return Set.of(errorIe);
	}
}

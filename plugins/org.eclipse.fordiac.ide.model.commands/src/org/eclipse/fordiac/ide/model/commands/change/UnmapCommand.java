/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH,  fortiss GmbH, AIT
 * 				 2019 Johannes Keppler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Filip Pr�stl Andr�n, Monika Wenger
 *       - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteMappedCommunicationFbCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationChannel;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.gef.commands.Command;

public class UnmapCommand extends Command implements ScopedCommand {
	private final Mapping mapping;
	private final AutomationSystem system;
	private int elementIndex;

	private final DeleteFBNetworkElementCommand deleteMappedFBCmd;

	public UnmapCommand(final FBNetworkElement element) {
		super(FordiacMessages.Unmap);
		mapping = element.getMapping();
		system = mapping.getAutomationSystem();
		deleteMappedFBCmd = getDeleteMappedFbCommand(mapping.getTo());
	}

	@Override
	public boolean canExecute() {
		return system != null;
	}

	@Override
	public void execute() {
		mapping.getFrom().setMapping(null);
		mapping.getTo().setMapping(null);
		elementIndex = system.getMapping().indexOf(mapping);
		system.getMapping().remove(mapping);
		deleteMappedFBCmd.execute();
	}

	@Override
	public void undo() {
		deleteMappedFBCmd.undo();
		mapping.getFrom().setMapping(mapping);
		mapping.getTo().setMapping(mapping);
		system.getMapping().add(elementIndex, mapping);
	}

	@Override
	public void redo() {
		mapping.getFrom().setMapping(null);
		mapping.getTo().setMapping(null);
		system.getMapping().remove(mapping);
		deleteMappedFBCmd.redo();
	}

	public FBNetworkElement getMappedFBNetworkElement() {
		return deleteMappedFBCmd.getFBNetworkElement();
	}

	private static DeleteFBNetworkElementCommand getDeleteMappedFbCommand(final FBNetworkElement fb) {
		if (fb instanceof final CommunicationChannel channel) {
			return new DeleteMappedCommunicationFbCommand(channel);
		}
		return new DeleteFBNetworkElementCommand(fb);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(mapping.getFrom(), deleteMappedFBCmd.getFbParent().eContainer());
	}

	public int getElementIndex() {
		return elementIndex;
	}
}

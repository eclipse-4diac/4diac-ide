/*******************************************************************************
 * Copyright (c) 2008, 2024 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler Unviersity Linz
 *               2023 Primetals Technologies Austria GmbHy
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - updated for new adapter FB handling
 *   Fabio Gandolfi - added FBNewtork error marker handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.ConnectionLayoutTagger;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class ChangeNameCommand extends Command implements ConnectionLayoutTagger, ScopedCommand {
	private final INamedElement element;
	private final String name;
	private String oldName;
	private final CompoundCommand additionalCommands = new CompoundCommand();
	private boolean validateName;

	private ChangeNameCommand(final INamedElement element, final String name) {
		this(element, name, true);
	}

	private ChangeNameCommand(final INamedElement element, final String name, final boolean validateName) {
		this.element = Objects.requireNonNull(element);
		this.name = name;
		this.validateName = validateName;
	}

	public static ChangeNameCommand forName(final INamedElement element, final String name) {
		INamedElement toRename = element;
		if (element instanceof final VarDeclaration varDecl && varDecl.isInOutVar() && !varDecl.isIsInput()) {
			toRename = varDecl.getInOutVarOpposite();
		}
		final ChangeNameCommand result = new ChangeNameCommand(toRename, name);
		addAdditionalRenames(toRename, name, result);
		return result;
	}

	private static void addAdditionalRenames(final INamedElement element, final String name,
			final ChangeNameCommand result) {
		if ((element instanceof final FBNetworkElement fbne) && fbne.isMapped()) {
			result.getAdditionalCommands().add(new ChangeNameCommand(fbne.getOpposite(), name));
		}
		if (element instanceof final IInterfaceElement interfaceElement
				&& interfaceElement.getFBNetworkElement() instanceof final SubApp subApp && subApp.isMapped()) {
			result.getAdditionalCommands().add(
					new ChangeNameCommand(subApp.getOpposite().getInterfaceElement(interfaceElement.getName()), name));
		}
		if (element instanceof final AdapterDeclaration adapterDeclaration) {
			handleAdapterDeclarationRename(name, result, adapterDeclaration);
		}
		if (element instanceof final AdapterFB adapterFB) {
			result.getAdditionalCommands().add(new ChangeNameCommand(adapterFB.getAdapterDecl(), name));
		}
		if (element instanceof final Attribute attribute) {
			result.setValidateName(false); // do not validate attribute names (may contain FQN)
			if (ChangeAttributeDeclarationCommand.attributeDeclarationChanged(attribute, name)) {
				result.getAdditionalCommands().add(ChangeAttributeDeclarationCommand.forName(attribute, name));
			}
		}
	}

	private static void handleAdapterDeclarationRename(final String name, final ChangeNameCommand result,
			final AdapterDeclaration adapterDeclaration) {
		// only when the adapter fb is in a FBNetwork we need to perform a name check.
		result.getAdditionalCommands().add(new ChangeNameCommand(adapterDeclaration.getAdapterFB(), name,
				adapterDeclaration.getAdapterFB().eContainer() instanceof FBNetwork));
	}

	@Override
	public boolean canExecute() {
		return (!isValidateName() || NameRepository.isValidName(element, name))
				&& (additionalCommands.isEmpty() || additionalCommands.canExecute())
				&& !(element instanceof final FBNetworkElement fbne && fbne.isContainedInTypedInstance());
	}

	@Override
	public boolean canRedo() {
		return super.canRedo() && Objects.equals(oldName, element.getName())
				&& (additionalCommands.isEmpty() || additionalCommands.canRedo());
	}

	@Override
	public boolean canUndo() {
		return super.canUndo() && Objects.equals(name, element.getName())
				&& (additionalCommands.isEmpty() || additionalCommands.canUndo());
	}

	@Override
	public void execute() {
		oldName = element.getName();
		setName(name);
		additionalCommands.execute();
	}

	@Override
	public void undo() {
		additionalCommands.undo();
		setName(oldName);
	}

	@Override
	public void redo() {
		setName(name);
		additionalCommands.redo();
	}

	private void setName(final String name) {
		element.setName(name);
	}

	public INamedElement getElement() {
		return element;
	}

	public String getOldName() {
		return oldName;
	}

	public CompoundCommand getAdditionalCommands() {
		return additionalCommands;
	}

	protected boolean isValidateName() {
		return validateName;
	}

	protected void setValidateName(final boolean validateName) {
		this.validateName = validateName;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		// changed name affects all siblings in named container
		EObject container = element;
		do {
			container = container.eContainer();
			if (container instanceof final INamedElement namedContainer) {
				return Set.of(namedContainer);
			}
		} while (container != null);
		return Set.of(element);
	}
}

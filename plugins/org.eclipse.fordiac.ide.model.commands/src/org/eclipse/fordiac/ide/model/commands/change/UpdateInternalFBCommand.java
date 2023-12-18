/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.commands.Command;

public class UpdateInternalFBCommand extends Command implements ScopedCommand {
	/** The updated version of the FBNetworkElement */
	private FB newElement;

	/** The FBNetworkElement which should be updated */
	private final FB oldElement;

	private final TypeEntry entry;

	private final BaseFBType baseFbType;

	private int index;

	public UpdateInternalFBCommand(final FB fb, final TypeEntry entry) {
		this.oldElement = Objects.requireNonNull(fb);
		this.entry = Objects.requireNonNull(entry);
		this.baseFbType = (BaseFBType) fb.eContainer();
	}

	private EList<FB> getInteralFBList() {
		return baseFbType.getInternalFbs();
	}

	@Override
	public boolean canExecute() {
		if (null == oldElement || null == entry || null == baseFbType) {
			return false;
		}
		return super.canExecute();
	}

	protected void createNewFB() {
		newElement = createCopiedFBEntry(oldElement);
		newElement.setInterface(newElement.getType().getInterfaceList().copy());
		newElement.setName(oldElement.getName());
		createValues();
		transferInstanceComments();
	}

	protected FB createCopiedFBEntry(final FBNetworkElement srcElement) {
		FB copy;
		if (entry instanceof AdapterTypeEntry) {
			copy = LibraryElementFactory.eINSTANCE.createAdapterFB();
			((AdapterFB) copy).setAdapterDecl(((AdapterFB) srcElement).getAdapterDecl());
		} else if (entry.getType() instanceof CompositeFBType) {
			copy = LibraryElementFactory.eINSTANCE.createCFBInstance();
		} else {
			copy = LibraryElementFactory.eINSTANCE.createFB();
		}

		copy.setTypeEntry(entry);
		return copy;
	}

	protected void createValues() {
		newElement.getInterface().getInputVars().stream().forEach(inVar -> {
			inVar.setValue(LibraryElementFactory.eINSTANCE.createValue());
			checkSourceParam(inVar);
		});
	}

	private void checkSourceParam(final VarDeclaration variable) {
		final VarDeclaration srcVar = oldElement.getInterface().getVariable(variable.getName());
		if ((null != srcVar) && (null != srcVar.getValue())) {
			variable.getValue().setValue(srcVar.getValue().getValue());
		}
	}

	protected void transferInstanceComments() {
		oldElement.getInterface().getAllInterfaceElements().stream().filter(ie -> !ie.getComment().isBlank())
				.forEach(ie -> {
					final IInterfaceElement newIE = newElement.getInterfaceElement(ie.getName());
					if (newIE != null) {
						newIE.setComment(ie.getComment());
					}
				});
	}

	@Override
	public void execute() {
		createNewFB();
		index = getIndexOfFB();
		redo();
	}

	@Override
	public void redo() {
		getInteralFBList().set(index, newElement);
	}

	@Override
	public void undo() {
		getInteralFBList().set(index, oldElement);
	}

	private int getIndexOfFB() {
		return getInteralFBList().indexOf(getInteralFBList().stream()
				.filter(fb -> fb.getName().equals(oldElement.getName())).findFirst().orElse(oldElement));
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(baseFbType);
	}
}

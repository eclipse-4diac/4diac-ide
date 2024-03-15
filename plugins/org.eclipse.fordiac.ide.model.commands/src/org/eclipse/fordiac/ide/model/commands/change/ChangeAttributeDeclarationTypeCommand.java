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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DirectlyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.gef.commands.Command;

public class ChangeAttributeDeclarationTypeCommand extends Command implements ScopedCommand {
	private final AttributeDeclaration attributeDeclaration;
	private AnyDerivedType oldType;
	private AnyDerivedType newType;

	public ChangeAttributeDeclarationTypeCommand(final AttributeDeclaration attributeDeclaration) {
		this.attributeDeclaration = Objects.requireNonNull(attributeDeclaration);
		createNewType();
	}

	private void createNewType() {
		if (attributeDeclaration.getType() instanceof StructuredType) {
			final DirectlyDerivedType directlyDerivedType = DataFactory.eINSTANCE.createDirectlyDerivedType();
			directlyDerivedType.setBaseType(ElementaryTypes.STRING);
			directlyDerivedType.setInitialValue(""); //$NON-NLS-1$
			newType = directlyDerivedType;
		} else if (attributeDeclaration.getType() instanceof DirectlyDerivedType) {
			newType = DataFactory.eINSTANCE.createStructuredType();
		}
		newType.setName(attributeDeclaration.getName());
	}

	@Override
	public final void execute() {
		oldType = attributeDeclaration.getType();
		attributeDeclaration.setType(newType);
	}

	@Override
	public final void undo() {
		attributeDeclaration.setType(oldType);
	}

	@Override
	public final void redo() {
		attributeDeclaration.setType(newType);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(attributeDeclaration);
	}
}

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

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.gef.commands.Command;

public class ChangeTargetAttributeCommand extends Command implements ScopedCommand {

	private final AttributeDeclaration attributeDeclaration;
	private final StructuredType newValue;
	private final StructuredType oldValue;

	public ChangeTargetAttributeCommand(final AttributeDeclaration attributeDeclaration,
			final StructuredType newValue) {
		this.attributeDeclaration = attributeDeclaration;
		this.newValue = newValue;
		StructuredType target = attributeDeclaration.getTarget();
		if (target != null) {
			target = (StructuredType) EcoreUtil.copy(InternalAttributeDeclarations.TARGET.getType());
		}
		this.oldValue = target;
	}

	@Override
	public void execute() {
		attributeDeclaration.setTarget(newValue);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		attributeDeclaration.setTarget(oldValue);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(attributeDeclaration);
	}
}

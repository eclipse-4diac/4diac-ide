/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
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
import org.eclipse.fordiac.ide.model.AttributeInheritMode;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.gef.commands.Command;

public class ChangeInheritAttributeCommand extends Command implements ScopedCommand {
	private final AttributeDeclaration attributeDeclaration;
	private final AttributeInheritMode newMode;
	private final AttributeInheritMode oldMode;

	public ChangeInheritAttributeCommand(final AttributeDeclaration attributeDeclaration,
			final AttributeInheritMode inheritMode) {
		this.attributeDeclaration = attributeDeclaration;
		this.newMode = inheritMode;
		final Attribute inheritAttribute = attributeDeclaration
				.getAttribute(InternalAttributeDeclarations.INHERIT.getName());
		this.oldMode = inheritAttribute != null ? AttributeInheritMode.valueOf(inheritAttribute.getValue())
				: AttributeInheritMode.IGNORE;
	}

	@Override
	public void execute() {
		changeValue(newMode);
	}

	@Override
	public void undo() {
		changeValue(oldMode);
	}

	@Override
	public void redo() {
		changeValue(newMode);
	}

	private void changeValue(final AttributeInheritMode mode) {
		if (mode == AttributeInheritMode.IGNORE) {
			attributeDeclaration.deleteAttribute(InternalAttributeDeclarations.INHERIT.getName());
		} else {
			attributeDeclaration.setAttribute(InternalAttributeDeclarations.INHERIT, mode.name(), null);
		}
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(attributeDeclaration);
	}
}

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
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Compiler;
import org.eclipse.gef.commands.Command;

public abstract class AbstractChangeCompilerCommand extends Command implements ScopedCommand {

	private final Compiler compiler;

	protected AbstractChangeCompilerCommand(final Compiler compiler) {
		this.compiler = Objects.requireNonNull(compiler);
	}

	public Compiler getCompiler() {
		return compiler;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(compiler);
	}
}

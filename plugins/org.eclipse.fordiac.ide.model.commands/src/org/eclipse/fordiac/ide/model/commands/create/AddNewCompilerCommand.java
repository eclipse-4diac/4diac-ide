/*******************************************************************************
 * Copyright (c) 2012, 2014 fortiss GmbH
 *               2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *  Bianca Wiesmayr
 *     - command now returns newly created element
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Compiler;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Language;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;

/**
 * The Class AddNewCompilerCommand.
 */
public class AddNewCompilerCommand extends CreationCommand implements ScopedCommand {

	/** The new Compiler value. */
	private Compiler compiler;

	private final FBType type;

	public AddNewCompilerCommand(final FBType type) {
		this.type = Objects.requireNonNull(type);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		compiler = LibraryElementFactory.eINSTANCE.createCompiler();
		compiler.setLanguage(Language.OTHER);
		compiler.setVersion("1.0"); //$NON-NLS-1$
		compiler.setVendor(FordiacMessages.Unknown);
		compiler.setProduct(FordiacMessages.Unknown);

		redo();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		getCompilerInfo().getCompiler().remove(compiler);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		getCompilerInfo().getCompiler().add(compiler);
	}

	private CompilerInfo getCompilerInfo() {
		if (type.getCompilerInfo() == null) {
			type.setCompilerInfo(LibraryElementFactory.eINSTANCE.createCompilerInfo());
		}
		return type.getCompilerInfo();
	}

	@Override
	public Object getCreatedElement() {
		return compiler;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(getCompilerInfo());
	}
}

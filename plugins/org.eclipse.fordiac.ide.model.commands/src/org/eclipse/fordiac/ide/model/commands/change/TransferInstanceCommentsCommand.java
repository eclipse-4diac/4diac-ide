/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.gef.commands.Command;

public class TransferInstanceCommentsCommand extends Command {
	private final StructManipulator srcStruct;
	private final Set<StructManipulator> destStructs;

	public TransferInstanceCommentsCommand(final StructManipulator srcStruct,
			final Set<StructManipulator> destStructs) {
		this.srcStruct = srcStruct;
		this.destStructs = destStructs;
	}

	@Override
	public void execute() {
		EList<ITypedElement> srcList;
		if (srcStruct instanceof Multiplexer) {
			srcList = srcStruct.getInputParameters();
		} else {
			srcList = srcStruct.getOutputParameters();
		}

		for (final INamedElement src : srcList) {
			for (final StructManipulator destStruct : destStructs) {
				if (destStruct instanceof Multiplexer) {
					destStruct.getInputParameters().stream().filter(dest -> dest.getName().equals(src.getName()))
							.forEach(dest -> dest.setComment(src.getComment()));
				} else {
					destStruct.getOutputParameters().stream().filter(dest -> dest.getName().equals(src.getName()))
							.forEach(dest -> dest.setComment(src.getComment()));
				}
			}
		}
	}

}

/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.typemanagement.refactoring.structmember;

import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;

enum StructMemberTargetKind {
	STRUCT_PIN,
	MULTIPLEXER,
	DEMULTIPLEXER;

	boolean matches(final StructManipulator manipulator) {
		return switch (this) {
		case STRUCT_PIN -> false;
		case MULTIPLEXER -> manipulator instanceof Multiplexer;
		case DEMULTIPLEXER -> manipulator instanceof Demultiplexer;
		};
	}
}

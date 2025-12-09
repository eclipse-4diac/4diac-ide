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
package org.eclipse.fordiac.ide.model.resource;

import org.eclipse.emf.ecore.resource.Resource.Diagnostic;

public class TypeImportDiagnostic implements Diagnostic {

	private final String message;
	private final String location;
	private final int line;

	public TypeImportDiagnostic(final String message, final String location) {
		this(message, location, 0);
	}

	public TypeImportDiagnostic(final String message, final String location, final int line) {
		this.message = message;
		this.location = location;
		this.line = line;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public int getLine() {
		return line;
	}

	@Override
	public int getColumn() {
		return 0;
	}
}

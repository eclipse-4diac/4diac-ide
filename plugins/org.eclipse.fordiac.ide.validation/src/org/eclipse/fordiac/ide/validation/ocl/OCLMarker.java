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
package org.eclipse.fordiac.ide.validation.ocl;

import java.util.Objects;

import org.eclipse.core.resources.IResource;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;

public record OCLMarker(IResource resource, ErrorMarkerBuilder builder) {

	public OCLMarker {
		Objects.requireNonNull(resource);
		Objects.requireNonNull(builder);
	}
}

/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.util;

import java.util.Optional;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.xtext.resource.XtextResource;

public interface STCorePartitioner {

	String combine(LibraryElement libraryElement);

	Optional<STCorePartition> partition(XtextResource resource);
}

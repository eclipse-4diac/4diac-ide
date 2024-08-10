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
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.document;

import java.util.Optional;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.fordiac.ide.structuredtextcore.ui.document.STCoreDocumentPartitioner;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.util.STFunctionPartitioner;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.XtextDocument;

public class STFunctionDocumentPartitioner extends STFunctionPartitioner implements STCoreDocumentPartitioner {

	@Override
	public Optional<STCorePartition> partition(final XtextDocument document) {
		try {
			return document.readOnly(resource -> {
				if (resource.getModificationStamp() != document.getModificationStamp()) {
					resource.reparse(document.get());
				}
				return partition(resource);
			}).or(() -> emergencyPartition(document));
		} catch (final Exception e) {
			return emergencyPartition(document); // try to salvage what we can
		}
	}

	protected Optional<STCorePartition> emergencyPartition(final XtextDocument document) {
		return Optional.of(createEmergencyPartition(document.get()));
	}

	@Override
	protected Optional<STCorePartition> emergencyPartition(final XtextResource resource) {
		return Optional.empty(); // always get it from the document instead
	}

	@Override
	protected Optional<STCorePartition> emergencyPartition(final STSource source) {
		return Optional.empty(); // always get it from the document instead
	}
}

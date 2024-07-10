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
package org.eclipse.fordiac.ide.globalconstantseditor.ui.document;

import java.util.Optional;

import org.eclipse.fordiac.ide.globalconstantseditor.util.GlobalConstantsPartitioner;
import org.eclipse.fordiac.ide.structuredtextcore.ui.document.STCoreDocumentPartitioner;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCorePartition;
import org.eclipse.xtext.ui.editor.model.XtextDocument;

public class GlobalConstantsDocumentPartitioner extends GlobalConstantsPartitioner
		implements STCoreDocumentPartitioner {

	@Override
	public Optional<? extends STCorePartition> partition(final XtextDocument document) {
		try {
			return document.readOnly(resource -> {
				if (resource.getModificationStamp() != document.getModificationStamp()) {
					resource.reparse(document.get());
				}
				return partition(resource);
			});
		} catch (final Exception e) {
			return Optional.empty();
		}
	}
}

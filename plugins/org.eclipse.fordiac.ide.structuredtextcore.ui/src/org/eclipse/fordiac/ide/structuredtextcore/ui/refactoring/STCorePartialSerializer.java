/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import java.util.List;

import org.eclipse.xtext.formatting2.regionaccess.IAstRegion;
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionDiffBuilder;
import org.eclipse.xtext.ide.serializer.impl.PartialSerializer;

@SuppressWarnings("restriction")
public class STCorePartialSerializer extends PartialSerializer {

	protected static class DeleteRegionsStrategy implements SerializationStrategy {
		private final List<? extends IAstRegion> regions;

		public DeleteRegionsStrategy(final List<? extends IAstRegion> regions) {
			this.regions = regions;
		}

		@Override
		public void serialize(final ITextRegionDiffBuilder result) {
			result.remove(regions.getFirst().getPreviousHiddenRegion(), regions.getLast().getNextHiddenRegion());
		}
	}

}

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
package org.eclipse.fordiac.ide.structuredtextcore.formatting2;

import org.eclipse.xtext.formatting2.IAutowrapFormatter;
import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.xtext.formatting2.IHiddenRegionFormatter;
import org.eclipse.xtext.formatting2.IHiddenRegionFormatting;
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegion;
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegionPart;
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;

public class STCoreAutowrapFormatter implements IAutowrapFormatter {

	private final IHiddenRegion endRegion;
	private boolean indent = true;

	public STCoreAutowrapFormatter(final IHiddenRegion endRegion) {
		this.endRegion = endRegion;
	}

	@Override
	public void format(final ITextSegment region, final IHiddenRegionFormatting wrapped,
			final IFormattableDocument document) {
		if (indent) {
			final IHiddenRegion beginRegion = switch (region) {
			case final IHiddenRegion hiddenRegion -> hiddenRegion;
			case final IHiddenRegionPart hiddenRegionPart -> hiddenRegionPart.getHiddenRegion();
			default -> null;
			};
			document.set(beginRegion, endRegion, IHiddenRegionFormatter::indent);
			indent = false;
		}
	}
}

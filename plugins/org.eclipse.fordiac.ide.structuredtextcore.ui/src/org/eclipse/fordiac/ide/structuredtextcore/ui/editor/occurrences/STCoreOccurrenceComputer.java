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
package org.eclipse.fordiac.ide.structuredtextcore.ui.editor.occurrences;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.xtext.ui.editor.occurrences.DefaultOccurrenceComputer;

public class STCoreOccurrenceComputer extends DefaultOccurrenceComputer {

	@Override
	protected boolean canBeReferencedLocally(final EObject object) {
		return super.canBeReferencedLocally(object) && !(object instanceof STSource);
	}
}

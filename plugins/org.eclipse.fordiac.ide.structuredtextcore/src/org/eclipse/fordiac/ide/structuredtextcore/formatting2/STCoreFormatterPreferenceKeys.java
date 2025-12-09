/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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

import org.eclipse.xtext.formatting2.FormatterPreferenceKeys;
import org.eclipse.xtext.preferences.IntegerKey;

public class STCoreFormatterPreferenceKeys extends FormatterPreferenceKeys {

	/**
	 * The maximum of characters that may fit into one comment line.
	 */
	public static final IntegerKey maxCommentWidth = new IntegerKey("comment.width.max", 100);
}

/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.datatype.helper;

import java.util.List;
import java.util.stream.Stream;

public class RetainHelper {

	static final List<String> tagList = List
			.of(Stream.of(RetainTag.values()).map(RetainTag::getString).toArray(String[]::new));

	public enum RetainTag {
		RETAIN, NON_RETAIN, NOTHING;

		public String getString() {
			return switch (this) {
			case RETAIN -> "RETAIN"; //$NON-NLS-1$
			case NON_RETAIN -> "NON_RETAIN"; //$NON-NLS-1$
			default -> ""; //$NON-NLS-1$
			};
		}

		public static List<String> getTagList() {
			return tagList;
		}

	}

	public static RetainTag deriveTag(final String tag) {
		if (tag == null || tag.isEmpty()) {
			return RetainTag.NOTHING;
		}
		return RetainTag.valueOf(tag);
	}

}

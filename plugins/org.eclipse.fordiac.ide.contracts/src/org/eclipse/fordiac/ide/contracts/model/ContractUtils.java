/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.model;

import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public final class ContractUtils {
	private ContractUtils() {

	}

	static boolean isContractSubapp(final FBNetworkElement element) {
		return element instanceof final SubApp containedSubapp
				&& containedSubapp.getName().startsWith(ContractKeywords.CONTRACT);
	}

	static boolean isInterval(final String[] parts, final int pos, final CharSequence div) {
		return parts[pos].contains(div);
	}

	static String createInterval(final ContractElement source) {
		final StringBuilder interval = new StringBuilder();
		interval.append(ContractKeywords.INTERVAL_OPEN);
		interval.append(source.getMin());
		interval.append(ContractKeywords.INTERVAL_DIVIDER);
		interval.append(source.getMax());
		interval.append(ContractKeywords.INTERVAL_CLOSE);
		return interval.toString();
	}

	static int getStartPosition(final String[] parts, final int posTime) {
		return parts[posTime].length() - ContractKeywords.UNIT_OF_TIME.length();
	}
}

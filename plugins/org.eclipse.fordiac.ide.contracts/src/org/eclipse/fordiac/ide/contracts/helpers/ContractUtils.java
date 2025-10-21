/*******************************************************************************
 * Copyright (c) 2023, 2025 Paul Pavlicek and others
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
 *   Felix Schmid
 *     - simplified by using format strings
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.helpers;

import java.util.List;

import org.eclipse.fordiac.ide.contractSpec.CausalFuncName;
import org.eclipse.fordiac.ide.contractSpec.CausalRelation;

/**
 * Contains helper methods to construct the string representation of contract
 * rules.
 */
@SuppressWarnings("nls") // translating doesn't make sense here
public final class ContractUtils {

	private ContractUtils() {

	}

	public static String createSingleEvent(final List<String> pins, final String timeExpr) {
		return "%s occurs within %s".formatted(createEventList(pins), timeExpr);
	}

	public static String createRepetition(final List<String> pins, final String timeExpr, final String offsetExpr,
			final String jitterExpr) {
		String repetition = "%s occurs every %s".formatted(createEventList(pins), timeExpr);

		final boolean offset = offsetExpr != null && !offsetExpr.isBlank();
		final boolean jitter = jitterExpr != null && !jitterExpr.isBlank();

		if (offset && jitter) {
			repetition += " with offset %s and jitter %s".formatted(offsetExpr, jitterExpr);
		} else if (offset) {
			repetition += " with offset %s".formatted(offsetExpr);
		} else if (jitter) {
			repetition += " with jitter %s".formatted(jitterExpr);
		}
		return repetition;
	}

	public static String createReaction(final List<String> iPins, final List<String> oPins, final boolean inputIsSeq,
			final boolean outputIsSeq, final String timeExpr, final boolean once, final int n, final int outOf) {
		final String reaction = "whenever %s occurs then %s occurs within %s"
				.formatted(createEventExpr(iPins, inputIsSeq), createEventExpr(oPins, outputIsSeq), timeExpr);
		return addReactionProperties(reaction, once, n, outOf);
	}

	public static String createAge(final List<String> iPins, final List<String> oPins, final boolean inputIsSeq,
			final boolean outputIsSeq, final String timeExpr, final boolean once, final int n, final int outOf) {
		final String age = "whenever %s occurs then %s has occurred within %s"
				.formatted(createEventExpr(oPins, outputIsSeq), createEventExpr(iPins, inputIsSeq), timeExpr);
		return addReactionProperties(age, once, n, outOf);
	}

	public static String createCausalReaction(final String iPin, final String oPin, final String timeExpr) {
		return "Reaction(%s, %s) within %s".formatted(iPin, oPin, timeExpr);
	}

	public static String createCausalAge(final String iPin, final String oPin, final String timeExpr) {
		return "Age(%s, %s) within %s".formatted(oPin, iPin, timeExpr);
	}

	public static String createCausalFuncDecl(final CausalFuncName name, final String port1, final String port2,
			final CausalRelation relation) {
		return "%s (%s, %s) := %s".formatted(name, port1, port2, relation);
	}

	private static String createEventList(final List<String> pins) {
		return String.join(", ", pins); //$NON-NLS-1$
	}

	private static String createEventExpr(final List<String> pins, final boolean isSequence) {
		if (pins.size() == 1) {
			return pins.get(0);
		}
		final StringBuilder sb = new StringBuilder();
		sb.append(isSequence ? '(' : '{');
		sb.append(createEventList(pins));
		sb.append(isSequence ? ')' : '}');
		return sb.toString();
	}

	@SuppressWarnings("boxing")
	private static String addReactionProperties(String rule, final boolean once, final int n, final int outOf) {
		if (once) {
			rule += " once";
		}
		if (n != outOf) {
			rule += " %d out of %d times".formatted(n, outOf);
		}
		return rule;
	}
}

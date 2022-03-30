/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber 
 *   	- initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.model;

public class StructMonitoringHelper {
	
	private static final String SEPARATOR = System.lineSeparator();
	private static final String TAB = "\t";
	
	public static String format(final String model) {
		if (model == null || model.length() < 2) {
			throw new IllegalArgumentException();
		}
		
		final StringBuilder builder = new StringBuilder(model);
		int level = -1; // the outer parenthesis will set this to 0
		boolean isStringLiteral = false;
		
		for (int i = 0; i < builder.length() - 1 /* skip last parenthesis */; i++) {			
			final char c = builder.charAt(i);
			
			// check if inside of string literal
			if (c == '\'') {
				isStringLiteral = !isStringLiteral;
				continue;
			}
			
			// not inside of string literal, can insert tabs and linebreaks
			if (!isStringLiteral) {
				if (c == '(') {
					level++;
					addLinebreak(builder, level, i);
				}
				if (c == ')') {
					level--;
				}
				if (c == ',') {
					addLinebreak(builder, level, i);
				}
			}
		}
		
		// separate last (outer) parenthesis from the struct "body"
		builder.insert(builder.length() - 1, System.lineSeparator());
		
		return builder.toString();
	}
	
	private static void addLinebreak(final StringBuilder builder, final int level, final int i) {
		if (i < builder.length()) {
			// level tells us how much tabs we need
			builder.insert(i + 1, createLinebreak(level));
		}
	}
	
	private static String createLinebreak(final int level) {
		final StringBuilder builder = new StringBuilder(SEPARATOR);
		for (int i = 0; i < level; i++) {
			builder.append(TAB);
		}
		return builder.toString();
	}
	
	public static String removeFormatting(final String model) {
		return model.replace(SEPARATOR, "").replace(TAB, "");
	}
	
	private StructMonitoringHelper() {
		super();
	}
	
}

/*******************************************************************************
 * Copyright (c) 2021, 2022 Johannes Kepler University Linz and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmend�a, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *   Paul Pavlicek, Bianca Wiesmayr
 *       - added builder functions
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FBTransactionBuilder {

	private final String inputEventName;
	private final String inputParameter;
	private final List<String> outputEventNames = new ArrayList<>();
	private final List<String> outputParameter = new ArrayList<>();

	public FBTransactionBuilder(final String inputEvent, final String inputParameter, final List<String> outputEvent,
			final List<String> outputParameter) {
		this.inputEventName = inputEvent;
		this.inputParameter = inputParameter;
		this.outputEventNames.addAll(outputEvent);
		this.outputParameter.addAll(outputParameter);
	}

	public FBTransactionBuilder(final String inputEvent, final String outputEvent, final String outputParameter) {
		this(inputEvent, null, Arrays.asList(outputEvent), Arrays.asList(outputParameter));
	}

	public FBTransactionBuilder(final String inputEvent) {
		this(inputEvent, null, Collections.emptyList(), Collections.emptyList());
	}

	public FBTransactionBuilder(final String inputEvent, final String outputEvent) {
		this(inputEvent, outputEvent, ""); //$NON-NLS-1$
	}

	public FBTransactionBuilder(final String inputEvent, final List<String> outputEvents) {
		this(inputEvent, null, outputEvents, Collections.emptyList());
	}

	public FBTransactionBuilder addOutputEvent(final String name) {
		addOutputEvent(name, ""); //$NON-NLS-1$
		return this;
	}

	public FBTransactionBuilder addOutputEvent(final String name, final String parameter) {
		outputEventNames.add(name);
		outputParameter.add(parameter);
		return this;
	}

	public FBTransactionBuilder addOutputEvents(final Iterable<String> names) {
		names.forEach(name -> addOutputEvent(name, "")); //$NON-NLS-1$
		return this;
	}

	public String getInputEventName() {
		return inputEventName;
	}

	public List<String> getOutputEventNames() {
		return outputEventNames;
	}

	public String getOutputEventName(final int index) {
		if (outputEventNames.size() <= index) {
			throw new IllegalArgumentException("Less than " + index + " expected output events"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return outputEventNames.get(index);
	}

	public List<String> getOutputParameters() {
		return outputParameter;
	}

	public String getInputParameter() {
		return inputParameter;
	}

	public static FBTransactionBuilder getSimpleFBTransaction(final String parameters) {
		return new FBTransactionBuilder("REQ", "CNF", parameters); //$NON-NLS-1$//$NON-NLS-2$
	}
}

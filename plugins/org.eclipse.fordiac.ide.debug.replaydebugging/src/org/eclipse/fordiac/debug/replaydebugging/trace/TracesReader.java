/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.debug.replaydebugging.trace;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.tracecompass.ctf.core.CTFException;
import org.eclipse.tracecompass.ctf.core.event.IEventDefinition;
import org.eclipse.tracecompass.ctf.core.event.types.AbstractArrayDefinition;
import org.eclipse.tracecompass.ctf.core.event.types.ICompositeDefinition;
import org.eclipse.tracecompass.ctf.core.event.types.IntegerDefinition;
import org.eclipse.tracecompass.ctf.core.event.types.StringDefinition;
import org.eclipse.tracecompass.ctf.core.trace.CTFTrace;
import org.eclipse.tracecompass.ctf.core.trace.CTFTraceReader;

/**
 * @brief Reads trace files and extracts sendOutputEvent events.
 *
 *        This class is responsible for reading CTF trace files from a specified
 *        path, extracting events of type "sendOutputEvent", and organizing them
 *        in a map where the keys are resource names and the values are lists of
 *        SendOutputEvent objects associated with each resource.
 */
public class TracesReader {

	private final File path;

	// Pattern to match trace filenames, e.g.,
	// "trace_myResource_20240101_123456789.ctf"
	private static final Pattern FILENAME_PATTERN = Pattern.compile("^trace_(.+)_\\d{8}_\\d{9}\\.ctf$"); //$NON-NLS-1$
	private static final String TYPENAME_FIELD = "typeName"; // Field for type name //$NON-NLS-1$
	private static final String INSTANCENAME_FIELD = "instanceName"; // Field for instance name //$NON-NLS-1$
	private static final String EVENTID_FIELD = "eventId"; // Field for event ID //$NON-NLS-1$
	private static final String EVENTCOUNTER_FIELD = "eventCounter"; // Field for event counter //$NON-NLS-1$
	private static final String OUTPUTS_FIELD = "outputs"; // Field for outputs //$NON-NLS-1$
	private static final String SEND_OUTPUT_EVENT = "sendOutputEvent"; // Event type name //$NON-NLS-1$

	public TracesReader(final String path) {
		this.path = new File(path); // Folder containing `metadata` + trace files
	}

	/**
	 * Reads the trace files in the specified path and returns a map of resource
	 * names to lists of SendOutputEvent objects.
	 *
	 * @return A map where keys are resource names and values are lists of
	 *         SendOutputEvent objects.
	 * @throws CTFException If there is an error reading the trace files.
	 */
	public Map<String, List<SendOutputEvent>> read() throws CTFException {

		final Map<String, List<SendOutputEvent>> events = new java.util.HashMap<>();
		final CTFTrace trace = new CTFTrace(path.getAbsolutePath());
		try (final CTFTraceReader reader = new CTFTraceReader(trace)) {
			while (reader.hasMoreEvents()) {
				final IEventDefinition event = reader.getCurrentEventDef();
				final String resourceName = getResourceName(reader.getTopStream().getFilename());
				final List<SendOutputEvent> resourceEvents = events.computeIfAbsent(resourceName,
						_ -> new java.util.ArrayList<>());

				reader.advance();

				final String eventType = event.getDeclaration().getName();
				if (!eventType.equals(SEND_OUTPUT_EVENT)) {
					continue; // Skip events that are not sendOutputEvent
				}

				final ICompositeDefinition fields = event.getFields();

				resourceEvents
						.add(new SendOutputEvent(((StringDefinition) fields.getDefinition(TYPENAME_FIELD)).getValue(),
								((StringDefinition) fields.getDefinition(INSTANCENAME_FIELD)).getValue(),
								(int) ((IntegerDefinition) fields.getDefinition(EVENTID_FIELD)).getValue(),
								(int) ((IntegerDefinition) fields.getDefinition(EVENTCOUNTER_FIELD)).getValue(),
								((AbstractArrayDefinition) fields.getDefinition(OUTPUTS_FIELD)).getDefinitions()
										.stream().map(StringDefinition.class::cast).map(StringDefinition::getValue)
										.toList()));
			}
		}
		return events;
	}

	private static String getResourceName(final String fileName) throws CTFException {
		final Matcher match = FILENAME_PATTERN.matcher(fileName);

		if (!match.find()) {
			throw new CTFException("Filename does not match expected pattern: " + fileName); //$NON-NLS-1$
		}
		return match.group(1);
	}

}
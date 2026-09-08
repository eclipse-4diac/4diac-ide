package org.eclipse.fordiac.ide.fb.interpreter.mm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.fb.interpreter.OpSem.BasicFBTypeRuntime;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.Value;

/**
 * @brief This class serves as a storage for the values of the data, events and
 *        connections of a network runtime. The value of the connections are
 *        stored as data values. It also stores the values of the connections
 *        for easier access when looking for connections with a specific source
 *        or destination.
 */
public class NetworkRuntimeState {

	private record ConnectionInfo(String source, String destination) {
	}

	private static final String CONNECTION_NAME_CONCATENATOR = "!";
	private final Map<String, Value> dataValues = new HashMap<>();
	private final Map<String, Event> events = new HashMap<>();
	private final Map<String, BasicFBTypeRuntime> basicFBs = new HashMap<>();
	private final Map<ConnectionInfo, Value> connectionValues = new HashMap<>();

	public void addDataValue(final String name, final Value value) {
		dataValues.put(name, value);
	}

	public Map<String, Value> getDataValues() {
		return dataValues;
	}

	public void addEvent(final String name, final Event event) {
		events.put(name, event);
	}

	public Map<String, Event> getEvents() {
		return events;
	}

	public void addBasicFBRT(final String name, final BasicFBTypeRuntime basicFBRT) {
		basicFBs.put(name, basicFBRT);
	}

	public Map<String, BasicFBTypeRuntime> getBasicFBRTs() {
		return basicFBs;
	}

	// the connection value is stored in two places. One with a custom name for
	// direct access and one with the connection info for easier access when looking
	// for connections with a specific source or destination
	public void addDataConnectionValue(final String sourceName, final String destinationName, final Value value) {
		final String customName = sourceName + CONNECTION_NAME_CONCATENATOR + destinationName;
		dataValues.put(customName, value);
		final var connectionInfo = new ConnectionInfo(sourceName, destinationName);
		connectionValues.put(connectionInfo, value);
	}

	public List<Value> getConnectionWithSource(final String name) {
		return connectionValues.keySet().stream().filter(conectionInfo -> conectionInfo.source().equals(name))
				.map(connectionValues::get).toList();
	}

	public Value getConnectionsWithDestination(final String name) {
		return connectionValues.keySet().stream().filter(conectionInfo -> conectionInfo.destination().equals(name))
				.map(connectionValues::get).findFirst().orElse(null);
	}

	public Value getConnectionValue(final String sourceName, final String destinationName) {
		final var connectionInfo = new ConnectionInfo(sourceName, destinationName);
		return connectionValues.get(connectionInfo);
	}

}

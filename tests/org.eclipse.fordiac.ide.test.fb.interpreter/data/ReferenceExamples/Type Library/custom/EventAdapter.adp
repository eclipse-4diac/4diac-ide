<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="EventAdapter" Comment="Adapter with only events">
	<Identification Standard="61499-1" Description="Copyright (c) 2023 Johannes Kepler University Linz This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/ SPDX-License-Identifier: EPL-2.0&#10;" >
	</Identification>
	<VersionInfo Organization="Johannes Kepler University Linz" Version="1.0" Author="Bianca Wiesmayr" Date="2023-03-07" Remarks="initial API and implementation and/or initial documentation">
	</VersionInfo>
	<CompilerInfo header="package fb.test;">
	</CompilerInfo>
	<InterfaceList>
		<EventInputs>
			<Event Name="REQ" Type="Event" Comment="">
			</Event>
			<Event Name="RSP" Type="Event" Comment="">
			</Event>
		</EventInputs>
		<EventOutputs>
			<Event Name="CNF" Type="Event" Comment="">
			</Event>
			<Event Name="IND" Type="Event" Comment="">
			</Event>
		</EventOutputs>
	</InterfaceList>
	<Service RightInterface="SOCKET" LeftInterface="PLUG" Comment="Adapter with only events">
		<ServiceSequence Name="request_confirm" Comment="">
			<ServiceTransaction>
				<InputPrimitive Interface="SOCKET" Event="REQ" Parameters="REQD"/>
				<OutputPrimitive Interface="PLUG" Event="REQ" Parameters="REQD"/>
			</ServiceTransaction>
			<ServiceTransaction>
				<InputPrimitive Interface="PLUG" Event="CNF" Parameters="CNFD"/>
				<OutputPrimitive Interface="SOCKET" Event="CNF" Parameters="CNFD"/>
			</ServiceTransaction>
		</ServiceSequence>
		<ServiceSequence Name="indication_response" Comment="">
			<ServiceTransaction>
				<InputPrimitive Interface="PLUG" Event="IND" Parameters="INDD"/>
				<OutputPrimitive Interface="SOCKET" Event="IND" Parameters="INDD"/>
			</ServiceTransaction>
			<ServiceTransaction>
				<InputPrimitive Interface="SOCKET" Event="RSP" Parameters="RSPD"/>
				<OutputPrimitive Interface="PLUG" Event="RSP" Parameters="RSPD"/>
			</ServiceTransaction>
		</ServiceSequence>
	</Service>
</AdapterType>

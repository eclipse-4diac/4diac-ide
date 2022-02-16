<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="PortAdapter" Comment="Adapter Interface">
	<Identification Standard="61499-1" Description="Copyright (c) 2020, 2022 Jonathan Lainer&#10;&#10; This program and the accompanying materials are made&#10;available under the terms of the Eclipse Public License 2.0&#10;which is available at https://www.eclipse.org/legal/epl-2.0/&#10;&#10;SPDX-License-Identifier: EPL-2.0" >
	</Identification>
	<VersionInfo Organization="4DIAC-Consortium" Version="0.1" Author="Jonathan Lainer" Date="2020-03-13" Remarks="Initial Contribution">
	</VersionInfo>
	<CompilerInfo header="package fb.test;">
	</CompilerInfo>
	<InterfaceList>
		<EventInputs>
			<Event Name="MAPO" Type="Event" Comment="Confirm Mapping">
			</Event>
		</EventInputs>
		<EventOutputs>
			<Event Name="MAP" Type="Event" Comment="Request Mapping">
				<With Var="GPIO_Port_Addr"/>
			</Event>
		</EventOutputs>
		<OutputVars>
			<VarDeclaration Name="GPIO_Port_Addr" Type="DWORD" Comment="IOMM Base Address"/>
		</OutputVars>
	</InterfaceList>
	<Service RightInterface="SOCKET" LeftInterface="PLUG" Comment="Adapter Interface">
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

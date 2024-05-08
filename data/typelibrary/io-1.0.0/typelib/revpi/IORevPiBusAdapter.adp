<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="IORevPiBusAdapter" Comment="Bus Adapter for Revolution Pi by KUNBUS GmbH">
	<Identification Standard="61499-2" Description="Copyright (c) 2018 fortiss GmbH&#10;&#10;This program and the accompanying materials are made&#10;available under the terms of the Eclipse Public License 2.0&#10;which is available at https://www.eclipse.org/legal/epl-2.0/&#10;&#10;SPDX-License-Identifier: EPL-2.0" >
	</Identification>
	<VersionInfo Organization="fortiss GmbH" Version="1.0" Author="José Cabral" Date="2018-04-22">
	</VersionInfo>
	<InterfaceList>
		<EventInputs>
			<Event Name="INITO" Type="Event" Comment="Initialization Confirm">
				<With Var="QO"/>
			</Event>
		</EventInputs>
		<EventOutputs>
			<Event Name="INIT" Type="Event" Comment="Service Initialization">
				<With Var="Index"/>
				<With Var="MasterId"/>
				<With Var="QI"/>
			</Event>
		</EventOutputs>
		<InputVars>
			<VarDeclaration Name="QO" Type="BOOL" Comment=""/>
		</InputVars>
		<OutputVars>
			<VarDeclaration Name="QI" Type="BOOL" Comment=""/>
			<VarDeclaration Name="MasterId" Type="UINT" Comment=""/>
			<VarDeclaration Name="Index" Type="UINT" Comment=""/>
		</OutputVars>
	</InterfaceList>
	<Service RightInterface="PLUG" LeftInterface="SOCKET" Comment="Bus Adapter for Revolution Pi by KUNBUS GmbH">
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

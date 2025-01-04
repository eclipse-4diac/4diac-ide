<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="EBBusAdapter">
	<Identification Description="Copyright (c) 2016 fortiss GmbH  &#10; &#10;This program and the accompanying materials are made  &#10;available under the terms of the Eclipse Public License 2.0  &#10;which is available at https://www.eclipse.org/legal/epl-2.0/  &#10; &#10;SPDX-License-Identifier: EPL-2.0  " >
	</Identification>
	<VersionInfo Organization="fortiss GmbH" Version="1.0" Author="Johannes Messmer" Date="2016-11-30">
	</VersionInfo>
	<InterfaceList>
		<EventInputs>
			<Event Name="INITO" Type="EInit" Comment="Initialization Confirm" >
				<With Var="QO"/>
			</Event>
		</EventInputs>
		<EventOutputs>
			<Event Name="INIT" Type="EInit" Comment="Service Initialization" >
				<With Var="Index"/>
				<With Var="UpdateInterval"/>
				<With Var="MasterId"/>
				<With Var="QI"/>
			</Event>
		</EventOutputs>
		<InputVars>
			<VarDeclaration Name="QO" Type="BOOL"/>
		</InputVars>
		<OutputVars>
			<VarDeclaration Name="QI" Type="BOOL"/>
			<VarDeclaration Name="MasterId" Type="UINT"/>
			<VarDeclaration Name="Index" Type="UINT"/>
			<VarDeclaration Name="UpdateInterval" Type="UINT" Comment="Global update interval of slave modules in Hz" />
		</OutputVars>
	</InterfaceList>
	<Service RightInterface="PLUG" LeftInterface="SOCKET">
		<ServiceSequence Name="request_confirm">
			<ServiceTransaction>
				<InputPrimitive Interface="SOCKET" Event="REQ" Parameters="REQD"/>
				<OutputPrimitive Interface="PLUG" Event="REQ" Parameters="REQD"/>
			</ServiceTransaction>
			<ServiceTransaction>
				<InputPrimitive Interface="PLUG" Event="CNF" Parameters="CNFD"/>
				<OutputPrimitive Interface="SOCKET" Event="CNF" Parameters="CNFD"/>
			</ServiceTransaction>
		</ServiceSequence>
		<ServiceSequence Name="indication_response">
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

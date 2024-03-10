<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="PLCnextBusAdapter" Comment="">
	<Identification Standard="61499-2" Description="Copyright (c) 2022 Peirlberger Juergen. This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0. SPDX-License-Identifier: EPL-2.0" >
	</Identification>
	<VersionInfo Version="1.0" Author="Peirlberger Juergen" Date="2022-04-07">
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
				<With Var="UpdateInterval"/>
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
			<VarDeclaration Name="UpdateInterval" Type="UINT" Comment="Global update interval of slave modules in Hz"/>
		</OutputVars>
	</InterfaceList>
	<Service RightInterface="PLUG" LeftInterface="SOCKET" Comment="">
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

<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="ARTimeOut" Comment="Interface for a resetable time out service">
	<Identification Standard="61499-1" Description="Copyright (c) 2017 fortiss GmbH&#10; &#10;This program and the accompanying materials are made&#10;available under the terms of the Eclipse Public License 2.0&#10;which is available at https://www.eclipse.org/legal/epl-2.0/&#10;&#10;SPDX-License-Identifier: EPL-2.0">
	</Identification>
	<VersionInfo Organization="HR Agrartechnik GmbH" Version="3.1" Author="Franz Höpfinger" Date="2026-08-25" Remarks="Fix Service Definition.">
	</VersionInfo>
	<VersionInfo Version="3.0" Author="Patrick Aigner" Date="2025-04-14" Remarks="changed package">
	</VersionInfo>
	<VersionInfo Organization="fortiss GmbH" Version="1.0" Author="Alois Zoitl" Date="2017-09-22" Remarks="initial API and implementation and/or initial documentation">
	</VersionInfo>
	<CompilerInfo packageName="iec61499::events">
	</CompilerInfo>
	<InterfaceList>
		<EventInputs>
			<Event Name="TimeOut" Type="Event" Comment="Request from Socket">
			</Event>
		</EventInputs>
		<EventOutputs>
			<Event Name="START" Type="Event" Comment="start/reset timeout notifcation">
				<With Var="DT"/>
			</Event>
			<Event Name="STOP" Type="Event" Comment="stop timeout notification">
			</Event>
		</EventOutputs>
		<OutputVars>
			<VarDeclaration Name="DT" Type="TIME" Comment="timeout duration"/>
		</OutputVars>
	</InterfaceList>
	<Service RightInterface="PLUG" LeftInterface="SOCKET">
		<ServiceSequence Name="Timeout">
			<ServiceTransaction>
				<InputPrimitive Interface="PLUG" Event="START" Parameters="DT"/>
				<OutputPrimitive Interface="SOCKET" Event="START" Parameters="DT"/>
			</ServiceTransaction>
			<ServiceTransaction>
				<InputPrimitive Interface="SOCKET" Event="TimeOut" Parameters=""/>
				<OutputPrimitive Interface="PLUG" Event="TimeOut"/>
			</ServiceTransaction>
		</ServiceSequence>
		<ServiceSequence Name="NormalOperation">
			<ServiceTransaction>
				<InputPrimitive Interface="PLUG" Event="START" Parameters="DT"/>
				<OutputPrimitive Interface="SOCKET" Event="START" Parameters="DT"/>
			</ServiceTransaction>
			<ServiceTransaction>
				<InputPrimitive Interface="PLUG" Event="STOP" Parameters=""/>
				<OutputPrimitive Interface="SOCKET" Event="STOP" Parameters=""/>
			</ServiceTransaction>
		</ServiceSequence>
	</Service>
	<Attribute Name="eclipse4diac::core::TypeHash" Value="''"/>
</AdapterType>

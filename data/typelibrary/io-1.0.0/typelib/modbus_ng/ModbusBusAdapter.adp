<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="ModbusBusAdapter" Comment="Modbus Bus Adapter">
	<Identification Standard="61499-1" Description="Copyright (c) 2024 Martin Erich Jobst&#10; &#10;This program and the accompanying materials are made&#10;available under the terms of the Eclipse Public License 2.0&#10;which is available at https://www.eclipse.org/legal/epl-2.0/&#10;&#10;SPDX-License-Identifier: EPL-2.0" >
	</Identification>
	<VersionInfo Version="1.0" Author="Martin Erich Jobst" Date="2024-09-12">
	</VersionInfo>
	<CompilerInfo>
	</CompilerInfo>
	<InterfaceList>
		<EventInputs>
			<Event Name="INIT0" Type="Event" Comment="">
				<With Var="QO"/>
			</Event>
		</EventInputs>
		<EventOutputs>
			<Event Name="INIT" Type="Event" Comment="">
				<With Var="QI"/>
				<With Var="MasterId"/>
				<With Var="Index"/>
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
</AdapterType>

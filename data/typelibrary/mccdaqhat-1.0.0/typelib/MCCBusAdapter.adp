<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="MCCBusAdapter" Comment="Bus adapter chaining MCC DAQ HAT modules">
	<Identification Description="Copyright (c) 2026 Thomas Oellinger&#10;&#10;This program and the accompanying materials are made&#10;available under the terms of the Eclipse Public License 2.0&#10;which is available at https://www.eclipse.org/legal/epl-2.0/&#10;&#10;SPDX-License-Identifier: EPL-2.0">
	</Identification>
	<VersionInfo Organization="Thomas Oellinger" Version="1.0" Author="Thomas Oellinger" Date="2026-11-14">
	</VersionInfo>
	<CompilerInfo packageName="eclipse4diac::io::mccdaqhat">
	</CompilerInfo>
	<InterfaceList>
		<EventInputs>
			<Event Name="INITO" Type="EInit" Comment="Initialization Confirm">
				<With Var="QO"/>
			</Event>
		</EventInputs>
		<EventOutputs>
			<Event Name="INIT" Type="EInit" Comment="Service Initialization">
				<With Var="QI"/>
				<With Var="MasterId"/>
				<With Var="Index"/>
			</Event>
		</EventOutputs>
		<InputVars>
			<VarDeclaration Name="QO" Type="BOOL" Comment="Event Output Qualifier"/>
		</InputVars>
		<OutputVars>
			<VarDeclaration Name="QI" Type="BOOL" Comment="Event Input Qualifier"/>
			<VarDeclaration Name="MasterId" Type="UINT" Comment="ID of the IO controller in the chain"/>
			<VarDeclaration Name="Index" Type="UINT" Comment="Position of the IO device in the chain"/>
		</OutputVars>
	</InterfaceList>
</AdapterType>

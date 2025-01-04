<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="WagoBusAdapter">
	<Identification Standard="61499-2" Description="Copyright (c) 2018 Jose Cabral  &#10; &#10;This program and the accompanying materials are made  &#10;available under the terms of the Eclipse Public License 2.0  &#10;which is available at https://www.eclipse.org/legal/epl-2.0/  &#10; &#10;SPDX-License-Identifier: EPL-2.0  " >
	</Identification>
	<VersionInfo Version="1.0" Author="Jose Cabral" Date="2018-12-05">
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
		</OutputVars>
	</InterfaceList>
	<Service RightInterface="PLUG" LeftInterface="SOCKET">
	</Service>
</AdapterType>

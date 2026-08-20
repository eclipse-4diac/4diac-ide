<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="WagoRegCom" Comment="Register communication for Wago modules">
	<Identification Standard="61499-2" Description="Copyright (c) 2026 Monika Wenger&#10;  &#10;This program and the accompanying materials are made &#10;available under the terms of the Eclipse Public License 2.0 &#10;which is available at https://www.eclipse.org/legal/epl-2.0/ &#10; &#10;SPDX-License-Identifier: EPL-2.0">
	</Identification>
	<VersionInfo Version="3.0" Author="Monika Wenger" Date="2026-07-21">
	</VersionInfo>
	<CompilerInfo packageName="eclipse4diac::io::wago">
	</CompilerInfo>
	<InterfaceList>
		<EventInputs>
			<Event Name="Opened" Type="Event" Comment="Confirms opening of register communication">
			</Event>
			<Event Name="CNF" Type="Event" Comment="Confirms that the R/W command has been processed">
				<With Var="counter"/>
				<With Var="rREG_D0"/>
				<With Var="rREG_D1"/>
			</Event>
			<Event Name="Closed" Type="Event" Comment="Confirms closing of register communication">
			</Event>
		</EventInputs>
		<EventOutputs>
			<Event Name="Open" Type="Event" Comment="Open register communication">
			</Event>
			<Event Name="Read" Type="Event" Comment="Read register with RegNr">
				<With Var="cmd"/>
			</Event>
			<Event Name="Write" Type="Event" Comment="Write register with RegNr">
				<With Var="cmd"/>
			</Event>
			<Event Name="Close" Type="Event" Comment="Close register communication">
			</Event>
		</EventOutputs>
		<InputVars>
			<VarDeclaration Name="counter" Type="USINT" Comment="Counts the commands processed"/>
			<VarDeclaration Name="rREG_D0" Type="BYTE" Comment="LSB data read from the register RegNr"/>
			<VarDeclaration Name="rREG_D1" Type="BYTE" Comment="MSB data read from the register RegNr"/>
		</InputVars>
		<OutputVars>
			<VarDeclaration Name="cmd" Type="eclipse4diac::io::wago::RegComCmd" Comment="Command for register communication"/>
		</OutputVars>
	</InterfaceList>
	<Attribute Name="eclipse4diac::core::TypeHash" Value=""/>
</AdapterType>

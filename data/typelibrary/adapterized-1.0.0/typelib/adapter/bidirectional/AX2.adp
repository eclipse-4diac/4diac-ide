<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="AX2" Comment="bidirectional Adapter Interface for 1 Event and 1 Bool" >
	<Identification Standard="61499-1" Description="Copyright (c) 2025 HR Agrartechnik GmbH  &#10; &#10;This program and the accompanying materials are made  &#10;available under the terms of the Eclipse Public License 2.0  &#10;which is available at https://www.eclipse.org/legal/epl-2.0/  &#10; &#10;SPDX-License-Identifier: EPL-2.0" >
	</Identification>
	<VersionInfo Organization="HR Agrartechnik GmbH" Version="1.0" Author="Franz Höpfinger" Date="2025-01-24" Remarks="Initial Version">
	</VersionInfo>
	<CompilerInfo>
	</CompilerInfo>
	<InterfaceList>
		<EventInputs>
			<Event Name="EI1" Type="Event" Comment="Request (or Indication)" >
				<With Var="DI1"/>
			</Event>
		</EventInputs>
		<EventOutputs>
			<Event Name="EO1" Type="Event" Comment="Indication (or Request)" >
				<With Var="DO1"/>
			</Event>
		</EventOutputs>
		<InputVars>
			<VarDeclaration Name="DI1" Type="BOOL" Comment="Request (or Indication) to Socket" />
		</InputVars>
		<OutputVars>
			<VarDeclaration Name="DO1" Type="BOOL" Comment="Indication (or Request) Data from Plug" />
		</OutputVars>
	</InterfaceList>
</AdapterType>

<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="CompoundAdapterWith" Comment="Adapter with events and data, not all data WITHed">
	<Identification Standard="61499-1" Description="Copyright (c) 2023 Johannes Kepler University Linz This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0/ SPDX-License-Identifier: EPL-2.0&#10;" >
	</Identification>
	<VersionInfo Organization="Johannes Kepler University Linz" Version="1.0" Author="Bianca Wiesmayr" Date="2023-03-08" Remarks="initial API and implementation and/or initial documentation">
	</VersionInfo>
	<InterfaceList>
		<EventInputs>
			<Event Name="REQ" Type="Event" Comment="Request from Socket">
				<With Var="DI1"/>
			</Event>
		</EventInputs>
		<EventOutputs>
			<Event Name="CNF" Type="Event" Comment="Confirmation from Plug">
				<With Var="DO1"/>
			</Event>
		</EventOutputs>
		<InputVars>
			<VarDeclaration Name="DI1" Type="INT" Comment="" InitialValue="42"/>
			<VarDeclaration Name="DI2" Type="BOOL" Comment="" InitialValue="TRUE"/>
		</InputVars>
		<OutputVars>
			<VarDeclaration Name="DO1" Type="INT" Comment="" InitialValue="21"/>
			<VarDeclaration Name="DO2" Type="BOOL" Comment=""/>
		</OutputVars>
	</InterfaceList>
</AdapterType>

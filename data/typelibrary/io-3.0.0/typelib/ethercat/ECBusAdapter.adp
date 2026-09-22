<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="ECBusAdapter" Comment="Adapter Interface">
	<Identification Standard="61499-1" Description="Copyright (c) 2026 Sichuan Qunyuan Technology Co., Ltd.&#10; &#10;This program and the accompanying materials are made&#10;available under the terms of the Eclipse Public License 2.0&#10;which is available at https://www.eclipse.org/legal/epl-2.0/&#10;&#10;SPDX-License-Identifier: EPL-2.0">
	</Identification>
	<VersionInfo Version="1.0" Author="Zijun Tang" Date="2026-04-09">
	</VersionInfo>
	<CompilerInfo packageName="eclipse4diac::io::ethercat">
	</CompilerInfo>
	<InterfaceList>
		<EventInputs>
			<Event Name="INITO" Type="EInit" Comment="Initialization Confirm">
			</Event>
		</EventInputs>
		<EventOutputs>
			<Event Name="INIT" Type="EInit" Comment="Service Initialization">
			</Event>
		</EventOutputs>
		<InputVars>
			<VarDeclaration Name="QO" Type="BOOL"/>
		</InputVars>
		<OutputVars>
			<VarDeclaration Name="QI" Type="BOOL"/>
			<VarDeclaration Name="ControllerId" Type="UINT"/>
			<VarDeclaration Name="Index" Type="UINT"/>
		</OutputVars>
	</InterfaceList>
</AdapterType>

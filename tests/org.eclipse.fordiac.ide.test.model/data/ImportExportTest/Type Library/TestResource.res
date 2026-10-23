<?xml version="1.0" encoding="UTF-8"?>
<ResourceType Name="TestResource" Comment="Resource type with a basic FB">
	<Identification Standard="61499-1" Description="Copyright (c) 2026 Martin Erich Jobst&#10; &#10;This program and the accompanying materials are made&#10;available under the terms of the Eclipse Public License 2.0&#10;which is available at https://www.eclipse.org/legal/epl-2.0/&#10;&#10;SPDX-License-Identifier: EPL-2.0">
	</Identification>
	<VersionInfo Version="1.0" Author="Martin Jobst" Date="2026-10-23">
	</VersionInfo>
	<CompilerInfo>
	</CompilerInfo>
	<VarDeclaration Name="RESOURCE_ID" Type="DINT" InitialValue="DINT#2"/>
	<FBNetwork>
		<FB Name="Processor" Type="fixture::basic::BasicTest" x="0" y="0">
			<Attribute Name="IntegerAttribute" Value="13"/>
			<Parameter Name="DI1" Value="DINT#3"/>
		</FB>
		<FB Name="Controller" Type="CompositeTest" x="200" y="0">
		</FB>
		<SubApp Name="TypedSubApp" Type="TestSubApp" Comment="Configured typed subapplication" x="400" y="100">
			<Parameter Name="DI1" Value="DINT#8"/>
		</SubApp>
	</FBNetwork>
	<Attribute Name="IntegerAttribute" Value="14"/>
</ResourceType>

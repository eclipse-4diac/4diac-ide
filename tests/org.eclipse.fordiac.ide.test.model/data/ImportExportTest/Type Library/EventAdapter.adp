<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="EventAdapter" Comment="Adapter with event and data interfaces">
	<Identification Standard="61499-1">
	</Identification>
	<VersionInfo Version="1.0" Author="mjobst" Date="2026-10-23">
	</VersionInfo>
	<CompilerInfo>
	</CompilerInfo>
	<InterfaceList>
		<EventInputs>
			<Event Name="REQ" Type="Event">
				<With Var="REQD"/>
			</Event>
			<Event Name="RSP" Type="Event">
				<With Var="RSPD"/>
			</Event>
		</EventInputs>
		<EventOutputs>
			<Event Name="CNF" Type="Event">
				<With Var="CNFD"/>
			</Event>
			<Event Name="IND" Type="Event">
				<With Var="INDD"/>
			</Event>
		</EventOutputs>
		<InputVars>
			<VarDeclaration Name="REQD" Type="WSTRING" Comment="Request data"/>
			<VarDeclaration Name="RSPD" Type="WSTRING" Comment="Response data"/>
		</InputVars>
		<OutputVars>
			<VarDeclaration Name="CNFD" Type="WSTRING" Comment="Confirmation data"/>
			<VarDeclaration Name="INDD" Type="WSTRING" Comment="Indication data"/>
		</OutputVars>
	</InterfaceList>
	<Service RightInterface="SOCKET" LeftInterface="PLUG" Comment="Adapter with only events">
		<ServiceSequence Name="request_confirm">
			<ServiceTransaction>
				<InputPrimitive Interface="SOCKET" Event="REQ" Parameters="REQD"/>
				<OutputPrimitive Interface="PLUG" Event="REQ" Parameters="REQD"/>
			</ServiceTransaction>
			<ServiceTransaction>
				<InputPrimitive Interface="PLUG" Event="CNF" Parameters="CNFD"/>
				<OutputPrimitive Interface="SOCKET" Event="CNF" Parameters="CNFD"/>
			</ServiceTransaction>
		</ServiceSequence>
		<ServiceSequence Name="indication_response">
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
	<Attribute Name="IntegerAttribute" Value="4"/>
</AdapterType>

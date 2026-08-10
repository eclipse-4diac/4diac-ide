<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="ECBusAdapter" Comment="Adapter Interface">
	<Identification Standard="61499-1">
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
	<Service RightInterface="SOCKET" LeftInterface="PLUG">
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
</AdapterType>

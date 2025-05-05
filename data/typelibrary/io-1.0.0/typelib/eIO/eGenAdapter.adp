<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="eGenAdapter" Comment="Adapter Interface">
	<Identification Standard="61499-1">
	</Identification>
	<VersionInfo Version="1.0" Author="Maximilian Scharf" Date="2025-05-05">
	</VersionInfo>
	<CompilerInfo>
	</CompilerInfo>
	<InterfaceList>
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

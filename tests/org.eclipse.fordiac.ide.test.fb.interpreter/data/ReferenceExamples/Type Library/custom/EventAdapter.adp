<?xml version="1.0" encoding="UTF-8"?>
<AdapterType Name="EventAdapter" Comment="">
	<Identification Standard="61499-1">
	</Identification>
	<VersionInfo Version="1.0" Author="AK115394" Date="2023-03-07">
	</VersionInfo>
	<CompilerInfo header="package fb.test;">
	</CompilerInfo>
	<InterfaceList>
		<EventInputs>
			<Event Name="REQ" Type="Event" Comment="">
			</Event>
			<Event Name="RSP" Type="Event" Comment="">
			</Event>
		</EventInputs>
		<EventOutputs>
			<Event Name="CNF" Type="Event" Comment="">
			</Event>
			<Event Name="IND" Type="Event" Comment="">
			</Event>
		</EventOutputs>
	</InterfaceList>
	<Service RightInterface="SOCKET" LeftInterface="PLUG" Comment="">
		<ServiceSequence Name="request_confirm" Comment="">
			<ServiceTransaction>
				<InputPrimitive Interface="SOCKET" Event="REQ" Parameters="REQD"/>
				<OutputPrimitive Interface="PLUG" Event="REQ" Parameters="REQD"/>
			</ServiceTransaction>
			<ServiceTransaction>
				<InputPrimitive Interface="PLUG" Event="CNF" Parameters="CNFD"/>
				<OutputPrimitive Interface="SOCKET" Event="CNF" Parameters="CNFD"/>
			</ServiceTransaction>
		</ServiceSequence>
		<ServiceSequence Name="indication_response" Comment="">
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

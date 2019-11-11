<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE AdapterType SYSTEM "http://www.holobloc.com/xml/LibraryElement.dtd">
<AdapterType Comment="Publish event adapter" Name="ArrowheadPublishAdp">
  <Identification Description="Copyright (c) 2019 fortiss GmbH&#13;&#10; &#13;&#10;This program and the accompanying materials are made&#13;&#10;available under the terms of the Eclipse Public License 2.0&#13;&#10;which is available at https://www.eclipse.org/legal/epl-2.0/&#13;&#10;&#13;&#10;SPDX-License-Identifier: EPL-2.0" Standard="61499-2"/>
  <VersionInfo Author="Cabral" Date="2019-09-17" Organization="fortiss GmbH" Version="1.0"/>
  <CompilerInfo header="package fb.test;"/>
  <InterfaceList>
    <EventInputs>
      <Event Comment="Publish finished" Name="published" Type="Event"/>
    </EventInputs>
    <EventOutputs>
      <Event Comment="Publish event" Name="publish" Type="Event">
        <With Var="publishEvent"/>
        <With Var="endpoint"/>
      </Event>
    </EventOutputs>
    <OutputVars>
      <VarDeclaration Comment="Publish event to be sent" Name="publishEvent" Type="PublishEvent"/>
      <VarDeclaration Comment="Endpoint (IP:PORT/URI) of the Event Handler (not officially in the documentation, but it simplifies the FBs)" Name="endpoint" Type="WSTRING"/>
    </OutputVars>
  </InterfaceList>
  <Service Comment="Publish event adapter" LeftInterface="SOCKET" RightInterface="PLUG">
    <ServiceSequence Comment="" Name="request_confirm">
      <ServiceTransaction>
        <InputPrimitive Event="REQ" Interface="SOCKET" Parameters="REQD"/>
        <OutputPrimitive Event="REQ" Interface="PLUG" Parameters="REQD"/>
      </ServiceTransaction>
      <ServiceTransaction>
        <InputPrimitive Event="CNF" Interface="PLUG" Parameters="CNFD"/>
        <OutputPrimitive Event="CNF" Interface="SOCKET" Parameters="CNFD"/>
      </ServiceTransaction>
    </ServiceSequence>
    <ServiceSequence Comment="" Name="indication_response">
      <ServiceTransaction>
        <InputPrimitive Event="IND" Interface="PLUG" Parameters="INDD"/>
        <OutputPrimitive Event="IND" Interface="SOCKET" Parameters="INDD"/>
      </ServiceTransaction>
      <ServiceTransaction>
        <InputPrimitive Event="RSP" Interface="SOCKET" Parameters="RSPD"/>
        <OutputPrimitive Event="RSP" Interface="PLUG" Parameters="RSPD"/>
      </ServiceTransaction>
    </ServiceSequence>
  </Service>
</AdapterType>

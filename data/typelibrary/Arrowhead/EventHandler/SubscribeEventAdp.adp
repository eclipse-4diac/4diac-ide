<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE AdapterType SYSTEM "http://www.holobloc.com/xml/LibraryElement.dtd">
<AdapterType Comment="Subscribe event adapter" Name="SubscribeEventAdp">
  <Identification Description="Copyright (c) 2019 fortiss GmbH&#13;&#10; &#13;&#10;This program and the accompanying materials are made&#13;&#10;available under the terms of the Eclipse Public License 2.0&#13;&#10;which is available at https://www.eclipse.org/legal/epl-2.0/&#13;&#10;&#13;&#10;SPDX-License-Identifier: EPL-2.0" Standard="61499-2"/>
  <VersionInfo Author="Cabral" Date="2019-09-18" Organization="fortiss GmbH" Version="1.0"/>
  <CompilerInfo header="package fb.test;"/>
  <InterfaceList>
    <EventInputs>
      <Event Comment="Subscribe finished" Name="Subscribed" Type="Event"/>
      <Event Comment="Unsubscribe finished" Name="Unsubscribed" Type="Event"/>
    </EventInputs>
    <EventOutputs>
      <Event Comment="Subscribe to event" Name="Subscribe" Type="Event">
        <With Var="eventFilter"/>
        <With Var="endpoint"/>
      </Event>
      <Event Comment="Unsubscribe from event" Name="Unsubscribe" Type="Event">
        <With Var="endpoint"/>
        <With Var="eventFilter"/>
      </Event>
    </EventOutputs>
    <OutputVars>
      <VarDeclaration Comment="Event filter " Name="eventFilter" Type="EventFilter"/>
      <VarDeclaration Comment="Endpoint (IP:PORT/URI) of the Service Registry (not officially in the documentation, but it simplifies the FBs)" Name="endpoint" Type="WSTRING"/>
    </OutputVars>
  </InterfaceList>
  <Service Comment="Subscribe event adapter" LeftInterface="SOCKET" RightInterface="PLUG">
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

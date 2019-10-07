<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE AdapterType SYSTEM "http://www.holobloc.com/xml/LibraryElement.dtd">
<AdapterType Comment="Request orchestration adapter" Name="OrchestratorRequestAdp">
  <Identification Description="Copyright (c) 2018 fortiss GmbH&#13;&#10; &#13;&#10;This program and the accompanying materials are made&#13;&#10;available under the terms of the Eclipse Public License 2.0&#13;&#10;which is available at https://www.eclipse.org/legal/epl-2.0/&#13;&#10;&#13;&#10;SPDX-License-Identifier: EPL-2.0" Standard="61499-2"/>
  <VersionInfo Author="Jose Cabral" Date="2018-09-21" Organization="fortiss GmbH" Version="1.0"/>
  <InterfaceList>
    <EventInputs>
      <Event Comment="A response was received from the orchestrator" Name="responseReceived" Type="Event">
        <With Var="orchestrationResponse"/>
      </Event>
    </EventInputs>
    <EventOutputs>
      <Event Comment="Request the orchestrator" Name="requestOrchestator" Type="Event">
        <With Var="serviceRequestForm"/>
        <With Var="endpoint"/>
      </Event>
    </EventOutputs>
    <InputVars>
      <VarDeclaration ArraySize="10" Comment="Response from the orchestrator" Name="orchestrationResponse" Type="OrchestrationForm"/>
    </InputVars>
    <OutputVars>
      <VarDeclaration Comment="Service request form to be sent to the orchestrator" Name="serviceRequestForm" Type="ServiceRequestForm"/>
      <VarDeclaration Comment="Endpoint (IP:PORT/URI) of the orchestrator" Name="endpoint" Type="WSTRING"/>
    </OutputVars>
  </InterfaceList>
  <Service Comment="Request orchestration adapter" LeftInterface="SOCKET" RightInterface="PLUG"/>
</AdapterType>

<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE AdapterType SYSTEM "http://www.holobloc.com/xml/LibraryElement.dtd">
<AdapterType Comment="Query services adapter" Name="QueryServicesAdp">
  <Identification Description="Copyright (c) 2018 fortiss GmbH&#13;&#10; &#13;&#10;This program and the accompanying materials are made&#13;&#10;available under the terms of the Eclipse Public License 2.0&#13;&#10;which is available at https://www.eclipse.org/legal/epl-2.0/&#13;&#10;&#13;&#10;SPDX-License-Identifier: EPL-2.0" Standard="61499-2"/>
  <VersionInfo Author="Jose Cabral" Date="2018-09-21" Organization="fortiss GmbH" Version="1.0"/>
  <InterfaceList>
    <EventInputs>
      <Event Comment="Query finished" Name="queried" Type="Event">
        <With Var="serviceEntries"/>
      </Event>
    </EventInputs>
    <EventOutputs>
      <Event Comment="Query for services" Name="query" Type="Event">
        <With Var="serviceQueryForm"/>
        <With Var="endpoint"/>
      </Event>
    </EventOutputs>
    <InputVars>
      <VarDeclaration ArraySize="10" Comment="Result of the query request" Name="serviceEntries" Type="ServiceRegistryEntry"/>
    </InputVars>
    <OutputVars>
      <VarDeclaration Comment="The Arrowhead Service object that is looked for (SD and supported IDD-s)" Name="serviceQueryForm" Type="ServiceQueryForm"/>
      <VarDeclaration Comment="Endpoint (IP:PORT/URI) of the Service Registry (not officially in the documentation, but it simplifies the FBs)" Name="endpoint" Type="WSTRING"/>
    </OutputVars>
  </InterfaceList>
  <Service Comment="Query services adapter" LeftInterface="SOCKET" RightInterface="PLUG"/>
</AdapterType>

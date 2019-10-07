<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE AdapterType SYSTEM "http://www.holobloc.com/xml/LibraryElement.dtd">
<AdapterType Comment="Register Service Adapter" Name="RegisterServiceAdp">
  <Identification Description="Copyright (c) 2018 fortiss GmbH&#13;&#10; &#13;&#10;This program and the accompanying materials are made&#13;&#10;available under the terms of the Eclipse Public License 2.0&#13;&#10;which is available at https://www.eclipse.org/legal/epl-2.0/&#13;&#10;&#13;&#10;SPDX-License-Identifier: EPL-2.0" Standard="61499-2"/>
  <VersionInfo Author="Jose Cabral" Date="2018-09-21" Organization="fortiss GmbH" Version="1.0"/>
  <InterfaceList>
    <EventInputs>
      <Event Comment="Register finished" Name="doneRegister" Type="Event"/>
      <Event Comment="Unregister finished" Name="doneUnregister" Type="Event"/>
    </EventInputs>
    <EventOutputs>
      <Event Comment="Register the services" Name="registerService" Type="Event">
        <With Var="serviceRegistryEntry"/>
        <With Var="endpoint"/>
      </Event>
      <Event Comment="Unregister the service" Name="unregisterService" Type="Event">
        <With Var="serviceRegistryEntry"/>
        <With Var="endpoint"/>
      </Event>
    </EventOutputs>
    <InputVars/>
    <OutputVars>
      <VarDeclaration Comment="Service Registry Entry for register or unregister" Name="serviceRegistryEntry" Type="ServiceRegistryEntry"/>
      <VarDeclaration Comment="Endpoint (IP:PORT/URI) of the Service Registry (not officially in the documentation, but it simplifies the FBs)" Name="endpoint" Type="WSTRING"/>
    </OutputVars>
  </InterfaceList>
  <Service Comment="Register Service Adapter" LeftInterface="SOCKET" RightInterface="PLUG"/>
</AdapterType>

<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE AdapterType SYSTEM "http://www.holobloc.com/xml/LibraryElement.dtd">
<AdapterType Name="WagoBusAdapter">
  <Identification Description="Copyright (c) 2018 fortiss GmbH&#13;&#10; &#13;&#10;This program and the accompanying materials are made&#13;&#10;available under the terms of the Eclipse Public License 2.0&#13;&#10;which is available at https://www.eclipse.org/legal/epl-2.0/&#13;&#10;&#13;&#10;SPDX-License-Identifier: EPL-2.0" Standard="61499-2"/>
  <VersionInfo Author="Jose Cabral" Date="2018-12-05" Version="1.0"/>
  <InterfaceList>
    <EventInputs>
      <Event Comment="Initialization Confirm" Name="INITO" Type="Event">
        <With Var="QO"/>
      </Event>
    </EventInputs>
    <EventOutputs>
      <Event Comment="Service Initialization" Name="INIT" Type="Event">
        <With Var="Index"/>
        <With Var="MasterId"/>
        <With Var="QI"/>
      </Event>
    </EventOutputs>
    <InputVars>
      <VarDeclaration Name="QO" Type="BOOL"/>
    </InputVars>
    <OutputVars>
      <VarDeclaration Name="QI" Type="BOOL"/>
      <VarDeclaration Name="MasterId" Type="UINT"/>
      <VarDeclaration Name="Index" Type="UINT"/>
    </OutputVars>
  </InterfaceList>
  <Service LeftInterface="SOCKET" RightInterface="PLUG"/>
</AdapterType>

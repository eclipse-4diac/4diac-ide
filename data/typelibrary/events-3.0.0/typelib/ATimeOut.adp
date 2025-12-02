<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<AdapterType Comment="Interface for a time out service roughly based on the definitions of ROOM" Name="ATimeOut">
  <Identification Description="Copyright (c) 2017 fortiss GmbH&#13;&#10; &#13;&#10;This program and the accompanying materials are made&#13;&#10;available under the terms of the Eclipse Public License 2.0&#13;&#10;which is available at https://www.eclipse.org/legal/epl-2.0/&#13;&#10;&#13;&#10;SPDX-License-Identifier: EPL-2.0" Standard="61499-1"/>
	<VersionInfo Version="3.0" Author="Patrick Aigner" Date="2025-04-14" Remarks="changed package">
	</VersionInfo>
  <VersionInfo Author="Alois Zoitl" Date="2017-09-22" Organization="fortiss GmbH" Remarks="initial API and implementation and/or initial documentation" Version="1.0"/>
	<CompilerInfo packageName="iec61499::events">
	</CompilerInfo>
  <InterfaceList>
    <EventInputs>
      <Event Comment="Request from Socket" Name="TimeOut" Type="Event"/>
    </EventInputs>
    <EventOutputs>
      <Event Comment="start timeout notifcation" Name="START" Type="Event">
        <With Var="DT"/>
      </Event>
      <Event Comment="stop timeout notification" Name="STOP" Type="Event"/>
    </EventOutputs>
    <InputVars/>
    <OutputVars>
      <VarDeclaration Comment="timeout duration" InitialValue="" Name="DT" Type="TIME"/>
    </OutputVars>
  </InterfaceList>
  <Service Comment="Interface for a time out service roughly based on the definitions of ROOM" LeftInterface="SOCKET" RightInterface="PLUG">
    <ServiceSequence Comment="" Name="Timeout">
      <ServiceTransaction>
        <InputPrimitive Event="START" Interface="PLUG" Parameters="TD"/>
        <OutputPrimitive Event="START" Interface="SOCKET" Parameters="TD"/>
      </ServiceTransaction>
      <ServiceTransaction>
        <InputPrimitive Event="TimeOut" Interface="SOCKET" Parameters=""/>
        <OutputPrimitive Event="TimeOut" Interface="PLUG"/>
      </ServiceTransaction>
    </ServiceSequence>
    <ServiceSequence Comment="" Name="NormalOperation">
      <ServiceTransaction>
        <InputPrimitive Event="Start" Interface="PLUG" Parameters="TD"/>
        <OutputPrimitive Event="Start" Interface="SOCKET" Parameters="TD"/>
      </ServiceTransaction>
      <ServiceTransaction>
        <InputPrimitive Event="STOP" Interface="PLUG" Parameters=""/>
        <OutputPrimitive Event="STOP" Interface="SOCKET" Parameters=""/>
      </ServiceTransaction>
    </ServiceSequence>
  </Service>
	<Attribute Name="eclipse4diac::core::TypeHash" Value="''"/>
</AdapterType>

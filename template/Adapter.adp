<?xml version="1.0" encoding="UTF-8"?>
<!-- 
/*******************************************************************************
 * Copyright (c) 2010 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
 -->
<!DOCTYPE AdapterType SYSTEM "http://www.holobloc.com/xml/LibraryElement.dtd" >
<AdapterType Name="Adapter" Comment="Adapter Interface" >
  <Identification Standard="61499-1" />
  <VersionInfo Organization="4DIAC-Consortium" Version="0.0" Author="GE" Date="2008-04-22" />
  <CompilerInfo header="package fb.test;" >
  </CompilerInfo>
  <InterfaceList>
    <EventInputs>
      <Event Name="REQ" Comment="Request from Socket" >
        <With Var="REQD" />
      </Event>
      <Event Name="RSP" Comment="Response from Socket" >
        <With Var="RSPD" />
      </Event>
    </EventInputs>
    <EventOutputs>
      <Event Name="CNF" Comment="Confirmation from Plug" >
        <With Var="CNFD" />
      </Event>
      <Event Name="IND" Comment="Indication from Plug" >
        <With Var="INDD" />
      </Event>
    </EventOutputs>
    <InputVars>
      <VarDeclaration Name="REQD" Type="WSTRING" Comment="Request Data from Socket" />
      <VarDeclaration Name="RSPD" Type="WSTRING" Comment="Response Data from Socket" />
    </InputVars>
    <OutputVars>
      <VarDeclaration Name="CNFD" Type="WSTRING" Comment="Confirmation Data from Plug" />
      <VarDeclaration Name="INDD" Type="WSTRING" Comment="Indication Data from Plug" />
    </OutputVars>
  </InterfaceList>
  <Service RightInterface="PLUG" LeftInterface="SOCKET" >
    <ServiceSequence Name="request_confirm" >
      <ServiceTransaction >
        <InputPrimitive Interface="SOCKET" Event="REQ" Parameters="REQD" />
        <OutputPrimitive Interface="PLUG" Event="REQ" Parameters="REQD" />
      </ServiceTransaction>
      <ServiceTransaction >
        <InputPrimitive Interface="PLUG" Event="CNF" Parameters="CNFD" />
        <OutputPrimitive Interface="SOCKET" Event="CNF" Parameters="CNFD" />
      </ServiceTransaction>
    </ServiceSequence>
    <ServiceSequence Name="indication_response" >
      <ServiceTransaction >
        <InputPrimitive Interface="PLUG" Event="IND" Parameters="INDD" />
        <OutputPrimitive Interface="SOCKET" Event="IND" Parameters="INDD" />
      </ServiceTransaction>
      <ServiceTransaction >
        <InputPrimitive Interface="SOCKET" Event="RSP" Parameters="RSPD" />
        <OutputPrimitive Interface="PLUG" Event="RSP" Parameters="RSPD" />
      </ServiceTransaction>
    </ServiceSequence>
  </Service>
</AdapterType>

<?xml version="1.0" encoding="UTF-8"?>
<!-- 
/*******************************************************************************
 * Copyright (c) 2010 Profactor GmbH
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright notice, 
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.  
 *   - Neither the name of the Eclipse Foundation, Inc. nor the names of its 
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
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

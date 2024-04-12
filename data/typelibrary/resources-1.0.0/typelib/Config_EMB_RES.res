<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE FBType SYSTEM "http://www.holobloc.com/xml/LibraryElement.dtd">
<ResourceType Comment="Basic resource with configuration inputs" Name="Config_EMB_RES">
  <Identification Description="Copyright (c) 2024 Primetals Technologies Austria GmbH&#13;&#10; &#13;&#10;This program and the accompanying materials are made&#13;&#10;available under the terms of the Eclipse Public License 2.0&#13;&#10;which is available at https://www.eclipse.org/legal/epl-2.0/&#13;&#10;&#13;&#10;SPDX-License-Identifier: EPL-2.0"
    Function="This resource is based on the basic resource for executing FB networks with the additional functionality of configuration data inputs. It already contains an instance of the E_RESTART FB providing events for starting up and shutting down an application.&#13;&#10;&#13;&#10;This resource is based on examples found in the different parts of IEC 61499 and the documentation at http://www.holobloc.com/doc/fb/rt/EMB_RES.htm"/>
  <VersionInfo Author="Markus Meingast" Date="2024-04-02" Organization="Primetals Technologies Austria GmbH" Version="1.0"/>
  <VarDeclaration Name="OPCUA_Namespace" Type="WSTRING" InitialValue="" Comment="OPC UA Namespace" />
  <FBNetwork>
    <FB Comment="" Name="START" Type="E_RESTART" x="100.0" y="0.0"/>
  </FBNetwork>
</ResourceType>
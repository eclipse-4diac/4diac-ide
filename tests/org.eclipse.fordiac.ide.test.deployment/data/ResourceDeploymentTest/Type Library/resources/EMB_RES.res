<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE FBType SYSTEM "http://www.holobloc.com/xml/LibraryElement.dtd">
<ResourceType Comment="Most basic resource for executing FB networks" Name="EMB_RES">
  <Identification Description="Copyright (c) 2017 fortiss GmbH&#13;&#10; &#13;&#10;This program and the accompanying materials are made&#13;&#10;available under the terms of the Eclipse Public License 2.0&#13;&#10;which is available at https://www.eclipse.org/legal/epl-2.0/&#13;&#10;&#13;&#10;SPDX-License-Identifier: EPL-2.0"
    Function="This resource provides the most basic functionality of an IEC 61499 resource, namely executing FB networks. For convenience it already contains an instance of the E_RESTART FB providing events for starting up and shutting down an application.&#13;&#10;&#13;&#10;This resource is based on examples found in the different parts of IEC 61499 and the documentation at http://www.holobloc.com/doc/fb/rt/EMB_RES.htm"/>
  <VersionInfo Author="Alois Zoitl" Date="2017-12-02" Organization="fortiss GmbH" Version="1.0"/>
  <FBNetwork>
    <FB Comment="" Name="START" Type="E_RESTART" x="100.0" y="0.0"/>
  </FBNetwork>
</ResourceType>

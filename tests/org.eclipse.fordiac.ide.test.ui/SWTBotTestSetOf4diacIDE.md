<!--
Copyright (c) 2023 Andrea Zoitl
  
 This program and the accompanying materials are made available under the
 terms of the Eclipse Public License 2.0 which is available at
 http://www.eclipse.org/legal/epl-2.0.

 SPDX-License-Identifier: EPL-2.0
 
 Contributors:
   Andrea Zoitl - initial API and implementation and/or initial documentation
-->

| Test group   | Test name                                      | FB           | Description - tests if...                                                  | Status  |
|--------------|------------------------------------------------|--------------|----------------------------------------------------------------------------|---------|
| new System   | menuNew4diacIDEProject                         |              | menu „New 4diac Project“ exists                                            | done    |
|              | createANew4diacIDEProject                      |              | a new 4diac IDE project can be created                                     | done    |
|              | TryToCreateANew4diacIDEProjectWithExistingName |              | try to create a new project with existing name (should not be able)        | done    |
|              | deleteExisting4diacIDEProject                  |              | delete existing 4diac project                                              | done    | 
|              | isProjectNotInSystemExplorerAfterDeletion      |              | is project not visible in system explorer after deletion                   | done    | 
|              |                                                |              |                                                                            |         |
|              |                                                |              |                                                                            |         |
| 1 FB         | dragAndDrop1FB                                 | E_CYCLE      | is it possible to drag and drop FB into editing area                       | done    |
|              | isAddedFbInProjectAppNode                      | "            | is FB visible in System Explorer unter App node                            | done    |
|              | deleteExistingFB                               | "            | an existing FB can be deleted                                              | done    | 
|              | moveFB                                         | "            | an existing FB can be moved on the canvas                                  | done    | 
|              | editDTofECycle                                 | "            | editing of DT of E_Cycle is possible and new value is shown                | done    | 
|              | directEditorDefaultValueTest                   | "            | the default value of DT of E_Cycle can be edited                           | done    | 
|              | directEditorNewValueTest                       | "            | the default value of DT of E_Cycle is shown                                | done    |
|              |                                                |              |                                                                            |         | 
|              | createValidConnection                          | E_CYCLE      | a valid connection between a red input and red output pin can be created   | done    |
|              | createValidConnection                          | E_CTD        | a valid connection between a blue input and blue output pin can be created | planned |
|              | isConnectionThereAfterMoving                   | "            | is a valid connection still visible after moving the FB on canvas          | planned | 
|              |                                                | E_N_TABLE    | it fails to connect a red input pin with a red input pin                   | planned | 
|              |                                                | "            | it fails to connect a blue input pin with a blue input pin                 | planned |
|              |                                                | "            | it fails to connect a red output pin with a red output pin                 | planned | 
|              |                                                | E_TABLE_CTRL | it fails to connect a red input pin with a blue input pin                  | planned |
|              |                                                | "            | it fails to connect a red input pin with a blue output pin                 | planned |
|              |                                                | "            | it fails to connect a blue input pin with a blue output pin                | planned |
|              |                                                | "            | it fails to connect a blue input pin with a red output pin                 | planned |
|              |                                                | "            | it fails to connect a blue output pin with a blue output pin               | planned | 
|              |                                                | "            | it fails to connect a red output pin with a blue output pin                | planned | 
|              |                                                |              |                                                                            |         | 
|              |                                                |              | it is possible to change the automatic generated name of a FB              | planned | 
|              |                                                |              | is it possible to double click on FB                                       | planned | 
|              |                                                |              | double click on FB - extra tab does appear                                 | planned |
|              |                                                |              | double click on FB - adding another FB is not possible                     | planned | 
|              |                                                |              | double click on FB - creating another connection is not possible           | planned | 
|              |                                                |              | double click on FB - deleting a connection is not possible                 | planned | 
|              |                                                |              | double click on FB - moving FB is not possible                             | planned |
|              |                                                |              |                                                                            |         |
|              |                                                |              |                                                                            |         | 
| 2 FBs        |                                                |              | it is possible to drag and drop a valid connection                         | planned | 
|              |                                                |              | connection stays after moving both FBs                                     | planned |
|              |                                                |              | a valid connection between two gray pins can be created                    | planned |
|              |                                                |              | it fails to connect a gray input pin with a gray input pin                 | planned |
|              |                                                |              | it fails to connect a gray input pin with a red input pin                  | planned |
|              |                                                |              | it fails to connect a gray input pin with a blue input pin                 | planned |
|              |                                                |              | it fails to connect a gray output pin with a gray output pin               | planned |
|              |                                                |              | it fails to connect a gray output pin with a red output pin                | planned | 
|              |                                                |              | it fails to connect a gray output pin with a red output pin                | planned |
|              |                                                |              |                                                                            |         | 
|              |                                                |              |                                                                            |         | 
|              |                                                |              |                                                                            |         | 
| multiple FBs |                                                |              | Fbs have running numbers - drag&drop a few and check numbers               | planned |
|              |                                                |              | new FB gets the smallest next free number                                  | planned | 
|              |                                                |              |   (if 1, 2, 3, 4 exists, delete 2, drag&drop new FB - should be no 2)      | planned |
|              |                                                |              | it is possible to generate the blinky app from tutorial                    | planned | 
|              |                                                |              |                                                                            |         |
|              |                                                |              |                                                                            |         | 
|              |                                                |              |                                                                            |         | 
|              |                                                |              |                                                                            |         | 
|              |                                                |              |                                                                            |         |
|              |                                                |              |                                                                            |         |
|              |                                                |              |                                                                            |         | 
|              |                                                |              |                                                                            |         |
|              |                                                |              |                                                                            |         | 
|              |                                                |              |                                                                            |         | 
|              |                                                |              |                                                                            |         | 
|              |                                                |              |                                                                            |         |
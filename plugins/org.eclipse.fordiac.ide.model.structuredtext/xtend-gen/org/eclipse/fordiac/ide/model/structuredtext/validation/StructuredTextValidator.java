/**
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * 
 *   Ernst Blecha - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.structuredtext.validation;

import org.eclipse.fordiac.ide.model.structuredtext.structuredText.PartialAccess;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextPackage;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.impl.PrimaryVariableImpl;
import org.eclipse.fordiac.ide.model.structuredtext.validation.AbstractStructuredTextValidator;
import org.eclipse.xtext.validation.Check;

/**
 * This class contains custom validation rules.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@SuppressWarnings("all")
public class StructuredTextValidator extends AbstractStructuredTextValidator {
  public static final String INVALID_PARTIAL = "invalidMultibitPartAccess";
  
  private boolean checkIndex(final PartialAccess p, final int size) {
    if (((p.getIndex() >= 0) && (p.getIndex() < size))) {
      return true;
    } else {
      return false;
    }
  }
  
  private boolean validPrimaryVariablePartialAccess(final PrimaryVariableImpl v) {
    PartialAccess _part = v.getPart();
    boolean _tripleNotEquals = (null != _part);
    if (_tripleNotEquals) {
      if ((((v.getPart().isBitaccess() && (!v.getPart().isByteaccess())) && (!v.getPart().isWordaccess())) && (!v.getPart().isDwordaccess()))) {
        boolean _equals = v.getVar().getType().getName().equals("LWORD");
        if (_equals) {
          return this.checkIndex(v.getPart(), 64);
        } else {
          boolean _equals_1 = v.getVar().getType().getName().equals("DWORD");
          if (_equals_1) {
            return this.checkIndex(v.getPart(), 32);
          } else {
            boolean _equals_2 = v.getVar().getType().getName().equals("WORD");
            if (_equals_2) {
              return this.checkIndex(v.getPart(), 16);
            } else {
              boolean _equals_3 = v.getVar().getType().getName().equals("BYTE");
              if (_equals_3) {
                return this.checkIndex(v.getPart(), 8);
              } else {
                return false;
              }
            }
          }
        }
      } else {
        if (((((!v.getPart().isBitaccess()) && v.getPart().isByteaccess()) && (!v.getPart().isWordaccess())) && (!v.getPart().isDwordaccess()))) {
          boolean _equals_4 = v.getVar().getType().getName().equals("LWORD");
          if (_equals_4) {
            return this.checkIndex(v.getPart(), 8);
          } else {
            boolean _equals_5 = v.getVar().getType().getName().equals("DWORD");
            if (_equals_5) {
              return this.checkIndex(v.getPart(), 4);
            } else {
              boolean _equals_6 = v.getVar().getType().getName().equals("WORD");
              if (_equals_6) {
                return this.checkIndex(v.getPart(), 2);
              } else {
                return false;
              }
            }
          }
        } else {
          if (((((!v.getPart().isBitaccess()) && (!v.getPart().isByteaccess())) && v.getPart().isWordaccess()) && (!v.getPart().isDwordaccess()))) {
            boolean _equals_7 = v.getVar().getType().getName().equals("LWORD");
            if (_equals_7) {
              return this.checkIndex(v.getPart(), 4);
            } else {
              boolean _equals_8 = v.getVar().getType().getName().equals("DWORD");
              if (_equals_8) {
                return this.checkIndex(v.getPart(), 2);
              } else {
                return false;
              }
            }
          } else {
            if (((((!v.getPart().isBitaccess()) && (!v.getPart().isByteaccess())) && (!v.getPart().isWordaccess())) && v.getPart().isDwordaccess())) {
              boolean _equals_9 = v.getVar().getType().getName().equals("LWORD");
              if (_equals_9) {
                return this.checkIndex(v.getPart(), 2);
              } else {
                return false;
              }
            }
          }
        }
      }
    } else {
      return true;
    }
    return false;
  }
  
  @Check
  public void checkPartialAccess(final PrimaryVariableImpl v) {
    boolean _validPrimaryVariablePartialAccess = this.validPrimaryVariablePartialAccess(v);
    boolean _not = (!_validPrimaryVariablePartialAccess);
    if (_not) {
      this.error("Incorrect partial access (e.g. accessing a DWORD inside a BYTE, index not within limits, ...)", StructuredTextPackage.Literals.PRIMARY_VARIABLE__VAR);
    }
  }
}

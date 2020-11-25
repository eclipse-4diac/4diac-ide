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

import com.google.common.base.Objects;
import java.util.Arrays;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AdapterRoot;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AdapterVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.LocalVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.PartialAccess;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.PrimaryVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextPackage;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.TimeLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Variable;
import org.eclipse.fordiac.ide.model.structuredtext.validation.AbstractStructuredTextValidator;
import org.eclipse.fordiac.ide.model.structuredtext.validation.DatetimeLiteral;
import org.eclipse.xtext.validation.Check;

/**
 * This class contains custom validation rules.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@SuppressWarnings("all")
public class StructuredTextValidator extends AbstractStructuredTextValidator {
  private boolean isIndexInRange(final PartialAccess p, final int start, final int stop) {
    return ((p.getIndex() >= start) && (p.getIndex() <= stop));
  }
  
  @Check
  public void checkPartialAccess(final PrimaryVariable v) {
    PartialAccess _part = v.getPart();
    boolean _tripleNotEquals = (null != _part);
    if (_tripleNotEquals) {
      PartialAccess _part_1 = v.getPart();
      int _arraySize = v.getVar().getArraySize();
      int _minus = (_arraySize - 1);
      boolean _isIndexInRange = this.isIndexInRange(_part_1, 0, _minus);
      boolean _not = (!_isIndexInRange);
      if (_not) {
        this.error("Incorrect partial access: index not within limits.", StructuredTextPackage.Literals.PRIMARY_VARIABLE__VAR);
      }
    }
  }
  
  private int _BitSize(final VarDeclaration v) {
    return this.BitSize(this.extractTypeInformation(v));
  }
  
  private int _BitSize(final PrimaryVariable v) {
    return this.BitSize(this.extractTypeInformation(v));
  }
  
  private int _BitSize(final LocalVariable v) {
    return this.BitSize(this.extractTypeInformation(v));
  }
  
  private int _BitSize(final String str) {
    int _switchResult = (int) 0;
    boolean _matched = false;
    boolean _equals = str.equals(FordiacKeywords.LWORD);
    if (_equals) {
      _matched=true;
      _switchResult = 64;
    }
    if (!_matched) {
      boolean _equals_1 = str.equals(FordiacKeywords.DWORD);
      if (_equals_1) {
        _matched=true;
        _switchResult = 32;
      }
    }
    if (!_matched) {
      boolean _equals_2 = str.equals(FordiacKeywords.WORD);
      if (_equals_2) {
        _matched=true;
        _switchResult = 16;
      }
    }
    if (!_matched) {
      boolean _equals_3 = str.equals(FordiacKeywords.BYTE);
      if (_equals_3) {
        _matched=true;
        _switchResult = 8;
      }
    }
    if (!_matched) {
      boolean _equals_4 = str.equals(FordiacKeywords.BOOL);
      if (_equals_4) {
        _matched=true;
        _switchResult = 1;
      }
    }
    if (!_matched) {
      _switchResult = 0;
    }
    return _switchResult;
  }
  
  private String _extractTypeInformation(final PartialAccess part, final String DataType) {
    String _xifexpression = null;
    if ((null != part)) {
      String _xifexpression_1 = null;
      boolean _isBitaccess = part.isBitaccess();
      if (_isBitaccess) {
        _xifexpression_1 = FordiacKeywords.BOOL;
      } else {
        String _xifexpression_2 = null;
        boolean _isByteaccess = part.isByteaccess();
        if (_isByteaccess) {
          _xifexpression_2 = FordiacKeywords.BYTE;
        } else {
          String _xifexpression_3 = null;
          boolean _isWordaccess = part.isWordaccess();
          if (_isWordaccess) {
            _xifexpression_3 = FordiacKeywords.WORD;
          } else {
            String _xifexpression_4 = null;
            boolean _isDwordaccess = part.isDwordaccess();
            if (_isDwordaccess) {
              _xifexpression_4 = FordiacKeywords.DWORD;
            } else {
              _xifexpression_4 = "";
            }
            _xifexpression_3 = _xifexpression_4;
          }
          _xifexpression_2 = _xifexpression_3;
        }
        _xifexpression_1 = _xifexpression_2;
      }
      _xifexpression = _xifexpression_1;
    } else {
      _xifexpression = DataType;
    }
    return _xifexpression;
  }
  
  private String _extractTypeInformation(final PrimaryVariable variable, final String DataType) {
    String _xifexpression = null;
    if (((null != variable) && (null != variable.getPart()))) {
      _xifexpression = this.extractTypeInformation(variable.getPart());
    } else {
      _xifexpression = variable.getVar().getType().getName();
    }
    return _xifexpression;
  }
  
  private String _extractTypeInformation(final Variable variable, final String DataType) {
    String _xifexpression = null;
    if (((null != variable) && (null != variable.getPart()))) {
      _xifexpression = this.extractTypeInformation(variable.getPart());
    } else {
      _xifexpression = "";
    }
    return _xifexpression;
  }
  
  protected String _extractTypeInformation(final PrimaryVariable variable) {
    return this.extractTypeInformation(variable, this.extractTypeInformation(variable.getVar()));
  }
  
  protected String _extractTypeInformation(final VarDeclaration variable) {
    return variable.getType().getName();
  }
  
  protected String _extractTypeInformation(final AdapterVariable variable) {
    String _xblockexpression = null;
    {
      final AdapterVariable head = variable.getCurr();
      String _switchResult = null;
      boolean _matched = false;
      if (head instanceof AdapterRoot) {
        _matched=true;
        _switchResult = ((AdapterRoot)head).getAdapter().getType().getName();
      }
      if (!_matched) {
        if (head instanceof AdapterVariable) {
          _matched=true;
          _switchResult = head.getVar().getType().getName();
        }
      }
      if (!_matched) {
        _switchResult = "";
      }
      _xblockexpression = _switchResult;
    }
    return _xblockexpression;
  }
  
  @Check
  public void checkLocalVariable(final LocalVariable v) {
    if ((v.isLocated() && v.isInitalized())) {
      this.error("Located variables can not be initialized.", StructuredTextPackage.Literals.LOCAL_VARIABLE__INITIAL_VALUE);
    } else {
      if ((v.isArray() && v.isInitalized())) {
        this.error("Local arrays can not be initialized.", StructuredTextPackage.Literals.LOCAL_VARIABLE__INITIAL_VALUE);
      }
    }
  }
  
  @Check
  public void checkArray(final LocalVariable v) {
    boolean _isArray = v.isArray();
    if (_isArray) {
      int _arrayStart = v.getArrayStart();
      boolean _notEquals = (_arrayStart != 0);
      if (_notEquals) {
        this.error("Only arrays with a start index of 0 are supported.", StructuredTextPackage.Literals.LOCAL_VARIABLE__ARRAY);
      }
      int _arrayStart_1 = v.getArrayStart();
      int _arrayStop = v.getArrayStop();
      boolean _greaterEqualsThan = (_arrayStart_1 >= _arrayStop);
      if (_greaterEqualsThan) {
        this.error("Only arrays with incrementing index are supported.", StructuredTextPackage.Literals.LOCAL_VARIABLE__ARRAY);
      }
    }
  }
  
  private int extractArraySize(final VarDeclaration v) {
    if ((v instanceof LocalVariable)) {
      int _arrayStop = ((LocalVariable)v).getArrayStop();
      int _arrayStart = ((LocalVariable)v).getArrayStart();
      int _minus = (_arrayStop - _arrayStart);
      return (_minus + 1);
    } else {
      return v.getArraySize();
    }
  }
  
  @Check
  public void checkAtLocation(final LocalVariable v) {
    if ((v.isLocated() && (null != v.getLocation()))) {
      if ((((this.BitSize(v.getLocation()) == 0) || (this.BitSize(v) == 0)) && v.isArray())) {
        this.error("Piecewise located variables are allowed only for variables of type ANY_BIT", StructuredTextPackage.Literals.LOCAL_VARIABLE__LOCATED);
      }
      if (((((this.BitSize(v.getLocation()) > 0) && (this.BitSize(v) > 0)) && v.isArray()) && ((this.extractArraySize(v) * this.BitSize(v)) > this.BitSize(v.getLocation())))) {
        this.error("Piecewise located variables cannot access more bits than are available in the destination", StructuredTextPackage.Literals.LOCAL_VARIABLE__LOCATED);
      }
      if ((((this.BitSize(v) == 0) && (this.BitSize(v.getLocation()) == 0)) && (!Objects.equal(this.extractTypeInformation(v.getLocation(), this.extractTypeInformation(v.getLocation())), this.extractTypeInformation(v))))) {
        this.error("General located variables must have matching types", StructuredTextPackage.Literals.LOCAL_VARIABLE__LOCATED);
      }
    }
  }
  
  @Check
  public void validateTimeLiteral(final TimeLiteral expr) {
    String _literal = expr.getLiteral();
    final DatetimeLiteral literal = new DatetimeLiteral(_literal);
    boolean _isValid = literal.isValid();
    boolean _not = (!_isValid);
    if (_not) {
      this.error("Invalid Literal", StructuredTextPackage.Literals.TIME_LITERAL__LITERAL);
    }
  }
  
  private int BitSize(final Object v) {
    if (v instanceof LocalVariable) {
      return _BitSize((LocalVariable)v);
    } else if (v instanceof VarDeclaration) {
      return _BitSize((VarDeclaration)v);
    } else if (v instanceof PrimaryVariable) {
      return _BitSize((PrimaryVariable)v);
    } else if (v instanceof String) {
      return _BitSize((String)v);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(v).toString());
    }
  }
  
  private String extractTypeInformation(final EObject variable, final String DataType) {
    if (variable instanceof PrimaryVariable) {
      return _extractTypeInformation((PrimaryVariable)variable, DataType);
    } else if (variable instanceof Variable) {
      return _extractTypeInformation((Variable)variable, DataType);
    } else if (variable instanceof PartialAccess) {
      return _extractTypeInformation((PartialAccess)variable, DataType);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(variable, DataType).toString());
    }
  }
  
  protected String extractTypeInformation(final EObject variable) {
    if (variable instanceof VarDeclaration) {
      return _extractTypeInformation((VarDeclaration)variable);
    } else if (variable instanceof AdapterVariable) {
      return _extractTypeInformation((AdapterVariable)variable);
    } else if (variable instanceof PrimaryVariable) {
      return _extractTypeInformation((PrimaryVariable)variable);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(variable).toString());
    }
  }
}

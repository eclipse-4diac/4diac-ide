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
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AdapterVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.LocatedVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.PartialAccess;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.PrimaryVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextPackage;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Variable;
import org.eclipse.fordiac.ide.model.structuredtext.validation.AbstractStructuredTextValidator;
import org.eclipse.xtext.validation.Check;

/**
 * This class contains custom validation rules.
 * 
 * See
 * https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
@SuppressWarnings("all")
public class StructuredTextValidator extends AbstractStructuredTextValidator {
	private int AccessBits(final PartialAccess part) {
		return this.BitSize(this.extractTypeInformation(part, ""));
	}

	private boolean isIndexInRange(final PartialAccess p, final int size) {
		return ((p.getIndex() >= 0) && (p.getIndex() < size));
	}

	@Check
	public void checkPartialAccess(final PrimaryVariable v) {
		PartialAccess _part = v.getPart();
		boolean _tripleNotEquals = (null != _part);
		if (_tripleNotEquals) {
			int _BitSize = this.BitSize(v.getVar().getType().getName());
			int _AccessBits = this.AccessBits(v.getPart());
			final int indexSize = (_BitSize / _AccessBits);
			if (((this.AccessBits(v.getPart()) > 0)
					&& (this.BitSize(v.getVar().getType().getName()) > this.AccessBits(v.getPart())))) {
				boolean _isIndexInRange = this.isIndexInRange(v.getPart(), indexSize);
				boolean _not = (!_isIndexInRange);
				if (_not) {
					this.error("Incorrect partial access: index not within limits.",
							StructuredTextPackage.Literals.PRIMARY_VARIABLE__VAR);
				} else {
					if (((this.AccessBits(v.getPart()) == 0) || (this.BitSize(v.getVar().getType().getName()) == 0))) {
						this.error("Incorrect partial access: datatypes other than ANY_BIT.",
								StructuredTextPackage.Literals.PRIMARY_VARIABLE__VAR);
					} else {
						if ((indexSize < 1)) {
							this.error(
									"Incorrect partial access: access dataype must be smaller than storage datatype.",
									StructuredTextPackage.Literals.PRIMARY_VARIABLE__VAR);
						}
					}
				}
			}
		}
	}

	private int _BitSize(final VarDeclaration v) {
		return this.BitSize(this.extractTypeInformation(v));
	}

	private int _BitSize(final PrimaryVariable v) {
		return this.BitSize(this.extractTypeInformation(v));
	}

	private int _BitSize(final LocatedVariable v) {
		return this.BitSize(this.extractTypeInformation(v));
	}

	private int _BitSize(final String str) {
		int _switchResult = (int) 0;
		boolean _matched = false;
		boolean _equals = str.equals("LWORD");
		if (_equals) {
			_matched = true;
			_switchResult = 64;
		}
		if (!_matched) {
			boolean _equals_1 = str.equals("DWORD");
			if (_equals_1) {
				_matched = true;
				_switchResult = 32;
			}
		}
		if (!_matched) {
			boolean _equals_2 = str.equals("WORD");
			if (_equals_2) {
				_matched = true;
				_switchResult = 16;
			}
		}
		if (!_matched) {
			boolean _equals_3 = str.equals("BYTE");
			if (_equals_3) {
				_matched = true;
				_switchResult = 8;
			}
		}
		if (!_matched) {
			boolean _equals_4 = str.equals("BOOL");
			if (_equals_4) {
				_matched = true;
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
				_xifexpression_1 = "BOOL";
			} else {
				String _xifexpression_2 = null;
				boolean _isByteaccess = part.isByteaccess();
				if (_isByteaccess) {
					_xifexpression_2 = "BYTE";
				} else {
					String _xifexpression_3 = null;
					boolean _isWordaccess = part.isWordaccess();
					if (_isWordaccess) {
						_xifexpression_3 = "WORD";
					} else {
						String _xifexpression_4 = null;
						boolean _isDwordaccess = part.isDwordaccess();
						if (_isDwordaccess) {
							_xifexpression_4 = "DWORD";
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
		String _xifexpression = null;
		if ((null != variable)) {
			_xifexpression = this.extractTypeInformation(variable, this.extractTypeInformation(variable.getVar()));
		} else {
			_xifexpression = variable.getVar().getType().getName();
		}
		return _xifexpression;
	}

	protected String _extractTypeInformation(final VarDeclaration variable) {
		return variable.getType().getName();
	}

	protected String _extractTypeInformation(final AdapterVariable variable) {
		return variable.getVar().getType().getName();
	}

	@Check
	public void checkAtLocation(final LocatedVariable v) {
		Variable _location = v.getLocation();
		boolean _tripleNotEquals = (null != _location);
		if (_tripleNotEquals) {
			if (((this.BitSize(v) == 0) && v.isArray())) {
				this.error("Piecewise located variables are allowed only for variables of type ANY_BIT",
						StructuredTextPackage.Literals.LOCATED_VARIABLE__LOCATION);
			}
			if ((((this.BitSize(v) > 0) && v.isArray()) && (v.getArraySize() == 0))) {
				this.error("Piecewise located variables must have at least an array size of 1",
						StructuredTextPackage.Literals.LOCATED_VARIABLE__LOCATION);
			}
			if ((((this.BitSize(v) > 0) && v.isArray()) && (v.getArraySize() > this.BitSize(v.getLocation())))) {
				this.error("Piecewise located variables cannot access more bits than are available in the destination",
						StructuredTextPackage.Literals.LOCATED_VARIABLE__LOCATION);
			}
			if ((((this.BitSize(v) == 0) && (this.BitSize(v.getLocation()) == 0)) && (!Objects.equal(
					this.extractTypeInformation(v.getLocation(), this.extractTypeInformation(v.getLocation())),
					this.extractTypeInformation(v))))) {
				this.error("General located variables must have matching types",
						StructuredTextPackage.Literals.LOCATED_VARIABLE__LOCATION);
			}
		}
	}

	private int BitSize(final Object v) {
		if (v instanceof LocatedVariable) {
			return _BitSize((LocatedVariable) v);
		} else if (v instanceof VarDeclaration) {
			return _BitSize((VarDeclaration) v);
		} else if (v instanceof PrimaryVariable) {
			return _BitSize((PrimaryVariable) v);
		} else if (v instanceof String) {
			return _BitSize((String) v);
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " + Arrays.<Object>asList(v).toString());
		}
	}

	private String extractTypeInformation(final EObject variable, final String DataType) {
		if (variable instanceof PrimaryVariable) {
			return _extractTypeInformation((PrimaryVariable) variable, DataType);
		} else if (variable instanceof Variable) {
			return _extractTypeInformation((Variable) variable, DataType);
		} else if (variable instanceof PartialAccess) {
			return _extractTypeInformation((PartialAccess) variable, DataType);
		} else {
			throw new IllegalArgumentException(
					"Unhandled parameter types: " + Arrays.<Object>asList(variable, DataType).toString());
		}
	}

	protected String extractTypeInformation(final EObject variable) {
		if (variable instanceof VarDeclaration) {
			return _extractTypeInformation((VarDeclaration) variable);
		} else if (variable instanceof AdapterVariable) {
			return _extractTypeInformation((AdapterVariable) variable);
		} else if (variable instanceof PrimaryVariable) {
			return _extractTypeInformation((PrimaryVariable) variable);
		} else {
			throw new IllegalArgumentException(
					"Unhandled parameter types: " + Arrays.<Object>asList(variable).toString());
		}
	}
}

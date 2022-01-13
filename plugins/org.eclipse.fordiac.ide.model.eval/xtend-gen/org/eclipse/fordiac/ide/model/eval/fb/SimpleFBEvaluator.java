/**
 * Copyright (c) 2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.eval.fb;

import java.util.Collection;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.st.StructuredTextEvaluator;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class SimpleFBEvaluator extends FBEvaluator<SimpleFBType> {
  private Evaluator algorithmEvaluator;
  
  public SimpleFBEvaluator(final SimpleFBType type, final Evaluator parent) {
    super(type, parent);
    StructuredTextEvaluator _switchResult = null;
    Algorithm _algorithm = type.getAlgorithm();
    final Algorithm alg = _algorithm;
    boolean _matched = false;
    if (alg instanceof STAlgorithm) {
      _matched=true;
      _switchResult = new StructuredTextEvaluator(((STAlgorithm)alg), this);
    }
    if (!_matched) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Cannot evaluate algorithm ");
      Class<? extends Algorithm> _class = alg.getClass();
      _builder.append(_class);
      throw new UnsupportedOperationException(_builder.toString());
    }
    this.algorithmEvaluator = _switchResult;
  }
  
  @Override
  public void evaluate() {
    try {
      this.algorithmEvaluator.evaluate();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public Collection<Variable> getVariables() {
    return CollectionLiterals.<Variable>emptyList();
  }
}

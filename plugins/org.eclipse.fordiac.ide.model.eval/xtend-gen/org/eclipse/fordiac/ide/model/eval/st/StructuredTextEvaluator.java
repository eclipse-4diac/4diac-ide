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
package org.eclipse.fordiac.ide.model.eval.st;

import com.google.common.collect.Iterables;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.eval.AbstractEvaluator;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorExitException;
import org.eclipse.fordiac.ide.model.eval.value.BoolValue;
import org.eclipse.fordiac.ide.model.eval.value.LIntValue;
import org.eclipse.fordiac.ide.model.eval.value.LRealValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.eval.variable.ElementaryVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.structuredtext.parser.antlr.StructuredTextParser;
import org.eclipse.fordiac.ide.model.structuredtext.resource.StructuredTextResource;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AssignmentStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BinaryExpression;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BinaryOperator;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BoolLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.CaseClause;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.CaseStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Constant;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ContinueStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ElseClause;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ElseIfClause;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ExitStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Expression;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ForStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.IfStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.IntLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.LocalVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.PrimaryVariable;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.RealLiteral;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.RepeatStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ReturnStatement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Statement;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StatementList;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextAlgorithm;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.UnaryExpression;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.UnaryOperator;
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.WhileStatement;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.LazyStringInputStream;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public final class StructuredTextEvaluator extends AbstractEvaluator {
  public static class StructuredTextException extends Exception {
    public StructuredTextException(final Statement statement) {
      super(statement.eClass().getName());
    }
  }
  
  public static class ContinueException extends StructuredTextEvaluator.StructuredTextException {
    public ContinueException(final Statement statement) {
      super(statement);
    }
  }
  
  public static class ReturnException extends StructuredTextEvaluator.StructuredTextException {
    public ReturnException(final Statement statement) {
      super(statement);
    }
  }
  
  public static class StructuredTextExitException extends EvaluatorExitException {
    public StructuredTextExitException(final Statement statement, final Evaluator evaluator) {
      super(evaluator);
    }
  }
  
  private static final String SYNTHETIC_URI_NAME = "__synthetic";
  
  private static final String URI_SEPERATOR = ".";
  
  private static final String FB_URI_EXTENSION = "xtextfbt";
  
  private static final String ST_URI_EXTENSION = "st";
  
  private static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE.getResourceServiceProvider(URI.createURI(((StructuredTextEvaluator.SYNTHETIC_URI_NAME + StructuredTextEvaluator.URI_SEPERATOR) + StructuredTextEvaluator.ST_URI_EXTENSION)));
  
  @Accessors
  private final String name;
  
  private final String text;
  
  private final BaseFBType fbType;
  
  private final boolean singleExpression;
  
  private final Map<VarDeclaration, Variable> variables;
  
  public StructuredTextEvaluator(final STAlgorithm alg, final Collection<Variable> variables, final Evaluator parent) {
    super(parent);
    EObject _rootContainer = EcoreUtil.getRootContainer(alg);
    this.fbType = ((BaseFBType) _rootContainer);
    StringConcatenation _builder = new StringConcatenation();
    String _name = this.fbType.getName();
    _builder.append(_name);
    _builder.append(".");
    String _name_1 = alg.getName();
    _builder.append(_name_1);
    this.name = _builder.toString();
    this.text = alg.getText();
    this.singleExpression = false;
    final Function1<Variable, VarDeclaration> _function = (Variable it) -> {
      return it.getDeclaration();
    };
    this.variables = IterableExtensions.<VarDeclaration, Variable>toMap(variables, _function);
  }
  
  public StructuredTextEvaluator(final String text, final Collection<Variable> variables, final BaseFBType fbType, final Evaluator parent) {
    super(parent);
    this.name = "anonymous";
    this.text = text;
    this.fbType = fbType;
    this.singleExpression = true;
    final Function1<Variable, VarDeclaration> _function = (Variable it) -> {
      return it.getDeclaration();
    };
    this.variables = IterableExtensions.<VarDeclaration, Variable>toMap(variables, _function);
  }
  
  @Override
  public Collection<Variable> getVariables() {
    return Collections.<Variable>unmodifiableCollection(this.variables.values());
  }
  
  @Override
  public Object getSourceElement() {
    return this.fbType;
  }
  
  @Override
  public Value evaluate() {
    Value _xblockexpression = null;
    {
      final IParseResult parseResult = this.parse();
      final EObject root = parseResult.getRootASTElement();
      _xblockexpression = this.evaluate(root);
    }
    return _xblockexpression;
  }
  
  protected Value _evaluate(final StructuredTextAlgorithm alg) {
    Object _xblockexpression = null;
    {
      this.evaluateStructuredTextAlgorithm(this.<StructuredTextAlgorithm>trap(alg));
      _xblockexpression = null;
    }
    return ((Value)_xblockexpression);
  }
  
  protected Value _evaluate(final Expression expr) {
    return this.evaluateExpression(this.<Expression>trap(expr));
  }
  
  private IParseResult parse() {
    try {
      ResourceSet _get = StructuredTextEvaluator.SERVICE_PROVIDER.<ResourceSet>get(ResourceSet.class);
      final XtextResourceSet resourceSet = ((XtextResourceSet) _get);
      this.createFBResource(resourceSet, this.fbType);
      Resource _createResource = resourceSet.createResource(this.computeUnusedUri(resourceSet, StructuredTextEvaluator.ST_URI_EXTENSION));
      final XtextResource resource = ((XtextResource) _createResource);
      IParser _parser = resource.getParser();
      final StructuredTextParser parser = ((StructuredTextParser) _parser);
      LazyStringInputStream _lazyStringInputStream = new LazyStringInputStream(this.text);
      Pair<String, Boolean> _mappedTo = Pair.<String, Boolean>of(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
      ParserRule _xifexpression = null;
      if (this.singleExpression) {
        _xifexpression = parser.getGrammarAccess().getExpressionRule();
      } else {
        _xifexpression = null;
      }
      Pair<String, ParserRule> _mappedTo_1 = Pair.<String, ParserRule>of(StructuredTextResource.OPTION_PARSER_RULE, _xifexpression);
      resource.load(_lazyStringInputStream, Collections.<String, Object>unmodifiableMap(CollectionLiterals.<String, Object>newHashMap(_mappedTo, _mappedTo_1)));
      EObject _rootASTElement = resource.getParseResult().getRootASTElement();
      final StructuredTextAlgorithm stalg = ((StructuredTextAlgorithm) _rootASTElement);
      final Consumer<VarDeclaration> _function = (VarDeclaration v) -> {
        this.createStructResource(resourceSet, v);
      };
      stalg.getLocalVariables().forEach(_function);
      final IParseResult parseResult = resource.getParseResult();
      final IResourceValidator validator = resource.getResourceServiceProvider().getResourceValidator();
      final List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
      boolean _isEmpty = issues.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        final Consumer<Issue> _function_1 = (Issue it) -> {
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("Parse error: ");
          {
            if ((!this.singleExpression)) {
              _builder.append(this.name);
              _builder.append(" at ");
              Integer _lineNumber = it.getLineNumber();
              _builder.append(_lineNumber);
              _builder.append(": ");
            }
          }
          String _message = it.getMessage();
          _builder.append(_message);
          this.error(_builder.toString());
        };
        issues.forEach(_function_1);
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("Parse error: ");
        {
          boolean _hasElements = false;
          for(final Issue issue : issues) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate("\n", "");
            }
            {
              if ((!this.singleExpression)) {
                _builder.append(this.name);
                _builder.append(" at ");
                Integer _lineNumber = issue.getLineNumber();
                _builder.append(_lineNumber);
                _builder.append(": ");
              }
            }
            String _message = issue.getMessage();
            _builder.append(_message);
          }
        }
        throw new Exception(_builder.toString());
      }
      return parseResult;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private URI computeUnusedUri(final ResourceSet resourceSet, final String fileExtension) {
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, Integer.MAX_VALUE, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final URI syntheticUri = URI.createURI((((StructuredTextEvaluator.SYNTHETIC_URI_NAME + i) + StructuredTextEvaluator.URI_SEPERATOR) + fileExtension));
        Resource _resource = resourceSet.getResource(syntheticUri, false);
        boolean _tripleEquals = (_resource == null);
        if (_tripleEquals) {
          return syntheticUri;
        }
      }
    }
    throw new IllegalStateException();
  }
  
  private void createFBResource(final XtextResourceSet resourceSet, final BaseFBType fbType) {
    final Resource fbResource = resourceSet.createResource(this.computeUnusedUri(resourceSet, StructuredTextEvaluator.FB_URI_EXTENSION));
    fbResource.getContents().add(fbType);
    final Consumer<AdapterDeclaration> _function = (AdapterDeclaration adp) -> {
      this.createAdapterResource(resourceSet, adp);
    };
    fbType.getInterfaceList().getSockets().forEach(_function);
    final Consumer<AdapterDeclaration> _function_1 = (AdapterDeclaration adp) -> {
      this.createAdapterResource(resourceSet, adp);
    };
    fbType.getInterfaceList().getPlugs().forEach(_function_1);
    final Consumer<VarDeclaration> _function_2 = (VarDeclaration v) -> {
      this.createStructResource(resourceSet, v);
    };
    fbType.getInterfaceList().getInputVars().forEach(_function_2);
    final Consumer<VarDeclaration> _function_3 = (VarDeclaration v) -> {
      this.createStructResource(resourceSet, v);
    };
    fbType.getInterfaceList().getOutputVars().forEach(_function_3);
    final Consumer<VarDeclaration> _function_4 = (VarDeclaration v) -> {
      this.createStructResource(resourceSet, v);
    };
    fbType.getInternalVars().forEach(_function_4);
  }
  
  private void createAdapterResource(final XtextResourceSet resourceSet, final AdapterDeclaration adapter) {
    final Resource adapterResource = resourceSet.createResource(this.computeUnusedUri(resourceSet, StructuredTextEvaluator.FB_URI_EXTENSION));
    adapterResource.getContents().add(adapter.getType().getAdapterFBType());
  }
  
  private void createStructResource(final XtextResourceSet resourceSet, final VarDeclaration variable) {
    DataType _type = variable.getType();
    if ((_type instanceof StructuredType)) {
      final Resource structResource = resourceSet.createResource(this.computeUnusedUri(resourceSet, StructuredTextEvaluator.FB_URI_EXTENSION));
      DataType _type_1 = variable.getType();
      final StructuredType type = ((StructuredType) _type_1);
      structResource.getContents().add(type);
      final Consumer<VarDeclaration> _function = (VarDeclaration v) -> {
        this.createStructResource(resourceSet, v);
      };
      type.getMemberVariables().forEach(_function);
    }
  }
  
  private Object evaluateStructuredTextAlgorithm(final StructuredTextAlgorithm alg) {
    Object _xblockexpression = null;
    {
      final Consumer<LocalVariable> _function = (LocalVariable it) -> {
        this.evaluateLocalVariable(it);
      };
      Iterables.<LocalVariable>filter(alg.getLocalVariables(), LocalVariable.class).forEach(_function);
      Object _xtrycatchfinallyexpression = null;
      try {
        this.evaluateStatementList(alg.getStatements());
      } catch (final Throwable _t) {
        if (_t instanceof StructuredTextEvaluator.ReturnException) {
          _xtrycatchfinallyexpression = null;
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      _xblockexpression = _xtrycatchfinallyexpression;
    }
    return _xblockexpression;
  }
  
  private void evaluateLocalVariable(final LocalVariable variable) {
    Constant _initialValue = variable.getInitialValue();
    Value _evaluateExpression = null;
    if (_initialValue!=null) {
      _evaluateExpression=this.evaluateExpression(_initialValue);
    }
    ElementaryVariable _elementaryVariable = new ElementaryVariable(variable, _evaluateExpression);
    this.variables.put(variable, _elementaryVariable);
  }
  
  private void evaluateStatementList(final StatementList list) {
    final Consumer<Statement> _function = (Statement it) -> {
      this.evaluateStatement(it);
    };
    list.getStatements().forEach(_function);
  }
  
  private void _evaluateStatement(final Statement stmt) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("The statement ");
    String _name = stmt.eClass().getName();
    _builder.append(_name);
    _builder.append(" is not supported");
    this.error(_builder.toString());
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("The statement ");
    String _name_1 = stmt.eClass().getName();
    _builder_1.append(_name_1);
    _builder_1.append(" is not supported");
    throw new UnsupportedOperationException(_builder_1.toString());
  }
  
  private void _evaluateStatement(final AssignmentStatement stmt) {
    Variable _evaluateVariable = this.evaluateVariable(stmt.getVariable());
    _evaluateVariable.setValue(this.evaluateExpression(this.<Expression>trap(stmt.getExpression())));
  }
  
  private void _evaluateStatement(final IfStatement stmt) {
    boolean _asBoolean = ValueOperations.asBoolean(this.evaluateExpression(this.<Expression>trap(stmt.getExpression())));
    if (_asBoolean) {
      this.evaluateStatementList(stmt.getStatments());
    } else {
      StatementList _elvis = null;
      final Function1<ElseIfClause, Boolean> _function = (ElseIfClause it) -> {
        return Boolean.valueOf(ValueOperations.asBoolean(this.evaluateExpression(this.<Expression>trap(it.getExpression()))));
      };
      ElseIfClause _findFirst = IterableExtensions.<ElseIfClause>findFirst(stmt.getElseif(), _function);
      StatementList _statements = null;
      if (_findFirst!=null) {
        _statements=_findFirst.getStatements();
      }
      if (_statements != null) {
        _elvis = _statements;
      } else {
        ElseClause _else = stmt.getElse();
        StatementList _statements_1 = null;
        if (_else!=null) {
          _statements_1=_else.getStatements();
        }
        _elvis = _statements_1;
      }
      if (_elvis!=null) {
        this.evaluateStatementList(_elvis);
      }
    }
  }
  
  private void _evaluateStatement(final CaseStatement stmt) {
    final Value value = this.evaluateExpression(this.<Expression>trap(stmt.getExpression()));
    StatementList _elvis = null;
    final Function1<CaseClause, Boolean> _function = (CaseClause it) -> {
      final Function1<Constant, Boolean> _function_1 = (Constant it_1) -> {
        Value _evaluateExpression = this.evaluateExpression(it_1);
        return Boolean.valueOf(ValueOperations.operator_equals(_evaluateExpression, value));
      };
      return Boolean.valueOf(IterableExtensions.<Constant>exists(it.getCase(), _function_1));
    };
    CaseClause _findFirst = IterableExtensions.<CaseClause>findFirst(stmt.getCase(), _function);
    StatementList _statements = null;
    if (_findFirst!=null) {
      _statements=_findFirst.getStatements();
    }
    if (_statements != null) {
      _elvis = _statements;
    } else {
      ElseClause _else = stmt.getElse();
      StatementList _statements_1 = null;
      if (_else!=null) {
        _statements_1=_else.getStatements();
      }
      _elvis = _statements_1;
    }
    if (_elvis!=null) {
      this.evaluateStatementList(_elvis);
    }
  }
  
  private void _evaluateStatement(final ForStatement stmt) {
    final Variable variable = this.evaluateVariable(stmt.getVariable());
    variable.setValue(this.evaluateExpression(this.<Expression>trap(stmt.getFrom())));
    final Value to = this.evaluateExpression(stmt.getTo());
    Value _elvis = null;
    Expression _by = stmt.getBy();
    Value _evaluateExpression = null;
    if (_by!=null) {
      _evaluateExpression=this.evaluateExpression(_by);
    }
    if (_evaluateExpression != null) {
      _elvis = _evaluateExpression;
    } else {
      Value _wrapValue = ValueOperations.wrapValue(Integer.valueOf(1), variable.getDeclaration().getType());
      _elvis = _wrapValue;
    }
    final Value by = _elvis;
    Value _defaultValue = ValueOperations.defaultValue(variable.getDeclaration().getType());
    boolean _greaterEqualsThan = ValueOperations.operator_greaterEqualsThan(by, _defaultValue);
    if (_greaterEqualsThan) {
      while (ValueOperations.operator_lessEqualsThan(variable.getValue(), to)) {
        {
          try {
            this.evaluateStatementList(stmt.getStatements());
          } catch (final Throwable _t) {
            if (_t instanceof StructuredTextEvaluator.ContinueException) {
            } else {
              throw Exceptions.sneakyThrow(_t);
            }
          }
          this.<Expression>trap(stmt.getBy());
          Value _value = variable.getValue();
          Value _plus = ValueOperations.operator_plus(_value, by);
          variable.setValue(_plus);
        }
      }
    } else {
      while (ValueOperations.operator_greaterEqualsThan(variable.getValue(), to)) {
        {
          try {
            this.evaluateStatementList(stmt.getStatements());
          } catch (final Throwable _t) {
            if (_t instanceof StructuredTextEvaluator.ContinueException) {
            } else {
              throw Exceptions.sneakyThrow(_t);
            }
          }
          this.<Expression>trap(stmt.getBy());
          Value _value = variable.getValue();
          Value _plus = ValueOperations.operator_plus(_value, by);
          variable.setValue(_plus);
        }
      }
    }
  }
  
  private void _evaluateStatement(final WhileStatement stmt) {
    while (ValueOperations.asBoolean(this.evaluateExpression(this.<Expression>trap(stmt.getExpression())))) {
      try {
        this.evaluateStatementList(stmt.getStatements());
      } catch (final Throwable _t) {
        if (_t instanceof StructuredTextEvaluator.ContinueException) {
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
  }
  
  private void _evaluateStatement(final RepeatStatement stmt) {
    do {
      try {
        this.evaluateStatementList(stmt.getStatements());
      } catch (final Throwable _t) {
        if (_t instanceof StructuredTextEvaluator.ContinueException) {
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    } while((!ValueOperations.asBoolean(this.evaluateExpression(this.<Expression>trap(stmt.getExpression())))));
  }
  
  private void _evaluateStatement(final ContinueStatement stmt) {
    try {
      Statement _trap = this.<Statement>trap(stmt);
      throw new StructuredTextEvaluator.ContinueException(_trap);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void _evaluateStatement(final ReturnStatement stmt) {
    try {
      Statement _trap = this.<Statement>trap(stmt);
      throw new StructuredTextEvaluator.ReturnException(_trap);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void _evaluateStatement(final ExitStatement stmt) {
    try {
      Statement _trap = this.<Statement>trap(stmt);
      throw new StructuredTextEvaluator.StructuredTextExitException(_trap, this);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private Value _evaluateExpression(final Expression expr) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("The expression ");
    String _name = expr.eClass().getName();
    _builder.append(_name);
    _builder.append(" is not supported");
    this.error(_builder.toString());
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("The expression ");
    String _name_1 = expr.eClass().getName();
    _builder_1.append(_name_1);
    _builder_1.append(" is not supported");
    throw new UnsupportedOperationException(_builder_1.toString());
  }
  
  private Value _evaluateExpression(final BinaryExpression expr) {
    Value _switchResult = null;
    BinaryOperator _operator = expr.getOperator();
    if (_operator != null) {
      switch (_operator) {
        case ADD:
          Value _evaluateExpression = this.evaluateExpression(expr.getLeft());
          Value _evaluateExpression_1 = this.evaluateExpression(expr.getRight());
          _switchResult = ValueOperations.operator_plus(_evaluateExpression, _evaluateExpression_1);
          break;
        case SUB:
          Value _evaluateExpression_2 = this.evaluateExpression(expr.getLeft());
          Value _evaluateExpression_3 = this.evaluateExpression(expr.getRight());
          _switchResult = ValueOperations.operator_minus(_evaluateExpression_2, _evaluateExpression_3);
          break;
        case MUL:
          Value _evaluateExpression_4 = this.evaluateExpression(expr.getLeft());
          Value _evaluateExpression_5 = this.evaluateExpression(expr.getRight());
          _switchResult = ValueOperations.operator_multiply(_evaluateExpression_4, _evaluateExpression_5);
          break;
        case DIV:
          Value _evaluateExpression_6 = this.evaluateExpression(expr.getLeft());
          Value _evaluateExpression_7 = this.evaluateExpression(expr.getRight());
          _switchResult = ValueOperations.operator_divide(_evaluateExpression_6, _evaluateExpression_7);
          break;
        case MOD:
          Value _evaluateExpression_8 = this.evaluateExpression(expr.getLeft());
          Value _evaluateExpression_9 = this.evaluateExpression(expr.getRight());
          _switchResult = ValueOperations.operator_modulo(_evaluateExpression_8, _evaluateExpression_9);
          break;
        case POWER:
          Value _evaluateExpression_10 = this.evaluateExpression(expr.getLeft());
          Value _evaluateExpression_11 = this.evaluateExpression(expr.getRight());
          _switchResult = ValueOperations.operator_power(_evaluateExpression_10, _evaluateExpression_11);
          break;
        case EQ:
          Value _evaluateExpression_12 = this.evaluateExpression(expr.getLeft());
          Value _evaluateExpression_13 = this.evaluateExpression(expr.getRight());
          boolean _equals = ValueOperations.operator_equals(_evaluateExpression_12, _evaluateExpression_13);
          _switchResult = BoolValue.toBoolValue(_equals);
          break;
        case NE:
          Value _evaluateExpression_14 = this.evaluateExpression(expr.getLeft());
          Value _evaluateExpression_15 = this.evaluateExpression(expr.getRight());
          boolean _notEquals = ValueOperations.operator_notEquals(_evaluateExpression_14, _evaluateExpression_15);
          _switchResult = BoolValue.toBoolValue(_notEquals);
          break;
        case LT:
          Value _evaluateExpression_16 = this.evaluateExpression(expr.getLeft());
          Value _evaluateExpression_17 = this.evaluateExpression(expr.getRight());
          boolean _lessThan = ValueOperations.operator_lessThan(_evaluateExpression_16, _evaluateExpression_17);
          _switchResult = BoolValue.toBoolValue(_lessThan);
          break;
        case LE:
          Value _evaluateExpression_18 = this.evaluateExpression(expr.getLeft());
          Value _evaluateExpression_19 = this.evaluateExpression(expr.getRight());
          boolean _lessEqualsThan = ValueOperations.operator_lessEqualsThan(_evaluateExpression_18, _evaluateExpression_19);
          _switchResult = BoolValue.toBoolValue(_lessEqualsThan);
          break;
        case GT:
          Value _evaluateExpression_20 = this.evaluateExpression(expr.getLeft());
          Value _evaluateExpression_21 = this.evaluateExpression(expr.getRight());
          boolean _greaterThan = ValueOperations.operator_greaterThan(_evaluateExpression_20, _evaluateExpression_21);
          _switchResult = BoolValue.toBoolValue(_greaterThan);
          break;
        case GE:
          Value _evaluateExpression_22 = this.evaluateExpression(expr.getLeft());
          Value _evaluateExpression_23 = this.evaluateExpression(expr.getRight());
          boolean _greaterEqualsThan = ValueOperations.operator_greaterEqualsThan(_evaluateExpression_22, _evaluateExpression_23);
          _switchResult = BoolValue.toBoolValue(_greaterEqualsThan);
          break;
        default:
          {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("The operator ");
            BinaryOperator _operator_1 = expr.getOperator();
            _builder.append(_operator_1);
            _builder.append(" is not supported");
            this.error(_builder.toString());
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("The operator ");
            BinaryOperator _operator_2 = expr.getOperator();
            _builder_1.append(_operator_2);
            _builder_1.append(" is not supported");
            throw new UnsupportedOperationException(_builder_1.toString());
          }
      }
    } else {
      {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("The operator ");
        BinaryOperator _operator_1 = expr.getOperator();
        _builder.append(_operator_1);
        _builder.append(" is not supported");
        this.error(_builder.toString());
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("The operator ");
        BinaryOperator _operator_2 = expr.getOperator();
        _builder_1.append(_operator_2);
        _builder_1.append(" is not supported");
        throw new UnsupportedOperationException(_builder_1.toString());
      }
    }
    return _switchResult;
  }
  
  private Value _evaluateExpression(final UnaryExpression expr) {
    Value _switchResult = null;
    UnaryOperator _operator = expr.getOperator();
    if (_operator != null) {
      switch (_operator) {
        case PLUS:
          Value _evaluateExpression = this.evaluateExpression(expr.getExpression());
          _switchResult = ValueOperations.operator_plus(_evaluateExpression);
          break;
        case MINUS:
          Value _evaluateExpression_1 = this.evaluateExpression(expr.getExpression());
          _switchResult = ValueOperations.operator_minus(_evaluateExpression_1);
          break;
        case NOT:
          _switchResult = ValueOperations.bitwiseNot(this.evaluateExpression(expr.getExpression()));
          break;
        default:
          {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("The operator ");
            UnaryOperator _operator_1 = expr.getOperator();
            _builder.append(_operator_1);
            _builder.append(" is not supported");
            this.error(_builder.toString());
            StringConcatenation _builder_1 = new StringConcatenation();
            _builder_1.append("The operator ");
            UnaryOperator _operator_2 = expr.getOperator();
            _builder_1.append(_operator_2);
            _builder_1.append(" is not supported");
            throw new UnsupportedOperationException(_builder_1.toString());
          }
      }
    } else {
      {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("The operator ");
        UnaryOperator _operator_1 = expr.getOperator();
        _builder.append(_operator_1);
        _builder.append(" is not supported");
        this.error(_builder.toString());
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("The operator ");
        UnaryOperator _operator_2 = expr.getOperator();
        _builder_1.append(_operator_2);
        _builder_1.append(" is not supported");
        throw new UnsupportedOperationException(_builder_1.toString());
      }
    }
    return _switchResult;
  }
  
  private Value _evaluateExpression(final BoolLiteral expr) {
    return BoolValue.toBoolValue(expr.isValue());
  }
  
  private Value _evaluateExpression(final IntLiteral expr) {
    return LIntValue.toLIntValue(expr.getValue());
  }
  
  private Value _evaluateExpression(final RealLiteral expr) {
    return LRealValue.toLRealValue(expr.getValue());
  }
  
  private Value _evaluateExpression(final PrimaryVariable expr) {
    return this.variables.get(expr.getVar()).getValue();
  }
  
  private Variable _evaluateVariable(final org.eclipse.fordiac.ide.model.structuredtext.structuredText.Variable variable) {
    EClass _eClass = variable.eClass();
    String _plus = (_eClass + " not supported");
    throw new UnsupportedOperationException(_plus);
  }
  
  private Variable _evaluateVariable(final PrimaryVariable variable) {
    return this.variables.get(variable.getVar());
  }
  
  public Value evaluate(final EObject expr) {
    if (expr instanceof Expression) {
      return _evaluate((Expression)expr);
    } else if (expr instanceof StructuredTextAlgorithm) {
      return _evaluate((StructuredTextAlgorithm)expr);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(expr).toString());
    }
  }
  
  private void evaluateStatement(final Statement stmt) {
    if (stmt instanceof AssignmentStatement) {
      _evaluateStatement((AssignmentStatement)stmt);
      return;
    } else if (stmt instanceof CaseStatement) {
      _evaluateStatement((CaseStatement)stmt);
      return;
    } else if (stmt instanceof ContinueStatement) {
      _evaluateStatement((ContinueStatement)stmt);
      return;
    } else if (stmt instanceof ExitStatement) {
      _evaluateStatement((ExitStatement)stmt);
      return;
    } else if (stmt instanceof ForStatement) {
      _evaluateStatement((ForStatement)stmt);
      return;
    } else if (stmt instanceof IfStatement) {
      _evaluateStatement((IfStatement)stmt);
      return;
    } else if (stmt instanceof RepeatStatement) {
      _evaluateStatement((RepeatStatement)stmt);
      return;
    } else if (stmt instanceof ReturnStatement) {
      _evaluateStatement((ReturnStatement)stmt);
      return;
    } else if (stmt instanceof WhileStatement) {
      _evaluateStatement((WhileStatement)stmt);
      return;
    } else if (stmt != null) {
      _evaluateStatement(stmt);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(stmt).toString());
    }
  }
  
  private Value evaluateExpression(final Expression expr) {
    if (expr instanceof IntLiteral) {
      return _evaluateExpression((IntLiteral)expr);
    } else if (expr instanceof RealLiteral) {
      return _evaluateExpression((RealLiteral)expr);
    } else if (expr instanceof BoolLiteral) {
      return _evaluateExpression((BoolLiteral)expr);
    } else if (expr instanceof PrimaryVariable) {
      return _evaluateExpression((PrimaryVariable)expr);
    } else if (expr instanceof BinaryExpression) {
      return _evaluateExpression((BinaryExpression)expr);
    } else if (expr instanceof UnaryExpression) {
      return _evaluateExpression((UnaryExpression)expr);
    } else if (expr != null) {
      return _evaluateExpression(expr);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(expr).toString());
    }
  }
  
  private Variable evaluateVariable(final org.eclipse.fordiac.ide.model.structuredtext.structuredText.Variable variable) {
    if (variable instanceof PrimaryVariable) {
      return _evaluateVariable((PrimaryVariable)variable);
    } else if (variable != null) {
      return _evaluateVariable(variable);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(variable).toString());
    }
  }
  
  @Pure
  @Override
  public String getName() {
    return this.name;
  }
}

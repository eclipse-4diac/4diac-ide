/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.eval.st

import java.util.Collection
import java.util.Map
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.eval.AbstractEvaluator
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.EvaluatorExitException
import org.eclipse.fordiac.ide.model.eval.value.BoolValue
import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.fordiac.ide.model.eval.variable.ElementaryVariable
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.structuredtext.parser.antlr.StructuredTextParser
import org.eclipse.fordiac.ide.model.structuredtext.resource.StructuredTextResource
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AssignmentStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BinaryExpression
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BoolLiteral
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.CaseStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ContinueStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ExitStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Expression
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ForStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.IfStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.IntLiteral
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.LocalVariable
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.PrimaryVariable
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.RealLiteral
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.RepeatStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ReturnStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Statement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StatementList
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextAlgorithm
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.UnaryExpression
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.WhileStatement
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.parser.IParseResult
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.util.LazyStringInputStream
import org.eclipse.xtext.validation.CheckMode

import static extension org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer
import static extension org.eclipse.fordiac.ide.model.eval.value.BoolValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.LIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.LRealValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*

final class StructuredTextEvaluator extends AbstractEvaluator {

	static final String SYNTHETIC_URI_NAME = "__synthetic" // $NON-NLS-1$
	static final String URI_SEPERATOR = "." // $NON-NLS-1$
	static final String FB_URI_EXTENSION = "xtextfbt" // $NON-NLS-1$
	static final String ST_URI_EXTENSION = "st" // $NON-NLS-1$
	static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(URI.createURI(SYNTHETIC_URI_NAME + URI_SEPERATOR + ST_URI_EXTENSION))

	@Accessors final String name
	final String text
	final BaseFBType fbType
	final boolean singleExpression
	final Map<VarDeclaration, Variable> variables

	new(STAlgorithm alg, Collection<Variable> variables, Evaluator parent) {
		super(parent)
		this.fbType = alg.rootContainer as BaseFBType
		this.name = '''«fbType.name».«alg.name»'''
		this.text = alg.text
		this.singleExpression = false
		this.variables = variables.toMap[declaration]
	}

	new(String text, Collection<Variable> variables, BaseFBType fbType, Evaluator parent) {
		super(parent)
		this.name = "anonymous"
		this.text = text
		this.fbType = fbType
		this.singleExpression = true
		this.variables = variables.toMap[declaration]
	}

	override getVariables() {
		variables.values.unmodifiableView
	}

	override getSourceElement() {
		this.fbType
	}

	override Value evaluate() {
		val parseResult = parse()
		val root = parseResult.rootASTElement
		root.evaluate
	}

	def dispatch Value evaluate(StructuredTextAlgorithm alg) {
		alg.trap.evaluateStructuredTextAlgorithm
		null
	}

	def dispatch Value evaluate(Expression expr) {
		expr.trap.evaluateExpression
	}

	def private IParseResult parse() {
		val resourceSet = SERVICE_PROVIDER.get(ResourceSet) as XtextResourceSet
		createFBResource(resourceSet, fbType)
		val resource = resourceSet.createResource(resourceSet.computeUnusedUri(ST_URI_EXTENSION)) as XtextResource
		val parser = resource.parser as StructuredTextParser
		resource.load(new LazyStringInputStream(text), #{
			XtextResource.OPTION_RESOLVE_ALL -> Boolean.TRUE,
			StructuredTextResource.OPTION_PARSER_RULE ->
				if(singleExpression) parser.grammarAccess.expressionRule else null
		})
		val stalg = resource.parseResult.rootASTElement as StructuredTextAlgorithm
		stalg.localVariables.forEach[v|createStructResource(resourceSet, v)]
		val parseResult = resource.parseResult
		val validator = resource.resourceServiceProvider.resourceValidator
		val issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl)
		if (!issues.empty) {
			issues.forEach[error('''Parse error: «IF !singleExpression»«name» at «lineNumber»: «ENDIF»«message»''')]
			throw new Exception('''Parse error: «FOR issue : issues SEPARATOR '\n'»«IF !singleExpression»«name» at «issue.lineNumber»: «ENDIF»«issue.message»«ENDFOR»''')
		}
		return parseResult
	}

	def private URI computeUnusedUri(ResourceSet resourceSet, String fileExtension) {
		for (i : 0 ..< Integer.MAX_VALUE) {
			val syntheticUri = URI.createURI(SYNTHETIC_URI_NAME + i + URI_SEPERATOR + fileExtension) // $NON-NLS-1$
			if (resourceSet.getResource(syntheticUri, false) === null) {
				return syntheticUri
			}
		}
		throw new IllegalStateException()
	}

	def private createFBResource(XtextResourceSet resourceSet, BaseFBType fbType) {
		val fbResource = resourceSet.createResource(resourceSet.computeUnusedUri(FB_URI_EXTENSION))
		fbResource.contents.add(fbType)
		fbType.interfaceList.sockets.forEach[adp|createAdapterResource(resourceSet, adp)];
		fbType.interfaceList.plugs.forEach[adp|createAdapterResource(resourceSet, adp)];
		fbType.interfaceList.inputVars.forEach[v|createStructResource(resourceSet, v)];
		fbType.interfaceList.outputVars.forEach[v|createStructResource(resourceSet, v)];
		fbType.internalVars.forEach[v|createStructResource(resourceSet, v)];
	}

	def private void createAdapterResource(XtextResourceSet resourceSet, AdapterDeclaration adapter) {
		val adapterResource = resourceSet.createResource(resourceSet.computeUnusedUri(FB_URI_EXTENSION));
		adapterResource.contents.add(adapter.type.adapterFBType);
	}

	def private void createStructResource(XtextResourceSet resourceSet, VarDeclaration variable) {
		if (variable.type instanceof StructuredType) {
			val structResource = resourceSet.createResource(resourceSet.computeUnusedUri(FB_URI_EXTENSION));
			val type = variable.type as StructuredType;
			structResource.contents.add(type);
			type.memberVariables.forEach[v|createStructResource(resourceSet, v)];
		}
	}

	def private evaluateStructuredTextAlgorithm(StructuredTextAlgorithm alg) {
		alg.localVariables.filter(LocalVariable).forEach[evaluateLocalVariable]
		try {
			alg.statements.evaluateStatementList
		} catch (ReturnException e) {
			// return
		}
	}

	def private void evaluateLocalVariable(LocalVariable variable) {
		variables.put(variable, new ElementaryVariable(variable, variable.initialValue?.evaluateExpression))
	}

	def private void evaluateStatementList(StatementList list) {
		list.statements.forEach[evaluateStatement]
	}

	def private dispatch void evaluateStatement(Statement stmt) {
		error('''The statement «stmt.eClass.name» is not supported''')
		throw new UnsupportedOperationException('''The statement «stmt.eClass.name» is not supported''')
	}

	def private dispatch void evaluateStatement(AssignmentStatement stmt) {
		stmt.variable.evaluateVariable.value = stmt.expression.trap.evaluateExpression
	}

	def private dispatch void evaluateStatement(IfStatement stmt) {
		if (stmt.expression.trap.evaluateExpression.asBoolean) {
			stmt.statments.evaluateStatementList
		} else {
			(stmt.elseif.findFirst[expression.trap.evaluateExpression.asBoolean]?.statements ?:
				stmt.^else?.statements)?.evaluateStatementList
		}
	}

	def private dispatch void evaluateStatement(CaseStatement stmt) {
		val value = stmt.expression.trap.evaluateExpression;
		(stmt.^case.findFirst[^case.exists[evaluateExpression == value]]?.statements ?: stmt.^else?.statements)?.
			evaluateStatementList
	}

	def private dispatch void evaluateStatement(ForStatement stmt) {
		val variable = stmt.variable.evaluateVariable
		// from
		variable.value = stmt.from.trap.evaluateExpression
		// to
		val to = stmt.to.evaluateExpression
		// by
		val by = stmt.by?.evaluateExpression ?: 1.wrapValue(variable.declaration.type)
		// direction?
		if (by >= variable.declaration.type.defaultValue) {
			while (variable.value <= to) {
				try {
					stmt.statements.evaluateStatementList
				} catch (ContinueException e) {
					// continue
				}
				stmt.by.trap
				variable.value = variable.value + by
			}
		} else {
			while (variable.value >= to) {
				try {
					stmt.statements.evaluateStatementList
				} catch (ContinueException e) {
					// continue
				}
				stmt.by.trap
				variable.value = variable.value + by
			}
		}
	}

	def private dispatch void evaluateStatement(WhileStatement stmt) {
		while (stmt.expression.trap.evaluateExpression.asBoolean) {
			try {
				stmt.statements.evaluateStatementList
			} catch (ContinueException e) {
				// continue
			}
		}
	}

	def private dispatch void evaluateStatement(RepeatStatement stmt) {
		do {
			try {
				stmt.statements.evaluateStatementList
			} catch (ContinueException e) {
				// continue
			}
		} while (!stmt.expression.trap.evaluateExpression.asBoolean);
	}

	def private dispatch void evaluateStatement(ContinueStatement stmt) { throw new ContinueException(stmt.trap) }

	def private dispatch void evaluateStatement(ReturnStatement stmt) { throw new ReturnException(stmt.trap) }

	def private dispatch void evaluateStatement(ExitStatement stmt) { throw new StructuredTextExitException(stmt.trap, this) }

	def private dispatch Value evaluateExpression(Expression expr) {
		error('''The expression «expr.eClass.name» is not supported''')
		throw new UnsupportedOperationException('''The expression «expr.eClass.name» is not supported''')
	}

	def private dispatch Value evaluateExpression(BinaryExpression expr) {
		switch (expr.operator) {
			case ADD:
				expr.left.evaluateExpression + expr.right.evaluateExpression
			case SUB:
				expr.left.evaluateExpression - expr.right.evaluateExpression
			case MUL:
				expr.left.evaluateExpression * expr.right.evaluateExpression
			case DIV:
				expr.left.evaluateExpression / expr.right.evaluateExpression
			case MOD:
				expr.left.evaluateExpression % expr.right.evaluateExpression
			case POWER:
				expr.left.evaluateExpression ** expr.right.evaluateExpression
			case EQ:
				BoolValue.toBoolValue(expr.left.evaluateExpression == expr.right.evaluateExpression)
			case NE:
				BoolValue.toBoolValue(expr.left.evaluateExpression != expr.right.evaluateExpression)
			case LT:
				BoolValue.toBoolValue(expr.left.evaluateExpression < expr.right.evaluateExpression)
			case LE:
				BoolValue.toBoolValue(expr.left.evaluateExpression <= expr.right.evaluateExpression)
			case GT:
				BoolValue.toBoolValue(expr.left.evaluateExpression > expr.right.evaluateExpression)
			case GE:
				BoolValue.toBoolValue(expr.left.evaluateExpression >= expr.right.evaluateExpression)
			default: {
				error('''The operator «expr.operator» is not supported''')
				throw new UnsupportedOperationException('''The operator «expr.operator» is not supported''')
			}
		}
	}

	def private dispatch Value evaluateExpression(UnaryExpression expr) {
		switch (expr.operator) {
			case PLUS:
				+expr.expression.evaluateExpression
			case MINUS:
				-expr.expression.evaluateExpression
			case NOT:
				expr.expression.evaluateExpression.bitwiseNot
			default: {
				error('''The operator «expr.operator» is not supported''')
				throw new UnsupportedOperationException('''The operator «expr.operator» is not supported''')
			}
		}
	}

	def private dispatch Value evaluateExpression(BoolLiteral expr) { expr.value.toBoolValue }

	def private dispatch Value evaluateExpression(IntLiteral expr) { expr.value.toLIntValue }

	def private dispatch Value evaluateExpression(RealLiteral expr) { expr.value.toLRealValue }

	def private dispatch Value evaluateExpression(PrimaryVariable expr) {
		variables.get(expr.^var).value
	}

	def private dispatch Variable evaluateVariable(
		org.eclipse.fordiac.ide.model.structuredtext.structuredText.Variable variable) {
		throw new UnsupportedOperationException(variable.eClass + " not supported");
	}

	def private dispatch Variable evaluateVariable(PrimaryVariable variable) {
		variables.get(variable.^var)
	}

	static class StructuredTextException extends Exception {
		new(Statement statement) {
			super(statement.eClass.name)
		}
	}

	static class ContinueException extends StructuredTextException {
		new(Statement statement) {
			super(statement)
		}
	}

	static class ReturnException extends StructuredTextException {
		new(Statement statement) {
			super(statement)
		}
	}

	static class StructuredTextExitException extends EvaluatorExitException {
		new(Statement statement, Evaluator evaluator) {
			super(evaluator)
		}
	}
}

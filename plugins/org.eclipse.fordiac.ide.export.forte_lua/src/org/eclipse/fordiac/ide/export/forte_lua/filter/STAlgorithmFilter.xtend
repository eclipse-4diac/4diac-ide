/*******************************************************************************
 * Copyright (c) 2015, 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_lua.filter

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.structuredtext.parser.antlr.StructuredTextParser
import org.eclipse.fordiac.ide.model.structuredtext.resource.StructuredTextResource
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AdapterRoot
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AdapterVariable
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.ArrayVariable
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.AssignmentStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BinaryExpression
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BinaryOperator
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.BoolLiteral
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.Call
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.CaseClause
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
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StringLiteral
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.StructuredTextAlgorithm
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.UnaryExpression
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.UnaryOperator
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.WhileStatement
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.impl.AdapterVariableImpl
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.util.LazyStringInputStream

import static extension org.eclipse.emf.ecore.util.EcoreUtil.copy
import static extension org.eclipse.emf.ecore.util.EcoreUtil.getAllProperContents
import static extension org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer
import static extension org.eclipse.fordiac.ide.export.forte_lua.filter.LuaConstants.*

class STAlgorithmFilter {

	static final URI SYNTHETIC_FB_URI = URI.createFileURI("__synthetic.xtextfbt")
	static final URI SYNTHETIC_ST_URI = URI.createFileURI("__synthetic.st")

	static final IResourceServiceProvider SERVICE_PRIVIDER = IResourceServiceProvider.Registry.INSTANCE.
		getResourceServiceProvider(SYNTHETIC_ST_URI)

	@Accessors(PUBLIC_GETTER)
	var errors = new ArrayList<String>

	def lua(STAlgorithm alg) {
		val resourceSet = SERVICE_PRIVIDER.get(ResourceSet)
		// create resource for function block and add copy
		val fbResource = resourceSet.createResource(SYNTHETIC_FB_URI)
		val fbCopy = alg.rootContainer.copy
		fbResource.contents.add(fbCopy)
		// create resource for algorithm
		val resource = resourceSet.createResource(SYNTHETIC_ST_URI) as XtextResource
		resource.load(new LazyStringInputStream(alg.text), #{XtextResource.OPTION_RESOLVE_ALL -> Boolean.TRUE})
		val parseResult = resource.parseResult
		if (parseResult.hasSyntaxErrors) {
			errors.addAll(parseResult.syntaxErrors.map[it.syntaxErrorMessage.message])
			return null
		}
		val stalg = parseResult.rootASTElement as StructuredTextAlgorithm
		val usedAdapterVariables = stalg.getAllProperContents(true).filter(AdapterVariable).filter [
			getClass() == AdapterVariableImpl
		].toSet
		val usedFBVariables = stalg.getAllProperContents(true).filter(PrimaryVariable).map[it.^var].filter [
			it.rootContainer instanceof FBType
		].toSet
		return '''
			«usedFBVariables.luaFBVariablesPrefix»
			«usedAdapterVariables.luaFBAdapterVariablesPrefix»
			«stalg.luaStructuredTextAlgorithm»
			«usedFBVariables.luaFBVariablesSuffix»
			«usedAdapterVariables.luaFBAdapterVariablesSuffix»
		'''
	}

	def lua(BasicFBType fb, String expression) {
		val resourceSet = SERVICE_PRIVIDER.get(ResourceSet)
		// create resource for function block and add copy
		val fbResource = resourceSet.createResource(SYNTHETIC_FB_URI)
		val fbCopy = fb.copy
		fbResource.contents.add(fbCopy)
		// create resource for algorithm
		val resource = resourceSet.createResource(SYNTHETIC_ST_URI) as XtextResource
		val parser = resource.parser as StructuredTextParser
		resource.load(new LazyStringInputStream(expression),
			#{StructuredTextResource.OPTION_PARSER_RULE -> parser.grammarAccess.expressionRule})
		val parseResult = resource.parseResult
		if (parseResult.hasSyntaxErrors) {
			errors.addAll(parseResult.syntaxErrors.map[it.syntaxErrorMessage.message])
			return null
		}
		val expr = parseResult.rootASTElement as Expression
		return expr.luaExpression
	}

	def private luaStructuredTextAlgorithm(StructuredTextAlgorithm alg) '''
		«alg.localVariables.luaLocalVariables»
		«alg.statements.luaStatementList»
	'''

	def private luaLocalVariables(List<VarDeclaration> variables) '''
		«FOR variable : variables»
			local «variable.luaVariable»«variable.luaLocalVariableInitializer»
		«ENDFOR»
	'''

	def private luaLocalVariableInitializer(VarDeclaration variable) {
		switch (variable) {
			LocalVariable case variable.initialValue !== null: ''' = variable.initialValue.luaExpression'''
			default:
				""
		}
	}

	def private CharSequence luaStatementList(StatementList list) '''
		«FOR stmt : list.statements»
			«stmt.luaStatement»
		«ENDFOR»
	'''

	def private dispatch luaStatement(Statement stmt) {
		throw new UnsupportedOperationException(stmt.eClass + " not supported");
	}

	def private dispatch luaStatement(
		AssignmentStatement stmt) '''«stmt.variable.luaExpression» = «stmt.expression.luaExpression»'''

	def private dispatch luaStatement(Call stmt) {
		stmt.luaExpression
	}

	def private dispatch luaStatement(ReturnStatement stmt) { "return" }

	def private dispatch luaStatement(IfStatement stmt) '''
	if «stmt.expression.luaExpression» then
	  «stmt.statments.luaStatementList»
	«FOR elseif : stmt.elseif»
		elseif «elseif.expression.luaExpression» then
		  «elseif.statements.luaStatementList»
	«ENDFOR»
	«IF stmt.^else !== null »
		else
		  «stmt.^else.statements.luaStatementList»
	«ENDIF»
	end'''

	def private dispatch luaStatement(CaseStatement stmt) '''
	local function case(val)
	  «FOR clause : stmt.^case BEFORE 'if ' SEPARATOR '\nelseif '»«clause.luaCaseClause»«ENDFOR»
	  «IF stmt.^else !== null »
	  	else
	  	  «stmt.^else.statements.luaStatementList»
	  «ENDIF»
	  end
	end
	case(«stmt.expression.luaExpression»)'''

	def private luaCaseClause(CaseClause clause) '''
	«FOR value : clause.^case SEPARATOR ' or '»val == «value.luaExpression»«ENDFOR» then
	  «clause.statements.luaStatementList»'''

	def private dispatch luaStatement(ExitStatement stmt) { "break" }

	def private dispatch luaStatement(ContinueStatement stmt) { "continue" }

	def private dispatch luaStatement(ForStatement stmt) '''
	for «stmt.variable.luaExpression» = «stmt.from.luaExpression», «stmt.to.luaExpression»«IF stmt.by !== null», «stmt.by.luaExpression»«ENDIF» do
	  «stmt.statements.luaStatementList»
	end'''

	def private dispatch luaStatement(WhileStatement stmt) '''
	while «stmt.expression.luaExpression» do
	  «stmt.statements.luaStatementList»
	end'''

	def private dispatch luaStatement(RepeatStatement stmt) '''
	repeat
	  «stmt.statements.luaStatementList»
	until «stmt.expression.luaExpression»'''

	def private dispatch CharSequence luaExpression(Expression expr) {
		throw new UnsupportedOperationException(expr.eClass + " not supported");
	}

	def private dispatch CharSequence luaExpression(
		BinaryExpression expr) '''(«expr.left.luaExpression» «expr.operator.luaBinaryOperator» «expr.right.luaExpression»)'''

	def private luaBinaryOperator(BinaryOperator op) {
		switch (op) {
			case OR: "or"
			case XOR: "~"
			case AND: "and"
			case AMPERSAND: "and"
			case EQ: "=="
			case NE: "~="
			case LT: "<"
			case LE: "<="
			case GT: ">"
			case GE: ">="
			case ADD: "+"
			case SUB: "-"
			case MUL: "*"
			case DIV: "/"
			case MOD: "%"
			case POWER: "^"
		}
	}

	def private dispatch CharSequence luaExpression(
		UnaryExpression expr) '''(«expr.operator.luaUnaryOperator» «expr.expression.luaExpression»)'''

	def private luaUnaryOperator(UnaryOperator op) {
		switch (op) {
			case MINUS: "-"
			case PLUS: "+"
			case NOT: "not"
		}
	}

	def private dispatch CharSequence luaExpression(BoolLiteral expr) '''«expr.value.toString»'''

	def private dispatch CharSequence luaExpression(IntLiteral expr) '''«expr.value.toString»'''

	def private dispatch CharSequence luaExpression(RealLiteral expr) '''«expr.value.toString»'''

	def private dispatch CharSequence luaExpression(StringLiteral expr) '''«expr.value.toString»'''

	def private dispatch CharSequence luaExpression(
		ArrayVariable expr) '''«expr.array.luaExpression»«FOR index : expr.index BEFORE '[' SEPARATOR '][' AFTER ']'»(«index.luaExpression») + 1«ENDFOR»'''

	def private dispatch CharSequence luaExpression(
		AdapterVariable expr) '''«expr.^var.name.luaAdapterVariable((expr.curr as AdapterRoot).adapter.name)»'''

	def private dispatch CharSequence luaExpression(PrimaryVariable expr) '''«expr.^var.luaVariable»'''
}

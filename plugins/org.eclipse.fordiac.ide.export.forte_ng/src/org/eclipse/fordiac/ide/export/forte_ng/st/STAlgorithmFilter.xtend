/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *               2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Ernst Blecha - add multibit partial access
 *******************************************************************************/

package org.eclipse.fordiac.ide.export.forte_ng.st

import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.structuredtext.parser.antlr.StructuredTextParser
import org.eclipse.fordiac.ide.model.structuredtext.resource.StructuredTextResource
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
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.PartialAccess
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.util.LazyStringInputStream

import static extension org.eclipse.emf.ecore.util.EcoreUtil.copy
import static extension org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer
import static extension org.eclipse.xtext.util.Strings.convertToJavaString

class STAlgorithmFilter {

	static final URI SYNTHETIC_FB_URI = URI.createFileURI("__synthetic.xtextfbt")
	static final URI SYNTHETIC_ST_URI = URI.createFileURI("__synthetic.st")

	static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE.getResourceServiceProvider(SYNTHETIC_ST_URI)

	def CharSequence generate(STAlgorithm alg, List<String> errors) {
		val resourceSet = SERVICE_PROVIDER.get(ResourceSet)
		// create resource for function block and add copy
		val fbResource = resourceSet.createResource(SYNTHETIC_FB_URI)
		val fbCopy = alg.rootContainer.copy
		fbResource.contents.add(fbCopy)
		// create resource for algorithm
		val resource = resourceSet.createResource(SYNTHETIC_ST_URI) as XtextResource
		resource.load(new LazyStringInputStream(alg.text), #{XtextResource.OPTION_RESOLVE_ALL -> Boolean.TRUE})
		val parseResult = resource.parseResult
		if(parseResult.hasSyntaxErrors) {
			errors.addAll(parseResult.syntaxErrors.map[it.syntaxErrorMessage.message])
			return null
		}
		val stalg = parseResult.rootASTElement as StructuredTextAlgorithm
		return stalg.generateStructuredTextAlgorithm
	}

	def CharSequence generate(String expression, BasicFBType fb, List<String> errors) {
		val resourceSet = SERVICE_PROVIDER.get(ResourceSet)
		// create resource for function block and add copy
		val fbResource = resourceSet.createResource(SYNTHETIC_FB_URI)
		val fbCopy = fb.copy
		fbResource.contents.add(fbCopy)
		// create resource for algorithm
		val resource = resourceSet.createResource(SYNTHETIC_ST_URI) as XtextResource
		val parser = resource.parser as StructuredTextParser
		resource.load(new LazyStringInputStream(expression), #{StructuredTextResource.OPTION_PARSER_RULE -> parser.grammarAccess.expressionRule})
		val parseResult = resource.parseResult
		if(parseResult.hasSyntaxErrors) {
			errors.addAll(parseResult.syntaxErrors.map[it.syntaxErrorMessage.message])
			return null
		}
		val expr = parseResult.rootASTElement as Expression
		return expr.generateExpression
	}

	def protected CharSequence generateStructuredTextAlgorithm(StructuredTextAlgorithm alg) '''
		«alg.localVariables.generateLocalVariables»
		«alg.statements.generateStatementList»
	'''

	def protected CharSequence generateLocalVariables(List<VarDeclaration> variables) '''
		«FOR variable : variables»
			CIEC_«variable.type.name» «variable.name»«variable.generateLocalVariableInitializer»;
		«ENDFOR»
	'''

	def protected CharSequence generateLocalVariableInitializer(VarDeclaration variable) {
		switch (variable) {
			LocalVariable case variable.initialValue !== null: ''' = «variable.initialValue.generateExpression»'''
			default:
				""
		}
	}

	def protected CharSequence generateStatementList(StatementList list) '''
		«FOR stmt : list.statements»
			«stmt.generateStatement»
		«ENDFOR»
	'''

	def protected dispatch CharSequence generateStatement(Statement stmt) {
		throw new UnsupportedOperationException(stmt.eClass + " not supported");
	}

	def protected dispatch CharSequence generateStatement(AssignmentStatement stmt) '''«stmt.variable.generateExpression» = «stmt.expression.generateExpression»;'''

	def protected dispatch CharSequence generateStatement(Call stmt) {
		return stmt.generateExpression
	}

	def protected dispatch CharSequence generateStatement(ReturnStatement stmt) '''return;'''

	def protected dispatch CharSequence generateStatement(IfStatement stmt) '''
		if(«stmt.expression.generateExpression») {
		  «stmt.statments.generateStatementList»
		}
		«FOR elseif : stmt.elseif»
			else if(«elseif.expression.generateExpression») {
			  «elseif.statements.generateStatementList»
			}
		«ENDFOR»
		«IF stmt.^else !== null»
			else {
			  «stmt.^else.statements.generateStatementList»
			}
		«ENDIF»
	'''

	def protected dispatch CharSequence generateStatement(CaseStatement stmt) '''
		local function case(val)
		  «FOR clause : stmt.^case BEFORE 'if ' SEPARATOR '\nelseif '»«clause.generateCaseClause»«ENDFOR»
		  «IF stmt.^else !== null»
		  	else
		  	  «stmt.^else.statements.generateStatementList»
		  «ENDIF»
		  end
		end
		case(«stmt.expression.generateExpression»)
	'''

	def protected CharSequence generateCaseClause(CaseClause clause) '''
		«FOR value : clause.^case SEPARATOR ' or '»val == «value.generateExpression»«ENDFOR» then
		  «clause.statements.generateStatementList»
	'''

	def protected dispatch CharSequence generateStatement(ExitStatement stmt) '''break;'''

	def protected dispatch CharSequence generateStatement(ContinueStatement stmt) '''continue;'''

	def protected dispatch CharSequence generateStatement(ForStatement stmt) '''
		for «stmt.variable.generateExpression» = «stmt.from.generateExpression», «stmt.to.generateExpression»«IF stmt.by !== null», «stmt.by.generateExpression»«ENDIF» do
		  «stmt.statements.generateStatementList»
		end
	'''

	def protected dispatch CharSequence generateStatement(WhileStatement stmt) '''
		while(«stmt.expression.generateExpression») {
		  «stmt.statements.generateStatementList»
		}
	'''

	def protected dispatch CharSequence generateStatement(RepeatStatement stmt) '''
		do {
		  «stmt.statements.generateStatementList»
		} while(!((«stmt.expression.generateExpression»)));
	'''

	def protected dispatch CharSequence generateExpression(Expression expr) {
		throw new UnsupportedOperationException(expr.eClass + " not supported");
	}

	def protected dispatch CharSequence generateExpression(
		BinaryExpression expr) '''(«expr.left.generateExpression» «expr.operator.generateBinaryOperator» «expr.right.generateExpression»)'''

	def protected CharSequence generateBinaryOperator(BinaryOperator op) {
		switch (op) {
			case OR:
				"||"
			case XOR:
				"^"
			case AND:
				"&&"
			case EQ:
				"=="
			case NE:
				"!="
			case LT:
				"<"
			case LE:
				"<="
			case GT:
				">"
			case GE:
				">="
			case ADD:
				"+"
			case SUB:
				"-"
			case MUL:
				"*"
			case DIV:
				"/"
			case MOD:
				"%"
			default:
				throw new UnsupportedOperationException('''The operator «op» is not supported''')
		}
	}

	def protected dispatch CharSequence generateExpression(UnaryExpression expr) '''(«expr.operator.generateUnaryOperator» «expr.expression.generateExpression»)'''

	def protected CharSequence generateUnaryOperator(UnaryOperator op) {
		switch (op) {
			case MINUS:
				"-"
			case PLUS:
				"+"
			case NOT:
				"!"
		}
	}

	def protected dispatch CharSequence generateExpression(BoolLiteral expr) {
		Boolean.toString(expr.value)
	}

	def protected dispatch CharSequence generateExpression(IntLiteral expr) {
		Long.toString(expr.value)
	}

	def protected dispatch CharSequence generateExpression(RealLiteral expr) {
		Double.toString(expr.value)
	}

	def protected dispatch CharSequence generateExpression(StringLiteral expr) '''"«expr.value.convertToJavaString»"'''

	def protected dispatch CharSequence generateExpression(
		ArrayVariable expr) '''«expr.array.generateExpression»«FOR index : expr.index BEFORE '[' SEPARATOR '][' AFTER ']'»(«index.generateExpression») + 1«ENDFOR»'''

	def protected dispatch CharSequence generateExpression(AdapterVariable expr) {
		'''«expr.adapter.name»().«expr.^var.name»()«IF null !== expr.part»«generateBitaccess(expr.part)»«ENDIF»'''
	}

	def protected dispatch CharSequence generateExpression(PrimaryVariable expr) {
		'''«expr.^var.name»()«IF null !== expr.part»«generateBitaccess(expr.part)»«ENDIF»'''
	}

	def protected CharSequence generateBitaccess(PartialAccess part) '''
		«IF part.bitaccess
			».X<«Long.toString(part.index)»>()«
		 ELSEIF part.byteaccess
		 	».B<«Long.toString(part.index)»>()«
		 ELSEIF part.wordaccess
		 	».W<«Long.toString(part.index)»>()«
		 ELSEIF part.dwordaccess
		 	».D<«Long.toString(part.index)»>()«
		 ENDIF»'''
}

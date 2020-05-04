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
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.LocatedVariable

import org.eclipse.xtext.validation.CheckMode
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
import org.eclipse.fordiac.ide.model.structuredtext.structuredText.InArgument

class STAlgorithmFilter {

	static final String SYNTHETIC_URI_NAME = "__synthetic" //$NON-NLS-1$
	static final String URI_SEPERATOR = "." //$NON-NLS-1$
	static final String FB_URI_EXTENSION = "xtextfbt" //$NON-NLS-1$
	static final String ST_URI_EXTENSION = "st" //$NON-NLS-1$

	static final IResourceServiceProvider SERVICE_PROVIDER = IResourceServiceProvider.Registry.INSTANCE.getResourceServiceProvider(URI.createURI(SYNTHETIC_URI_NAME + URI_SEPERATOR + ST_URI_EXTENSION))

	def createFBResource(XtextResourceSet resourceSet, BaseFBType fbType) {
		// create resource for function block and add copy
		val fbResource = resourceSet.createResource(resourceSet.computeUnusedUri(FB_URI_EXTENSION))
		fbResource.contents.add(fbType)
		for (AdapterDeclaration adapter : fbType.getInterfaceList().getSockets()) {
			createAdapterResource(resourceSet, adapter)
		}
		for (AdapterDeclaration adapter : fbType.getInterfaceList().getPlugs()) {
			createAdapterResource(resourceSet, adapter)
		}
	}

	def createAdapterResource(XtextResourceSet resourceSet, AdapterDeclaration adapter) {
		val adapterResource = resourceSet.createResource(resourceSet.computeUnusedUri(FB_URI_EXTENSION));
		adapterResource.getContents().add(adapter.getType().getAdapterFBType());
	}

	def protected URI computeUnusedUri(ResourceSet resourceSet, String fileExtension) {
		for (i: 0..<Integer.MAX_VALUE) {
			val syntheticUri = URI.createURI(SYNTHETIC_URI_NAME + i + URI_SEPERATOR + fileExtension) //$NON-NLS-1$
			if (resourceSet.getResource(syntheticUri, false) === null) {
				return syntheticUri
			}
		}
		throw new IllegalStateException()
	}

	def CharSequence generate(STAlgorithm alg, List<String> errors) {
		val resourceSet = SERVICE_PROVIDER.get(ResourceSet) as XtextResourceSet
		createFBResource(resourceSet, alg.rootContainer as BaseFBType)
		// create resource for algorithm
		val resource = resourceSet.createResource(resourceSet.computeUnusedUri(ST_URI_EXTENSION)) as XtextResource
		resource.load(new LazyStringInputStream(alg.text), #{XtextResource.OPTION_RESOLVE_ALL -> Boolean.TRUE})
		val parseResult = resource.parseResult
		val validator = resource.resourceServiceProvider.resourceValidator
		val issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl)
		if (!issues.empty) {
			errors.addAll(issues.map[alg.name + ", Line " + Long.toString(it.lineNumber) + ": " + it.message])
			return null
		}
		val stalg = parseResult.rootASTElement as StructuredTextAlgorithm
		return stalg.generateStructuredTextAlgorithm
	}

	def CharSequence generate(String expression, BasicFBType fb, List<String> errors) {
		val resourceSet = SERVICE_PROVIDER.get(ResourceSet) as XtextResourceSet
		createFBResource(resourceSet, fb.copy as BaseFBType)
 		// create resource for algorithm
		val resource = resourceSet.createResource(resourceSet.computeUnusedUri(ST_URI_EXTENSION)) as XtextResource
		val parser = resource.parser as StructuredTextParser
		resource.load(new LazyStringInputStream(expression), #{StructuredTextResource.OPTION_PARSER_RULE -> parser.grammarAccess.expressionRule})
		val parseResult = resource.parseResult
		val validator = resource.resourceServiceProvider.resourceValidator
		val issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl)
		if (!issues.empty) {
			errors.addAll(issues.map[it.message])
			return null
		}
		val expr = parseResult.rootASTElement as Expression
		return expr.generateExpression
	}

	def protected CharSequence generateStructuredTextAlgorithm(StructuredTextAlgorithm alg) '''
		«alg.localVariables.generateLocalVariables»
		«alg.statements.generateStatementList»
	'''

	def private int BitSize(String str) {
		switch (str) {
			case str.equals("LWORD"): 64
			case str.equals("DWORD"): 32
			case str.equals("WORD"):  16
			case str.equals("BYTE"):   8
			case str.equals("BOOL"):   1
			default:                   0
		}
	}

	def protected CharSequence generateArrayDecl(LocalVariable variable) 
	'''«IF variable.located
			»«variable.generateArrayDeclLocated»«
		ELSE
			»«variable.generateArrayDeclLocal»«
		ENDIF»'''

	def protected CharSequence generateArrayDeclLocated(LocalVariable variable) {
		val l = variable.location
		switch (l) {
			PrimaryVariable: '''
				«IF variable.type.name.BitSize > 0 && l.^var.type.name.BitSize > 0»
					«IF l.^var.type.name.BitSize > variable.type.name.BitSize»
						ARRAY_AT<CIEC_«variable.type.name», CIEC_«l.^var.type.name», 0, «variable.arraySize-1»> «variable.name»(«l.^var.name»);
					«ELSE»
						#error Accessing CIEC_«l.^var.type.name» via CIEC_«variable.type.name» would result in undefined behaviour
					«ENDIF»
				«ELSE»
				    #error Piecewise access is supported only for types with defined bit-representation (e.g. not CIEC_«l.^var.type.name» via CIEC_«variable.type.name») 
				«ENDIF»
			'''
			default: '''#error unhandled located array'''
		}
	}

	def protected CharSequence generateArrayDeclLocal(LocalVariable variable) '''
		CIEC_«variable.type.name» «variable.name»[«variable.arraySize»]«variable.generateLocalVariableInitializer»;
	'''

	def protected CharSequence generateVariableDeclLocated(LocalVariable variable) {
		val l = variable.location
		switch (l) {
			PrimaryVariable: '''// replacing all instances of «variable.extractTypeInformation»:«variable.name» with «variable.generateVarAccess»''' //names will just be replaced during export
			default: '''#error located variable of unhandled type'''
		}
	}

	def protected CharSequence generateVariableDeclLocal(LocalVariable variable) '''
		CIEC_«variable.type.name» «variable.name»«variable.generateLocalVariableInitializer»;
	'''

	def protected CharSequence generateLocalVariables(List<VarDeclaration> variables) '''
		«FOR variable : variables»
			«switch (variable) {
				LocalVariable case !variable.located && !variable.array             : variable.generateVariableDeclLocal
				LocalVariable case !variable.located &&  variable.array             : variable.generateArrayDeclLocal
				LocalVariable case variable.located && null !== variable.location && !variable.array : variable.generateVariableDeclLocated
				LocalVariable case variable.located && null !== variable.location &&  variable.array : variable.generateArrayDeclLocated
			}»
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
	switch («stmt.expression.generateExpression») {
		«FOR clause : stmt.^case»«clause.generateCaseClause»«ENDFOR»
		«IF stmt.^else !== null»
		default:
			«stmt.^else.statements.generateStatementList»
			break;
		«ENDIF»
	}
	'''

	def protected CharSequence generateCaseClause(CaseClause clause) '''
		case «FOR value : clause.^case SEPARATOR ' case '»«value.generateExpression»:«ENDFOR»
			«clause.statements.generateStatementList»
			break;
	'''

	def protected dispatch CharSequence generateStatement(ExitStatement stmt) '''break;'''

	def protected dispatch CharSequence generateStatement(ContinueStatement stmt) '''continue;'''

	def protected dispatch CharSequence generateStatement(ForStatement stmt) '''
		// as it is done in lua: https://www.lua.org/manual/5.1/manual.html#2.4.5
		auto by = «IF stmt.by !== null »«stmt.by.generateExpression»« ELSE »1« ENDIF»;
		auto to = «stmt.to.generateExpression»;
		for(«stmt.variable.generateExpression» = «stmt.from.generateExpression»;
		    (by >  0 && «stmt.variable.generateExpression» <= to) ||
		    (by <= 0 && «stmt.variable.generateExpression» >= to);
		    «stmt.variable.generateExpression» = «stmt.variable.generateExpression» + by){
			«stmt.statements.generateStatementList»
		}
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

	def protected dispatch CharSequence generateExpression(Call expr) {
		'''«expr.func»(«FOR arg : expr.args SEPARATOR ', '»«arg.generateExpression»«ENDFOR»)'''
	}
	
	def protected dispatch CharSequence generateExpression(InArgument arg) {
		arg.expr.generateExpression
	}

	def protected dispatch CharSequence generateExpression(Expression expr) {
		throw new UnsupportedOperationException(expr.eClass + " not supported");
	}

	def protected dispatch CharSequence generateExpression(
		BinaryExpression expr) {
			switch (expr.operator) {
			case POWER:
				'''EXPT(«expr.left.generateExpression», «expr.right.generateExpression»)'''
			default:
				'''(«expr.left.generateExpression» «expr.operator.generateBinaryOperator» «expr.right.generateExpression»)'''
			}
		}

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
		ArrayVariable expr) '''«expr.array.generateExpression»«FOR index : expr.index BEFORE '[' SEPARATOR '][' AFTER ']'»«index.generateExpression»«ENDFOR»'''

	def protected dispatch CharSequence generateExpression(AdapterVariable expr) {
		'''«expr.adapter.name»().«expr.^var.name»()«expr.generateBitaccess»'''
	}

	def protected dispatch CharSequence generateExpression(PrimaryVariable expr)  '''«expr.^var.generateVarAccess»«expr.generateBitaccess»'''

	def protected dispatch CharSequence generateVarAccess(VarDeclaration variable) '''«variable.name»()'''

	def protected dispatch CharSequence generateVarAccess(LocalVariable variable) 	
	'''«IF variable.located
			»«variable.generateVarAccessLocated»«
		ELSE
			»«variable.generateVarAccessLocal»«
		ENDIF»'''

	def protected CharSequence generateVarAccessLocal(LocalVariable variable) '''«variable.name»'''

	def protected CharSequence generateVarAccessLocated(LocalVariable variable)
		'''«IF variable.array
				»«variable.name»«
			ELSE
				»«variable.location.generateExpression»«generateBitaccess(variable, variable.location.extractTypeInformation,variable.extractTypeInformation,0)»«
			ENDIF»'''

	def protected CharSequence generateBitaccess(AdapterVariable variable) {
		if (null !== variable.part) {
			generateBitaccess(variable.^var, variable.^var.type.name, variable.extractTypeInformation, variable.part.index)
		}
	}

	def protected CharSequence generateBitaccess(PrimaryVariable variable) {
		if (null !== variable.part) {
			generateBitaccess(variable.^var, variable.^var.type.name, variable.extractTypeInformation, variable.part.index)
		}
	}

	def protected CharSequence generateBitaccess(VarDeclaration variable, String DataType, String AccessorType, int Index) {
		if (BitSize(AccessorType) > 0 && variable.array && variable.arraySize * BitSize(DataType) > BitSize(AccessorType)) {
			'''.partial<CIEC_«AccessorType»,«Long.toString(Index)»>()'''
		} else if (BitSize(DataType) == BitSize(AccessorType)) {
			''''''
		} else {
			''''''//This should never happen - we cannot access more bits than are available in the source type
		}
	}

	def private dispatch String extractTypeInformation(PartialAccess part, String DataType) {
		if (null !== part) {
			if (part.bitaccess)        "BOOL"
			else if (part.byteaccess)  "BYTE"
			else if (part.wordaccess)  "WORD"
			else if (part.dwordaccess) "DWORD"
			else                       ""
		} else                         DataType
	}

	def private dispatch String extractTypeInformation(PrimaryVariable variable, String DataType) {
		if (null !== variable.part) {
			extractTypeInformation(variable.part, DataType)
		} else {
			DataType
		}
	}

	def protected dispatch String extractTypeInformation(PrimaryVariable variable) {
		variable.extractTypeInformation(variable.^var.extractTypeInformation)
	}

	def protected dispatch String extractTypeInformation(LocalVariable variable) {
		variable.type.name
	}

	def protected dispatch String extractTypeInformation(VarDeclaration variable) {
		variable.type.name
	}

}

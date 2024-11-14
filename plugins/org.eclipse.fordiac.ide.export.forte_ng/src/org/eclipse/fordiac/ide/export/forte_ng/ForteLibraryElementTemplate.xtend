/*******************************************************************************
 * Copyright (c) 2019, 2024 fortiss GmbH
 *                          Johannes Kepler Unviersity Linz
 *                          Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Alois Zoitl  - extracted base class for all types from fbtemplate
 *   Martin Jobst - generate variable default values with constant expressions
 *                - refactor memory layout
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng

import java.nio.file.Path
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.xtend.lib.annotations.Accessors

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*
import static extension org.eclipse.xtext.EcoreUtil2.*
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.emf.common.util.EList

abstract class ForteLibraryElementTemplate<T extends LibraryElement> extends ForteNgExportTemplate {

	@Accessors(PROTECTED_GETTER) final T type
	final Map<VarDeclaration, ILanguageSupport> variableLanguageSupport

	new(T type, String name, Path prefix, Map<?,?> options) {
		super(name, prefix)
		this.type = type;
		variableLanguageSupport = type.getAllContentsOfType(VarDeclaration).toInvertedMap [
			ILanguageSupportFactory.createLanguageSupport("forte_ng", it, options)
		]
	}

	def protected getClassName() { type.generateTypeName }

	def protected generateHeader() '''
		/*************************************************************************
		 *** FORTE Library Element
		 ***
		 *** This file was generated using the 4DIAC FORTE Export Filter V1.0.x NG!
		 ***
		 *** Name: «type.name»
		 *** Description: «type.comment.escapeMultilineCommentString»
		 *** Version:
		 «FOR info : type.versionInfo»
		 	***     «info.version.escapeMultilineCommentString»: «info.date.escapeMultilineCommentString»/«info.author.escapeMultilineCommentString» - «info.organization.escapeMultilineCommentString» - «info.remarks.escapeMultilineCommentString»
		 «ENDFOR»
		 *************************************************************************/
	'''

	def protected generateIncludeGuardStart() '''
		#pragma once
	'''

	def protected generateIncludeGuardEnd() '''
	'''

	def protected generateVariableDeclarations(List<VarDeclaration> variables, boolean const) '''
		«FOR variable : variables AFTER '\n'»
			«IF const»static const «ENDIF»«variable.generateVariableTypeName» «variable.generateName»;
		«ENDFOR»
	'''

	def protected generateVariableDefinitions(List<VarDeclaration> variables, boolean const) '''
		«FOR variable : variables AFTER '\n'»
			«IF const»const «ENDIF»«variable.generateVariableTypeName» «className»::«variable.generateName» = «variable.generateVariableDefaultValue»;
		«ENDFOR»
	'''

	def protected generateVariableInitializer(Iterable<VarDeclaration> variables) ///
	'''«FOR variable : variables.filter[!value?.value.nullOrEmpty] BEFORE ",\n" SEPARATOR ",\n"»«variable.generateName»(«variable.generateVariableDefaultValue»)«ENDFOR»'''

	def protected generateVariableInitializerFromParameters(Iterable<VarDeclaration> variables) //
	'''«FOR variable : variables BEFORE ",\n" SEPARATOR ",\n"»«variable.generateName»(«variable.generateNameAsParameter»)«ENDFOR»'''

	def protected generateAdapterDeclarations(List<AdapterDeclaration> adapters) '''
		«FOR adapter : adapters AFTER '\n'»
			«adapter.type.generateTypeName» «adapter.generateName»;
		«ENDFOR»
	'''

	def protected generateAdapterInitializer(Iterable<AdapterDeclaration> adapters) ///
	'''«FOR adapter : adapters BEFORE ",\n" SEPARATOR ",\n"»«adapter.generateName»(«adapter.name.FORTEStringId», *this, «!adapter.isIsInput»)«ENDFOR»'''

	def protected generateAccessorDeclaration(String function, boolean const) {
		generateAccessorDeclaration(function, "CIEC_ANY *", const)
	}

	def protected generateAccessorDeclaration(String function, String type, boolean const) '''
		«IF const»const «ENDIF»«type»«function»(size_t)«IF const» const«ENDIF» override;
	'''

	def protected generateAccessorDefinition(List<VarDeclaration> variables, String function, boolean const) {
		generateAccessorDefinition(variables, function, "CIEC_ANY *", const)
	}

	def protected generateAccessorDefinition(List<? extends IInterfaceElement> variables, String function, String type, boolean const) '''
		«IF variables.empty»
			«IF const»const «ENDIF»«type»«className»::«function»(size_t)«IF const» const«ENDIF» {
			  return nullptr;
			}
			
		«ELSE»
			«IF const»const «ENDIF»«type»«className»::«function»(const size_t paIndex)«IF const» const«ENDIF» {
			  switch(paIndex) {
			    «FOR variable : variables»
			    	case «variables.indexOf(variable)»: return &«variable.generateName»;
			    «ENDFOR»
			  }
			  return nullptr;
			}
			
		«ENDIF»
	'''
	
	def protected CharSequence generateNameAsParameter(VarDeclaration variable) '''pa«variable.name»'''

	def CharSequence generateVariableDefaultValue(VarDeclaration decl) {
		variableLanguageSupport.get(decl)?.generate(emptyMap)
	}

	def CharSequence generateVariableTypeName(VarDeclaration decl) {
		variableLanguageSupport.get(decl)?.generate(#{ForteNgExportFilter.OPTION_TYPE -> Boolean.TRUE})
	}

	def CharSequence generateVariableTypeNameAsParameter(VarDeclaration decl) {
		variableLanguageSupport.get(decl)?.generate(#{ForteNgExportFilter.OPTION_TYPE_PARAM -> Boolean.TRUE})
	}

	def CharSequence generateVariableTypeSpec(VarDeclaration decl) {
		variableLanguageSupport.get(decl)?.generate(#{ForteNgExportFilter.OPTION_TYPE_SPEC -> Boolean.TRUE})
	}

	def protected getFORTENameList(List<? extends INamedElement> elements) {
		elements.map[name.FORTEStringId].join(", ")
	}

	def protected getFORTETypeList(List<? extends VarDeclaration> elements) {
		elements.map[generateVariableTypeSpec].join(", ")
	}
	
	def protected getFORTEEventTypeList(List<? extends Event> elements) {
		elements.map[it.typeName.FORTEStringId].join(", ")
	}

	override getErrors() {
		(super.getErrors + variableLanguageSupport.values.filterNull.flatMap[getErrors].toSet).toList
	}

	override getWarnings() {
		(super.getWarnings + variableLanguageSupport.values.filterNull.flatMap[getWarnings].toSet).toList
	}

	override getInfos() {
		(super.getInfos + variableLanguageSupport.values.filterNull.flatMap[getInfos].toSet).toList
	}

	def Set<INamedElement> getDependencies(Map<?, ?> options) {
		variableLanguageSupport.values.filterNull.flatMap[getDependencies(options)].toSet
	}
}

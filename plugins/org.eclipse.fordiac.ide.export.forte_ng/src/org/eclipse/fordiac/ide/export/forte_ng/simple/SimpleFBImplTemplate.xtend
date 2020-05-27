package org.eclipse.fordiac.ide.export.forte_ng.simple

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.export.forte_ng.st.STAlgorithmFilter
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
import org.eclipse.xtend.lib.annotations.Accessors

class SimpleFBImplTemplate extends ForteFBTemplate {

	@Accessors(PROTECTED_GETTER) SimpleFBType type
	extension STAlgorithmFilter stAlgorithmFilter = new STAlgorithmFilter

	new(SimpleFBType type, String name, Path prefix) {
		super(name, prefix)
		this.type = type
	}

	override generate() '''
		«generateHeader»

		«generateImplIncludes»

		«generateFBDefinition»

		«generateFBInterfaceDefinition»

		«generateFBInterfaceSpecDefinition»

        «IF !type.internalVars.isEmpty»
          «generateInternalVarDefinition(type)»

        «ENDIF»
		«generateAlgorithms»

	'''

	def protected generateAlgorithms() '''
		«type.algorithm.generateAlgorithm»
	'''

	def protected dispatch generateAlgorithm(Algorithm alg) {
		errors.add('''Cannot export algorithm «alg.class»''')
		return ""
	}

	def protected dispatch generateAlgorithm(STAlgorithm alg) '''
		void «FBClassName»::alg_«alg.name»(void) {
		  «alg.generate(errors)»
		}
	'''
}

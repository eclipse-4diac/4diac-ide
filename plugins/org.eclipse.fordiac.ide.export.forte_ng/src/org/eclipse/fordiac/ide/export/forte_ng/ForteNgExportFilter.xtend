/*******************************************************************************
 * Copyright (c) 2019, 2025 fortiss GmbH, Johannes Kepler University Linz
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
 *                - added support for custom language templates
 *                - added support for global constants
 *   Alois Zoitl  - added support for structured types
 *   Ernst Blecha - added support for exporting CMakeLists.txt
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng

import java.nio.file.Path
import java.nio.file.Paths
import java.text.MessageFormat
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.export.Messages
import org.eclipse.fordiac.ide.export.TemplateExportFilter
import org.eclipse.fordiac.ide.export.forte_ng.adapter.AdapterFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.adapter.AdapterFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.basic.BasicFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.basic.BasicFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.cmake.CMakeListsUtil
import org.eclipse.fordiac.ide.export.forte_ng.cmake.IncludeCMakeListsTemplate
import org.eclipse.fordiac.ide.export.forte_ng.cmake.ProjectCMakeListsTemplate
import org.eclipse.fordiac.ide.export.forte_ng.cmake.SourceCMakeListsTemplate
import org.eclipse.fordiac.ide.export.forte_ng.composite.CompositeFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.composite.CompositeFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.function.FunctionFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.function.FunctionFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.globalconst.GlobalConstantsHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.globalconst.GlobalConstantsImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.language.LanguageHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.language.LanguageImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.service.ServiceInterfaceFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.service.ServiceInterfaceFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.simple.SimpleFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.simple.SimpleFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.struct.StructuredTypeHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.struct.StructuredTypeImplTemplate
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType
import org.eclipse.fordiac.ide.model.typelibrary.CMakeListsMarker

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

class ForteNgExportFilter extends TemplateExportFilter {

	public static final String OPTION_TYPE = "type"
	public static final String OPTION_TYPE_PARAM = "type_param"
	public static final String OPTION_HEADER = "header"

	override protected getTemplates(String name, EObject source) {
		val cache = newHashMap
		val options = #{ILanguageSupport.OPTION_CACHE -> cache}
		switch (source) {
			BasicFBType:
				#{
					new BasicFBHeaderTemplate(source, source.generateTypeHeaderFileName,
						source.generateTypeHeaderFilePath, options),
					new BasicFBImplTemplate(source, source.generateTypeSourceFileName,
						source.generateTypeSourceFilePath, options)
				}
			SimpleFBType:
				#{
					new SimpleFBHeaderTemplate(source, source.generateTypeHeaderFileName,
						source.generateTypeHeaderFilePath, options),
					new SimpleFBImplTemplate(source, source.generateTypeSourceFileName,
						source.generateTypeSourceFilePath, options)
				}
			FunctionFBType:
				#{
					new FunctionFBHeaderTemplate(source, source.generateTypeHeaderFileName,
						source.generateTypeHeaderFilePath, options),
					new FunctionFBImplTemplate(source, source.generateTypeSourceFileName,
						source.generateTypeSourceFilePath, options)
				}
			SubAppType,
			AttributeDeclaration,
			AutomationSystem: // SubAppType is derived from CompositeFBType and needs to be handled first
			{
				warnings.add(
					MessageFormat.format(Messages.TemplateExportFilter_PREFIX_ERRORMESSAGE_WITH_TYPENAME,
						source.typeEntry.file.fullPath.toString, Messages.TemplateExportFilter_FILE_IGNORED))
				emptySet
			}
			CompositeFBType:
				#{
					new CompositeFBHeaderTemplate(source, source.generateTypeHeaderFileName,
						source.generateTypeHeaderFilePath, options),
					new CompositeFBImplTemplate(source, source.generateTypeSourceFileName,
						source.generateTypeSourceFilePath, options)
				}
			AdapterType:
				#{
					new AdapterFBHeaderTemplate(source, source.generateTypeHeaderFileName,
						source.generateTypeHeaderFilePath, options),
					new AdapterFBImplTemplate(source, source.generateTypeSourceFileName,
						source.generateTypeSourceFilePath, options)
				}
			ServiceInterfaceFBType:
				#{
					new ServiceInterfaceFBHeaderTemplate(source, source.generateTypeHeaderFileName,
						source.generateTypeHeaderFilePath, options),
					new ServiceInterfaceFBImplTemplate(source, source.generateTypeSourceFileName,
						source.generateTypeSourceFilePath, options)
				}
			StructuredType:
				#{
					new StructuredTypeHeaderTemplate(source, source.generateTypeHeaderFileName,
						source.generateTypeHeaderFilePath, options),
					new StructuredTypeImplTemplate(source, source.generateTypeSourceFileName,
						source.generateTypeSourceFilePath, options)
				}
			GlobalConstants:
				#{
					new GlobalConstantsHeaderTemplate(source, source.generateTypeHeaderFileName,
						source.generateTypeHeaderFilePath, options),
					new GlobalConstantsImplTemplate(source, source.generateTypeSourceFileName,
						source.generateTypeSourceFilePath, options)
				}
			CMakeListsMarker:
				(#{
					new ProjectCMakeListsTemplate(source.project, source.output),
					new IncludeCMakeListsTemplate(source.project, source.output, Path.of("include")),
					new SourceCMakeListsTemplate(source.project, source.output, Path.of("src"))
				} + CMakeListsUtil.getSubdirs(source.output, "include").map [ prefix |
					new IncludeCMakeListsTemplate(source.project, source.output, prefix)
				] + CMakeListsUtil.getSubdirs(source.output, "src").map [ prefix |
					new SourceCMakeListsTemplate(source.project, source.output, prefix)
				]).toSet.unmodifiableView
			LibraryElement: {
				val languageSupport = ILanguageSupportFactory.createLanguageSupport("forte_ng", source, options)
				if (languageSupport !== null) {
					#{
						new LanguageHeaderTemplate(languageSupport, source.generateTypeHeaderFileName,
							source.generateTypeHeaderFilePath),
						new LanguageImplTemplate(languageSupport, source.generateTypeSourceFileName,
							source.generateTypeSourceFilePath)
					}
				} else {
					errors.add('''Unknown source type «source.eClass.name»''')
					emptySet
				}
			}
			default: {
				val languageSupport = ILanguageSupportFactory.createLanguageSupport("forte_ng", source, options)
				if (languageSupport !== null) {
					#{
						new LanguageHeaderTemplate(languageSupport, '''«name».h''', Paths.get("")),
						new LanguageImplTemplate(languageSupport, '''«name».cpp''', Paths.get(""))
					}
				} else {
					errors.add('''Unknown source type «source.eClass.name»''')
					emptySet
				}
			}
		}
	}
}

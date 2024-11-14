/*******************************************************************************
 * Copyright (c) 2019, 2024 fortiss GmbH, Johannes Kepler University Linz
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
 *   Alois Zoitl  - added support for structured types
 *   Ernst Blecha - added support for exporting CMakeLists.txt
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng

import java.nio.file.Paths
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.export.TemplateExportFilter
import org.eclipse.fordiac.ide.export.forte_ng.adapter.AdapterFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.adapter.AdapterFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.basic.BasicFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.basic.BasicFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.cmake.CMakeListsTemplate
import org.eclipse.fordiac.ide.export.forte_ng.composite.CompositeFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.composite.CompositeFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.function.FunctionFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.function.FunctionFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.language.LanguageHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.language.LanguageImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.service.ServiceInterfaceFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.service.ServiceInterfaceFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.simple.SimpleFBHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.simple.SimpleFBImplTemplate
import org.eclipse.fordiac.ide.export.forte_ng.struct.StructuredTypeHeaderTemplate
import org.eclipse.fordiac.ide.export.forte_ng.struct.StructuredTypeImplTemplate
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
import org.eclipse.fordiac.ide.model.typelibrary.CMakeListsMarker

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration
import org.eclipse.fordiac.ide.export.Messages
import java.text.MessageFormat
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem
import org.eclipse.fordiac.ide.export.language.ILanguageSupport

class ForteNgExportFilter extends TemplateExportFilter {

	public static final String OPTION_TYPE = "type"
	public static final String OPTION_TYPE_PARAM = "type_param"
	public static final String OPTION_TYPE_SPEC = "type_spec"
	public static final String OPTION_HEADER = "header"

	override protected getTemplates(String name, EObject source) {
		val cache = newHashMap
		val options = #{ILanguageSupport.OPTION_CACHE -> cache}
		switch (source) {
			BasicFBType:
				#{
					new BasicFBHeaderTemplate(source, source.generateTypeInclude, Paths.get(source.generateTypePath),
						options),
					new BasicFBImplTemplate(source, source.generateTypeSource, Paths.get(source.generateTypePath),
						options)
				}
			SimpleFBType:
				#{
					new SimpleFBHeaderTemplate(source, source.generateTypeInclude, Paths.get(source.generateTypePath),
						options),
					new SimpleFBImplTemplate(source, source.generateTypeSource, Paths.get(source.generateTypePath),
						options)
				}
			FunctionFBType:
				#{
					new FunctionFBHeaderTemplate(source, source.generateTypeInclude, Paths.get(source.generateTypePath),
						options),
					new FunctionFBImplTemplate(source, source.generateTypeSource, Paths.get(source.generateTypePath),
						options)
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
					new CompositeFBHeaderTemplate(source, source.generateTypeInclude,
						Paths.get(source.generateTypePath), options),
					new CompositeFBImplTemplate(source, source.generateTypeSource, Paths.get(source.generateTypePath),
						options)
				}
			AdapterType:
				#{
					new AdapterFBHeaderTemplate(source, source.generateTypeInclude, Paths.get(source.generateTypePath),
						options),
					new AdapterFBImplTemplate(source, source.generateTypeSource, Paths.get(source.generateTypePath),
						options)
				}
			ServiceInterfaceFBType:
				#{
					new ServiceInterfaceFBHeaderTemplate(source, source.generateTypeInclude,
						Paths.get(source.generateTypePath), options),
					new ServiceInterfaceFBImplTemplate(source, source.generateTypeSource,
						Paths.get(source.generateTypePath), options)
				}
			StructuredType:
				#{
					new StructuredTypeHeaderTemplate(source, source.generateTypeInclude,
						Paths.get(source.generateTypePath), options),
					new StructuredTypeImplTemplate(source, source.generateTypeSource,
						Paths.get(source.generateTypePath), options)
				}
			CMakeListsMarker:
				#{
					new CMakeListsTemplate('''CMakeLists.txt''', Paths.get(""))
				}
			INamedElement: {
				val languageSupport = ILanguageSupportFactory.createLanguageSupport("forte_ng", source, options)
				if (languageSupport !== null) {
					#{
						new LanguageHeaderTemplate(languageSupport, source.generateTypeInclude,
							Paths.get(source.generateTypePath)),
						new LanguageImplTemplate(languageSupport, source.generateTypeSource,
							Paths.get(source.generateTypePath))
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

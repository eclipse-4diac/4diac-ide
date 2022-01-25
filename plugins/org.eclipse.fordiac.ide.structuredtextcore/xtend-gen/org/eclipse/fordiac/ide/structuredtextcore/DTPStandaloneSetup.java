/**
 * Copyright (c) 2022 Primetals Technologies GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.structuredtextcore;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.util.function.Consumer;
import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class DTPStandaloneSetup implements ISetup {
  @Inject
  @Extension
  private FileExtensionProvider _fileExtensionProvider;
  
  @Inject
  @Extension
  private IResourceServiceProvider.Registry _registry;
  
  @Inject
  private IResourceServiceProvider resourceServiceProvider;
  
  @Override
  public Injector createInjectorAndDoEMFRegistration() {
    Injector _xblockexpression = null;
    {
      DTPRuntimeModule _dTPRuntimeModule = new DTPRuntimeModule();
      final Injector injector = Guice.createInjector(_dTPRuntimeModule);
      injector.injectMembers(this);
      final Consumer<String> _function = (String it) -> {
        this._registry.getExtensionToFactoryMap().put(it, this.resourceServiceProvider);
      };
      this._fileExtensionProvider.getFileExtensions().forEach(_function);
      _xblockexpression = injector;
    }
    return _xblockexpression;
  }
}

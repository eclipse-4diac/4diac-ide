/*******************************************************************************
 * Copyright (c) 2014, 2018 fortiss GmbH, Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499.providers;

import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.iec61499.executors.DeploymentExecutor;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.interactors.TypeNameCreator;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.GlobalConstantsEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.util.LibraryElementHashException;

public class FDBK2DevMgmInteractorProvider extends DefaultDevMgmInteractorProvider {
	private static final String PROFILE_NAME = "FBDK2"; //$NON-NLS-1$

	private static final String WRITE_PARAMETER_FBDK2 = "<Request ID=\"{0}\" Action=\"WRITE\"><Parameter Value=\"{1}\" Reference=\"{2}\" /></Request>"; //$NON-NLS-1$

	private static final TypeNameCreator FBDK_TYPE_NAME_CREATOR = new TypeNameCreator() {

		@Override
		public String getTypeName(final FBNetworkElement fb) {
			return getTypeName(fb.getTypeEntry());
		}

		@Override
		public String getTypeName(final TypeEntry entry) {
			return entry.getTypeName();
		}

		@Override
		public String getTypeNameWithHash(final TypeEntry entry) throws LibraryElementHashException {
			// FBRT does not support type hashing so provide the normal name
			return getTypeName(entry);
		}
	};

	@Override
	public String getProfileName() {
		return PROFILE_NAME;
	}

	@Override
	public IDeviceManagementInteractor createInteractor(final Device dev,
			final IDeviceManagementCommunicationHandler overrideHandler) {
		return new DeploymentExecutor(dev, overrideHandler) {

			@Override
			protected String getWriteParameterMessage() {
				return WRITE_PARAMETER_FBDK2;
			}

			@Override
			public Response queryFBType(final FBTypeEntry entry) throws DeploymentException {
				// FIXME we currently can not easily query the fbtype
				return null;
			}

			@Override
			public Response queryDataType(final DataTypeEntry entry) throws DeploymentException {
				// FIXME we currently can not easily query the data types
				return super.queryDataType(entry);
			}

			@Override
			public Response queryGlobalConstType(final GlobalConstantsEntry entry) throws DeploymentException {
				// FBRT does not support data types
				return null;
			}

			@Override
			public List<org.eclipse.fordiac.ide.deployment.devResponse.Resource> queryResources() {
				// currently no working query resources found
				return Collections.emptyList();
			}

			@Override
			protected TypeNameCreator getTypeNameCreator() {
				return FBDK_TYPE_NAME_CREATOR;
			}
		};

	}
}

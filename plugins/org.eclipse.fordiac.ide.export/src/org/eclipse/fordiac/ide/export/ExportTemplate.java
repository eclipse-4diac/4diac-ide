/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ExportTemplate implements IExportTemplate {

	private final String name;
	private final Path path;

	private final List<String> errors = new ArrayList<>();
	private final List<String> warnings = new ArrayList<>();
	private final List<String> infos = new ArrayList<>();

	public ExportTemplate(String name) {
		this(name, Paths.get(""));
	}

	public ExportTemplate(String name, Path prefix) {
		this.name = name;
		this.path = prefix.resolve(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Path getPath() {
		return path;
	}

	@Override
	public List<String> getErrors() {
		return errors;
	}

	@Override
	public List<String> getWarnings() {
		return warnings;
	}

	@Override
	public List<String> getInfos() {
		return infos;
	}

	@Override
	public int hashCode() {
		return path.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		final ExportTemplate other = (ExportTemplate) obj;
		return Objects.equals(path, other.path);
	}

	@Override
	public String toString() {
		return "ExportTemplate [" + path + "]";
	}
}

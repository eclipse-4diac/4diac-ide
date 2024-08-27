/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.st;

import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.search.IModelMatcher;
import org.eclipse.fordiac.ide.model.search.ISearchSupport;
import org.eclipse.fordiac.ide.model.search.Match;
import org.eclipse.fordiac.ide.model.search.TextMatch;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.DefaultLocationInFileProvider;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.util.ITextRegionWithLineInformation;

public abstract class StructuredTextSearchSupport implements ISearchSupport {

	private final ILocationInFileProvider locationInFileProvider = new DefaultLocationInFileProvider();

	@Override
	public Stream<Match> search(final IModelMatcher matcher) throws CoreException {
		return prepare().map(EcoreUtil2::eAll)
				.flatMap(contents -> StreamSupport.stream(Spliterators.spliteratorUnknownSize(contents, 0), false))
				.filter(matcher::matches).map(this::createMatch);
	}

	protected abstract Stream<EObject> prepare() throws CoreException;

	protected Match createMatch(final EObject object) {
		if (locationInFileProvider
				.getSignificantTextRegion(object) instanceof final ITextRegionWithLineInformation region) {
			return new TextMatch(EcoreUtil.getURI(object), region.getLineNumber(), region.getOffset(),
					region.getLength());
		}
		return new Match(EcoreUtil.getURI(object), FordiacMarkerHelper.getLocation(object));
	}
}

/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.library;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.library.download.AbstractLibraryPackageDownloader;
import org.eclipse.fordiac.ide.library.download.DownloadResult;

/**
 * Minimal second provider of the common downloader base: reads a static HTTP
 * layout, with credentials supplied by the test.
 *
 * <pre>
 * &lt;base&gt;/index.json
 * &lt;base&gt;/&lt;symbolicName&gt;/&lt;version&gt;/MANIFEST.MF
 * &lt;base&gt;/&lt;symbolicName&gt;/&lt;version&gt;/&lt;symbolicName&gt;-&lt;version&gt;.zip
 * </pre>
 */
public class StaticLayoutDownloader extends AbstractLibraryPackageDownloader {

	private record Index(Integer formatVersion, Map<String, List<String>> libraries) {
	}

	private static final Integer FORMAT_VERSION = Integer.valueOf(1);

	private final String address;
	private final Consumer<HttpRequest.Builder> credentials;

	/**
	 * Creates the downloader.
	 *
	 * @param address     the base address of the layout
	 * @param credentials adds the credentials to a request
	 */
	public StaticLayoutDownloader(final String address, final Consumer<HttpRequest.Builder> credentials) {
		this.address = address;
		this.credentials = credentials;
	}

	/**
	 * Returns credentials for HTTP Basic authentication.
	 *
	 * @param user     the user name
	 * @param password the password
	 * @return adds the {@code Authorization} header to a request
	 */
	public static Consumer<HttpRequest.Builder> basic(final String user, final String password) {
		final String encoded = Base64.getEncoder()
				.encodeToString((user + ':' + password).getBytes(StandardCharsets.UTF_8));
		return request -> request.header("Authorization", "Basic " + encoded); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public String getName() {
		return "Static layout"; //$NON-NLS-1$
	}

	@Override
	protected DownloadResult<URI> getBaseUri() {
		return toBaseUri(address);
	}

	@Override
	protected void addCredentials(final HttpRequest.Builder request) {
		credentials.accept(request);
	}

	@Override
	protected List<CatalogueEntry> loadCatalogue(final IProgressMonitor monitor) throws IOException {
		// a static layout has no pages, so the response headers are not needed
		final Index index = getJson(resolve("index.json"), Index.class, monitor).body(); //$NON-NLS-1$
		if (!FORMAT_VERSION.equals(index.formatVersion()) || index.libraries() == null) {
			throw new IOException("Unsupported library index"); //$NON-NLS-1$
		}
		// the locator is the folder of the package: <symbolicName>/<version>
		final List<CatalogueEntry> entries = new ArrayList<>();
		index.libraries().forEach((name, versions) -> versions
				.forEach(version -> entries.add(new CatalogueEntry(name, version, name + '/' + version))));
		return entries;
	}

	@Override
	protected URI getArchiveLocation(final String locator, final IProgressMonitor monitor) throws IOException {
		return resolve(locator + '/' + locator.replace('/', '-') + ".zip"); //$NON-NLS-1$
	}

	@Override
	protected URI getManifestLocation(final String locator, final IProgressMonitor monitor) throws IOException {
		return resolve(locator + "/MANIFEST.MF"); //$NON-NLS-1$
	}
}

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
package org.eclipse.fordiac.ide.library.download;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.LibraryPackageId;
import org.eclipse.fordiac.ide.library.Messages;
import org.eclipse.fordiac.ide.library.download.DownloadResult.Status;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.Strictness;

/**
 * Common base of library package downloaders that read a library source over
 * HTTP.
 *
 * <p>
 * Subclasses provide the base URI and optionally the credentials of their
 * source, its catalogue, and the locations of the archive and the manifest of a
 * package. This base performs all requests:
 * <ul>
 * <li>credentials are added only to requests for the origin (scheme, host and
 * port) of the base URI;</li>
 * <li>redirects are followed by this base, at most five, and never from HTTPS
 * to HTTP;</li>
 * <li>responses are limited in size, see {@link #MAX_METADATA_BYTES_PROPERTY}
 * and {@link #MAX_ARCHIVE_BYTES_PROPERTY};</li>
 * <li>failed requests, unsuccessful HTTP status codes and invalid responses
 * are reported as non-OK {@link DownloadResult}s, and cancellation of the
 * progress monitor, checked before every request, as
 * {@link OperationCanceledException}.</li>
 * </ul>
 * The base also selects the version to download. Catalogue entries whose name
 * or version violates the manifest rules (see {@link LibraryPackageId}) are
 * skipped, with one warning reporting their count per catalogue load. Lists that a
 * source splits into pages are read by the subclass, page by page with
 * {@link #getJson}, because every source pages differently.
 *
 * <p>
 * A downloader loads the catalogue of its source once and keeps it; the
 * {@link LibraryPackageDownloaderProvider} creates new downloaders for every
 * lookup. Subclasses implement activation using state shared across instances of
 * the same source. Instances are not thread-safe.
 */
public abstract class AbstractLibraryPackageDownloader implements ILibraryPackageDownloader {

	/**
	 * Name of the system property with the maximum size of a JSON response or a
	 * manifest in bytes; 4 MiB by default.
	 */
	public static final String MAX_METADATA_BYTES_PROPERTY = "org.eclipse.fordiac.ide.library.download.maxMetadataBytes"; //$NON-NLS-1$

	/**
	 * Name of the system property with the maximum size of a library archive in
	 * bytes; 64 MiB by default.
	 */
	public static final String MAX_ARCHIVE_BYTES_PROPERTY = "org.eclipse.fordiac.ide.library.download.maxArchiveBytes"; //$NON-NLS-1$

	private static final long DEFAULT_MAX_METADATA_BYTES = 4L * 1024 * 1024;
	private static final long DEFAULT_MAX_ARCHIVE_BYTES = 64L * 1024 * 1024;
	private static final int MAX_REDIRECTS = 5;
	private static final Set<Integer> REDIRECTS = Set.of(Integer.valueOf(301), Integer.valueOf(302),
			Integer.valueOf(303), Integer.valueOf(307), Integer.valueOf(308));
	private static final String HTTP = "http"; //$NON-NLS-1$
	private static final String HTTPS = "https"; //$NON-NLS-1$
	private static final String LOCATION_HEADER = "Location"; //$NON-NLS-1$
	private static final String RETRY_AFTER_HEADER = "Retry-After"; //$NON-NLS-1$
	private static final String MANIFEST_FILE_NAME = "MANIFEST.MF"; //$NON-NLS-1$
	private static final String ARCHIVE_EXTENSION = ".zip"; //$NON-NLS-1$
	private static final String PARTIAL_EXTENSION = ".part"; //$NON-NLS-1$

	private static final Gson GSON = new GsonBuilder().setStrictness(Strictness.STRICT).disableJdkUnsafe().create();

	/**
	 * A package as listed by the catalogue of a source.
	 *
	 * @param symbolicName the symbolic name of the library
	 * @param version      the version as given by the source
	 * @param locator      locates the package in the source, passed to
	 *                     {@link AbstractLibraryPackageDownloader#getArchiveLocation
	 *                     getArchiveLocation} and
	 *                     {@link AbstractLibraryPackageDownloader#getManifestLocation
	 *                     getManifestLocation}; text that only the subclass
	 *                     interprets, so that it can also be resolved by a new
	 *                     downloader instance, and never credentials
	 */
	public record CatalogueEntry(String symbolicName, String version, String locator) {
	}

	/**
	 * A parsed JSON response. Its headers let a subclass find the next page of a
	 * list.
	 *
	 * @param <T>     the type of the document
	 * @param body    the parsed document, never {@code null}
	 * @param headers the response headers
	 */
	public record JsonResponse<T>(T body, HttpHeaders headers) {
	}

	/** A catalogue entry with a valid name and version, as used for selecting a version. */
	private record Candidate(LibraryPackageId id, String locator) {
	}

	private static final class InvalidCredentialsException extends IOException {
		private static final long serialVersionUID = 1L;

		InvalidCredentialsException() {
			super(Messages.LibraryPackageDownloader_InvalidCredentials);
		}
	}

	private final LibraryPackageTransport transport;
	private DownloadResult<URI> baseUri;
	private List<Candidate> catalogue;

	/** Creates a downloader using {@link LibraryPackageTransport#getDefault()}. */
	protected AbstractLibraryPackageDownloader() {
		this(LibraryPackageTransport.getDefault());
	}

	/**
	 * Creates a downloader.
	 *
	 * @param transport sends the requests
	 */
	protected AbstractLibraryPackageDownloader(final LibraryPackageTransport transport) {
		this.transport = Objects.requireNonNull(transport);
	}

	/**
	 * Returns the base URI of the source. Only requests for its origin carry
	 * credentials.
	 *
	 * <p>
	 * Implementations read their local configuration only, see
	 * {@link #toBaseUri(String)}.
	 *
	 * @return the base URI, or a result with status
	 *         {@link DownloadResult.Status#NO_CONFIG NO_CONFIG} or
	 *         {@link DownloadResult.Status#CONFIG_ERROR CONFIG_ERROR}
	 */
	protected abstract DownloadResult<URI> getBaseUri();

	/**
	 * Loads the catalogue of the source, using {@link #getJson} for the requests.
	 *
	 * @param monitor the progress monitor, can be {@code null}
	 * @return the packages offered by the source
	 * @throws IOException if a request fails or a response is invalid
	 */
	protected abstract List<CatalogueEntry> loadCatalogue(IProgressMonitor monitor) throws IOException;

	/**
	 * Returns the location of the archive of a package.
	 *
	 * @param locator the locator from the catalogue
	 * @param monitor the progress monitor, can be {@code null}
	 * @return the location of the archive
	 * @throws FileNotFoundException if the package has no archive
	 * @throws IOException           if a request fails or a response is invalid
	 */
	protected abstract URI getArchiveLocation(String locator, IProgressMonitor monitor) throws IOException;

	/**
	 * Returns the location of the manifest of a package.
	 *
	 * @param locator the locator from the catalogue
	 * @param monitor the progress monitor, can be {@code null}
	 * @return the location of the manifest
	 * @throws IOException if a request fails or a response is invalid
	 */
	protected abstract URI getManifestLocation(String locator, IProgressMonitor monitor) throws IOException;

	/**
	 * Adds the credentials of the source to a request. Called only for requests
	 * for the origin of the {@linkplain #getBaseUri() base URI}. Adds nothing by
	 * default.
	 *
	 * @param request the request to add the credentials to
	 */
	protected void addCredentials(final HttpRequest.Builder request) {
		// no credentials by default
	}

	@Override
	public final DownloadResult<List<String>> availableLibraries(final IProgressMonitor monitor)
			throws OperationCanceledException {
		final DownloadResult<List<Candidate>> loaded = catalogue(monitor);
		if (loaded.status() != Status.OK) {
			return failed(loaded);
		}
		return new DownloadResult<>(
				loaded.result().stream().map(candidate -> candidate.id().symbolicName()).distinct().toList());
	}

	@Override
	public final DownloadResult<List<String>> availableVersions(final String symbolicName,
			final IProgressMonitor monitor) throws OperationCanceledException {
		final DownloadResult<List<Candidate>> loaded = catalogue(monitor);
		if (loaded.status() != Status.OK) {
			return failed(loaded);
		}
		final List<String> versions = candidates(loaded.result(), symbolicName).stream()
				.map(candidate -> candidate.id().version().toString()).distinct().toList();
		return new DownloadResult<>(versions);
	}

	@Override
	public final DownloadResult<Map<String, List<String>>> availableLibrariesAndVersions(
			final IProgressMonitor monitor) throws OperationCanceledException {
		final DownloadResult<List<Candidate>> loaded = catalogue(monitor);
		if (loaded.status() != Status.OK) {
			return failed(loaded);
		}
		final Map<String, Set<String>> uniqueVersions = new LinkedHashMap<>();
		for (final Candidate candidate : loaded.result()) {
			uniqueVersions.computeIfAbsent(candidate.id().symbolicName(), _ -> new LinkedHashSet<>())
					.add(candidate.id().version().toString());
		}
		final Map<String, List<String>> versions = new LinkedHashMap<>();
		uniqueVersions.forEach((name, libraryVersions) -> versions.put(name, List.copyOf(libraryVersions)));
		return new DownloadResult<>(Collections.unmodifiableMap(versions));
	}

	@Override
	public final DownloadResult<Path> downloadLibrary(final String symbolicName, final VersionRange range,
			final Version preferredVersion, final IProgressMonitor monitor) throws OperationCanceledException {
		final SubMonitor progress = SubMonitor.convert(monitor, 3);
		final DownloadResult<List<Candidate>> loaded = catalogue(progress.split(1));
		if (loaded.status() != Status.OK) {
			return failed(loaded);
		}
		final List<Candidate> candidates = candidates(loaded.result(), symbolicName);
		if (candidates.isEmpty()) {
			return libraryNotFound(symbolicName);
		}
		final Optional<Candidate> selected = select(candidates, range, preferredVersion);
		if (selected.isEmpty()) {
			return new DownloadResult<>(Status.NOT_FOUND, MessageFormat
					.format(Messages.LibraryPackageDownloader_VersionNotFound, symbolicName, range, getName()));
		}
		final Candidate candidate = selected.get();
		try {
			final URI location = getArchiveLocation(candidate.locator(), progress.split(1));
			final String folderName = candidate.id().folderName();
			return new DownloadResult<>(download(location, folderName, folderName + ARCHIVE_EXTENSION,
					maxArchiveBytes(), progress.split(1)));
		} catch (final IOException e) {
			return failure(e);
		}
	}

	@Override
	public final DownloadResult<Path> downloadManifest(final String symbolicName, final Version version,
			final IProgressMonitor monitor) throws OperationCanceledException {
		final SubMonitor progress = SubMonitor.convert(monitor, 3);
		final DownloadResult<List<Candidate>> loaded = catalogue(progress.split(1));
		if (loaded.status() != Status.OK) {
			return failed(loaded);
		}
		final Optional<Candidate> candidate = candidates(loaded.result(), symbolicName).stream()
				.filter(c -> c.id().version().equals(version)).findFirst();
		if (candidate.isEmpty()) {
			return new DownloadResult<>(Status.NOT_FOUND, MessageFormat
					.format(Messages.LibraryPackageDownloader_ManifestNotFound, symbolicName, version, getName()));
		}
		try {
			final URI location = getManifestLocation(candidate.get().locator(), progress.split(1));
			return new DownloadResult<>(downloadManifestFile(location, candidate.get().id().folderName(), progress.split(1)));
		} catch (final IOException e) {
			return failure(e);
		}
	}

	/**
	 * Loads the catalogue again, even if it was loaded before.
	 *
	 * @param monitor the progress monitor, can be {@code null}
	 * @return the status of the load
	 * @throws OperationCanceledException if the monitor is canceled
	 */
	protected final DownloadResult<Void> refreshCatalogue(final IProgressMonitor monitor)
			throws OperationCanceledException {
		catalogue = null;
		final DownloadResult<List<Candidate>> loaded = catalogue(monitor);
		return loaded.status() == Status.OK ? new DownloadResult<>(Status.OK) : failed(loaded);
	}

	/**
	 * Checks an address from the configuration and turns it into a base URI with a
	 * trailing slash.
	 *
	 * @param address the configured address
	 * @return the base URI, or a result with status
	 *         {@link DownloadResult.Status#CONFIG_ERROR CONFIG_ERROR} if the
	 *         address is not an HTTP or HTTPS address without user information
	 */
	protected final DownloadResult<URI> toBaseUri(final String address) {
		final String trimmed = Objects.toString(address, "").strip(); //$NON-NLS-1$
		try {
			final URI uri = new URI(trimmed);
			final String path = uri.getRawPath();
			if (path != null && !path.endsWith("/")) { //$NON-NLS-1$
				// Insert the slash before the query or fragment, preserving URI escapes.
				return checkBaseUri(new URI(trimmed.replaceFirst("[?#]|$", "/$0"))); //$NON-NLS-1$ //$NON-NLS-2$
			}
			return checkBaseUri(uri);
		} catch (final URISyntaxException e) {
			return invalidBaseUri();
		}
	}

	/**
	 * Resolves a reference against the base URI.
	 *
	 * @param reference the reference, e.g. a relative path with query
	 * @return the resolved location
	 * @throws IOException if the base URI is not configured or the reference is
	 *                     invalid
	 */
	protected final URI resolve(final String reference) throws IOException {
		try {
			return requireBaseUri().resolve(reference);
		} catch (final IllegalArgumentException e) {
			throw new IOException(MessageFormat.format(Messages.LibraryPackageDownloader_InvalidLocation,
					describe(requireBaseUri())));
		}
	}

	/**
	 * Requests a JSON document and parses it.
	 *
	 * @param <T>      the type of the document
	 * @param location the location of the document
	 * @param type     the type of the document
	 * @param monitor  the progress monitor, can be {@code null}
	 * @return the parsed document with the response headers
	 * @throws IOException if the request fails or the response is no valid
	 *                     document of the given type
	 */
	protected final <T> JsonResponse<T> getJson(final URI location, final Class<T> type,
			final IProgressMonitor monitor) throws IOException {
		final HttpResponse<byte[]> response = send(location, BodyHandlers.ofByteArray(), maxMetadataBytes(),
				monitor);
		return new JsonResponse<>(parse(response, type), response.headers());
	}

	/**
	 * Downloads a file of a package into
	 * {@code <workspace>/.download/<folderName>/<fileName>}, limited to the
	 * maximum archive size. The file appears only when it is complete.
	 *
	 * @param location   the location of the file
	 * @param folderName the name of the download folder, a single path segment
	 * @param fileName   the name of the file, a single path segment
	 * @param monitor    the progress monitor, can be {@code null}
	 * @return the downloaded file
	 * @throws IOException if a name is invalid or the download fails
	 */
	protected final Path downloadFile(final URI location, final String folderName, final String fileName,
			final IProgressMonitor monitor) throws IOException {
		return download(location, folderName, fileName, maxArchiveBytes(), monitor);
	}

	/**
	 * Downloads the package manifest with the metadata size limit.
	 *
	 * @param location the location of the manifest
	 * @param folderName the name of the download folder, a single path segment
	 * @param monitor the progress monitor, can be {@code null}
	 * @return the downloaded manifest
	 * @throws IOException if the download fails
	 */
	protected final Path downloadManifestFile(final URI location, final String folderName,
			final IProgressMonitor monitor) throws IOException {
		return download(location, folderName, MANIFEST_FILE_NAME, maxMetadataBytes(), monitor);
	}

	private DownloadResult<List<Candidate>> catalogue(final IProgressMonitor monitor) {
		if (catalogue != null) {
			return new DownloadResult<>(catalogue);
		}
		final DownloadResult<URI> base = baseUri();
		if (base.status() != Status.OK) {
			return failed(base);
		}
		try {
			catalogue = toCandidates(loadCatalogue(monitor));
			return new DownloadResult<>(catalogue);
		} catch (final IOException e) {
			return failure(e);
		}
	}

	private List<Candidate> toCandidates(final List<CatalogueEntry> entries) {
		final List<Candidate> candidates = new ArrayList<>(entries.size());
		for (final CatalogueEntry entry : entries) {
			toCandidate(entry).ifPresent(candidates::add);
		}
		final int skipped = entries.size() - candidates.size();
		if (skipped > 0) {
			ILog.get().warn(MessageFormat.format(Messages.LibraryPackageDownloader_InvalidEntry, getName(), skipped));
		}
		return List.copyOf(candidates);
	}

	private static Optional<Candidate> toCandidate(final CatalogueEntry entry) {
		final Optional<LibraryPackageId> id = LibraryPackageId.parse(entry.symbolicName(), entry.version());
		if (id.isPresent() && entry.locator() != null && !entry.locator().isBlank()) {
			return Optional.of(new Candidate(id.get(), entry.locator()));
		}
		return Optional.empty();
	}

	private static List<Candidate> candidates(final List<Candidate> catalogue, final String symbolicName) {
		return catalogue.stream().filter(candidate -> candidate.id().symbolicName().equals(symbolicName)).toList();
	}

	private static Optional<Candidate> select(final List<Candidate> candidates, final VersionRange range,
			final Version preferredVersion) {
		final List<Candidate> inRange = candidates.stream()
				.filter(candidate -> range == null || range.includes(candidate.id().version()))
				.toList();
		if (preferredVersion != null) {
			final Optional<Candidate> preferred = inRange.stream()
					.filter(candidate -> candidate.id().version().equals(preferredVersion)).findFirst();
			if (preferred.isPresent()) {
				return preferred;
			}
		}
		return inRange.stream().max(Comparator.comparing(candidate -> candidate.id().version()));
	}

	private DownloadResult<URI> baseUri() {
		if (baseUri == null) {
			final DownloadResult<URI> configured = Objects.requireNonNull(getBaseUri());
			baseUri = configured.status() == Status.OK ? checkBaseUri(configured.result()) : configured;
		}
		return baseUri;
	}

	private URI requireBaseUri() throws IOException {
		final DownloadResult<URI> base = baseUri();
		if (base.status() != Status.OK) {
			throw new IOException(base.message());
		}
		return base.result();
	}

	private DownloadResult<URI> checkBaseUri(final URI uri) {
		if (isHttp(uri) && uri.getHost() != null && uri.getRawUserInfo() == null) {
			return new DownloadResult<>(uri);
		}
		return invalidBaseUri();
	}

	private DownloadResult<URI> invalidBaseUri() {
		return new DownloadResult<>(Status.CONFIG_ERROR,
				MessageFormat.format(Messages.LibraryPackageDownloader_InvalidBaseUri, getName()));
	}

	private <T> HttpResponse<T> send(final URI location, final BodyHandler<T> handler, final long maxBytes,
			final IProgressMonitor monitor) throws IOException {
		final URI base = requireBaseUri();
		URI target = location;
		for (int redirects = 0;; redirects++) {
			if (monitor != null && monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			if (!isHttp(target) || target.getHost() == null || target.getRawUserInfo() != null) {
				throw new IOException(
						MessageFormat.format(Messages.LibraryPackageDownloader_InvalidLocation, describe(target)));
			}
			final HttpRequest.Builder request = HttpRequest.newBuilder(target).GET();
			if (isSameOrigin(target, base)) {
				try {
					addCredentials(request);
				} catch (final IllegalArgumentException e) {
					throw new InvalidCredentialsException();
				}
			}
			final String description = describe(target);
			final HttpResponse<T> response = transport.send(request.build(),
					info -> isSuccess(info.statusCode())
							? new LimitingBodySubscriber<>(handler.apply(info), maxBytes, description)
							: discardBody(),
					monitor);
			final int status = response.statusCode();
			if (isSuccess(status)) {
				return response;
			}
			if (!REDIRECTS.contains(Integer.valueOf(status))) {
				throw new LibraryPackageRequestException(target, status,
						response.headers().firstValue(RETRY_AFTER_HEADER).orElse(null));
			}
			if (redirects >= MAX_REDIRECTS) {
				throw new IOException(
						MessageFormat.format(Messages.LibraryPackageDownloader_TooManyRedirects, describe(location)));
			}
			target = redirectTarget(target, response.headers());
		}
	}

	private static <T> HttpResponse.BodySubscriber<T> discardBody() {
		return BodySubscribers.mapping(BodySubscribers.ofInputStream(), stream -> {
			try {
				stream.close();
			} catch (final IOException e) {
				throw new UncheckedIOException(e);
			}
			return null;
		});
	}

	private static URI redirectTarget(final URI current, final HttpHeaders headers) throws IOException {
		final String location = headers.firstValue(LOCATION_HEADER).filter(value -> !value.isBlank())
				.orElseThrow(() -> new IOException(
						MessageFormat.format(Messages.LibraryPackageDownloader_InvalidRedirect, describe(current))));
		final URI next;
		try {
			final URI reference = new URI(location);
			// URI.resolve uses the parent directory for a query-only reference.
			next = location.startsWith("?") //$NON-NLS-1$
					? new URI(current.getScheme() + "://" + current.getRawAuthority() + current.getRawPath() + location) //$NON-NLS-1$
					: current.resolve(reference);
		} catch (final URISyntaxException | IllegalArgumentException e) {
			throw new IOException(
					MessageFormat.format(Messages.LibraryPackageDownloader_InvalidRedirect, describe(current)));
		}
		if (HTTPS.equalsIgnoreCase(current.getScheme()) && !HTTPS.equalsIgnoreCase(next.getScheme())) {
			throw new IOException(
					MessageFormat.format(Messages.LibraryPackageDownloader_UnencryptedRedirect, describe(current)));
		}
		return next;
	}

	private Path download(final URI location, final String folderName, final String fileName, final long maxBytes,
			final IProgressMonitor monitor) throws IOException {
		final Path folder = downloadRoot().resolve(checkFileName(folderName));
		final Path target = folder.resolve(checkFileName(fileName));
		Files.createDirectories(folder);
		final Path partial = Files.createTempFile(folder, "download-", PARTIAL_EXTENSION); //$NON-NLS-1$
		try {
			send(location, BodyHandlers.ofFile(partial, StandardOpenOption.WRITE,
					StandardOpenOption.TRUNCATE_EXISTING), maxBytes, monitor);
			if (monitor != null && monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			if (Files.size(partial) == 0) {
				throw new IOException(MessageFormat.format(Messages.LibraryPackageDownloader_EmptyFile, describe(location)));
			}
			Files.move(partial, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
			return target;
		} finally {
			Files.deleteIfExists(partial);
		}
	}

	private static Path downloadRoot() {
		return ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toFile().toPath()
				.resolve(LibraryManager.PACKAGE_DOWNLOAD_DIRECTORY);
	}

	private static String checkFileName(final String name) throws IOException {
		if (!isValidFileName(name)) {
			throw new IOException(MessageFormat.format(Messages.LibraryPackageDownloader_InvalidFileName, name));
		}
		return name;
	}

	private static boolean isValidFileName(final String name) {
		if (name == null || name.isBlank() || ".".equals(name) || "..".equals(name) //$NON-NLS-1$ //$NON-NLS-2$
				|| name.chars().anyMatch(c -> c == '/' || c == '\\' || c == ':' || Character.isISOControl(c))) {
			return false;
		}
		try {
			final Path path = Path.of(name);
			return path.getNameCount() == 1 && !path.isAbsolute() && name.equals(path.toString());
		} catch (final InvalidPathException e) {
			return false;
		}
	}

	private static <T> T parse(final HttpResponse<byte[]> response, final Class<T> type) throws IOException {
		try (Reader reader = new InputStreamReader(new ByteArrayInputStream(response.body()),
				StandardCharsets.UTF_8.newDecoder())) {
			final T document = GSON.fromJson(reader, type);
			if (document == null) {
				throw new JsonParseException("empty document"); //$NON-NLS-1$
			}
			return document;
		} catch (final JsonParseException e) {
			throw new IOException(MessageFormat.format(Messages.LibraryPackageDownloader_InvalidResponse,
					describe(response.uri())));
		}
	}

	private <T> DownloadResult<T> failure(final IOException e) {
		if (e instanceof FileNotFoundException || (e instanceof final LibraryPackageRequestException request
				&& request.getStatusCode() == HttpURLConnection.HTTP_NOT_FOUND)) {
			return new DownloadResult<>(Status.NOT_FOUND, e.getMessage());
		}
		if (e instanceof InvalidCredentialsException
				|| (e instanceof final LibraryPackageRequestException request
						&& (request.isAccessDenied() || request.getStatusCode() == HttpURLConnection.HTTP_PROXY_AUTH))) {
			// credentials or permissions have to be changed in the configuration
			return new DownloadResult<>(Status.CONFIG_ERROR,
					MessageFormat.format(Messages.LibraryPackageDownloader_RequestError, getName(), e.getMessage()));
		}
		final String message = MessageFormat.format(Messages.LibraryPackageDownloader_RequestError, getName(),
				Objects.toString(e.getMessage(), e.getClass().getSimpleName()));
		ILog.get().warn(message);
		return new DownloadResult<>(Status.ERROR, message);
	}

	private <T> DownloadResult<T> libraryNotFound(final String symbolicName) {
		return new DownloadResult<>(Status.NOT_FOUND,
				MessageFormat.format(Messages.LibraryPackageDownloader_LibraryNotFound, symbolicName, getName()));
	}

	private static <T> DownloadResult<T> failed(final DownloadResult<?> result) {
		return new DownloadResult<>(result.status(), result.message());
	}

	private static boolean isSuccess(final int status) {
		return status >= 200 && status < 300 && status != HttpURLConnection.HTTP_PARTIAL;
	}

	private static boolean isHttp(final URI uri) {
		return HTTP.equalsIgnoreCase(uri.getScheme()) || HTTPS.equalsIgnoreCase(uri.getScheme());
	}

	private static boolean isSameOrigin(final URI target, final URI base) {
		return target.getScheme().equalsIgnoreCase(base.getScheme())
				&& target.getHost().equalsIgnoreCase(base.getHost()) && port(target) == port(base);
	}

	private static int port(final URI uri) {
		if (uri.getPort() != -1) {
			return uri.getPort();
		}
		return HTTPS.equalsIgnoreCase(uri.getScheme()) ? 443 : 80;
	}

	private static long maxMetadataBytes() {
		return limit(MAX_METADATA_BYTES_PROPERTY, DEFAULT_MAX_METADATA_BYTES);
	}

	private static long maxArchiveBytes() {
		return limit(MAX_ARCHIVE_BYTES_PROPERTY, DEFAULT_MAX_ARCHIVE_BYTES);
	}

	private static long limit(final String property, final long defaultValue) {
		final long value = Long.getLong(property, defaultValue).longValue();
		return value > 0 ? value : defaultValue;
	}

	/**
	 * Describes a location for messages, without user information, query and
	 * fragment, as these can carry secrets such as signatures of redirect targets.
	 *
	 * @param location the location
	 * @return the description
	 */
	static String describe(final URI location) {
		try {
			return new URI(location.getScheme(), null, location.getHost(), location.getPort(), location.getPath(),
					null, null).toString();
		} catch (final URISyntaxException e) {
			return Objects.toString(location.getHost());
		}
	}
}

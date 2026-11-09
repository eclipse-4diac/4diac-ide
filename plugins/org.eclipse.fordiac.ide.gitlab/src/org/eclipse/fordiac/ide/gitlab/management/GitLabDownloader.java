/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH, Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *   Michael Oberlehner - request GitLab lists in pages of 100 entries
 *   Michael Oberlehner - one downloader per configured endpoint
 *   Michael Oberlehner - read GitLab with Gson through the common downloader base
 *******************************************************************************/
package org.eclipse.fordiac.ide.gitlab.management;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.fordiac.ide.gitlab.Messages;
import org.eclipse.fordiac.ide.gitlab.Package;
import org.eclipse.fordiac.ide.gitlab.Project;
import org.eclipse.fordiac.ide.gitlab.management.GitLabResponses.PackageFileResponse;
import org.eclipse.fordiac.ide.gitlab.management.GitLabResponses.PackageResponse;
import org.eclipse.fordiac.ide.gitlab.management.GitLabResponses.ProjectResponse;
import org.eclipse.fordiac.ide.gitlab.preferences.GitLabEndpoint;
import org.eclipse.fordiac.ide.gitlab.treeviewer.LeafNode;
import org.eclipse.fordiac.ide.library.LibraryPackageId;
import org.eclipse.fordiac.ide.library.download.AbstractLibraryPackageDownloader;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.download.LibraryPackageRequestException;
import org.eclipse.fordiac.ide.library.download.LibraryPackageTransport;

import com.google.gson.JsonElement;

/**
 * Downloads libraries from the package registries of one GitLab endpoint.
 *
 * <p>
 * The downloaders of all configured endpoints are created by the
 * {@link GitLabDownloaderProvider}. The catalogue lists the packages of all
 * projects the token is a member of; projects without package registry are
 * skipped. Requests, redirects, limits and errors are handled by
 * {@link AbstractLibraryPackageDownloader}, which sends the access token only
 * to the configured endpoint.
 */
public class GitLabDownloader extends AbstractLibraryPackageDownloader {

	/**
	 * Name of the system property with the maximum number of pages of a GitLab
	 * list; 1000 by default.
	 */
	public static final String MAX_PAGES_PROPERTY = "org.eclipse.fordiac.ide.gitlab.maxPages"; //$NON-NLS-1$

	private static final long DEFAULT_MAX_PAGES = 1000;
	private static final String PRIVATE_TOKEN_HEADER = "PRIVATE-TOKEN"; //$NON-NLS-1$
	private static final String NEXT_PAGE_HEADER = "X-Next-Page"; //$NON-NLS-1$
	private static final String PROJECTS = "api/v4/projects"; //$NON-NLS-1$
	private static final String PACKAGES = "/packages/"; //$NON-NLS-1$
	// the maximum page size of the GitLab API, to keep the number of requests low
	private static final String PAGE_PARAMETERS = "per_page=100&page="; //$NON-NLS-1$
	private static final String FIRST_PAGE = "1"; //$NON-NLS-1$
	private static final String MANIFEST_FILENAME = "MANIFEST.MF"; //$NON-NLS-1$
	private static final String ZIP_EXTENSION = ".zip"; //$NON-NLS-1$
	private static final String REGISTRY_DISABLED = "disabled"; //$NON-NLS-1$
	// names, versions and package types become part of request paths
	private static final Pattern PATH_SEGMENT = Pattern.compile("[A-Za-z0-9_][A-Za-z0-9._+-]*"); //$NON-NLS-1$
	private static final Pattern PAGE_NUMBER = Pattern.compile("[1-9]\\d{0,8}"); //$NON-NLS-1$
	private static final Pattern ID = Pattern.compile("[1-9][0-9]{0,18}"); //$NON-NLS-1$
	private static final String LOCATOR_SEPARATOR = "/"; //$NON-NLS-1$

	public enum FileFilter {
		ZIP(Pattern.compile(".*\\.zip")), MANIFEST(Pattern.compile(".*\\.MF")); //$NON-NLS-1$//$NON-NLS-2$

		private final Pattern pattern;

		FileFilter(final Pattern pattern) {
			this.pattern = pattern;
		}

		public Pattern getPattern() {
			return pattern;
		}
	}

	/** The location of a page of a GitLab list. */
	@FunctionalInterface
	private interface Page {
		URI at(String page) throws IOException;
	}

	private final GitLabEndpoint endpoint;
	private Map<Project, List<Package>> projectAndPackageMap;
	private Map<String, List<LeafNode>> packagesAndLeaves;

	/**
	 * Creates the downloader of a GitLab endpoint.
	 *
	 * @param endpoint the configured endpoint
	 */
	public GitLabDownloader(final GitLabEndpoint endpoint) {
		this(endpoint, LibraryPackageTransport.getDefault());
	}

	GitLabDownloader(final GitLabEndpoint endpoint, final LibraryPackageTransport transport) {
		super(transport);
		this.endpoint = Objects.requireNonNull(endpoint);
	}

	/**
	 * Returns the endpoint this downloader connects to.
	 *
	 * @return the configured endpoint
	 */
	public GitLabEndpoint getEndpoint() {
		return endpoint;
	}

	public Map<String, List<LeafNode>> getPackagesAndLeaves() {
		return packagesAndLeaves != null ? packagesAndLeaves : Map.of();
	}

	public Map<Project, List<Package>> getProjectsAndPackages() {
		return projectAndPackageMap != null ? projectAndPackageMap : Map.of();
	}

	/**
	 * Loads the projects and packages of the endpoint again.
	 *
	 * @return the status of the load
	 */
	public DownloadResult<Void> fetchProjectsAndPackages() {
		return refreshCatalogue(null);
	}

	/**
	 * Downloads the first file of a package that matches a filter.
	 *
	 * @param project the project of the package
	 * @param pack    the package
	 * @param filter  selects the file
	 * @return the downloaded file, or {@code null} if no file of the package
	 *         matches
	 * @throws IOException if a request fails or a response is invalid
	 */
	public Path packageDownloader(final Project project, final Package pack, final FileFilter filter)
			throws IOException {
		if (!isValidId(project.id()) || !isValidId(pack.id()) || !isPathSegment(pack.packageType())) {
			throw new IOException(Messages.Invalid_Locator);
		}
		final LibraryPackageId id = packageId(pack);
		final Optional<String> fileName = findFileName(project.id(), pack, id, filter, null);
		if (fileName.isEmpty()) {
			return null;
		}
		final URI location = fileUri(project.id(), pack, fileName.get());
		return filter == FileFilter.MANIFEST ? downloadManifestFile(location, id.folderName(), null)
				: downloadFile(location, id.folderName(), fileName.get(), null);
	}

	@Override
	public String getName() {
		return MessageFormat.format(Messages.GitLabDownloader_Name, endpoint.name());
	}

	@Override
	protected DownloadResult<URI> getBaseUri() {
		final boolean missingUrl = endpoint.url().isBlank();
		final boolean missingToken = endpoint.token().isBlank();
		if (missingUrl && missingToken) {
			return new DownloadResult<>(DownloadResult.Status.NO_CONFIG, Messages.No_Config);
		}
		if (missingUrl || missingToken) {
			return new DownloadResult<>(DownloadResult.Status.CONFIG_ERROR, Messages.Configuration_Incomplete);
		}
		return toBaseUri(endpoint.url());
	}

	@Override
	protected void addCredentials(final HttpRequest.Builder request) {
		request.header(PRIVATE_TOKEN_HEADER, endpoint.token());
	}

	@Override
	protected List<CatalogueEntry> loadCatalogue(final IProgressMonitor monitor) throws IOException {
		projectAndPackageMap = null;
		packagesAndLeaves = null;
		final SubMonitor progress = SubMonitor.convert(monitor, 2);
		final List<CatalogueProject> projects = getProjects(progress.split(1));
		final SubMonitor packageProgress = progress.split(1).setWorkRemaining(projects.size());
		final Map<Project, List<Package>> projectPackages = new LinkedHashMap<>();
		final Map<String, List<LeafNode>> leaves = new LinkedHashMap<>();
		final List<CatalogueEntry> entries = new ArrayList<>();
		int skippedPackages = 0;
		for (final CatalogueProject catalogueProject : projects) {
			final Project project = catalogueProject.project();
			final Optional<List<PackageResponse>> responses = getPackages(catalogueProject, packageProgress.split(1));
			if (responses.isPresent()) {
				final List<Package> packages = responses.get().stream().flatMap(response -> toPackage(response).stream())
						.collect(Collectors.toCollection(ArrayList::new));
				skippedPackages += responses.get().size() - packages.size();
				projectPackages.put(project, packages);
				for (final Package pack : packages) {
					leaves.computeIfAbsent(pack.name(), _ -> new ArrayList<>())
							.add(new LeafNode(project, pack, pack.version()));
					entries.add(new CatalogueEntry(pack.name(), pack.version(), toLocator(project, pack)));
				}
			}
		}
		if (skippedPackages > 0) {
			ILog.get().warn(MessageFormat.format(Messages.Skipped_Invalid_Packages, getName(), skippedPackages));
		}
		projectAndPackageMap = projectPackages;
		packagesAndLeaves = leaves;
		return entries;
	}

	@Override
	protected URI getArchiveLocation(final String locator, final IProgressMonitor monitor) throws IOException {
		final PackageLocation location = fromLocator(locator);
		final String fileName = findFileName(location.projectId(), location.pack(),
				packageId(location.pack()), FileFilter.ZIP, monitor)
				.orElseThrow(() -> new FileNotFoundException(MessageFormat.format(Messages.Archive_Not_Found,
						location.pack().name(), location.pack().version(), location.projectId().toString())));
		return fileUri(location.projectId(), location.pack(), fileName);
	}

	@Override
	protected URI getManifestLocation(final String locator, final IProgressMonitor monitor) throws IOException {
		final PackageLocation location = fromLocator(locator);
		return fileUri(location.projectId(), location.pack(), MANIFEST_FILENAME);
	}

	/** {@code <projectId>/<packageId>/<packageType>/<name>/<version>} */
	private static String toLocator(final Project project, final Package pack) {
		return String.join(LOCATOR_SEPARATOR, project.id().toString(), pack.id().toString(), pack.packageType(),
				pack.name(), pack.version());
	}

	private static PackageLocation fromLocator(final String locator) throws IOException {
		final String[] parts = locator.split(LOCATOR_SEPARATOR, -1);
		if (parts.length == 5 && ID.matcher(parts[0]).matches() && ID.matcher(parts[1]).matches()
				&& isPathSegment(parts[2]) && isPathSegment(parts[3]) && isPathSegment(parts[4])) {
			try {
				return new PackageLocation(Long.valueOf(parts[0]),
						new Package(Long.valueOf(parts[1]), parts[3], parts[4], parts[2]));
			} catch (final NumberFormatException e) {
				throw new IOException(Messages.Invalid_Locator);
			}
		}
		throw new IOException(Messages.Invalid_Locator);
	}

	/** A package of a project, decoded from a locator. */
	private record PackageLocation(Long projectId, Package pack) {
	}

	private record CatalogueProject(Project project, boolean registryEnabled) {
	}

	private List<CatalogueProject> getProjects(final IProgressMonitor monitor) throws IOException {
		final List<CatalogueProject> projects = new ArrayList<>();
		for (final ProjectResponse response : listPages(
				page -> resolve(PROJECTS + "?membership=true&" + PAGE_PARAMETERS + page), //$NON-NLS-1$
				ProjectResponse[].class, monitor)) {
			toProject(response).ifPresent(projects::add);
		}
		return projects;
	}

	private Optional<List<PackageResponse>> getPackages(final CatalogueProject catalogueProject, final IProgressMonitor monitor)
			throws IOException {
		final Project project = catalogueProject.project();
		return listPages(page -> resolve(PROJECTS + '/' + project.id() + "/packages?" //$NON-NLS-1$
				+ PAGE_PARAMETERS + page), PackageResponse[].class, monitor, !catalogueProject.registryEnabled());
	}

	private Optional<String> findFileName(final Long projectId, final Package pack, final LibraryPackageId id,
			final FileFilter filter, final IProgressMonitor monitor) throws IOException {
		final String expected = filter == FileFilter.ZIP ? id.folderName() + ZIP_EXTENSION : MANIFEST_FILENAME;
		final String legacy = filter == FileFilter.ZIP ? pack.name() + '-' + pack.version() + ZIP_EXTENSION : expected;
		String selected = null;
		for (final PackageFileResponse file : listPages(
				page -> resolve(PROJECTS + '/' + projectId + PACKAGES + pack.id() + "/package_files?" //$NON-NLS-1$
						+ PAGE_PARAMETERS + page), PackageFileResponse[].class, monitor)) {
			final JsonElement name = file != null ? file.fileName() : null;
			if (name == null || !name.isJsonPrimitive() || !name.getAsJsonPrimitive().isString()
					|| name.getAsString().isBlank()) {
				throw new IOException(Messages.Invalid_File_Name);
			}
			final String fileName = name.getAsString();
			if (expected.equals(fileName) || (selected == null && legacy.equals(fileName))) {
				selected = fileName;
			}
		}
		return Optional.ofNullable(selected);
	}

	private static LibraryPackageId packageId(final Package pack) throws IOException {
		return LibraryPackageId.parse(pack.name(), pack.version()).orElseThrow(() -> new IOException(Messages.Invalid_Locator));
	}

	private URI fileUri(final Long projectId, final Package pack, final String fileName) throws IOException {
		return resolve(PROJECTS + '/' + projectId + PACKAGES + pack.packageType() + '/' + pack.name() + '/'
				+ pack.version() + '/' + fileName);
	}

	private <T> List<T> listPages(final Page page, final Class<T[]> pageType, final IProgressMonitor monitor)
			throws IOException {
		return listPages(page, pageType, monitor, false).orElseThrow();
	}

	private <T> Optional<List<T>> listPages(final Page page, final Class<T[]> pageType, final IProgressMonitor monitor,
			final boolean skipForbiddenFirstPage) throws IOException {
		final long maxPages = maxPages();
		final List<T> elements = new ArrayList<>();
		final URI first = page.at(FIRST_PAGE);
		URI location = first;
		for (long count = 1; location != null; count++) {
			if (count > maxPages) {
				throw new IOException(MessageFormat.format(Messages.Too_Many_Pages, first.getPath(),
						Long.toString(maxPages)));
			}
			final JsonResponse<T[]> response;
			try {
				response = getJson(location, pageType, monitor);
			} catch (final LibraryPackageRequestException e) {
				if (skipForbiddenFirstPage && count == 1 && e.getStatusCode() == HttpURLConnection.HTTP_FORBIDDEN) {
					return Optional.empty();
				}
				throw e;
			}
			elements.addAll(Arrays.asList(response.body()));
			location = nextPage(response.headers(), page).orElse(null);
		}
		return Optional.of(elements);
	}

	private static long maxPages() {
		final long value = Long.getLong(MAX_PAGES_PROPERTY, DEFAULT_MAX_PAGES).longValue();
		return value > 0 ? value : DEFAULT_MAX_PAGES;
	}

	private static Optional<URI> nextPage(final HttpHeaders headers, final Page page) throws IOException {
		final Optional<String> next = headers.firstValue(NEXT_PAGE_HEADER).map(String::strip)
				.filter(Predicate.not(String::isEmpty));
		if (next.isEmpty()) {
			return Optional.empty();
		}
		if (!PAGE_NUMBER.matcher(next.get()).matches()) {
			throw new IOException(Messages.Invalid_Next_Page);
		}
		return Optional.of(page.at(next.get()));
	}

	private Optional<CatalogueProject> toProject(final ProjectResponse response) throws IOException {
		if (response == null || !isValidId(response.id()) || response.name() == null || response.name().isBlank()) {
			ILog.get().warn(MessageFormat.format(Messages.Invalid_Project, getName(),
					response != null ? Objects.toString(response.id()) : null));
			return Optional.empty();
		}
		final JsonElement enabled = response.packagesEnabled();
		boolean registryEnabled = response.packageRegistryAccessLevel() != null;
		if (enabled != null && !enabled.isJsonNull()) {
			if (!enabled.isJsonPrimitive() || !enabled.getAsJsonPrimitive().isBoolean()) {
				throw new IOException(MessageFormat.format(Messages.Invalid_Registry_State, response.id().toString()));
			}
			if (!enabled.getAsBoolean()) {
				return Optional.empty();
			}
			registryEnabled = true;
		}
		if (REGISTRY_DISABLED.equals(response.packageRegistryAccessLevel())) {
			// no request for the packages of a project without package registry
			return Optional.empty();
		}
		return Optional.of(new CatalogueProject(new Project(response.id(), response.name()), registryEnabled));
	}

	private static Optional<Package> toPackage(final PackageResponse response) {
		if (response == null || !isValidId(response.id()) || !isPathSegment(response.packageType())
				|| LibraryPackageId.parse(response.name(), response.version()).isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new Package(response.id(), response.name(), response.version(), response.packageType()));
	}

	private static boolean isValidId(final Long id) {
		return id != null && id.longValue() > 0;
	}

	private static boolean isPathSegment(final String value) {
		return value != null && PATH_SEGMENT.matcher(value).matches();
	}
}

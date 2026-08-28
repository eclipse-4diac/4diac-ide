# Copilot Instructions for 4diac IDE

## Project Overview

**Eclipse 4diac IDE** is an open-source engineering tool for IEC 61499-based distributed automation systems.
The project is primarily written in Java with some Xtend code.
It follows the **Eclipse Foundation development process** and is licensed under **EPL 2.0**.

**Default Branch:** `develop`  
**Build Tool:** Maven  
**Key Technologies:** Java 25+, Eclipse IDE, Eclipse GEF Classic, Xtext

## Coding Guidelines

- Use descriptive names for variables, methods, and classes.
- Use established utilities or helpers to perform common tasks.
- Never include debug code, System.out, or TODO comments in commits.
- Use descriptive log messages or provide user feedback where appropriate.
- Do not fail silently in case of errors.
- Do not write new features in Xtend.

## PR Review Guidelines

When reviewing pull requests, verify the following:

- Each PR should be focused on a single, logical topic or issue.
- Commit messages must not reference GitHub issues or pull requests.
- Commit messages must not start with conventional commit prefixes, such as `[feat]` or `fix:`.
- Changed copyright headers should only have the year of the first contribution (e.g., `2004`), instead of a year range (e.g., `2024, 2026`).
- Changes to `MANIFEST.MF` files must not add UI dependencies to non-UI plugins.
- All user-facing strings should be externalized using Eclipse message bundles.
- User-facing strings must not be marked using `//$NON-NLS-n$`.
- When exporting packages for internal usage, such as in tests, use friend exports instead of unrestricted exports.

## Dependency & Security Policies

- Avoid unintended widening of the public API surface.
- Avoid introducing new third-party dependencies without explicit approval.
- Never log sensitive info (passwords, tokens) to the console.
- Always follow good security practices.

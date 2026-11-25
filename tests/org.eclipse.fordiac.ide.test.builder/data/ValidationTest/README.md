# Validation test fixtures

This project contains input models for the builder tests that exercise library-element validation. Fixtures under `Type Library/lib`
are grouped by the model class being validated; each case is named for the constraint it is intended to cover. For
example, `Type Library/lib/AdapterDeclaration/MultipleInputConnections.sub` targets
`LibraryElementValidator.ADAPTER_DECLARATION__VALIDATE_MULTIPLE_INPUT_CONNECTIONS`.

## File structure

- `.project` identifies this fixture directory as an Eclipse project used by the tests.
- `.settings/org.eclipse.fordiac.ide.model.prefs` enables project-scoped validation preferences used by selected cases.
- `Type Library/SimpleTest.fbt` is the baseline FB type fixture.
- `Type Library/lib/<ModelClass>/<Constraint>.<extension>` contains an individual validation case.
- `Type Library/lib/<ModelClass>/support/` contains the supporting types and models owned by that case group.
- `markers.txt` in any folder is the expected, sorted builder-marker baseline for that folder's direct resources.
  Marker paths are relative to that folder, followed by `:<line>` when a valid line number is available;
  `.` denotes a marker on the folder itself. If a folder has no
  `markers.txt`, the tests assert that it has no markers.

The project settings enable warning-level checks for multiple event-output connections, package-name/folder mismatches,
and missing values for generic type variables.

The fixture filename describes the intended condition, but not every case necessarily produces a distinct marker:
validation severity and shared validation implementations can affect which diagnostics appear. When changing fixtures,
run the builder test and update the relevant folder-level `markers.txt` files only from the actual complete marker
output, preserving incidental diagnostics and exact message text. Validation cases that need helper types should use
local copies instead of depending on another test folder. Do not add any test cases directly to BuilderTest,
add appropriate fixtures instead.

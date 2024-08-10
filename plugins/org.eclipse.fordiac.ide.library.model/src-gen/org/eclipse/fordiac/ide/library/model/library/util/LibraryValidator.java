/**
 */
package org.eclipse.fordiac.ide.library.model.library.util;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import org.eclipse.emf.ecore.xml.type.util.XMLTypeUtil;
import org.eclipse.emf.ecore.xml.type.util.XMLTypeValidator;

import org.eclipse.fordiac.ide.library.model.library.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.library.model.library.LibraryPackage
 * @generated
 */
public class LibraryValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final LibraryValidator INSTANCE = new LibraryValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.fordiac.ide.library.model.library"; //$NON-NLS-1$

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * The cached base package validator.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XMLTypeValidator xmlTypeValidator;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibraryValidator() {
		super();
		xmlTypeValidator = XMLTypeValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return LibraryPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case LibraryPackage.ATTRIBUTE:
				return validateAttribute((Attribute)value, diagnostics, context);
			case LibraryPackage.DEPENDENCIES:
				return validateDependencies((Dependencies)value, diagnostics, context);
			case LibraryPackage.EXCLUDES:
				return validateExcludes((Excludes)value, diagnostics, context);
			case LibraryPackage.EXPORTS:
				return validateExports((Exports)value, diagnostics, context);
			case LibraryPackage.INCLUDES:
				return validateIncludes((Includes)value, diagnostics, context);
			case LibraryPackage.LIBRARY:
				return validateLibrary((Library)value, diagnostics, context);
			case LibraryPackage.LIBRARY_ELEMENT:
				return validateLibraryElement((LibraryElement)value, diagnostics, context);
			case LibraryPackage.MANIFEST:
				return validateManifest((Manifest)value, diagnostics, context);
			case LibraryPackage.PRODUCT:
				return validateProduct((Product)value, diagnostics, context);
			case LibraryPackage.REQUIRED:
				return validateRequired((Required)value, diagnostics, context);
			case LibraryPackage.VERSION_INFO:
				return validateVersionInfo((VersionInfo)value, diagnostics, context);
			case LibraryPackage.NAME_SPACE_FILTER:
				return validateNameSpaceFilter((String)value, diagnostics, context);
			case LibraryPackage.SYMBOLIC_NAME:
				return validateSymbolicName((String)value, diagnostics, context);
			case LibraryPackage.VERSION_RANGE:
				return validateVersionRange((String)value, diagnostics, context);
			case LibraryPackage.VERSION_SIMPLE:
				return validateVersionSimple((String)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttribute(Attribute attribute, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(attribute, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDependencies(Dependencies dependencies, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(dependencies, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExcludes(Excludes excludes, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(excludes, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExports(Exports exports, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(exports, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIncludes(Includes includes, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(includes, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLibrary(Library library, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(library, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLibraryElement(LibraryElement libraryElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(libraryElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateManifest(Manifest manifest, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(manifest, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateProduct(Product product, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(product, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRequired(Required required, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(required, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVersionInfo(VersionInfo versionInfo, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(versionInfo, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNameSpaceFilter(String nameSpaceFilter, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateNameSpaceFilter_Pattern(nameSpaceFilter, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @see #validateNameSpaceFilter_Pattern
	 */
	public static final  PatternMatcher [][] NAME_SPACE_FILTER__PATTERN__VALUES =
		new PatternMatcher [][] {
			new PatternMatcher [] {
				XMLTypeUtil.createPatternMatcher("((_[a-zA-Z0-9]|[a-zA-Z])(_?[a-zA-Z0-9])*|\\*\\*?)(::((_[a-zA-Z0-9]|[a-zA-Z])(_?[a-zA-Z0-9])*|\\*\\*?))*")
			}
		};

	/**
	 * Validates the Pattern constraint of '<em>Name Space Filter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNameSpaceFilter_Pattern(String nameSpaceFilter, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validatePattern(LibraryPackage.Literals.NAME_SPACE_FILTER, nameSpaceFilter, NAME_SPACE_FILTER__PATTERN__VALUES, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSymbolicName(String symbolicName, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateSymbolicName_Pattern(symbolicName, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @see #validateSymbolicName_Pattern
	 */
	public static final  PatternMatcher [][] SYMBOLIC_NAME__PATTERN__VALUES =
		new PatternMatcher [][] {
			new PatternMatcher [] {
				XMLTypeUtil.createPatternMatcher("[a-zA-Z][-_a-zA-Z0-9]*")
			}
		};

	/**
	 * Validates the Pattern constraint of '<em>Symbolic Name</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSymbolicName_Pattern(String symbolicName, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validatePattern(LibraryPackage.Literals.SYMBOLIC_NAME, symbolicName, SYMBOLIC_NAME__PATTERN__VALUES, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVersionRange(String versionRange, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateVersionRange_Pattern(versionRange, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @see #validateVersionRange_Pattern
	 */
	public static final  PatternMatcher [][] VERSION_RANGE__PATTERN__VALUES =
		new PatternMatcher [][] {
			new PatternMatcher [] {
				XMLTypeUtil.createPatternMatcher("[0-9]+(\\.[0-9]+)*|[\\[\\(][0-9]+(\\.[0-9]+)*\\-[0-9]+(\\.[0-9]+)*[\\]\\)]")
			}
		};

	/**
	 * Validates the Pattern constraint of '<em>Version Range</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVersionRange_Pattern(String versionRange, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validatePattern(LibraryPackage.Literals.VERSION_RANGE, versionRange, VERSION_RANGE__PATTERN__VALUES, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVersionSimple(String versionSimple, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validateVersionSimple_Pattern(versionSimple, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @see #validateVersionSimple_Pattern
	 */
	public static final  PatternMatcher [][] VERSION_SIMPLE__PATTERN__VALUES =
		new PatternMatcher [][] {
			new PatternMatcher [] {
				XMLTypeUtil.createPatternMatcher("[0-9]+(\\.[0-9]+)*")
			}
		};

	/**
	 * Validates the Pattern constraint of '<em>Version Simple</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVersionSimple_Pattern(String versionSimple, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validatePattern(LibraryPackage.Literals.VERSION_SIMPLE, versionSimple, VERSION_SIMPLE__PATTERN__VALUES, diagnostics, context);
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //LibraryValidator

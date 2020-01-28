package org.eclipse.fordiac.ide.export.forte_ng.simple;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate;
import org.eclipse.fordiac.ide.export.forte_ng.st.STAlgorithmFilter;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class SimpleFBImplTemplate extends ForteFBTemplate {
  @Accessors(AccessorType.PROTECTED_GETTER)
  private SimpleFBType type;
  
  @Extension
  private STAlgorithmFilter stAlgorithmFilter = new STAlgorithmFilter();
  
  public SimpleFBImplTemplate(final SimpleFBType type, final String name, final Path prefix) {
    super(name, prefix);
    this.type = type;
  }
  
  @Override
  public CharSequence generate() {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateHeader = this.generateHeader();
    _builder.append(_generateHeader);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateImplIncludes = this.generateImplIncludes();
    _builder.append(_generateImplIncludes);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateFBDefinition = this.generateFBDefinition();
    _builder.append(_generateFBDefinition);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateFBInterfaceDefinition = this.generateFBInterfaceDefinition();
    _builder.append(_generateFBInterfaceDefinition);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateFBInterfaceSpecDefinition = this.generateFBInterfaceSpecDefinition();
    _builder.append(_generateFBInterfaceSpecDefinition);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    CharSequence _generateAlgorithms = this.generateAlgorithms();
    _builder.append(_generateAlgorithms);
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence generateAlgorithms() {
    StringConcatenation _builder = new StringConcatenation();
    CharSequence _generateAlgorithm = this.generateAlgorithm(this.type.getAlgorithm());
    _builder.append(_generateAlgorithm);
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _generateAlgorithm(final Algorithm alg) {
    List<String> _errors = this.getErrors();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Cannot export algorithm ");
    Class<? extends Algorithm> _class = alg.getClass();
    _builder.append(_class);
    _errors.add(_builder.toString());
    return "";
  }
  
  protected CharSequence _generateAlgorithm(final STAlgorithm alg) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void ");
    CharSequence _fBClassName = this.getFBClassName();
    _builder.append(_fBClassName);
    _builder.append("::alg_");
    String _name = alg.getName();
    _builder.append(_name);
    _builder.append("(void) {");
    _builder.newLineIfNotEmpty();
    _builder.append("  ");
    CharSequence _generate = this.stAlgorithmFilter.generate(alg, this.getErrors());
    _builder.append(_generate, "  ");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence generateAlgorithm(final Algorithm alg) {
    if (alg instanceof STAlgorithm) {
      return _generateAlgorithm((STAlgorithm)alg);
    } else if (alg != null) {
      return _generateAlgorithm(alg);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(alg).toString());
    }
  }
  
  @Pure
  protected SimpleFBType getType() {
    return this.type;
  }
}

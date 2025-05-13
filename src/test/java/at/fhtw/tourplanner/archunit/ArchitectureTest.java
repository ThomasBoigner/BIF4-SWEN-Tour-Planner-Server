package at.fhtw.tourplanner.archunit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "at.fhtw.tourplanner", importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
public class ArchitectureTest {
    private static final String PRESENTATION = "Presentation";
    private static final String SERVICE = "Service";
    private static final String PERSISTENCE = "Persistence";
    private static final String DOMAIN = "Domain";
    private static final String FOUNDATION = "Foundation";
    private static final String CONFIGURATION = "Configuration";
    private static final String VALIDATION = "Validation";

    private static final String PKG_PRESENTATION = "..presentation..";
    private static final String PKG_SERVICE = "..service..";
    private static final String PKG_PERSISTENCE = "..persistence..";
    private static final String PKG_DOMAIN = "..domain..";
    private static final String PKG_FOUNDATION = "..foundation..";
    private static final String PKG_CONFIGURATION = "..config..";
    private static final String PKG_VALIDATION = "..validation..";

    @ArchTest
    static final ArchRule layeredArchitecture = layeredArchitecture()
            .consideringAllDependencies()
            .layer(PRESENTATION).definedBy(PKG_PRESENTATION)
            .layer(SERVICE).definedBy(PKG_SERVICE)
            .layer(PERSISTENCE).definedBy(PKG_PERSISTENCE)
            .layer(DOMAIN).definedBy(PKG_DOMAIN)
            .layer(FOUNDATION).definedBy(PKG_FOUNDATION)
            .layer(CONFIGURATION).definedBy(PKG_CONFIGURATION)
            .layer(VALIDATION).definedBy(PKG_VALIDATION)
            .whereLayer(PRESENTATION).mayOnlyBeAccessedByLayers(VALIDATION)
            .whereLayer(SERVICE).mayOnlyBeAccessedByLayers(PRESENTATION, SERVICE, VALIDATION, CONFIGURATION)
            .whereLayer(PERSISTENCE).mayOnlyBeAccessedByLayers(SERVICE)
            .whereLayer(DOMAIN).mayOnlyBeAccessedByLayers(SERVICE, PERSISTENCE, VALIDATION)
            .whereLayer(FOUNDATION).mayOnlyBeAccessedByLayers(PERSISTENCE, SERVICE, PRESENTATION, FOUNDATION)
            .whereLayer(CONFIGURATION).mayNotBeAccessedByAnyLayer()
            .allowEmptyShould(true);

    @ArchTest
    static final ArchRule serviceClassesMustResideInLayerService = classes().that().haveSimpleNameEndingWith("Service")
            .should().resideInAnyPackage(PKG_SERVICE)
            .allowEmptyShould(true);

    @ArchTest
    static final ArchRule restControllerClassesMustResideInLayerPresentation = classes().that().haveSimpleNameEndingWith("RestController")
            .should().resideInAnyPackage(PKG_PRESENTATION)
            .allowEmptyShould(true);
}

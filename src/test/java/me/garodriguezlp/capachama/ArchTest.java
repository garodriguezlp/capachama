package me.garodriguezlp.capachama;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("me.garodriguezlp.capachama");

        noClasses()
            .that()
            .resideInAnyPackage("me.garodriguezlp.capachama.service..")
            .or()
            .resideInAnyPackage("me.garodriguezlp.capachama.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..me.garodriguezlp.capachama.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}

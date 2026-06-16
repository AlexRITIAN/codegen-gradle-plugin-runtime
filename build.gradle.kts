import org.gradle.kotlin.dsl.compileOnly

plugins {
    `java-library`
    signing
    alias(libs.plugins.publish)
}

group = "io.github.alexritian"
version = "1.0.8"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // jooq
    api(libs.bundles.jooq.all)

    implementation(libs.jetbrains.annotations)
    compileOnly(libs.jackson.databind)

    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)

}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(project.group.toString(), project.name, project.version.toString())
    pom {
        name.set("${project.group}:${project.name}")
        description.set("This project provides runtime support libraries required by Codegen-gradle-plugin")
        url.set("https://github.com/AlexRITIAN/codegn-gradle-plugin-runtime")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("AlexRITIAN")
                name.set("Too_Young")
                email.set("yhritianfq@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/AlexRITIAN/codegn-gradle-plugin-runtime")
            connection.set("scm:git:git://github.com/AlexRITIAN/codegn-gradle-plugin-runtime")
            developerConnection.set("scm:git:ssh://git@github.com:AlexRITIAN/codegn-gradle-plugin-runtime.git")
        }
    }

}

signing {
    useGpgCmd()
    sign(publishing.publications)
}
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    `java-library`
    signing
    alias(libs.plugins.publish)
}

group = "io.github.alexritian"
version = "1.0.1-SNAPSHOT"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/AlexRITIAN/catalog")
        credentials {
            username = providers.gradleProperty("githubPackagesUsername").get()
            password = providers.gradleProperty("githubPackagesPassword").get()
        }
    }
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api(libs.commons.math3)

    // jooq
    api(libs.jooq)
    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.guava)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

publishing {
    repositories {
        maven {
            name = "githubPackages"
            url = uri("https://maven.pkg.github.com/AlexRITIAN/codegn-gradle-plugin-runtime")
            credentials(PasswordCredentials::class)
        }
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

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

tasks.register("resolveCatalog") {
    doLast {
        configurations.detachedConfiguration(
            dependencies.create("io.github.alexritian:catalog:0.0.1-SNAPSHOT")
        ).resolve()
    }
}


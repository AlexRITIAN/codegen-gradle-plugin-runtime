dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from("io.github.alexritian:catalog:0.0.16")
        }
    }
}

rootProject.name = "codegen-gradle-plugin-runtime"

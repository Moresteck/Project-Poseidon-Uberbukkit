// To import vars from gradle.properties, add an entry to buildSrc/src/main/kotlin/ProjectInfo.kt

java.toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
java.sourceCompatibility = JavaVersion.toVersion(javaVersion)
java.targetCompatibility = JavaVersion.toVersion(javaVersion)

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

plugins {
    id("java")
    id("maven-publish")
    id("idea")
    id("eclipse")
    id("net.kyori.blossom") version "2.1.0"
    id("com.gradleup.shadow") version "9.2.2"
    id("application")
}

dependencies {
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("net.sf.jopt-simple:jopt-simple:6.0-alpha-3")
    implementation("jline:jline:0.9.94")
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")
    implementation("com.mysql:mysql-connector-j:9.2.0")
    implementation("org.avaje:ebean:2.7.3")
    implementation("org.yaml:snakeyaml:1.7")
    implementation("com.google.guava:guava-collections:r03")
    implementation("org.jetbrains:annotations:20.0.0")

    // Bukkit Mods
    implementation("com.google.guava:guava:32.0.1-jre")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("com.google.code.gson:gson:2.9.0")
}

// For exposing statics to Java, see BuildParameters.java.peb inside the main/java-templates dir
sourceSets.main.configure {
    blossom.javaSources {
        property("server_software_name", serverSoftwareName)
        property("version", version.toString())
        property("description", description)
        property("homepage_url", homepageUrl)
        property("source_url", sourceUrl)
    }
}

// Applies templating to files, you can access values by using ${name}
// Uses the values from buildSrc
//
// Uncomment when you want to process a resource.
//tasks.processResources {
//    filesMatching(listOf()) {
//        expand(project.properties)
//    }
//}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

tasks.named<Jar>("jar").configure {
    manifest {
        from("src/main/resources/META-INF/MANIFEST.MF")
    }

    archiveClassifier = "original"
}

tasks.shadowJar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    archiveClassifier = ""

    from(listOf(sourceSets.main.get().output))

    exclude("junit/**")
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
}

// shadowjar creates handy task already, but if you don't like the name, just change this to "run" and set the classpath manually
tasks.named<JavaExec>("runShadow") {
//    dependsOn(tasks.shadowJar)

    var d = File("run");
    if (!d.exists())
        d.mkdirs()

    workingDir = d

//    classpath = files(tasks.shadowJar.get().archiveFile.get().asFile)
    mainClass = "org.bukkit.craftbukkit.Main"
}
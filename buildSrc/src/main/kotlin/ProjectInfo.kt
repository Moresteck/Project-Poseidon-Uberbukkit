import org.gradle.api.Project

// Exposes gradle.properties vars to build.gradle.kts

object ProjectInfo

val Project.serverSoftwareName: String get() = properties["server_software_name"].toString()
val Project.description: String get() = properties["description"].toString()
val Project.projectVersion: String get() = properties["version"].toString()

val Project.homepageUrl: String get() = properties["homepage_url"].toString()
val Project.issueUrl: String get() = properties["issue_url"].toString()
val Project.sourceUrl: String get() = properties["source_url"].toString()

val Project.javaVersion: String get() = properties["java_version"].toString()

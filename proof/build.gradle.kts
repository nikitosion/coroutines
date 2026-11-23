plugins { alias(libs.plugins.kotlin.jvm) }

kotlin { jvmToolchain(25) }

dependencies {
    testImplementation(project(":given"))
    testImplementation(project(":fixed"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.lincheck)
    testImplementation(libs.coroutines)
    testRuntimeOnly(libs.junit.launcher)
}

tasks.test {
    useJUnitPlatform { excludeTags("race-proof") }
    jvmArgs("-XX:+EnableDynamicAgentLoading")
}

tasks.register<Test>("raceProof") {
    group = "verification"
    description = "Run the race scenario for the selected indexer"
    testClassesDirs = sourceSets.test.get().output.classesDirs
    classpath = sourceSets.test.get().runtimeClasspath
    useJUnitPlatform { includeTags("race-proof") }
    jvmArgs("-XX:+EnableDynamicAgentLoading")
    systemProperty("kfd.target", providers.gradleProperty("kfd.target").getOrElse("given"))
    testLogging { showStandardStreams = true }
    outputs.upToDateWhen { false }
}

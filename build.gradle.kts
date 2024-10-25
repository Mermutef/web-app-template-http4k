import org.gradle.api.JavaVersion.VERSION_21
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

plugins {
    kotlin("jvm") version "1.9.23"
    application
    id("org.flywaydb.flyway") version ("10.13.0")
    id("org.jooq.jooq-codegen-gradle") version "3.19.10"
}

buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-database-postgresql:10.13.0")
        classpath("org.postgresql:postgresql:42.7.3")
    }
}

repositories {
    maven("mvn-repo")
    mavenCentral()
}

sourceSets["main"].kotlin {
    srcDir("src/main/kotlin-generated")
}

tasks.withType<JavaExec> {
    standardInput = System.`in`
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            allWarningsAsErrors = false
            jvmTarget = "21"
            freeCompilerArgs += "-Xjvm-default=all"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    java {
        sourceCompatibility = VERSION_21
        targetCompatibility = VERSION_21
    }

    test {
        useJUnitPlatform()
    }
}

application {
    mainClass = "ru.yarsu.WebApplicationKt"
}

val http4kVersion: String by project
val http4kConnectVersion: String by project
val junitVersion: String by project
val kotlinVersion: String by project
val ktlintVersion: String by project
val kotestVersion: String by project
val h2dbVersion: String by project
val flywayVersion: String by project

dependencies {
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("org.slf4j:slf4j-api:2.0.12")
    implementation("io.konform:konform-jvm:0.4.0")
    implementation("ch.qos.logback:logback-classic:1.5.4")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.flywaydb:flyway-core:10.13.0")
    implementation("org.jooq:jooq:3.19.8")
    implementation("org.jooq:jooq-postgres-extensions:3.19.8")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("org.http4k:http4k-client-okhttp:$http4kVersion")
    implementation("org.http4k:http4k-cloudnative:$http4kVersion")
    implementation("org.http4k:http4k-core:$http4kVersion")
    implementation("org.http4k:http4k-format-jackson:$http4kVersion")
    implementation("org.http4k:http4k-multipart:$http4kVersion")
    implementation("org.http4k:http4k-server-netty:$http4kVersion")
    implementation("org.http4k:http4k-template-pebble:$http4kVersion")
    implementation("org.http4k:http4k-cloudnative:$http4kVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.2")
    testImplementation("org.http4k:http4k-testing-approval:$http4kVersion")
    testImplementation("org.http4k:http4k-testing-hamkrest:$http4kVersion")
    testImplementation("org.http4k:http4k-testing-kotest:$http4kVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.2")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
}

tasks.register<Copy>("cacheLocal") {
    from(File("${gradle.gradleUserHomeDir}/caches/modules-2/files-2.1"))
    into("${projectDir.absolutePath}/mvn-repo")
    eachFile {
        val parts = this.path.split("/")
        this.path = "${parts[0].replace('.', '/')}/${parts[1]}/${parts[2]}/${parts[4]}"
    }
    includeEmptyDirs = false
}

val appProperties =
    Properties()
        .apply {
            val propertiesFile = project.file("app.properties")
            if (propertiesFile.exists()) {
                load(propertiesFile.reader())
            }
        }

val dbHost: String = appProperties.getProperty("db.host", "localhost")
val dbPort: String = appProperties.getProperty("db.port", "5432")
val dbName: String = appProperties.getProperty("db.base", "prom")
val jdbcUrl = "jdbc:postgresql://$dbHost:$dbPort/$dbName"
val dbUser: String = appProperties.getProperty("db.user", "postgres")
val dbPassword: String = appProperties.getProperty("db.password", "password")

flyway {
    url = jdbcUrl
    user = dbUser
    password = dbPassword
    locations = arrayOf("classpath:ru/yarsu/db/migrations")
    cleanDisabled = false
}

jooq {
    configuration {
        jdbc {
            url = jdbcUrl
            username = dbUser
            password = dbPassword
        }

        generator {
            name = "org.jooq.codegen.KotlinGenerator"

            database {
                includes = ".*"
                excludes = "flyway_schema_history"
                inputSchema = "public"
                catalogVersionProvider =
                    """
                    SELECT MAX(version) FROM flyway_schema_history
                    """.trimMargin()
                schemaVersionProvider =
                    """
                    SELECT MAX(version) FROM flyway_schema_history
                    """.trimMargin()
            }

            target {
                packageName = "ru.yarsu.db.generated"
                directory = "src/main/kotlin-generated"
            }
        }
    }
}

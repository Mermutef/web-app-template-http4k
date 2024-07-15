package ru.yarsu.config

import org.http4k.cloudnative.env.Environment
import java.io.File

data class AppConfig(
    val webConfig: WebConfig,
    val securityConfig: SecurityConfig,
    val dbConfig: DataBaseConfig,
) {
    companion object {
        private val appEnv =
            Environment.from(File("app.properties")) overrides
                Environment.JVM_PROPERTIES overrides
                Environment.ENV overrides
                WebConfig.defaultEnv

        fun readConfiguration(): AppConfig =
            AppConfig(
                WebConfig.fromEnvironment(appEnv),
                SecurityConfig.fromEnvironment(appEnv),
                DataBaseConfig.fromEnvironment(appEnv),
            )
    }
}

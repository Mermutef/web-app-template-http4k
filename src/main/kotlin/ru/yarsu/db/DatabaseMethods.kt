package ru.yarsu.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.impl.DSL.using
import ru.yarsu.config.DataBaseConfig

object DatabaseMethods {
    fun createJooqContext(
        config: DataBaseConfig,
        showJooqDebug: Boolean = false,
    ): DSLContext {
        val dataSource = hikariFromDatabaseConfig(config)
        val jooqSettings = Settings()
        jooqSettings.isExecuteLogging = showJooqDebug
        jooqSettings.isDiagnosticsLogging = showJooqDebug
        return using(
            dataSource,
            SQLDialect.POSTGRES,
            jooqSettings,
        )
    }

    private fun hikariFromDatabaseConfig(config: DataBaseConfig): HikariDataSource {
        val hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl = config.jdbc
        hikariConfig.username = config.user
        hikariConfig.password = config.password
        return HikariDataSource(hikariConfig)
    }

    inline fun <IN1 : Any, IN2 : Any, IN3 : Any, IN4 : Any, IN5 : Any, IN6 : Any, IN7 : Any, IN8 : Any, IN9 : Any, OUT : Any> safeLet(
        arg1: IN1?,
        arg2: IN2?,
        arg3: IN3?,
        arg4: IN4?,
        arg5: IN5?,
        arg6: IN6?,
        arg7: IN7?,
        arg8: IN8?,
        arg9: IN9?,
        block: (IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9) -> OUT?,
    ): OUT? =
        if (arg1 != null && arg2 != null && arg3 != null && arg4 != null && arg5 != null && arg6 != null && arg7 != null && arg8 != null && arg9 != null) {
            block(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9)
        } else {
            null
        }

    inline fun <IN1 : Any, IN2 : Any, IN3 : Any, IN4 : Any, IN5 : Any, IN6 : Any, OUT : Any> safeLet(
        arg1: IN1?,
        arg2: IN2?,
        arg3: IN3?,
        arg4: IN4?,
        arg5: IN5?,
        arg6: IN6?,
        block: (IN1, IN2, IN3, IN4, IN5, IN6) -> OUT?,
    ): OUT? =
        if (arg1 != null && arg2 != null && arg3 != null && arg4 != null && arg5 != null && arg6 != null) {
            block(arg1, arg2, arg3, arg4, arg5, arg6)
        } else {
            null
        }

    inline fun <IN1 : Any, IN2 : Any, IN3 : Any, OUT : Any> safeLet(
        arg1: IN1?,
        arg2: IN2?,
        arg3: IN3?,
        block: (IN1, IN2, IN3) -> OUT?,
    ): OUT? =
        if (arg1 != null && arg2 != null && arg3 != null) {
            block(arg1, arg2, arg3)
        } else {
            null
        }

    inline fun <IN1 : Any, OUT : Any> safeLet(
        arg1: IN1?,
        block: (IN1) -> OUT?,
    ): OUT? =
        if (arg1 != null) {
            block(arg1)
        } else {
            null
        }
}

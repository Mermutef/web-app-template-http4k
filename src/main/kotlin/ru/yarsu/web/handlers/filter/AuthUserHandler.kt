package ru.yarsu.web.handlers.filter

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.cookie.cookie
import org.http4k.core.cookie.invalidateCookie
import org.http4k.core.with
import org.http4k.lens.RequestContextLens
import ru.yarsu.domain.entities.JwtTools
import ru.yarsu.domain.entities.Specialist
import ru.yarsu.domain.operations.specialist.GetSpecialistOperation

class AuthUserHandler(
    private val next: HttpHandler,
    private val addAuthUserToContextLens: RequestContextLens<Specialist?>,
    private val getSpecialist: GetSpecialistOperation,
    private val jwtTools: JwtTools,
) : HttpHandler {
    override fun invoke(request: Request): Response {
        val jwt =
            request.cookie("auth")?.value
                ?: return next(request)
        val id =
            jwtTools.validateToken(jwt)
                ?: return next(request).invalidateCookie("auth")
        return next(request.with(addAuthUserToContextLens of getSpecialist.get(id)))
    }
}
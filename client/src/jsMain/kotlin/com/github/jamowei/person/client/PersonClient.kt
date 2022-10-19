package com.github.jamowei.person.client

import com.github.jamowei.common.client.Protocol
import io.ktor.client.*
import io.ktor.client.engine.js.*
import kotlinx.browser.window

class PersonClient(
    override val protocol: Protocol = Protocol.HTTPS,
    override val host: String = window.location.host,
    override val port: Int = 443
) : PersonClientBase() {
    override val client: HttpClient = HttpClient(Js, defaultClientConfig)
}
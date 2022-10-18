package com.github.jamowei.person.client

import com.github.jamowei.common.client.Port
import io.ktor.client.*
import io.ktor.client.engine.js.*
import kotlinx.browser.window

class PersonClient(
    override val host: String = window.location.host,
    override val port: Port = Port.HTTPS
) : PersonClientBase() {
    override val client: HttpClient = HttpClient(Js, clientConfig)
}
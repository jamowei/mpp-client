package com.github.jamowei.common.client

class NotFoundException(override val message: String, override val cause: Throwable) :
    Exception(message, cause)

class NoServiceResponseException(override val message: String, override val cause: Throwable) :
    Exception(message, cause)
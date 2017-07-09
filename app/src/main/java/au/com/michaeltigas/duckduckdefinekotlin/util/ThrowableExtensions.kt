package au.com.michaeltigas.duckduckdefinekotlin.util

import retrofit2.HttpException
import java.io.IOException
import javax.net.ssl.SSLHandshakeException


/**
 * Created by Michael Tigas on 9/7/17.
 *
 * Provide extensions for the [Throwable] class
 */

fun Throwable.isIoException(): Boolean = this is IOException

fun Throwable.isHttpException(): Boolean = this is HttpException

fun Throwable.isSSLHandshakeException(): Boolean {
    return this.javaClass.name == SSLHandshakeException::class.java.name
}

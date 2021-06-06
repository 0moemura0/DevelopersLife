package com.example.developerslife.network

sealed class Resource<T>(
    private val data: T? = null,
    private val message: String? = null,
    private val code: Int? = null
) {

    private var onSuccess: ((T) -> Unit)? = null
    private var onLoading: ((T?) -> Unit)? = null
    private var onError: ((Int?, String, T?) -> Unit)? = null

    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(code: Int?, message: String, data: T? = null) : Resource<T>(data, message, code)

    fun handle(op: Resource<T>.() -> Unit) {
        op(this)
        when (this) {
            is Loading -> {
                onLoading?.let { it(data) }
            }
            is Success -> {
                onSuccess?.let { it(data!!) }
            }
            is Error -> {
                onError?.let { it(code, message!!, data) }
            }
        }
    }

    fun success(onSuccess: (T) -> Unit) {
        this.onSuccess = onSuccess
    }

    fun loading(onLoading: (T?) -> Unit) {
        this.onLoading = onLoading
    }

    fun error(onError: (Int?, String, T?) -> Unit) {
        this.onError = onError
    }
}
package com.chaudharylabs.cricbuzzclone.data.api


sealed class NetworkResult<Result> {
    data class Success<Result>(val data: Result?) : NetworkResult<Result>()
    class Error<Result>(val message: String) : NetworkResult<Result>()
    class Loading<Result> : NetworkResult<Result>()
}

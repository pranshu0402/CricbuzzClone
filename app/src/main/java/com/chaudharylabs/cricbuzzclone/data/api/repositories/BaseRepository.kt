package com.chaudharylabs.cricbuzzclone.data.api.repositories

import android.content.Context
import com.chaudharylabs.cricbuzzclone.data.api.APIInterface
import com.chaudharylabs.cricbuzzclone.data.api.ApiClient

open class BaseRepository(protected val context: Context) {
    protected val retrofitInterface: APIInterface = ApiClient.retrofitInstance
}

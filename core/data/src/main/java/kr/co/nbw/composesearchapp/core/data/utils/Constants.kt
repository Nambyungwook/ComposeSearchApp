package kr.co.nbw.composesearchapp.core.data.utils

import kr.co.nbw.composesearchapp.core.data.BuildConfig

object Constants {
    const val BASE_URL = "https://dapi.kakao.com/"
    const val API_KEY = BuildConfig.bookApiKey
    const val DATE_TIME_FOMAT = "yyyy-MM-dd'T'HH:mm:ss"
    const val STARTING_PAGE_INDEX = 1
    const val PAGING_SIZE = 10
}
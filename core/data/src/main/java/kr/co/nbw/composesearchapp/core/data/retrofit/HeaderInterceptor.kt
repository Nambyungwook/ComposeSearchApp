package kr.co.nbw.composesearchapp.core.data.retrofit

import kr.co.nbw.composesearchapp.core.data.utils.Constants.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader("Authorization", "KakaoAK ${API_KEY}")

        return chain.proceed(requestBuilder.build())
    }
}
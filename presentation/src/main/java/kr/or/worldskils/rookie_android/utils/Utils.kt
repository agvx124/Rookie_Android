package kr.or.worldskils.rookie_android.utils

import androidx.databinding.library.BuildConfig
import kr.or.worldskils.rookie_android.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors


/**
 * Created by NA on 2020-06-02
 * skehdgur8591@naver.com
 */

class Utils {

    companion object {
        val RETROFIT: Retrofit =
            Retrofit.Builder()
                .client(getClient())
                .baseUrl(Constants.DEFAULT_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build()

        private fun getClient(): OkHttpClient {
            val builder: OkHttpClient.Builder = OkHttpClient.Builder()

            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(interceptor)
            }

            return builder.build()
        }
    }




}

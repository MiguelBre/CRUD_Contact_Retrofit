package br.senai.sp.jandira.contact_retrofit.api

import br.senai.sp.jandira.contact_retrofit.constants.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApi {

    companion object{
        private lateinit var instance: Retrofit

        fun getRetrofit(): Retrofit{
            if (!::instance.isInitialized) {
                instance = Retrofit
                    .Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return instance
        }
    }
}
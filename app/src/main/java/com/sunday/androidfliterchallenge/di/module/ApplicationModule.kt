package com.sunday.androidfliterchallenge.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sunday.androidfliterchallenge.BuildConfig
import com.sunday.androidfliterchallenge.data.remote.FilterService
import com.sunday.androidfliterchallenge.data.repository.FilterRepository
import com.sunday.androidfliterchallenge.data.repository.FilterRepositoryImpl
import com.sunday.androidfliterchallenge.rx.Scheduler
import com.sunday.androidfliterchallenge.rx.SchedulerImpl
import com.sunday.androidfliterchallenge.utils.HttpLoggingInterceptor
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
public class ApplicationModule {

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    fun provideRxScheduler() : Scheduler{
        return SchedulerImpl()
    }

    @Provides
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }

    @Provides
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder().serializeNulls()
//        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    internal fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor/*, authInterceptor: AuthInterceptor*/): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    internal fun retrofitAdapterProvider(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    internal fun filterServiceProvider(gson: Gson, okHttpClient: OkHttpClient): FilterService {
        val retrofit =  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
        return retrofit.create(FilterService::class.java)
    }

    @Provides
    fun filterRepositoryProvider(filterService: FilterService, disposable: CompositeDisposable, scheduler: Scheduler) : FilterRepository {
        return FilterRepositoryImpl(filterService, disposable, scheduler)
    }
}
package nl.hva.myteam.core.di

import nl.hva.myteam.BuildConfig
import nl.hva.myteam.core.navigation.Navigator
import nl.hva.myteam.core.platform.NetworkHandler
import nl.hva.myteam.features.data.datasource.Api
import nl.hva.myteam.features.data.repositories.PokemonRepository
import nl.hva.myteam.features.domain.usecases.GetAllPokemonUseCase
import nl.hva.myteam.features.domain.usecases.GetPokemonDetailsUseCase
import nl.hva.myteam.features.presentation.detail.DetailViewModel
import nl.hva.myteam.features.presentation.pokedex.PokedexViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.factory
import org.koin.experimental.builder.single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val applicationModule = module {
    single<Navigator>()
}

val networkModule = module {
    single { createOkHttpClient() }
    single { createRetrofit(okHttpClient = get()) }
    single { createWebService(retrofit = get()) }
    single { NetworkHandler(context = get()) }
}

val repositoryModule = module {
    single<PokemonRepository>()
}

val useCaseModule = module {
    factory<GetAllPokemonUseCase>()
    factory<GetPokemonDetailsUseCase>()
}

val viewModelModule = module {
    viewModel<PokedexViewModel>()
    viewModel<DetailViewModel>()
}

val koinModules = listOf(
    applicationModule,
    networkModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)

fun createOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        )

    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun createWebService(retrofit: Retrofit): Api {
    return retrofit.create(Api::class.java)
}
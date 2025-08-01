package com.verve.bittrack.di

import com.verve.bittrack.data.datasource.auth.AuthDataSource
import com.verve.bittrack.data.datasource.auth.FirebaseAuthDataSource
import com.verve.bittrack.data.datasource.coin.CoinDataSource
import com.verve.bittrack.data.datasource.coin.CoinDataSourceImpl
import com.verve.bittrack.data.datasource.coin.CoinDetailDataSource
import com.verve.bittrack.data.datasource.coin.CoinDetailDataSourceImpl
import com.verve.bittrack.data.datasource.favorite.FavoriteDataSource
import com.verve.bittrack.data.datasource.favorite.FavoriteDatabaseDataSource
import com.verve.bittrack.data.repository.CoinDetailRepository
import com.verve.bittrack.data.repository.CoinDetailRepositoryImpl
import com.verve.bittrack.data.repository.CoinRepository
import com.verve.bittrack.data.repository.CoinRepositoryImpl
import com.verve.bittrack.data.repository.FavoriteRepository
import com.verve.bittrack.data.repository.FavoriteRepositoryImpl
import com.verve.bittrack.data.repository.UserRepository
import com.verve.bittrack.data.repository.UserRepositoryImpl
import com.verve.bittrack.data.source.AppDatabase
import com.verve.bittrack.data.source.dao.FavoriteDao
import com.verve.bittrack.data.source.firebase.FirebaseService
import com.verve.bittrack.data.source.firebase.FirebaseServiceImpl
import com.verve.bittrack.data.source.network.services.ApiServices
import com.verve.bittrack.presentation.detail.DetailViewModel
import com.verve.bittrack.presentation.favorite.FavoriteViewModel
import com.verve.bittrack.presentation.home.HomeViewModel
import com.verve.bittrack.presentation.login.LoginViewModel
import com.verve.bittrack.presentation.profile.ProfileViewModel
import com.verve.bittrack.presentation.register.RegisterViewModel
import com.verve.bittrack.presentation.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {
    private val networkModule =
        module {
            single<ApiServices> { ApiServices.invoke() }
        }

    private val localModule =
        module {
            single<AppDatabase> { AppDatabase.getInstance(androidContext()) }
            single<FavoriteDao> { get<AppDatabase>().favoriteDao() }
        }

    private val firebaseModule =
        module {
            single<AuthDataSource> { FirebaseAuthDataSource(get()) }
            single<FirebaseService> { FirebaseServiceImpl() }
        }

    private val datasource =
        module {
            single<CoinDataSource> { CoinDataSourceImpl(get()) }
            single<CoinDetailDataSource> { CoinDetailDataSourceImpl(get()) }
            single<FavoriteDataSource> { FavoriteDatabaseDataSource(get()) }
        }

    private val repository =
        module {
            single<CoinRepository> { CoinRepositoryImpl(get()) }
            single<UserRepository> { UserRepositoryImpl(get()) }
            single<CoinDetailRepository> { CoinDetailRepositoryImpl(get()) }
            single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::HomeViewModel)
            viewModelOf(::LoginViewModel)
            viewModelOf(::RegisterViewModel)
            viewModelOf(::ProfileViewModel)
            viewModelOf(::SplashViewModel)
            viewModelOf(::FavoriteViewModel)
            viewModel { params ->
                DetailViewModel(
                    extras = params.get(),
                    coinDetailRepository = get(),
                    favoriteRepository = get(),
                )
            }
        }

    val modules =
        listOf(
            networkModule,
            localModule,
            datasource,
            repository,
            firebaseModule,
            viewModelModule,
        )
}

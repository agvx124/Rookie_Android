package kr.or.worldskils.rookie_android.di

import kr.or.worldskils.rookie_android.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
//    viewModel { MainViewModel(get()) }
//    viewModel { GetFileViewModel(get()) }
//    viewModel { LoginViewModel(get()) }
//    viewModel { GetMainViewModel(get(), get()) }
//    viewModel { SendMainViewModel(get()) }
//    viewModel { SendViewModel(get(), get()) }
//    viewModel { SplashViewModel(get()) }
//    viewModel { WaitSendViewModel(get()) }
}

//val remoteModule = module {
//    single { RemoteClient }
//}
//
//val dataSourceModule = module {
//    single { FileSourceImpl(get()) as FileDataSource }
//}
//
//val apiModule = module {
//    single { fileAPI }
//}
//
//val repositoryModule = module {
//    single { FileRepositoryImpl(get()) as FileRepository }
//}
//
//val useCaseModule = module {
//    single { FileUseCase(get()) }
//}
//
//val retrofit = RemoteClient.createRetrofit(true)
//private val fileAPI = retrofit.create(FileService::class.java)

val appModules = listOf(
    viewModelModule
//    remoteModule,
//    dataSourceModule,
//    apiModule,
//    repositoryModule,
//    useCaseModule
)
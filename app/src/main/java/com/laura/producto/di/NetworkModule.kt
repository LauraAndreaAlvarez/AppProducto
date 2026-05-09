package com.laura.producto.di
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.laura.producto.data.remote.ProductoApiService
import com.laura.producto.data.repository.ProductoRepositoryImpl
import com.laura.producto.domain.repository.ProductoRepository
import com.laura.producto.domain.user_case.AniadirProductoUseCase
import com.laura.producto.domain.user_case.BuscarProductosPorPatronSobreNombreUseCase
import com.laura.producto.domain.user_case.ObtenerProductosUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class) // Define que estas instancias viven tanto como la App
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        /*
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        */
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080") // IP para conectar con el localhost de Spring
            .client(okHttpClient) // <-- se inyecta el cliente con logs
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ProductoApiService {
        return retrofit.create(ProductoApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: ProductoApiService): ProductoRepository {
        // Inyectamos la implementación de Data en la interfaz de Domain
        return ProductoRepositoryImpl(api)
    }

    @Provides
    fun provideObtenerProductosUseCase(productoRepositorio: ProductoRepository): ObtenerProductosUseCase {
        return ObtenerProductosUseCase(productoRepositorio)
    }

    @Provides
    fun provideAniadirProductoUseCase(productoRepositorio: ProductoRepository): AniadirProductoUseCase {
        return AniadirProductoUseCase(productoRepositorio)
    }

    @Provides
    fun provideBuscarProductosPorPatronSobreNombreUseCase(productoRepositorio: ProductoRepository): BuscarProductosPorPatronSobreNombreUseCase {
        return BuscarProductosPorPatronSobreNombreUseCase(productoRepositorio)
    }
}
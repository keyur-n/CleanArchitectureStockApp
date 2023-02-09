package com.example.cleanarchitecturestockapp.data.repository

import com.example.cleanarchitecturestockapp.data.csv.CsvParser
import com.example.cleanarchitecturestockapp.data.local.StockDatabase
import com.example.cleanarchitecturestockapp.data.mapper.toCompanyListing
import com.example.cleanarchitecturestockapp.data.mapper.toCompanyListingEntity
import com.example.cleanarchitecturestockapp.data.mapper.toCompanyInfo
import com.example.cleanarchitecturestockapp.data.remote.StockApi
import com.example.cleanarchitecturestockapp.domain.model.CompanyInfo
import com.example.cleanarchitecturestockapp.domain.model.CompanyListing
import com.example.cleanarchitecturestockapp.domain.model.IntradayInfo
import com.example.cleanarchitecturestockapp.domain.repository.StockRepository
import com.example.cleanarchitecturestockapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDatabase,
    val companyListingParser: CsvParser<CompanyListing>,
    val intradayInfoParser: CsvParser<IntradayInfo>
) : StockRepository {
    private val dao = db.dao
    override suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading<List<CompanyListing>>(true))
            val localListing = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListing.map { it.toCompanyListing() }
            ))

            val isDbEmpty = localListing.isEmpty() && query.isBlank()
            val shouldLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListing = try {
                val response = api.getListing()
                companyListingParser.parse(response.byteStream())
            } catch (e: IOException) {
                emit(Resource.Error("Network Error"))
                null
            } catch (e: HttpException) {
                emit(Resource.Error("Couldn't Load Data"))
                null
            }
            remoteListing?.let { listings ->

                dao.removeCompanyListing()
                dao.insertCompanyListing(listings.map {
                    it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data = dao.searchCompanyListing("").map { it.toCompanyListing() }
                ))
                emit(Resource.Loading<List<CompanyListing>>(false))
            }
//            emit(Resource.Loading(false))
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response=api.getIntraday(symbol)
            val result=intradayInfoParser.parse(response.byteStream())
            Resource.Success(result)
        }catch (e:IOException){
            Resource.Error(e?.localizedMessage)
        }
        catch (e:HttpException){
            Resource.Error(e?.localizedMessage)
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val response=api.getCompanyInfo(symbol)
            Resource.Success(response.toCompanyInfo())
        }catch (e:IOException){
            Resource.Error(e?.localizedMessage)
        }
        catch (e:HttpException){
            Resource.Error(e?.localizedMessage)
        }
    }
}

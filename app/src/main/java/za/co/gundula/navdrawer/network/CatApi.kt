package za.co.gundula.navdrawer.network

import io.reactivex.Single
import retrofit2.http.GET
import za.co.gundula.navdrawer.model.CatRemoteModel

interface CatApi {

    //api/images/get?format=json&results_per_page=100&size=small&type=png
    @GET("api/images/get?format=json&results_per_page=100&size=small&type=png")
    fun getCats(): Single<List<CatRemoteModel>>
}
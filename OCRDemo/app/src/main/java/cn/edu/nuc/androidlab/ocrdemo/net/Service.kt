package cn.edu.nuc.androidlab.ocrdemo.net

import cn.edu.nuc.androidlab.ocrdemo.bean.Book
import cn.edu.nuc.androidlab.ocrdemo.bean.SearchResult
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by MurphySL on 2017/8/1.
 */
object Service{
    val api_douban : DoubanService by lazy {
        Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(DoubanService::class.java)
    }

}

interface DoubanService{
    @GET("book/isbn/:{name}")
    fun getBookInfoByISBN(@Path("name") name : String) : Observable<Book>

    @GET("book/search")
    fun searchBookInfo(@Query("q") q : String, @Query("count") count : Int = 1) : Observable<SearchResult>
}
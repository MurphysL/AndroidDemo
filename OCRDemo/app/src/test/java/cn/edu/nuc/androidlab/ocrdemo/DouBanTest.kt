package cn.edu.nuc.androidlab.ocrdemo

import cn.edu.nuc.androidlab.ocrdemo.net.Service
import org.junit.Test

/**
 * 豆瓣接口测试
 * Created by MurphySL on 2017/8/18.
 */
class DoubanTest{

    @Test
    fun getBookInfo(){
        Service.api_douban.getBookInfoByISBN("9787111407010").subscribe {
            //print(it)
        }
    }

    @Test
    fun douBanSearchTest(){
        Service.api_douban.searchBookInfo("算法导论").subscribe {
            print(it.books[0].author)
            it.books[0].tags.forEach {
                println(it)
            }
        }
    }
}
package cn.edu.nuc.androidlab.ocrdemo


import cn.edu.nuc.androidlab.ocrdemo.net.Service
import org.junit.Test

/**
 * Created by MurphySL on 2017/8/18.
 */

@Test
fun douBanSearchTest(){
    Service.api_douban.searchBookInfo("算法导论").subscribe {
        print(it.books[0].author)
    }
}
package cn.edu.nuc.androidlab.ocrdemo.bean

/**
 * Created by MurphySL on 2017/8/18.
 */
data class SearchResult(var start: Int,
                        var count: Int,
                        var total: Int,
                        var books: List<Book>)
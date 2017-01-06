package com.example.murphysl.autoupdatetest;


import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

import java.io.IOException;

/**
 * DownloadCreater
 *
 * @author: MurphySL
 * @time: 2016/11/18 22:07
 */


public class DownloadCreater {

    public static void main(String[] args) throws Exception {
        //生成包名
        Schema schema = new Schema(1 , "com.example.murphysl.autoupdatetest.db");
        Entity threadInfo = schema.addEntity("ThreadInfo");
        Entity fileInfo = schema.addEntity("FileInfo");
        threadInfo.addStringProperty("location").notNull();
        threadInfo.addIntProperty("start").notNull();
        threadInfo.addIntProperty("end").notNull();
        threadInfo.addIntProperty("isFinish");
        threadInfo.addStringProperty("threadName").notNull();
        fileInfo.addStringProperty("filename").notNull();
        fileInfo.addStringProperty("my_url").notNull();
        fileInfo.addIntProperty("length").notNull();
        fileInfo.addIntProperty("progress").notNull();

        new DaoGenerator().generateAll(schema , "D://db2");

    }
}

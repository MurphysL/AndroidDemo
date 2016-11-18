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
        Schema schema = new Schema(1 , "dao");
        Entity threadInfo = schema.addEntity("ThreadInfo");
        threadInfo.addStringProperty("location").notNull();
        threadInfo.addIntProperty("start").notNull();
        threadInfo.addIntProperty("end").notNull();
        threadInfo.addBooleanProperty("isFinish");
        threadInfo.addStringProperty("threadName").notNull();

        new DaoGenerator().generateAll(schema , "E://AndroidStudioProjects/AndroidDemo/autoupdatetest"
         + "/src/main/java/com/example/murphysl/autoupdatetest/db");

    }
}

package com.example.murphysl.todo;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

/**
 * NoteDaoGenerator
 *
 * @author: MurphySL
 * @time: 2016/11/2 0:22
 */

public class NoteDaoGenerator {

    public static final int version = 4;
    public static final String entityPackageName = "com.example.murphysl.todo";
    public static final String entityClassName = "Todo";
    public static final String daoPackageName = "com.example.murphysl.todo.db.dao";
    //自动生成模板类存放的绝对地址，也就是你的module创建的session文件夹 也就是java-gen
    public static final String autoGenerateJavaPath="E:\\AndroidStudioProjects\\AndroidDemo\\todo\\src\\main\\java-gen";

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(version, entityPackageName);
        schema.setDefaultJavaPackageDao(daoPackageName);//如果不指定 默认与entityPackageName一致
        Entity entity=schema.addEntity(entityClassName);
        entity.addIdProperty();//主键
        entity.addStringProperty("title");//表的地2列 列名
        entity.addStringProperty("content");//表的地3列 列名
        entity.addStringProperty("createTime");//表的地4列 列名
        entity.setClassNameDao("NoteDao");//设置dao类的名称
        entity.setTableName("todo");//设置表名,默认是entityClassName(NOTE)的大写形式
        //自动生成java模板类
        new DaoGenerator().generateAll(schema,autoGenerateJavaPath);

    }

}

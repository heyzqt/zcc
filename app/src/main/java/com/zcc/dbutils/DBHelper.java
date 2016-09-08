package com.zcc.dbutils;

import android.content.Context;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.zcc.entity.Address;
import com.zcc.entity.Business;
import com.zcc.entity.Collect;
import com.zcc.entity.Order;
import com.zcc.entity.ShoppingCar;
import com.zcc.entity.User;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 数据库操作类
 */
public class DBHelper {
    private static DbUtils db;

    public static DbUtils getInstance(Context context) {
        if (db == null) {
            synchronized (DBHelper.class) {
                if (db == null) {
                    DbUtils.DaoConfig config = new DbUtils.DaoConfig(context);
                    config.setDbName("zcc"); //数据库名
                    config.setDbVersion(1);  //版本
                    db = DbUtils.create(config);
                    try {//创建表
                        db.createTableIfNotExist(User.class);
                        db.createTableIfNotExist(Address.class);
                        db.createTableIfNotExist(Business.class);
                        db.createTableIfNotExist(Collect.class);
                        db.createTableIfNotExist(Order.class);
                        db.createTableIfNotExist(ShoppingCar.class);
                        db.configAllowTransaction(true);
                    } catch (DbException e) {
                        Toast.makeText(context, "创建表失败！", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        }
        return db;
    }
}


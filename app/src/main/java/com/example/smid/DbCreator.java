package com.example.smid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Class DbCreator kế thừa từ class SQLiteOpenHelper để tạo và quản lý cơ sở dữ liệu SQLite.
 *
 * @author Hồ Ngọc Hòa, Phạm Nguyễn Hoài Nam
 * @version 17/10/2023
 * @since 1.0
 */
public class DbCreator extends SQLiteOpenHelper {
    /** Tên của cơ sở dữ liệu.*/ public static final String DATABASE_NAME = "LastTerm.db";

    /** Tên của bảng dữ liệu.*/ public static final String TABLE_Result = "Result";

    /** Phiên bản của cơ sở dữ liệu.*/ private static final int DATABASE_VERSION = 1;


    /**
     * Khởi tạo một đối tượng 'DbCreator'.
     *
     * @param context Context của ứng dụng.
     */
    public DbCreator(Context context) {
        // Gọi constructor của lớp cơ sở để tạo cơ sở dữ liệu.
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Tạo bảng dữ liệu khi cơ sở dữ liệu được tạo mới.
     *
     * @param db Đối tượng `SQLiteDatabase` đại diện cho cơ sở dữ liệu.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL tạo bảng dữ liệu.
        db.execSQL("CREATE TABLE " + TABLE_Result +
                " (resultID INTEGER PRIMARY KEY AUTOINCREMENT, score INTEGER);");
    }


    /**
     * Nâng cấp cơ sở dữ liệu khi phiên bản của cơ sở dữ liệu thay đổi.
     *
     * @param db Đối tượng `SQLiteDatabase` đại diện cho cơ sở dữ liệu.
     * @param oldVersion Phiên bản cũ của cơ sở dữ liệu.
     * @param newVersion Phiên bản mới của cơ sở dữ liệu.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

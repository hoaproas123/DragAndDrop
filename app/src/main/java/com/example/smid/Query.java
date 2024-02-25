package com.example.smid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/**
 * Class 'Query' cung cấp các phương thức để thực hiện các thao tác trên cơ sở dữ liệu SQLite.
 *
 * @author Hồ Ngọc Hòa, Phạm Nguyễn Hoài Nam
 * @version 17/10/2023
 * @since 1.0
 */
public class Query {

    /** Đối tượng 'SQLiteDatabase' để thực hiện các thao tác trên cơ sở dữ liệu.*/ private final SQLiteDatabase db;


    /**
     * Khởi tạo một đối tượng 'Query'.
     *
     * @param context Đối tượng 'Context' của ứng dụng.
     */
    public Query(Context context) {
        // Tạo đối tượng 'DbCreator'.
        DbCreator dbCreator = new DbCreator(context);

        // Lấy đối tượng 'SQLiteDatabase' để thực hiện các thao tác trên cơ sở dữ liệu.
        db = dbCreator.getWritableDatabase();
    }


    /**
     * Thêm dữ liệu mới vào bảng 'Result'.
     *
     * @param r Đối tượng 'Result' chứa dữ liệu của bản ghi mới.
     */
    public void insertRecord(Result r) {
        try {
            // Tạo câu lệnh SQL để thêm bản ghi mới.
            String sql = "INSERT INTO " + DbCreator.TABLE_Result +
                    "(score) VALUES('" + r.getScore() + "');";

            // Thực thi câu lệnh SQL.
            db.execSQL(sql);

            // Trả về 1 nếu thêm thành công.
        } catch (Exception e) {
            // Log lỗi nếu thêm thất bại.
            Log.e("DB", e + "Query.Insert");

            // Trả về -1 nếu thêm thất bại.
        }
    }


    /**
     * Lấy tất cả các dữ liệu trong bảng 'Result'.
     *
     * @return Trả về một đối tượng 'Cursor' chứa tất cả các dữ liệu trong bảng 'Result'.
     */
    public Cursor getRecords() {
        // Tạo câu lệnh SQL để lấy tất cả các dữ liệu trong bảng 'Result' sắp xếp giảm dần theo resultID.
        String sql = "SELECT * FROM " + DbCreator.TABLE_Result + " ORDER BY resultID DESC";

        // Thực thi câu lệnh SQL và trả về một đối tượng 'Cursor' chứa tất cả các dữ liệu trong bảng 'Result'.
        return db.rawQuery(sql, null);
    }
}
package com.example.smid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
/**
 * Class 'ResultActivity' đại diện cho màn hình hiển thị tất cả các điểm của người chơi.

 * @author Hồ Ngọc Hòa, Phạm Nguyễn Hoài Nam
 * @version 17/10/2023
 * @since 1.0
 */
public class ResultActivity extends AppCompatActivity {

    /** Nút đăng xuất.*/ protected Button btn_Logout;

    /** Danh sách hiển thị tất cả các điểm của người chơi.*/ protected ListView lv_allscore;

    /** Chuỗi msg ghi file log.*/ protected String msg = "ResultActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Lấy danh sách hiển thị tất cả các điểm của người chơi.
        lv_allscore = findViewById(R.id.lv_allscore);

        // Tải dữ liệu vào danh sách.
        Load_list();

        // Lấy nút đăng xuất.
        btn_Logout = findViewById(R.id.button_Logout);

        // Cài đặt sự kiện cho nút đăng xuất.
        btn_Logout.setOnClickListener(v -> {
            // Chuyển đến màn hình đăng nhập.
            Intent i = new Intent(getApplication(), LoginActivity.class);
            startActivity(i);

            // Kết thúc màn hình hiện tại.
            finish();
        });
    }


    /**
     * Tải dữ liệu vào danh sách hiển thị tất cả các điểm của người chơi.
     */
    public void Load_list() {
        // Tạo một đối tượng 'ArrayList' để lưu trữ dữ liệu.
        ArrayList<String> al = new ArrayList<>();

        // Tạo một đối tượng 'Query' để truy vấn cơ sở dữ liệu.
        Query query = new Query(this);

        // Lấy tất cả các dữ liệu trong bảng 'Result'.
        Cursor c = query.getRecords();

        // Duyệt qua tất cả các dữ liệu và thêm vào 'ArrayList'.
        while (c.moveToNext()) {
            int stt = c.getInt(0);
            int score = c.getInt(1);
            al.add("Score " + stt + ": " + score);
        }

        // Tạo một đối tượng 'ArrayAdapter' để hiển thị dữ liệu trong danh sách.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, al);

        // Thiết lập adapter cho danh sách.
        lv_allscore.setAdapter(adapter);
    }


    /**
     * Ghi đè phương thức 'onStart()' của class 'AppCompatActivity'.
     * Được gọi khi hoạt động bắt đầu.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "onStart invoked");
    }


    /**
     * Ghi đè phương thức 'onResume()' của class 'AppCompatActivity'.
     * Được gọi khi hoạt động được tiếp tục.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "onResume invoked");
    }


    /**
     * Ghi đè phương thức 'onPause()' của class 'AppCompatActivity'.
     * Được gọi khi hoạt động bị tạm dừng.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "onPause invoked");
    }


    /**
     * Ghi đè phương thức 'onStop()' của class 'AppCompatActivity'.
     * Được gọi khi hoạt động bị dừng.
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "onStop invoked");
    }


    /**
     * Ghi đè phương thức 'onSaveInstanceState()' của class 'AppCompatActivity'.
     * Được gọi khi hệ thống lưu trạng thái của hoạt động.
     *
     * @param outState Đối tượng 'Bundle' để lưu trạng thái của hoạt động.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    /**
     * Ghi đè phương thức 'onRestoreInstanceState()' của class 'AppCompatActivity'.
     * Được gọi khi hệ thống khôi phục trạng thái của hoạt động.
     *
     * @param savedInstanceState Đối tượng `Bundle` chứa trạng thái của hoạt động đã lưu.
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    /**
     * Ghi đè phương thức 'onRestart()' của class 'AppCompatActivity'.
     * Được gọi khi hoạt động được khởi động lại.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(msg, "onRestart invoked");
    }


    /**
     * Ghi đè phương thức 'onDestroy()' của class 'AppCompatActivity'.
     * Được gọi khi hoạt động bị hủy.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(msg, "onDestroy invoked");
    }
}
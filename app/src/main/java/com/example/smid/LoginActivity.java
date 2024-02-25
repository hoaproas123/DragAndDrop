package com.example.smid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Class LoginActivity đại diện cho màn hình đăng nhập của ứng dụng.
 *
 * @author Hồ Ngọc Hòa, Phạm Nguyễn Hoài Nam
 * @version 17/10/2023
 * @since 1.0
 */
public class LoginActivity extends AppCompatActivity {
    /** Nút đăng ký.*/ protected Button btn_register;

    /** Nút đăng nhập.*/ protected Button btn_login;

    /** Ô nhập tên người dùng.*/ protected EditText edtT_username;

    /** Ô nhập mật khẩu.*/ protected EditText edtT_password;

    /** Đối tượng `Dialog` hiển thị khi người dùng nhấp vào nút đăng ký.*/ protected Dialog dialog;

    /** Chuỗi msg ghi file log.*/ protected String msg = "LoginActivity";

    /** Trạng thái đang mở của hộp thoại đăng ký.*/ protected boolean isOpenDialog = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Liên kết các thành phần với layout
        edtT_username = findViewById(R.id.editText_li_username);
        edtT_password = findViewById(R.id.editText_li_password);
        btn_login = findViewById(R.id.button_li_login);
        btn_register = findViewById(R.id.button_li_register);

        // Đặt sự kiện nhấp cho nút đăng ký.
        btn_register.setOnClickListener(v -> {
            openRegister_dialog(); //Mở hộp thoại đăng ký
            isOpenDialog = true; //Đánh dấu trạng thái của hộp thoại
        });

        // Đặt sự kiện nhấp cho nút đăng nhập.
        btn_login.setOnClickListener(v -> {
            // Lấy tên người dùng và mật khẩu đã nhập.
            String username = edtT_username.getText().toString();
            String password = edtT_password.getText().toString();

            // Kiểm tra tên người dùng và mật khẩu có đúng hay không.
            if (username.equals(Account.username) && password.equals(Account.password)) {
                // Hiển thị thông báo đăng nhập thành công.
                Toast.makeText(LoginActivity.this, R.string.toast_login_success, Toast.LENGTH_SHORT).show();

                // Tạo một đối tượng 'Intent' để chuyển sang màn hình chính.
                Intent i = new Intent(getApplication(), MainActivity.class);

                // Tạo một đối tượng 'Bundle' để truyền dữ liệu sang màn hình chính.
                Bundle inform = new Bundle();
                inform.putString("username", username);

                // Thêm dữ liệu vào đối tượng 'Intent'.
                i.putExtras(inform);

                // Chuyển sang màn hình chính.
                startActivity(i);

                // Kết thúc màn hình đăng nhập.
                finish();
            } else {
                // Hiển thị thông báo đăng nhập thất bại.
                Toast.makeText(LoginActivity.this, R.string.toast_login_fail, Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void openRegister_dialog() {
        // Tạo hộp thoại
        dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_register);

        // Lấy cửa sổ của hộp thoại
        Window window=dialog.getWindow();
        if(window==null){
            return;
        }

        // Thiết lập kích thước và nền của cửa sổ
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,1350);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Thiết lập vị trí của cửa sổ
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity= Gravity.CENTER;
        window.setAttributes(windowAttributes);

        // Không cho phép đóng hộp thoại khi nhấn bên ngoài
        dialog.setCancelable(false);

        // Lấy các thành phần trong hộp thoại
        EditText edtT_rg_username =dialog.findViewById(R.id.editText_rg_username);
        EditText edtT_rg_password =dialog.findViewById(R.id.editText_rg_password);
        Button btn_rg_register =dialog.findViewById(R.id.button_rg_register);

        // Thiết lập giá trị cho các ô nhập liệu
        edtT_rg_username.setText(Account.username);
        edtT_rg_password.setText(Account.password);

        // Thiết lập sự kiện cho nút đăng ký
        btn_rg_register.setOnClickListener(v -> {
            // Cập nhật thông tin tài khoản
            Account.username = edtT_rg_username.getText().toString();
            Account.password = edtT_rg_password.getText().toString();
            isOpenDialog=false;
            // Hiển thị thông báo đăng ký thành công
            Toast.makeText(LoginActivity.this, R.string.toast_register_action, Toast.LENGTH_SHORT).show();

            // Đóng hộp thoại
            dialog.dismiss();
        });
        // Hiển thị hộp thoại
        dialog.show();
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

        // Lưu trạng thái của sự kiện đăng nhập
        outState.putString("li_username", edtT_username.getText().toString());//lưu username
        outState.putString("li_password", edtT_password.getText().toString());//lưu password

        //Nếu hộp thoại đăng ký đang mở
        if (isOpenDialog) {
            //Lưu trạng thái đang mở
            outState.putBoolean("isOpenDialog", isOpenDialog);

            // Lưu trạng thái của hộp thoại đăng ký.
            EditText edtT_rg_username = dialog.findViewById(R.id.editText_rg_username);
            EditText edtT_rg_password = dialog.findViewById(R.id.editText_rg_password);
            outState.putString("rg_username", edtT_rg_username.getText().toString());//lưu username
            outState.putString("rg_password", edtT_rg_password.getText().toString());//lưu password
        }
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

        // Khôi phục trạng thái của sự kiện đăng nhập
        edtT_username.setText(savedInstanceState.getString("li_username"));//lấy username
        edtT_password.setText(savedInstanceState.getString("li_password"));//lấyt password

        isOpenDialog = savedInstanceState.getBoolean("isOpenDialog");//lấy trạng thái của isOpenDialog

        //Nếu hộp thoại đăng ký đang mở
        if (isOpenDialog) {
            openRegister_dialog();//Mở hộp thoại đăng ký
            // Khôi phục trạng thái của hộp thoại đăng ký.
            EditText edtT_rg_username = dialog.findViewById(R.id.editText_rg_username);
            EditText edtT_rg_password = dialog.findViewById(R.id.editText_rg_password);
            edtT_rg_username.setText(savedInstanceState.getString("rg_username"));//lấy username
            edtT_rg_password.setText(savedInstanceState.getString("rg_password"));//lấy mật khẩu
        }
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
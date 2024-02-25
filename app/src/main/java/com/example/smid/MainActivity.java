package com.example.smid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;

/**
 * Class MainActivity đại diện cho màn hình chính của ứng dụng.
 *
 * @author Hồ Ngọc Hòa, Phạm Nguyễn Hoài Nam
 * @version 17/10/2023
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {

    /** ImageView hiển thị hình ảnh chính.*/ protected ImageView img;

    /** ImageView hiển thị image hình tròn.*/ protected ImageView img_circle;

    /** ImageView hiển thị biểu tượng thùng rác.*/ protected ImageView img_recyle;

    /** Img phóng to thu nhỏ, kéo vào để sử dụng.*/ protected ImageView imgZoomOut, imgZoomIn;

    /** TextView hiển thị số lượng.*/ protected TextView tv_quantity;

    /** TextView hiển thị trạng thái đăng nhập.*/ protected TextView tv_loginState;

    /** Button tăng số lượng.*/ protected Button btn_Add;

    /** Button hoàn thành.*/ protected Button btn_Finish;

    /** RelativeLayout chứa tất cả các thành phần của giao diện người dùng.*/ protected RelativeLayout relativeLayout;

    /** Chuỗi msg ghi file log.*/ protected String msg = "MainActivity";

    /** Đường dẫn đến tệp hình ảnh.*/ protected Uri uri;

    /** Đối tượng Bitmap chứa dữ liệu của hình ảnh.*/ protected Bitmap bitmap;

    /** Vị trí mặc định của hình ảnh trên màn hình.*/ protected float locationDefaultX, locationDefaultY;

    /** Vị trí mặc định của hình ảnh trên màn hình.*/ protected float locationX, locationY;

    /** Hệ số tỷ lệ cho hình ảnh chính. */ protected float scaleX, scaleY;

    /** Số lượng.*/ protected int quantity = 0;


    /**
     * Khởi tạo các thành phần giao diện người dùng
     *
     * Khởi tạo các biến của giao diện người dùng.
     * @param không có tham số đầu vào
     * @return không có giá trị trả về
     */
    protected void InitUI() {
        // Lấy các thành phần của giao diện người dùng.
        relativeLayout = findViewById(R.id.container);
        tv_quantity = (TextView) findViewById(R.id.tv_quantity);
        tv_loginState = (TextView) findViewById(R.id.tv_loginState);
        img = (ImageView) findViewById(R.id.imageViewMain);
        img_circle = (ImageView) findViewById(R.id.imageViewCircle);
        img_recyle = (ImageView) findViewById(R.id.imageViewRe);
        imgZoomOut=(ImageView)findViewById(R.id.imageViewZoomOut);
        imgZoomIn=(ImageView)findViewById(R.id.imageViewZoomIn);
        btn_Add = (Button) findViewById(R.id.button_Add);
        btn_Finish = (Button) findViewById(R.id.button_finish);

        // Khởi tạo số lượng.
        quantity = 0;

        // Lấy thông tin đăng nhập từ màn hình trước.
        Intent intent = getIntent();
        Bundle infom = intent.getExtras();
        if (infom != null) {
            tv_loginState.setText("Username: " + infom.getString("username"));
        }
    }


    /**
     * Xử lý kết quả trả về từ hoạt động khác
     *
     * Phương thức này được gọi khi người chơi chọn một tệp hình ảnh. Nó lấy tệp hình ảnh và hiển thị nó trong hình ảnh chính.
     * @param requestCode mã yêu cầu của hoạt động đã khởi chạy
     * @param resultCode mã kết quả của hoạt động đã khởi chạy
     * @param data dữ liệu trả về từ hoạt động đã khởi chạy
     * @return không có giá trị trả về
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Vị trí mặc định của hình ảnh trên màn hình
        locationDefaultX=img.getX();
        locationDefaultY=img.getY();

        // Nếu kết quả là thành công
        if (resultCode == RESULT_OK) {
            if (data ==null) {
                return;
            }
            // Lấy URI của tệp ảnh
            uri = data.getData();
            // Truy cập tệp ảnh
            bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

                // Hiển thị tệp ảnh
                img.setImageBitmap(bitmap);



                //Thông báo kết quả
                if(btn_Add.getText().equals("Update"))
                {
                    Toast.makeText(this, R.string.toast_update_img, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, R.string.toast_add_img, Toast.LENGTH_SHORT).show();
                }

                // Vị trí mặc định của hình ảnh trên màn hình
                img.setVisibility(View.VISIBLE);
                btn_Add.setText("Update");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     Khởi tạo hoạt động chính của ứng dụng

     Phương thức này được gọi khi hoạt động được tạo. Nó khởi tạo các biến cần thiết cho chương trình.
     @param savedInstanceState trạng thái đã lưu của hoạt động

     @return không có giá trị trả về
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo giao diện người dùng
        InitUI();

        //Tạo sự kiện kéo thả cho img
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    //Khởi tạo Bóng và bắt đầu kéo
                    ClipData data = ClipData.newPlainText("" , "");
                    View.DragShadowBuilder shadowBuilder= new View.DragShadowBuilder(img);//Khởi tạo bóng
                    v.startDragAndDrop(data , shadowBuilder , img , 0);//bắt đầu kéo
                }
                return false;
            }
        });
        relativeLayout.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                if(event.getAction()==DragEvent.ACTION_DRAG_STARTED) {return true;}

                else if (event.getAction()==DragEvent.ACTION_DRAG_LOCATION) {
                    //Cập nhật vị trí của img trong khi kéo
                    img.setX(event.getX()-img.getWidth()/2);
                    img.setY(event.getY()-img.getHeight()/2);
                    setEvent(event);//Hàm xử lý hiệu ứng khi Drag
                    return true;
                }
                else if (event.getAction()==DragEvent.ACTION_DROP) {
                    if(InTrue(event,img_recyle))//Kiểm tra img có nằm trong vùng thả của img_recycle
                    {
                        img.setVisibility(View.GONE);//Ẩn img
                        btn_Add.setText("Add");
                        quantity=0;
                        scaleX=getResources().getInteger(R.integer.minScale);
                        scaleY=getResources().getInteger(R.integer.minScale);
                        // Set độ Scale hiện tại của img về mặc định
                        img.setScaleX(scaleX);
                        img.setScaleY(scaleY);
                        tv_quantity.setText("Quantity: "+ quantity);
                        //Thông báo đã xóa
                        Toast.makeText(MainActivity.this, R.string.toast_removed_action, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //Cập nhật vị trí img tại điểm drop
                        img.setX(event.getX()-img.getWidth()/2);
                        img.setY(event.getY()-img.getHeight()/2);
                        locationX=img.getX()-img_circle.getX();
                        locationY=img.getY()-img_circle.getY();
                        if(InTrue(event,img_circle)) {
                            //Tăng giá trị và hiển quantity khi drop image vào image_circle
                            quantity++;
                            tv_quantity.setText("Quantity: "+ quantity);
                        }
                        Toast.makeText(MainActivity.this, R.string.toast_droped_action, Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }

                else if (event.getAction()==DragEvent.ACTION_DRAG_ENTERED) {return true;}

                else if (event.getAction()==DragEvent.ACTION_DRAG_EXITED) {return true;}

                else if (event.getAction()==DragEvent.ACTION_DRAG_ENDED) {return true;}

                return false;
            }
        });

        //Tạo sự kiện click cho nút thêm image
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khởi tạo một đối tượng Intent mới với loại ACTION_GET_CONTENT
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //Nếu đã bị ẩn thì set lại vị trí ban đầu của img
                if(img.getVisibility()==View.GONE){
                    img.setX(locationDefaultX);
                    img.setY(locationDefaultY);
                }

                // Đặt các thuộc tính MIME_TYPE và EXTRA_ALLOW_MULTIPLE của đối tượng Intent
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Khởi chạy hoạt động Intent
                startActivityForResult(intent,MODE_APPEND);
            }
        });

        //Tạo sự kiện click cho nút finish
        btn_Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một đối tượng 'Query' để truy vấn cơ sở dữ liệu.
                Query query = new Query(MainActivity.this);
                Result r = new Result(quantity);//Lấy thông tin 1 Result từ số điểm quantity sau khi kết thúc trò chơi
                query.insertRecord(r);//Nhập dữ liệu Result r vào database

                //Mở activityResult để hiển thị kết quả
                Intent i=new Intent(getApplicationContext(), ResultActivity.class);
                startActivity(i);
                quantity=0; //Reset quantity về 0;
                tv_quantity.setText("Quantity: "+ quantity);
            }
        });
    }


    /**
     * Xử lý hiệu ứng khi kéo hình ảnh
     *
     * Cài đặt hiệu ứng cho con trỏ chuột
     * @param event sự kiện kéo thả
     * @return không có giá trị trả về
     */
    protected void setEvent(DragEvent event){
        // Kiểm tra xem hình ảnh chính có nằm trong vùng img_circle không
        if(InTrue(event,img_circle))//Nếu True
        {
            img_circle.setBackgroundColor(getColor(R.color.colorChange));// đổi màu background img_circle
        }
        else if(InTrue(event,img_circle)==false)//Nếu False
        {
            img_circle.setBackgroundColor(getColor(R.color.colorDefault));// trả màu background về màu mặc định
        }

        // Kiểm tra xem img có nằm trong vùng hình ảnh zoom in hay không
        if(InTrue(event,imgZoomIn)) //Nếu true
        {
            if(scaleX>getResources().getInteger(R.integer.maxScale)&&scaleY>getResources().getInteger(R.integer.maxScale)) // Nếu vượt qua mức zoom in cho phép
            {
                //set scale = hệ số scale tối đa
                scaleX=getResources().getInteger(R.integer.maxScale);
                scaleY=getResources().getInteger(R.integer.maxScale);
            }
            else //Ngược lại
            {
                //Tăng hệ số scale lên 0.2 đơn vị
                img.setScaleX(scaleX+=0.2);
                img.setScaleY(scaleY+=0.2);
            }

        }
        // Kiểm tra xem img có nằm trong vùng hình ảnh zoom out hay không
        if(InTrue(event,imgZoomOut)) //Nếu true
        {
            if(scaleX<getResources().getInteger(R.integer.minScale)&&scaleY<getResources().getInteger(R.integer.minScale))// Nếu vượt qua mức zoom out cho phép
            {
                //set scale = hệ số scale tối thiểu
                scaleX=getResources().getInteger(R.integer.minScale);
                scaleY=getResources().getInteger(R.integer.minScale);
            }
            //Giảm hệ số scale đi 0.2 đơn vị
            img.setScaleX(scaleX-=0.2);
            img.setScaleY(scaleY-=0.2);
        }
    }


    /**
     * Kiểm tra xem con trỏ chuột có nằm trong vùng img_circle hay không
     *
     * @param event sự kiện kéo thả
     * @param imageView hình ảnh cần kiểm tra
     * @return true nếu con trỏ chuột nằm trong vùng Image, false nếu ngược lại
     */    protected boolean InTrue(DragEvent event,ImageView imageView){
        // Lấy tọa độ của image view 1
        int x1 = (int) event.getX();
        int y1 = (int)event.getY();
        // Lấy tọa độ của image view 2
        int width2 = imageView.getWidth();
        int height2 = imageView.getHeight();
        int x2 = (int)imageView.getX();
        int y2 = (int)imageView.getY();
        // Tính khoảng cách từ điểm (x1, y1) đến tâm hình tròn
        int dx = x1 - (x2 +width2/2 + x2 + width2 - width2/2) / 2;
        int dy = y1 - (y2 +width2/2 + y2 + height2 -width2/2) / 2;
        double distance = Math.sqrt(dx*dx+dy*dy);
        if(distance<=width2/2)//Nếu khoảng cách từ con trỏ chuột đến tâm hình tròn <= bán kính hình tròn
        {
            return true;
        }
        return false;
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Lưu trạng thái sự kiện
        outState.putInt("quantity",quantity);//Lưu số điểm
        outState.putString("btn_add_state",btn_Add.getText().toString());//Lưu trạng thái của btn_add
        outState.putParcelable("uri",uri);//Lưu đường dẫn hình ảnh img
        //Lưu vị trí mặc định của img
        outState.putFloat("locationDefaultX",locationDefaultX);
        outState.putFloat("locationDefaultY",locationDefaultY);
        outState.putFloat("locationX",locationX);
        outState.putFloat("locationY", locationY);
        outState.putFloat("scaleX",scaleX);
        outState.putFloat("scaleY",scaleY);
    }


    /**
     * Ghi đè phương thức 'onRestoreInstanceState()' của class 'AppCompatActivity'.
     * Được gọi khi hệ thống khôi phục trạng thái của hoạt động.
     *
     * @param savedInstanceState Đối tượng `Bundle` chứa trạng thái của hoạt động đã lưu.
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Khôi phục trạng thái sự kiện
        quantity=savedInstanceState.getInt("quantity");//Lấy số điểm
        tv_quantity.setText("Quantity: "+quantity);//Hiển thị điểm
        //Lấy vị trí mặc định của img
        locationDefaultX=savedInstanceState.getFloat("locationDefaultX");
        locationDefaultY=savedInstanceState.getFloat("locationDefaultY");

        locationX=savedInstanceState.getFloat("locationX");
        locationY=savedInstanceState.getFloat("locationY");
        //Lấy độ scale hiện tại của img
        scaleX=savedInstanceState.getFloat("scaleX");
        scaleY=savedInstanceState.getFloat("scaleY");
        //Nếu ảnh đã được thêm
        if(savedInstanceState.getString("btn_add_state").equals("Update")){
            uri=savedInstanceState.getParcelable("uri");//Lấy đường dẫn ảnh của img
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                // Hiển thị tệp ảnh
                img.setImageBitmap(bitmap);
                img.setX(img_circle.getX()+locationX);
                img.setY(img_circle.getY()+locationY);
                Log.d("TAG", "onRestoreInstanceState: "+img_circle.getRight());
                Log.d("TAG", "onRestoreInstanceState: "+img_circle.getBottom());
                //Set độ scale hiện tại của img
                img.setScaleX(scaleX);
                img.setScaleY(scaleY);
            }catch (FileNotFoundException e){
                throw new RuntimeException(e);
            }
            if(img.getVisibility()==View.GONE)
            {
                //Hiển thị hình ảnh
                img.setVisibility(View.VISIBLE);
            }
            //Lưu trạng thái của btnAdd
            btn_Add.setText(savedInstanceState.getString("btn_add_state"));
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
package com.example.smid;
/**
 * Class 'Result' đại diện cho một dòng dữ liệu trong bảng 'Result' của cơ sở dữ liệu.
 *
 * @author Hồ Ngọc Hòa, Phạm Nguyễn Hoài Nam
 * @version 17/10/2023
 * @since 1.0
 */
public class Result {
    /** Số điểm.*/ private final int score;


    /**
     * Khởi tạo một đối tượng 'Result' với điểm được chỉ định.
     *
     * @param score số điểm.
     */
    public Result(int score) {
        this.score = score;
    }


    /**
     * Lấy điểm .
     *
     * @return số điểm.
     */
    public int getScore() {
        return score;
    }
}

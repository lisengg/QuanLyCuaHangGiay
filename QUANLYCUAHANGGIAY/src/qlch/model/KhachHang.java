/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlch.model;

/**
 *
 * @author TD
 */
public class KhachHang {
    private String makh;
    private String tenkh;
    private String sdt;
    private int tongdiem;
    private String nguoiTao;

    public KhachHang() {
    }
     @Override
    public String toString() {
        return this.tenkh;
    }
    public KhachHang(String makh, String tenkh, String sdt, int tongdiem, String nguoiTao) {
        this.makh = makh;
        this.tenkh = tenkh;
        this.sdt = sdt;
        this.tongdiem = tongdiem;
        this.nguoiTao = nguoiTao;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getTongdiem() {
        return tongdiem;
    }

    public void setTongdiem(int tongdiem) {
        this.tongdiem = tongdiem;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }
    
    
    
    
}

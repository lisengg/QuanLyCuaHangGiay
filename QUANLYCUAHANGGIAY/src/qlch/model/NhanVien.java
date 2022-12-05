/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlch.model;

/**
 *
 * @author PC
 */
public class NhanVien {

    private String MaNV;
    private String TenNV;
    private String ThamNien;
    private String SDT;
    private String MaCV;
    private String CongViec;
    private String HinhAnh;
<<<<<<< HEAD
    private String TaiKhoan;
    private String MatKhau;
    
=======
    private boolean vaiTro;
    private String MatKhau;

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public NhanVien(String MatKhau) {
        this.MatKhau = MatKhau;
    }
>>>>>>> 8de8ebbbaa8401fab289187e332c698f49da2a2c

//    public boolean isVaiTro() {
//        return vaiTro;
//    }
//
//    public void setVaiTro(boolean vaiTro) {
//        this.vaiTro = vaiTro;
//    }
//
//    public NhanVien(boolean vaiTro) {
//        this.vaiTro = vaiTro;
//    }

    public NhanVien() {
    }

    public NhanVien(String MaNV, String TenNV, String ThamNien, String SDT, String MaCV, String CongViec, String HinhAnh, String TaiKhoan, String MatKhau) {
        this.MaNV = MaNV;
        this.TenNV = TenNV;
        this.ThamNien = ThamNien;
        this.SDT = SDT;
        this.MaCV = MaCV;
        this.CongViec = CongViec;
        this.HinhAnh = HinhAnh;
        this.TaiKhoan = TaiKhoan;
        this.MatKhau = MatKhau;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public String getThamNien() {
        return ThamNien;
    }

    public void setThamNien(String ThamNien) {
        this.ThamNien = ThamNien;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getMaCV() {
        return MaCV;
    }

    public void setMaCV(String MaCV) {
        this.MaCV = MaCV;
    }

    public String getCongViec() {
        return CongViec;
    }

    public void setCongViec(String CongViec) {
        this.CongViec = CongViec;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }

<<<<<<< HEAD
    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String TaiKhoan) {
        this.TaiKhoan = TaiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    
    
    
=======
>>>>>>> 8de8ebbbaa8401fab289187e332c698f49da2a2c
}

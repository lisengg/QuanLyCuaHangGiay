
package qlch.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import qlch.model.NhanVien;
import qlch.connection.DBConnection;

/**
 *
 * @author PC
 */
public class NhanVienDAO extends MainDAO<NhanVien, String>{
    final String INSERT_SQL = "INSERT INTO NHANVIEN(MANV, TENNV, THAMNIEN, SDT, MACV, HINHANH) values(?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE NHANVIEN set TENNV = ?, THAMNIEN = ?, SDT = ?, MACV = ?, HINHANH = ? WHERE MANV = ?";
    final String DELETE_SQL = "DELETE FROM NHANVIEN WHERE MANV = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM NHANVIEN";
    final String SELECT_BY_ID_SQL = "SELECT * FROM NHANVIEN WHERE MANV = ?";
    final String SELECT_BY_TAIKHOAN_SQL = "select taikhoan,matkhau,tencv from NHANVIEN inner join CHUCVU on nhanvien.MACV=CHUCVU.macv where taikhoan = ?";
    
     @Override
    public void insert(NhanVien entity) {
        DBConnection.update(INSERT_SQL, entity.getMaNV(), entity.getTenNV(), entity.getThamNien(), entity.getSDT(), entity.getMaCV(), entity.getHinhAnh());
    }

    @Override
    public void update(NhanVien entity) {
        DBConnection.update(UPDATE_SQL, entity.getMaNV(), entity.getTenNV(), entity.getThamNien(), entity.getSDT(), entity.getMaCV(), entity.getHinhAnh());
    }

    @Override
    public void delete(String id) {
        DBConnection.update(DELETE_SQL, id);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = selectBySql(SELECT_BY_ID_SQL, id);
         if (list.isEmpty()) {
             return null;
         }
         return list.get(0);
    }
    
    public NhanVien selectByTaiKhoanNhanVien(String id) {
        List<NhanVien> list = selectBySql(SELECT_BY_TAIKHOAN_SQL, id);
         if (list.isEmpty()) {
             return null;
         }
         return list.get(0);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
       List<NhanVien> list = new ArrayList<NhanVien>();
        try {
            ResultSet rs = DBConnection.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MANV"));
                entity.setTenNV(rs.getString("TENNV"));
                entity.setThamNien(rs.getString("THAMNIEN"));
                entity.setSDT(rs.getString("SDT"));
                entity.setMaCV(rs.getString("MACV"));
                entity.setHinhAnh(rs.getString("HINHANH"));
               
                list.add(entity);
                 
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }
}

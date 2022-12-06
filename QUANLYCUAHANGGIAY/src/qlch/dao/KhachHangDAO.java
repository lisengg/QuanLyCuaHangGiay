
package qlch.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import qlch.model.KhachHang;
import qlch.connection.DBConnection;

/**
 *
 * @author TD
 */
public class KhachHangDAO extends MainDAO<KhachHang, String>{
     final String INSERT_SQL = "INSERT INTO KHACHHANG(MAKH, TENKH, SDT, TONGDIEM) values(?,?,?,?)";
    final String UPDATE_SQL = "UPDATE KHACHHANG set TENKH = ?, SDT = ?, TONGDIEM = ? WHERE MAKH = ?";
    final String DELETE_SQL = "DELETE FROM KHACHHANG WHERE MAKH = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM KHACHHANG";
    final String SELECT_BY_ID_SQL = "SELECT * FROM KHACHHANG WHERE MAKH = ?";

    @Override
    public void insert(KhachHang entity) {
       DBConnection.update(INSERT_SQL, entity.getMakh(), entity.getTenkh(), entity.getSdt(), entity.getTongdiem());
    }

    @Override
    public void update(KhachHang entity) {
        DBConnection.update(UPDATE_SQL, entity.getMakh(), entity.getTenkh(), entity.getSdt(), entity.getTongdiem());
    }

    @Override
    public void delete(String id) {
        DBConnection.update(DELETE_SQL, id);
    }

    @Override
    public List<KhachHang> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KhachHang selectById(String id) {
       List<KhachHang> list = selectBySql(SELECT_BY_ID_SQL, id);
         if (list.isEmpty()) {
             return null;
         }
         return list.get(0);
    }

    @Override
    protected List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<KhachHang>();
        try {
            ResultSet rs = DBConnection.query(sql, args);
            while (rs.next()) {
                KhachHang entity = new KhachHang();
                entity.setMakh(rs.getString("MAKH"));
                entity.setTenkh(rs.getString("TENKH"));
                entity.setSdt(rs.getString("SDT"));
                entity.setTongdiem(rs.getInt("TONGDIEM"));
                list.add(entity);
                 
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
   
}

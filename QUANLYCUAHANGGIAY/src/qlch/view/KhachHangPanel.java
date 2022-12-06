/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package qlch.view;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import qlch.dao.KhachHangDAO;
import qlch.model.KhachHang;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import qlch.connection.Validation;

/**
 *
 * @author TD
 */
public class KhachHangPanel extends javax.swing.JPanel {

    static String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=duan1;user=teamsix;password=win123;encrypt=true;trustServerCertificate=true";
    static String user = "teamsix";
    static String pass = "win123";
    DefaultTableModel model;
    KhachHangDAO dao = new KhachHangDAO();
    List<KhachHang> list = new ArrayList<>();
    int row = -1;

    public KhachHangPanel() {
        initComponents();
        fillTable();
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
        try {
            list = dao.selectAll();
            for (KhachHang kh : list) {
                Object[] row = {
                    kh.getMakh(),
                    kh.getTenkh(),
                    kh.getSdt(),
                    kh.getTongdiem(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private boolean validForm() {
        if (!Validation.isEmpty(txtMaKH, "Mã khách hàng không được rỗng!")) {
            return false;
        }
        if (!Validation.isID(txtMaKH, "Mã khách hàng sai định dạng! mã khách hàng có 4 ký tự với 2 kí tự đầu là 'KH' (bắt buộc) và 2 kí tự cuối là số bất kì ví dụ KH01")) {
            return false;
        }
        if (!Validation.isEmpty(txtTenKH, "Tên khách hàng không được rỗng!")) {
            return false;
        }
        if (!Validation.isEmpty(txtSdt, "Số điện thoại không được rỗng!")) {
            return false;
        }
        if (!Validation.isNumber(txtSdt, "Số điện thoại phải là số!")) {
            return false;
        }
        if (!Validation.isPhone(txtSdt, "Số điện thoại sai định dạng! Số điện thoại phải đủ 10 số và số đầu là 0.")) {
            return false;
        }
        if (!Validation.isEmpty(txtTongDiem, "Tổng điểm không được rỗng!")) {
            return false;
        }
        if (!Validation.isNumber(txtTongDiem, "Tổng điểm phải là số!")) {
            return false;
        }
        return true;
    }

    public void setForm(KhachHang kh) {
        txtMaKH.setText(kh.getMakh());
        txtTenKH.setText(kh.getTenkh());
        txtSdt.setText(String.valueOf(kh.getSdt()));
        txtTongDiem.setText(String.valueOf(kh.getTongdiem()));
    }

    KhachHang getForm() {
        KhachHang kh = new KhachHang();
        kh.setMakh(txtMaKH.getText());
        kh.setTenkh(txtTenKH.getText());
        kh.setSdt(txtSdt.getText());
        kh.setTongdiem(Integer.valueOf(txtTongDiem.getText()));
        return kh;
    }
    
    public void clearForm() {
        KhachHang kh = new KhachHang();
        this.setForm(kh);
        this.row = -1;
        //this.updateStatus();
        txtSdt.setText("");
        txtTongDiem.setText("");
        txtTimKiem.setText("");
    }

    public void insert() {
        KhachHang kh = getForm();
        try {
            dao.insert(kh);
            this.fillTable();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm mới thất bại!");
        }
    }

    public void update() {
        KhachHang kh = getForm();
        validForm();
        try {
            dao.update(kh);
            this.fillTable();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
        }
    }

    void delete() {
//        if (!Auth.isManager()) {
//            JOptionPane.showMessageDialog(this, "Ban khong co quyen xoa!");
//        } else {
        int out = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khách hàng này không ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (!(out == JOptionPane.YES_OPTION)) {
        } else {
            String makh = txtMaKH.getText();
            try {
                dao.delete(makh);
                this.fillTable();
                this.clearForm();
                JOptionPane.showMessageDialog(this, "xoá thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "xoá thất bại!");
            }
        }
//        }
    }

    public void Search() {
        try {
            fillTable();
            String user = "teamsix";
            String pass = "win123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=DUAN1;encrypt=false;";
            Connection con = DriverManager.getConnection(url, user, pass);
            DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
            String sql = "select * from KHACHHANG where SDT like ?";
            PreparedStatement st = con.prepareStatement(sql);

            String searchCriteria = "%";
            if (!txtTimKiem.getText().equals("")) {
                searchCriteria += txtTimKiem.getText() + "%";
            }

            st.setString(1, searchCriteria);
            ResultSet rs = st.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("MAKH"),
                    rs.getString("TENKH"),
                    rs.getString("SDT"),
                    rs.getString("TONGDIEM"),});
            }
            model.fireTableDataChanged();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void SearchName() {
        try {
            fillTable();
            String user = "teamsix";
            String pass = "win123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=DUAN1;encrypt=false;";
            Connection con = DriverManager.getConnection(url, user, pass);
            DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
            String sql = "select * from KHACHHANG where TENKH like ?";
            PreparedStatement st = con.prepareStatement(sql);

            String searchCriteria = "%";
            if (!txtTimKiem.getText().equals("")) {
                searchCriteria += txtTimKiem.getText() + "%";
            }

            st.setString(1, searchCriteria);
            ResultSet rs = st.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("MAKH"),
                    rs.getString("TENKH"),
                    rs.getString("SDT"),
                    rs.getString("TONGDIEM"),});
            }
            model.fireTableDataChanged();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void first() {
        row = 0;
        tblKhachHang.setRowSelectionInterval(row, row);
        Show();
    }

    void prev() {
        int index = tblKhachHang.getSelectedRow();
         index--;
        if (index < 0) {
           index = list.size() - 1;  
        }
         tblKhachHang.setRowSelectionInterval(index, index);
         Show();
    }

    void next() {
       int index = tblKhachHang.getSelectedRow();
         index++;
        if (index > list.size() - 1) {
           index = 0;
        }
         tblKhachHang.setRowSelectionInterval(index, index);
         Show();
    }

    void last() {
        this.row = tblKhachHang.getRowCount() - 1;
        tblKhachHang.setRowSelectionInterval(row, row);
        Show();
    }
    public void Show(){
         int row = tblKhachHang.getSelectedRow();
        txtMaKH.setText(tblKhachHang.getValueAt(row, 0).toString());
        txtTenKH.setText(tblKhachHang.getValueAt(row, 1).toString());
        txtSdt.setText(tblKhachHang.getValueAt(row, 2).toString());
        txtTongDiem.setText(tblKhachHang.getValueAt(row, 3).toString());
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        txtTimKiem = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        txtLamMoi = new javax.swing.JButton();
        txtMaKH = new javax.swing.JTextField();
        btnLoad = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        txtTongDiem = new javax.swing.JTextField();
        btntiemKiem = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(951, 480));
        setPreferredSize(new java.awt.Dimension(951, 622));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel2.setText("Quản Lý Khách Hàng");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel6.setText("Mã khách hàng:");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 120, -1));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel1.setText("Tên khách hàng:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 120, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel3.setText("Số điện thoại:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 120, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel7.setText("Tìm số điện thoại:");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 510, 120, -1));
        add(txtTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 470, 25));
        add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 500, 540, 30));

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Tổng điểm"
            }
        ));
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhachHang);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 880, 230));

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 540, 50, 30));

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 540, 50, 30));

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 540, 50, 30));

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 540, 50, 30));

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Create.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 540, 120, 30));

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Edit.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 540, 120, 30));

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 540, 120, 30));

        txtLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Refresh.png"))); // NOI18N
        txtLamMoi.setText("Làm Mới");
        txtLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLamMoiActionPerformed(evt);
            }
        });
        add(txtLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 540, 120, 30));
        add(txtMaKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 470, 25));

        btnLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Upload.png"))); // NOI18N
        btnLoad.setText("Load");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });
        add(btnLoad, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 140, 120, 30));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel8.setText("Tổng điểm:");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 120, -1));
        add(txtSdt, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, 470, 25));
        add(txtTongDiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, 470, 25));

        btntiemKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Search.png"))); // NOI18N
        btntiemKiem.setText("Tìm Kiếm ");
        btntiemKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntiemKiemActionPerformed(evt);
            }
        });
        add(btntiemKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 500, 120, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
       Show();
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validForm()) {
            int check = 0;
            List<KhachHang> list = dao.selectAll();
            for (KhachHang kh : list) {
                if (kh.getMakh().equals(txtMaKH.getText())) {
                    JOptionPane.showMessageDialog(this, "Không thể Lưu! mã khách hàng đã tồn tại");
                    check = 1;
                     //txtMaKH.setBackground(Color.yellow);
                    break;
                }else if(kh.getSdt().equals(txtSdt.getText())){
                    JOptionPane.showMessageDialog(this, "Không thể Lưu! số điện thoại khách hàng đã tồn tại");
                    check = 1;
                    break;
                } 
            }
            if (check == 0) {
                insert();
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (validForm()) {
            
            try {

                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection(url, user, pass);
                String sql = "update  KHACHHANG set TENKH = ?, SDT = ?, TONGDIEM = ?  where MAKH = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, txtTenKH.getText());
                st.setString(2, txtSdt.getText());
                st.setInt(3, Integer.valueOf(txtTongDiem.getText()));
                st.setString(4, txtMaKH.getText());
                st.executeUpdate();
                JOptionPane.showMessageDialog(this, "Cập Nhật thành công!");
                con.close();
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
            fillTable();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLamMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_txtLamMoiActionPerformed

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        fillTable();
        clearForm();
    }//GEN-LAST:event_btnLoadActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btntiemKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntiemKiemActionPerformed
        // TODO add your handling code here:
        Search();
      //  SearchName();
    }//GEN-LAST:event_btntiemKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btntiemKiem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JButton txtLamMoi;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTongDiem;
    // End of variables declaration//GEN-END:variables

}

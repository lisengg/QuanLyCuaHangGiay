/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

package qlch.view;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import qlch.dao.KhachHangDAO;
import qlch.model.KhachHang;

/**
 *
 * @author TD
 */
public class KhachHangPanel extends javax.swing.JPanel {

    KhachHangDAO dao = new KhachHangDAO();
    int row = 0;
    public KhachHangPanel() {
        initComponents();
        fillTable();
//        updateStatus();
    }

     public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
        try {
            List<KhachHang> list = dao.selectAll();
            for (KhachHang kh : list) {
                Object[] row = {
                    kh.getMakh(),
                    kh.getTenkh(),
                    kh.getSdt(),
                    kh.getTongdiem(),
                    kh.getNguoiTao()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
   
     public void setForm(KhachHang kh) {
        txtmakh.setText(kh.getMakh());
        txttenkh.setText(kh.getTenkh());
        txtsdt.setText(String.valueOf(kh.getSdt()));
        txttongdiem.setText(String.valueOf(kh.getTongdiem()));
        txtnguoitao.setText(kh.getNguoiTao());
    }
    
    KhachHang getForm() {
        KhachHang kh = new KhachHang();
        kh.setMakh(txtmakh.getText());
        kh.setTenkh(txttenkh.getText());
        kh.setSdt(txtsdt.getText());
        kh.setTongdiem(Integer.valueOf(txttongdiem.getText()));
        kh.setNguoiTao(txtnguoitao.getText());
        
        return kh;
    }

    void edit() {
        try {
            String maKH = (String) tblKhachHang.getValueAt(this.row, 0);
            KhachHang kh = dao.selectById(maKH);
            if (kh != null) {
                setForm(kh);
                //updateStatus();
               // tabs.setSelectedIndex(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

//    void updateStatus() {
//        boolean edit = this.row >= 0;
//        boolean first = this.row == 0;
//        boolean last = this.row == tblKhachHang.getRowCount();
////        txtMaCD.setEditable(!edit);
//        //Khi insert thì không update, delete
//        btnThem.setEnabled(edit);
//        btnSua.setEnabled(edit);
//        btnXoa.setEnabled(edit);
//
//        btnFirst.setEnabled(edit && !first);
//        btnPrev.setEnabled(edit && !first);
//        btnNext.setEnabled(edit && !first);
//        btnLast.setEnabled(edit && !first);
//    }
    public void clearForm() {
        KhachHang kh = new KhachHang();
        this.setForm(kh);
        this.row = -1;
        //this.updateStatus();
        txtsdt.setText("");
        txttongdiem.setText("");
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
                String makh = txtmakh.getText();
                try {
                    dao.delete(makh);
                    this.fillTable();
                    this.clearForm();
                    JOptionPane.showMessageDialog(this, "xoa thanh cong!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "xoa that bai!");
                }
          }
//        }
    }
    void first(){
        this.row = 0;
        this.edit();
    }
    
    void prev(){
        if(this.row > 0){
            this.row--;
            this.edit();
        }
    }
    
    void next(){
        if(this.row < tblKhachHang.getRowCount()-1){
            this.row++;
            this.edit();
        }
        
    }
    
    void last(){
        this.row = tblKhachHang.getRowCount()-1;
        this.edit();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtnguoitao = new javax.swing.JTextField();
        txttongdiem = new javax.swing.JTextField();
        txtsdt = new javax.swing.JTextField();
        txttenkh = new javax.swing.JTextField();
        txttimkiem = new javax.swing.JTextField();
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
        txtmakh = new javax.swing.JTextField();
        btntiemKiem = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

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
        jLabel7.setText("Tổng điểm:");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 120, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel5.setText("Người tạo:");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 120, -1));

        txtnguoitao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnguoitaoActionPerformed(evt);
            }
        });
        add(txtnguoitao, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 240, 470, 25));

        txttongdiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttongdiemActionPerformed(evt);
            }
        });
        add(txttongdiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, 470, 25));
        add(txtsdt, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, 470, 25));

        txttenkh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttenkhActionPerformed(evt);
            }
        });
        add(txttenkh, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 470, 25));
        add(txttimkiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 440, 390, 30));

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Tổng điểm", "Người tạo"
            }
        ));
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhachHang);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, 700, 140));

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 440, -1, -1));

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 440, -1, -1));

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 440, -1, -1));

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 440, -1, -1));

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Create.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 480, 100, 30));

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Edit.png"))); // NOI18N
        btnSua.setText("Sữa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 480, 100, 30));

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 480, 110, 30));

        txtLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Refresh.png"))); // NOI18N
        txtLamMoi.setText("Làm Mới");
        txtLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLamMoiActionPerformed(evt);
            }
        });
        add(txtLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 480, 110, 30));
        add(txtmakh, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 470, 25));

        btntiemKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Search.png"))); // NOI18N
        btntiemKiem.setText("Tìm Kiếm");
        btntiemKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntiemKiemActionPerformed(evt);
            }
        });
        add(btntiemKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 440, 110, 30));

        jButton1.setText("Thanh toán");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 490, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void txtnguoitaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnguoitaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnguoitaoActionPerformed

    private void txttongdiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttongdiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttongdiemActionPerformed

    private void txttenkhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttenkhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttenkhActionPerformed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
//        int row = tblKhachHang.getSelectedRow();
//        System.out.println(tblKhachHang.getValueAt(row, 5).toString());
//        txttimkiem.setText(tblKhachHang.getValueAt(row, 0).toString());
//        txttenkh.setText(tblKhachHang.getValueAt(row, 1).toString());
//        txtsdt.setText(tblKhachHang.getValueAt(row, 2).toString());
//        txttongdiem.setText(tblKhachHang.getValueAt(row, 3).toString());
//        txtnguoitao.setText(tblKhachHang.getValueAt(row, 4).toString());
        
        int row = tblKhachHang.getSelectedRow();
        //System.out.println("'" + tblNhanVien.getValueAt(row, 5).toString() + "'");
        txtmakh.setText(tblKhachHang.getValueAt(row, 0).toString());
        txttenkh.setText(tblKhachHang.getValueAt(row, 1).toString());
        txtsdt.setText(tblKhachHang.getValueAt(row, 2).toString());
        txttongdiem.setText(tblKhachHang.getValueAt(row, 3).toString());
        txtnguoitao.setText(tblKhachHang.getValueAt(row, 4).toString());
       
            
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insert();

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
       delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLamMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_txtLamMoiActionPerformed

    private void btntiemKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntiemKiemActionPerformed
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Connection con = DriverManager.getConnection(url, user, pass);
//            String sql = "select *,count(*) over() totalRow from NhanVien where MANV = ?";
//            PreparedStatement st = con.prepareStatement(sql);
//            st.setString(1, txtTiemKiem.getText());
//            ResultSet rs = st.executeQuery();
//            rs.next();
//            if (rs.getInt("totalRow") > 0) {
//                JOptionPane.showMessageDialog(this, "Đã tìm thấy nhân viên có mã : " + txtTiemKiem.getText());
//                txttimkiem.setText(rs.getString(1));
//                txttenkh.setText(rs.getString(2));
//                txtsdt.setText(rs.getString(3));
//                txttongdiem.setText(rs.getString(4));
//                txtnguoitao.setText(rs.getString(5));
//                //                lblimg.setText("");
//                //                ImageIcon img = new ImageIcon(getClass().getResource("/HinhAnh/" + rs.getString(8)));
//                //                Image im = img.getImage();
//                //                im.getScaledInstance(lblimg.getWidth(), lblimg.getHeight(), 0);
//                //                lblimg.setIcon(img);
//                //tx.setText(rs.getString(6));
//                //txtBoNho.setText(rs.getString(7));
//
//            }
//            con.close();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Không tìm thấy Nhân Viên có mã : " + txtTiemKiem.getText());
//            reset();
//        }
    }//GEN-LAST:event_btntiemKiemActionPerformed

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

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
       this.setVisible(false);
       ThanhToanPanel t = new ThanhToanPanel();
       t.setVisible(true);
    }//GEN-LAST:event_jButton1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btntiemKiem;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JButton txtLamMoi;
    private javax.swing.JTextField txtmakh;
    private javax.swing.JTextField txtnguoitao;
    private javax.swing.JTextField txtsdt;
    private javax.swing.JTextField txttenkh;
    private javax.swing.JTextField txttimkiem;
    private javax.swing.JTextField txttongdiem;
    // End of variables declaration//GEN-END:variables

}

package qlch.view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import qlch.dao.NhanVienDAO;
import qlch.model.NhanVien;

/**
 *
 * @author PC
 */
public class NhanVienPanel extends javax.swing.JPanel {

    public NhanVienPanel() {
        initComponents();
//        loaddatajtable();
        fillTable();
    }

    String strHinh = null;
    String head[] = {"Mã Nhân Viên", "Tên Nhân Viên", "Thâm Niên", "SDT", "Mã Chức Vụ", "Hình Ảnh"};
    DefaultTableModel model = new DefaultTableModel(head, 0);

    String user = "teamsix";
    String pass = "win123";
    String url = "jdbc:sqlserver://localhost:1433;databaseName=duan1;encrypt=true;trustServerCertificate=true";

    public void loaddatajtable() {
        try {

            model.setRowCount(0);
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement st = con.createStatement();
            String sql = "select * from NHANVIEN";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(rs.getString(3));
                row.add(rs.getString(4));
                row.add(rs.getString(5));
                row.add(rs.getString(6));
                model.addRow(row);
            }
            tblNhanVien.setModel(model);
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    NhanVienDAO dao = new NhanVienDAO();
    int row = 0;

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = dao.selectAll();
            for (NhanVien kh : list) {
                Object[] row = {
                    kh.getMaNV(),
                    kh.getTenNV(),
                    kh.getThamNien(),
                    kh.getSDT(),
                    kh.getMaCV(),
                    kh.getHinhAnh()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void Search() {
        try {
            //LoadDataToTable();
            //String user = "teamsix";
            //String pass = "win123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //String url = "jdbc:sqlserver://localhost:1433;databaseName=DUAN1;encrypt=false;";
            Connection con = DriverManager.getConnection(url, user, pass);

            String sql = "select * from NHANVIEN where MANV like ?";
            PreparedStatement st = con.prepareStatement(sql);

            String searchCriteria = "%";
            if (!txtTiemKiem.getText().equals("")) {
                searchCriteria += txtTiemKiem.getText() + "%";
            }

            st.setString(1, searchCriteria);
            ResultSet rs = st.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("MANV"),
                    rs.getString("TENNV"),
                    rs.getString("THAMNIEN"),
                    rs.getString("SDT"),
                    rs.getString("MACV"),
                    rs.getString("HinhAnh"),});
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setForm(NhanVien NV) {
        txtMaNV.setText(NV.getMaNV());
        txtTenNV.setText(NV.getTenNV());
        txtThamNien.setText(NV.getThamNien());
        txtSDT.setText(NV.getSDT());
        txtMaCV.setText(NV.getMaCV());
        //lblimg.setText(NV.getHinhAnh());
        if (tblNhanVien.getValueAt(row, 5).toString().equals("") || tblNhanVien.getValueAt(row, 5).toString().equals("Không có hình")) {
            lblimg.setText("Không có hình");
            lblimg.setIcon(null);
        } else {
            lblimg.setText("");
            File file = new File("src\\qlch\\images\\" + tblNhanVien.getValueAt(row, 5).toString());
            Image img;
            try {
                img = ImageIO.read(file);
                lblimg.setIcon(new ImageIcon(img.getScaledInstance(lblimg.getWidth(), lblimg.getHeight(), 0)));
            } catch (IOException ex) {
                Logger.getLogger(NhanVienPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    NhanVien getForm() {
        NhanVien NV = new NhanVien();
        NV.setMaNV(txtMaNV.getText());
        NV.setTenNV(txtTenNV.getText());
        NV.setThamNien(txtThamNien.getText());
        NV.setSDT(txtSDT.getText());
        NV.setMaCV(txtMaCV.getText());
        //NV.setHinhAnh(lblimg.getToolTipText());
        if (strHinh == null) {
            NV.setHinhAnh("Không có hình");
        } else {
            NV.setHinhAnh(strHinh);
        }
        return NV;
    }

    public void insert() {
        NhanVien NV = getForm();
        try {
            dao.insert(NV);
            this.fillTable();
            //this.clearForm();
            JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm mới thất bại!");
        }

    }

    public void update() {
        NhanVien NV = getForm();
        try {
            dao.update(NV);
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
            String manv = txtMaNV.getText();
            try {
                dao.delete(manv);
                this.fillTable();
                reset();
                JOptionPane.showMessageDialog(this, "xoa thanh cong!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "xoa that bai!");
            }
        }
//        }
    }

    void edit() {
        try {
            String MANV = (String) tblNhanVien.getValueAt(this.row, 0);
            NhanVien nv = dao.selectById(MANV);
            if (nv != null) {
                setForm(nv);
                //updateStatus();
                // tabs.setSelectedIndex(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void first() {
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
        if(this.row < tblNhanVien.getRowCount()-1){
            this.row++;
            this.edit();
        }
        
    }
    
    void last(){
        this.row = tblNhanVien.getRowCount()-1;
        this.edit();
    }

    public void reset() {
        txtMaNV.setText("");
        txtTenNV.setText("");
        txtThamNien.setText("");
        txtSDT.setText("");
        txtMaCV.setText("");
        lblimg.setText("Hình Ảnh");
        lblimg.setIcon(null);
    }

    public void HienThi() {
        int row = tblNhanVien.getSelectedRow();
        //System.out.println("'" + tblNhanVien.getValueAt(row, 5).toString() + "'");
        txtMaNV.setText(tblNhanVien.getValueAt(row, 0).toString());
        txtTenNV.setText(tblNhanVien.getValueAt(row, 1).toString());
        txtThamNien.setText(tblNhanVien.getValueAt(row, 2).toString());
        txtSDT.setText(tblNhanVien.getValueAt(row, 3).toString());
        txtMaCV.setText(tblNhanVien.getValueAt(row, 4).toString());
        if (tblNhanVien.getValueAt(row, 5).toString().equals("") || tblNhanVien.getValueAt(row, 5).toString().equals("Không có hình")) {
            lblimg.setText("Không có hình");
            lblimg.setIcon(null);
        } else {
            lblimg.setText("");
            File file = new File("src\\qlch\\images\\" + tblNhanVien.getValueAt(row, 5).toString());
            Image img;
            try {
                img = ImageIO.read(file);
                lblimg.setIcon(new ImageIcon(img.getScaledInstance(lblimg.getWidth(), lblimg.getHeight(), 0)));
            } catch (IOException ex) {
                Logger.getLogger(NhanVienPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTiemKiem = new javax.swing.JTextField();
        txtThamNien = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtMaCV = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JTextField();
        txtTenNV = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btntiemKiem = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblimg = new javax.swing.JLabel();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        txtLamMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(900, 480));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel2.setText("Quản Lý Nhân Viên");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, 740, 110));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel1.setText("Tên Nhân Viên   :");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 120, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel3.setText("Thâm Niên          :");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 120, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel4.setText("Tìm Kiếm :");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 400, 70, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel5.setText("Mã Chức Vụ      :");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 120, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel6.setText("Mã Nhân Viên    :");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 120, -1));

        txtTiemKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTiemKiemActionPerformed(evt);
            }
        });
        add(txtTiemKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 400, 190, 25));
        add(txtThamNien, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, 360, 25));

        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });
        add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, 360, 25));

        txtMaCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaCVActionPerformed(evt);
            }
        });
        add(txtMaCV, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 240, 360, 25));
        add(txtMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 360, 25));

        txtTenNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenNVActionPerformed(evt);
            }
        });
        add(txtTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 360, 25));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel7.setText("SDT                     :");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 120, -1));

        btntiemKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Search.png"))); // NOI18N
        btntiemKiem.setText("Tìm Kiếm");
        btntiemKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntiemKiemActionPerformed(evt);
            }
        });
        add(btntiemKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 400, -1, 25));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblimg.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblimg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblimg.setText("Hình Ảnh");
        lblimg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblimgMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblimg, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblimg, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 80, 170, 180));

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 400, -1, -1));

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 400, -1, -1));

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 400, -1, -1));

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 400, -1, -1));

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Edit.png"))); // NOI18N
        btnSua.setText("Sữa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 440, 100, 30));

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 440, 100, 30));

        txtLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Refresh.png"))); // NOI18N
        txtLamMoi.setText("Làm Mới");
        txtLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLamMoiActionPerformed(evt);
            }
        });
        add(txtLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 440, -1, 30));

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Create.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 440, 100, 30));
    }// </editor-fold>//GEN-END:initComponents

    private void txtTiemKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTiemKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTiemKiemActionPerformed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    private void txtMaCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaCVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaCVActionPerformed

    private void txtTenNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenNVActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
//        try {
//                int out = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm nhân viên này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
//                if (out == JOptionPane.YES_OPTION) {
//                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                    Connection con = DriverManager.getConnection(url, user, pass);
//                    String sql = "INSERT INTO NHANVIEN VALUES (?,?,?,?,?,?)";
//                    PreparedStatement st = con.prepareStatement(sql);
//                    st.setString(1, txtMaNV.getText());
//                    st.setString(2, txtTenNV.getText());
//                    st.setString(3, txtThamNien.getText());
//                    st.setString(4, txtSDT.getText());
//                    st.setString(5, txtMaCV.getText());
//                    if (strHinh == null) {
//                        st.setString(6, "Không có hình");
//                    } else {
//                        st.setString(6, strHinh);
//                    }
//                    st.executeUpdate();
//                    JOptionPane.showMessageDialog(this, "Thêm thành công !!!");
//                    con.close();
//                    loaddatajtable();
//                    //reset();
//                }
//            } catch (Exception e) {
//                System.out.println(e);
//            }
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void lblimgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblimgMouseClicked
        try {
            JFileChooser hinh = new JFileChooser("src\\qlch\\images");
            hinh.showOpenDialog(null);
            File file = hinh.getSelectedFile();
            Image img = ImageIO.read(file);
            strHinh = file.getName();
            lblimg.setText("");
            int width = lblimg.getWidth();
            int height = lblimg.getHeight();
            lblimg.setIcon(new ImageIcon(img.getScaledInstance(width, height, 0)));
        } catch (IOException ex) {
            System.out.println("Lỗi:" + ex.toString());
        }
    }//GEN-LAST:event_lblimgMouseClicked

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        HienThi();
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void txtLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLamMoiActionPerformed
        reset();
    }//GEN-LAST:event_txtLamMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
//        try {
//            int out = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn Xóa nhân viên này không ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
//            if (out == JOptionPane.YES_OPTION) {
//                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                Connection con = DriverManager.getConnection(url, user, pass);
//                String sql = "delete from NHANVIEN where MANV=?";
//                PreparedStatement st = con.prepareStatement(sql);
//                st.setString(1, txtMaNV.getText());
//                st.executeUpdate();
//                con.close();
//                loaddatajtable();
//                reset();
//            } else {
//                JOptionPane.showMessageDialog(this, "Hủy chỉnh sửa");
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
//        try {
//            int out = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn chỉnh sửa nhân viên này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
//            if (out == JOptionPane.YES_OPTION) {
//                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                Connection con = DriverManager.getConnection(url, user, pass);
//                String sql = "UPDATE NHANVIEN set TENNV = ?, THAMNIEN = ?, SDT = ?, MACV = ?, HINHANH = ? WHERE MANV = ?";
//                PreparedStatement st = con.prepareStatement(sql);
//                st.setString(1, txtTenNV.getText());
//                st.setString(2, txtThamNien.getText());
//                st.setString(3, txtSDT.getText());
//                st.setString(4, txtMaCV.getText());
//                if (strHinh == null) {
//                    st.setString(5, "");
//                } else {
//                    st.setString(5, strHinh);
//                }
//                st.setString(6, txtMaNV.getText());
//                st.executeUpdate();
//                JOptionPane.showMessageDialog(this, "Cập Nhật thành công !!!");
//                con.close();
//                loaddatajtable();
//                //reset();
//            } else {
//                JOptionPane.showMessageDialog(this, "Hủy chỉnh sửa");
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
update();

    }//GEN-LAST:event_btnSuaActionPerformed

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
//                txtMaNV.setText(rs.getString(1));
//                txtTenNV.setText(rs.getString(2));
//                txtThamNien.setText(rs.getString(3));
//                txtSDT.setText(rs.getString(4));
//                txtMaCV.setText(rs.getString(5));
////                lblimg.setText("");
////                ImageIcon img = new ImageIcon(getClass().getResource("/HinhAnh/" + rs.getString(8)));
////                Image im = img.getImage();
////                im.getScaledInstance(lblimg.getWidth(), lblimg.getHeight(), 0);
////                lblimg.setIcon(img);
//                //tx.setText(rs.getString(6));
//                //txtBoNho.setText(rs.getString(7));
//
//            }
//            con.close();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Không tìm thấy Nhân Viên có mã : " + txtTiemKiem.getText());
//            reset();
//        }
        Search();
    }//GEN-LAST:event_btntiemKiemActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btntiemKiem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblimg;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JButton txtLamMoi;
    private javax.swing.JTextField txtMaCV;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtThamNien;
    private javax.swing.JTextField txtTiemKiem;
    // End of variables declaration//GEN-END:variables
}

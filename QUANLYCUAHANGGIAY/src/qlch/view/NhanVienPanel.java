package qlch.view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import qlch.connection.Validation;
import qlch.dao.NhanVienDAO;
import qlch.model.NhanVien;

/**
 *
 * @author PC
 */
public class NhanVienPanel extends javax.swing.JPanel {

    public NhanVienPanel() {
        initComponents();
        loaddatajtable();
//        fillTable();
    }

    String strHinh = null;
    String head[] = {"Mã Nhân Viên", "Tên Nhân Viên", "Thâm Niên", "SDT", "Mã Chức Vụ", "Tên Chức Vụ", "Hình Ảnh", "Tài Khoản", "Mật Khẩu"};
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
                row.add(rs.getString(7));
                row.add(rs.getString(8));
                row.add(rs.getString(9));
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
    List<NhanVien> list = new ArrayList<>();

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = dao.selectAll();
            for (NhanVien NV : list) {
                Object[] row = {
                    NV.getMaNV(),
                    NV.getTenNV(),
                    NV.getThamNien(),
                    NV.getSDT(),
                    NV.getMaCV(),
                    NV.getCongViec(),
                    NV.getHinhAnh(),
                    NV.getTaiKhoan(),
                    NV.getMatKhau()
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
            if (!txtTimKiem.getText().equals("")) {
                searchCriteria += txtTimKiem.getText() + "%";
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
                    rs.getString("CONGVIEC"),
                    rs.getString("HinhAnh"),
                    rs.getString("TAIKHOAN"),
                    rs.getString("MATKHAU"),});
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
        if (tblNhanVien.getValueAt(row, 5).toString().equals("Nhân Viên")) {
            rdoBanHang.setSelected(true);
            rdoQuanLy.setSelected(false);
            rdoThuNgan.setSelected(false);
        }
        if (tblNhanVien.getValueAt(row, 5).toString().equals("Thu Ngân")) {
            rdoBanHang.setSelected(false);
            rdoQuanLy.setSelected(false);
            rdoThuNgan.setSelected(true);
        }
        if (tblNhanVien.getValueAt(row, 5).toString().equals("Quản Lý")) {
            rdoQuanLy.setSelected(true);
            rdoThuNgan.setSelected(false);
            rdoBanHang.setSelected(false);
        }
        if (tblNhanVien.getValueAt(row, 6).toString().equals("") || tblNhanVien.getValueAt(row, 6).toString().equals("Không có hình")) {
            lblImg.setText("Không có hình");
            lblImg.setIcon(null);
        } else {
            lblImg.setText("");
            File file = new File("src\\qlch\\images\\" + tblNhanVien.getValueAt(row, 6).toString());
            Image img;
            try {
                img = ImageIO.read(file);
                lblImg.setIcon(new ImageIcon(img.getScaledInstance(lblImg.getWidth(), lblImg.getHeight(), 0)));
            } catch (IOException ex) {
                Logger.getLogger(NhanVienPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        txtTaiKhoan.setText(NV.getTaiKhoan());
        txtMatKhau.setText(NV.getMatKhau());

    }

    NhanVien getForm() {
        NhanVien NV = new NhanVien();
        NV.setMaNV(txtMaNV.getText());
        NV.setTenNV(txtTenNV.getText());
        NV.setThamNien(txtThamNien.getText());
        NV.setSDT(txtSDT.getText());
        NV.setMaCV(txtMaCV.getText());
        //NV.setHinhAnh(lblimg.getToolTipText());
        if (rdoBanHang.isSelected()) {
            NV.setCongViec("Nhân Viên");
        } else if (rdoThuNgan.isSelected()) {
            NV.setCongViec("Thu Ngân");
        } else {
            NV.setCongViec("Quản Lý");
        }
        if (strHinh == null) {
            NV.setHinhAnh("");
        } else {
            NV.setHinhAnh(strHinh);
        }
        NV.setTaiKhoan(txtTaiKhoan.getText());
        NV.setMatKhau(txtMatKhau.getText());
        return NV;
    }

    private boolean validForm() {
        if (!Validation.isEmpty(txtMaNV, "Mã nhân viên không được rỗng!")) {
            return false;
        }
        if (!Validation.isIDNV(txtMaNV, "Mã nhân viên sai định dạng! mã nhân viên có 4 ký tự với 2 kí tự đầu là NV in hoa và là kí tự cuối là số ví dụ NV01")) {
            return false;
        }
        if (!Validation.isEmpty(txtTenNV, "Tên nhân viên không được bỏ rỗng!")) {
            return false;
        }
        if (!Validation.isEmpty(txtThamNien, "Thâm Niên không được rỗng!")) {
            return false;
        }
        if (!Validation.isEmpty(txtSDT, "Số điện thoại không được rỗng!")) {
            return false;
        }
        if (!Validation.isNumber(txtSDT, "Số điện thoại phải là số!")) {
            return false;
        }
        if (!Validation.isPhone(txtSDT, "Số điện thoại sai định dạng! Số điện thoại phải đủ 10 số và số đầu là 0.")) {
            return false;
        }
        if (!Validation.isEmpty(txtMaCV, "Mã công việc không được rỗng!")) {
            return false;
        }

        return true;
    }

    public void insert() {
        NhanVien NV = getForm();
        try {
            dao.insert(NV);
            this.fillTable();
            //this.clearForm();
            reset();
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
            reset();
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
                JOptionPane.showMessageDialog(this, "Xóa Thành Công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Xóa Thất Bại!");
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
        row = 0;
        tblNhanVien.setRowSelectionInterval(row, row);
    }

    void prev() {
        if (this.row > 0) {
            this.row--;
            tblNhanVien.setRowSelectionInterval(row, row);
            this.edit();
        }
    }

    void next() {
        if (this.row < tblNhanVien.getRowCount() - 1) {
            this.row++;
            tblNhanVien.setRowSelectionInterval(row, row);
            this.edit();
            
        }

    }

    void last() {
        this.row = tblNhanVien.getRowCount() - 1;
        tblNhanVien.setRowSelectionInterval(row, row);
        this.edit();
    }

//    void first() {
//        row = 0;
//        tblNhanVien.setRowSelectionInterval(row, row);
//        //this.edit();
//    }
//
//    void prev() {
//        int index = tblNhanVien.getSelectedRow();
//         index--;
//        if (index < 0) {
//           index = list.size() - 1;  
//        }
//        tblNhanVien.setRowSelectionInterval(index, index);
//    }
//
//    void next() {
//       int index = tblNhanVien.getSelectedRow();
//         index++;
//        if (index > list.size() + 1) {
//           index = 0;
//           //this.edit();
//        }
//        tblNhanVien.setRowSelectionInterval(index, index);
//    }
//
//    void last() {
//        this.row = tblNhanVien.getRowCount() - 1;
//        tblNhanVien.setRowSelectionInterval(row, row);
//        //this.edit();
//    }
//    
    public void reset() {
        txtMaNV.setText("");
        txtTenNV.setText("");
        txtThamNien.setText("");
        txtSDT.setText("");
        txtMaCV.setText("");
        rdoBanHang.setSelected(false);
        rdoThuNgan.setSelected(false);
        rdoQuanLy.setSelected(false);
        lblImg.setText("Hình Ảnh");
        lblImg.setIcon(null);
        txtTaiKhoan.setText("");
        txtMatKhau.setText("");
    }

    public void HienThi() {
        int row = tblNhanVien.getSelectedRow();
        //System.out.println("'" + tblNhanVien.getValueAt(row, 5).toString() + "'");
        txtMaNV.setText(tblNhanVien.getValueAt(row, 0).toString());
        txtTenNV.setText(tblNhanVien.getValueAt(row, 1).toString());
        txtThamNien.setText(tblNhanVien.getValueAt(row, 2).toString());
        txtSDT.setText(tblNhanVien.getValueAt(row, 3).toString());
        txtMaCV.setText(tblNhanVien.getValueAt(row, 4).toString());
        if (tblNhanVien.getValueAt(row, 5).toString().equals("Nhân Viên")) {
            rdoBanHang.setSelected(true);
            //rdoQuanLy.setSelected(false);
            //rdoThuNgan.setSelected(false);
        }
        if (tblNhanVien.getValueAt(row, 5).toString().equals("Thu Ngân")) {
            rdoBanHang.setSelected(false);
            rdoQuanLy.setSelected(false);
            rdoThuNgan.setSelected(true);
        }
        if (tblNhanVien.getValueAt(row, 5).toString().equals("Quản Lý")) {
            rdoQuanLy.setSelected(true);
            rdoThuNgan.setSelected(false);
            rdoBanHang.setSelected(false);
        }
        if (tblNhanVien.getValueAt(row, 6).toString().equals("") || tblNhanVien.getValueAt(row, 6).toString().equals("Không có hình")) {
            lblImg.setText("Không có hình");
            lblImg.setIcon(null);
        } else {
            lblImg.setText("");
            File file = new File("src\\qlch\\images\\" + tblNhanVien.getValueAt(row, 6).toString());
            Image img;
            try {
                img = ImageIO.read(file);
                lblImg.setIcon(new ImageIcon(img.getScaledInstance(lblImg.getWidth(), lblImg.getHeight(), 0)));
            } catch (IOException ex) {
                Logger.getLogger(NhanVienPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        txtTaiKhoan.setText(tblNhanVien.getValueAt(row, 7).toString());
        txtMatKhau.setText(tblNhanVien.getValueAt(row, 8).toString());

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
        txtTimKiem = new javax.swing.JTextField();
        txtThamNien = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JTextField();
        txtTenNV = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btntiemKiem = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblImg = new javax.swing.JLabel();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        txtLamMoi = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JTextField();
        rdoThuNgan = new javax.swing.JRadioButton();
        rdoQuanLy = new javax.swing.JRadioButton();
        txtMaCV = new javax.swing.JTextField();
        txtTaiKhoan = new javax.swing.JTextField();
        rdoBanHang = new javax.swing.JRadioButton();

        setPreferredSize(new java.awt.Dimension(900, 480));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel2.setText("Quản Lý Nhân Viên");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, -1, -1));

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 890, 120));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel1.setText("Tên Nhân Viên   :");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 120, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel3.setText("Thâm Niên          :");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 120, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel4.setText("Tìm Kiếm :");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, 70, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel5.setText("Mật Khẩu       :");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 320, 90, 20));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel6.setText("Mã Nhân Viên    :");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 120, -1));

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 500, 260, 25));
        add(txtThamNien, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 500, 25));

        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });
        add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 500, 25));

        txtMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNVActionPerformed(evt);
            }
        });
        add(txtMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 500, 25));

        txtTenNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenNVActionPerformed(evt);
            }
        });
        add(txtTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 500, 25));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel7.setText("SDT                     :");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 120, -1));

        btntiemKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Search.png"))); // NOI18N
        btntiemKiem.setText("Tìm Kiếm");
        btntiemKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntiemKiemActionPerformed(evt);
            }
        });
        add(btntiemKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 500, -1, 25));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblImg.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImg.setText("Hình Ảnh");
        lblImg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImgMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 90, 190, 200));

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 510, -1, -1));

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 510, -1, -1));

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 510, -1, -1));

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 510, -1, -1));

        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Edit.png"))); // NOI18N
        btnSua.setText("Sữa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 540, 100, 30));

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 540, 100, 30));

        txtLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Refresh.png"))); // NOI18N
        txtLamMoi.setText("Làm Mới");
        txtLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLamMoiActionPerformed(evt);
            }
        });
        add(txtLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 540, -1, 30));

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/Create.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 540, 100, 30));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel8.setText("Mã Chức Vụ      :");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 120, 20));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel9.setText("Công Việc           :");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 120, 20));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel10.setText("Tài Khoản          :");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 120, 20));

        txtMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMatKhauActionPerformed(evt);
            }
        });
        add(txtMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 320, 190, 25));

        rdoThuNgan.setFont(new java.awt.Font("Times New Roman", 3, 16)); // NOI18N
        rdoThuNgan.setText("Thu Ngân");
        add(rdoThuNgan, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 280, -1, -1));

        rdoQuanLy.setFont(new java.awt.Font("Times New Roman", 3, 16)); // NOI18N
        rdoQuanLy.setText("Quản Lý");
        add(rdoQuanLy, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 280, -1, -1));

        txtMaCV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaCVActionPerformed(evt);
            }
        });
        add(txtMaCV, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 500, 25));

        txtTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTaiKhoanActionPerformed(evt);
            }
        });
        add(txtTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 320, 190, 25));

        rdoBanHang.setFont(new java.awt.Font("Times New Roman", 3, 16)); // NOI18N
        rdoBanHang.setText("Nhân Viên Bán Hàng");
        add(rdoBanHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 280, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

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
        //insert();
        if (validForm()) {
            int check = 0;
            List<NhanVien> list = dao.selectAll();
            for (NhanVien NV : list) {
                if (NV.getMaNV().equals(txtMaNV.getText())) {
                    JOptionPane.showMessageDialog(this, "Không thể thêm! mã nhân viên đã tồn tại");
                    check = 1;
                    //txtMaKH.setBackground(Color.yellow);
                    break;
                }
            }
            if (check == 0) {
                insert();
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void lblImgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImgMouseClicked
        try {
            JFileChooser hinh = new JFileChooser("src\\qlch\\images");
            hinh.showOpenDialog(null);
            File file = hinh.getSelectedFile();
            Image img = ImageIO.read(file);
            strHinh = file.getName();
            lblImg.setText("");
            int width = lblImg.getWidth();
            int height = lblImg.getHeight();
            lblImg.setIcon(new ImageIcon(img.getScaledInstance(width, height, 0)));
        } catch (IOException ex) {
            System.out.println("Lỗi:" + ex.toString());
        }
    }//GEN-LAST:event_lblImgMouseClicked

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
        try {
            int out = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn chỉnh sửa nhân viên này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (out == JOptionPane.YES_OPTION) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection con = DriverManager.getConnection(url, user, pass);
                String sql = "UPDATE NHANVIEN set TENNV = ?, THAMNIEN = ?, SDT = ?, MACV = ?, CONGVIEC = ?, HINHANH = ?, TAIKHOAN = ?, MATKHAU = ? WHERE MANV = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, txtTenNV.getText());
                st.setString(2, txtThamNien.getText());
                st.setString(3, txtSDT.getText());
                st.setString(4, txtMaCV.getText());
                if (rdoBanHang.isSelected()) {
                    st.setString(5, "Nhân Viên");
                } else if (rdoThuNgan.isSelected()) {
                    st.setString(5, "Thu Ngân");

                } else {
                    st.setString(5, "Quản Lý");
                }
                if (strHinh == null) {
                    st.setString(6, "");
                } else {
                    st.setString(6, strHinh);
                }
                st.setString(7, txtTaiKhoan.getText());
                st.setString(8, txtMatKhau.getText());
                st.setString(9, txtMaNV.getText());
                st.executeUpdate();
                JOptionPane.showMessageDialog(this, "Cập Nhật thành công !!!");
                con.close();
                loaddatajtable();
                reset();
            } else {
                JOptionPane.showMessageDialog(this, "Hủy chỉnh sửa");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        //       update();

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

    private void txtMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNVActionPerformed

    private void txtMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMatKhauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMatKhauActionPerformed

    private void txtMaCVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaCVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaCVActionPerformed

    private void txtTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTaiKhoanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTaiKhoanActionPerformed


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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImg;
    private javax.swing.JRadioButton rdoBanHang;
    private javax.swing.JRadioButton rdoQuanLy;
    private javax.swing.JRadioButton rdoThuNgan;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JButton txtLamMoi;
    private javax.swing.JTextField txtMaCV;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMatKhau;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTaiKhoan;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtThamNien;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

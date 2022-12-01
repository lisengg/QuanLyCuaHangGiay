package qlch.view;

import java.awt.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author PC
 */
public class Loading extends javax.swing.JFrame {

    /**
     * Creates new form Loading
     */
    public Loading() {
        initComponents();
    }

    public Loading(Color clor) {
        initComponents();
        LoadingBar.setBackground(clor);
        runP();
    }

    public void runP() {
        Loading load = new Loading();
        load.setVisible(true);

        try {
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(5);
                load.LoadingValue.setText(i + "%");
                load.LoadingBar.setBackground(Color.red);
                if (i == 10) {
                    load.lblLoading.setText("Đang tải....");
                }

                if (i == 30) {
                    load.lblLoading.setText("Đang kết nối Database....");
                }

                if (i == 70) {
                    load.lblLoading.setText("Kết nối Database thành công!!!");
                }

                if (i == 90) {
                    load.lblLoading.setText("Đang khởi chạy ứng dụng....");
                }

                load.LoadingBar.setValue(i);

            }
            load.dispose();
            DangNhapFrame d = new DangNhapFrame();
            d.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

        LoadingBackground = new keeptoo.KGradientPanel();
        jLabel2 = new javax.swing.JLabel();
        LoadingBar = new javax.swing.JProgressBar();
        lblLoading = new javax.swing.JLabel();
        LoadingValue = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        LoadingBackground.setkEndColor(new java.awt.Color(100, 232, 222));
        LoadingBackground.setkStartColor(new java.awt.Color(138, 100, 235));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Rekaens Store");

        LoadingBar.setBackground(new java.awt.Color(255, 0, 51));

        lblLoading.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblLoading.setForeground(new java.awt.Color(255, 255, 255));
        lblLoading.setText("Loading...");

        LoadingValue.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        LoadingValue.setForeground(new java.awt.Color(255, 255, 255));
        LoadingValue.setText("0%");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlch/images/icons8_nike_300px.png"))); // NOI18N

        javax.swing.GroupLayout LoadingBackgroundLayout = new javax.swing.GroupLayout(LoadingBackground);
        LoadingBackground.setLayout(LoadingBackgroundLayout);
        LoadingBackgroundLayout.setHorizontalGroup(
            LoadingBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoadingBackgroundLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lblLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LoadingValue)
                .addGap(5, 5, 5))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoadingBackgroundLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(LoadingBar, javax.swing.GroupLayout.PREFERRED_SIZE, 1085, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoadingBackgroundLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(LoadingBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoadingBackgroundLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(309, 309, 309))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoadingBackgroundLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(390, 390, 390))))
        );
        LoadingBackgroundLayout.setVerticalGroup(
            LoadingBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoadingBackgroundLayout.createSequentialGroup()
                .addContainerGap(135, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addGroup(LoadingBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLoading)
                    .addComponent(LoadingValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LoadingBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LoadingBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(LoadingBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Loading.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        Loading load = new Loading();
        //load.setVisible(true);
        /*
        try {
            for(int i=0;i<=100;i++){
                Thread.sleep(50);
                load.LoadingValue.setText(i+"%");
                load.LoadingBar.setBackground(Color.red);
                if(i==10){
                    load.lblLoading.setText("Đang tải....");
                }
                
                if(i==30){
                    load.lblLoading.setText("Đang kết nối Database....");
                }
                
                 if(i==70){
                    load.lblLoading.setText("Kết nối Database thành công!!!");
                }
                 
                  if(i==90){
                    load.lblLoading.setText("Đang khởi chạy ứng dụng....");
                }
                
                  load.LoadingBar.setValue(i);
                 
            }
             load.dispose();
            DangNhapFrame d = new DangNhapFrame();
            d.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
         */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private keeptoo.KGradientPanel LoadingBackground;
    private javax.swing.JProgressBar LoadingBar;
    private javax.swing.JLabel LoadingValue;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblLoading;
    // End of variables declaration//GEN-END:variables
}

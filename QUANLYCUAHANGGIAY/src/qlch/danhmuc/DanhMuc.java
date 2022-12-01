
package qlch.danhmuc;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DanhMuc {
    private String kind;
    private JPanel jpn;
    private JLabel lbl;

    public DanhMuc() {
    }

    public DanhMuc(String kind, JPanel jpn, JLabel lbl) {
        this.kind = kind;
        this.jpn = jpn;
        this.lbl = lbl;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public JPanel getJpn() {
        return jpn;
    }

    public void setJpn(JPanel jpn) {
        this.jpn = jpn;
    }

    public JLabel getLbl() {
        return lbl;
    }

    public void setLbl(JLabel lbl) {
        this.lbl = lbl;
    }
    
    
}

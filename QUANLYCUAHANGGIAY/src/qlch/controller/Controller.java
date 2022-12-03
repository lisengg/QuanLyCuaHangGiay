package qlch.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import qlch.danhmuc.DanhMuc;
import qlch.view.BanHangPanel;
import qlch.view.HoaDonPanel;
import qlch.view.KhachHangPanel;
import qlch.view.NhanVienPanel;
import qlch.view.SanPhamPanel;
import qlch.view.ThanhToanPanel;
import qlch.view.ThongKePanel;
import qlch.view.TrangChuPanel;

public class Controller {

    private String kindSelected = "";
    private JPanel root;
    private List<DanhMuc> listItem = null;

    public Controller(JPanel root) {
        this.root = root;
    }

    public void setView(JPanel jpnItem, JLabel lblItem) {
        kindSelected = "TrangChu";
        jpnItem.setBackground(new Color(170, 214, 255));
        lblItem.setBackground(new Color(170, 214, 255));

        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new TrangChuPanel());
        root.validate();
        root.repaint();

    }

    public void setEvent(List<DanhMuc> listItem) {
       this.listItem = listItem;
        for (DanhMuc item : listItem) {
            item.getLbl().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getLbl()));
             item.getJpn().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getLbl()));
        }
    }

    class LabelEvent implements MouseListener {

        private JPanel node;

        private String kind;
        private JPanel jpnItem;
        private JLabel lblItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel lblItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.lblItem = lblItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "TrangChu":
                    node = new TrangChuPanel();
                    break;
                case "SanPham":
                    node = new SanPhamPanel();
                    break;
                case "KhachHang":
                    node = new KhachHangPanel();
                    break;
                case "ThongKe":
                    node = new ThongKePanel();
                    break;
                case "BanHang":
                    node = new BanHangPanel();
                    break;
                case "NhanVien":
                    node = new NhanVienPanel();
                    break;
                 case "HoaDon":
                    node = new HoaDonPanel();
                    break;
                default:
                    node = new TrangChuPanel();
                    break;
            }
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
            setChangedBackground(kind);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            jpnItem.setBackground(new Color(170, 214, 255));
            lblItem.setBackground(new Color(170, 214, 255));
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(new Color(224, 224, 224));
            lblItem.setBackground(new Color(224, 224, 224));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(255, 255, 255));
                lblItem.setBackground(new Color(255, 255, 255));
            }
        }

    }

    private void setChangedBackground(String kind) {
        for (DanhMuc item : listItem) {
            if (item.getKind().equalsIgnoreCase(kind)) {
                item.getJpn().setBackground(new Color(170, 214, 255));
                item.getLbl().setBackground(new Color(170, 214, 255));
            } else {
                item.getJpn().setBackground(new Color(255, 255, 255));
                item.getLbl().setBackground(new Color(255, 255, 255));
            }
        }
    }

}

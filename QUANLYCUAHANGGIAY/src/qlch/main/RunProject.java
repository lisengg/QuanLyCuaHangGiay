package qlch.main;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import qlch.view.DangNhapFrame;
import qlch.view.Loading;
import qlch.view.MainJFrame;

public class RunProject {
    
    public static void main(String[] args) {
        //new Loading().setVisible(true);
        Loading load = new Loading();
        load.runP();
    }
    
}

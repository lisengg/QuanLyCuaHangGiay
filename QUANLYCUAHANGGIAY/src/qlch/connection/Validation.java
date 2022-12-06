/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlch.connection;

import java.awt.Color;
import java.beans.Expression;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author 84948
 */
public class Validation {
    public static boolean isEmpty(JTextField txtField,String msg){
        String txtValue = txtField.getText().trim();
        boolean exp = (txtValue.length() == 0);
        return showMessage(txtField, msg, exp);
    }
    public static boolean isNumber(JTextField txtField,String msg){
        String txtValue = txtField.getText().trim();
        String strPtn = "^\\d{1,}$";
        boolean exp = (!txtValue.matches(strPtn));
        return showMessage(txtField, msg, exp); 
    }
     public static boolean isPhone(JTextField txtField,String msg){
        String txtValue = txtField.getText().trim();
        String strPtn = "0\\d{9}";
        boolean exp = (!txtValue.matches(strPtn));
        return showMessage(txtField, msg, exp); 
    }
    public static boolean isID(JTextField txtField,String msg){
        String txtValue = txtField.getText().trim();
        String strPtn = "^[KH]{2}\\d{2}$";
        boolean exp = (!txtValue.matches(strPtn));
        return showMessage(txtField, msg, exp); 
    }
    public static boolean isIDNV(JTextField txtField,String msg){
        String txtValue = txtField.getText().trim();
        String strPtn = "^[NV]{2}\\d{2}$";
        boolean exp = (!txtValue.matches(strPtn));
        return showMessage(txtField, msg, exp); 
    }
    private static boolean showMessage(JTextField txtField,String msg,boolean Expression){
        if (Expression){
            JOptionPane.showMessageDialog(null, msg);
            txtField.setBackground(Color.yellow);
            txtField.requestFocus();
            return false;
        }
        txtField.setBackground(Color.WHITE);
        return true;
    }
}

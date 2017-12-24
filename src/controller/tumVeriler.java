/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.baglanti;

/**
 *
 * @author ozkan
 */
public class tumVeriler {

    private String tarih, port, sarki, kullanici, logMetin, logTarih, logKullanici;

    PreparedStatement ps;
    ResultSet rs;

    public List<tumVeriler> logOku() throws SQLException {
        model.baglanti baglanti = new model.baglanti();
        baglanti.baglan();

        List<tumVeriler> liste = new ArrayList<>();
        tumVeriler[] veriler = null;
        String sorgu;
        sorgu = "SELECT * FROM t_log ORDER BY id desc";
        ps = baglanti.con.prepareStatement(sorgu);
        rs = ps.executeQuery(sorgu);
        int sayac = 0;
        while (rs.next()) {
            sayac++;
        }
        veriler = new tumVeriler[sayac];
        ps = baglanti.con.prepareStatement(sorgu);
        rs = ps.executeQuery(sorgu);
        int i = 0;
        while (rs.next()) {
            veriler[i] = new tumVeriler();
            veriler[i].setLogMetin(rs.getString("metin"));
            veriler[i].setLogTarih(String.valueOf(rs.getDate("tarih")));
            veriler[i].setLogKullanici(rs.getString("kullanici"));
            liste.add(veriler[i]);
            i++;
        }
        baglanti.con.close();
        return liste;
    }

    public boolean logYaz(String metin, String kullanici) throws SQLException {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);
        
        int kulID = kullaniciIDAl(kullanici);
        try {
            model.baglanti baglanti = new model.baglanti();
            baglanti.baglan();
            String sorgu = "INSERT INTO t_log (metin, tarih, kullanici) VALUES('" + metin + "', '" + currentTime + "', " + kulID + ")";
            ps = baglanti.con.prepareStatement(sorgu);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(tumVeriler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public String sarkiOku(int kullanici) throws SQLException {
        model.baglanti baglanti = new model.baglanti();
        baglanti.baglan();

        String sorgu, sarki = "";
        sorgu = "SELECT * FROM t_sarki WHERE kullanici=" + kullanici + "";
        ps = baglanti.con.prepareStatement(sorgu);
        rs = ps.executeQuery(sorgu);
        while (rs.next()) {
            sarki = rs.getString("sarki");
        }
        baglanti.con.close();
        return sarki;

    }

    public boolean sarkiGuncelle(String kullanici, String sarki) {
        model.baglanti baglanti = new model.baglanti();
        baglanti.baglan();
        
        int id = 0;
        id = kullaniciIDAl(kullanici);
        
        String sorgu = "update t_sarki set sarki=? where kullanici=?";
        try {
            ps = baglanti.con.prepareStatement(sorgu);
            ps.setString(1, sarki);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(tumVeriler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
       
    }
    
    public boolean sarkiEkle(String kullanici, String sarki){
        model.baglanti baglanti = new model.baglanti();
        baglanti.baglan();
        
        int id = 0;
        id = kullaniciIDAl(kullanici);
        
        String sorgu = "insert into t_sarki (sarki, kullanici) VALUES (?,?)";
        try {
            ps = baglanti.con.prepareStatement(sorgu);
            ps.setString(1, sarki);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(tumVeriler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean kullaniciKontrol(String kullanici) throws SQLException {
        model.baglanti baglanti = new model.baglanti();
        baglanti.baglan();

        boolean sonuc = false;

        String sorgu = "SELECT COUNT(*) FROM t_kullanici WHERE isim='" + kullanici + "'";
        ps = baglanti.con.prepareStatement(sorgu);
        rs = ps.executeQuery(sorgu);
        while (rs.next()) {
            int a = rs.getInt(1);
            if (a > 0) {
                sonuc = true;
            }
        }
        return sonuc;
    }

    public int kullaniciIDAl(String kullanici) {
        int sonuc = 0;
        try {
            model.baglanti baglanti = new model.baglanti();
            baglanti.baglan();

            String sorgu = "SELECT id FROM t_kullanici WHERE isim='" + kullanici + "'";
            ps = baglanti.con.prepareStatement(sorgu);
            rs = ps.executeQuery(sorgu);
            while (rs.next()) {
                sonuc = rs.getInt(1);

            }

        } catch (SQLException ex) {
            Logger.getLogger(tumVeriler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sonuc;
    }
    
    public String kullaniciAdiAl(int ID) throws SQLException{
        String sonuc = "";
        model.baglanti baglanti = new model.baglanti();
        baglanti.baglan();
        try {
            

            String sorgu = "SELECT isim FROM t_kullanici WHERE id=" + ID + "";
            ps = baglanti.con.prepareStatement(sorgu);
            rs = ps.executeQuery(sorgu);
            while (rs.next()) {
                sonuc = rs.getString("isim");
            
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(tumVeriler.class.getName()).log(Level.SEVERE, null, ex);
        }
        baglanti.con.close();
        return sonuc;
    }
    
    public boolean kullaniciEkle(String kullanici) {
        try {
            model.baglanti baglanti = new model.baglanti();
            baglanti.baglan();
            String sorgu = "INSERT INTO t_kullanici (isim) VALUES('" + kullanici + "')";
            ps = baglanti.con.prepareStatement(sorgu);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(tumVeriler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public String getLogMetin() {
        return logMetin;
    }

    public void setLogMetin(String logMetin) {
        this.logMetin = logMetin;
    }

    public String getLogTarih() {
        return logTarih;
    }

    public void setLogTarih(String logTarih) {
        this.logTarih = logTarih;
    }

    public String getLogKullanici() {
        return logKullanici;
    }

    public void setLogKullanici(String logKullanici) {
        this.logKullanici = logKullanici;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSarki() {
        return sarki;
    }

    public void setSarki(String sarki) {
        this.sarki = sarki;
    }

    public String getKullanici() {
        return kullanici;
    }

    public void setKullanici(String kullanici) {
        this.kullanici = kullanici;
    }

}

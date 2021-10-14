package com.javafx.librarian.controller;

import java.io.File;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.swing.JFrame;

import com.javafx.librarian.dao.DocGiaDao;
import com.javafx.librarian.model.*;
import com.javafx.librarian.service.BaoCaoSachTTService;
import com.javafx.librarian.service.BaoCaoTheoTheLoaiService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;

public class PrintReport extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public void showReport(String path, int thang, int nam, String mabc, int tongluotmuon) throws JRException, ClassNotFoundException, SQLException {

        List<CTBaoCaoTheoTheLoai> ct = BaoCaoTheoTheLoaiService.getInstance().getAllCTBCTheoTL(thang, nam);
        List<ReportMuonTheLoai> rp = new ArrayList<>();
        ct.forEach(e -> {
            ReportMuonTheLoai data = new ReportMuonTheLoai(e.getMaTheLoai(), e.getTenTheLoai(), e.getSoLuotMuon(), e.getTiLe());
            rp.add(data);
        });
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(rp, false);

        // First, compile jrxml file.
        System.out.println(getClass().getResource(path));
        File sourceName = new File("src/com/javafx/librarian/view/report/BCMuonTL.jrxml");
        System.out.println("Absolute path" + sourceName.getAbsolutePath());
        JasperReport jasperReport = JasperCompileManager.compileReport(sourceName.getAbsolutePath());
        // Fields for report
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("thangBC", String.valueOf(thang));
        parameters.put("namBC", String.valueOf(nam));
        parameters.put("tongluotmuon", String.valueOf(tongluotmuon));
        parameters.put("data", beanColDataSource);

        //ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        //list.add(parameters);

        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);
        System.out.print("Done!");

    }

    public void showReport2(String path, java.sql.Date ngay) throws JRException {
        List<CTBaoCaoSTT> ct = BaoCaoSachTTService.getInstance().getAllCTBCSachTT(ngay);
        List<ReportSachTT> rp = new ArrayList<>();
        ct.forEach(e -> {
            ReportSachTT data = new ReportSachTT(e.getMaSach(), e.getTenSach(), e.getNgayMuon(), e.getSoNgayTraTre());
            rp.add(data);
        });
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(rp, false);

        // First, compile jrxml file.
        System.out.println(getClass().getResource(path));
        File sourceName = new File("src/com/javafx/librarian/view/report/BCSachTT.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(sourceName.getAbsolutePath());
        // Fields for report
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("ngayBC", String.valueOf(ngay.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        parameters.put("data", beanColDataSource);

        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);
        System.out.print("Done!");
    }

}

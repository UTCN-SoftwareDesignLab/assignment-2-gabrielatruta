package com.example.assignment2.report;

import com.example.assignment2.book.model.dto.BookDTO;
import com.example.assignment2.book.service.BookService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.assignment2.report.ReportType.*;

@Service
@AllArgsConstructor
public class PdfReportServiceJasper implements ReportService{

    private final BookService bookService;

    private static final String fileName = "Books_Out_Of_Stock";

    public String export() {

        List<BookDTO> books = bookService.booksOutOfStock();

        Map<String, Object> parameter  = new HashMap<>();

        JRBeanCollectionDataSource bookCollectionDataSource =
                new JRBeanCollectionDataSource(books);

        parameter.put("title", ("UNAVAILABLE BOOKS: "));

        JasperReport jasperDesign;
        try {
            jasperDesign = JasperCompileManager.compileReport(getDesign());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperDesign, parameter, bookCollectionDataSource);

            File file = new File(fileName);

            OutputStream outputSteam = new FileOutputStream(file);

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputSteam);
        } catch (JRException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return "Books_Out_Of_Stock_Jasper.pdf";
    }

    @Override
    public ReportType getType() {
        return PDF_JASPER;
    }

    private JasperDesign getDesign() throws JRException {
        JasperDesign jasDes = new JasperDesign();
        jasDes.setName("Jasper_Report");
        jasDes.setPageWidth(600);
        jasDes.setPageHeight(600);
        jasDes.setLeftMargin(13);
        jasDes.setRightMargin(13);
        jasDes.setTopMargin(13);
        jasDes.setBottomMargin(13);
        jasDes.setColumnWidth(555);

        // Style
        JRDesignStyle mystyle = new JRDesignStyle();
        mystyle.setName("zala1_Style");
        mystyle.setDefault(true);
        mystyle.setFontSize(13f);
        mystyle.setPdfFontName("Helvetica");
        mystyle.setPdfEncoding("UTF-8");
        jasDes.addStyle(mystyle);

        // Fields
        JRDesignField field1 = new JRDesignField();
        field1.setName("Title");
        field1.setValueClass(String.class);
        jasDes.addField(field1);

        JRDesignField field2 = new JRDesignField();
        field2.setName("Author");
        field2.setValueClass(String.class);
        jasDes.addField(field2);

        JRDesignField field3 = new JRDesignField();
        field1.setName("Genre");
        field1.setValueClass(String.class);
        jasDes.addField(field3);

        JRDesignField field4 = new JRDesignField();
        field1.setName("Price");
        field1.setValueClass(String.class);
        jasDes.addField(field4);


        // Title
        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(50);

        JRDesignStaticText titleText = new JRDesignStaticText();
        titleText.setText("UNAVAILABLE BOOKS");
        titleText.setX(0);
        titleText.setY(10);
        titleText.setWidth(515);
        titleText.setHeight(30);
        titleText.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        titleText.setFontSize(22f);
        titleBand.addElement(titleText);
        jasDes.setTitle(titleBand);

        // Detail
        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(60);

        JRDesignTextField tf1 = new JRDesignTextField();
        tf1.setBlankWhenNull(true);
        tf1.setX(0);
        tf1.setY(10);
        tf1.setWidth(150);
        tf1.setHeight(30);
        tf1.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        tf1.setStyle(mystyle);
        tf1.setExpression(new JRDesignExpression("$F{Title}"));
        detailBand.addElement(tf1);

        JRDesignTextField tf2 = new JRDesignTextField();
        tf2.setBlankWhenNull(true);
        tf2.setX(150);
        tf2.setY(10);
        tf2.setWidth(150);
        tf2.setHeight(30);
        tf2.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        tf2.setStyle(mystyle);
        tf2.setExpression(new JRDesignExpression("$F{Author}"));
        detailBand.addElement(tf2);

        JRDesignTextField tf3 = new JRDesignTextField();
        tf2.setBlankWhenNull(true);
        tf2.setX(300);
        tf2.setY(10);
        tf2.setWidth(150);
        tf2.setHeight(30);
        tf2.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        tf2.setStyle(mystyle);
        tf2.setExpression(new JRDesignExpression("$F{Genre}"));
        detailBand.addElement(tf3);

        JRDesignTextField tf4 = new JRDesignTextField();
        tf2.setBlankWhenNull(true);
        tf2.setX(450);
        tf2.setY(10);
        tf2.setWidth(150);
        tf2.setHeight(30);
        tf2.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        tf2.setStyle(mystyle);
        tf2.setExpression(new JRDesignExpression("$F{Price}"));
        detailBand.addElement(tf4);

        ((JRDesignSection) jasDes.getDetailSection()).addBand(detailBand);

        return jasDes;
    }
}

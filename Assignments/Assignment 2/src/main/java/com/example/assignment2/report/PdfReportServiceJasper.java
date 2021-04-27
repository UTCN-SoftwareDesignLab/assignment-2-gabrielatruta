package com.example.assignment2.report;

import com.example.assignment2.book.model.dto.BookDTO;
import com.example.assignment2.book.service.BookService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.assignment2.report.ReportType.PDF_JASPER;

@Service
@AllArgsConstructor
public class PdfReportServiceJasper implements ReportService{

    private final BookService bookService;
    private static final String name = "Books_Out_Of_Stock_Jasper.pdf";

    public ByteArrayOutputStream export() {

        List<BookDTO> outOfStock = bookService.booksOutOfStock();

        Map<String, Object> type  = new HashMap<>();

        JRBeanCollectionDataSource bookCollectionDataSource = new JRBeanCollectionDataSource(outOfStock);

        type.put("title", ("UNAVAILABLE BOOKS"));

        JasperReport jasperDesign;
        try {
            jasperDesign = JasperCompileManager.compileReport(getDesign());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperDesign, type, bookCollectionDataSource);

            File file = new File(name);

            OutputStream outputSteam = new FileOutputStream(file);

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputSteam);
        } catch (JRException | FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] buffer = new byte[4096];
        BufferedInputStream bis;
        try {

            bis = new BufferedInputStream(new FileInputStream("Books_Out_Of_Stock_Jasper.pdf"));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            int bytes;
            while ((bytes = bis.read(buffer, 0, buffer.length)) > 0) {
                output.write(buffer, 0, bytes);
            }
            bis.close();
            return  output;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ReportType getType() {
        return PDF_JASPER;
    }

    private JasperDesign getDesign() throws JRException {
        JasperDesign jasperDesign = new JasperDesign();
        jasperDesign.setName("outOfStockBooks");
        jasperDesign.setPageWidth(550);
        jasperDesign.setPageHeight(800);
        jasperDesign.setColumnWidth(150);
        jasperDesign.setLeftMargin(20);
        jasperDesign.setRightMargin(20);
        jasperDesign.setTopMargin(15);
        jasperDesign.setBottomMargin(15);

        JRDesignStyle style = new JRDesignStyle();
        style.setName("Helvetica");
        style.setDefault(true);
        style.setFontSize(10f);
        style.setPdfFontName("Helvetica");
        style.setPdfEncoding("Cp1252");
        style.setPdfEmbedded(false);
        jasperDesign.addStyle(style);

        JRDesignField title = new JRDesignField();
        title.setName("title");
        title.setValueClass(String.class);
        jasperDesign.addField(title);

        JRDesignField author = new JRDesignField();
        author.setName("author");
        author.setValueClass(String.class);
        jasperDesign.addField(author);

        JRDesignField genre = new JRDesignField();
        genre.setName("genre");
        genre.setValueClass(String.class);
        jasperDesign.addField(genre);

        JRDesignField price = new JRDesignField();
        price.setName("price");
        price.setValueClass(Long.class);
        jasperDesign.addField(price);

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
        jasperDesign.setTitle(titleBand);

        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(40);

        JRDesignTextField titleField = new JRDesignTextField();
        titleField.setBlankWhenNull(true);
        titleField.setX(0);
        titleField.setY(10);
        titleField.setWidth(150);
        titleField.setHeight(30);
        titleField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        titleField.setStyle(style);
        titleField.setExpression(new JRDesignExpression("$F{title}"));
        detailBand.addElement(titleField);

        JRDesignTextField authorField = new JRDesignTextField();
        authorField.setBlankWhenNull(true);
        authorField.setX(150);
        authorField.setY(10);
        authorField.setWidth(150);
        authorField.setHeight(30);
        authorField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        authorField.setStyle(style);
        authorField.setExpression(new JRDesignExpression("$F{author}"));
        detailBand.addElement(authorField);

        JRDesignTextField genreField = new JRDesignTextField();
        genreField.setBlankWhenNull(true);
        genreField.setX(300);
        genreField.setY(10);
        genreField.setWidth(150);
        genreField.setHeight(30);
        genreField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        genreField.setStyle(style);
        genreField.setExpression(new JRDesignExpression("$F{genre}"));
        detailBand.addElement(genreField);

        JRDesignTextField priceField = new JRDesignTextField();
        priceField.setBlankWhenNull(true);
        priceField.setX(450);
        priceField.setY(10);
        priceField.setWidth(150);
        priceField.setHeight(30);
        priceField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        priceField.setStyle(style);
        priceField.setExpression(new JRDesignExpression("$F{price}"));
        detailBand.addElement(priceField);

        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(detailBand);

        return jasperDesign;
    }
}

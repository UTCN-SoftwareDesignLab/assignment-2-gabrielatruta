package com.example.assignment2.report;

import com.example.assignment2.book.model.dto.BookDTO;
import com.example.assignment2.book.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.assignment2.report.ReportType.CSV;

@Service
@AllArgsConstructor
public class CsvReportService implements ReportService{
    private final BookService bookService;

    @Override
    public ByteArrayOutputStream export() {
        List<BookDTO> books = new ArrayList<>(bookService.booksOutOfStock());

        try{
            FileWriter fileWriter = new FileWriter("Books_Out_Of_Stock.csv");
            for(BookDTO book: books){
                fileWriter.append(book.getId().toString());
                fileWriter.append(",");
                fileWriter.append(book.getTitle());
                fileWriter.append(",");
                fileWriter.append(book.getAuthor());
                fileWriter.append(",");
                fileWriter.append(book.getGenre());
                fileWriter.append(",");
                fileWriter.append(book.getPrice().toString());
                fileWriter.append("\n");
            }
            fileWriter.flush();
        }catch(IOException e){
            e.printStackTrace();
        }


        byte[] buffer = new byte[4096];
        BufferedInputStream bis;
        try {

            bis = new BufferedInputStream(new FileInputStream("Books_Out_Of_Stock.csv"));
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
        return CSV;
    }

}

package com.example.assignment2.report;

import com.example.assignment2.book.model.dto.BookDTO;
import com.example.assignment2.book.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.assignment2.report.ReportType.CSV;

@Service
@AllArgsConstructor
public class CsvReportService implements ReportService{
    private final BookService bookService;

    @Override
    public String export() {
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
            return "Error generating CSV file!";
        }
        return "Books_Out_Of_Stock.csv";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}

package com.vitortenorio.descomplicando.core.factory;

import com.opencsv.CSVWriter;
import com.vitortenorio.descomplicando.core.util.ObjectMapperUtil;
import com.vitortenorio.descomplicando.infra.data.model.SingleQuestionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvFactory {
    @Value("${file.path.answer}")
    private String PATH_ANSWER;

    private static final String CSV_EXTENSION = ".csv";
    private final ObjectMapperUtil objectMapperUtil;
    private final FileFactory fileFactory;


    public void createAndSaveSingleFile(List<SingleQuestionModel> list, String fileName) {
        fileFactory.validateAndCreateDirectory(PATH_ANSWER);

        try (Writer writer = new FileWriter(PATH_ANSWER + fileName + CSV_EXTENSION, StandardCharsets.UTF_8);
             CSVWriter csvWriter = new CSVWriter(writer, ';', CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {

             String[] headers = {"lesson", "question", "answer"};
            csvWriter.writeNext(headers);

            for (SingleQuestionModel answer : list) {
                String[] data = {
                        "\"" + answer.lesson() + "\"",
                        "\"" + answer.question() + "\"",
                        "\"" + answer.answer() + "\""
                };
                csvWriter.writeNext(data);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

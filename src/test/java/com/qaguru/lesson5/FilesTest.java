package com.qaguru.lesson5;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.qaguru.lesson5.services.model.Samples;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class FilesTest {

    private ClassLoader classLoader = FilesTest.class.getClassLoader();

    @BeforeAll
    static void beforeAll() {
    }

    @Test
    void xlsExtractArchiveTest() throws Exception {

        try (InputStream resource = classLoader.getResourceAsStream("sample.zip");
             ZipInputStream zipInputStream = new ZipInputStream(resource)) {

            ZipEntry zipEntry;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {

                log.info(zipEntry.getName());

                try (InputStream inputStream = new ZipFile(new File(classLoader.getResource("sample.zip").getPath()))
                        .getInputStream(zipEntry)) {

                    if (zipEntry.getName().endsWith(".xlsx")) {
                        XLS fileXls = new XLS(inputStream);
                        assertThat(fileXls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue()).isEqualTo("Dulce");
                    }

                }

            }
        }
    }

    @Test
    void pdfExtractArchiveTest() throws Exception {

        try (InputStream resource = classLoader.getResourceAsStream("sample.zip");
             ZipInputStream zipInputStream = new ZipInputStream(resource)) {

            ZipEntry zipEntry;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {

                log.info(zipEntry.getName());

                try (InputStream inputStream = new ZipFile(new File(classLoader.getResource("sample.zip").getPath()))
                        .getInputStream(zipEntry)) {

                    if (zipEntry.getName().endsWith(".pdf")) {
                        PDF filePdf = new PDF(inputStream);
                        assertThat(filePdf.text).containsIgnoringCase("Если во время или после использования модели у вас возникнут вопросы или проблемы");
                    }
                }

            }
        }
    }

    @Test
    void csvExtractArchiveTest() throws Exception {

        try (InputStream resource = classLoader.getResourceAsStream("sample.zip");
             ZipInputStream zipInputStream = new ZipInputStream(resource)) {

            ZipEntry zipEntry;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {

                log.info(zipEntry.getName());

                try (InputStream inputStream = new ZipFile(new File(classLoader.getResource("sample.zip").getPath()))
                        .getInputStream(zipEntry)) {

                    if (zipEntry.getName().endsWith(".csv")) {
                        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                        List<String[]> stringsListCsv = csvReader.readAll();
                        String[] expectedRow = new String[]{

                                "2021",
                                "Level 1",
                                "99999",
                                "All industries",
                                "Dollars (millions)",
                                "H01",
                                "Total income",
                                "Financial performance",
                                "757,504",
                                "ANZSIC06 divisions A-S (excluding classes K6330, L6711, O7552, O760, O771, O772, S9540, S9601, S9602, and S9603)"

                        };
                        assertThat(stringsListCsv).contains(expectedRow);
                    }
                }
            }
        }
    }

    @Test
    void jsonParseTest() throws Exception {

        File fileJson = new File(classLoader.getResource("sampleJSON.json").getPath());

        Samples samples = new ObjectMapper().readValue(fileJson, Samples.class);

        assertThat(samples.getSamples().get(1).getId()).isEqualTo("0002");
        assertThat(samples.getSamples().get(0).getBatters().getBatter().get(2).getType()).isEqualTo("Blueberry");
        assertThat(samples.getSamples().get(0).getTopping().get(1).getId()).isEqualTo("5002");

    }
}


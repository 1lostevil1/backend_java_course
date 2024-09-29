package edu.project3;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Все классы проверены отдельно, надо лишь проверить, что параметры считываются
 */
public class NginxLogsStatsTest {
    private final static String LOG = "75.98.236.143 - - [15/Nov/2023:21:34:33 +0000] " +
        "\"GET /Up-sized-dynamic-workforce/hybrid/monitoring.hmtl HTTP/1.1\" 200 1129 \"-\" " +
        "\"Mozilla/5.0 (Macintosh; PPC Mac OS X 10_7_8) AppleWebKit/5330 (KHTML, like Gecko)" +
        " Chrome/37.0.880.0 Mobile Safari/5330\"";
    /** На одну секунду меньше */
    private final static String LOG_BEFORE = "75.98.236.143 - - [15/Nov/2023:21:34:32 +0000] " +
        "\"GET /Up-sized-dynamic-workforce/hybrid/monitoring.hmtl HTTP/1.1\" 200 1129 \"-\" " +
        "\"Mozilla/5.0 (Macintosh; PPC Mac OS X 10_7_8) AppleWebKit/5330 (KHTML, like Gecko)" +
        " Chrome/37.0.880.0 Mobile Safari/5330\"";
    /** На одну секунду больше */
    private final static String LOG_AFTER = "75.98.236.143 - - [15/Nov/2023:21:34:34 +0000] " +
        "\"GET /Up-sized-dynamic-workforce/hybrid/monitoring.hmtl HTTP/1.1\" 200 1129 \"-\" " +
        "\"Mozilla/5.0 (Macintosh; PPC Mac OS X 10_7_8) AppleWebKit/5330 (KHTML, like Gecko)" +
        " Chrome/37.0.880.0 Mobile Safari/5330\"";
    private final static String MARKDOWN_URL = "### Общая информация\n" +
        "\n" +
        "|        Метрика         |           Значение           |\n" +
        "|:----------------------:|:----------------------------:|\n" +
        "|        Файл(ы)         | [http://localhost:8080/logs] |\n" +
        "| Начальные дата и время |     15/11/2023 21:34:33      |\n" +
        "| Конечные дата и время  |     15/11/2023 21:34:33      |\n" +
        "|  Количество запросов   |              1               |\n" +
        "| Средний размер ответа  |             1129             |\n" +
        "\n" +
        "### Запрашиваемые ресурсы\n" +
        "\n" +
        "|                       Ресурс                       | Количество |\n" +
        "|:--------------------------------------------------:|:----------:|\n" +
        "| /Up-sized-dynamic-workforce/hybrid/monitoring.hmtl |     1      |\n" +
        "\n" +
        "### Коды ответа\n" +
        "\n" +
        "| Статус | Имя | Количество |\n" +
        "|:------:|:---:|:----------:|\n" +
        "|  200   | OK  |     1      |\n" +
        "\n";
    private final static String MARKDOWN_FILE = "### Общая информация\n" +
        "\n" +
        "|        Метрика         |      Значение       |\n" +
        "|:----------------------:|:-------------------:|\n" +
        "|        Файл(ы)         |       [file]        |\n" +
        "| Начальные дата и время | 15/11/2023 21:34:33 |\n" +
        "| Конечные дата и время  | 15/11/2023 21:34:33 |\n" +
        "|  Количество запросов   |          1          |\n" +
        "| Средний размер ответа  |        1129         |\n" +
        "\n" +
        "### Запрашиваемые ресурсы\n" +
        "\n" +
        "|                       Ресурс                       | Количество |\n" +
        "|:--------------------------------------------------:|:----------:|\n" +
        "| /Up-sized-dynamic-workforce/hybrid/monitoring.hmtl |     1      |\n" +
        "\n" +
        "### Коды ответа\n" +
        "\n" +
        "| Статус | Имя | Количество |\n" +
        "|:------:|:---:|:----------:|\n" +
        "|  200   | OK  |     1      |\n" +
        "\n";

    private final static String DIRECTORY_PATH = "test_nginx";
    private WireMockServer wireMockServer;

    @BeforeEach
    void createDirectory() throws Exception {
        deleteDirectory(new File(DIRECTORY_PATH));
        Files.createDirectory(Path.of(DIRECTORY_PATH));
    }

    @AfterEach
    void deleteAllFiles() throws Exception {
        deleteDirectory(new File(DIRECTORY_PATH));
    }

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    public static void deleteDirectory(File directory) throws Exception {
        if(!directory.exists()) {
            return;
        }

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }

        // Удаляем саму директорию
        if (!directory.delete()) {
            throw new Exception("Не удалось удалить файл");
        }
    }

    @Test
    void url() throws IOException {
        //given
        stubFor(get(urlEqualTo("/logs"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody(LOG_BEFORE + "\n" + LOG + "\n" + LOG_AFTER)));
        //when
        String answer = NginxLogsStats.nginxLogsStats(List.of(
            "--path", wireMockServer.url("/logs"),
            "--from", LocalDateTime.of(2023, 11, 15, 21, 34, 33).toString(),
            "--to", LocalDateTime.of(2023, 11, 15, 21, 34, 33).toString(),
            "--format", "markdown"
        ).toArray(new String[0]));
        //then
        assertThat(answer.equals(MARKDOWN_URL));
    }

    @Test
    void file() throws IOException {
        //given
        Path path = Path.of(DIRECTORY_PATH, "file");
        Files.createFile(path);
        Files.writeString(path, LOG);
        //when
        String answer = NginxLogsStats.nginxLogsStats(List.of(
            "--path", path.toString(),
            "--from", LocalDateTime.of(2023, 11, 15, 21, 34, 33).toString(),
            "--to", LocalDateTime.of(2023, 11, 15, 21, 34, 33).toString(),
            "--format", "markdown"
        ).toArray(new String[0]));
        //then
        assertThat(answer.equals(MARKDOWN_FILE));
    }

    @Test
    void withoutPath() {
        //expect
        assertThrows(
            IllegalArgumentException.class,
            () -> NginxLogsStats.nginxLogsStats(new String[] {})
        );
    }
}

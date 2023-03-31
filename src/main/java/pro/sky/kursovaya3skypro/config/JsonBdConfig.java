package pro.sky.kursovaya3skypro.config;

import io.jsondb.JsonDBTemplate;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Конфигурация для работы с библиотекой jsonDb
 */
@Configuration
public class JsonBdConfig {
    /**
     * Путь сохранения json
     */
    private String dbPath;

    /**
     * Название папки для сохранения json
     */
    @Value("${sources.directory:}")
    private String directory;

    /**
     * Создание бина в который передается путь сохранения json и пакет, где находятся объекты для записи в json
     * @return {@link JsonDBTemplate}
     */
    @Bean
    public JsonDBTemplate jsonDBTemplate() {
       return new JsonDBTemplate(dbPath,this.getClass().getPackageName().replace("config", "model"));
    }

    /**
     * Создание папки куда будут сохраняться json
     */
    @PostConstruct
    public void createDirectory() {
        Path workingDir = Paths.get("").toAbsolutePath();
        Path checkingDir = Paths.get(workingDir+"/"+directory);
        if (!Files.exists(checkingDir)) {
            try {
                Files.createDirectories(checkingDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        dbPath = checkingDir.toString();
    }
}

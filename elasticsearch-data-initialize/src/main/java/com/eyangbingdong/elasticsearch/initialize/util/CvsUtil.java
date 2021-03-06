package com.eyangbingdong.elasticsearch.initialize.util;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.Reader;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.ClassLoader.getSystemResource;
import static java.nio.file.Files.newBufferedReader;

/**
 * @author <a href="mailto:yangbingdong1994@gmail.com">yangbingdong</a>
 */
@Slf4j
public class CvsUtil {

    public static <T> List<T> readVsvToBean(String filePath, Class<T> clazz) {
        try (Reader reader = newBufferedReader(Paths.get(getSystemResource(filePath).toURI()))) {
            return new CsvToBeanBuilder<T>(reader).withType(clazz).build().parse();
        } catch (Exception e) {
            log.error("CSV paese error", e);
            return null;
        }
    }

    public static <T> List<T> readVsvToBean(Reader reader, Class<T> clazz) {
        return new CsvToBeanBuilder<T>(reader).withType(clazz).build().parse();
    }
}

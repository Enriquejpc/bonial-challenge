package com.bonial.challenge.adapter.file;

import com.bonial.challenge.configuration.ErrorCode;
import com.bonial.challenge.core.exception.BusinessException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@UtilityClass
@Slf4j
public class FileReader {

    public String read(final String filePath) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(filePath);
        StringBuilder textBuilder = new StringBuilder();
        try {
            Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            textBuilder.append(FileCopyUtils.copyToString(reader));
        } catch (Exception ex) {
            log.error("Error reading file", ex);
            throw new BusinessException(ErrorCode.INTERNAL_ERROR);
        }

        return textBuilder.toString().replaceAll("\n", " ");
    }
}

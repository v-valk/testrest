package ee.testrest.allure;

import io.qameta.allure.Allure;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AllureUtils {
    private static final Logger logger = Logger.getLogger(AllureUtils.class.getName());

    public static void attachJpegToAllureReport(String attachmentName, Path file) {
        try (InputStream inputStream = Files.newInputStream(file)) {
            Allure.addAttachment(attachmentName, "image/jpeg", inputStream, ".jpeg");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to attach image/jpeg to Allure report", e);
        }
    }
}

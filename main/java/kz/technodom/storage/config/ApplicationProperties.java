package kz.technodom.storage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Filestorage.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final ApplicationProperties.IMAGESDIR imagesdir = new ApplicationProperties.IMAGESDIR();

    public ApplicationProperties.IMAGESDIR getImagesdir() {
        return this.imagesdir;
    }

    public static class IMAGESDIR{
        private String filepath;
        public IMAGESDIR(){
        }

        public String getFilepath() {
            return filepath;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
        }
    }
}

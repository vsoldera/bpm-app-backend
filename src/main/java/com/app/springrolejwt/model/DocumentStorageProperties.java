package com.app.springrolejwt.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@ConfigurationProperties(prefix = "file")
@Component
@Entity
@Table(name = "documentstorageproperties")
@Data
public class DocumentStorageProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer documentId;

    @Column(name = "user_id")
    private Integer UserId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "document_format")
    private String documentFormat;

    @Column(name = "upload_dir")
    private String uploadDir;
}

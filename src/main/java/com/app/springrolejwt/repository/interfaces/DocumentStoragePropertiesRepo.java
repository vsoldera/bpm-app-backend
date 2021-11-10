package com.app.springrolejwt.repository.interfaces;

import com.app.springrolejwt.model.DocumentStorageProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.print.Doc;

@Repository
public interface DocumentStoragePropertiesRepo extends JpaRepository<DocumentStorageProperties, Integer> {

    @Query(value = "SELECT * from DocumentStorageProperties a where a.user_id = ?1",
            nativeQuery = true)
    DocumentStorageProperties checkByUserId(Integer userId);

    @Query(value = "Select fileName from DocumentStorageProperties a where a.user_id = ?1 and a.document_type = ?2",
            nativeQuery = true)
    String getUploadDocumentPath(Integer userId, String docType);
}

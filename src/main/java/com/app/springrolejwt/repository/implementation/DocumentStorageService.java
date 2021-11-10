package com.app.springrolejwt.repository.implementation;

import com.app.springrolejwt.model.DocumentStorageProperties;
import com.app.springrolejwt.model.User;
import com.app.springrolejwt.repository.interfaces.DocumentStoragePropertiesRepo;
import com.app.springrolejwt.repository.interfaces.UserRepository;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class DocumentStorageService {

    private final Path fileStorageLocation;

    @Autowired
    DocumentStoragePropertiesRepo docStorageRepo;

    @Autowired
    UserRepository userRepository;


    @Autowired
    public DocumentStorageService(DocumentStorageProperties fileStorageProperties) {

        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();


        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            //throw new DocumentStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }

    }

    public String storeFile(MultipartFile file, Integer userId, String docType, String imageType) {

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = "";

        Optional<User> username = userRepository.findById(userId.longValue());



        try {
            String fileExtension = "";


            fileExtension = imageType;

            fileName = userId + "_" + docType + "." + fileExtension;
            System.out.println("FILENAME: " + "."+fileName);

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            DocumentStorageProperties doc = docStorageRepo.checkByUserId(userId);

            if (doc != null && doc.getUserId().equals(userId)) {

                    doc.setDocumentFormat(file.getContentType());
                    doc.setFileName(fileName);
                    username.get().setPhotoPath("/api/user/getImage/"+fileName);
                    docStorageRepo.save(doc);

            } else {

                DocumentStorageProperties newDoc = new DocumentStorageProperties();

                newDoc.setUserId(userId);
                newDoc.setDocumentFormat(file.getContentType());
                newDoc.setFileName(fileName);
                newDoc.setDocumentType(docType);
                username.get().setPhotoPath("/api/user/getImage/"+fileName);
                docStorageRepo.save(newDoc);

            }

            return fileName;

        } catch (IOException ex) {
            System.out.println("Deu ruim!");
            //throw new DocumentStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

        return null;
    }


    public Resource loadFileAsResource(String fileName) throws Exception {

        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName);
        }
    }

    public String getDocumentName(Integer userId, String docType) {
        return docStorageRepo.getUploadDocumentPath(userId, docType);
    }


}

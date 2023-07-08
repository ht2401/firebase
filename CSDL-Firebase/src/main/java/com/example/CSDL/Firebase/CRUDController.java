package com.example.CSDL.Firebase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class CRUDController {
    public CRUDService crudService;

    public CRUDController (CRUDService crudService) {
        this.crudService = crudService;
    }

    @PostMapping("/create")
    public String createCRUD (@RequestBody CRUD crud) throws InterruptedException, ExecutionException {
        return crudService.createCRUD(crud);
    }


    @GetMapping("/get")
    public CRUD getCRUD (@RequestParam String name) throws InterruptedException, ExecutionException {
        return crudService.getCRUD(name);
    }


    @PutMapping("/update/{documentId}")
    public ResponseEntity<String> updateCRUD(@PathVariable String documentId, @RequestBody CRUD crud) throws InterruptedException, ExecutionException {
        crud.setDocumentId(documentId);
        String result = crudService.updateCRUD(crud);
        if (result != null && !result.equals("DocumentId not found")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build(); // Trả về HTTP 404 Not Found nếu "documentId" không tồn tại
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCRUD(@RequestParam String documentId) throws InterruptedException, ExecutionException {
        String result = crudService.deleteCRUD(documentId);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/test")
    public ResponseEntity<String> testGetEndpoint() {
        return ResponseEntity.ok("test thôi");
    }
}

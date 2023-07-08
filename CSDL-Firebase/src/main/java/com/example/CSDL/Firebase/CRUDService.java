package com.example.CSDL.Firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class CRUDService {
    public String createCRUD(CRUD crud) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("crud_user").document(crud.getDocumentId());

        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            // Nếu tài liệu với "documentId" đã tồn tại, trả về thông báo lỗi
            return "Lỗi: Đã có sẵn tài liệu với documentId " + crud.getDocumentId();
        } else {
            // Nếu tài liệu không tồn tại, thực hiện tạo mới
            ApiFuture<WriteResult> collectionApiFuture = documentReference.set(crud);
            return collectionApiFuture.get().getUpdateTime().toString();
        }
    }


    public CRUD getCRUD(String name) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("crud_user").document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        CRUD crud;
        if (document.exists()) {
            crud = document.toObject(CRUD.class);
            return crud;
        }
        return null;
    }


    public String updateCRUD(CRUD crud) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("crud_user").document(crud.getDocumentId());

        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            // Nếu tài liệu với "documentId" đã tồn tại, thực hiện cập nhật
            ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(crud);
            return writeResultApiFuture.get().getUpdateTime().toString();
        } else {
            // Nếu tài liệu không tồn tại, trả về một giá trị đặc biệt để chỉ ra lỗi
            return "ko có tài liệu";
        }
    }


    public String deleteCRUD(String documentId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("crud_user").document(documentId);
        ApiFuture<DocumentSnapshot> documentSnapshot = documentReference.get();
        DocumentSnapshot snapshot = documentSnapshot.get();
        if (snapshot.exists()) {
            ApiFuture<WriteResult> writeResult = documentReference.delete();
            writeResult.get();
            return "Xóa thành công";
        } else {
            return "Không có ID này trong bộ sưu tập";
        }
    }
}

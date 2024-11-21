package com.example.clubdiversion.ui.documento;

import java.util.List;

public interface DocumentoContract {
    interface View {
        void showDocuments(List<String> descriptions, List<String> links);
        void navigateToDocumentViewer(String description, String link);
        void setUserType(String userType);
        void showAdminError();
        void clearFields();
    }

    interface Presenter {
        void loadDocuments();
        void checkUserType();
        void addDocument(String description, String link);
    }
}

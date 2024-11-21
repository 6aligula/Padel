package com.example.clubdiversion.ui.documento;

import com.example.clubdiversion.data.repository.DocumentoRepository;

import java.util.List;

public class DocumentoPresenter implements DocumentoContract.Presenter {

    private final DocumentoContract.View view;
    private final DocumentoRepository repository;

    public DocumentoPresenter(DocumentoContract.View view, DocumentoRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadDocuments() {
        List<String> descriptions = repository.getDescriptions();
        List<String> links = repository.getLinks();
        view.showDocuments(descriptions, links);
    }

    @Override
    public void checkUserType() {
        String userType = repository.isCurrentUserAdmin() ? "Administrador" : "Socio";
        view.setUserType(userType);
    }

    @Override
    public void addDocument(String description, String link) {
        if (description.isEmpty() || link.isEmpty()) {
            view.showAdminError();
            return;
        }

        if (repository.isDescriptionRegistered(description)) {
            view.showAdminError();
        } else {
            repository.insertDocument(description, link);
            view.clearFields();
            loadDocuments();
        }
    }
}

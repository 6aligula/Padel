package com.example.clubdiversion.ui.duda;

import java.util.List;

public interface DudaContract {

    interface View {
        void showDudaList(List<String> dudas, List<String> respuestas);
        void showDudaCreated(String message);
        void showError(String message);
        void clearFields();
        void setUserType(String userType);
        void enableResponseField(boolean enabled);
    }

    interface Presenter {
        void loadDudaList();
        void createDuda(String duda, String respuesta);
        void clearFields();
        void updateDuda(String duda, String respuesta);
        void checkUserType();
    }
}

package com.example.clubdiversion.ui.edit;

import com.example.clubdiversion.data.entities.UserResponse;

public interface EditContract {
    interface View {
        void showSuccess(String message);
        void showError(String error);
    }

    interface Presenter {
        void updateUser(int userId, UserResponse user, String password);
    }
}

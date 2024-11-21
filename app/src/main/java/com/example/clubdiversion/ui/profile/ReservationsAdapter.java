package com.example.clubdiversion.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubdiversion.R;
import com.example.clubdiversion.data.entities.ReservationResponse;

import java.util.List;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder> {

    private List<ReservationResponse> reservations;

    public ReservationsAdapter(List<ReservationResponse> reservations) {
        this.reservations = reservations;
    }

    public void updateReservations(List<ReservationResponse> newReservations) {
        this.reservations.clear();
        this.reservations.addAll(newReservations);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        ReservationResponse reservation = reservations.get(position);
        holder.bind(reservation);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {

        private TextView txtDate, txtInstallation;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtInstallation = itemView.findViewById(R.id.txtInstallation);
        }

        public void bind(ReservationResponse reservation) {
            // Usa un formato estructurado para mostrar los datos
            txtDate.setText(String.format("Fecha: %s", reservation.getDate()));
            txtInstallation.setText(String.format("Instalación: %s", reservation.getInstallation()));
        }

    }
}


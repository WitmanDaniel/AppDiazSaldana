// En un nuevo archivo llamado ReservationAdapter.java
package unc.edu.pe.appdiazsaldana;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private List<Reservation> reservations;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Reservation reservation);
    }

    public ReservationAdapter(List<Reservation> reservations, OnItemClickListener listener) {
        this.reservations = reservations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation reservation = reservations.get(position);
        holder.bind(reservation, listener);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void updateReservations(List<Reservation> newReservations) {
        this.reservations = newReservations;
        notifyDataSetChanged();
    }

    static class ReservationViewHolder extends RecyclerView.ViewHolder {
        private final TextView clientName;
        private final TextView tableNumber;
        private final TextView diningArea;
        private final TextView reservationDate;
        private final ImageView areaImage;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            clientName = itemView.findViewById(R.id.text_client_name);
            tableNumber = itemView.findViewById(R.id.text_table_number);
            diningArea = itemView.findViewById(R.id.text_dining_area);
            reservationDate = itemView.findViewById(R.id.text_reservation_date);
            areaImage = itemView.findViewById(R.id.image_area);
        }

        public void bind(final Reservation reservation, final OnItemClickListener listener) {
            clientName.setText(reservation.getClientName());
            tableNumber.setText("Mesa: " + reservation.getTableNumber());
            diningArea.setText("Área: " + reservation.getDiningArea());
            reservationDate.setText("Fecha: " + reservation.getReservationDate());
            areaImage.setImageResource(Reservation.getImageResourceForArea(reservation.getDiningArea()));

            itemView.setOnClickListener(v -> listener.onItemClick(reservation));
        }
    }
}

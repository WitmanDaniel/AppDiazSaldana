// En tu archivo MainActivity.java existente

package unc.edu.pe.appdiazsaldana;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservationAdapter adapter;
    private List<Reservation> allReservations;
    private EditText searchInput;

    // Lanzador para recibir el resultado de la pantalla de edición
    private final ActivityResultLauncher<Intent> editReservationLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Reservation updatedReservation = (Reservation) result.getData().getSerializableExtra("UPDATED_RESERVATION");
                    if (updatedReservation != null) {
                        // Actualizamos la lista original y notificamos al adaptador
                        for (int i = 0; i < allReservations.size(); i++) {
                            if (allReservations.get(i).getId() == updatedReservation.getId()) {
                                allReservations.set(i, updatedReservation);
                                adapter.updateReservations(new ArrayList<>(allReservations)); // Pasamos una copia
                                break;
                            }
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Asegúrate de tener este layout

        recyclerView = findViewById(R.id.recycler_view_reservations);
        searchInput = findViewById(R.id.search_input);

        loadInitialData(); // Cargamos los datos de ejemplo

        // Configuración del RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReservationAdapter(allReservations, reservation -> {
            // Acción al hacer clic en una reserva: abrir la pantalla de edición
            Intent intent = new Intent(MainActivity.this, EditReservationActivity.class);
            intent.putExtra("RESERVATION_TO_EDIT", reservation);
            editReservationLauncher.launch(intent);
        });
        recyclerView.setAdapter(adapter);

        // Configuración de la barra de búsqueda
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadInitialData() {
        // Datos de ejemplo
        allReservations = new ArrayList<>();
        allReservations.add(new Reservation(1, "Juan Pérez", 5, "Salón Principal", "20/05/2024", 4));
        allReservations.add(new Reservation(2, "Ana Gómez", 2, "Terraza", "22/05/2024", 6));
        allReservations.add(new Reservation(3, "Carlos Ruiz", 10, "Salón VIP", "25/05/2024", 2));
    }

    private void filter(String text) {
        // Filtramos la lista de reservas por nombre de cliente
        List<Reservation> filteredList = allReservations.stream()
                .filter(reservation -> reservation.getClientName().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
        adapter.updateReservations(filteredList);
    }
}

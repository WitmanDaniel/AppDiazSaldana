package unc.edu.pe.appdiazsaldana;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;

public class EditReservationActivity extends AppCompatActivity {

    private TextInputEditText editClientName, editTableNumber, editNumberOfPeople;
    private AutoCompleteTextView editDiningArea;
    private TextView textCourtesyDrink, editReservationDate;
    private ImageView imageArea;
    private Button saveButton;
    private Reservation currentReservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reservation);


        editClientName = findViewById(R.id.edit_client_name);
        editTableNumber = findViewById(R.id.edit_table_number);
        editNumberOfPeople = findViewById(R.id.edit_number_of_people);
        editDiningArea = findViewById(R.id.edit_dining_area);
        editReservationDate = findViewById(R.id.edit_reservation_date);
        textCourtesyDrink = findViewById(R.id.text_courtesy_drink);
        imageArea = findViewById(R.id.image_area_edit);
        saveButton = findViewById(R.id.button_save);


        currentReservation = (Reservation) getIntent().getSerializableExtra("RESERVATION_TO_EDIT");

        if (currentReservation != null) {
            loadReservationData();
        }

        String[] areas = {"Salón Principal", "Terraza", "Salón VIP"};
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, areas);
        editDiningArea.setAdapter(areaAdapter);


        editDiningArea.setOnItemClickListener((parent, view, position, id) -> {
            String selectedArea = (String) parent.getItemAtPosition(position);
            imageArea.setImageResource(Reservation.getImageResourceForArea(selectedArea));
        });

        editReservationDate.setOnClickListener(v -> showDatePickerDialog());


        saveButton.setOnClickListener(v -> saveChanges());
    }

    private void loadReservationData() {
        editClientName.setText(currentReservation.getClientName());
        editTableNumber.setText(String.valueOf(currentReservation.getTableNumber()));
        editNumberOfPeople.setText(String.valueOf(currentReservation.getNumberOfPeople()));
        editDiningArea.setText(currentReservation.getDiningArea(), false); // false para no filtrar
        editReservationDate.setText(currentReservation.getReservationDate());
        textCourtesyDrink.setText("Bebida: " + currentReservation.getCourtesyDrink());
        imageArea.setImageResource(Reservation.getImageResourceForArea(currentReservation.getDiningArea()));
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    // Formateamos la fecha y la ponemos en el TextView
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    editReservationDate.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void saveChanges() {

        if (editClientName.getText().toString().isEmpty() || editTableNumber.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }


        currentReservation.setClientName(editClientName.getText().toString());
        currentReservation.setTableNumber(Integer.parseInt(editTableNumber.getText().toString()));
        currentReservation.setDiningArea(editDiningArea.getText().toString());
        currentReservation.setReservationDate(editReservationDate.getText().toString());
        currentReservation.setNumberOfPeople(Integer.parseInt(editNumberOfPeople.getText().toString()));


        Intent resultIntent = new Intent();
        resultIntent.putExtra("UPDATED_RESERVATION", currentReservation);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}

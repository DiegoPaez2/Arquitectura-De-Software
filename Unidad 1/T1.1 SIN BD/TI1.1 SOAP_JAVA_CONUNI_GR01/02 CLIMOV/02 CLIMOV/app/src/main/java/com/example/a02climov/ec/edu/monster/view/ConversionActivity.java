package ec.edu.monster.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executors;

import ec.edu.monster.controller.ConversionController;
import ec.edu.monster.model.ConversionModel;
import com.example.a02climov.R;

public class ConversionActivity extends AppCompatActivity {

    private static final String SERVICE_URL =
            "http://192.168.100.170:8080/WS_CONUNI_SOAPJAVA_GRO01/WS_Conversion_Unidades";

    private final String[] opciones = new String[]{
            "Kilogramos a Libras",
            "Libras a Kilogramos",
            "Gramos a Onzas",
            "Celsius a Fahrenheit",
            "Celsius a Kelvin",
            "Fahrenheit a Celsius",
            "Kilómetros a Millas",
            "Metros a Pies",
            "Pulgadas a Centímetros"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        Spinner spTipo = findViewById(R.id.spTipo);
        EditText etValor = findViewById(R.id.etValor);
        Button btnConvertir = findViewById(R.id.btnConvertir);
        TextView tvResultado = findViewById(R.id.tvResultado);

        spTipo.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, opciones));

        btnConvertir.setOnClickListener(v -> {
            String tipo = (String) spTipo.getSelectedItem();
            String valorStr = etValor.getText().toString().trim();
            if (valorStr.isEmpty()) {
                Toast.makeText(this, "Ingrese un valor", Toast.LENGTH_SHORT).show();
                return;
            }
            double valor = Double.parseDouble(valorStr);

            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    ConversionController ctrl = new ConversionController(SERVICE_URL);
                    ConversionModel model = new ConversionModel(valor, tipo);
                    double out = ctrl.convertir(model);

                    new Handler(Looper.getMainLooper()).post(
                            () -> tvResultado.setText("Resultado: " + out)
                    );
                } catch (Exception e) {
                    new Handler(Looper.getMainLooper()).post(
                            () -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
                }
            });
        });
    }
}


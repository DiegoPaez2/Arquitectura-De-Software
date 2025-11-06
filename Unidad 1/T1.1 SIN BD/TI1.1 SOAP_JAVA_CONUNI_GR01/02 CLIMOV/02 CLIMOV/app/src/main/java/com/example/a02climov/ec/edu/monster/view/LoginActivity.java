package ec.edu.monster.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executors;

import ec.edu.monster.controller.ConversionController;
import ec.edu.monster.model.SessionModel;
import com.example.a02climov.R;

public class LoginActivity extends AppCompatActivity {

    // 👉 URL de tu servicio SOAP (ajustada a tu IP real)
    private static final String SERVICE_URL =
            "http://192.168.100.170:8080/WS_CONUNI_SOAPJAVA_GRO01/WS_Conversion_Unidades";

    private final SessionModel session = new SessionModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Referencias de UI
        EditText etUsuario = findViewById(R.id.etUsuario);
        EditText etContrasena = findViewById(R.id.etContrasena);
        Button btnLogin = findViewById(R.id.btnLogin);

        // === Mostrar/Ocultar contraseña con el ícono del ojo ===
        etContrasena.setOnTouchListener((v, event) -> {
            final int DRAWABLE_END = 2; // índice del ícono en el extremo derecho
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (etContrasena.getRight()
                        - etContrasena.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                    int cursor = etContrasena.getSelectionEnd();
                    if (etContrasena.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        etContrasena.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    } else {
                        etContrasena.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    }
                    etContrasena.setSelection(cursor);
                    return true;
                }
            }
            return false;
        });

        // === Botón INGRESAR ===
        btnLogin.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString().trim();
            String contrasena = etContrasena.getText().toString();

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    ConversionController ctrl = new ConversionController(SERVICE_URL);
                    boolean ok = ctrl.login(usuario, contrasena);

                    new Handler(Looper.getMainLooper()).post(() -> {
                        if (ok) {
                            session.setLoggedIn(true);
                            session.setUsuario(usuario);
                            Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show();

                            // Ir a pantalla de conversiones
                            Intent intent = new Intent(this, ec.edu.monster.view.ConversionActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
                }
            });
        });
    }
}

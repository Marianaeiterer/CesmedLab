package souza.cesmedlab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class activity_result extends AppCompatActivity {
    Button button;
    EditText correo, mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        correo = findViewById(R.id.caja_correo);
        mensaje = findViewById(R.id.caja_mensaje);
        button = findViewById(R.id.btn_enviar);

        // El setOnClickListener del botón Enviar

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                String enviarcorreo =correo.getText().toString();//  here lab email
                String enviarasunto = "Exámenes";
                String enviarmensaje = mensaje.getText().toString();// here the pdf file

                // Defino mi Intent y hago uso del objeto ACTION_SEND
                Intent intent = new Intent(Intent.ACTION_SEND);

                // Defino los Strings Email, Asunto y Mensaje con la función putExtra
                intent.putExtra(Intent.EXTRA_EMAIL,
                        new String[] { enviarcorreo });
                intent.putExtra(Intent.EXTRA_SUBJECT, enviarasunto);
                intent.putExtra(Intent.EXTRA_TEXT, enviarmensaje);

                // Establezco el tipo de Intent
                intent.setType("message/rfc822");

                // Lanzo el selector de cliente de Correo
                startActivity(
                        Intent
                                .createChooser(intent,
                                        "Elije un cliente de Correo:"));
            }
        });


    }
    public void goHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
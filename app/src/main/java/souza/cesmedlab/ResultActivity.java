package souza.cesmedlab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ResultActivity extends AppCompatActivity {
    Button send;
    public final static String email="Email", result="Result",
            patienceName="Patience's Name", doctorCRM="Doctor's CRM",
            doctorName = "Doctor's Name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

    }
    public void confirmResult(View view){
        EditText editText = (EditText) findViewById(R.id.caja_crm);
        String dCRM = editText.getText().toString();
        editText = (EditText) findViewById(R.id.caja_doctorName);
        String doctorNames = editText.getText().toString();
        editText = (EditText) findViewById(R.id.caja_patientName);
        String patienceNames = editText.getText().toString();
        editText = (EditText) findViewById(R.id.caja_correo);
        String Email = editText.getText().toString();
        editText = (EditText) findViewById(R.id.caja_mensaje);
        String Result = editText.getText().toString();

        send = findViewById(R.id.btn_enviar);
        Intent intent = new Intent(this, ConfirmResultActivity.class);
        intent.putExtra(doctorCRM, dCRM);
        intent.putExtra(doctorName, doctorNames);
        intent.putExtra(patienceName, patienceNames);
        intent.putExtra(email, Email);
        intent.putExtra(result, Result);
        startActivity(intent);

    }
    public void goHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
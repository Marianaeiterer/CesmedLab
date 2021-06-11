package souza.cesmedlab;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ExamActivity extends AppCompatActivity {
    private EditText doctorName, doctorCRM, patientName;
    private Button showList;
    ListView listHematologias, listQumicaS;
    ArrayAdapter<String> adapter;
    String[] Hematologia = {"Biometria Hemática completa","Hcto/Hb",
            "Plaquetas","Sdimentación(VSG)","Hematozoario","Retriculocitos",
            "Grupo y factor Flh"};

    String[] QuimicaSanguinea = {"Glucosa Basal","Glucosa Post. prandial","Curva de Glucosa -- Horas","Urea",
            "Cretanina", "Acido úrico","Colesterol","HDL colesterol", "LDL colesterol", "Triglicéridos",
            "Bilirrubina", "Proteinas totales","Albumina","Globulinas", "Calcio"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        doctorName = findViewById(R.id.doctor_name);
        doctorCRM = findViewById(R.id.doctor_crm);
        patientName = findViewById(R.id.patient_name);
        showList = findViewById(R.id.ShowList);
        listHematologias = findViewById(R.id.listView_hematologia);
        listQumicaS = findViewById(R.id.listView_qumicaS);
        listCreation();
    }

    public void onConfirmation(View view){


        //un Comentario de prueba

    }
    public void listCreation(){
        
        String itemSelected = "Selected Items: \n";
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,Hematologia);
        listHematologias.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,QuimicaSanguinea);
        listQumicaS.setAdapter(adapter);

    }

    public void ListChecked(View view){
        String itemSelected = "Selected Items: \n";
        for ( int i = 0 ; i < listHematologias.getCount(); i++){
            if (listHematologias.isItemChecked(i)){
                itemSelected+= "HEMATOLOGIAS: \n"+listHematologias.getItemAtPosition(i)+"\n";
            }
        }
        

        for ( int i = 0 ; i < listQumicaS.getCount(); i++){
            if (listQumicaS.isItemChecked(i)){
                itemSelected+= "QUIMICA SANGUINEA: \n"+listQumicaS.getItemAtPosition(i)+"\n";
            }
        }
        Toast.makeText(this, itemSelected,Toast.LENGTH_SHORT).show();

    }

}
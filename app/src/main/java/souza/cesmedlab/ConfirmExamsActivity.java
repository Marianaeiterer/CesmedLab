package souza.cesmedlab;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.IpSecManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfirmExamsActivity extends AppCompatActivity {
    String doctorName;
    String doctorCRM;
    String patientName;
    ArrayList<String> exams;
    public static final int CREATEPDF = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_exams);
        Intent intent = getIntent();
        if (intent != null) { // If nothing was sent, make sure we don't try to read it
            doctorName = intent.getStringExtra("Doctor Name");     // If param not there, then it will be null
            doctorCRM = intent.getStringExtra("Doctor CRM");  // If param not there, then set it to 0
            patientName = intent.getStringExtra("Patient Name");
            Bundle bundle = getIntent().getExtras();
            exams = bundle.getStringArrayList("exams");
            ListView listView = findViewById(R.id.exams);
            ArrayAdapter<String> items = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exams);
            listView.setAdapter(items);

            // Capture the layout's TextView and set the string as its text
            TextView textView = findViewById(R.id.confirmation);
            textView.setText("Doctor Name: " + doctorName + "\n\nDoctor CRM: " + doctorCRM + "\n\nPatient Name: " + patientName + "\n\nRequired Exams : ");
        } else {
            TextView textView = findViewById(R.id.confirmation);
            textView.setText("Oh, don't you have a favorite scripture? Try Ether 12:27");
        }
    }

    public void onConfirmation(View view){
        createPDF("Exams.pdf");
    }

    private void createPDF(String title) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("aplication/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, title);
        startActivityForResult(intent, CREATEPDF);
    }

    public void onBack(View view){
        Intent intent = new Intent(this, ExamActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATEPDF) {
            if (data.getData() != null) {
                Uri caminhDoArquivo = data.getData();

                PdfDocument pdfDocument = new PdfDocument();
                Paint title = new Paint();
                Paint text = new Paint();
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1240, 1754, 1).create();
                PdfDocument.Page page = pdfDocument.startPage(pageInfo);
                Canvas canvas = page.getCanvas();
                title.setTextAlign(Paint.Align.CENTER);
                title.setTextSize(60f);
                title.setFakeBoldText(true);
                canvas.drawText("Exams", pageInfo.getPageWidth() / 2, 100, title);

                text.setTextAlign(Paint.Align.LEFT);
                text.setTextSize(40f);
                text.setFakeBoldText(false);

                canvas.drawText("Doctor Name: " + doctorName, 100, 200, text);
                canvas.drawText("Doctor CRM: " + doctorCRM , 100, 250, text);
                canvas.drawText("Patient Name: " + patientName , 100, 300, text);
                canvas.drawText("Required Exams : " , 100, 350, text);
                int y = 410;
                for(int i = 0; i < exams.size(); i++){
                    canvas.drawText(exams.get(i), 150, y, text);
                    y+=50;
                }

                pdfDocument.finishPage(page);
                gravarPdf(caminhDoArquivo, pdfDocument);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void gravarPdf(Uri caminhDoArquivo, PdfDocument pdfDocument) {
        try {
            BufferedOutputStream stream = new BufferedOutputStream(Objects.requireNonNull(getContentResolver().openOutputStream(caminhDoArquivo)));
            pdfDocument.writeTo(stream);
            pdfDocument.close();
            stream.flush();
            Toast.makeText(this, "PDF Recorded with Sucess", Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File doesn't exist", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Unknown Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
package souza.cesmedlab;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class activity_confirm_result extends AppCompatActivity {
    String doctorCRM, doctorName, patienceName, email, result;
    public static final int CREATEPDF = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_result);
        Intent intent = getIntent();
        if(intent != null){
            doctorCRM = intent.getStringExtra("Doctor CRM");
            doctorName = intent.getStringExtra("Doctor Name");
            patienceName = intent.getStringExtra("Patience Name");
            email = intent.getStringExtra("Email");
            result = intent.getStringExtra("Result");
            TextView textView = findViewById(R.id.confirmation);
            textView.setText("Doctor CRM: "+doctorCRM+"\n\nDoctor Name: "+doctorName+"\n\nPatience Name: "+patienceName+"\n\nE-mail: "+email+"\n\nResult: "+result);

        }else{
            TextView textView = findViewById(R.id.confirmation);
            textView.setText("WARRING \n\nWrite anything");
        }
    }
    public void onConfirmation(View view){
        createPDF("Result"+"-"+patienceName+".pdf");

    }
    private void createPDF(String title){
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("aplication/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, title);
        startActivityForResult(intent,CREATEPDF);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATEPDF) {
            if (data.getData() != null) {
                Uri caminhoDoArquivo = data.getData();

                PdfDocument pdfDocument = new PdfDocument();
                Paint title = new Paint();
                Paint text = new Paint();
                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1240, 1754, 1).create();
                PdfDocument.Page page = pdfDocument.startPage(pageInfo);
                Canvas canvas = page.getCanvas();
                title.setTextAlign(Paint.Align.CENTER);
                title.setTextSize(60f);
                title.setFakeBoldText(true);
                canvas.drawText("Result", pageInfo.getPageWidth() / 2, 100, title);

                text.setTextAlign(Paint.Align.LEFT);
                text.setTextSize(40f);
                text.setFakeBoldText(false);

                //canvas.drawText("Doctor Name: " + doctorName, 100, 200, text);
                canvas.drawText("Doctor CRM: " + doctorCRM , 100, 250, text);
                canvas.drawText("Doctor Name: " + doctorName , 100, 250, text);
                canvas.drawText("Patient Name: " + patienceName , 100, 300, text);
                canvas.drawText("Patient Email: " + email , 100, 300, text);
                canvas.drawText("Required Result : "+result , 100, 350, text);


                pdfDocument.finishPage(page);
                gravarPdf(caminhoDoArquivo, pdfDocument);
                sendEmail(caminhoDoArquivo, pdfDocument);
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
    private void sendEmail(Uri caminhDoArquivo, PdfDocument pdfDocument){
        // alexis.ortiz81@outlook.com, lucas.sms@gmail.com, oscar.fmalves@gmail.com
        Toast.makeText(this, "Send PDF in the Email", Toast.LENGTH_LONG).show();
        String recipientsList = email;
        String[] recipients = recipientsList.split(",");
        String subject = "Exams Requirements - Patient: " + patienceName;
        String message = patienceName + " needs to do the following requirements\nAtt, \n" + doctorName;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.putExtra(Intent.EXTRA_STREAM, caminhDoArquivo);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Send email using:"));


    }
    public void goBack(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }
}
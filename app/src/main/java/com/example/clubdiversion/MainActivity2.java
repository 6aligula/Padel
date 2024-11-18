package com.example.clubdiversion;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity2 extends AppCompatActivity {

    SurfaceView pdfSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        pdfSurfaceView = findViewById(R.id.pdfSurfaceView);

        // Obtén el enlace del PDF desde el Intent
        String pdfLink = getIntent().getStringExtra("Link");
        if (pdfLink != null && !pdfLink.isEmpty()) {
            new RetrieveAndRenderPDF(pdfSurfaceView).execute(pdfLink);
        }
    }

    class RetrieveAndRenderPDF extends AsyncTask<String, Void, InputStream> {
        SurfaceView surfaceView;

        public RetrieveAndRenderPDF(SurfaceView surfaceView) {
            this.surfaceView = surfaceView;
        }

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (Exception e) {
                Log.e("PDF_ERROR", "Error retrieving PDF: ", e);
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            if (inputStream != null) {
                renderPDF(inputStream, surfaceView);
            }
        }

        private void renderPDF(InputStream inputStream, SurfaceView surfaceView) {
            PdfiumCore pdfiumCore = new PdfiumCore(MainActivity2.this);

            try {
                // Crear un archivo temporal para almacenar el PDF
                File tempFile = File.createTempFile("temp_pdf", ".pdf", getCacheDir());
                FileOutputStream fileOutputStream = new FileOutputStream(tempFile);

                // Copiar el contenido del InputStream al archivo temporal
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
                fileOutputStream.close();

                // Obtener un ParcelFileDescriptor del archivo temporal
                ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY);

                // Abrir el documento PDF
                PdfDocument pdfDocument = pdfiumCore.newDocument(parcelFileDescriptor);

                // Renderizar la primera página
                pdfiumCore.openPage(pdfDocument, 0);
                int width = pdfiumCore.getPageWidthPoint(pdfDocument, 0);
                int height = pdfiumCore.getPageHeightPoint(pdfDocument, 0);

                SurfaceHolder holder = surfaceView.getHolder();
                Surface surface = holder.getSurface(); // Obtener el Surface directamente

                // Renderizar la página en el Surface
                pdfiumCore.renderPage(pdfDocument, surface, 0, 0, 0, width, height);

                // Cerrar el documento y eliminar el archivo temporal
                pdfiumCore.closeDocument(pdfDocument);
                tempFile.delete();

            } catch (Exception e) {
                Log.e("PDF_RENDER_ERROR", "Error rendering PDF: ", e);
            }
        }

    }
}

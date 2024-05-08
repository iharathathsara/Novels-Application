package com.initezz.novels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.initezz.novels.api.RetrofitClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Retrofit;

public class NovelPdfActivity extends AppCompatActivity {
    PDFView pdfView;
    private long downloadId;
    String pdfName;
    private boolean isReceiverRegistered = false;
    private NotificationManager notificationManager;
    private String channelId = "info";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel_pdf);

        String pdfPath=getIntent().getExtras().getString("pdfPath");
        pdfName=getIntent().getExtras().getString("pdfName");

        String retrofit = RetrofitClient.getBaseUrl();

        String path=retrofit+pdfPath;
        pdfView=findViewById(R.id.pdfView);
        new Retrivepdf().execute(path);


        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.downloadBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPdf(path);
            }
        });

    }

    private void downloadPdf(String url) {

        // Create a DownloadManager.Request with the download URL
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("PDF Downloading");
        request.setDescription("Downloading PDF");

        // Set the destination directory for the downloaded file
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Novels/"+pdfName+".pdf");

        // Get the DownloadManager service and enqueue the request
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadId = downloadManager.enqueue(request);

        // Register a BroadcastReceiver to receive the completion of the download
        registerReceiver(downloadCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        Toast.makeText(this, "okk", Toast.LENGTH_SHORT).show();
    }

    private BroadcastReceiver downloadCompleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Check if the download was successful
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (id == downloadId) {
                // Handle the completion of the download
                unregisterReceiver(this);

                isReceiverRegistered = false;

                Toast.makeText(context, "Downloaded Completed", Toast.LENGTH_SHORT).show();

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the BroadcastReceiver to avoid memory leaks
//        unregisterReceiver(downloadCompleteReceiver);

        /////////////
        if (isReceiverRegistered) {
            unregisterReceiver(downloadCompleteReceiver);
            isReceiverRegistered = false;
        }
    }

    class Retrivepdf extends AsyncTask<String,Void, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream=null;
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
                if(httpURLConnection.getResponseCode()==200){
                    inputStream=new BufferedInputStream(httpURLConnection.getInputStream());
                }
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream)
//                    .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    // allows to draw something on the current page, usually visible in the middle of the screen
//                    .onDraw(onDrawListener)
                    // allows to draw something on all pages, separately for every page. Called only for visible pages
//                    .onDrawAll(onDrawListener)
                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            findViewById(R.id.loadingView).setVisibility(View.GONE);
                            findViewById(R.id.downloadBtn).setVisibility(View.VISIBLE);
                        }
                    }) // called after document is loaded and starts to be rendered
//                    .onPageChange(onPageChangeListener)
//                    .onPageScroll(onPageScrollListener)
//                    .onError(onErrorListener)
//                    .onPageError(onPageErrorListener)
//                    .onRender(onRenderListener) // called after document is rendered for the first time
                    // called on single tap, return true if handled, false to toggle scroll handle visibility
//                    .onTap(onTapListener)
//                    .onLongPress(onLongPressListener)
                    .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .spacing(0)
                    .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
//                    .linkHandler(DefaultLinkHandler)
                    .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
                    .fitEachPage(false) // fit each page to the view, else smaller pages are scaled relative to largest page.
                    .pageSnap(false) // snap pages to screen boundaries
                    .pageFling(false) // make a fling change only a single page like ViewPager
                    .nightMode(false) // toggle night mode
                    .load();

        }

    }
}
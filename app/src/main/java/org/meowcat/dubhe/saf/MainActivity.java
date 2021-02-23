package org.meowcat.dubhe.saf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import java.io.IOException;
import java.util.Objects;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {

    static final String TAG = "MLGMXYYSD_SAFTEST";
    static final int DATA_REQUEST_CODE = 114;
    static final int OBB_REQUEST_CODE = 514;
    Button dataBtn;
    Button obbBtn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBtn = findViewById(R.id.data);
        obbBtn = findViewById(R.id.obb);
        textView = findViewById(R.id.tv);
        dataBtn.setOnClickListener(v -> selectData());
        obbBtn.setOnClickListener(v -> selectObb());
    }

    void selectData() {
        // See: http://mlgmxyysd.meowcat.org/2021/02/18/android-r-saf-data/
        Uri uri = Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fdata/document/primary%3AAndroid%2Fdata");
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
        intent.putExtra("android.provider.extra.INITIAL_URI", uri);
        textView.setText("data: request permission");
        startActivityForResult(intent, DATA_REQUEST_CODE);
    }

    void selectObb() {
        // See: http://mlgmxyysd.meowcat.org/2021/02/18/android-r-saf-data/
        Uri uri = Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fobb/document/primary%3AAndroid%2Fobb");
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
        intent.putExtra("android.provider.extra.INITIAL_URI", uri);
        textView.setText("obb: request permission");
        startActivityForResult(intent, OBB_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || resultCode != Activity.RESULT_OK) {
            if (requestCode == DATA_REQUEST_CODE) {
                textView.setText("data: user refused");
            } else if (requestCode == OBB_REQUEST_CODE) {
                textView.setText("obb: user refused");
            }
            return;
        }
        if (requestCode == DATA_REQUEST_CODE || requestCode == OBB_REQUEST_CODE) {
            DocumentFile child = Objects.requireNonNull(DocumentFile.fromTreeUri(this, data.getData()));
            Uri uri = Objects.requireNonNull(child.createFile("text/plain", TAG)).getUri();
            DocumentFile[] documentFiles = child.listFiles();
            StringBuilder stringBuilder = new StringBuilder();
            for (DocumentFile documentFile : documentFiles) {
                stringBuilder.append(documentFile.getName()).append("\n");
            }
            try {
                getContentResolver().openOutputStream(uri).write(stringBuilder.toString().getBytes());
                if (requestCode == DATA_REQUEST_CODE) {
                    textView.setText("data: write success");
                } else {
                    textView.setText("obb: write success");
                }
            } catch (IOException e) {
                if (requestCode == DATA_REQUEST_CODE) {
                    textView.setText("data: write failed");
                } else {
                    textView.setText("obb: write failed");
                }
                Log.w(TAG, e);
            }
        }
    }
}
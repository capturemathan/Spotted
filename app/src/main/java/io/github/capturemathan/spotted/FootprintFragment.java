package io.github.capturemathan.spotted;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class FootprintFragment extends Fragment {

    String url = "https://www.google.com/search?q=";
    WebView wv;
    EditText missingname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_footprint, null);
        Button search = rootview.findViewById(R.id.missingsearch);
        wv = rootview.findViewById(R.id.webview);
        missingname = rootview.findViewById(R.id.missingperson);
        Log.v("url", url);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchquery = missingname.getText().toString();
                Log.v("search", searchquery);
                url = url + searchquery;
                wv.getSettings().setLoadsImagesAutomatically(true);
                wv.getSettings().setJavaScriptEnabled(true);
                wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv.setWebViewClient(new WebViewClient());
                wv.loadUrl(url);
            }
        });

        return rootview;
    }
}

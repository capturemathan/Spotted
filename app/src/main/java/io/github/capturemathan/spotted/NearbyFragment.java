package io.github.capturemathan.spotted;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;

public class NearbyFragment extends Fragment {
    String nearbyloc;
    String nearname, url;
    Button nearbypics, nearbysubmit;
    EditText nearbyname;
    final int PICK_IMAGE_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_nearby, null);

        nearbyname = rootview.findViewById(R.id.nearbyname);
        nearbypics = rootview.findViewById(R.id.nearby_choosephotos);
        nearbysubmit = rootview.findViewById(R.id.nearbybutton);

        nearbysubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nearname = nearbyname.getText().toString();
                new getresponse().execute();
            }
        });

        nearbypics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        final PlacesAutocompleteTextView placesAutocomplete = rootview.findViewById(R.id.nearbyplaces_autocomplete);
        placesAutocomplete.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {
                        // do something awesome with the selected place
                        placesAutocomplete.getDetailsFor(place, new DetailsCallback() {
                            @Override
                            public void onSuccess(PlaceDetails placeDetails) {
                                nearbyloc = placeDetails.name;
                                Toast.makeText(getContext(), placeDetails.name
                                        , Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                Log.d("test", "failure " + throwable);
                            }
                        });

                    }
                }
        );
        return rootview;
    }

    private class getresponse extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getActivity(), "Sending Data", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            url = "http://192.168.43.191/nearby.php?name=" + nearname + "&location=" + nearbyloc;
            Log.v("url", url);
            /*HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            Log.e("MainActivity", "Response from url: " + jsonStr);
            try {
                Document doc = Jsoup.connect(url).get();
                Log.v("page", doc.toString());
                Element e = doc.select("p#mathan").first();
                m = e.text();
            } catch (IOException e1) {
                e1.printStackTrace();
            }*/
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //t.setText(m);
            Toast.makeText(getActivity(), "Details Submitted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
            super.onPostExecute(aVoid);
        }
    }

}

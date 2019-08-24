package io.github.capturemathan.spotted;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;

public class SpottedActivity extends AppCompatActivity {
    Button spottedphoto, spottedsubmit;
    String spottedloc;
    final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotted);

        spottedphoto = findViewById(R.id.spotted_choosephotos);

        spottedphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        final PlacesAutocompleteTextView placesAutocomplete = findViewById(R.id.spottedplaces_autocomplete);
        placesAutocomplete.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {
                        // do something awesome with the selected place
                        placesAutocomplete.getDetailsFor(place, new DetailsCallback() {
                            @Override
                            public void onSuccess(PlaceDetails placeDetails) {
                                spottedloc = placeDetails.name;
                                Toast.makeText(getApplicationContext(), placeDetails.name
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
    }
}

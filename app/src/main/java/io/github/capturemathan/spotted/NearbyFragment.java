package io.github.capturemathan.spotted;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;

public class NearbyFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_nearby, null);
        final PlacesAutocompleteTextView placesAutocomplete = rootview.findViewById(R.id.places_autocomplete);
        placesAutocomplete.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {
                        // do something awesome with the selected place
                        placesAutocomplete.getDetailsFor(place, new DetailsCallback() {
                            @Override
                            public void onSuccess(PlaceDetails placeDetails) {
                                Toast.makeText(getContext(), placeDetails.toString()
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
}

package io.github.capturemathan.spotted;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class DetailsFragment extends Fragment {
    final int PICK_IMAGE_REQUEST = 1;
    Button choose_photo, upload_photo;
    Bitmap bitmap;
    Uri filePath;
    EditText name, age, phone, iden;
    String missingname, missingage, missingphone, missingiden;
    String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_details, null);

        choose_photo = rootview.findViewById(R.id.details_choosephotos);
        upload_photo = rootview.findViewById(R.id.details_uploadphotos);
        name = rootview.findViewById(R.id.details_name);
        age = rootview.findViewById(R.id.details_age);
        phone = rootview.findViewById(R.id.details_phone);
        iden = rootview.findViewById(R.id.details_identification);


        choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                missingname = name.getText().toString();
                missingage = age.getText().toString();
                missingphone = phone.getText().toString();
                missingiden = iden.getText().toString();
                new getresponse().execute();
            }
        });

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

            url = "http://192.168.43.191/missing.php?name=" + missingname + "&age=" + missingage + "&iden=" + missingiden + "&phone=" + missingphone;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            String path = getPathFromCameraData(data, this.getActivity());
            Log.i("PICTURE", "Path: " + path);
            Log.v("Filepath", filePath.toString());
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getPathFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


}

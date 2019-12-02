package usjt.com.aula06_sqlite_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class PlaceListActivity extends AppCompatActivity {

    private ListView locationsLV;
    private List<Place> list = new ArrayList<>();

    private List<Place> places;

    private PlaceDAO placeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_list);
        placeDAO = new PlaceDAO(this);
        locationsLV = findViewById(R.id.locationsListView);
        Intent origemIntent = getIntent();
        this.places = (ArrayList) origemIntent.getSerializableExtra("places");

        List<Place> places = placeDAO.search();
        PlaceArrayAdapter adapter =
                new PlaceArrayAdapter(this, places);
        locationsLV.setAdapter(adapter);
        locationsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String locaisList = locationsLV.getItemAtPosition(position).toString();
                String lat = locaisList.split(",")[0].split(":")[1];
                String lon = locaisList.split(",")[1].split(":")[1];

                Uri gmmIntentUri = Uri.parse(String.format("geo:%s,%s", lat, lon));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        Toast.makeText(this, "Salvo", Toast.LENGTH_LONG).show();
    }

}
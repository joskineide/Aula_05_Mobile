package usjt.com.aula06_sqlite_mobile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int REQUEST_CODE_GPS_PERMISSION = 1001;

    private Place place;
    private ArrayList<Place> places = new ArrayList<>();

    private EditText searchEditText;
    private TextView locationTextView;

    private double latitude;
    private double longitude;
    private String coodenate;

    private PlaceDAO placeDAO = new PlaceDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                latitude = lat;
                longitude = lon;
                coodenate = String.format("Lat: %f, Long: %f", lat, lon);
                System.out.println("\nVerificar coordenada do GPS: " + coodenate + "\n");

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        setContentView(R.layout.activity_main);

    }

    public void showList(List<Place> lo) {
        place = new Place();
        if(lo.size() >= 50) {
            lo.remove(0);
        }
        place.setLatitude(latitude);
        place.setLongitude(longitude);
        lo.add(place);

        placeDAO.insertLocation(place);

    }


    public void listPlaces(View view) {
        showList(places);
        Intent intent = new Intent(this, PlaceListActivity.class);
        intent.putExtra("places", places);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        //a permissão já foi dada?
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
            //somente ativa
            // a localização é obtida via hardware, intervalo de 0 segundos e 0 metros entre atualizações
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, locationListener);
        }
        else{
            //permissão ainda não foi nada, solicita ao usuário
            //quando o usuário responder, o método onRequestPermissionsResult vai ser chamado
            ActivityCompat.requestPermissions(this,
                    new String []{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_GPS_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull
            String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GPS_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //permissão concedida, ativamos o GPS
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            0, 0, locationListener);
                }
            }
            else{
                //usuário negou, não ativamos
                Toast.makeText(this,
                        getString(R.string.need_gps), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }
}

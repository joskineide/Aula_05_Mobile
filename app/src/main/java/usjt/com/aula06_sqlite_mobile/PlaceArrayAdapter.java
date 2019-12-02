package usjt.com.aula06_sqlite_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class PlaceArrayAdapter extends ArrayAdapter<Place> {

    private Context context;
    private List<Place> places;

    public PlaceArrayAdapter(Context context, List<Place> places) {
        super (context, -1, places);
        this.context = context;
        this.places = places;
    }

    @NonNull
    @Override
    public View getView (int position,
                         @Nullable View convertView,
                         @NonNull ViewGroup parent) {
        Place currentPlace = getItem(position);
        PlaceViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new PlaceViewHolder();
            viewHolder.latitudeTextView = convertView.findViewById(R.id.latitudeTextView);
            viewHolder.longitudeTextView = convertView .findViewById(R.id.longitudeTextView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (PlaceViewHolder) convertView.getTag();

        double latitude = currentPlace.getLatitude();
        double longitude = currentPlace.getLongitude();


        viewHolder.latitudeTextView.setText("Lat: " + latitude);
        viewHolder.longitudeTextView.setText("Lon: " + longitude);
        return convertView;

    }

    @Override
    public int getCount() {
        return places.size();
    }
}

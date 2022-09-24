package com.example.Colorful_Daegu.control;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.Colorful_Daegu.MainActivity;
import com.example.Colorful_Daegu.R;
import com.example.Colorful_Daegu.model.TouristSpot;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;


public class TouristSpotActivity extends AppCompatActivity {
    private ArrayList<TouristSpot> tours = new ArrayList<TouristSpot>();
    private ArrayList<String> tIds = new ArrayList<String>();  //필요없음
    private DatabaseReference ref;
    private HashMap<String,ArrayList<Integer>> stamps = new HashMap<String,ArrayList<Integer>>();
    private double longitude=0,latitude=0;

    private MapView.POIItemEventListener eventListener = new MapView.POIItemEventListener() {
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        System.out.println("click");
        Intent intent = new Intent(getApplicationContext(),TouristSpotDetailActivity.class);
        intent.putExtra("tid",String.valueOf(mapPOIItem.getTag()));
        startActivity(intent);
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tourist_spot);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        MapView mapView = new MapView(this);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_tourist_spot_balloon_layout, null);
        TextView tv = v.findViewById(R.id.text_des);
        tv.setMovementMethod(new ScrollingMovementMethod());

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
        mapView.setPOIItemEventListener(eventListener);

        // 중심점 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.8687847, 128.598467), true);


//         줌 레벨 변경
        mapView.setZoomLevel(6, true);

        ref = FirebaseDatabase.getInstance().getReference("Daegu/4/");
        ref.child("touristSpot").get().addOnCompleteListener(task ->{
            if(task.isSuccessful()){
                for(DataSnapshot snapshot: task.getResult().getChildren()){
                    tIds.add(snapshot.getKey());
                    tours.add(snapshot.getValue(TouristSpot.class));
                }
            }

            ref.child("stampState").child(user.getUid()).get().addOnCompleteListener(task1 -> {
                if(task1.isSuccessful()){
                    for(DataSnapshot snapshot : task1.getResult().getChildren()){
                        ArrayList<Integer> arr1 = new ArrayList<>();
                        for(DataSnapshot sn : snapshot.getChildren()){
                            arr1.add(sn.getValue(Integer.class));
                        }
                        stamps.put(snapshot.getKey(),arr1);
                    }

                    for(int i=0;i<tours.size();i++){
                        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(tours.get(i).getLocation().getLatitude(), tours.get(i).getLocation().getLongitude());

                        MapPOIItem marker = new MapPOIItem();
                        marker.setItemName(tours.get(i).getName());
                        marker.setTag(i);
                        marker.setMapPoint(MARKER_POINT);
                        marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                        mapView.addPOIItem(marker);
                    }
                }
            });


        });


        ExtendedFloatingActionButton rankFab = (ExtendedFloatingActionButton) findViewById(R.id.rank_fab);
        rankFab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),RankActivity.class);
            startActivity(intent);
        });

        FloatingActionButton locationFab = (FloatingActionButton) findViewById(R.id.location_fab);
        locationFab.setOnClickListener(view -> {
            final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if ( Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions( TouristSpotActivity.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                        0 );
            }else {
                Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);

                } else {
                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.8899242, 128.610697), true);
                    mapView.setZoomLevel(4, true);

                }
            }
            });



    }

    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;
        private ImageView imageView;
        private ImageView img_success;
        private TextView textDes;
        private ArrayList<Integer> stamp;
        private int achNum=0;
        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.item_tourist_spot_balloon_layout, null);
            imageView = mCalloutBalloon.findViewById(R.id.ball_img);
            img_success = mCalloutBalloon.findViewById(R.id.success_img);
            textDes = mCalloutBalloon.findViewById(R.id.text_des);
            textDes.setMovementMethod(new ScrollingMovementMethod());
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            stamp = stamps.get(Integer.toString(poiItem.getTag()));
            achNum=0;
            for(int i=0; i< stamp.size();i++){
                if(stamp.get(i)==1)
                    achNum++;
                System.out.println(achNum);
            }
            if(achNum==stamp.size()){
                img_success.setVisibility(View.VISIBLE);
            } else {
                img_success.setVisibility(View.INVISIBLE);
            }

            ((TextView) mCalloutBalloon.findViewById(R.id.text_name)).setText(tours.get(poiItem.getTag()).getName());
//            Glide.with(mCalloutBalloon).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRD6vcHKuBV0F2oiyO9M_8UoqnBlCIFkviLGQ&usqp=CAU").into((imageView));
//            Glide.with(mCalloutBalloon).load(R.drawable.spot_knu).into((imageView));

            ((ImageView) mCalloutBalloon.findViewById(R.id.ball_img)).setImageResource(R.drawable.spot_knu);
            ((TextView) mCalloutBalloon.findViewById(R.id.text_des)).setText(tours.get(poiItem.getTag()).getDescription());
            ((TextView) mCalloutBalloon.findViewById(R.id.rate_achievement)).setText(Integer.toString(achNum));
            ((TextView) mCalloutBalloon.findViewById(R.id.rating_tourist)).setText(String.valueOf(tours.get(poiItem.getTag()).getRating()));
            ((TextView) mCalloutBalloon.findViewById(R.id.rate)).setText(Integer.toString(stamp.size()));

            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return mCalloutBalloon;
        }
    }

}

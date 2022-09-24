package com.example.Colorful_Daegu.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.FloatLayout;

import com.bumptech.glide.Glide;
import com.example.Colorful_Daegu.R;
import com.example.Colorful_Daegu.model.StampState;
import com.example.Colorful_Daegu.model.StampState2;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class TouristSpotActivity extends AppCompatActivity {
    private ArrayList<TouristSpot> tours = new ArrayList<TouristSpot>();
    private ArrayList<String> tIds = new ArrayList<String>();  //필요없음
    private DatabaseReference ref;
    private HashMap<String,ArrayList<Integer>> stamps = new HashMap<String,ArrayList<Integer>>();
    private ArrayList<Integer> rateAch= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_spot);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        MapView mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());

        // 중심점 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.8899242, 128.610697), true);


//         줌 레벨 변경
        mapView.setZoomLevel(3, true);
        mapView.setZoomLevel(6, true);

        ref = FirebaseDatabase.getInstance().getReference("Daegu/0/");
        ref.child("touristSpot").get().addOnCompleteListener(task ->{
            if(task.isSuccessful()){
                for(DataSnapshot snapshot: task.getResult().getChildren()){
                    tIds.add(snapshot.getKey());
                    tours.add(snapshot.getValue(TouristSpot.class));
                }
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
        });
        ref.child("stampState").child("VxFq8m6PLTPx1cG9zhsz6O9lZsa2").get().addOnCompleteListener(task -> {  //todo: uId 경로로 지정하기
            if(task.isSuccessful()){
                ArrayList<Integer> arr1 = new ArrayList<>();
                for(DataSnapshot snapshot : task.getResult().getChildren()){
//                    arr1.add(snapshot.getValue());
//                    System.out.println(snapshot.getValue(Integer.class));
//                    arr1.add(snapshot.getValue(StampState2.class));
//                    stamps.put(snapshot.getKey(),snapshot.getValue(ArrayList<Integer>.class));
                    System.out.println(snapshot.getKey());
                    System.out.println(snapshot.getValue());
                    arr1.add(snapshot.getValue());
                }
                System.out.println(arr1);
//                System.out.println(stamps);
            }
        });







        ExtendedFloatingActionButton rankFab = (ExtendedFloatingActionButton) findViewById(R.id.rank_fab);
        rankFab.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(),TouristSpotActivity.class);
//            startActivity(intent);
            System.out.println("success_rank");
        });


    }

    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;
        private ImageView imageView;
        private ArrayList<Integer> stamp;
        private int achNum=0;
        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.balloon_layout, null);
            imageView = mCalloutBalloon.findViewById(R.id.ball_img);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
//            stamp = stamps.get(Integer.toString(poiItem.getTag())).getStampState();
//
//            for(int i=0; i< stamp.size();i++){
//                if(stamp.get(i)==1)
//                    achNum++;
//            }
            ((TextView) mCalloutBalloon.findViewById(R.id.text_name)).setText(tours.get(poiItem.getTag()).getName());
//            Glide.with(mCalloutBalloon).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRD6vcHKuBV0F2oiyO9M_8UoqnBlCIFkviLGQ&usqp=CAU").into((imageView));
//            Glide.with(mCalloutBalloon).load(R.drawable.spot_knu).into((imageView));

            ((ImageView) mCalloutBalloon.findViewById(R.id.ball_img)).setImageResource(R.drawable.spot_knu);
            ((TextView) mCalloutBalloon.findViewById(R.id.text_des)).setText(tours.get(poiItem.getTag()).getDescription());
//            ((TextView) mCalloutBalloon.findViewById(R.id.rate_achievement)).setText(achNum);
            ((TextView) mCalloutBalloon.findViewById(R.id.rating_tourist)).setText(tours.get(poiItem.getTag()).getRating());
//            ((TextView) mCalloutBalloon.findViewById(R.id.rate)).setText(stamps.size());
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return mCalloutBalloon;
        }
    }


}

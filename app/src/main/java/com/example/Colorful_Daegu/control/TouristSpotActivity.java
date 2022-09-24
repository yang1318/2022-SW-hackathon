package com.example.Colorful_Daegu.control;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Colorful_Daegu.R;
import com.example.Colorful_Daegu.model.TouristSpot;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
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
import java.util.HashMap;


public class TouristSpotActivity extends AppCompatActivity {
    private ArrayList<TouristSpot> tours = new ArrayList<TouristSpot>();
    private ArrayList<String> tIds = new ArrayList<String>();  //필요없음
    private DatabaseReference ref;
    private HashMap<String,ArrayList<Integer>> stamps = new HashMap<String,ArrayList<Integer>>();
//    private MapView mapView = new MapView(this);
//    private  eventListener = new MarkerClickEvent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tourist_spot);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        MapView mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
//        mapView.setPOIItemEventListener(eventListener);

        // 중심점 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(35.8899242, 128.610697), true);


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

            ref.child("stampState").child("VxFq8m6PLTPx1cG9zhsz6O9lZsa2").get().addOnCompleteListener(task1 -> {  //todo: uId 경로로 지정하기
                if(task1.isSuccessful()){
                    for(DataSnapshot snapshot : task1.getResult().getChildren()){
                        ArrayList<Integer> arr1 = new ArrayList<>();
                        for(DataSnapshot sn : snapshot.getChildren()){
                            arr1.add(sn.getValue(Integer.class));
                        }
                        stamps.put(snapshot.getKey(),arr1);
                    }
                    System.out.println(stamps);

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

//        mapView.setPOIItemEventListener(new MapView.POIItemEventListener() {
//            @Override
//            public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
//                System.out.println("ahffk");
//            }
//
//            @Override
//            public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
//                System.out.println("clickcl");
//            }
//
//            @Override
//            public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
//                System.out.println("click");
//                Intent intent = new Intent(getApplicationContext(),TouristSpotActivity.class);
//                intent.putExtra("tid",mapPOIItem.getTag());
//                startActivity(intent);
//            }
//
//            @Override
//            public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
//
//            }
//        });



        ExtendedFloatingActionButton rankFab = (ExtendedFloatingActionButton) findViewById(R.id.rank_fab);
        rankFab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),RankActivity.class);
            startActivity(intent);
//            System.out.println("success_rank");
        });


    }

//    @Override
//    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
//
//    }
//
//    @Override
//    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
//
//    }
//
//    @Override
//    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
//        System.out.println("click");
//        Intent intent = new Intent(getApplicationContext(),TouristSpotActivity.class);
//        intent.putExtra("tid",mapPOIItem.getTag());
//        startActivity(intent);
//    }
//
//    @Override
//    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {



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

        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            textDes.setMovementMethod(new ScrollingMovementMethod());
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
            System.out.println("clickclick");
            return mCalloutBalloon;
        }
    }

//    class MarkerClickEvent implements MapView.POIItemEventListener{
//
//        @Override
//        public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
//
//        }
//
//        @Override
//        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
//
//        }
//
//        @Override
//        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
//            System.out.println("click");
//            Intent intent = new Intent(getApplicationContext(),TouristSpotActivity.class);
//            intent.putExtra("tid",mapPOIItem.getTag());
//            startActivity(intent);
//        }
//
//        @Override
//        public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
//
//        }
//    }

}

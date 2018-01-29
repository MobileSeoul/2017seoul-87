package twojeong.dreammoa.location;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import twojeong.dreammoa.R;
import twojeong.dreammoa.item.Store;
import twojeong.dreammoa.main.MainActivity;
import twojeong.dreammoa.search.detailSearchActivity;

import static android.Manifest.*;
import static twojeong.dreammoa.main.MainActivity.dreamMoaDB;

public class MyLocationSearchActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LinearLayout linearLayout;
    private ImageButton homeBtn;
    LatLng Location;
    LocationManager locationManager;
    private Spinner addressSpinner;
    private Spinner typeSpinner;
    private Button searchBtn;
    private ImageButton telBtn;
    private TextView dName, dAddress, dTel;
    private int addressNum=0,typeNum=0;
    double mLatitude;  //위도
    double mLongitude; //경도

    ArrayList<Store> dataList;
    Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylocationsearch);

        // SupportMapFragment을 통해 레이아웃에 만든 fragment의 ID를 참조하고 구글맵을 호출한다.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); // getMapAsync must be called on the main thread;
        init();
    }

    private void init() {
        dName = (TextView)findViewById(R.id.dName);
        dAddress = (TextView)findViewById(R.id.dAddress);
        dTel = (TextView)findViewById(R.id.dTel);
        telBtn = (ImageButton) findViewById(R.id.telBtn);

        searchBtn = (Button) findViewById(R.id.searchBtn);
        addressSpinner = (Spinner) findViewById(R.id.addressSpinner);
        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                finish();

            }
        });
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout.setVisibility(View.GONE);
        //스피너 어댑터 설정
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.address,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.type,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //어뎁터 연결
        addressSpinner.setAdapter(adapter1);
        typeSpinner.setAdapter(adapter2);

        //스피너 이벤트
        addressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addressNum = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {            }
        });
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeNum = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {            }
        });
        telBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 사용자의 OS 버전이 마시멜로우 이상인지 체크한다. */
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {

                    /* 사용자 단말기의 권한 중 "전화걸기" 권한이 허용되어 있는지 체크한다.

                    */
                    int permissionResult = checkSelfPermission(Manifest.permission.CALL_PHONE);

                    /* CALL_PHONE의 권한이 없을 때 */
                    // 패키지는 안드로이드 어플리케이션의 아이디다.( 어플리케이션 구분자 )
                    if (permissionResult == PackageManager.PERMISSION_DENIED) {


                        /* 사용자가 CALL_PHONE 권한을 한번이라도 거부한 적이 있는 지 조사한다.
                        * 거부한 이력이 한번이라도 있다면, true를 리턴한다.
                        */
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {

                            AlertDialog.Builder dialog = new AlertDialog.Builder(MyLocationSearchActivity.this);
                            dialog.setTitle("권한이 필요합니다.")
                                    .setMessage("이 기능을 사용하기 위해서는 단말기의 \"전화걸기\" 권한이 필요합니다. 계속하시겠습니까?")
                                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                            }

                                        }
                                    })
                                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(MyLocationSearchActivity.this, "기능을 취소했습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .create()
                                    .show();
                        }

                        //최초로 권한을 요청할 때
                        else {
                            // CALL_PHONE 권한을 Android OS 에 요청한다.
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                        }

                    }
                    /* CALL_PHONE의 권한이 있을 때 */
                    else {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+dTel.getText()));
                        startActivity(intent);
                    }

                }
                /* 사용자의 OS 버전이 마시멜로우 이하일 떄 */
                else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+dTel.getText()));
                    startActivity(intent);
                }

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                if (addressNum==0) {
                    Toast.makeText(getApplication(),"자치구를 선택해주세요.",Toast.LENGTH_LONG).show();
                    return;
                }
                dataList = dreamMoaDB.storeListSelect(addressNum,typeNum);
                Geocoder mCoder = new Geocoder(getApplicationContext());

                List<Address> addressList;
                LatLng newLatLng;
                Double Lat=37.52487;
                Double Lon=126.92723;
                for (int i = 0; i < dataList.size(); i++) {
                    Store store = dataList.get(i);
                    String storeAddress = store.getAddress1() +" "+store.getAddress2();

                    try {
                        //주소값을 통하여 로케일을 받아온다
                        addressList = mCoder.getFromLocationName(storeAddress, 1);
                        Lat = addressList.get(0).getLatitude();
                        Lon = addressList.get(0).getLongitude();
                        Lat = Double.parseDouble(String.format("%.5f", Lat));
                        Lon = Double.parseDouble(String.format("%.5f", Lon));
                        //해당 로케일로 좌표를 구성한다
                        newLatLng = new LatLng(Lat, Lon);
                        MarkerOptions makerOptions = new MarkerOptions();
                        makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                                .position(newLatLng)
                                .title(i+"")
                                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.map_pointer)));

                        mMap.addMarker(makerOptions).showInfoWindow();
                    } catch (Exception e) {

                    }
                }
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        linearLayout.setVisibility(View.GONE);
                    }
                });
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        linearLayout.setVisibility(View.VISIBLE);
                        int i = Integer.parseInt(marker.getTitle());
                        Store store = dataList.get(i);
                        dName.setText(store.getName());
                        dAddress.setText(store.getAddress1()+" "+store.getAddress2()+" "+store.getAddress3());
                        dTel.setText(store.getTel());
                        return true;
                    }
                });

            }
        });

        //LocationManager
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        //GPS가 켜져있는지 체크
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //GPS 설정화면으로 이동
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);

            finish();
        }

        //마시멜로 이상이면 권한 요청하기
        if(Build.VERSION.SDK_INT >= 23){
            //권한이 없는 경우
            if((ContextCompat.checkSelfPermission(MyLocationSearchActivity.this, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                    (ContextCompat.checkSelfPermission(MyLocationSearchActivity.this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
                ActivityCompat.requestPermissions(MyLocationSearchActivity.this, new String[]{permission.ACCESS_COARSE_LOCATION , permission.ACCESS_FINE_LOCATION} , 1);
            }
            //권한이 있는 경우
            else{
                requestMyLocation();
            }
        }
        //마시멜로 아래
        else{
            requestMyLocation();
        }

    }

    //권한 요청후 응답 콜백
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //ACCESS_COARSE_LOCATION 권한
        if(requestCode==1){
            //권한받음
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                requestMyLocation();
            }
            //권한못받음
            else{
                Toast.makeText(this, "권한없음", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    public void requestMyLocation(){
        if(ContextCompat.checkSelfPermission(MyLocationSearchActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MyLocationSearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        //요청
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, locationListener);
    }
    //위치정보 구하기 리스너
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(ContextCompat.checkSelfPermission(MyLocationSearchActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(MyLocationSearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }
            //나의 위치를 한번만 가져오기 위해
            locationManager.removeUpdates(locationListener);

            //위도 경도
            mLatitude = location.getLatitude();   //위도
            mLongitude = location.getLongitude(); //경도
            Toast.makeText(getApplicationContext(),mLatitude+", "+mLongitude,Toast.LENGTH_LONG).show();

            //맵생성
            SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
            //콜백클래스 설정
            mapFragment.getMapAsync(MyLocationSearchActivity.this);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { Log.d("gps", "onStatusChanged"); }

        @Override
        public void onProviderEnabled(String provider) { }

        @Override
        public void onProviderDisabled(String provider) { }
    };



    @Override
    public void onMapReady(GoogleMap googleMap) {
        // 구글 맵 객체를 불러온다.
        mMap = googleMap;
        //지도타입 - 일반
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //나의 위치 설정
        LatLng position = new LatLng(37.566235, 126.978019);

        //화면중앙의 위치와 카메라 줌비율
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 13));

    }
}

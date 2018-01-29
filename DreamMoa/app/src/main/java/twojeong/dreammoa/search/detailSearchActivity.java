package twojeong.dreammoa.search;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import java.util.List;

import twojeong.dreammoa.R;
import twojeong.dreammoa.main.MainActivity;

public class detailSearchActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ImageButton backBtn;
    List<Address> addressList;
    LatLng newLatLng;
    TextView dName, dAddress, dTel;
    String storeName, storeAddress, storeEtcAddress, storeTel;
    private ImageButton telBtn;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsearch);

        Intent intent = getIntent();
        storeName = intent.getStringExtra("name");
        storeAddress = intent.getStringExtra("address");
        storeEtcAddress = intent.getStringExtra("etcAddress");
        storeTel = intent.getStringExtra("tel");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); //getMapAsync must be called on the main thread
        init();
    }

    private void init() {
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        dName = (TextView)findViewById(R.id.dName);
        dAddress = (TextView)findViewById(R.id.dAddress);
        dTel = (TextView)findViewById(R.id.dTel);
        dName.setText(storeName);
        dAddress.setText(storeAddress+" "+storeEtcAddress);
        dTel.setText(storeTel);

        backBtn = (ImageButton) findViewById(R.id.backbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        telBtn = (ImageButton) findViewById(R.id.telBtn);
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

                            AlertDialog.Builder dialog = new AlertDialog.Builder(detailSearchActivity.this);
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
                                            Toast.makeText(detailSearchActivity.this, "기능을 취소했습니다.", Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-2222"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                }
                /* 사용자의 OS 버전이 마시멜로우 이하일 떄 */
                else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+storeTel));
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        });
    }

    /**
     * 사용자가 권한을 허용했는지 거부했는지 체크
     * @param requestCode   1000번
     * @param permissions   개발자가 요청한 권한들
     * @param grantResults  권한에 대한 응답들
     *                    permissions와 grantResults는 인덱스 별로 매칭된다.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {

            /* 요청한 권한을 사용자가 "허용"했다면 인텐트를 띄워라
                내가 요청한 게 하나밖에 없기 때문에. 원래 같으면 for문을 돈다.*/
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1111-2222"));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                }
            }
            else {
                Toast.makeText(detailSearchActivity.this, "권한 요청을 거부했습니다.", Toast.LENGTH_SHORT).show();
            }

        }



        backBtn = (ImageButton) findViewById(R.id.backbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // 구글 맵 객체를 불러온다.

        mMap = googleMap;
        Geocoder mCoder = new Geocoder(this);

        try {
            //주소값을 통하여 로케일을 받아온다
            addressList = mCoder.getFromLocationName(storeAddress, 1);
            Double Lat = addressList.get(0).getLatitude();
            Double Lon = addressList.get(0).getLongitude();
            Lat = Double.parseDouble(String.format("%.5f", Lat));
            Lon = Double.parseDouble(String.format("%.5f", Lon));
            //해당 로케일로 좌표를 구성한다
            newLatLng = new LatLng(Lat, Lon);
        } catch (Exception e) {

        }


        CameraPosition cp = new CameraPosition.Builder().target((newLatLng)).zoom(17).build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));



        MarkerOptions marker = new MarkerOptions();
        marker.position(newLatLng)
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.map_pointer)));
        mMap.addMarker(marker).showInfoWindow(); // 마커추가,화면에출력

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
                return true;
            }
        });
    }
}

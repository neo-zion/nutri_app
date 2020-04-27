package com.example.my33_navigationdrawer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.my33_navigationdrawer.DTO.SearchDTO.IList;
import com.example.my33_navigationdrawer.DTO.SearchDTO.MList;
import com.example.my33_navigationdrawer.DTO.SearchDTO.SearchDTO;
import com.example.my33_navigationdrawer.api.RestMethods;
import com.example.my33_navigationdrawer.retrofit2.RestClient;
import com.example.my33_navigationdrawer.utils.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.my33_navigationdrawer.utils.CommonMethod.isNetworkConnected;


public class MainActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "MainActivity2";
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavi;
    private DrawerLayout drawer;
    private View headerView;
    private Toolbar toolbar;
    private FragmentNotice fragmentNotice;
    private FragmentHome fragmentHome;
    private FragmentSDiary fragmentSDiary;
    private FragmentQuery fragmentQuery;
    private FragmentSearchMain fragmentSearchMain;
    private FragmentMarket fragmentMarket;
    private FragmentSettings fragmentSettings;
    private FragmentNutrient fragmentNutrient;
    private FragmentStreamView fragmentStreamView;
    private FragmentReminder fragmentReminder;
    private List<IList> iList;
    private List<MList> mList;
    private Button nav_logout, searchBtn, nav_settings, nav_home;
    private Bundle bundle;
    private EditText etSearch;
    private TextView navLoginID;
    private RestMethods restMethods;
    private ProgressDialog progressDialog;
    private AlertDialog dialog;
    private String image_path;
    private ImageView headerImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        setContent();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        String nickname = PreferenceManager.getString(MainActivity2.this, "nickname");
        image_path = PreferenceManager.getString(MainActivity2.this, "image_path");
        headerView = navigationView.getHeaderView(0);
        navLoginID = headerView.findViewById(R.id.loginID);
        Toast.makeText(this, nickname+"님 환영합니다.", Toast.LENGTH_SHORT).show();
        navLoginID.setText("반갑습니다 " + nickname + "님");
        headerImage = headerView.findViewById(R.id.imageView);
        Log.d(TAG, "image_path: " + image_path);
        if(image_path != null && !image_path.isEmpty()){
            Glide.with(MainActivity2.this).load(image_path).into(headerImage);
        }

        // 이미지 변경시 네비게이션 드로워 헤더 이미지 드로워 열고 닫을 때 바뀜.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            // 열 때
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                String newImage_path = PreferenceManager.getString(MainActivity2.this, "image_path");
                if (newImage_path.length() != 0) {
                    Glide.with(MainActivity2.this).load(newImage_path).into(headerImage);
                }
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            // 닫을 때
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                String newImage_path = PreferenceManager.getString(MainActivity2.this, "image_path");
//                if (newImage_path.length() != 0) {
//                    Glide.with(MainActivity2.this).load(newImage_path).into(headerImage);
//                }
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);





        // 드로어에 로그인 정보 표시하기




        // 네비게이션 드로워 홈버튼
        nav_home = headerView.findViewById(R.id.nav_home);
        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragmentHome);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                drawer.closeDrawer(GravityCompat.START);
            }
        });


        // 네비게이션 드로워 로그아웃 버튼  // 로그아웃 소스 sharedPreference
        nav_logout = headerView.findViewById(R.id.nav_logout);
        nav_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PreferenceManager.clear(MainActivity2.this);
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity2.this, "로그아웃되셨습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



        //네비게이션 드로워 설정버튼
        nav_settings = headerView.findViewById(R.id.nav_settings);
        nav_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragmentSettings);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                drawer.closeDrawer(GravityCompat.START);

            }
        });



        // 첫 화면 지정
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragmentHome).commitAllowingStateLoss();
        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록

        //editText 키보드에서 엔터키로 검색 구현
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchAction();
                    return true;
                }
                return false;
            }
        });

        //검색버튼 검색 구현
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAction();

            }
        });

        // 바닥내비 버튼 클릭
        bottomNavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager fragmentManager =getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.previous: {
                        Toast.makeText(MainActivity2.this, "이전메뉴", Toast.LENGTH_SHORT).show();
                        fragmentManager.popBackStack();
                        break;
                    }
                    case R.id.refresh: { // 새로고침
                        Toast.makeText(MainActivity2.this, "새로고침", Toast.LENGTH_SHORT).show();
                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container); //현재 프래그먼트 불러옴
                        transaction.detach(fragment).attach(fragment).commit();
                        break;
                    }
                    case R.id.home: {
                        Toast.makeText(MainActivity2.this, "홈", Toast.LENGTH_SHORT).show();
                        transaction.replace(R.id.container, fragmentHome);
                        transaction.addToBackStack(null);
                        transaction.commitAllowingStateLoss();
                        break;
                    }
                    case R.id.video: {
                        Toast.makeText(MainActivity2.this, "건강동영상", Toast.LENGTH_SHORT).show();
                        transaction.replace(R.id.container, fragmentStreamView);
                        transaction.addToBackStack(null);
                        transaction.commitAllowingStateLoss();
                        break;
                    }
                    case R.id.notice: {
                        Toast.makeText(MainActivity2.this, "공지사항", Toast.LENGTH_SHORT).show();
                        transaction.replace(R.id.container, fragmentNotice);
                        transaction.addToBackStack(null);
                        transaction.commitAllowingStateLoss();
                        break;
                    }
                }
                return true;
            }
        });
    }

    // 변수들 레이아웃과 연결하기
    private void setContent() {
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        bottomNavi =findViewById(R.id.bottomNavi);
        searchBtn = findViewById(R.id.search_button);
        etSearch = findViewById(R.id.etSearch);

        fragmentNotice = new FragmentNotice();
        fragmentHome = new FragmentHome();
        fragmentQuery = new FragmentQuery();
        fragmentMarket = new FragmentMarket();
        fragmentSearchMain = new FragmentSearchMain();
        fragmentSettings = new FragmentSettings();
        fragmentSDiary = new FragmentSDiary();
        fragmentNutrient = new FragmentNutrient();
        fragmentStreamView = new FragmentStreamView();
        fragmentReminder = new FragmentReminder();

    }



    //검색 구현 메서드
    private void searchAction() {
        if(isNetworkConnected(MainActivity2.this) == true) {
            final String search = etSearch.getText().toString().trim();
            if (search.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                dialog = builder.setMessage("검색어를 입력하시기 바랍니다.")
                        .setPositiveButton("OK", null)
                        .create();
                dialog.show();
                return;
            }

            Log.d(TAG, "검색어는======>" + search);

            // 검색 버튼 클릭 후 키보드 내리는 코드
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);

            bundle = new Bundle();


            //Builds HTTP Client for API Calls
            restMethods = RestClient.buildHTTPClient();

            // Set up progress before call 프로그레스 다이알로그
            progressDialog = new ProgressDialog(MainActivity2.this);
            // 프로그레스 다이알로그
            //progressDialog.setMax(100);
            //프로그레스 다이알로그 메세지 설정
            progressDialog.setMessage("로딩중입니다...");
            //progressDialog.setTitle("ProgressDialog bar ");
            // 프로그레스다이알로그 취소여부 결정
            //progressDialog.setCancelable(true);
            //프로그레스다이알로그 스타일 설정
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
            // show it
            progressDialog.show();

            restMethods.search(search).enqueue(new Callback<SearchDTO>() {
                @Override
                public void onResponse(Call<SearchDTO> call, Response<SearchDTO> response) {
                    if (response.isSuccessful()) {
                        SearchDTO searchDTO = response.body();
                        iList = searchDTO.getIList();
                        mList = searchDTO.getMList();

                        bundle.putSerializable("iList", (Serializable) iList);
                        bundle.putSerializable("mList", (Serializable) mList);
                        bundle.putString("search", search);  //번들에 담기 검색값
                        etSearch.getText().clear();
                        fragmentSearchMain.setArguments(bundle);
                        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container); //현재 보이는 프래그먼트를 찾아줌
                        // close it after response
                        progressDialog.dismiss();
                        Log.v(TAG, "Yes!");

                        //분기: 만약 현재 프래그먼트가 검색결과가 보이는 fragmentsearchmain의 것이라면 새로고침
                        if (fragment instanceof FragmentSearchMain) {
                            FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                            transaction1.detach(fragment).attach(fragment);
                            transaction1.addToBackStack(null);
                            transaction1.commitAllowingStateLoss();
                        } else {
                            FragmentTransaction transaction2 = fragmentManager.beginTransaction();
                            transaction2.replace(R.id.container, fragmentSearchMain);
                            transaction2.addToBackStack(null);
                            transaction2.commitAllowingStateLoss();
                        }


                    }

                }

                @Override
                public void onFailure(Call<SearchDTO> call, Throwable t) {
                    // close it after response
                    progressDialog.dismiss();
//                Log.v(TAG, "No Response!");
                    t.printStackTrace();
//                //Response failed
                    Log.e(TAG, "Response: " + t.getMessage());
                }
            });
        }else {
            Toast.makeText(MainActivity2.this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //네비게이션 드로워 메뉴 선택 1
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == R.id.nav_dailyIntake){
            Toast.makeText(this, "일일섭취량 선택", Toast.LENGTH_SHORT).show();
            onFragmentSelected(0, null);
        }else if(id == R.id.nav_stretchReminder){
            Toast.makeText(this, "스트레칭 리마인더 선택", Toast.LENGTH_SHORT).show();
            onFragmentSelected(1, null);
        }else if(id == R.id.nav_sDiary){
            Toast.makeText(this, "S-Diary선택", Toast.LENGTH_SHORT).show();
            onFragmentSelected(2, null);
        } else if(id == R.id.nav_streamView){
            Toast.makeText(this, "건강 동영상 선택", Toast.LENGTH_SHORT).show();
            onFragmentSelected(3, null);
        } else if(id == R.id.nav_query){
            Toast.makeText(this, "1:1문의 선택", Toast.LENGTH_SHORT).show();
            onFragmentSelected(4, null);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //네비게이션 드로워 메뉴 선택 2
    public void onFragmentSelected(int position, Bundle bundle){
        Fragment curFragment = null;
        if(position == 0) {
            curFragment = fragmentNutrient;
        } else if(position == 1) {
            curFragment = fragmentReminder;
        }else if(position == 2) {
            curFragment = fragmentSDiary;
        } else if(position == 3) {
            curFragment = fragmentStreamView;
        } else if(position == 4) {
            curFragment = fragmentQuery;
        }
        FragmentTransaction transaction3 = fragmentManager.beginTransaction();
        transaction3.replace(R.id.container, curFragment);
        transaction3.addToBackStack(null);
        transaction3.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
        fragmentManager.popBackStack();
    }
    //프레그먼트 교체 작업용
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }


}

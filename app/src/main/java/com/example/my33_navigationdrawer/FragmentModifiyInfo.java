package com.example.my33_navigationdrawer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.my33_navigationdrawer.api.RestMethods;
import com.example.my33_navigationdrawer.retrofit2.RestClient;
import com.example.my33_navigationdrawer.utils.CommonMethod;
import com.example.my33_navigationdrawer.utils.PreferenceManager;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.my33_navigationdrawer.retrofit2.RestClient.BASE_URL;
import static com.example.my33_navigationdrawer.utils.CommonMethod.isNetworkConnected;


public class FragmentModifiyInfo extends Fragment {

    private static final String TAG = "FragmentModifiyInfo";
    // Random code that identifies the result of the picker

    final int CAMERA_REQUEST = 1000;
    final int LOAD_IMAGE = 1001;


    File file = null;
    long fileSize = 0;

    public String imageRealPathA, imageDbPathA;


    private ProgressDialog progressDialog;
    private RestMethods restMethods;
    private Button btnBack, btnPhoto, btnLoad, btn_Modify, btn_Mypage;
    private Context mContext;
    private ImageView imageView;
    private FragmentManager fragmentManager;
    private String url;
    private FragmentTransaction transaction;
    private FragmentMypage fragmentMypage;
    java.text.SimpleDateFormat tmpDateFormat;


    public static FragmentModifiyInfo newInstance(Bundle bundle) {
        FragmentModifiyInfo fragmentModifiyInfo = new FragmentModifiyInfo();
        fragmentModifiyInfo.setArguments(bundle);
        return fragmentModifiyInfo;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_modifyinfo,
                container, false);


        tmpDateFormat = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");

        btnBack = rootView.findViewById(R.id.modifyBack);
        btnPhoto = rootView.findViewById(R.id.btnPhoto);
        btnLoad = rootView.findViewById(R.id.btnLoad);
        btn_Modify = rootView.findViewById(R.id.btn_Modify);
        btn_Mypage = rootView.findViewById(R.id.btn_Mypage);
        imageView = rootView.findViewById(R.id.imageView);
        fragmentMypage = new FragmentMypage();
        fragmentManager = getParentFragmentManager();
        mContext = getContext();
        //Builds HTTP Client for API Calls
        restMethods = RestClient.buildHTTPClient();


        url = PreferenceManager.getString(mContext,"image_path");
        Log.d(TAG, "url    " + url);
        if(url != null && !url.isEmpty()){
            Glide.with(mContext).load(url).into(imageView);
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

        btn_Mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragmentMypage);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    file = createFile();
                    Log.d("FilePath ", file.getAbsolutePath());

                }catch(Exception e){
                    Log.d("Sub1Add:filepath", "Something Wrong", e);
                }

                imageView.setVisibility(View.VISIBLE);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // API24 이상 부터
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            FileProvider.getUriForFile(mContext,
                                    mContext.getPackageName() + ".fileprovider", file));
                    Log.d("sub1:appId", mContext.getPackageName());
                }else {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                }

                if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                    startActivityForResult(intent, CAMERA_REQUEST);
                }

            }
        });


        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPicker();

            }
        });

        btn_Modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageRealPathA != null) {
                // Set up progress before call 프로그레스 다이알로그
                progressDialog = new ProgressDialog(mContext);
                // 프로그레스 다이알로그
                //progressDoalog.setMax(100);

                //프로그레스 다이알로그 메세지 설정
                progressDialog.setMessage("로딩중입니다...");

                //progressDoalog.setTitle("ProgressDialog bar ");

                // 프로그레스다이알로그 취소여부 결정
                //progressDialog.setCancelable(true);

                //프로그레스다이알로그 스타일 설정
                progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
                // show it
                progressDialog.show();
                final String nickname1 = PreferenceManager.getString(mContext, "nickname");
                if(isNetworkConnected(mContext) == true){
                        if(fileSize <= 30000000){  // 파일크기가 30메가 보다 작아야 업로드 할수 있음
                            //파일 생성
                            //img_url은 이미지의 경로
                            Log.d(TAG, "변경 버튼 클릭 : " + imageRealPathA);
                            File file = new File(imageRealPathA);
                            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                            MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);
                            RequestBody imageDbPath = RequestBody.create(MediaType.parse("text/plain"), imageDbPathA);
                            RequestBody nickname = RequestBody.create(MediaType.parse("text/plain"), nickname1);

                            restMethods.modifyMember(nickname,imageDbPath,body).enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                        if (response.isSuccessful()) {
                                            // close it after response
                                            progressDialog.dismiss();
                                            if (response.body()==1) {
                                                Log.v(TAG, "정보 수정 성공 ");

                                                PreferenceManager.setString(mContext,"image_path", imageDbPathA);
                                                Log.d(TAG, "imageDbPathA: " + imageDbPathA);

                                                Toast.makeText(mContext, "사진이 변경되었습니다.", Toast.LENGTH_LONG).show();
                                                btn_Modify.setVisibility(View.GONE);
                                                btnBack.setVisibility(View.INVISIBLE);
                                                btnPhoto.setVisibility(View.INVISIBLE);
                                                btnLoad.setVisibility(View.INVISIBLE);


                                                btn_Mypage.setVisibility(View.VISIBLE);

                                            } else {
                                                Log.v(TAG, "정보 수정 실패 ");
                                            }

                                        } else {
                                            // close it after response
                                            progressDialog.dismiss();
                                            Log.v(TAG, "통신 불량!");
                                        }
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    // close it after response
                                    progressDialog.dismiss();
                                    Log.v(TAG, "통신 실패!");

                                    t.printStackTrace();
                                    //Response failed
                                    Log.e(TAG, "Response: " + t.getMessage());
                                }
                            });
                        }else{
                            // 알림창 띄움
                            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("알림");
                            builder.setMessage("파일 크기가 30MB초과하는 파일은 업로드가 제한되어 있습니다.\n30MB이하 파일로 선택해 주십시요!!!");
                            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.show();
                        }

                    }else {
                        Toast.makeText(mContext, "인터넷이 연결되어 있지 않습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                }   else { // imageRealPath 가 null, 사진 변경이 없을 경우.
                    fragmentManager = getParentFragmentManager();
                    fragmentManager.popBackStack();
                }

        }


        });


        return rootView;
    }


    private File createFile() throws IOException {

        String imageFileName = "My" + tmpDateFormat.format(new Date()) + ".jpg";
        File storageDir = Environment.getExternalStorageDirectory();
        File curFile = new File(storageDir, imageFileName);

        return curFile;
    }

    /**
     * Method that displays the image/video chooser.
     */
    public void ShowPicker()
    {
        imageView.setVisibility(View.VISIBLE);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), LOAD_IMAGE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            try {
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(file.getAbsolutePath());
                if(newBitmap != null){
                    imageView.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(mContext, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPathA = file.getAbsolutePath();
                String uploadFileName = imageRealPathA.split("/")[imageRealPathA.split("/").length - 1];
                imageDbPathA = BASE_URL + "resources/fileUpload/img/" + uploadFileName;

            } catch (Exception e){
                e.printStackTrace();
            }
        }else if (requestCode == LOAD_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                String path = "";
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    // Get the path from the Uri
                    path = getPathFromURI(selectedImageUri);
                    Log.d(TAG, "파일 실제 경로:==== " + path);
                }
                // 이미지 돌리기 및 리사이즈
                Bitmap newBitmap = CommonMethod.imageRotateAndResize(path);
                if(newBitmap != null){
                    imageView.setImageBitmap(newBitmap);
                }else{
                    Toast.makeText(mContext, "이미지가 null 입니다...", Toast.LENGTH_SHORT).show();
                }

                imageRealPathA = path;
                Log.d("Sub1Add", "imageFilePathA Path : " + imageRealPathA);
                String uploadFileName = imageRealPathA.split("/")[imageRealPathA.split("/").length - 1];

                imageDbPathA = BASE_URL + "resources/fileUpload/img/" + uploadFileName;

            } catch (Exception e){
                e.printStackTrace();
            }


        }

    }

    // Get the real path from the URI
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }




}
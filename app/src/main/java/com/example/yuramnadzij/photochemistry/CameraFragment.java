package com.example.yuramnadzij.photochemistry;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CameraFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CameraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SurfaceView cameraView;
    private TextView textView;
    private CameraSource cameraSource;
    private ViewfinderView vfw;
    private RelativeLayout relativeLayout;
    private Button button;

    final int RequestCameraPermissionID = 1001;

    private OnFragmentInteractionListener mListener;

    public CameraFragment() {
        // Required empty public constructor
    }


    public static CameraFragment newInstance(String param1, String param2) {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.fragment_camera, container, false);
        cameraView = (SurfaceView) vw.findViewById(R.id.surfaceView);
        textView = (TextView) vw.findViewById(R.id.text_view);
        relativeLayout = (RelativeLayout) vw.findViewById(R.id.relativeLayout);
        vfw = (ViewfinderView) vw.findViewById(R.id.vfw);
        button = vw.findViewById(R.id.btn_fragment_res);

        vfw.setOnTouchListener(vfw);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getActivity().getApplicationContext()).build();
        BoxDetector boxDetector = new BoxDetector(textRecognizer, vfw.getFramingRect());
        //boxDetector.setProcessor(textRecognizer.setProcessor());
        if(!textRecognizer.isOperational()){
            Log.w("MainActivity", "Detector not loaded yet");
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            int width = display.getWidth();
            int height = display.getHeight();
            cameraSource = new CameraSource.Builder(getActivity().getApplicationContext(), boxDetector)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 720)
                    //.setRequestedPreviewSize(640, 480)
                    //.setRequestedPreviewSize(width, height)
                    .setAutoFocusEnabled(true)
                    .setRequestedFps(30.0f)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback(){
                @Override
                public void surfaceCreated(SurfaceHolder holder){
                    try{
                        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

                @Override
                public void surfaceDestroyed(SurfaceHolder holder){
                    cameraSource.stop();
                }
            });

            boxDetector.setProcessor(new Detector.Processor<TextBlock>(){
                @Override
                public void release(){}

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections){
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if(items.size() != 0){

                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for(int i = 0; i < items.size(); i++){
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }

                                textView.setText(stringBuilder.toString());
                            }
                        });
                    }
                }
            });
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setFragmentToShow(2);
            }
        });
        return vw;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(((MainActivity)getActivity()).getIsVisible()){
            Toast.makeText(getActivity(), "Cursor position 1 / ", Toast.LENGTH_LONG).show();
            button.setVisibility(View.VISIBLE);
            button.setText(((MainActivity)getActivity()).getResult());
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

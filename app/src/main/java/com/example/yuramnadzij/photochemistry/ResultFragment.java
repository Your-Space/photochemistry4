package com.example.yuramnadzij.photochemistry;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout relativeLayout;
    private TextView solvingHeadline;
    private TextView solution;
    private TextView result;

    private OnFragmentInteractionListener mListener;

    public ResultFragment() {
        // Required empty public constructor
    }

    public void setResult(String problem, String result){
        solvingHeadline.setText(problem);
        solution.setText(result);
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultFragment newInstance(String param1, String param2) {
        ResultFragment fragment = new ResultFragment();
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
        View vw = inflater.inflate(R.layout.fragment_result, container, false);

        relativeLayout = vw.findViewById(R.id.relativveLayout);
        solvingHeadline = (TextView) vw.findViewById(R.id.txtNoSol);
        solution = (TextView)  vw.findViewById(R.id.solution);
        result = (TextView)  vw.findViewById(R.id.btn_result);
        boolean big = false;
        solution.setOnClickListener(new View.OnClickListener() {
            boolean big = false;
            @Override
            public void onClick(View v) {
                if(solution.getText().toString().equals("No chemical problem scanned or entered"))
                   return;
                else{
                    if (big) {
                        final float scale = getContext().getResources().getDisplayMetrics().density;
                        int pixels = (int) (20 * scale + 0.5f);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                 solution.getHeight() - solvingHeadline.getHeight());
                        params.setMargins(pixels, 0, pixels, 0);
                        solution.setLayoutParams(params);
                        big = false;
                        return;
                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            solvingHeadline.getHeight() + solution.getHeight());
                    params.setMargins(0, 0, 0, 0);
                    solution.setLayoutParams(params);
                    big = true;
                }
            }
        });
        return vw;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(((MainActivity)getActivity()).getIsVisible()){
            solvingHeadline.setText("Solution steps");
            solution.setText(((MainActivity) getActivity()).getResult());
            result.setVisibility(View.VISIBLE);
            result.setText(((MainActivity) getActivity()).getResult());
        } else {
            solvingHeadline.setText("No Solution");
            solution.setText("No chemical problem scanned or entered");
            result.setVisibility(View.INVISIBLE);
            result.setText("");
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

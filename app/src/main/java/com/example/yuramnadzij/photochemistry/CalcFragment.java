package com.example.yuramnadzij.photochemistry;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalcFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalcFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalcFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText editText;
    private FrameLayout frameLayout;
    private Keyboard keyboard;
    private Button button;
    private KeyboardView keyboardView;
    private Button[] buttonControl;
    private LinearLayout relativeLayout;
    public int cursorPosition = 0;

    private OnFragmentInteractionListener mListener;

    public CalcFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalcFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalcFragment newInstance(String param1, String param2) {
        CalcFragment fragment = new CalcFragment();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.fragment_calc, container, false);
        editText = (EditText) vw.findViewById(R.id.editText);
        frameLayout = (FrameLayout) vw.findViewById(R.id.frameLayout);
        button = (Button) vw.findViewById(R.id.btn_fragment_result);
        relativeLayout = vw.findViewById(R.id.control_layout);
        buttonControl = new Button[5];
        buttonControl[0] = vw.findViewById(R.id.equal);
        buttonControl[1] = vw.findViewById(R.id.balance);
        buttonControl[2] = vw.findViewById(R.id.moveLeft);
        buttonControl[3] = vw.findViewById(R.id.moveRigth);
        buttonControl[4] = vw.findViewById(R.id.delete);
        buttonControl[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balance();
            }
        });
        buttonControl[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveLeft();
            }
        });
        buttonControl[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveRight();
            }
        });
        buttonControl[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backspace();
            }
        });


        editText.setShowSoftInputOnFocus(false);

        editText.setOnFocusChangeListener(focusChangeListener);
        editText.setOnClickListener(eClickListener);
        frameLayout.setOnClickListener(fClickListener);



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setHint("");
                if(editText.getText().toString().length() == 0)
                    editText.setHint("Type a chemical problem...");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        return vw;
    }


    public EditText.OnFocusChangeListener focusChangeListener = new EditText.OnFocusChangeListener(){
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                showCustomKeyboard(editText);
                editText.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.edit_text_design));
            }
        }
    };

    public EditText.OnClickListener eClickListener = new EditText.OnClickListener(){
        @Override
        public void onClick(View v) {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            showCustomKeyboard(editText);
            editText.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.edit_text_design));
            cursorPosition = editText.getSelectionStart();
        }
    };



    public FrameLayout.OnClickListener fClickListener = new FrameLayout.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(editText.hasFocus()) {
                editText.clearFocus();
                editText.setFocusable(false);
                editText.setFocusableInTouchMode(false);
                hideCustomKeyboard(editText);
                editText.setCursorVisible(true);
                editText.setBackgroundColor(Color.TRANSPARENT);
            } else {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                editText.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.edit_text_design));
                showCustomKeyboard(editText);
            }
        }
    };


    public Button.OnClickListener clickListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            ((MainActivity)getActivity()).setResult(button.getText().toString());
        }
    };

    public void hideCustomKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        relativeLayout.setVisibility(View.GONE);
    }
    public void showCustomKeyboard( View v) {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
        relativeLayout.setVisibility(View.VISIBLE);
        //if( v!=null)((InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
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


    public void backspace(){
        InputConnection ic = editText.onCreateInputConnection(new EditorInfo());
        CharSequence selectedText = ic.getSelectedText(0);
        if (TextUtils.isEmpty(selectedText)) {
            // no selection, so delete previous character
            ic.deleteSurroundingText(1, 0);
            if(cursorPosition != 0) cursorPosition--;
        } else {
            // delete the selection
            ic.commitText("", 1);
            cursorPosition = editText.getSelectionStart();
            editText.setSelection(cursorPosition);
        }
        Toast.makeText(getActivity(), "Cursor position 1 / " + cursorPosition, Toast.LENGTH_LONG).show();
    }

    public void balance(){
        ChemicalReaction chemicalReaction = new ChemicalReaction();
        chemicalReaction.setReaction(editText.getText().toString());
        chemicalReaction.setStart();
        chemicalReaction.setEnd();
        chemicalReaction.setSides();
        chemicalReaction.setSidesFormulas();
        Balance b = new Balance(chemicalReaction);
        button.setText(chemicalReaction.getReaction());
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(clickListener);
    }

    public void moveRight(){
        if(cursorPosition < editText.getText().toString().length()) {
            cursorPosition++;
            editText.setSelection(cursorPosition);
            // Toast.makeText(getActivity(), "Cursor position " + cursorPosition, Toast.LENGTH_LONG).show();
        }
    }

    public void moveLeft(){
        if(cursorPosition > 0) {
            cursorPosition--;
            editText.setSelection(cursorPosition);
            //  Toast.makeText(getActivity(), "Cursor position 1 / " + cursorPosition, Toast.LENGTH_LONG).show();
        }
    }
}

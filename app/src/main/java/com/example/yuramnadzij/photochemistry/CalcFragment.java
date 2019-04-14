package com.example.yuramnadzij.photochemistry;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;


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
    private ImageButton crossButton;
    private KeyboardView keyboardView;
    public int cursorPosition = 0;
    private boolean caps = true;

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

        keyboardView = (KeyboardView) vw.findViewById(R.id.keyboard_view);
        keyboardView.setPreviewEnabled(false);
        keyboard = new Keyboard(getContext(), R.xml.keyboard);
        keyboard.setShifted(caps);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(keyboardActionListener);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        editText.setShowSoftInputOnFocus(false);
        button = (Button) vw.findViewById(R.id.btn_fragment_result);
        editText.setOnFocusChangeListener(focusChangeListener);
        editText.setOnClickListener(eClickListener);
        frameLayout.setOnClickListener(fClickListener);
        crossButton = vw.findViewById(R.id.crossButton);

        crossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                crossButton.setVisibility(View.INVISIBLE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setFragmentToShow(2);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setHint("");
                if(editText.getText().toString().length() == 0)
                    editText.setHint("Type a chemical problem...");
                if(button.getVisibility() == View.VISIBLE) {
                    button.setVisibility(View.GONE);
                 //   ((MainActivity)getActivity()).setIsVisible(false);
                }
                if(!editText.getText().equals("")) crossButton.setVisibility(View.VISIBLE);
                else crossButton.setVisibility(View.GONE);
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

    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(((MainActivity)getActivity()).getIsVisible()) button.setVisibility(View.VISIBLE);
    }*/

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
                hideCustomKeyboard();
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


    public KeyboardView.OnKeyboardActionListener keyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) { }
        @Override
        public void onRelease(int primaryCode) { }
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            InputConnection ic = editText.onCreateInputConnection(new EditorInfo());
            if (ic == null) return;
            switch (primaryCode) {
                case Keyboard.KEYCODE_SHIFT:
                    caps = !caps;
                    keyboard.setShifted(caps);
                    keyboardView.invalidateAllKeys();
                    break;
                case -100:
                    ChemicalReaction chemicalReaction = new ChemicalReaction();
                    chemicalReaction.setReaction(editText.getText().toString());
                    chemicalReaction.setStart();
                    chemicalReaction.setEnd();
                    chemicalReaction.setSides();
                    chemicalReaction.setSidesFormulas();
                    Balance b = new Balance(chemicalReaction);
                    button.setText(chemicalReaction.getReaction());
                    button.setVisibility(View.VISIBLE);
                    ((MainActivity)getActivity()).setIsVisible(true);
                    ((MainActivity)getActivity()).setResult(button.getText().toString());
                    break;
                case Keyboard.KEYCODE_DELETE:
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
                    break;
                case -12:
                    if(cursorPosition < editText.getText().toString().length()) {
                        cursorPosition++;
                        editText.setSelection(cursorPosition);
                        // Toast.makeText(getActivity(), "Cursor position " + cursorPosition, Toast.LENGTH_LONG).show();
                    }
                    break;
                case -13:
                    if(cursorPosition > 0) {
                        cursorPosition--;
                        editText.setSelection(cursorPosition);
                        //  Toast.makeText(getActivity(), "Cursor position 1 / " + cursorPosition, Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    char code = (char) primaryCode;
                    if(Character.isLetter(code) && caps){
                        code = Character.toUpperCase(code);
                    }
                    cursorPosition++;
                    ic.commitText(String.valueOf(code), 1);
                    if(!caps) {
                        caps = true;
                        keyboard.setShifted(caps);
                    }
            }
        }
        @Override
        public void onText(CharSequence text) { }
        @Override
        public void swipeLeft() { }
        @Override
        public void swipeRight() { }
        @Override
        public void swipeDown() { }
        @Override
        public void swipeUp() { }
    };

    public void hideCustomKeyboard() {
        keyboardView.setVisibility(View.GONE);
        keyboardView.setEnabled(false);
        if(((MainActivity)getActivity()).getIsVisible()) button.setVisibility(View.VISIBLE);
    }
    public void showCustomKeyboard( View v) {
        keyboardView.setVisibility(View.VISIBLE);
        keyboardView.setEnabled(true);
        if( v!=null ){
            ((InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
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

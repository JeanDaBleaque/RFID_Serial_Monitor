package com.jean.rfid.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jean.rfid.MainActivity;
import com.jean.rfid.R;
import com.jean.rfid.RFCommandManager;
import com.jean.rfid.SerialCommunicationObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView txtName;
    private TextView txtSurname;
    private TextView txtPerm;
    private Button btnSendCmd;
    public Tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
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
    public View.OnClickListener commandListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SerialCommunicationObject serialObject = MainActivity.getSerialObject();
            if (!txtName.getText().equals("") && !txtSurname.getText().equals("") && !txtPerm.getText().equals("") && serialObject != null) {
                RFCommandManager commandManager = MainActivity.getManager();
                String message = "addcard " + txtName.getText() + " " + txtSurname.getText() + " " + txtPerm.getText();
                commandManager.handle(message);
            } else {
                Toast.makeText(getContext(), "Parameters can't be null.", Toast.LENGTH_SHORT);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        txtName = getActivity().findViewById(R.id.txtNameA);
        txtSurname = getActivity().findViewById(R.id.txtSurnameA);
        txtPerm = getActivity().findViewById(R.id.txtPermA);
        btnSendCmd = getActivity().findViewById(R.id.btnSendAdd);
        btnSendCmd.setOnClickListener(commandListener);
        super.onViewCreated(view, savedInstanceState);
    }
}
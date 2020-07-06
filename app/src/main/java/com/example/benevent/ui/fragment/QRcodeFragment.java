package com.example.benevent.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benevent.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QRcodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QRcodeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView imageView;
    Button button;
    Button btnScan;
    EditText editText;

    TextView tv_qr_readTxt;

    public QRcodeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QRcodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QRcodeFragment newInstance(String param1, String param2) {
        QRcodeFragment fragment = new QRcodeFragment();
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
        Activity context = this.getActivity();
        View view = inflater.inflate(R.layout.fragment_q_rcode, container, false);
        imageView = (ImageView)view.findViewById(R.id.imageView);
        editText = (EditText)view.findViewById(R.id.editText);
        button = (Button)view.findViewById(R.id.button);
        btnScan = (Button)view.findViewById(R.id.btnScan);
        tv_qr_readTxt = (TextView) view.findViewById(R.id.tv_qr_readTxt);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(QRcodeFragment.this);
                integrator.setPrompt("Scan");
                integrator.setBeepEnabled(true);
                //The following line if you want QR code
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setOrientationLocked(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null){
            if (intentResult.getContents() == null){
                tv_qr_readTxt.setText("annulé");
            }else {
                tv_qr_readTxt.setText(intentResult.getContents());

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
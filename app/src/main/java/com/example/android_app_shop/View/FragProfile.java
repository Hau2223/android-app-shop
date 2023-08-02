package com.example.android_app_shop.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.android_app_shop.Controller.AccountHandler;
import com.example.android_app_shop.R;


public class FragProfile extends Fragment {

    AccountHandler accountHandler ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_profile, container, false);
        accountHandler = new AccountHandler( getContext(), "smartphone.db", null, 1);

        TextView US = view.findViewById(R.id.TV_UserName);
        TextView PS = view.findViewById(R.id.getPassword);
        Button btnChangePassword = view.findViewById(R.id.btnChangePassword);
        EditText inputChangePassWord = view.findViewById(R.id.inputChangePassWord);
        EditText inputChangePassWordAgain = view.findViewById(R.id.inputChangePassWordAgain);
        Button btnLogOut =  view.findViewById(R.id.btnLogOut);

        // Get the data from the Bundle object
        Bundle bundle = getArguments();

        // Get the data from the Bundle object
        String username = bundle.getString("us");
        String password = bundle.getString("ps");

        // Set the data
        US.setText(username);
        PS.setText(password);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Get data from edit text
                String PS_change =  inputChangePassWord.getText().toString();
                String PS_changeAgain =  inputChangePassWordAgain.getText().toString();

                if(!(PS_changeAgain.equals(PS_change)))
                {
                    Toast.makeText(getContext(), "Mật khẩu xác nhận không khớp !", Toast.LENGTH_SHORT).show();
                } else if(inputChangePassWord.length() < 6){
                    Toast.makeText(getContext(), "Vui lòng nhập ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
                }
                else {
                    accountHandler.ChangePassword(username , PS_change );
                    Log.d("username" , username);
                    Log.d("pass" , PS_change);
                    Toast.makeText(getContext(), "Mật khẩu thay đổi thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.clear(); // Xóa hết thông tin trong SharedPreferences
                editor.commit();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameFragment, new Frag_Login()).commit();
            }
        });
        return view;
    }
}
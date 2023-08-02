package com.example.android_app_shop.View;

import android.os.Bundle;
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

public class Frag_Register extends Fragment {
    Button btnRegister;
    EditText getUsername , getPassword , getPasswordAgain;

    TextView Nav_Login ;
    AccountHandler accountHandler ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_register, container, false);
        getUsername = view.findViewById(R.id.inputUsername);
        getPassword = view.findViewById(R.id.inputPassWord);
        getPasswordAgain = view.findViewById(R.id.inputPassWordAgain);
        btnRegister = view.findViewById(R.id.btnRegister);
        Nav_Login = view.findViewById(R.id.Nav_Login);

        accountHandler = new AccountHandler( getContext(), "smartphone.db", null, 1);
        accountHandler.initData();

        Nav_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameFragment, new Frag_Login()).commit();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getUsername.getText().toString();
                String password = getPassword.getText().toString();
                String passwordAgain = getPasswordAgain.getText().toString();
                if(username.length() == 0 || password.length() == 0 || passwordAgain.length() == 0){
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(!password.equals(passwordAgain))
                    {
                        Toast.makeText(getContext(), "Mật khẩu xác nhận không khớp !", Toast.LENGTH_SHORT).show();
                    }
                    else if(getPassword.length() < 6){
                        Toast.makeText(getContext(), "Vui lòng nhập ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
                    }
                    else if (accountHandler.checkUser(username, password)) {
                        Toast.makeText(getContext(), "Tài khoản đã tồn tại !", Toast.LENGTH_SHORT).show();
                    } else {
                        if (accountHandler.AddAccount(username, password)) {
                            Toast.makeText(getContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            // Replace fragment_Login with fragment_Login
                            fragmentManager.beginTransaction().replace(R.id.frameFragment, new Frag_Login()).commit();
                        } else {
                            Toast.makeText(getContext(), "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        return view;
    }
}

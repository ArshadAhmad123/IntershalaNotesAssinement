package com.example.intershalanotesassinement.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intershalanotesassinement.R;
import com.example.intershalanotesassinement.database.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

   FirebaseAuth mAuth;
   TextView signup,login;
   EditText email,password,rePassword;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_signup, container, false);

          email=v.findViewById(R.id.address);
          signup=v.findViewById(R.id.signup);
          password=v.findViewById(R.id.password);
          login=v.findViewById(R.id.login);
          rePassword = v.findViewById(R.id.repassword);
          login.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  getActivity().getSupportFragmentManager().beginTransaction()
                          .replace(R.id.main, new SigninFragment())
                          .commit();
              }
          });
          signup.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  signUp();
              }
          });
        return v;
    }

    private void signUp() {
        mAuth = FirebaseAuth.getInstance();
        String emailAdress = email.getText().toString().trim();
        String passwordEm =  password.getText().toString().trim();
        String rePasswordEm  = rePassword.getText().toString().trim();
        if(emailAdress.isEmpty()){
            email.setError("field required!");
            return;
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAdress).matches()) {
            email.setError("invalid email!");
            return;
        }else if(passwordEm.isEmpty()){
            password.setError("field required!");
            return;
        } else if (passwordEm.length()<8) {
            password.setError("invalid password!");
            return;
        }else if (!rePasswordEm.equals(passwordEm)) {
            rePassword.setError("confirm password!");
            return;
        }
        mAuth.createUserWithEmailAndPassword(emailAdress, passwordEm)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            PrefManager prefManager = PrefManager.getInstance(getContext());
                             SharedPreferences prefs = prefManager.getPrefs();
                             prefs.edit().putBoolean("is_signed_up", true).apply();
                            prefs.edit().putBoolean("is_signed_in", true).apply();
                            Toast.makeText(getContext(), "Authentication success.", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.main, new MainNoteFragment())
                                    .commit();
                        } else {
                            Exception exception = task.getException();
                            if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getContext(), "Invalid email or password.", Toast.LENGTH_SHORT).show();
                            } else if (exception instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getContext(), "User already exists.", Toast.LENGTH_SHORT).show();
                            } else if (exception instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(getContext(), "Password is too weak.", Toast.LENGTH_SHORT).show();
                            } else if (exception instanceof FirebaseAuthException) {
                                Toast.makeText(getContext(), "Firebase authentication error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Unknown error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }
}
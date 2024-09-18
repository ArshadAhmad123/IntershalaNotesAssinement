package com.example.intershalanotesassinement.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intershalanotesassinement.R;
import com.example.intershalanotesassinement.database.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SigninFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SigninFragment extends Fragment {
     EditText password,adress;
     TextView signup;
     Button signin;
     FirebaseAuth mAuth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SigninFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SigninFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SigninFragment newInstance(String param1, String param2) {
        SigninFragment fragment = new SigninFragment();
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
        View v=inflater.inflate(R.layout.fragment_signin, container, false);
        password=v.findViewById(R.id.password);
        adress=v.findViewById(R.id.address);
        signup=v.findViewById(R.id.signup);
        signin = v.findViewById(R.id.signin);
        adress=v.findViewById(R.id.address);

          signup.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  getActivity().getSupportFragmentManager().beginTransaction()
                          .replace(R.id.main, new SignupFragment())
                          .commit();
              }
          });
          signin.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  mAuth = FirebaseAuth.getInstance();
                  String emailAdress = adress.getText().toString().trim();
                  String passwordEm =  password.getText().toString().trim();
                  if(emailAdress.isEmpty()){
                      adress.setError("field required!");
                      return;
                  }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAdress).matches()) {
                      adress.setError("invalid email!");
                      return;
                  }else if(passwordEm.isEmpty()){
                      password.setError("field required!");
                      return;
                  } else if (passwordEm.length()<8) {
                      password.setError("invalid password!");
                      return;
                  }
                  mAuth.signInWithEmailAndPassword(emailAdress,passwordEm)
                          .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                              @Override
                              public void onComplete(@NonNull Task<AuthResult> task) {
                                  if (task.isSuccessful()) {
                                      Toast.makeText(getContext(), "Login successful.", Toast.LENGTH_SHORT).show();
                                      PrefManager prefManager = PrefManager.getInstance(getContext());
                                      SharedPreferences prefs1 = prefManager.getPrefs();
                                      prefs1.edit().putBoolean("is_signed_in", true).apply();
                                      prefs1.edit().putBoolean("is_signed_up", true).apply();
                                      // Navigate to MainNoteFragment
                                      getActivity().getSupportFragmentManager().beginTransaction()
                                              .replace(R.id.main, new MainNoteFragment())
                                              .commit();
                                  } else {
                                      Exception exception = task.getException();
                                      if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                          Toast.makeText(getContext(), "Invalid email or password.", Toast.LENGTH_SHORT).show();
                                      } else if (exception instanceof FirebaseAuthInvalidUserException) {
                                          Toast.makeText(getContext(), "User not found.", Toast.LENGTH_SHORT).show();
                                      } else {
                                          Toast.makeText(getContext(), "Login error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                      }
                                  }
                              }
                          });

              }
          });
        // Inflate the layout for this fragment
        return v;
    }
}
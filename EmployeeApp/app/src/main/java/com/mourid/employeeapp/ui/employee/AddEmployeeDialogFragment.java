package com.mourid.employeeapp.ui.employee;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mourid.employeeapp.R;
import com.mourid.employeeapp.api.RetrofitEmployee;
import com.mourid.employeeapp.api.RetrofitService;
import com.mourid.employeeapp.api.EmployeeApi;
import com.mourid.employeeapp.entities.Employee;
import com.mourid.employeeapp.entities.Service;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmployeeDialogFragment extends DialogFragment {
    private EditText nom;
    private EditText prenom;
    private EditText dateNaissance;

    private EditText serviceId;

    // Ajoutez d'autres champs nécessaires

    private Button add;
    private Button cancel;

    private Context parentFragmentContext;

    public AddEmployeeDialogFragment(Context context) {
        this.parentFragmentContext = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addemployee, container, false);

        nom = view.findViewById(R.id.nom);
        prenom = view.findViewById(R.id.prenom);
        dateNaissance = view.findViewById(R.id.dateNaissance);
        serviceId = view.findViewById(R.id.serviceId);




        add = view.findViewById(R.id.ok);
        cancel = view.findViewById(R.id.cancel);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Créez un nouvel objet Employee avec les valeurs saisies
               Employee employee= new Employee(0L,nom.getText().toString(),prenom.getText().toString(),dateNaissance.getText().toString());

                addEmployee(employee);
                dismiss();
            }
        });

        return view;
    }

    public void addEmployee(Employee employee) {
        EmployeeApi employeeApi = RetrofitEmployee.getClient().create(EmployeeApi.class);
        Call<Void> call = employeeApi.addEmployee(employee);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Gérez la réponse après l'ajout de l'employé (peut être vide ici)
                Log.d("rep",response.toString());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Gérez les erreurs en cas d'échec de l'appel

            }
        });
    }
}

package com.mourid.employeeapp.ui.employee;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mourid.employeeapp.R;
import com.mourid.employeeapp.api.EmployeeApi;
import com.mourid.employeeapp.api.RetrofitService;

import com.mourid.employeeapp.entities.Employee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEmployeeDialogFragment extends DialogFragment {
    private Long id;
    private EditText nom;
    private EditText prenom;
    private EditText dateNaissance;
    // Ajoutez d'autres champs nécessaires

    private Button update;
    private Button delete;
    private Context parentFragmentContext;
    private Employee employee;

    public UpdateEmployeeDialogFragment(Long id, Context context) {
        this.id = id;
        this.parentFragmentContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_updateemployee, container, false);

        nom = view.findViewById(R.id.nom);
        prenom = view.findViewById(R.id.prenom);
        dateNaissance = view.findViewById(R.id.dateNaissance);
        // Initialisez d'autres champs nécessaires

        delete = view.findViewById(R.id.delete);
        update = view.findViewById(R.id.update);

        getEmployee(id);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mettez à jour les champs de l'objet Employee et appelez la méthode pour effectuer la mise à jour
                employee.setNom(nom.getText().toString());
                employee.setPrenom(prenom.getText().toString());
                // Mettez à jour d'autres champs nécessaires

                updateEmployee(employee, employee.getId());
                dismiss();
            }
        });

        return view;
    }

    public void getEmployee(long id) {
        EmployeeApi employeeApi = RetrofitService.getClient().create(EmployeeApi.class);
        Call<Employee> call = employeeApi.getEmployeeById(id);
        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                employee = response.body();
                if (employee != null) {
                    // Affichez les valeurs dans les EditText
                    nom.setText(employee.getNom());
                    prenom.setText(employee.getPrenom());
                    dateNaissance.setText(employee.getDateNaissance().toString());
                    // Affichez d'autres champs nécessaires
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                // Gérez les erreurs en cas d'échec de l'appel
            }
        });
    }

    public void updateEmployee(Employee employee, Long id) {
        EmployeeApi employeeApi = RetrofitService.getClient().create(EmployeeApi.class);
        Call<Void> call = employeeApi.updateEmployee(id, employee);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Gérez la réponse (peut être vide ici)
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Gérez les erreurs en cas d'échec de l'appel
            }
        });
    }

    public void deleteEmployee(Long id) {
        EmployeeApi employeeApi = RetrofitService.getClient().create(EmployeeApi.class);
        Call<Void> call = employeeApi.deleteEmployee(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Gérez la réponse (peut être vide ici)
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Gérez les erreurs en cas d'échec de l'appel
            }
        });
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteEmployee(id);
                dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        builder.create().show();
    }
}

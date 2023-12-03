package com.mourid.employeeapp.ui.employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mourid.employeeapp.R;
import com.mourid.employeeapp.adapters.EmployeeAdapter;
import com.mourid.employeeapp.api.EmployeeApi;
import com.mourid.employeeapp.api.RetrofitEmployee;
import com.mourid.employeeapp.databinding.FragmentEmployeeBinding;
import com.mourid.employeeapp.entities.Employee;
import com.mourid.employeeapp.ui.service.AddServiceDialogFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmployeeFragment extends Fragment {

    private FragmentEmployeeBinding binding;
    private FloatingActionButton add;
    private List<Employee> employees = new ArrayList<>();
    private ListView listView;
    private EmployeeAdapter employeeAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EmployeeViewModel employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);
        binding = FragmentEmployeeBinding.inflate(inflater, container,false);
        View root = binding.getRoot();

        listView = binding.listemployees;
        employeeAdapter = new EmployeeAdapter(employees,getContext());
        getServices();
        add = binding.add;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddEmployeeDialogFragment dialogFragment = new AddEmployeeDialogFragment(getContext());
                dialogFragment.show(requireActivity().getSupportFragmentManager(), "AddEmployeeDialog");
            }
        });
        swipeRefreshLayout = binding.swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Trigger refresh when the user swipes
                getServices();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView idTextView = view.findViewById(R.id.id);
                String employeeIdString = idTextView.getText().toString().trim();

                if (!employeeIdString.isEmpty()) {
                    try {
                        long employeeId = Long.parseLong(employeeIdString);
                        UpdateEmployeeDialogFragment dialogFragment = new UpdateEmployeeDialogFragment(employeeId, getContext());
                        dialogFragment.show(requireActivity().getSupportFragmentManager(), "UpdateEmployeeDialog");
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        // Handle the case where parsing fails (e.g., the string is not a valid long)
                    }
                } else {
                    // Handle the case where the string is empty
                }
            }

        });

        return root;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void getServices(){
        EmployeeApi employeeApi = RetrofitEmployee.getClient().create(EmployeeApi.class);
        Call<List<Employee>> call =employeeApi.getAllEmployees();
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                employees = response.body();
                employeeAdapter.updateEmployeeList(employees);
                listView.setAdapter(employeeAdapter);
                employeeAdapter.notifyDataSetChanged();
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {

            }



        });
    }
}
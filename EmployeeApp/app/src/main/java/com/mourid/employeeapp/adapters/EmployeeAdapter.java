package com.mourid.employeeapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mourid.employeeapp.R;
import com.mourid.employeeapp.entities.Employee;
import com.mourid.employeeapp.entities.Service;

import java.util.List;

public class EmployeeAdapter extends BaseAdapter {
    private List<Employee> employees;
    private LayoutInflater inflater;

    public EmployeeAdapter(List<Employee> employees, Context context) {
        this.employees = employees;
        this.inflater = LayoutInflater.from(context);
    }
    public void updateEmployeeList(List<Employee> newEmployees) {
        employees.clear();
        employees.addAll(newEmployees);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return employees.size();
    }

    @Override
    public Object getItem(int position) {
        return employees.get(position);
    }

    @Override
    public long getItemId(int position) {
        return employees.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = inflater.inflate(R.layout.employee_list_item, null);
        TextView name = convertView.findViewById(R.id.nom);
        TextView prenom = convertView.findViewById(R.id.prenom);
        TextView date = convertView.findViewById(R.id.dateNaissance);
        //TextView service = convertView.findViewById(R.id.service);
        name.setText(employees.get(position).getNom());
        prenom.setText(employees.get(position).getPrenom().toString());
        date.setText(employees.get(position).getDateNaissance().toString());
        //service.setText(employees.get(position).getService().toString());
        return convertView;
    }
}

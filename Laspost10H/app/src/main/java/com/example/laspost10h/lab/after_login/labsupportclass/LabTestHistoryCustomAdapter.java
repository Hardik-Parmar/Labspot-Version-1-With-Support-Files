package com.example.laspost10h.lab.after_login.labsupportclass;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.laspost10h.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class LabTestHistoryCustomAdapter extends BaseAdapter
{
    public class ViewHolder
    {
        MaterialTextView applicant_id, applicant_name, test_name, test_request_date, date_of_report_dispatched, date_of_report_delivered;
    }

    public List<Lab_Test_History_Content> final_list;

    public Activity activity_context;

    ArrayList<Lab_Test_History_Content> arrayList;

    public LabTestHistoryCustomAdapter(Activity activity_context, List<Lab_Test_History_Content> list_demo)
    {
        this.final_list = list_demo;
        this.activity_context = activity_context;
        arrayList = new ArrayList<Lab_Test_History_Content>();
        arrayList.addAll(final_list);
    }


    @Override
    public int getCount()
    {
        return final_list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent)
    {
        View testRequestStatusRowView = view;

        ViewHolder viewHolder = null;

        if(testRequestStatusRowView == null)
        {
            LayoutInflater layoutInflater = activity_context.getLayoutInflater();

            testRequestStatusRowView = layoutInflater.inflate(R.layout.lab_test_history_row_display, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.applicant_id = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.lab_test_history_request_id);
            viewHolder.applicant_name = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.lab_test_history_lab_name);
            viewHolder.test_name = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.lab_test_history_test_name);
            viewHolder.test_request_date = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.lab_test_history_request_date);
            viewHolder.date_of_report_dispatched = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.lab_test_history_date_of_report_dispatch);
            viewHolder.date_of_report_delivered = (MaterialTextView) testRequestStatusRowView.findViewById(R.id.lab_test_history_date_of_report_delivered);

            testRequestStatusRowView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        // here setting data into list
        viewHolder.applicant_id.setText(final_list.get(position).getRequest_id());
        viewHolder.applicant_name.setText(final_list.get(position).getApplicant_name());
        viewHolder.test_name.setText(final_list.get(position).getTest_name());
        viewHolder.test_request_date.setText(final_list.get(position).getDate_of_order());
        viewHolder.date_of_report_dispatched.setText(final_list.get(position).getDate_of_report_dispatched());
        viewHolder.date_of_report_delivered.setText(final_list.get(position).getDate_of_report_delivered());

        return testRequestStatusRowView;
    }
}
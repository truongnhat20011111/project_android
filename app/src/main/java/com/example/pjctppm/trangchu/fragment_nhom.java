package com.example.pjctppm.trangchu;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pjctppm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class fragment_nhom extends Fragment {

    View v;
    ArrayList<CaNhan_Nhom> list;
    adapter_listview_in_nhom adapter;
    RecyclerView recyclerView;

    // dialog
    TextInputEditText name_work;
    Button add,cancel;

    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_nhom_layout,container,false);

        list = new ArrayList<>();
        //list.add(new CaNhan_Nhom("Group 1",R.drawable.ic_baseline_work_24));
        //list.add(new CaNhan_Nhom("Group 2",R.drawable.ic_baseline_work_24));

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_group);
        adapter = new adapter_listview_in_nhom(getContext(),list);

        adapter.setOnClickListener(new adapter_listview_in_nhom.OnClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Toast.makeText(getActivity(),list.get(position).getName_work(),Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnLongClickListener(new adapter_listview_in_nhom.OnLongClickListener() {
            @Override
            public void OnLongClick(View view, final int position) {
                dialog_delete(position);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        fab = (FloatingActionButton) v.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });

        return v;
    }

    private void dialog_delete (final int position) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Thông Báo");
        alertDialog.setIcon(R.drawable.ic_baseline_delete_outline_24);
        alertDialog.setMessage("Bạn có muốn xóa "+list.get(position).getName_work()+" không ?");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show(); // hiển thị dialog
    }

    private void dialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_themcv);
        dialog.setCanceledOnTouchOutside(true);

        add = (Button) dialog.findViewById(R.id.bt_add);
        cancel = (Button) dialog.findViewById(R.id.bt_cancel);
        name_work = (TextInputEditText) dialog.findViewById(R.id.edt_cv);

        add.setOnClickListener(new View.OnClickListener() {
            String name = null;
            @Override
            public void onClick(View v) {
                name = Objects.requireNonNull(name_work.getText()).toString().trim();
                if (!name.equals("") && KiemTra(list,name)) {

                    list.add(new CaNhan_Nhom(name,R.drawable.ic_baseline_work_24));
                    adapter.notifyDataSetChanged();
                    dialog.cancel();
                    Toast.makeText(getActivity(),"Đã Thêm "+name,Toast.LENGTH_SHORT).show();

                } else if (!name.equals("") && !KiemTra(list,name)){
                    Toast.makeText(getActivity(),"Tên Công Việc Trùng",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),"Hãy Điền Đầy Đủ Thông Tin",Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private static boolean KiemTra(ArrayList<CaNhan_Nhom> array,String str){
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getName_work().equals(str)) {
                return false;
            }
        }
        return true;
    }
}

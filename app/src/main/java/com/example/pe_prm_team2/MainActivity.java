package com.example.pe_prm_team2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pe_prm_team2.adapter.UserAdapter;
import com.example.pe_prm_team2.api.ApiService;
import com.example.pe_prm_team2.model.Dataresponse;
import com.example.pe_prm_team2.model.User;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private List<User> list;
    private RecyclerView rcvUser;

    private SearchView searchView;
    private Spinner spinner;
    private String[] listSpin={"Page 1","Page 2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handleSpinner();
        handleSearchView();

        //remove focus search view
        LinearLayout mainLayout = findViewById(R.id.main_layout);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.clearFocus();
            }
        });

        rcvUser=findViewById(R.id.rcv_user);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,1);
        rcvUser.setLayoutManager(gridLayoutManager);
        callUserApi(1);
    }

    private void handleSearchView() {
        searchView=findViewById(R.id.searchbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                searchUser(s);
                return true;
            }
        });
    }

    private void handleSpinner() {
        ArrayAdapter<String> spinApapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listSpin);
        spinApapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner=findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item= adapterView.getItemAtPosition(i).toString();
                if(item.equals("Page 1"))
                    callUserApi(1);
                else
                    callUserApi(2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setAdapter(spinApapter);
    }


    private void searchUser(String textsearh) {
        List<User> searchResult = new ArrayList<>();
        for (User user: list) {
            String username = (user.getFirst_name()+" "+user.getLast_name()).toLowerCase().trim();
            if(username.contains(textsearh.toLowerCase().trim())||
                    user.getEmail().toLowerCase().trim().contains(textsearh.toLowerCase().trim())){
                searchResult.add(user);
            }
        }
        if (searchResult.isEmpty()){
            Toast.makeText(MainActivity.this,"Không tìm thấy user!",Toast.LENGTH_SHORT).show();
        }
            rcvUser.setAdapter(new UserAdapter(searchResult));
    }

    public void callUserApi(int page){
          ApiService.apiservice.getUserApi(page).enqueue(new Callback<Dataresponse>() {
              @Override
              public void onResponse(Call<Dataresponse> call, Response<Dataresponse> response) {
                  Dataresponse dataresponse=response.body();
                  list=dataresponse.getData();
                  rcvUser.setAdapter(new UserAdapter(list));
              }

              @Override
              public void onFailure(Call<Dataresponse> call, Throwable t) {
                  Toast.makeText(MainActivity.this, "Lỗi call api", Toast.LENGTH_SHORT).show();
              }
          });

    }
}
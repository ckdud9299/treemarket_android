package com.example.treemarket;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo);

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter();
        recyclerView.setAdapter(postAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostApi postApi = retrofit.create(PostApi.class);

        Call<List<ProductsAppResponse>> call = postApi.getAllPosts();

        call.enqueue(new Callback<List<ProductsAppResponse>>() {
            @Override
            public void onResponse(Call<List<ProductsAppResponse>> call, Response<List<ProductsAppResponse>> response) {
                if (response.isSuccessful()) {
                    List<ProductsAppResponse> productsAppResponses = response.body();
                    if (productsAppResponses != null && !productsAppResponses.isEmpty()) {
                        postAdapter.setProducts(productsAppResponses);

                        for (ProductsAppResponse product : productsAppResponses) {
                            String imageUrl = product.getImage();
                            Log.d("Image URL", imageUrl);
                        }
                    } else {
                        // Handle no posts found
                    }
                } else {
                    // Handle failure to fetch posts
                }
            }

            @Override
            public void onFailure(Call<List<ProductsAppResponse>> call, Throwable t) {
                // Handle network error
            }
        });
    }
}

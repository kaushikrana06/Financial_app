package com.example.financeapp.News;

import android.content.Context;
import android.widget.Toast;
import com.example.financeapp.R;

import com.example.financeapp.News.Model.NewsApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

public void getNewsHeadlines(DataListener listener,String category, String  query)
{
    CallApi callApi=retrofit.create(CallApi.class);
    Call<NewsApiResponse>call=callApi.callheadlines("in",category,query,context.getString(R.string.api_key));

    try {
        call.enqueue(new Callback<NewsApiResponse>() {
            @Override
            public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
            if(!response.isSuccessful()){
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
            }
            listener.onFetch(response.body().getArticles(), response.message());
            }

            @Override
            public void onFailure(Call<NewsApiResponse> call, Throwable t) {
            listener.onErroor("Request Failed");
            }
        });
    }catch (Exception e){
        e.printStackTrace();
    }
}

    public RequestManager(Context context) {
        this.context = context;
    }
    public interface CallApi{
        @GET("top-headlines")
        Call<NewsApiResponse> callheadlines(
                @Query("country")String country,
                @Query("category")String category,
                @Query("q")String  query,
                @Query("apikey")String api_key
        );
    }
}

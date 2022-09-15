package com.example.financeapp.News;

import com.example.financeapp.News.Model.NewsHeadlines;
import java.util.List;

public interface DataListener<NewsApiResponse> {
    void onFetch(List<NewsHeadlines> list, String msg);
    void onErroor(String msg);
}

package com.ddona.day15;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.ddona.day15.adapter.NewsAdapter;
import com.ddona.day15.callback.OnNewsItemClickListener;
import com.ddona.day15.model.News;
import com.ddona.day15.parser.NewParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements OnNewsItemClickListener {
    private RecyclerView rvNews;
    private List<News> arrNews;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvNews = findViewById(R.id.rv_news);
        arrNews = new ArrayList<>();
        adapter = new NewsAdapter(this, arrNews, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvNews.setLayoutManager(layoutManager);
        rvNews.setAdapter(adapter);
        new ParserTask().execute("https://cdn.24h.com.vn/upload/rss/trangchu24h.rss");
    }

    @Override
    public void onNewsClick(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(arrNews.get(position).getLink()));
        startActivity(intent);
    }


    class ParserTask extends AsyncTask<String, Void, List<News>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "start parsing", Toast.LENGTH_SHORT).show();
        }
        @Override
        protected List<News> doInBackground(String... args) {
            String link = args[0];
            try {
                URL url = new URL(link);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                return NewParser.getInstance().parserNews(stream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(List<News> news) {
            super.onPostExecute(news);
            arrNews.clear();
            arrNews.addAll(news);
            adapter.notifyDataSetChanged();
        }
    }

}

package com.ddona.day15.parser;

import com.ddona.day15.model.News;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NewParser {
    private static NewParser INSTANCE;

    public static NewParser getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NewParser();
        }
        return INSTANCE;
    }

    private NewParser() {

    }

    public List<News> parserNews(InputStream inputStream) {
        List<News> result = new ArrayList<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, "utf-8");
            int tagType = parser.getEventType();
            News news = null;
            String content = null;
            boolean needParser = false;
            while (tagType != XmlPullParser.END_DOCUMENT) {
                switch (tagType) {
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();
                        if (tag.equals("item")) {
                            needParser = true;
                            news = new News();
                        } else if (tag.equals("img")) {
                            String image = parser.getAttributeValue(null, "src");
                            news.setImage(image);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        content = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (!needParser) {
                            break;
                        }
                        String endTag = parser.getName();
                        if (endTag.equals("item")) {
                            result.add(news);
                        } else if (endTag.equals("title")) {
                            news.setTitle(content);
                        } else if (endTag.equals("description")) {
                            news.setDescription(content);
                        } else if (endTag.equals("link")) {
                            news.setLink(content);
                        } else if (endTag.equals("pubDate")) {
                            news.setDate(content);
                        }
                        break;
                }
                tagType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

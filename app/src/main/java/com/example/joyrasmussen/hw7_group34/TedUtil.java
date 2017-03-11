package com.example.joyrasmussen.hw7_group34;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by joyrasmussen on 3/11/17.
 */

public class TedUtil {
    public static class TedPullParser {
        public static ArrayList<TED> tedParser(InputStream in) throws XmlPullParserException, IOException, ParseException {
            ArrayList<TED> list = new ArrayList<>();
            TED ted = null;
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF_8");

            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        switch(parser.getName()){
                            case "title":
                                String title = parser.nextText().trim();
                                if(!title.equals("TED Radio Hour")){
                                    ted = new TED();
                                    ted.setTitle(title);
                                }
                                break;
                            case "description":
                                if(ted != null){
                                    ted.setDescription(parser.nextText().trim());
                                }
                                break;
                            case "pubdate":
                                ted.setDate(parser.nextText().trim());
                                break;
                            case "itunes:image":
                                if(ted != null) {
                                    ted.setImage(parser.getAttributeValue(null, "href"));
                                }
                                break;
                            case "itunes:duration":
                                ted.setDuration(parser.nextText().trim());
                                break;
                            case "enclosure":
                                ted.setMp3(parser.getAttributeValue(null, "url"));
                                break;
                            default:
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            list.add(ted);
                            ted = null;
                        }
                        break;
                    default:
                        break;

                }

                event = parser.next();
            }
            return list;
        }
    }
}
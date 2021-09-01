package it.unifi.ing.hci.service;

import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import elemental2.dom.DomGlobal;
import elemental2.dom.Response;
import it.unifi.ing.hci.interop.Console;
import it.unifi.ing.hci.model.Media;

public class REST {

    public static void getImageList(onResponse onResponse){
        String API = "https://jsonplaceholder.typicode.com/todos/";
        DomGlobal.fetch(API)
                .then(Response::text)
                .then(
                        data -> {
                            Console.log(data);
                            JSONValue jsonValue = JSONParser.parseStrict(data);
                            onResponse.onresponse(jsonValue);
                            return null;
                        }
                );
    }

    public static void getMediaCounts(onResponse onResponse){
        String API = "http://localhost:8080/media";
        DomGlobal.fetch(API)
                .then(Response::text)
                .then(
                        data -> {
                            JSONValue jsonValue = JSONParser.parseStrict(data);
                            onResponse.onresponse(jsonValue);
                            return null;
                        }
                );
    }

    public static void deleteImage(Media media, onResponse onResponse){
        String API = "https://jsonplaceholder.typicode.com/todos/";
        DomGlobal.fetch(API)
                .then(Response::text)
                .then(
                        data -> {
                            Console.log(data);
                            JSONValue jsonValue = JSONParser.parseStrict(data);
                            onResponse.onresponse(jsonValue);
                            return null;
                        }
                );
    }

    public interface onResponse{
        void onresponse(JSONValue jsonValue);
    }
}

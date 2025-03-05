package com.example.spring.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class WeatherResponseDTO {

    @JsonProperty("response")
    private Response response;

    public static class Response {
        @JsonProperty("body")
        private Body body;

        @JsonProperty("header")
        private Header header;
    }

    public static class Header {
        @JsonProperty("resultCode")
        private String resultCode;
        @JsonProperty("resultMsg")
        private String resultMsg;
    }

    public static class Body {
        @JsonProperty("dataType")
        private String  dataType;
        @JsonProperty("items")
        private Items items;
        @JsonProperty("pageNo")
        private int pageNo;
        @JsonProperty("numOfRows")
        private int numOfRows;
        @JsonProperty("totalCount")
        private int totalCount;
    }

    public static class Items {
        @JsonProperty("item")
        private List<Item> item;
    }

    public static class Item {
        @JsonProperty("baseDate")
        private String  base_date;
        @JsonProperty("baseTime")
        private String  base_time;
        @JsonProperty("category")
        private String category;
        @JsonProperty("fcstDate")
        private String fcstDate;
        @JsonProperty("fcstTime")
        private String fcstTime;
        @JsonProperty("fcstValue")
        private String fcstValue;
        @JsonProperty("nx")
        private int  nx;
        @JsonProperty("ny")
        private int  ny;
    }
}

package com.tammamkhalaf.myuaeguide.Categories.Hotels;

import com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces.Gist;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HotelService {
    String BASE_URL = "https://hotels4.p.rapidapi.com/locations/search?query=dubai&locale=en_US";
    String RAPIDAPI_KEY = "cb558f9b05mshc727d913f6cde72p158d4fjsn1c4bf14f36d0";
    String RAPIDAPI_TRUEWAY_PLACES_HOST = "hotels4.p.rapidapi.com";

    //"https://hotels4.p.rapidapi.com/locations/search?query=dubai&locale=en_US"

    //todo insure the return type of Call Object

    /**
     * 1
     * Search for related locations and suggestions
     */
    @Headers({
            "x-rapidapi-key:" + RAPIDAPI_KEY,
            "x-rapidapi-host:" + RAPIDAPI_TRUEWAY_PLACES_HOST,
            "Content-Type:application/x-www-form-urlencoded",
    })
    @GET("locations/{search}")
//todo create response pojo class
    Call<Gist> searchHotel(@Path("search") String search, @Query("query") String query, @Query("local") String language);

    /**
     * 2
     * Get all available photos of property
     */
    @Headers({
            "x-rapidapi-key:" + RAPIDAPI_KEY,
            "x-rapidapi-host:" + RAPIDAPI_TRUEWAY_PLACES_HOST,
            "Content-Type:application/x-www-form-urlencoded",
    })
    @GET("properties/{get-hotel-photos}")
//todo create response pojo class
    Call<Gist> getHotelPhotos(@Path("get-hotel-photos") String get_hotel_photos, @Query("id") int id);

    /**
     * 3
     * Get all available information of a property
     */
    @Headers({
            "x-rapidapi-key:" + RAPIDAPI_KEY,
            "x-rapidapi-host:" + RAPIDAPI_TRUEWAY_PLACES_HOST,
            "Content-Type:application/x-www-form-urlencoded",
    })
    @GET("properties/{get-details}")
//todo create response pojo class
    Call<Gist> getDetails(@Path("get-details") String get_details,
                          @Query("id") int id,
                          @Query("locale") String locale,
                          @Query("currency") String currency,
                          @Query("checkOut") String date,
                          @Query("adults") int Adults,
                          @Query("checkIn") String data
    );

    /***
     * 4
     * Get locale meta data
     *
     * **/
    @Headers({
            "x-rapidapi-key:" + RAPIDAPI_KEY,
            "x-rapidapi-host:" + RAPIDAPI_TRUEWAY_PLACES_HOST,
            "Content-Type:application/x-www-form-urlencoded",
    })
    @GET("get-meta-data")
    //todo create response pojo class
    Call<Object> getMetaData();


    /**
     * 5
     * List properties with options and filters
     **/
    @Headers({
            "x-rapidapi-key:" + RAPIDAPI_KEY,
            "x-rapidapi-host:" + RAPIDAPI_TRUEWAY_PLACES_HOST,
            "Content-Type:application/x-www-form-urlencoded",
    })
    @GET("properties/list")
    Call<Object> listProperties(@Query("destinationId") int destinationId,
                                @Query("pageNumber") int pageNumber,
                                @Query("checkIn") String dateCheckIn,
                                @Query("checkOut") String dateCheckout,
                                @Query("pageSize") int pageSize,
                                @Query("adults1") int adults,
                                @Query("currency") String currency,
                                @Query("locale") String locale,
                                @Query("sortOrder") double price);
}

/**
 * {
 * "term": "new york",
 * "moresuggestions": 10631,
 * "autoSuggestInstance": null,
 * "trackingID": "3afbb9e1-73e9-4be2-bdb3-cd686a0b10a8",
 * "misspellingfallback": false,
 * "suggestions": [
 * {
 * "group": "CITY_GROUP",
 * "entities": [
 * {
 * "geoId": "1000000000000002621",
 * "destinationId": "1506246",
 * "landmarkCityDestinationId": null,
 * "type": "CITY",
 * "caption": "<span class='highlighted'>New</span> <span class='highlighted'>York</span>, New York, United States of America",
 * "redirectPage": "DEFAULT_PAGE",
 * "latitude": 40.75668,
 * "longitude": -73.98647,
 * "name": "New York"
 * },
 * {
 * "geoId": "1000000000006056111",
 * "destinationId": "1638632",
 * "landmarkCityDestinationId": null,
 * "type": "REGION",
 * "caption": "Finger Lakes Region, <span class='highlighted'>New</span> <span class='highlighted'>York</span>, United States of America",
 * "redirectPage": "DEFAULT_PAGE",
 * "latitude": 42.713029,
 * "longitude": -76.957634,
 * "name": "Finger Lakes Region"
 * },
 * {
 * "geoId": "1000000000000602887",
 * "destinationId": "1489365",
 * "landmarkCityDestinationId": null,
 * "type": "REGION",
 * "caption": "Long Island, <span class='highlighted'>New</span> <span class='highlighted'>York</span>, United States of America",
 * "redirectPage": "DEFAULT_PAGE",
 * "latitude": 40.87553,
 * "longitude": -72.856806,
 * "name": "Long Island"
 * },
 * {
 * "geoId": "1000000000006055962",
 * "destinationId": "1440145",
 * "landmarkCityDestinationId": null,
 * "type": "REGION",
 * "caption": "Adirondack Region, <span class='highlighted'>New</span> <span class='highlighted'>York</span>, United States of America",
 * "redirectPage": "DEFAULT_PAGE",
 * "latitude": 43.971654,
 * "longitude": -74.682755,
 * "name": "Adirondack Region"
 * },
 * {
 * "geoId": "1000000000006638061",
 * "destinationId": "10779362",
 * "landmarkCityDestinationId": null,
 * "type": "REGION",
 * "caption": "Westchester County, <span class='highlighted'>New</span> <span class='highlighted'>York</span>, United States of America",
 * "redirectPage": "DEFAULT_PAGE",
 * "latitude": 41.152023,
 * "longitude": -73.753606,
 * "name": "Westchester County"
 * },
 * {
 * "geoId": "1000000000006056138",
 * "destinationId": "1643111",
 * "landmarkCityDestinationId": null,
 * "type": "REGION",
 * "caption": "Seneca Lake (and vicinity), <span class='highlighted'>New</span> <span class='highlighted'>York</span>, United States of America",
 * "redirectPage": "DEFAULT_PAGE",
 * "latitude": 42.682704,
 * "longitude": -76.906683,
 * "name": "Seneca Lake (and vicinity)"
 * }
 * ]
 * },
 * {
 * "group": "LANDMARK_GROUP",
 * "entities": [
 * {
 * "geoId": "1000000000000502103",
 * "destinationId": "1634221",
 * "landmarkCityDestinationId": "1506246",
 * "type": "LANDMARK",
 * "caption": "<span class='highlighted'>New</span> <span class='highlighted'>York</span> University, New York, New York, United States of America",
 * "redirectPage": "DEFAULT_PAGE",
 * "latitude": 40.72984,
 * "longitude": -73.99529,
 * "name": "New York University"
 * },
 * {
 * "geoId": "1000000000006087131",
 * "destinationId": "1668482",
 * "landmarkCityDestinationId": "1506688",
 * "type": "LANDMARK",
 * "caption": "State University of <span class='highlighted'>New</span> <span class='highlighted'>York</span>-Binghamton, Vestal, New York, United States of America",
 * "redirectPage": "DEFAULT_PAGE",
 * "latitude": 42.087563,
 * "longitude": -75.970113,
 * "name": "State University of New York-Binghamton"
 * },
 * {
 * "geoId": "1000000000006182892",
 * "destinationId": "1728577",
 * "landmarkCityDestinationId": "12547841",
 * "type": "LANDMARK",
 * "caption": "Ausable Chasm, Town of Chesterfield, <span class='highlighted'>New</span> <span class='highlighted'>York</span>, United States of America",
 * "redirectPage": "DEFAULT_PAGE",
 * "latitude": 44.524723,
 * "longitude": -73.461534,
 * "name": "Ausable Chasm"
 * }
 * ]
 * },
 * {
 * "group": "TRANSPORT_GROUP",
 * "entities": [
 * {
 * "geoId": "1000000000004933194",
 * "destinationId": "1662393",
 * "landmarkCityDestinationId": null,
 * "type": "AIRPORT",
 * "caption": "John F. Kennedy International Airport (JFK), United States of America (<span class='highlighted'>New</span> <span class='highlighted'>York</span>)",
 * "redirectPage": "DEFAULT_PAGE",
 * "latitude": 40.644166,
 * "longitude": -73.782548,
 * "name": "John F. Kennedy International Airport (JFK)"
 * },
 * {
 * "geoId": "1000000000006185978",
 * "destinationId": "1730462",
 * "landmarkCityDestinationId": null,
 * "type": "TRAIN_STATION",
 * "caption": "<span class='highlighted'>New</span> <span class='highlighted'>York</span> Penn Station, New York, New York, United States of America",
 * "redirectPage": "DEFAULT_PAGE",
 * "latitude": 40.75056,
 * "longitude": -73.994609,
 * "name": "New York Penn Station"
 * },
 * {
 * "geoId": "1000000000006028921",
 * "destinationId": "1662396",
 * "landmarkCityDestinationId": null,
 * "type": "MINOR_AIRPORT",
 * "caption": "<span class='highlighted'>New</span> <span class='highlighted'>York</span>, NY (JRB-Downtown Manhattan Heliport), United States of America",
 * "redirectPage": "DEFAULT_PAGE",
 * "latitude": 40.701648,
 * "longitude": -74.009287,
 * "name": "New York, NY (JRB-Downtown Manhattan Heliport)"
 * }
 * ]
 * },
 * {
 * "group": "HOTEL_GROUP",
 * "entities": [
 * {
 * "geoId": "1100000000000123169",
 * "destinationId": "123169",
 * "landmarkCityDestinationId": null,
 * "type": "HOTEL",
 * "caption": "<span class='highlighted'>New</span> <span class='highlighted'>York</span>-New York Hotel & Casino, Las Vegas, Nevada, United States of America",
 * "redirectPage": "DEFAULT_PAGE",
 * "latitude": 36.103044,
 * "longitude": -115.173669,
 * "name": "New York-New York Hotel & Casino"
 * },
 * {
 * "geoId": "1100000000000115902",
 * "destinationId": "115902",
 * "landmarkCityDestinationId": null,
 * "type": "HOTEL",
 * "caption": "<span class='highlighted'>New</span> <span class='highlighted'>York</span> Marriott Marquis, New York, New York, United States of America",
 * "redirectPage": "DEFAULT_PAGE",
 * "latitude": 40.758234,
 * "longitude": -73.985457,
 * "name": "New York Marriott Marquis"
 * },
 * {
 * "geoId": "1100000000000106443",
 * "destinationId": "106443",
 * "landmarkCityDestinationId": null,
 * "type": "HOTEL",
 * "caption": "<span class='highlighted'>New</span> <span class='highlighted'>York</span> LaGuardia Airport Marriott, East Elmhurst, New York, United States of America",
 * "redirectPage": "DEFAULT_PAGE",
 * "latitude": 40.768417,
 * "longitude": -73.867602,
 * "name": "New York LaGuardia Airport Marriott"
 * }
 * ]
 * }
 * ]
 * }
 **/
//QueryMap
//Map<Object,Object>
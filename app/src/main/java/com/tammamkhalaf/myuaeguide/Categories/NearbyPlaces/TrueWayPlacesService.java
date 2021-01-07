package com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TrueWayPlacesService {
    String RAPIDAPI_KEY = "cb558f9b05mshc727d913f6cde72p158d4fjsn1c4bf14f36d0";
    String RAPIDAPI_TRUEWAY_PLACES_HOST = "trueway-places.p.rapidapi.com";

    @Headers({
            "x-rapidapi-key:"+RAPIDAPI_KEY,
            "x-rapidapi-host:"+RAPIDAPI_TRUEWAY_PLACES_HOST,
            "Content-Type:application/x-www-form-urlencoded",
    })
    @GET("FindPlaceByText")//Map<List<Object>,List<Object>>
    Call<Gist> findPlacesByText(@Query("text")String text, @Query("language")String language);

    @Headers({
            "Content-Type:application/x-www-form-urlencoded",
            "x-rapidapi-key:"+RAPIDAPI_KEY,
            "x-rapidapi-host:"+RAPIDAPI_TRUEWAY_PLACES_HOST
    })
    @GET("FindPlacesNearby")
    Call<Gist> findPlacesNearby(@Query("location") String locationCoordinates,@Query("type")String type,@Query("radius")int radius,@Query("language") String language);


    /**
     *
     airport
     amusement_park
     aquarium
     art_gallery
     atm
     bakery
     bank
     bar
     beauty_salon
     bicycle_store
     book_store
     bowling
     bus_station
     cafe
     campground
     car_dealer
     car_rental
     car_repair
     car_wash
     casino
     cemetery
     church
     cinema
     city_hall
     clothing_store
     convenience_store
     courthouse
     dentist
     department_store
     doctor
     electrician
     electronics_store
     embassy
     fire_station
     flowers_store
     funeral_service
     furniture_store
     gas_station
     government_office
     grocery_store
     gym
     hairdressing_salon
     hardware_store
     homegoodsstore
     hospital
     insurance_agency
     jewelry_store
     laundry
     lawyer
     library
     liquor_store
     locksmith
     lodging
     mosque
     museum
     night_club
     park
     parking
     pet_store
     pharmacy
     plumber
     police_station
     post_office
     primary_school
     rail_station
     realestateagency
     restaurant
     rv_park
     school
     secondary_school
     shoe_store
     shopping_center
     spa
     stadium
     storage
     store
     subway_station
     supermarket
     synagogue
     taxi_stand
     temple
     tourist_attraction
     train_station
     transit_station
     travel_agency
     university
     veterinarian
     zoo
     * */
}

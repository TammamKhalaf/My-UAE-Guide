package com.tammamkhalaf.myuaeguide.repository;

import com.tammamkhalaf.myuaeguide.categories.hotels.network.HotelServiceApi;
import com.tammamkhalaf.myuaeguide.categories.nearbyPlaces.network.TrueWayPlacesServiceApi;
import com.tammamkhalaf.myuaeguide.categories.nearbyPlaces.TrueWayPlacesResponse;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;


public class Repository {
    private TrueWayPlacesServiceApi twpApiService;
    private HotelServiceApi hotelServiceApi;

    @Inject
    public Repository(TrueWayPlacesServiceApi twpApiService, HotelServiceApi hotelServiceApi) {
        this.twpApiService = twpApiService;
        this.hotelServiceApi = hotelServiceApi;
    }

    public Observable<TrueWayPlacesResponse> getTwpPlaces(HashMap<String, String> TrueWayPlacesFilterMap,
                                                          String locationCoordinates, int radius, String type){
        TrueWayPlacesFilterMap.put("location",locationCoordinates);
        TrueWayPlacesFilterMap.put("type","cafe");
        return twpApiService.findPlacesNearby(TrueWayPlacesFilterMap,radius,"en");
        /**
         * type >>>>
         * airport,amusement_park,aquarium,art_gallery,atm,
         * bakery,bank,bar,beauty_salon,bicycle_store,book_store,bowling,bus_station
         * cafe,campground,car_dealer,car_rental,car_repair,car_wash,casino,cemetery,church,cinema,city_hall,clothing_store,convenience_store,courthouse
         * dentist,department_store,doctor,electrician,electronics_store,embassy
         * fire_station,flowers_store,funeral_service,furniture_store,gas_station
         * government_office,grocery_store,gym,
         * hairdressing_salon,hardware_store,homegoodsstore,hospital,insurance_agency,jewelry_store
         * laundry,lawyer,library,liquor_store,locksmith,lodging,mosque,museum,night_club
         * park,parking,pet_store,pharmacy,plumber,police_station,post_office,primary_school,
         * rail_station,realestateagency,restaurant,rv_park
         * school,secondary_school,shoe_store,shopping_center,spa,stadium,storage,store,subway_station,supermarket,synagogue
         * taxi_stand,temple,tourist_attraction,train_station,transit_station,travel_agency,university
         * veterinarian,zoo
         */
    }
}

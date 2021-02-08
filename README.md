# My-Uae-Guide

This application is ongoing and is under continuous development ,



Project description

You can search any place nearby or with-in the specified city.</br>
1- We will display specific or all related shops to match your search.</br>
such as :-</br>
restaurant, coffee-tea , snacks-fast-food</br>
going-out , sights-museums, transport, airport</br>
accommodation, shopping, leisure-outdoor</br>
administrative-areas-buildings, natural-geographical, petrol-station</br>
atm-bank-exchange, hospital-health-care-facility</br>
and then they can see the result as list</br>
of different places appear automatically in the app</br>

2- We provide almost all the numbers of all departments and shops registered with us.</br>
3- You can perform bookings as well.</br>
4- If you have a shop or some things and want to be a part of our growing industry then add your place by following simple steps.
5- We will handle everything for you.</br>
6- As you have the app in your pocket then you don't have to worry about anything.</br>
-----------------------------------------------------------------------------------------------------------------------------------

if you need to use the app ----->

1- you want to create project at firebase with your app name then enable phone auth from firebase authentication , create firebase real-time database </br>
and link the resourece file with your code and change the project package name

2- you want to create account at https://developer.here.com/ and change app code app id with yours</br>

-----------------------------------------------------------------------------------------------------------------------------------

App packages structure ----->

1- Admin - empty <br/>
2- Categories --> here developer --> for here explore entry point and here develoepr lockup entry point <br/>
3- Chat --> chat classes <br/>
4- Commom --> login signup , onboarding & splashscreen <br/>
5- Databases --> contain firebase models for chat and user details , here developer for dao of room database app database <br/>
6- di for dependecy injection --> here we hava retrofit model and database model for whole app <br/>
7- Helper classes contain adapters of home screen 1- adapter for featured locations 2- for most viewed 3- for categories <br/>
   slider adapter for onboarding screen for images and text views <br/>
8- Location owner this contains activity with fragments and bottom navigation for logged in end user or merchant but empty for now you can customize it<br/>
9- Repository<br/>
10- User contail favorite activity, all categories activity , userdashboard which is the main screen<br/>
11- View models for activities<br/>



# Features 

•	User will able to see all top rated 5 movies categories with its year.

•	User will be able to search by movie title.

•	User will be able to display all movie’s photo from flicker website by movie title. 

# Technical Features

•	Model-View-ViewModel architecture.

•	Kotlin Programming language 

•	Kotlin Coroutines to handle all asynchronous tasks   

•	Android Jetpack

    o	LiveData to notify view with any data changes
  
    o	Lifecycle handle lifecycle state changes 
  
    o	ViewModel allows data to survive configuration changes like screen rotations 
  
    o	Room to save data locally on SQLite so user can use the app offline 
  
    o	Hilt to handle dependency injection 
  
•	Retrofit for https network calls

•	Junit4, Hamcrest, AndroidX Test, and AndroidX architecture components core for building unit tests

# Prototype Packages 

•	api: it contains flicker apis 

•	model: contains all model classes.

•	repository: contains repositories classes to fetch data and handle all business logic.

•	db: for local database creation and its operations like insert and select

•	di: to provide app third party libraries dependencies like Room and Retrofit

•	ui: contains view classes along with their ViewModel.

•	test: contains all unit tests classes

# Libraries

•	Retrofit 

•	GSON

•	Hilt

•	Room

•	Junit4

•	Robolectric

•	Mockito

# How it works 

Once app is open for the first time, Movies list will be loaded from json file and then populated to local database.
App will display the top rated 5 movies categorized by its year, once user click the keyboard search button, app will filter the movies by the keyword from the load database.
When user click on movie will app details activity for displaying movies related photos by calling flicker search api.



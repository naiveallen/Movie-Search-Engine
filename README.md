# README

This is a standalone Java application which runs queries on the MovieLens/imdb data and extracts useful information. The primary users for this application will be users seeking for movies and their ratings that match their search criteria. This application will have a user interface that provides the user the available movie attributes such as genre, country, cast, rating, year and user’s tags and ratings. Using this application, the user will search for the movies from various categories that have the properties (attributes) the user is looking for. The GUI is build by **Java Swing**.

## Main UI (Movie Search)

![Main UI](https://lh3.googleusercontent.com/_kbH4gW2ESmre9tu1Xsbb_lgfyRv89jTtMYR0yxSeVuViy6FZvvb6kwM0-nn0XlL7mOXNGLfGPsCASlan_uaJ8Bsi0UYYcfizVWmpp61Da2Jtdbs_bhWbajYsLF1p4YeYwHO3WC8e8UsBjI9MWumntLeKtV-6R4SoEhvZlelivSnsbCaZpV7bUPYOltv1DrifbKZq58dlb7byJY3oK-CtiqBG9IQA-1ILAu7c6JdVwiMFwAjUzD9UDKqHYdq2NIudXFJXaJCjSUWq7CFLcCu81E60FwuezV6SqnOBVyWDTVheBPpytYmgh4tGrfGNWXqIP_gyu1ENAWpMdPMD1JAikUhKhuKwkZbCruWWo0vdjBcflLn2yA1V4mqwHCKF5EbcITq2Bmn1j3UIntRqsXRAUE-gpgrjZs6EoefMMAsVPlPzf-DZd2XeBgazf-mH2HQ3rPzo0ZcSZxkIKYrFgZIegxsz4c8jlMq7kF6iWw-OnclFEGDwYW6RHriAq-IDhc4VkhrK7r2eNOPCdIjlPmACASOEnYMuQlNY9Lm6yKQgpsBgH_5LDUQiHVCovFZmH035qikYW9ljK1F51fWSnOlH_ltOZyGZnvVFL5ZQmSYbm75-ookrSyuBw7xZSUzn565Hi8cr_GH-eMUNVOHif4zlRCub9_aq0o=w1414-h945-no)

## Usage flow of the GUI

 1. Once the application is loaded, Genres attribute values are loaded from the backend database.

2) The user is **required** to select desired genres attribute values. Assume that use selects Drama as the genres attribute value.

3) The Countries matching the genres selections will be listed under the Country attribute panel. Since user selected Drama in previous step, only country values that their movie genre is Drama should appear in the Country attribute panel. Assume that use selects USA as the country attribute value.

4) The user can select desired Country attribute values. This attribute is **optional** in building the query. User might not select a country at all.

5) The Filming Location Country attribute values corresponding to the Country selection will be listed. This attribute is **optional** in building the query. Since user selected Drama and USA in previous step, only filming location values that are USA production AND their movie genre is Drama, should appear in the Filming Location Country attribute panel. Assume that use selects India as the filming location country.

6) The user specifies Critic’s ratings, Number of ratings, and Movie year. These attributes are **optional** in building the query. Assume that use specifies critic's rating to be equal 7, number of reviews to be greater than 10,000 and movie year to be between 2000 and 2010.

7) The movie tag values corresponding to the previous selections will be listed in the Movie Tag panel. This attribute is optional in building the query. Based on previous selections, tag values corresponding to movies that are USA production **AND** Drama genre **AND** filmed in India **AND** critic rating=7 **AND** number of reviews>10,000 **AND** 2000 < movie year < 2010, should appear in the Movie Tag panel.


## Note

The application should be able to search for the movies that have either all the specified attribute values (AND condition) or that have any of the attribute values specified (OR condition).
For example, if user selected AND condition, and selected Drama and Family as genre, movies with Drama **AND** Family genres should be listed.
If user selected OR condition, and selected Drama and Family as genre, movies with Drama **OR** Family genre should be listed.

## Files

**createdb.sql:**  This file creates all required tables.
**dropdb.sql:** This file drops all tables and the other objects once created by createdb.sql file.
**populate.java:** This program populates data files into database. Every time you run this program, it will remove the previous data in the tables.
**hw3.java:**  This program provides a GUI, to query database. 

## Dataset

**Data statistics**

 - 10197 movies 
 - 20 movie genres 
 - 20809 movie genre assignments (avg. 2.040 genres per movie)
 - 72 countries 
 - 10197 country assignments (avg. 1.000 countries per movie) 
 - 47899 location assignments (avg. 5.350 locations per movie) 
 - 13222 tags 
 - 47957 tag assignments (tags), i.e. tuples [user, tag, movie] (avg. 22.696 tags per user, avg. 8.117 tags per movie) 

The dataset used for the project is an extension of MovieLens10M dataset, published by GroupLeans research group, http://www.grouplens.org. The datadset links the movies of MovieLens dataset with their corresponding web pages at Internet Movie Database (IMDb) and Rotten Tomatoes movie review systems.
http://www.imdb.com
http://www.rottentomatoes.com


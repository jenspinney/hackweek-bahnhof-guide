# Hackweek Nov 2017 Project (German train departure info using Deutsche Bahn API)

For Hackweek, I set out to make a simple Android app to learn the basics of Android development. I decided to make an app that would show all the trains leaving from a given German train station for a given day/time. When a user clicks on a particular train, they should see a list of all of the stops of that train.

I got the basic idea working, even if it doesn't look particularly pretty:
<img src="/Screenshot_20171116-225957.png" width="250" />
<img src="/Screenshot_20171116-230003.png" width="250" />

The major problem I ran into was the Deutsche Bahn API itself. 90% of the time, the server responds with 500 errors, making an app like this unusable. To get around this for the hackathon, I manually downloaded some data from their API and saved it as sample static data here. By default, the app uses this very incomplete static data instead of querying the Deutsche Bahn API. (This is why there are so many "UNKNOWN" destinations in the screenshots. If you decide to take this code and use it against the real API, note that you'll need to provide your API access token in a string resource named "db_api_key". Hopefully with time, their API will improve and an app like this will be possible.

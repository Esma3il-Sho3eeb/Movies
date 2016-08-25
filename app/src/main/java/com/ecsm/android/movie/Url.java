package com.ecsm.android.movie;



public final class Url {
    public static final class Base{
        public static final String API="http://api.themoviedb.org/3";
        public static final String IMAGE = "http://image.tmdb.org/t/p/w185";

        public static final class Secure{
            public static final String API="https://api.themoviedb.org/3";
            public static final String IMAGE = "https://image.tmdb.org/t/p/w185";
        }

    }
    public static final class Full{
        public static final String Popular=Base.API+ EndPoint.Movie.Popular+API_KEY;
        public static final String TOP_RATED=Base.API+ EndPoint.Movie.Top_Rated+API_KEY;

        public static final class Secure{
            public static final String Popular=Base.Secure.API+ EndPoint.Movie.Popular+API_KEY;
            public static final String TOP_RATED=Base.Secure.API+ EndPoint.Movie.Top_Rated+API_KEY;

        }


    }

    public static final String API_KEY="?api_key=9490ec35a6eea2efe32378982073f7a3";

    public static final class EndPoint{
        public static final class Movie{
            public static final String Latest="/movie/latest";
            public static final String Now_Playing="/movie/now_playing";
            public static final String Popular="/movie/popular";
            public static final String Top_Rated="/movie/top_rated";
            public static final String Upcoming="/movie/upcoming";
        }

    }
}

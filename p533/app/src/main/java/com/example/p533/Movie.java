package com.example.p533;

public class Movie {
    String rank;
    String movieNm;
    String openDt;
    String audiAcc;
    String rankInten;

    public Movie() {
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getMovieNm() {
        return movieNm;
    }

    public void setMovieNm(String movieNm) {
        this.movieNm = movieNm;
    }

    public String getOpenDt() {
        return openDt;
    }

    public void setOpenDt(String openDt) {
        this.openDt = openDt;
    }

    public String getAudiAcc() {
        return audiAcc;
    }

    public void setAudiAcc(String audi) {
        this.audiAcc = audi;
    }

    public String getRankInten() {
        return rankInten;
    }

    public void setRankInten(String rankInten) {
        this.rankInten = rankInten;
    }

    public Movie(String rank, String movieNm, String openDt, String audi, String rankInten) {
        this.rank = rank;
        this.movieNm = movieNm;
        this.openDt = openDt;
        this.audiAcc = audi;
        this.rankInten = rankInten;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "rank='" + rank + '\'' +
                ", movieNm='" + movieNm + '\'' +
                ", openDt='" + openDt + '\'' +
                ", audiAcc='" + audiAcc + '\'' +
                ", rankInten='" + rankInten + '\'' +
                '}';
    }
}

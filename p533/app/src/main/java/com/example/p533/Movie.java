package com.example.p533;

public class Movie {
    String rank;
//    int movieImg;
    String movieNm;
    String openDt;
    String audiAcc;
    String rankInten;
//    String rankIntenImg;

    public Movie() {
    }

    public Movie(String rank, String movieNm, String openDt, String audiAcc, String rankInten) {
        this.rank = rank;
//        this.movieImg = movieImg;
        this.movieNm = movieNm;
        this.openDt = openDt;
        this.audiAcc = audiAcc;
        this.rankInten = rankInten;
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

    public void setAudiAcc(String audiAcc) {
        this.audiAcc = audiAcc;
    }

    public String getRankInten() {
        return rankInten;
    }

    public void setRankInten(String rankInten) {
        this.rankInten = rankInten;
    }

//    public int getMovieImg() {
//        return movieImg;
//    }
//
//    public void setMovieImg(int movieImg) {
//        this.movieImg = movieImg;
//    }
}

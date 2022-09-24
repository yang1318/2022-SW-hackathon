package com.example.Colorful_Daegu.model;

public class RankItem implements Comparable<RankItem> {
    private String name;
    private int StampNum;

    public RankItem(String name, int stampNum) {
        this.name = name;
        StampNum = stampNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStampNum() {
        return StampNum;
    }

    public void setStampNum(int stampNum) {
        StampNum = stampNum;
    }

    @Override
    public int compareTo(RankItem rankItem) {
        if(rankItem.getStampNum()<StampNum){
            return 1;
        } else if(rankItem.getStampNum()>StampNum){
            return -1;
        }
        return 0;
    }
}

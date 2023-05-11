package com.yusa.acgnbbs.domain;

import java.io.Serializable;
import java.util.List;


public class SingleSmoothingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Double> realDataList;

    private Double lastPredictParam;

    public List<Double> getRealDataList() {
        return realDataList;
    }

    public void setRealDataList(List<Double> realDataList) {
        this.realDataList = realDataList;
    }

    public Double getLastPredictParam() {
        return lastPredictParam;
    }

    public void setLastPredictParam(Double lastPredictParam) {
        this.lastPredictParam = lastPredictParam;
    }
}


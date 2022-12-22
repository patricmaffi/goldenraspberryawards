package com.awards.movies.application.presenter;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PremiumMinMaxWinnerView implements IView {
    private List<PremiumRangeView> min;
    private List<PremiumRangeView> max;

    public PremiumMinMaxWinnerView withMin(List<PremiumRangeView> min) {
        this.min = min;
        return this;
    }
    public PremiumMinMaxWinnerView withMax(List<PremiumRangeView> max) {
        this.max = max;
        return this;
    }

    public List<PremiumRangeView> getMin() {
        return min;
    }

    public List<PremiumRangeView> getMax() {
        return max;
    }
}

package com.awards.movies.application.command_handler;


import com.awards.movies.application.command.GetMaxRangedWinnerProducer;
import com.awards.movies.application.command.GetMinRangedWinnerProducer;
import com.awards.movies.application.command.GetPremiumMinMaxWinner;
import com.awards.movies.application.presenter.IView;
import com.awards.movies.application.presenter.PremiumMinMaxWinnerView;
import com.awards.movies.application.presenter.PremiumRangePresenter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Service responsible for getting the min and max award interval from movie producers
 */
@Component
@AllArgsConstructor
public class GetPremiumMinMaxWinnerHandler implements ICommandHandler<IView, GetPremiumMinMaxWinner>{

    PremiumRangePresenter premiumRangePresenter;
    GetMinRangedWinnerProducerHandler getMinRangedWinnerProducerHandler;
    GetMaxRangedWinnerProducerHandler getMaxRangedWinnerProducerHandler;

    /**
     * Calculates movie producers with the highest and lowest award range
     * @param GetPremiumMinMaxWinner respective command
     * @return the view of producers with the highest and lowest award range
     */
    @Override
    public IView handle(GetPremiumMinMaxWinner command) {

        var resultMin = getMinRangedWinnerProducerHandler
                .handle(new GetMinRangedWinnerProducer());

        var resultMax = getMaxRangedWinnerProducerHandler
                .handle(new GetMaxRangedWinnerProducer());

        var viewMin = resultMin.stream()
                .map(e -> premiumRangePresenter.presentMin(e))
                .collect(Collectors.toList());

        var viewMax = resultMax.stream()
                .map(e -> premiumRangePresenter.presentMax(e))
                .toList();

        return new PremiumMinMaxWinnerView()
                .withMin(viewMin)
                .withMax(viewMax);
    }
}

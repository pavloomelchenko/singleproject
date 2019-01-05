package com.mycompany.strategytester;

import org.junit.Test;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BaseTest {


    @Test
    public void test() {


        List<Integer> data =  Arrays.asList(1,1,1,1,1,2,1,1,1,3,1,1,1);
        List<Integer> stoch = Arrays.asList(1,1,1,1,1,3,1,1,1,2,1,1,1);


        Optional<Divergence> divergence = calcDivergence(data, stoch);
        if (divergence.isPresent()){

        }



    }

    private Optional<Divergence> calcDivergence(List<Integer> data, List<Integer> stoch, int tick, int N) {




    }
}

package com.itvillage.chapter05.chapter0503;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * switchMap을 이용한 효율적인 키워드 검색 예제
 */
public class ObservableSwitchMapExample03 {
    public static void main(String[] args) {
        Searcher searcher = new Searcher();
        // 사용자가 입력하는 검색어라고 가정한다.
        final List<String> keywords = Arrays.asList("M", "Ma", "Mal", "Malay");

        Observable.interval(100L, TimeUnit.MILLISECONDS)
                .take(4)
                .doOnNext(data -> Logger.don(data))
                .switchMap(data -> { /** switchMap을 사용했기 때문에 마지막 키워드를 사용한 최신 검색 결과만 가져온다 */
                    String keyword = keywords.get(data.intValue());

                    return Observable.just(searcher.search(keyword))
                            .delay(300L, TimeUnit.MILLISECONDS);
                })
                .flatMap(resultList -> Observable.fromIterable(resultList))
                .subscribe(Logger::on);

        TimeUtil.sleep(1000L);
    }
}


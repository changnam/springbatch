package com.honsoft.springbatch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.honsoft.springbatch.domain.Report;

public class CustomItemProcessor implements ItemProcessor<Report, Report> {
    public Report process(Report itemObj) throws Exception {
        System.out.println("Processing Item?= " + itemObj);
        return itemObj;
    }


}
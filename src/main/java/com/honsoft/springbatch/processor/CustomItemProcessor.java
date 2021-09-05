package com.honsoft.springbatch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.honsoft.springbatch.domain.Contract;

public class CustomItemProcessor implements ItemProcessor<Contract, Contract>{

    public Contract process(Contract item) throws Exception {
        System.out.println("Processing..."+item.getContractId()+" - "+item.getContractName());
        return item;
    }

}
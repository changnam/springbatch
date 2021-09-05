package com.honsoft.springbatch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.honsoft.springbatch.domain.Contract;

public class CustomItemWriter implements ItemWriter<Contract>{

    @Autowired
    ContractDaoImpl contractDao;

	@Override
	public void write(List<? extends Contract> items) throws Exception {
		 System.out.println("Writer...");

	        List<Contract> contractList = (List<Contract>) items;
	        contractDao.saveData(contractList);
		
	}
}
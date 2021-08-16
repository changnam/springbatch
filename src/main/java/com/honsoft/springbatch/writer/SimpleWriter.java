package com.honsoft.springbatch.writer;


import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class SimpleWriter implements ItemWriter<String> {

  public void write(List<? extends String> items) throws Exception {
    for (String item : items) {
      // prefix each item with "my-"
      final String prefix = "My_";
      item = prefix + item;

      System.out.println(Thread.currentThread() + " writes: " + item);
    }
  }
}

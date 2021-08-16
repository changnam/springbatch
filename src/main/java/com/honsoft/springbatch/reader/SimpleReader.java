package com.honsoft.springbatch.reader;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class SimpleReader implements ItemReader<String> {

  // creates an unmodifiable list
  String[] itemArray = new String[] { "Java", "Spring", "Hibernate" };
  // creates a modifiable list
  List<String> items = new ArrayList<String>(Arrays.asList(itemArray));

  public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    if (!items.isEmpty()) {
      return getNextItem();
    }
    return null;
  }

  // using items directly without lock is not thread safe
  private synchronized String getNextItem() {
    return items.remove(0);
  }

} 
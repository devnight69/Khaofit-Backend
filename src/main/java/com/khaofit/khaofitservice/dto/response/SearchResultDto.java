package com.khaofit.khaofitservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * this is a search result reponse dto .
 *
 * @author kousik manik
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultDto {

  private String name;
  private String description;
  private String type;

}


package com.casaba.common.base;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
@Data
@ToString
public class User {

	private Integer id;
	private String agentName;
	private String mobile;
	private String password;

}

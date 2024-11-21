package org.jsp.banking_system.helper;

import lombok.Data;

@Data
public class responsive_structure<T> {
int code;
String message;
T data;
}

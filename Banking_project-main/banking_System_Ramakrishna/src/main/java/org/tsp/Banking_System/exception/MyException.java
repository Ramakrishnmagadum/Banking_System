package org.tsp.Banking_System.exception;

public class MyException extends Exception
{
String msg="id not found";

public MyException(String msg) 
{
	this.msg = msg;
}
// if we create an object without passing parameter then default constrictor will called
public MyException()
{
	
}
@Override
public String toString()
{
	return msg;
}

}

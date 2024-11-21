package org.jsp.banking_system.Exception;

public class MyException extends Exception
{
String msg;

public MyException(String msg)
{
	this.msg=msg;
}


//if create an object without passing parameter then this default constructer will call
public MyException()
{
	
}


@Override
public String toString() {
	return msg;
}

}

package com.FCI.SWE.Models;
/** <h1>Notification class</h1>
* <p>
* This class will act as an interface command class 
* </p>
*
* @author  Yasmine Abdel Latif, Nour Mohammed Srour Alwani, Mariam Fouad, Salwa
*         Ahmed, Huda Mohammed
* @version 1.0
* @since 9 - 4 - 2015
*/
public interface Notification {
	public String userName = "";
	public String sName = "";
	public int type = 0;
	public boolean seen = false;
	public void excute();
}

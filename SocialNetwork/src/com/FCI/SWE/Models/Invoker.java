package com.FCI.SWE.Models;
/** <h1>Invoker class</h1>
* <p>
* This class will act as an invoker of the notifications 
* </p>
*
* @author  Yasmine Abdel Latif, Nour Mohammed Srour Alwani, Mariam Fouad, Salwa
*         Ahmed, Huda Mohammed
* @version 1.0
* @since 9 - 4 - 2015
*/
public class Invoker {
	private Notification noti;
	/**
	 * Setter Function
	 * @param notificate
	 */
	public void setCommand(Notification notificate) 
	{ 
		noti = notificate; 
	}
	/**
	 * Execution Function
	 */
	public void excute()
	{
        this.noti.excute();
    }
	
}

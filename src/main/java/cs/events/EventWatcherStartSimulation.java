//########################################################################
//#
//# � University of Southampton IT Innovation Centre, 2011 
//# Copyright in this library belongs to the University of Southampton 
//# University Road, Highfield, Southampton, UK, SO17 1BJ 
//# This software may not be used, sold, licensed, transferred, copied 
//# or reproduced in whole or in part in any manner or form or in or 
//# on any media by any person other than in accordance with the terms 
//# of the Licence Agreement supplied with the software, or otherwise 
//# without the prior written consent of the copyright owners.
//#
//# This software is distributed WITHOUT ANY WARRANTY, without even the 
//# implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, 
//# except where stated in the Licence Agreement supplied with the software.
//#
//#	Created By :			Mariusz Jacyno
//#	Created Date :			2011-08-05
//#	Created for Project :	ROBUST
//#
//########################################################################

package cs.events;

import iplatform.batch.ModelRunner;
import iplatform.model.interfaces.SimulationClockEvent;

import java.util.Hashtable;

import org.apache.log4j.Logger;

import cs.ModelInterface;
import cs.config.ModelConfiguration;
import cs.dataexport.DataExport;

public class EventWatcherStartSimulation implements SimulationClockEvent{

	static Logger logger = Logger.getLogger(ModelRunner.class);
	private ModelInterface modelInterface = null;
	private int id = 0;

	//this is class used to configure the model
	private ModelConfiguration modelConfiguration = null;

	public EventWatcherStartSimulation(ModelInterface modelInterfaceRef, int idRef)
	{
		this.id = idRef;
		this.modelInterface = modelInterfaceRef; 
		this.modelConfiguration = modelInterface.getModelConfiguration();
	}
	
	@Override
	public void triggerEvent() {
		try
		{
			logger.info("---> start simulation event triggered by simplatform clock...");

			//initialise all managers 
			modelInterface.initialiseManagers();
			
			//if in batch or service mode - trigger setup process automatically
			if(modelInterface.getSimulationClock().getToolConfiguration().getOperationMode() == 1 || modelInterface.getSimulationClock().getToolConfiguration().getOperationMode() == 3)
			{
				logger.info("running simulation in batch mode...");
				modelInterface.getManagerBootstrap().createConsumers();
				modelInterface.getManagerBootstrap().createProviders();
				modelInterface.getSimulationClock().setTickDelay(0);
			}
			else
			{
				logger.info("running simulation in GUI mode...");
			}
		}
		catch(Exception exc)
		{
			logger.error("Error: ", exc);
		}
	}

	@Override
	public int getEventId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void triggerEvent(int tickNumber, int tickTimeDurationInMilliseconds) {
		// TODO Auto-generated method stub
		
	}
}

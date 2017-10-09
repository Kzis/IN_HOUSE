package test.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.cct.inhouse.core.config.parameter.domain.Parameter;

public interface IRemote extends Remote {

	 public Parameter getParameter() throws RemoteException;
	 
}

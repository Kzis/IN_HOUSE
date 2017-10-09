package test.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.cct.inhouse.central.core.config.parameter.domain.CParameterConfig;
import com.cct.inhouse.core.config.parameter.domain.Parameter;

public class RMIServer extends UnicastRemoteObject implements IRemote {

	private static final long serialVersionUID = 3295436124800893222L;

	public RMIServer() throws RemoteException {
		super();
	}

	public Parameter getParameter() {
		return CParameterConfig.getParameter();
	}

	public void init() {
		try {
			RMIServer obj = new RMIServer();
			
			UnicastRemoteObject.unexportObject(obj, true);
			IRemote stub = (IRemote) UnicastRemoteObject.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry(5555);
			registry.rebind("IRemote", stub);

			System.err.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
		System.err.println("");
	}
}

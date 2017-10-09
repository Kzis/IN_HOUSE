package test.rmi;

import java.rmi.NoSuchObjectException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {

	public RMIClient() {
		
	}

	public void requestParameter() {
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry(5555);
//				IRemote stub = (IRemote) registry.lookup("IRemote");
				
//				Parameter response = stub.getParameter();
//				System.out.println("response: " + response.getApplication().getTitle());
			} catch (NoSuchObjectException e) {
				e.printStackTrace();
			//} catch (NotBoundException e) {
			//	e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
}

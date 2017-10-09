package util.googlecalendar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.string.StringUtil;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Acl;
import com.google.api.services.calendar.model.AclRule;
import com.google.api.services.calendar.model.AclRule.Scope;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

public class GoogleCalendarUtil {

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
    /** Global instance of the scopes required by this quickstart.
    *
    * If modifying these scopes, delete your previously saved credentials
    * at ~/.credentials/calendar-java-quickstart
    */
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR, CalendarScopes.CALENDAR_READONLY);
    
    /**
     * Initialize Calendar service with private key from json file
     * @param applicationName
     * @param projectKeyFile
     * @return
     * @throws Exception
     */
    public static Calendar getCalendar(String applicationName, String projectKeyFile) throws Exception{
    	Calendar service = null;
    	InputStream is = null;
    	try{
    		
    		is = new FileInputStream(new File(projectKeyFile)); 

			GoogleCredential credentials = GoogleCredential.fromStream(is).createScoped(SCOPES);
    		
    		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    		
    		service = new Calendar.Builder(httpTransport, JSON_FACTORY, credentials)
    			.setApplicationName(applicationName)
    			.build();
    	}catch(Exception e){
    		throw e;
    	}finally{
    		try{
    			if(is != null){
    				is.close();
    			}
    		}catch(Exception e){}
    	}
    	return service;
    }
    
    /**
     * Add Calendar
     * @param service
     * @param titleCalendar
     * @param timeZomeName
     * @param accountService (Ex. test@gmail.com)
     * @return
     * @throws Exception
     */
    public static com.google.api.services.calendar.model.Calendar addCalendar(Calendar service, String titleCalendar, String description, String timeZomeName, String accountService) throws Exception{
    	com.google.api.services.calendar.model.Calendar createdCalendar = null;
    	try{
    		// Create a new calendar
    		com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
    		// Title of the calendar.
    		calendar.setSummary(titleCalendar);
    		
    		if(description != null && !description.equals("")){
    			calendar.setDescription(description);
    		}
    		
    		calendar.setTimeZone(timeZomeName);

    		// Insert the new calendar
    		createdCalendar = service.calendars().insert(calendar).execute();
    		
    		if(StringUtil.stringToNull(accountService) != null){
    			AclRule rule = new AclRule();
    			Scope scope = new Scope();
    			scope.setType(AclRuleScopeType.User.getValue()).setValue(accountService);
    			rule.setScope(scope).setRole(AclRuleRole.Owner.getValue());
    			service.acl().insert(createdCalendar.getId(), rule).execute();
    		}

    		//System.out.println("CalendarId" + createdCalendar.getId() + " is created.");
    	}catch(Exception e){
    		throw e;
    	}
    	return createdCalendar;
    }
    
    /**
     * Delete Calendar
     * @param service
     * @param calendarId
     * @throws Exception 
     */
    public static void deleteCalendar(Calendar service, String calendarId) throws Exception{
    	try{
    		// Delete a calendar
    		service.calendars().delete(calendarId).execute();
    		
    		//System.out.println("CalendarId" + calendarId + " is deleted.");
    	}catch(Exception e){
    		throw e;
    	}
    }
    
    /**
     * Share Calendar
     * @param service
     * @param calendarId
     * @param scopeType (Ex. user/group)
     * @param scopeValue (Ex. xxxx@gmail.com)
     * @param role (Ex. reader/writer)
     * @throws Exception
     */
    public static void shareCalendar(Calendar service, String calendarId, String scopeType, String scopeValue, String role) throws Exception{
    	try{
    		
	    	// Create access rule with associated scope
			AclRule rule = new AclRule();
			Scope scope = new Scope();
			scope.setType(scopeType).setValue(scopeValue);
			rule.setScope(scope).setRole(role);
			service.acl().insert(calendarId, rule).execute();
		}catch(Exception e){
			throw e;
		}
    }
    
    /**
     * Get Acl Rule From Calendar
     * @param service
     * @param calendarId
     * @return
     * @throws Exception
     */
    public static Map<String, AclRule> getMapAclRuleFromCalendar(Calendar service, String calendarId) throws Exception{
    	Map<String, AclRule> mapAclRule = new HashMap<String, AclRule>();
    	try{
    		Acl acl = service.acl().list(calendarId).execute();
			
			for (AclRule rule : acl.getItems()) {
				mapAclRule.put(rule.getId(), rule);
			}
    	}catch(Exception e){
    		throw e;
    	}
    	return mapAclRule;
    }
    
    /**
     * Get All Calendar
     * @param service
     * @return
     * @throws Exception 
     */
    public static Map<String, com.google.api.services.calendar.model.Calendar> getCalendarMap(Calendar service) throws Exception{
    	Map<String, com.google.api.services.calendar.model.Calendar> calendarMap = new HashMap<String, com.google.api.services.calendar.model.Calendar>();
    	try{
	    	// Iterate through entries in calendar list
			String pageToken = null;
			do {
				CalendarList calendarList = service.calendarList().list().setPageToken(pageToken).execute();
		    	List<CalendarListEntry> items = calendarList.getItems();
		    	for (CalendarListEntry calendarListEntry : items) {
	    			
	    			com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
	    			calendar.setId(calendarListEntry.getId());
	    			calendar.setSummary(calendarListEntry.getSummary());
	    			calendar.setDescription(calendarListEntry.getDescription());
	    			calendar.setTimeZone(calendarListEntry.getTimeZone());
	    			calendarMap.put(calendarListEntry.getSummary(), calendar);
	    		}
				pageToken = calendarList.getNextPageToken();
			} while (pageToken != null);
    	}catch(Exception e){
    		throw e;
    	}
    	return calendarMap;
    }
    
    /**
     * Add Event To Calendar
     * @param service
     * @param eventName
     * @param eventDesc
     * @param calendarId
     * @param timeZone
     * @param startDate 
     * @param endDate 
     * @param colorId
     * @throws Exception
     */
    public static void addEvent(Calendar service, String eventName, String eventDesc, String calendarId, String timeZone, Date startDate, Date endDate, String colorId) throws Exception{
    	try{
//    		LogUtil.COMMON.debug("============================================");
//    		LogUtil.COMMON.debug("CALENDAR_ID " + calendarId);
//    		LogUtil.COMMON.debug("EVENT_NAME " + eventName);
//    		LogUtil.COMMON.debug("START_DATE " + startDate);
//    		LogUtil.COMMON.debug("END_DATE " + endDate);
//    		LogUtil.COMMON.debug("============================================");
	    	Event event = new Event()
	        .setSummary(eventName)
	        .setDescription(eventDesc);
	
		    DateTime startDateTime = new DateTime(startDate);
		    EventDateTime start = new EventDateTime()
		        .setDateTime(startDateTime)
		        .setTimeZone(timeZone);
		    event.setStart(start);
		
		    DateTime endDateTime = new DateTime(endDate);
		    EventDateTime end = new EventDateTime()
		        .setDateTime(endDateTime)
		        .setTimeZone(timeZone);
		    event.setEnd(end);
	
		    if(colorId != null){
		    	event.setColorId(colorId);
		    }
		    
		    event = service.events().insert(calendarId, event).execute();
		    //System.out.printf("Event created: %s\n", event.getHtmlLink());
    	}catch(Exception e){
    		throw e;
    	}
    }
    
    /**
     * Update Event Description
     * @param service
     * @param calendarId
     * @param eventId
     * @param eventDesc
     * @throws Exception
     */
    public static void updateEventDescription(Calendar service, String calendarId, String eventId, String eventDesc) throws Exception{
    	try{
    		
    		Event event = service.events().get(calendarId, eventId).execute().setDescription(eventDesc);
    		
    		// Update the event
    		Event updatedEvent = service.events().update(calendarId, event.getId(), event).execute();

    		//System.out.printf("Event updated: %s\n", updatedEvent.getUpdated());
    	}catch(Exception e){
    		throw e;
    	}
    }
    
    /**
     * Get List Event
     * @param service
     * @param calendarId
     * @param minDate
     * @param maxDate
     * @return
     * @throws Exception 
     */
	public static List<Event> getListEvent(Calendar service, String calendarId, Date minDate, Date maxDate) throws Exception{
		List<Event> listEvent = null;    	
		try{
			
			Events events = service.events().list(calendarId)
							.setOrderBy("startTime")
		    				.setSingleEvents(true)
		    				.setTimeMin(new DateTime(minDate))
		    				.setTimeMax(new DateTime(maxDate))
		    				.execute();
	    		
	    	listEvent = events.getItems();
	    		
	    }catch(Exception e){
	    	throw e;
	    }
	    return listEvent;
	}

	/**
	 * Delete Event
	 * @param service
	 * @param calendarId
	 * @param eventId
	 * @throws IOException
	 */
	public static void deleteEvent(Calendar service, String calendarId, String eventId) throws IOException{
		service.events().delete(calendarId, eventId).execute();
	}
	
	/**
	 * https://developers.google.com/google-apps/calendar/v3/reference/acl/insert
	 */
	public enum AclRuleScopeType {
		  Default("default") 		// The public scope. This is the default value.
		, User("user")				// Limits the scope to a single user.
		, Group("group")			// Limits the scope to a group.
		, Domain("domain");			// Limits the scope to a domain.
		
		private String value;
		
		private AclRuleScopeType(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	public enum AclRuleRole {
		  None("none") 						// Provides no access.
		, FreeBusyReader("freeBusyReader")	// Provides read access to free/busy information.
		, Reader("reader")					// Provides read access to the calendar. Private events will appear to users with reader access, but event details will be hidden.
		, Writer("writer")					// Provides read and write access to the calendar. Private events will appear to users with writer access, and event details will be visible.
		, Owner("owner");					// Provides ownership of the calendar. This role has all of the permissions of the writer role with the additional ability to see and manipulate ACLs.
		
		private String value;
		
		private AclRuleRole(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
}

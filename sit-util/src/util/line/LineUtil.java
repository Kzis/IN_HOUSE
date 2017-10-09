package util.line;



import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.linecorp.bot.client.LineMessagingService;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.Multicast;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.LocationMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.VideoMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import retrofit2.Response;

public class LineUtil {
	
	/**
	 * <P> Send message to line account </P>
	 * <P> For more info {@link https://devdocs.line.me/en/#text } </P>
	 * @param channelAccessToken use to set in the request header to verify that the call is from your BOT API server
	 * @param userId ID that is returned via webhook from the user, Not use the LINE ID found on the LINE app
	 * @param text text to send
	 * @return Response<BotApiResponse>
	 * @throws IOException
	 */
	public static Response<BotApiResponse> sendMessage(String channelAccessToken, String userId, String text) throws IOException {
		
		Response<BotApiResponse> response;
		
		try{
			TextMessage textMessage = new TextMessage(text);
			PushMessage pushMsg = new PushMessage(userId, textMessage);
			response = push(channelAccessToken, pushMsg);
			
		} catch (IOException e) {
			throw e;
		}
		
		return response;
	}
	
	/**
	 * <P> Send messages to line account </P>
	 * <P> For more info {@link https://devdocs.line.me/en/#text } </P>
	 * @param channelAccessToken use to set in the request header to verify that the call is from your BOT API server
	 * @param userId ID that is returned via webhook from the user, Not use the LINE ID found on the LINE app
	 * @param messages list of messages to send
	 * @return BotApiResponse
	 * @throws IOException
	 */
	public static Response<BotApiResponse> sendMessages(String channelAccessToken, String userId, List<Message> messages) throws IOException {
		
		Response<BotApiResponse> response;
		
		try{
			
			PushMessage pushMsg = new PushMessage(userId, messages);
			response = push(channelAccessToken, pushMsg);
			
		} catch (IOException e) {
			throw e;
		}
		
		return response;
	}
	
	/**
	 * <P> Broadcast message to multiple line account </P>
	 * <P> For more info {@link https://devdocs.line.me/en/#text } </P>
	 * @param channelAccessToken use to set in the request header to verify that the call is from your BOT API server
	 * @param userIds set of ID that is returned via webhook from the user, Not use the LINE ID found on the LINE app
	 * @param text text to broadcast
	 * @return Response<BotApiResponse>
	 * @throws IOException
	 */
	public static Response<BotApiResponse> broadcastMessage(String channelAccessToken, Set<String> userIds, String text) throws IOException {
		
		Response<BotApiResponse> response;
		
		try{
			
			TextMessage textMessage = new TextMessage(text);
			Multicast pushMsg =  new Multicast(userIds, textMessage);
			response = multicast(channelAccessToken, pushMsg);
			
		}catch (IOException e){
			throw e;
		}
	
		return response;
	}
	
	/**
	 * <P> Broadcast messages to multiple line account </P>
	 * <P> For more info {@link https://devdocs.line.me/en/#text } </P>
	 * @param channelAccessToken use to set in the request header to verify that the call is from your BOT API server
	 * @param userIds set of ID that is returned via webhook from the user, Not use the LINE ID found on the LINE app
	 * @param messages list of messages to broadcast
	 * @return Response<BotApiResponse>
	 * @throws IOException
	 */
	public static Response<BotApiResponse> broadcastMessages(String channelAccessToken, Set<String> userIds, List<Message> messages) throws IOException {
		
		Response<BotApiResponse> response;
		
		try{
			
			Multicast pushMsg =  new Multicast(userIds, messages);
			response = multicast(channelAccessToken, pushMsg);
			
		}catch (IOException e){
			throw e;
		}
	
		return response;
	}
	
	
	/**
	 * <P> Send sticker to line account </P>
	 * <P> For more info {@link https://devdocs.line.me/en/#sticker } </P>
	 * @param channelAccessToken use to set in the request header to verify that the call is from your BOT API server
	 * @param userId ID that is returned via webhook from the user, Not use the LINE ID found on the LINE app
	 * @param packageId Package ID
	 * @param stickerId Sticker ID
	 * @return Response<BotApiResponse>
	 * @throws Exception
	 */
	public static Response<BotApiResponse> sendSticker(String channelAccessToken, String userId, String packageId, String stickerId ) throws Exception{
		
		Response<BotApiResponse> response;
		try{
			
			StickerMessage sm = new StickerMessage(packageId,stickerId);
			PushMessage pushMsg = new PushMessage(userId, sm);
			response = push(channelAccessToken, pushMsg);
			
		}catch (Exception e){
			throw e;
		}
		
		return response;
	}
	
	/**
	 * <P> Broadcast sticker to multiple line account </P>
	 * <P> For more info {@link https://devdocs.line.me/en/#sticker } </P>
	 * @param channelAccessToken use to set in the request header to verify that the call is from your BOT API server
	 * @param userIds set of ID that is returned via webhook from the user, Not use the LINE ID found on the LINE app
	 * @param packageId package ID
	 * @param stickerId sticker ID
	 * @return Response<BotApiResponse>
	 * @throws Exception
	 */
	public static Response<BotApiResponse> broadcastSticker(String channelAccessToken, Set<String> userIds, String packageId, String stickerId) throws Exception {
	
		Response<BotApiResponse> response;
		
		try {
			
			StickerMessage sm = new StickerMessage(packageId, stickerId);
			Multicast pushMsg =  new Multicast(userIds, sm);
			response = multicast(channelAccessToken, pushMsg);			
			
		} catch (Exception e){
			throw e;
		}
		
		return response;
	}
	
	/**
	 * <P> Send image to line account </P>
	 * <P> For more info {@link https://devdocs.line.me/en/#image } </P>
	 * @param channelAccessToken use to set in the request header to verify that the call is from your BOT API server
	 * @param userId ID that is returned via webhook from the user, Not use the LINE ID found on the LINE app
	 * @param originalContentUrl original image url
	 * @param previewImageUrl preview image url
	 * @return Response<BotApiResponse>
	 * @throws IOException
	 */
	public static Response<BotApiResponse> sendImage(String channelAccessToken, String userId, String originalContentUrl, String previewImageUrl) throws IOException {
	
		Response<BotApiResponse> response;
		try{
			
			ImageMessage  im = new ImageMessage(originalContentUrl,previewImageUrl);
			PushMessage pushMsg = new PushMessage(userId, im);
			response = push(channelAccessToken, pushMsg);
			
		}catch (IOException e){
			throw e;
		}
		
		return response;
	}
	
	/**
	 * <P> Broadcast image to multiple line account </P>
	 * <P> For more info {@link https://devdocs.line.me/en/#image } </P>
	 * @param channelAccessToken use to set in the request header to verify that the call is from your BOT API server
	 * @param userIds set of ID that is returned via webhook from the user, Not use the LINE ID found on the LINE app
	 * @param originalContentUrl original image url
	 * @param previewImageUrl preview image url
	 * @return Response<BotApiResponse>
	 * @throws IOException
	 */
	public static Response<BotApiResponse> broadcastImage(String channelAccessToken, Set<String> userIds, String originalContentUrl, String previewImageUrl) throws Exception{
	
		Response<BotApiResponse> response;
		try {
			
			ImageMessage  im = new ImageMessage(originalContentUrl, previewImageUrl);
			Multicast pushMsg =  new Multicast(userIds, im);
			response = multicast(channelAccessToken, pushMsg);
			
		} catch (Exception e){
			throw e;
		}
		
		return response;
	}
	

	/**
	 * <P> Send location to line account </P>
	 * <P> For more info {@link https://devdocs.line.me/en/#location } </P>
	 * @param channelAccessToken use to set in the request header to verify that the call is from your BOT API server
	 * @param userId ID that is returned via webhook from the user, Not use the LINE ID found on the LINE app
	 * @param title title
	 * @param address address
	 * @param lattitude lattitude
	 * @param longitude longitude
	 * @return Response<BotApiResponse>
	 * @throws IOException
	 */
	public static Response<BotApiResponse> sendLocation(String channelAccessToken, String userId, String title, String address, double lattitude, double longitude) throws IOException{
	
		Response<BotApiResponse> response;
		
		try{
			
			LocationMessage  im = new LocationMessage(title, address, lattitude, longitude);
			PushMessage pushMessage = new PushMessage(userId, im);
			response = push(channelAccessToken, pushMessage);
			
		} catch (IOException e) {
			throw e;
		}
		
		return response;
	}
	
	/**
	 * <P> Broadcast location to multiple line account </P>
	 * <P> For more info {@link https://devdocs.line.me/en/#location } </P>
	 * @param channelAccessToken use to set in the request header to verify that the call is from your BOT API server
	 * @param userIds set of ID that is returned via webhook from the user, Not use the LINE ID found on the LINE app
	 * @param title title
	 * @param address address
	 * @param lattitude lattitude
	 * @param longitude longitude
	 * @return Response<BotApiResponse>
	 * @throws IOException
	 */
	public static Response<BotApiResponse> broadcastLocation(String channelAccessToken, Set<String> userIds, String title, String address, double lattitude, double longitude) throws IOException{
	
		Response<BotApiResponse> response;
		
		try{
			
			LocationMessage  im = new LocationMessage(title, address, lattitude, longitude);
			Multicast multicast = new Multicast(userIds, im);
			response = multicast(channelAccessToken, multicast);
			
		} catch (IOException e) {
			throw e;
		}
		
		return response;
	}
	
	/**
	 * <P> Send video to line account </P>
	 * <P> For more info {@link https://devdocs.line.me/en/#video } </P>
	 * @param channelAccessToken use to set in the request header to verify that the call is from your BOT API server
	 * @param userId ID that is returned via webhook from the user, Not use the LINE ID found on the LINE app
	 * @param originalContentUrl original video url
	 * @param previewImageUrl preview video url
	 * @return Response<BotApiResponse>
	 * @throws IOException
	 */
	public static Response<BotApiResponse> sendVideo (String channelAccessToken, String userId, String originalContentUrl, String previewImageUrl) throws IOException{
		
		Response<BotApiResponse> response;
		
		try{
			
			VideoMessage  videoMessage = new VideoMessage(originalContentUrl, previewImageUrl);
			PushMessage pushMessage = new PushMessage(userId, videoMessage);
			response = push(channelAccessToken, pushMessage);
			
			
		}catch (IOException e){
			throw e;
		}
		
		return response;
	}
	
	/**
	 * <P> Broadcast video to line account </P>
	 * <P> For more info {@link https://devdocs.line.me/en/#video } </P>
	 * @param channelAccessToken use to set in the request header to verify that the call is from your BOT API server
	 * @param userIds set of ID that is returned via webhook from the user, Not use the LINE ID found on the LINE app
	 * @param originalContentUrl original video url
	 * @param previewImageUrl preview video url
	 * @return Response<BotApiResponse>
	 * @throws IOException
	 */
	public static Response<BotApiResponse> broadcastVideo(String channelAccessToken, Set<String> userIds, String originalContentUrl, String previewImageUrl) throws IOException{
		
		Response<BotApiResponse> response;
		
		try {
			
			VideoMessage videoMessage = new VideoMessage(originalContentUrl, previewImageUrl);
			Multicast pushMessage = new Multicast(userIds, videoMessage);
			response = multicast(channelAccessToken, pushMessage);
			
		} catch (IOException e){
			throw e;
		}
		
		return response;
	}
	
	/**
	 * <P> Send audio to line account </P>
	 * <P> For more info {@link https://devdocs.line.me/en/#audio } </P>
	 * @param channelAccessToken use to set in the request header to verify that the call is from your BOT API server
	 * @param userId ID that is returned via webhook from the user, Not use the LINE ID found on the LINE app
	 * @param originalContentUrl original audio url
	 * @param duration audio duration
	 * @return Response<BotApiResponse>
	 * @throws IOException
	 */
	public static Response<BotApiResponse> sendAudio(String channelAccessToken, String userId, String originalContentUrl, Integer duration) throws IOException{
		
		Response<BotApiResponse> response;
		
		try{
			
			AudioMessage  audioMessage = new AudioMessage(originalContentUrl, duration);
			PushMessage pushMessage = new PushMessage(userId, audioMessage);
			response = push(channelAccessToken, pushMessage);
			
		}catch (IOException e){
			throw e;
		}
		
		return response;
	}
	
	/**
	 * <P> Broadcast audio to line account </P>
	 * <P> For more info {@link https://devdocs.line.me/en/#audio } </P>
	 * @param channelAccessToken use to set in the request header to verify that the call is from your BOT API server
	 * @param userIds set of ID that is returned via webhook from the user, Not use the LINE ID found on the LINE app
	 * @param originalContentUrl original audio url
	 * @param duration audio duration
	 * @return Response<BotApiResponse>
	 * @throws IOException
	 */
	public static Response<BotApiResponse> broadcastAudio(String channelAccessToken, Set<String> userIds, String originalContentUrl, Integer duration) throws IOException{
		
		Response<BotApiResponse> response;
		
		try{
			
			AudioMessage audioMessage = new AudioMessage(originalContentUrl, duration);
			Multicast pushMsg = new Multicast(userIds, audioMessage);
			response = multicast(channelAccessToken, pushMsg);
			
		} catch (IOException e) {
			throw e;
		}
		
		return response;
	}
	
	/**
	 * Send message
	 * @param channelAccessToken use to set in the request header to verify that the call is from your BOT API server
	 * @param pushMessage
	 * @return Response<BotApiResponse>
	 * @throws Exception
	 */
	private static Response<BotApiResponse>  push(String channelAccessToken, PushMessage pushMessage) throws IOException {
		
		LineMessagingServiceBuilder builder = LineMessagingServiceBuilder.create(channelAccessToken);
		LineMessagingService service = builder.build();
		retrofit2.Call<BotApiResponse> call = service.pushMessage(pushMessage);
		Response<BotApiResponse> response = call.execute();
		
		return response;
	}
	
	
	/**
	 * Broadcast message
	 * @param channelAccessToken use to set in the request header to verify that the call is from your BOT API server
	 * @param multicast
	 * @return Response<BotApiResponse>
	 * @throws IOException 
	 */
	private static Response<BotApiResponse> multicast(String channelAccessToken, Multicast multicast) throws IOException {
		
		LineMessagingServiceBuilder builder = LineMessagingServiceBuilder.create(channelAccessToken);
		LineMessagingService service = builder.build();
		retrofit2.Call<BotApiResponse> call = service.multicast(multicast);
		Response<BotApiResponse> response = call.execute();
		
		return response;
	}
}

package com.tweetfetcher.client;

import static org.junit.Assert.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.junit.Test;

public class FetcherClient {
	@Test
	public void testRESTSimple() {
		Client client = ClientBuilder.newClient();
		String resulttext = client
				.target("http://localhost:8080/TweetFetcher/client/res")
				.request(MediaType.TEXT_PLAIN).get(String.class);
		System.out.println(resulttext);
		assertNotNull(resulttext);
		String resultxml = client
				.target("http://localhost:8080/TweetFetcher/client/res")
				.request(MediaType.TEXT_XML).get(String.class);
		System.out.println(resultxml);
		assertNotNull(resultxml);
	}
}

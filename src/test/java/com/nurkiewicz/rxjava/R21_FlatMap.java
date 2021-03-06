package com.nurkiewicz.rxjava;

import com.nurkiewicz.rxjava.util.Urls;
import io.reactivex.Flowable;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class R21_FlatMap {

	/**
	 * Hint: UrlDownloader.download()
	 * Hint: flatMap(), maybe concatMap()?
	 * Hint: toList()
     * Hint: blockingGet()
	 */
	@Test
	public void shouldDownloadAllUrlsInArbitraryOrder() throws Exception {
		Flowable<URL> urls = Urls.all();

		//when
		List<String> bodies = null;  //urls...

		//then
		assertThat(bodies).hasSize(996);
		assertThat(bodies).contains("<html>www.twitter.com</html>", "<html>www.aol.com</html>", "<html>www.mozilla.org</html>");
	}

	/**
	 * Hint: Pair.of(...)
	 * Hint: Flowable.toMap()
	 */
	@Test
	public void shouldDownloadAllUrls() throws Exception {
		//given
		Flowable<URL> urls = Urls.all();
		
		//when
		//WARNING: URL key in HashMap is a bad idea here
		Map<URI, String> bodies = null; //urls...
		
		//then
		assertThat(bodies).hasSize(996);
		assertThat(bodies).containsEntry(new URI("http://www.twitter.com"), "<html>www.twitter.com</html>");
		assertThat(bodies).containsEntry(new URI("http://www.aol.com"), "<html>www.aol.com</html>");
		assertThat(bodies).containsEntry(new URI("http://www.mozilla.org"), "<html>www.mozilla.org</html>");
	}
	
	/**
	 * Hint: flatMap with int parameter
	 */
	@Test
	public void downloadThrottled() throws Exception {
		//given
		Flowable<URL> urls = Urls.all().take(20);
		
		//when
		//Use UrlDownloader.downloadThrottled()
		Map<URI, String> bodies = new HashMap<>();
		
		//then
		assertThat(bodies).containsEntry(new URI("http://www.twitter.com"), "<html>www.twitter.com</html>");
		assertThat(bodies).containsEntry(new URI("http://www.adobe.com"), "<html>www.adobe.com</html>");
		assertThat(bodies).containsEntry(new URI("http://www.bing.com"), "<html>www.bing.com</html>");
	}
	
	private URI toUri(URL url) {
		try {
			return url.toURI();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
}

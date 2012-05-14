import javax.servlet.http.HttpSession;

public class SessionAwareCacheStore implements PaginationCacheStore {

	HttpSession cacheStore;

	public SessionAwareCacheStore(HttpSession session) {
		cacheStore = session;
	}

	public void clear() {
		cacheStore.removeAttribute("theotherpages.pages");

	}

	public Object get(String key) {
		return cacheStore.getAttribute(key);
	}

	public void put(String key, Object value) {
		cacheStore.setAttribute(key, value);
	}

	public void remove(String key) {
		cacheStore.removeAttribute(key);
	}
}
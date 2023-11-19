package clases;

import java.util.ArrayList;

public class UsuarioTwitter implements Comparable{
	String id;
	String screenName;
	ArrayList<String> tags;
	String avatar;
	Long followersCount;
	Long friendsCount;
	String lang;
	Long lastSeen;
	String tweetId;
	ArrayList<String> friends;
	
//	GETTERS Y SETTERSS
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Long getFollowersCount() {
		return followersCount;
	}
	public void setFollowersCount(Long followersCount) {
		this.followersCount = followersCount;
	}
	public Long getFriendsCount() {
		return friendsCount;
	}
	public void setFriendsCount(Long friendsCount) {
		this.friendsCount = friendsCount;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public Long getLastSeen() {
		return lastSeen;
	}
	public void setLastSeen(Long lastSeen) {
		this.lastSeen = lastSeen;
	}
	public String getTweetId() {
		return tweetId;
	}
	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
	}
	public ArrayList<String> getFriends() {
		return friends;
	}
	public void setFriends(ArrayList<String> friends) {
		this.friends = friends;
	}
	
//	CONSTRUCTOR
	public UsuarioTwitter(String id, String screenName, ArrayList<String> tags, String avatar, Long followersCount,
			Long friendsCount, String lang, Long lastSeen, String tweetId, ArrayList<String> friends) {
		this.id = id;
		this.screenName = screenName;
		this.tags = tags;
		this.avatar = avatar;
		this.followersCount = followersCount;
		this.friendsCount = friendsCount;
		this.lang = lang;
		this.lastSeen = lastSeen;
		this.tweetId = tweetId;
		this.friends = friends;
	}
	
	
//	toString
	@Override
	public String toString() {
		return "UsuarioTwitter [id=" + id + ", screenName=" + screenName + ", followersCount=" + followersCount
				+ ", friendsCount=" + friendsCount + "]";
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		UsuarioTwitter otro = (UsuarioTwitter) o;
		if (!(GestionTwitter.getMapaAmigos().get(this).size() == GestionTwitter.getMapaAmigos().get(otro).size())) {
			return GestionTwitter.getMapaAmigos().get(otro).size() - GestionTwitter.getMapaAmigos().get(this).size();
		}else {
			String nombre1 = this.screenName;
			String nombre2 = otro.getScreenName();
			return nombre1.compareTo(nombre2);
		}
		
	}
	
	
	
	
	
}

/**
 * 
 */
package it.unical.mat.moviesquik.model.accounting;

import java.util.Date;
import java.util.List;

import it.unical.mat.moviesquik.controller.movieparty.MoviePartySearchFilter;
import it.unical.mat.moviesquik.model.movieparty.MovieParty;
import it.unical.mat.moviesquik.model.posting.Notification;
import it.unical.mat.moviesquik.model.posting.Post;
import it.unical.mat.moviesquik.model.watchlist.Watchlist;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.DataListPage;

/**
 * @author Agostino
 *
 */
public class User
{
	public static final String DEFAULT_USER_PROFILE_IMG_PATH = "res/drawable/user_avatar.jpg";
	public static final String USER_PROFILE_IMG_PATH = "res/user/";
	
	private Long id;
	private String first_name;
	private String last_name;
	private String email;
	private Date birthday;
	private String gender;
	private String password;
	private Boolean isKid;
	private Family family;
	private Long facebookId;
	private List<User> friends;
	private Integer followersCount;
	private String profileImagePath;
	private List<Notification> notifications;
	private List<Notification> unreadNotifications;
	private List<Post> personalPosts;
	private List<Post> allPosts;
	private List<Watchlist> watchlists;
	private List<MovieParty> allParties;
	private List<String> favoriteGenres;
	
	public User()
	{}

	public User(final String first_name, 
				final String last_name, 
				final String email, 
				final Date birthday, 
				final String gender, 
				final String password)
	{
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.birthday = birthday;
		this.gender = gender;
		this.password = password;
		this.isKid = false;
	}

	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getFirstName()
	{
		return first_name;
	}

	public void setFirstName(String first_name)
	{
		this.first_name = first_name;
	}

	public String getLastName()
	{
		return last_name;
	}

	public void setLastName(String last_name)
	{
		this.last_name = last_name;
	}
	
	public String getFullName()
	{
		return first_name + " " + last_name;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Date getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public Boolean getIsKid()
	{
		return isKid;
	}
	
	public void setIsKid(Boolean isKid)
	{
		this.isKid = isKid;
	}

	public Family getFamily()
	{
		return family;
	}
	
	public void setFamily(Family family)
	{
		this.family = family;
	}
	
	public Long getFacebookId()
	{
		return facebookId;
	}
	
	public void setFacebookId(Long facebookId)
	{
		this.facebookId = facebookId;
	}
	
	public List<User> getFriends()
	{
		if ( friends == null || friends.isEmpty() )
			friends = DBManager.getInstance().getDaoFactory().getUserDao().findFriends(this, 10);
		return friends;
	}
	
	public void setFriends(List<User> friends)
	{
		this.friends = friends;
	}
	
	public Integer getFollowersCount()
	{
		return followersCount;
	}
	
	public void setFollowersCount(Integer followersCount)
	{
		this.followersCount = followersCount;
	}
	
	public String getProfileImagePath()
	{
		return (profileImagePath == null || profileImagePath.isEmpty()) 
				? DEFAULT_USER_PROFILE_IMG_PATH : USER_PROFILE_IMG_PATH + profileImagePath;
	}
	
	public void setProfileImagePath(String profileImagePath)
	{
		this.profileImagePath = profileImagePath;
	}
	
	public List<Notification> getNotifications()
	{
		if ( notifications == null || notifications.isEmpty() )
			setNotifications(DBManager.getInstance().getDaoFactory().getNotificationDao()
				.findByUser(this, DataListPage.DEFAULT_NOTIFICATIONS_PAGE));
		return notifications;
	}
	
	public void setNotifications(List<Notification> notifications)
	{
		this.notifications = notifications;
	}
	
	public List<Notification> getUnreadNotifications()
	{
		if ( unreadNotifications == null || unreadNotifications.isEmpty() )
			setUnreadNotifications(DBManager.getInstance().getDaoFactory().getNotificationDao()
				.findUnreadByUser(this, DataListPage.DEFAULT_NOTIFICATIONS_PAGE));
		return unreadNotifications;
	}
	
	public void setUnreadNotifications(List<Notification> unreadNotifications)
	{
		this.unreadNotifications = unreadNotifications;
	}
	
	public List<Post> getPersonalPosts()
	{
		if ( personalPosts == null || personalPosts.isEmpty() )
			personalPosts = DBManager.getInstance().getDaoFactory().getPostDao().findByUser(this, DataListPage.DEFAULT_POSTS_PAGE);
		return personalPosts;
	}
	
	public List<Post> getAllPosts()
	{
		if ( allPosts == null || allPosts.isEmpty() )
			allPosts = DBManager.getInstance().getDaoFactory().getPostDao().findByFollowedUsers(this, DataListPage.DEFAULT_POSTS_PAGE);
		return allPosts;
	}
	
	public List<Watchlist> getWatchlists()
	{
		if ( watchlists == null )
			watchlists = DBManager.getInstance().getDaoFactory().getWatchlistDao().findByUser(this);
		return watchlists;
	}
	public void setWatchlists(List<Watchlist> watchlists)
	{
		this.watchlists = watchlists;
	}
	
	public List<MovieParty> getAllMovieParties()
	{
		if ( allParties == null || allParties.isEmpty() )
			allParties = DBManager.getInstance().getDaoFactory().getMoviePartyDao()
				.findAllByUser(this, MoviePartySearchFilter.ALL, DataListPage.DEFAULT_MOVIE_PARTIES_PAGE);
		return allParties;
	}
	
	public List<String> getFavoriteGenres()
	{
		if ( favoriteGenres == null || favoriteGenres.isEmpty() )
			favoriteGenres = DBManager.getInstance().getDaoFactory().getUserGenreDao().findFavorites(this);
		return favoriteGenres;
	}
	
	public void setFavoriteGenres(List<String> favoriteGenres)
	{
		this.favoriteGenres = favoriteGenres;
	}
}

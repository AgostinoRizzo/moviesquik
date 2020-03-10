/**
 * 
 */
package it.unical.mat.moviesquik.model;

import java.util.Date;
import java.util.List;

import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.DataListPage;

/**
 * @author Agostino
 *
 */
public class User
{
	private Long id;
	private String first_name;
	private String last_name;
	private String email;
	private Date birthday;
	private String gender;
	private String password;
	private Family family;
	private List<User> friends;
	private Integer followersCount;
	private List<String> favoritesGenres;
	private String profileImagePath;
	private List<Notification> unreadNotifications;
	private List<Post> personalPosts;
	private List<Post> allPosts;
	
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

	public Family getFamily()
	{
		return family;
	}
	
	public void setFamily(Family family)
	{
		this.family = family;
	}
	
	public List<User> getFriends()
	{
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
	
	public List<String> getFavoritesGenres()
	{
		return favoritesGenres;
	}
	
	public void setFavoritesGenres(List<String> favoritesGenres)
	{
		this.favoritesGenres = favoritesGenres;
	}
	
	public String getProfileImagePath()
	{
		return profileImagePath;
	}
	
	public void setProfileImagePath(String profileImagePath)
	{
		this.profileImagePath = profileImagePath;
	}
	
	public List<Notification> getUnreadNotifications()
	{
		setUnreadNotifications(DBManager.getInstance().getDaoFactory().getNotificationDao()
				.findUnreadByUser(this, Notification.NOTIFICATIONS_LIMIT));
		return unreadNotifications;
	}
	
	public void setUnreadNotifications(List<Notification> unreadNotifications)
	{
		this.unreadNotifications = unreadNotifications;
	}
	
	public List<Post> getPersonalPosts()
	{
		personalPosts = DBManager.getInstance().getDaoFactory().getPostDao().findByUser(this, DataListPage.DEFAULT_POSTS_PAGE);
		return personalPosts;
	}
	
	public List<Post> getAllPosts()
	{
		allPosts = DBManager.getInstance().getDaoFactory().getPostDao().findByFollowedUsers(this, DataListPage.DEFAULT_POSTS_PAGE);
		return allPosts;
	}
}

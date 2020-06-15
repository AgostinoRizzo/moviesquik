/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.unical.mat.moviesquik.model.Family;
import it.unical.mat.moviesquik.model.User;
import it.unical.mat.moviesquik.persistence.DBManager;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.persistence.dao.UserDao;
import it.unical.mat.moviesquik.util.DateUtil;

/**
 * @author Agostino
 *
 */
public class UserDaoJDBC implements UserDao
{
	protected static final String INSERT_STATEMENT           = "insert into \"user\"(user_id, first_name, last_name, email, birthday, gender, password, family_id, kid) values (?,?,?,?,?,?,?,?,?)";
	protected static final String FIND_BY_PRIMARY_KEY_QUERY  = "select * from \"user\" where user_id = ?";
	protected static final String FIND_ALL_QUERY             = "select * from \"user\"";
	protected static final String UPDATE_STATEMENT           = "update \"user\" SET first_name = ?, last_name = ?, email = ?, birthday = ?, gender = ?, password = ?, profile_img = ?, family_id = ?, kid = ? WHERE user_id = ?";
	protected static final String DELETE_STATEMENT           = "delete from \"user\" where user_id = ?";
	protected static final String FIND_BY_FAMILY_QUERY       = "select * from \"user\" where family_id = ?";
	protected static final String FIND_BY_EMAIL_QUERY        = "select * from \"user\" where email = ?";
	protected static final String FIND_BY_LOGIN_QUERY        = "select * from \"user\" where email = ? and password = ?";
	protected static final String FIND_FRIENDS_QUERY         =   "select \"user\".* \r\n" + 
																 "from following INNER JOIN \"user\" ON (followed_id = user_id)\r\n" + 
																 "where follower_id = ? and EXISTS \r\n" + 
																 "	(\r\n" + 
																 "		select * from following AS back_following\r\n" + 
																 "		where back_following.follower_id = following.followed_id and back_following.followed_id = ?\r\n" + 
																 "	)";
	protected static final String FOLLOWERS_COUNT_QUERY      = "select COUNT(*) from following where (followed_id = ?)";
	protected static final String FIND_BY_NAME_QUERY         = "select * from \"user\" where lower(first_name) like lower(concat('%', ?, '%')) or (lower(last_name) like lower(concat('%', ?, '%')) and char_length(?) > 0) limit ?";
	protected static final String FIND_FOLLOWED_IDS_QUERY    = "select followed_id from following where (follower_id = ?)";
	protected static final String FIND_FRIENDS_BY_NAME_QUERY = FIND_FRIENDS_QUERY + " INTERSECT " + FIND_BY_NAME_QUERY;

	private final StatementPrompterJDBC statementPrompter;
	
	public UserDaoJDBC( final StatementPrompterJDBC statementPrompter )
	{
		this.statementPrompter = statementPrompter;
	}
	
	@Override
	public boolean save(User usr)
	{
		try
		{
			usr.setId(IdBroker.getNextId(statementPrompter));
			final PreparedStatement statement = statementPrompter.prepareStatement(INSERT_STATEMENT);
			
			setDataToInsertStatement(usr, statement);
			
			statement.executeUpdate();
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	public User findByPrimaryKey(Long key)
	{
		User usr = null;
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_PRIMARY_KEY_QUERY);
			statement.setLong  (1, key);
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
				usr = createUserFromResult(result, null);
			return usr;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return null; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	public List<User> findAll()
	{
		final List<User> users = new ArrayList<User>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_ALL_QUERY);
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				users.add( createUserFromResult(result, null) );
			return users;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return users; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	public boolean update(User usr)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(UPDATE_STATEMENT);
			
			statement.setString   (1, usr.getFirstName());
			statement.setString   (2, usr.getLastName());
			statement.setString   (3, usr.getEmail());
			statement.setTimestamp(4, DateUtil.toJDBC(usr.getBirthday()));
			statement.setString   (5, usr.getGender());
			statement.setString   (6, usr.getPassword());
			statement.setString   (7, usr.getProfileImagePath());
			statement.setLong     (8, usr.getFamily().getId());
			
			statement.setLong     (9, usr.getId());
			
			statement.executeUpdate();
			
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}

	@Override
	public boolean delete(User usr)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(DELETE_STATEMENT);
			statement.setLong (1, usr.getId());
			statement.executeUpdate();
			return true;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return false; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<User> findByFamily(Family family)
	{
		final List<User> users = new ArrayList<User>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_FAMILY_QUERY);
			statement.setLong  (1, family.getId());
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				users.add( createUserFromResult(result, family) );
			return users;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return users; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public User findByEmail(String email)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_EMAIL_QUERY);
			statement.setString(1, email);
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
				return createUserFromResult(result, null);
			return null;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return null; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public User findByLogin(String email, String password)
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_LOGIN_QUERY);
			statement.setString(1, email);
			statement.setString(2, password);
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
				return createUserFromResult(result, null);
			return null;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return null;}
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<User> findFriends(User user, int maxCount)
	{
		/*
		final User f1 = new User();
		final User f2 = new User();
		final User f3 = new User();
		
		f1.setFirstName("F1"); f1.setLastName("G1"); f1.setEmail("f1@email.com");
		f2.setFirstName("F2"); f2.setLastName("G2"); f2.setEmail("f1@email.com");
		f3.setFirstName("F3"); f3.setLastName("G3"); f3.setEmail("f1@email.com");
		
		final List<User> friends = new ArrayList<User>();
		friends.add(f1);
		friends.add(f2);
		friends.add(f3);
		
		return friends;
		*/
		
		final List<User> friends = new ArrayList<User>();
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_FRIENDS_QUERY);
			statement.setLong  (1, user.getId());
			statement.setLong  (2, user.getId());
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				friends.add( createUserFromResult(result, null) );
			return friends;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return friends; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<User> findByName(String name, int limit)
	{
		final List<User> users = new ArrayList<User>();
		final String[] fullname = name.split(" ", 2);
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_BY_NAME_QUERY);
			statement.setString(1, (fullname.length > 0) ? fullname[0] : "");
			statement.setString(2, (fullname.length > 1) ? fullname[1] : "");
			statement.setString(3, (fullname.length > 1) ? fullname[1] : "");
			statement.setInt(4, limit);
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				users.add( createUserFromResult(result, null) );
			return users;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return users; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	@Override
	public List<User> findFriendsByName(String name, User user, int limit)
	{
		final List<User> friends = new ArrayList<User>();
		final String[] fullname = name.split(" ", 2);
		
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FIND_FRIENDS_BY_NAME_QUERY);
			statement.setLong  (1, user.getId());
			statement.setLong  (2, user.getId());
			
			statement.setString(3, (fullname.length > 0) ? fullname[0] : "");
			statement.setString(4, (fullname.length > 1) ? fullname[1] : "");
			statement.setString(5, (fullname.length > 1) ? fullname[1] : "");
			statement.setInt(6, limit);
			
			ResultSet result = statement.executeQuery();
			
			while ( result.next() )
				friends.add( createUserFromResult(result, null) );
			return friends;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return friends; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	protected static void setDataToInsertStatement( final User usr, final PreparedStatement statement ) throws SQLException
	{
		statement.setLong     (1, usr.getId());
		statement.setString   (2, usr.getFirstName());
		statement.setString   (3, usr.getLastName());
		statement.setString   (4, usr.getEmail());
		statement.setTimestamp(5, DateUtil.toJDBC(usr.getBirthday()));
		statement.setString   (6, usr.getGender());
		statement.setString   (7, usr.getPassword());
		statement.setLong     (8, usr.getFamily().getId());
		statement.setBoolean  (9, usr.getIsKid());
	}
	
	private User createUserFromResult( final ResultSet result, final Family family ) throws SQLException
	{
		final DaoFactory daoFactory = DBManager.getInstance().getDaoFactory();
		final User usr = new User();
		
		usr.setId(result.getLong("user_id"));
		usr.setFirstName(result.getString("first_name"));
		usr.setLastName(result.getString("last_name"));
		usr.setEmail(result.getString("email"));
		usr.setBirthday(DateUtil.toJava(result.getTimestamp("birthday")));
		usr.setGender(result.getString("gender"));
		usr.setPassword(result.getString("password"));
		
		usr.setFamily( (family == null ) ? daoFactory.getFamilyDao()
				.findByPrimaryKey(result.getLong("family_id")) : family );
		
		usr.setFollowersCount(getFollowersCount(usr));
		usr.setFavoritesGenres(getFavoritesGenres(usr));
		
		usr.setProfileImagePath(result.getString("profile_img"));
		usr.setIsKid(result.getBoolean("kid"));
		
		return usr;
	}
	
	private Integer getFollowersCount( final User user )
	{
		try
		{
			final PreparedStatement statement = statementPrompter.prepareStatement(FOLLOWERS_COUNT_QUERY);
			statement.setLong(1, user.getId());
			
			ResultSet result = statement.executeQuery();
			
			if ( result.next() )
				return result.getInt("count");
			return 0;
		}
		
		catch (SQLException e)
		{ e.printStackTrace(); return 0; }
		
		finally 
		{ statementPrompter.onFinalize(); }
	}
	
	private List<String> getFavoritesGenres( final User user )
	{
		final String g1 = "Action";
		final String g2 = "Cartoon";
		final String g3 = "Science";
		
		final List<String> genres = new ArrayList<String>();
		genres.add(g1);
		genres.add(g2);
		genres.add(g3);
		
		return genres;
	}
}

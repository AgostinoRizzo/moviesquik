/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao.jdbc;

import it.unical.mat.moviesquik.persistence.DataSource;
import it.unical.mat.moviesquik.persistence.dao.BillingDao;
import it.unical.mat.moviesquik.persistence.dao.ChatMessageDao;
import it.unical.mat.moviesquik.persistence.dao.CommentDao;
import it.unical.mat.moviesquik.persistence.dao.CreditCardDao;
import it.unical.mat.moviesquik.persistence.dao.DaoFactory;
import it.unical.mat.moviesquik.persistence.dao.FamilyDao;
import it.unical.mat.moviesquik.persistence.dao.FollowingDao;
import it.unical.mat.moviesquik.persistence.dao.FriendshipDao;
import it.unical.mat.moviesquik.persistence.dao.MediaContentDao;
import it.unical.mat.moviesquik.persistence.dao.MediaContentGenreDao;
import it.unical.mat.moviesquik.persistence.dao.MediaContentSearchDao;
import it.unical.mat.moviesquik.persistence.dao.NotificationDao;
import it.unical.mat.moviesquik.persistence.dao.PlanBillingUpdateTransaction;
import it.unical.mat.moviesquik.persistence.dao.PostDao;
import it.unical.mat.moviesquik.persistence.dao.PostFeedbackDao;
import it.unical.mat.moviesquik.persistence.dao.RegistrationTransaction;
import it.unical.mat.moviesquik.persistence.dao.UserDao;
import it.unical.mat.moviesquik.persistence.dao.WatchlistDao;
import it.unical.mat.moviesquik.persistence.dao.WatchlistItemDao;
import it.unical.mat.moviesquik.persistence.dao.analytics.AnalyticsExtractorDao;
import it.unical.mat.moviesquik.persistence.dao.analytics.MediaAnalyticsHistoryLogDao;
import it.unical.mat.moviesquik.persistence.dao.analytics.MediaContentReviewDao;
import it.unical.mat.moviesquik.persistence.dao.analytics.MediaContentSharingDao;
import it.unical.mat.moviesquik.persistence.dao.analytics.MediaContentStatisticsDao;
import it.unical.mat.moviesquik.persistence.dao.analytics.MediaStatisticLogDao;
import it.unical.mat.moviesquik.persistence.dao.analytics.WatchHistoryLogDao;
import it.unical.mat.moviesquik.persistence.dao.business.AnalystDao;
import it.unical.mat.moviesquik.persistence.dao.business.CDNServerDao;
import it.unical.mat.moviesquik.persistence.dao.jdbc.analytics.AnalyticsExtractorDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.analytics.MediaAnalyticsHistoryLogDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.analytics.MediaContentReviewDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.analytics.MediaContentSharingDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.analytics.MediaContentStatisticsDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.analytics.MediaStatisticLogDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.analytics.WatchHistoryLogDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.business.AnalystDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.business.CDNServerDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.media.MediaContentDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.media.MediaContentGenreDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.media.MediaContentSearchDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.movieparty.MoviePartyDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.movieparty.MoviePartyInvitationDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.jdbc.movieparty.MoviePartyParticipationDaoJDBC;
import it.unical.mat.moviesquik.persistence.dao.movieparty.MoviePartyDao;
import it.unical.mat.moviesquik.persistence.dao.movieparty.MoviePartyInvitationDao;
import it.unical.mat.moviesquik.persistence.dao.movieparty.MoviePartyParticipationDao;

/**
 * @author Agostino
 *
 */
public class DaoFactoryJDBC implements DaoFactory
{
	private final DataSource dataSource;
	
	public DaoFactoryJDBC( final DataSource dataSource )
	{
		this.dataSource = dataSource;
	}
	
	@Override
	public UserDao getUserDao()
	{
		return new UserDaoJDBC(getNewStatementPrompter());
	}

	@Override
	public FamilyDao getFamilyDao()
	{
		return new FamilyDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public CreditCardDao getCreditCardDao()
	{
		return new CreditCardDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public BillingDao getBillingDao()
	{
		return new BillingDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public MediaContentDao getMediaContentDao()
	{
		return new MediaContentDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public MediaContentSearchDao getMediaContentSearchDao()
	{
		return new MediaContentSearchDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public MediaContentGenreDao getMediaContentGenreDao()
	{
		return new MediaContentGenreDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public FriendshipDao getFriendshipDao()
	{
		return new FriendshipDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public FollowingDao getFollowingDao()
	{
		return new FollowingDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public NotificationDao getNotificationDao()
	{
		return new NotificationDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public PostDao getPostDao()
	{
		return new PostDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public CommentDao getCommentDao()
	{
		return new CommentDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public PostFeedbackDao getPostFeedbackDao()
	{
		return new PostFeedbackDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public WatchlistDao getWatchlistDao()
	{
		return new WatchlistDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public WatchlistItemDao getWatchlistItemDao()
	{
		return new WatchlistItemDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public MoviePartyDao getMoviePartyDao()
	{
		return new MoviePartyDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public MoviePartyInvitationDao getMoviePartyInvitationDao()
	{
		return new MoviePartyInvitationDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public MoviePartyParticipationDao getMoviePartyParticipationDao()
	{
		return new MoviePartyParticipationDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public ChatMessageDao getChatMessageDao()
	{
		return new ChatMessageDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public MediaContentReviewDao getMediaContentReviewDao()
	{
		return new MediaContentReviewDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public WatchHistoryLogDao getWatchHistoryLogDao()
	{
		return new WatchHistoryLogDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public MediaStatisticLogDao getMediaStatisticLogDao()
	{
		return new MediaStatisticLogDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public MediaContentStatisticsDao getMediaContentStatisticsDao()
	{
		return new MediaContentStatisticsDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public MediaContentSharingDao getMediaContentSharingDao()
	{
		return new MediaContentSharingDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public AnalyticsExtractorDao getAnalyticsExtractorDao()
	{
		return new AnalyticsExtractorDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public AnalystDao getAnalystDao()
	{
		return new AnalystDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public MediaAnalyticsHistoryLogDao getMediaAnalyticsHistoryLogDao()
	{
		return new MediaAnalyticsHistoryLogDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public CDNServerDao getCDNServerDao()
	{
		return new CDNServerDaoJDBC(getNewStatementPrompter());
	}
	
	@Override
	public RegistrationTransaction getRegistrationTransaction()
	{
		return new RegistrationTransactionJDBC(getNewStatementPrompter());
	}
	
	@Override
	public PlanBillingUpdateTransaction getPlanBillingUpdateTransaction()
	{
		return new PlanBillingUpdateTransactionJDBC(getNewStatementPrompter());
	}
	
	private StatementPrompterJDBC getNewStatementPrompter()
	{
		return new StatementPrompterJDBC(dataSource);
	}

}

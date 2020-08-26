/**
 * 
 */
package it.unical.mat.moviesquik.persistence.dao;

import it.unical.mat.moviesquik.persistence.dao.analytics.AnalyticsExtractorDao;
import it.unical.mat.moviesquik.persistence.dao.analytics.MediaAnalyticsHistoryLogDao;
import it.unical.mat.moviesquik.persistence.dao.analytics.MediaContentReviewDao;
import it.unical.mat.moviesquik.persistence.dao.analytics.MediaContentSharingDao;
import it.unical.mat.moviesquik.persistence.dao.analytics.MediaContentStatisticsDao;
import it.unical.mat.moviesquik.persistence.dao.analytics.MediaStatisticLogDao;
import it.unical.mat.moviesquik.persistence.dao.analytics.WatchHistoryLogDao;
import it.unical.mat.moviesquik.persistence.dao.business.AnalystDao;
import it.unical.mat.moviesquik.persistence.dao.movieparty.MoviePartyDao;
import it.unical.mat.moviesquik.persistence.dao.movieparty.MoviePartyInvitationDao;
import it.unical.mat.moviesquik.persistence.dao.movieparty.MoviePartyParticipationDao;

/**
 * @author Agostino
 *
 */
public interface DaoFactory
{
	public UserDao getUserDao();
	public FamilyDao getFamilyDao();
	public CreditCardDao getCreditCardDao();
	public BillingDao getBillingDao();
	public MediaContentDao getMediaContentDao();
	public MediaContentSearchDao getMediaContentSearchDao();
	public MediaContentGenreDao getMediaContentGenreDao();
	public FriendshipDao getFriendshipDao();
	public FollowingDao getFollowingDao();
	public NotificationDao getNotificationDao();
	public PostDao getPostDao();
	public CommentDao getCommentDao();
	public PostFeedbackDao getPostFeedbackDao();
	public WatchlistDao getWatchlistDao();
	public WatchlistItemDao getWatchlistItemDao();
	public MoviePartyDao getMoviePartyDao();
	public MoviePartyInvitationDao getMoviePartyInvitationDao();
	public MoviePartyParticipationDao getMoviePartyParticipationDao();
	public ChatMessageDao getChatMessageDao();
	public MediaContentReviewDao getMediaContentReviewDao();
	public WatchHistoryLogDao getWatchHistoryLogDao();
	public MediaStatisticLogDao getMediaStatisticLogDao();
	public MediaContentStatisticsDao getMediaContentStatisticsDao();
	public MediaContentSharingDao getMediaContentSharingDao();
	public AnalyticsExtractorDao getAnalyticsExtractorDao();
	public AnalystDao getAnalystDao();
	public MediaAnalyticsHistoryLogDao getMediaAnalyticsHistoryLogDao();
	
	public RegistrationTransaction getRegistrationTransaction();
	public PlanBillingUpdateTransaction getPlanBillingUpdateTransaction();
}

import tweepy
access_token = '1765406326190956544-zwGEUqCwjfjbX9xNHDobWwnEDT3V0c'
access_secret = 'k30q2ojCi03gyVnQg6RELYIC9S7Pq51dp9spv3rGZWrVr'
consumer_key = '8eFaLRqzb98OHeTBzW7e67sT8'
consumer_secret = 'qPbSSIaCNbU3vy2XuRElFHqNr8dxgEhGIe31DLJCgVf3dUgl72'

def get_twitter_conn_v1(api_key, api_secret, access_token, access_token_secret) -> tweepy.API:
    """Get twitter conn 1.1"""

    auth = tweepy.OAuth1UserHandler(api_key, api_secret)
    auth.set_access_token(
        access_token,
        access_token_secret,
    )
    return tweepy.API(auth)

def get_twitter_conn_v2(api_key, api_secret, access_token, access_token_secret) -> tweepy.Client:
    """Get twitter conn 2.0"""

    client = tweepy.Client(
        consumer_key=api_key,
        consumer_secret=api_secret,
        access_token=access_token,
        access_token_secret=access_token_secret,
    )

    return client
def a(event_name):
    client_v1 = get_twitter_conn_v1(consumer_key, consumer_secret, access_token, access_secret)
    client_v2 = get_twitter_conn_v2(consumer_key, consumer_secret, access_token, access_secret)
    # media_path = imageUrl
    # media = client_v1.media_upload(filename=media_path)
    # media_id = media.media_id
    # client_v2.create_tweet(text=event_name,media_ids=[media_id])
    client_v2.create_tweet(text=event_name)

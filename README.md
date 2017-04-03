# juji hw

The deadline is April 4th. This is a working log to record what I did and what problems I met.

## day1: Design

>"Representative Tweets"
>
>You will be developing a working Web site that allows your users to see a twitter user's "representative tweets". At most 10 "representative tweets" must be selected from the tweets of a twitter user. You are free to devise your own implementation of "representativeness", as long as you can articulate your justification.
>
>On your site, when a user enters a person's twitter handle, your user expects to see her/his representative tweets,  as well as the evidence of their representativeness.  Feel free to improve the user experience in whatever ways you feel necessary given the time you have.
>
>Your submission includes a link to the working Web site, a link (or attachment) to the source code, and a link (or attachment) to a document where you outline and justify your choices in design and implementation. You are free to use whatever tools, resources and libraries that you have a right to use.

### Website Architecture

1. Front-end: 1 page for user to input his twitter username. 1 page for displaying user's most 10 representative tweets. 
2. Back-end: 
	+ received user's username as a request, 
	+ return user's most representative tweets as response.

possible tools: spark java as website framework. MongoDB as database.

Here is what happens on our server, between user input twitter account and see his top-10 representative:

1. server received a twitter username.
2. server use some twitter API to download this user's most recent 1000 twitters.
3. server got the data and send them into a **"Representativeness Ranker"** so the tweets text will be sorted by some ranking algorithm which I will discuss later.
4. server stores the top-10 representative tweets into local DB, also send back this computing result to a user.

### Representative Ranker
(Just an example)

+ Input: user's most recent 1000 original tweets.

+ Output: user's most representative 10 tweets.

Here the problem is how to define "representative"? What kind of tweet can mostly represent a user's personality or features?

>1. tweets with higher "likes" number and "retweet" number.

I choose "like" and "retweet" as a representative feature based on the following assumptions:


+ The input data is user's original tweets (not just copy or retweet from somewhere else). 
+ A user's original tweets usually coordinate with his personality and features. 
+ Followers follow this user usually because they appreciate his personality or features.
+ Most "like" and "retweet" are from user's direct followers, or followers' followers within 2 connections.


>2. tweet with higher "like" and "retweet" from mutual connections.

Also this aspect considers the following assumption: 

+ 2 users follow each other usually because they share similar personality or interests. 

Thus I believe that "likes" and "retweet" from mutual connections will be more representative. When ranker computing a score, I will add more weights on this case.

Here is an example:

User A has a tweet, which has been liked by 300 times, retweeted by 500 times. Also the like and retweet number from mutual connection is 20 and 40. Thus for this tweet, ranker can compute a score in such a way:

```
score(tweet) = (300 + 20) / 300 * w1 + (500 + 40) / 500 * w2
```


### A Plan

Iteration 1: Understand the problem. Analysis product features and core modules.

Iteration 2: data collector module to obtain data from twitter. 

Iteration 3: Representative Ranker implement.

Iteration 4: website framework. UI design + DB + server

Iteration 5: Deploy & Test.

### Report Today

Web site framework was done, Frond-end and back-end could communicate. 

Deployment does not work. 

## Day 2

Today's task is get data from twitter. Server send a username into twitter API, the response is this user's most recent tweets, which should be stored in mongoDB.
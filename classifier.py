# -*- coding: utf-8 -*-
from naiveBayesClassifier import tokenizer
from naiveBayesClassifier.trainer import Trainer
from naiveBayesClassifier.classifier import Classifier
import sys

users = "uvcyclotron papercIip_syste gawankarmangesh ramri326 BrainFooders pritam2003 saketbairoliya2 ashwini1024 ShrutiGupta128 akd25395 HARSHA_081 AnuragKankanala DellCares VartikaAg GlenGilmore abhilashc10 debumathur nepal_volunteer Shimba15 UtkarshPathrabe pratikBg notarymama ekam234 shikharBrajesh JhimanshuI f2011210 iapurvakumar ReadyForHillary GChandria davidgasiunas PejantanDiedra pIeasurabIy_hon NavdeepBajaj1 tenaciousrahul Applenewsnow44 himanshu7676 MayurAjaySaxena BennyBurrito1 KuldeepWaldiya booklikha Mansi1410 ullasaurs JetpackBearApp RanaPra07438338 Vivek_Ghose spandana2802 rohanmanchanda Sichinain YashJai85978380"

users = users.split()

influ = dict()
tweets = dict()
topicInfluence = dict()

topic = sys.argv[1]
if(topic!='politics' and topic!="sports" and topic !="technology"):
    print "please enter one of these - politics/sports/technology"
    exit()
#print sys.argv[0]

###################classifier################################################
def tweet_classification(unknownInstance):
    newsTrainer = Trainer(tokenizer)
    with open("train.txt") as f:
        for line in f:
            str = line
            str = str.split(' ', 1 );
            newsTrainer.train(str[1], str[0])
    newsClassifier = Classifier(newsTrainer.data, tokenizer)
	# Now you have a classifier which can give a try to classifiy text of news whose
	# category is unknown, yet.
    classification = newsClassifier.classify(unknownInstance)
	# the classification variable holds the possible categories sorted by
	# their probablity value
    ans = dict()
    for i in range(3):
        if(classification[0][1]!=0.0):
            ans[classification[i][0]] = classification[i][1] / classification[0][1];
            #print classification
    #print ans
    return ans
#############################################################################

#get influence
with open("influence.txt") as fin:
    for line in fin:
        words = line.split()
        influ[words[0]] = words[1]

#get tweets for all users
with open("final_tweets.txt") as fit:
    for line in fit:
        words = line.split(' ',1)
        if words[0] not in tweets:
            tweets[words[0]] = []
        tweets[words[0]].append(words[1])

#get topic influence for all users
for user in users:
    if user in tweets:
        inf = 0.0
        i = 0.0
        for tweet in tweets[user]:
            #print "tweet", tweet
            prob = tweet_classification(tweet)
            #print "prob = " , prob
            if topic in prob:
                inf+=prob[topic]
            else:
                prob = 0
            i= i+1
        #print "influ - ",influ[user]
        #print inf/i
        topicInfluence[user] = float((inf/i))*float(influ[user])

maxm = 0.0
for user in users:
    if(user in topicInfluence):
        if(topicInfluence[user] > maxm):
            maxm = topicInfluence[user]
if(maxm!=0.0):
    for user in users:
        if user in topicInfluence:
            topicInfluence[user] = topicInfluence[user] / maxm

out = file("topic_influence.txt", 'w')
for user in users:
    if user in topicInfluence:
        out.write(user + " " +str(topicInfluence[user]) + "\n")
    else:
        out.write(user + " " + str(0.0) + "\n")



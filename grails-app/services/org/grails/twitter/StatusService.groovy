package org.grails.twitter

import org.grails.twitter.auth.Person

import grails.transaction.Transactional

@Transactional
class StatusService {

    def brokerMessagingTemplate

    def groovyPageRenderer
    def twitterSecurityService
    def timelineService

    def getFollowersOf(String userName) {
        Person.where {
            followed.userName == userName
        }.property('userName').list()
    }

    void clearTimelineCacheForUser(String newMessageUserName) {
        log.debug "Message received. New status message posted by user <${newMessageUserName}>."
        def following = getFollowersOf(newMessageUserName)

        following.each { uname ->
            timelineService.clearTimelineCacheForUser(uname)
        }
    }

    String updateStatus(String message, String userName) {
        def author = Person.findByUserName(userName)
        def status = new Status(message: message, author: author)
        status.save()

        timelineService.clearTimelineCacheForUser(userName)

        def html = groovyPageRenderer.render template: '/status/statusMessage', model: [statusMessage: status]

        def sendTo = getFollowersOf(userName) + userName
        sendTo.each { user ->
            brokerMessagingTemplate.convertAndSendToUser user, '/queue/timeline', html
        }

        html
    }

    void unfollow(String userName) {
        def person = Person.findByUserName(userName)
        if (person) {
            def currentUser = twitterSecurityService.currentUser
            currentUser.removeFromFollowed(person)
            timelineService.clearTimelineCacheForUser(currentUser.userName)
        }
    }

    void follow(String userName) {
        def person = Person.findByUserName(userName)
        if (person) {
            def currentUser = twitterSecurityService.currentUser
            currentUser.addToFollowed(person)
            timelineService.clearTimelineCacheForUser(currentUser.userName)
        }
    }
}

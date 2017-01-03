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

    void updateStatus(String message) {
        def status = new Status(message: message)
        status.author = twitterSecurityService.loadCurrentUser()
        status.save()
        timelineService.clearTimelineCacheForUser(status.author.userName)

        def renderedStatus = groovyPageRenderer.render template: '/status/statusMessages',
                model: [statusMessage: status]

        getFollowersOf(status.author.userName).each { follower ->
            brokerMessagingTemplate.convertAndSendToUser follower, '/queue/timeline', renderedStatus
        }
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

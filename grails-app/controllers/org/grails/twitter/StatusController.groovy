package org.grails.twitter

import grails.plugin.springsecurity.annotation.Secured
import org.grails.twitter.auth.Person

@Secured('isAuthenticated()')
class StatusController {

    def statusService
    def timelineService
    def springSecurityService

    def index() {
        def messages = timelineService.getTimelineForUser()
        def username = springSecurityService.principal.username
        def person = Person.findByUserName(username)
        def tweetCount = Status.where { author.userName == username }.count()

        def following = person.followed
        def followers = Person.where { followed.userName == username }.list()
        def otherUsers = Person.list() - following - followers - person

        [statusMessages: messages, person: person, tweetCount: tweetCount,
         following: following, followers: followers, otherUsers: otherUsers]
    }

    def updateStatus(String message) {
        statusService.updateStatus message
        def messages = timelineService.getTimelineForUser()

        def content = twitter.renderMessages messages: messages
        render content
    }
}

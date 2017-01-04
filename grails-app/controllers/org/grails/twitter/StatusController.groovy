package org.grails.twitter

import grails.plugin.springsecurity.annotation.Secured
import org.grails.twitter.auth.Person

@Secured('isAuthenticated()')
class StatusController {

    def statusService
    def timelineService
    def twitterSecurityService

    def index() {
        def messages = timelineService.timelineForUser
        def username = twitterSecurityService.currentUsername
        def person = Person.findByUserName(username)
        def totalStatusCount = Status.where { author.userName == username }.count()

        def following = person.followed
        def followers = Person.where { followed.userName == username }.list()
        def otherUsers = Person.list() - following - followers - person

        [statusMessages: messages,
         person: person,
         totalStatusCount: totalStatusCount,
         following: following,
         followers: followers,
         otherUsers: otherUsers]
    }

    def updateStatus(String message) {
        statusService.updateStatus message

        def messages = timelineService.timelineForUser
        def content = twitter.renderMessages messages: messages
        render content
    }
}

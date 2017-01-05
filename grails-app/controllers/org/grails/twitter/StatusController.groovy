package org.grails.twitter

import grails.plugin.springsecurity.annotation.Secured
import java.security.Principal
import org.grails.twitter.auth.Person
import org.springframework.messaging.handler.annotation.MessageMapping

@Secured('isAuthenticated()')
class StatusController {

    def statusService
    def timelineService
    def twitterSecurityService

    def index() {
        def messages = timelineService.timelineForUser
        def person = twitterSecurityService.currentUser
        def totalStatusCount = Status.where { author.userName == person.userName }.count()

        def following = person.followed
        def followers = Person.where { followed.userName == person.userName }.list()
        def otherUsers = Person.list() - following - followers - person

        [statusMessages: messages,
         person: person,
         totalStatusCount: totalStatusCount,
         following: following,
         followers: followers,
         otherUsers: otherUsers]
    }

    @MessageMapping('/updateStatus')
    protected String updateStatus(String message, Principal author) {
        statusService.updateStatus message, author.principal.username
    }
}

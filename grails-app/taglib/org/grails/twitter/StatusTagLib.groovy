package org.grails.twitter

import org.grails.twitter.auth.Person

class StatusTagLib {
    static namespace = 'twitter'

    def twitterSecurityService

    def renderCurrentUserName = { attrs ->
        def currentUser = twitterSecurityService.currentUser
        out << currentUser.displayName
    }
    def renderMessages = { attrs ->
        def messages = attrs.messages
        messages.eachWithIndex { message, counter ->
            out << g.render(template: '/status/statusMessages', model: [statusMessage: message, messageCounter: counter])
        }
    }

    def followLink = { attrs ->
        def currentUser = twitterSecurityService.currentUser
        def userName = attrs.userName
        if(userName != currentUser.userName) {
            if(currentUser.followed.find { it.userName == userName }) {
                out << g.link(controller: 'person', action: 'unfollow', params: [userToUnfollow: userName]) {
                    'unfollow'
                }
            } else {
                out << g.link(controller: 'person', action: 'follow', params: [userToFollow: userName]) {
                    'follow'
                }
            }
        } else {
            out << '(you)'
        }
    }
}

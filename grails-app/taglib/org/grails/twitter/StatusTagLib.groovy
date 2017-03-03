package org.grails.twitter

class StatusTagLib {
    static namespace = 'twitter'

    def twitterSecurityService

    def renderCurrentUserName = { attrs ->
        def currentUser = twitterSecurityService.currentUser
        out << currentUser.displayName
    }

    def renderMessages = { attrs ->
        def messages = attrs.messages
        messages.each { message ->
            out << g.render(template: '/status/statusMessage', model: [statusMessage: message])
        }
    }

    def followLink = { attrs ->
        def currentUsername = twitterSecurityService.currentUsername
        def userName = attrs.userName
        if(userName != currentUsername) {
            def currentUser = twitterSecurityService.currentUser
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

package org.grails.twitter

class StatusController {

    def statusService
    def timelineService
    def twitterSecurityService

    def index() {
        def messages = timelineService.getTimelineForUser(twitterSecurityService.principal.username)
        [statusMessages: messages]
    }

    def updateStatus(String message) {
        statusService.updateStatus message
        def messages = timelineService.getTimelineForUser(twitterSecurityService.principal.username)
        
        def content = twitter.renderMessages messages: messages
        render content
    }
}

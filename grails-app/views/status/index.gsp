<html>
<head>
    <meta name="layout" content="main"/>
    <title>What Are You Doing?</title>
</head>
<body>
    <g:set var="displayName"><twitter:renderCurrentUserName /></g:set>

    <g:render template="/navbar" />

    <div class="pageBody">
        <div class="column sideboard">
            <div id="profile" class="panel">
                <div>
                    <gravatar:image email="${person.email ?: person.displayName}" defaultGravatarUrl="retro" />
                    <h2>${displayName}</h2>
                </div>
                <div id="userStats">
                    <dl>
                        <dt><g:message code="status.updateCount.label" /></dt><dd>${tweetCount}</dd>
                        <dt><g:message code="status.followingCount.label" /></dt><dd>${following.size()}</dd>
                        <dt><g:message code="status.followersCount.label" /></dt><dd>${followers.size()}</dd>
                    </dl>
                </div>
            </div>
            <div id="following" class="panel">
                <h2><g:message code="status.followingList.label" /></h2>
                <g:render template="/status/peopleList" model="[people: following]" />
            </div>
            <div id="followers" class="panel">
                <h2><g:message code="status.followersList.label" /></h2>
                <g:render template="/status/peopleList" model="[people: followers]" />
            </div>
            <div id="otherUsers" class="panel">
                <h2><g:message code="status.otherUsersList.label" /></h2>
                <g:render template="/status/peopleList" model="[people: otherUsers]" />
            </div>
        </div>
        <div class="column mainboard">
            <h1><g:message code="status.greeting" args="${displayName}" /></h1>
            <div class="updateStatusForm">
                <g:formRemote url="[action: 'updateStatus']" update="messages" name="updateStatusForm"
                              onSuccess="document.getElementById('message').value=''">
                    <g:textField name="message" value="" placeholder="${g.message(code: 'status.placeholder')}" />
                </g:formRemote>
            </div>
            <div id="messages">
                <twitter:renderMessages messages="${statusMessages}"/>
            </div>
        </div>
    </div>

    <asset:javascript src="spring-websocket" />
    <script type="text/javascript">
        $(function() {
            var socket = new SockJS("${createLink(uri: '/stomp')}");
            var client = Stomp.over(socket);

            client.connect({}, function() {
                client.subscribe('/user/queue/timeline', function(message) {
                    $(message.body).prependTo("#messages").hide().slideDown();
                })
            });
        });
    </script>
</body>
</html>
